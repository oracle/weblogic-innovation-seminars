package com.oracle.weblogic.examples.ejb;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Properties;

/**
  * **************************************************************************
 * <p/>
 * This code is provided for example purposes only.  Oracle does not assume
 * any responsibility or liability for the consequences of using this code.
 * If you choose to use this code for any reason, including but not limited
 * to its use as an example you do so at your own risk and without the support
 * of Oracle.
 *
 * This code is provided under the following licenses:
 *
 * GNU General Public License (GPL-2.0)
 * COMMON DEVELOPMENT AND DISTRIBUTION LICENSE Version 1.0 (CDDL-1.0)
 *
 * <p/>
 * ****************************************************************************
 * User: jeffrey.a.west
 * Date: Jan 17, 2011
 * Time: 1:48:25 PM
 */
public class Constants
{
  public static final String JMS_ENDPOINT_ADDRESS;
  public static final String WL_INITIAL_CONTEXT;
  public static final String USERNAME;
  public static final String PASSWORD;
  public static final SimpleDateFormat filenameDateFormatter = new SimpleDateFormat("yyyyMMdd_HHmmss");

  static
  {
    Properties props = new Properties();
    File propertiesFile = new File("jms-sender.properties");
    if (propertiesFile.exists())
    {
      System.out.println("Configuring with properties file=[" + propertiesFile.getAbsolutePath() + "]");
      try
      {
        FileInputStream propFileStream = new FileInputStream(propertiesFile);
        props.load(propFileStream);
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
      JMS_ENDPOINT_ADDRESS = props.getProperty("JMS_ENDPOINT_ADDRESS");
      WL_INITIAL_CONTEXT = props.getProperty("WL_INITIAL_CONTEXT");
      USERNAME = props.getProperty("USERNAME");
      PASSWORD = props.getProperty("PASSWORD");
    }
    else
    {
      System.out.println("Properties file=[" + propertiesFile.getAbsolutePath() + "] does not exist.  Using defaults!");

      JMS_ENDPOINT_ADDRESS = "t3://127.0.0.1:7101,127.0.0.1:7102";
      WL_INITIAL_CONTEXT = "weblogic.jndi.WLInitialContextFactory";
      USERNAME = "weblogic";
      PASSWORD = "welcome1";
    }
  }
}
