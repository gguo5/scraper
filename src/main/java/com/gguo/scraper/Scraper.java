/*
 * Scraper (TM)
 * Copyright 2017 gguo.
 *
 * **********************************************************************************
 * Class: Scraper
 * This class is the launch class for the application.
 * It takes two parameters <global configuration.XML> file and the operation mode.
 * Currently the application works in two modes:

 *  Auto    The application runs without any user interaction
 *  Manual  The application is controlled by user interactions.
 * **********************************************************************************
 * Version 	Date		Author 			Comments
 * 0.1.0	 01/01/2017     gguo          Initial version
 * 
 */
package com.gguo.scraper;

import javax.swing.JOptionPane;
import org.apache.log4j.Logger;
import com.gguo.scraper.ui.ScraperUI;
import com.gguo.utilities.string.Tools;

/**
 *
 * @author gguo
 */
public class Scraper {

    /**
     * @param args the command line arguments
     */
    
    private static final String MODES=":UI:AUTO:";
    public static final String logSep= "*****************************************************";
    public static final String Version= "0.1.0";
    public static final String Date= "19/01/2017";
    private static final Logger logger  = Logger.getLogger(Scraper.class);
    public static ScraperProperties prop;
    
    
    public static void main(String[] args) {
        Scraper me = new Scraper();
        me.logHeader();
        if (args.length!= 2) {
            System.out.println("Unable to start Scraper "+ Version);
            System.out.println("Required Parameters: Scraper.xml mode");
            System.out.println("    Scraper.xml, you need to specify the full path to the XML file Scraper.xml");
            System.out.println("    mode, the application runs in two modes, auto or manual mode so mode must be either:");
            logger.error("Unable to start Scraper, invalid parameters");
            logger.info("Required Parameters: Scraper.xml mode");
            logger.info("    Scraper.xml, you need to specify the full path to the XML file Scraper.xml");
            logger.info("    mode, the application runs in two modes, auto or manual mode so mode must be either:");

            me.showModes();
            System.exit(1);
        }
        String theMode= args[1].toUpperCase();
        int idx= theMode.indexOf('=');
        if (idx!= -1) theMode= theMode.substring(0, idx);

        if (!MODES.contains(theMode.toUpperCase())) {
            System.out.println("The mode must bet set to one of the following:");
            logger.error("Unknown command line value for Mode: "+ args[1]);
            logger.error("The mode must be set to one of the following:");
            me.showModes();
            System.exit(1);
        }
        try {
            me.doit(args);
        } catch (Exception E) {
            logger.error(E);
            E.printStackTrace();
            System.exit(1);
        }
    }
    
    private void doit(String[] args) throws Exception {
        try {
            prop= new ScraperProperties(args[0], logSep, Version, Date);
            
        } catch (Exception E) {
            logger.error("Unable to process the file: Scraper.xml", E);
            if (args[1].equalsIgnoreCase("UI")) {
                JOptionPane.showMessageDialog(null, "Unable to process the file: Scraper.xml\n"+ E.getMessage());
                E.printStackTrace();
                System.exit(1);
            }
        }
        
        //String[] opMode= tools.StringSplit(args[1], '=');
        String[] opMode= args[1].split("=");
        switch(opMode[0].toUpperCase()) {

            case "UI":
                ScraperUI doUI= new ScraperUI();
                doUI.setTitle("Scraper ver "+ Version);
                doUI.setLocationRelativeTo(null);
                doUI.setup(prop);
                doUI.setVisible(true);
            break;
            case "AUTO":
                doBackGround(Tools.StringSplit(opMode[1], ','));
        }
    }
    
    private void logHeader() {
        logger.info("Scraper version "+ Version);
        logger.info("(c) 2017 GGUO");
        logger.info(logSep);
    }
    
    private void showModes() {
        System.out.println("    Auto=<runtype,...>:   Runs in automatic mode no user intevention and it has three run types");
        System.out.println("          USI, this will populate USIs using the eIndex Web Service");
        System.out.println("          GUSIgen this will call the GUSIgen Web Service to start the USI generation process");
        System.out.println("          GUSIget this will call the GUSIget Web Service to retrieve the USI list");
        System.out.println("    UI: The application is controlled via a UI");
        logger.info("    Auto=<runtype,...>:   Runs in automatic mode no user intevention and it has three run types");
        logger.info("          USI, this will populate USIs using the eIndex Web Service");
        logger.info("          GUSIgen this will call the GUSIgen Web Service to start the USI generation process");
        logger.info("          GUSIget this will call the GUSIget Web Service to retrieve the USI list");
        
        logger.info("    UI: The application is controlled via a UI");
    }
    
     private void doBackGround(String[] mode) throws Exception {
        String msg= "Starting Scraper in background mode with the following flags:";
        for (String A:  mode) {
            msg+= "  "+ A;
        }
        logger.info(msg);
        for (String A:  mode) {
            startProcess(A);
        }
    }
     
     
     private void startProcess(String mode) throws Exception {
        
       // Grhanite doGUSIbackground= null;
        
        switch(mode.toUpperCase()) {
            case "USI":
                //EIndexUSI doBackground= new EIndexUSI(prop);
                //doBackground.process();
            break;
            case "GUSIGEN":
//                doGUSIbackground= new Grhanite(prop);
//                doGUSIbackground.Generate();
            break;
            case "GUSIGET":
//                doGUSIbackground= new Grhanite(prop);
//                doGUSIbackground.Retrieve();
            break;
            default:
                System.out.println("You must specify the backgound mode");
        }
    }
}
