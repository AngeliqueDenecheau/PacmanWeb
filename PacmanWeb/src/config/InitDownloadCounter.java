package config;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class InitDownloadCounter implements ServletContextListener {
    private static final String ATT_DOWNLOAD_COUNTER = "downloadcounter";
	
	private int counter;

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent event)  { 
    	/* Récupération du ServletContext lors du chargement de l'application */
        ServletContext servletContext = event.getServletContext();
        
        /* Instanciation de notre DAOFactory */
        this.counter = 0;
        
        /* Enregistrement dans un attribut ayant pour portée toute l'application */
        servletContext.setAttribute(ATT_DOWNLOAD_COUNTER, this.counter);
    }
    
	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent event)  { 
        /* Rien à réaliser lors de la fermeture de l'application... */
    }	
}
