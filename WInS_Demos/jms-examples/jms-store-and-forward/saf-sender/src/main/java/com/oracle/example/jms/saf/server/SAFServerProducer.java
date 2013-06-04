package com.oracle.example.jms.saf.server;

import com.oracle.example.jms.Constants;
import com.oracle.example.jms.producer.WLJMSProducer;

import javax.jms.JMSException;
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
public class SAFServerProducer extends WLJMSProducer
{
  public static final String JMS_CF_JNDI = "com/oracle/example/jms/saf/cf";
  public static final String JMS_QUEUE_JNDI = "com/oracle/example/jms/saf/local-queue";

  public static void main(String[] args)
  {
    SAFServerProducer sender = null;

    int messageCount = 10;
    int intervalDelay = 1000;

    try
    {
      messageCount = Integer.parseInt(args[0]);
      intervalDelay = Integer.parseInt(args[1]);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }

    try
    {
      sender = new SAFServerProducer(Constants.WL_INITIAL_CONTEXT, Constants.JMS_ENDPOINT_ADDRESS, Constants.USERNAME, Constants.PASSWORD, JMS_CF_JNDI, JMS_QUEUE_JNDI);

      String batchId = java.util.UUID.randomUUID().toString();

      sender.sendMessageBatch("WLS FTW! batch=[" + batchId + "]", messageCount, intervalDelay);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    finally
    {
      if (sender != null)
      {
        try
        {
          sender.close();
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
      }
    }
  }


  public SAFServerProducer(String pInitialContext, String pURL, String pUsername, String pPassword, String pCFName, String pQueueJNDIName) throws NamingException, JMSException
  {
    super(pInitialContext, pURL, pUsername, pPassword, pCFName, pQueueJNDIName);
  }
}