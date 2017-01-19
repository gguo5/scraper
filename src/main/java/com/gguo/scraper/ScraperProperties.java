/*
 * Scraper (TM)
 * Copyright 2017 gguo.
 *
 * **********************************************************************************
 * Class: ScraperProperties
 * This class is
 * 
 * **********************************************************************************
 * Version 	Date		Author 			Comments
 * 0.1.0	 01/01/2017     gguo          Initial version
 * 
 */
package com.gguo.scraper;

import java.io.File;
import org.apache.log4j.Logger;
import com.gguo.utilities.xml.XMLParser;
import org.w3c.dom.NodeList;

/**
 *
 * @author gguo
 */
public class ScraperProperties {

    private static Logger logger = Logger.getLogger(ScraperProperties.class);
    public String Version, Date, LogSep,keystore,UserGuide, SupportMSG;
    private XMLParser Xml;
    private String pwdKey;
    public File GUSIlock;

    ScraperProperties(String XMLpath, String logSep, String version, String date) throws Exception {
        Version = version;
        Date = date;
        LogSep = logSep;
        loadXML(XMLpath);
    }

    private void loadXML(String path) throws Exception {
        try {
            Xml = new XMLParser(new File(path));
        } catch (Exception A) {
            logger.error("Unable to open XML file : " + A);
            throw A;
        }
        loadEnv();

    }

    private void loadEnv() throws Exception {
        NodeList NL = Xml.getNodeList("ME:ENVIRONMENT");
        if (NL.getLength() < 1) {
            logFatalError("XML file is missing the Environment list");
        }
        for (int I = 0; I < NL.getLength(); I++) {
            try {
                String Value = NL.item(I).getAttributes().item(0).getNodeValue();
                switch (NL.item(I).getAttributes().item(0).getNodeName().toUpperCase()) {
                    case "PWDKEY":
                        pwdKey = Value;
                        break;
                    case "PLUGINS":
                        //loadPlugins(new File(Value));
                        break;
                    case "KEYSTORE":
                        keystore = Value;
                        break;
                    case "DRIVERS":
                        //loadDrivers(new File(Value));
                        break;
                    case "USERGUIDE":
                        UserGuide = Value;
                        break;
                    case "GUSILOCKFILE":
                        try {
                            GUSIlock = new File(Value);
                        } catch (Exception E) {
                            GUSIlock = null;
                        }
                        break;
                    case "SUPPORTMSG":
                        SupportMSG = Value;
                        break;
                }
            } catch (Exception E) {
            }
        }

        if (keystore == null || keystore.length() == 0) {
            logFatalError("The keystore has not been specified, you will not be able to connect to BioGrid.  Please check the ENVIRONMENT section in the USIRequestor.xml file");
        }
    }
    
    
    private Exception logFatalError(String error) throws Exception {
        logger.error(error);
        throw new Exception(error);
    }
}
