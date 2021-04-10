package forms;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import beans.User;
import dao.CosmeticDao;
import dao.UserDao;

public class UpdateForm {
	public static final String PARAM_IMAGE = "image";
	public static final String PARAM_LOGIN = "login";
	public static final String PARAM_EMAIL = "email";
	public static final String PARAM_PASSWORD = "password";
	public static final String PARAM_CONFIRM_PASSWORD = "confirm_password";
	public static final int TAILLE_TAMPON = 10240; // 10 ko

	private String erreur;
	
	public String getErreur() {return erreur;};
	
	private UserDao userDao;
	
	public UpdateForm(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public User update(HttpServletRequest request, String chemin) {
		/* Récupération du contenu des champs text */
		String login = getValeurParam(request, PARAM_LOGIN);
		String email = getValeurParam(request, PARAM_EMAIL);
		String password = getValeurParam(request, PARAM_PASSWORD);
		String confirm_password = getValeurParam(request, PARAM_CONFIRM_PASSWORD);
	
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		if(login != null && !login.isBlank() && !login.equals(user.getLogin())) {traiterLogin(login, user);}
		if(email != null && !email.isBlank() && !email.equals(user.getEmail())) {traiterEmail(email, user);}
		if(password != null && !password.isBlank()) {traiterPassword(password, confirm_password, user);}
		if(erreur != null) return null;
		
		String nomFichier = null;
		InputStream contenuFichier = null;
		try {
			/* Les données reçues sont multipart, on doit donc utiliser la méthode getPart() pour traiter le champ d'envoi de fichiers */
		    Part part = request.getPart(PARAM_IMAGE);
		    
		    /* Il faut déterminer s'il s'agit d'un champ classique ou d'un champ de type fichier : on délègue cette opération  à la méthode utilitaire getNomFichier() */
		    nomFichier = getNomFichier(part);

		    System.out.println("nom fichier : " + nomFichier); 
		    /* Si la méthode a renvoyé quelque chose, il s'agit donc d'un champ de type fichier (input type="file") */
		    if(nomFichier != null && !nomFichier.isEmpty()) {
		    	
		    	contenuFichier = part.getInputStream();
		    	
		    	/* Extraction du type MIME du fichier depuis l'InputStream nommé "contenu" */
		    	/*MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.MagicMimeMimeDetector");
		    	InputStream contenu = contenuFichier;
		    	BufferedInputStream bufferedIs = new BufferedInputStream(contenu);
		    	Collection<?> mimeTypes = MimeUtil.getMimeTypes(bufferedIs);*/
		    	
		    	/* Si le fichier est bien une image, alors son en-tête MIME commence par la chaîne "image" */
		    	/*if (!mimeTypes.toString().startsWith("image")) {
		    		erreur = "Le fichier envoyé doit être une image !";
		    		return null;
		    	}*/
		    	
		    	String[] formats = {".jpeg", ".jpg", ".png", ".webp", ".svg"};
		    	String extension = nomFichier.substring(nomFichier.lastIndexOf('.'));
		    	
		    	if(!Arrays.asList(formats).contains(extension)) {
		    		erreur = "Ce format d'image n'est pas accepté !";
		    		return null;
		    	}
		    	
		    	nomFichier = "user" + user.getUser_id() + extension;
		    }
		} catch (IllegalStateException e) {
			/* Exception retournée si la taille des données dépasse les limites définies dans la section <multipart-config> de la déclaration de notre servlet d'upload dans le fichier web.xml */
	        erreur = "Le fichier envoyé ne doit pas dépasser 1 Mo.";
	        return null;
	    } catch (IOException e) {
	        /* Exception retournée si une erreur au niveau des répertoires de stockage survient (répertoire inexistant, droits d'accès insuffisants, etc.) */
	        erreur = "Erreur de configuration du serveur.";
	        return null;
	    } catch (ServletException e) {
	        /* Exception retournée si la requête n'est pas de type multipart/form-data. Cela ne peut arriver que si l'utilisateur essaie de contacter la servlet d'upload par un formulaire différent de celui qu'on lui propose */
	        erreur = "Ce type de requête n'est pas supporté, merci d'utiliser le formulaire prévu pour envoyer votre fichier.";
	        return null;
	    }
		
		if(nomFichier != null && contenuFichier != null) {
	        /* Écriture du fichier sur le disque */
	        try {
	            ecrireFichier(contenuFichier, nomFichier, chemin);
	        } catch (Exception e) {
	            erreur = "Erreur lors de l'écriture du fichier sur le disque.";
	            return null;
	        }
	        user.setImage(nomFichier);
        }
		
        userDao.update(user);
		if(password != null && !password.isBlank()) userDao.updatePassword(user);
			
		return userDao.find_by_id(user.getUser_id());
	}
    
    /* Méthode utilitaire qui retourne null si un champ est vide, et son contenu sinon */
	private static String getValeurParam(HttpServletRequest request, String param) {
	    String valeur = request.getParameter(param);
	    if (valeur == null || valeur.trim().length() == 0) return null;
	    return valeur.trim();
	}
    
	public void traiterLogin(String login, User user) {
		try {
			validationLogin(login);
		} catch (FormValidationException e) {
			erreur = e.getMessage();
		}
		if(erreur == null) user.setLogin(login);
	}
	
	public void validationLogin(String login) throws FormValidationException {
    	if(login.length() < 3 || login.length() > 20) throw new FormValidationException("Votre login doit être compris entre 3 et 20 caractères !");
    	if(userDao.find_by_login(login) != null) throw new FormValidationException("Ce login est déjà pris !");
    }
	
	public void traiterEmail(String email, User user) {
		try {
			validationEmail(email);
		} catch (FormValidationException e) {
			erreur = e.getMessage();
		}
		if(erreur == null) user.setEmail(email);
	}
    
    public void validationEmail(String email) throws FormValidationException {
    	if(email.length() < 1) throw new FormValidationException("Veuillez renseigner votre adresse email !");
    	if(email.length() > 60) throw new FormValidationException("Votre adresse email est trop longue !");
    	if(!email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) throw new FormValidationException("Veuillez renseigner une adresse email valide !");
    	if(userDao.find_by_email(email) != null) throw new FormValidationException("Un compte existe déjà avec cette adresse email !");
    }
	
	public void traiterPassword(String password, String confirm_password, User user) {
		try {
			validationPassword(password, confirm_password);
		} catch (FormValidationException e) {
			erreur = e.getMessage();
		} catch (NoSuchAlgorithmException e) {
			erreur = e.getMessage();
		}
		if(erreur == null) user.setPassword(password);
	}
    
    public void validationPassword(String password, String confirm_password) throws FormValidationException, NoSuchAlgorithmException {
    	if(password.length() < 8 || password.length() > 20) throw new FormValidationException("Votre mot de passe doit être compris entre 8 et 20 caractères !");
    	Pattern pattern = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(password);
        if(!matcher.find()) throw new FormValidationException("Votre mot de passe doit contenir au minimum un chiffre, une lettre et un caractère spécial !");
    	if(!password.equals(confirm_password)) throw new FormValidationException("Vos mots de passe ne correspondent pas !");
    }
	
    /* Méthode utilitaire qui a pour unique but d'analyser l'en-tête "content-disposition", et de vérifier si le paramètre "filename" y est présent. Si oui, alors le champ traité est de type File et la méthode retourne son nom, sinon il s'agit d'un champ de formulaire classique et la méthode retourne null */
    private static String getNomFichier(Part part) {
        /* Boucle sur chacun des paramètres de l'en-tête "content-disposition" */
        for (String contentDisposition : part.getHeader("content-disposition").split(";")) {
            /* Recherche de l'éventuelle présence du paramètre "filename" */
            if (contentDisposition.trim().startsWith("filename")) {
                /* Si "filename" est présent, alors renvoi de sa valeur, c'est-à-dire du nom de fichier sans guillemets */
                return contentDisposition.substring(contentDisposition.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        /* Si rien n'a été trouvé */
        return null;
    }

    /* Méthode utilitaire qui a pour but d'écrire le fichier passé en paramètre sur le disque, dans le répertoire donné et avec le nom donné */
    private void ecrireFichier(InputStream contenuFichier, String nomFichier, String chemin) throws FormValidationException {
        /* Prépare les flux. */
        BufferedInputStream entree = null;
        BufferedOutputStream sortie = null;
        try {
            /* Ouvre les flux. */
            entree = new BufferedInputStream(contenuFichier, TAILLE_TAMPON);
            sortie = new BufferedOutputStream(new FileOutputStream(new File(chemin + nomFichier)),TAILLE_TAMPON);
            
            /* Lit le fichier reçu et écrit son contenu dans un fichier sur le disque. */
            byte[] tampon = new byte[TAILLE_TAMPON];
            int longueur = 0;
            while((longueur = entree.read(tampon)) > 0) {
                sortie.write(tampon, 0, longueur);
            }
        } catch (Exception e) {
            throw new FormValidationException("Erreur lors de l'écriture du fichier sur le disque.");
        } finally {
            try {
                sortie.close();
            } catch (IOException ignore) {
            }
            try {
                entree.close();
            } catch (IOException ignore) {
            }
        }
    }
}
