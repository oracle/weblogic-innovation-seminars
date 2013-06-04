package com.oracle.example.jms.migration;

import com.oracle.example.jms.Constants;
import com.oracle.example.jms.producer.SimpleJMSProducer;

import javax.jms.*;
import javax.naming.NamingException;

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
 */
public class MigrationProducer extends SimpleJMSProducer
{
  public static final String JMS_CF_JNDI = "com/oracle/example/jms/migration/cf";
  public static final String JMS_QUEUE_JNDI = "com/oracle/example/jms/migration/queue";

  public static void main(String[] args)
  {
    MigrationProducer sender = null;

    try
    {
      sender = new MigrationProducer(Constants.WL_INITIAL_CONTEXT, Constants.JMS_ENDPOINT_ADDRESS, Constants.USERNAME, Constants.PASSWORD, JMS_CF_JNDI, JMS_QUEUE_JNDI);

      sender.sendTextMessages("Hello World!!", 250, 0);
      sleep(10000);
    }
    catch (JMSException e)
    {
      e.printStackTrace();
    }
    catch (NamingException e)
    {
      e.printStackTrace();
    }
    finally
    {
      if (sender != null)
      {
        sender.close();
      }
    }
    try
    {
      sender = new MigrationProducer(Constants.WL_INITIAL_CONTEXT, Constants.JMS_ENDPOINT_ADDRESS, Constants.USERNAME, Constants.PASSWORD, JMS_CF_JNDI, JMS_QUEUE_JNDI);
      sender.sendTextMessages("WebLogic Rulz!!", 500, 1000);
    }
    catch (JMSException e)
    {
      e.printStackTrace();
    }
    catch (NamingException e)
    {
      e.printStackTrace();
    }
    finally
    {
      if (sender != null)
      {
        sender.close();
      }
    }
  }

  public MigrationProducer(String pInitialContext, String pURL, String pUsername, String pPassword, String pCFName, String pQueueJNDIName) throws NamingException, JMSException
  {
    super(pInitialContext, pURL, pUsername, pPassword, pCFName, pQueueJNDIName);
  }
}