package com.oracle.example.jms.saf.client;

import com.oracle.example.jms.producer.WLJMSProducer;
import weblogic.jms.extensions.WLDestination;

import javax.jms.JMSException;
import javax.jms.QueueConnectionFactory;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Hashtable;

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
 * Time: 12:19:10 PM
 */
public class SAFClientProducer extends WLJMSProducer
{
  public static final String SAF_INITIAL_CONTEXT = "weblogic.jms.safclient.jndi.InitialContextFactoryImpl";
  public static final String SAF_CLIENT_CONFIG_FILE = "file:///C:/Oracle/Middleware/user_projects/jms-package/examples/saf-client/ClientSAF-jms.xml";
  public static final String PASSWORD_KEY = "password-key";
  public static final String JMS_CF_JNDI = "com/oracle/example/jms/saf-client/cf";
  public static final String JMS_QUEUE_JNDI = "com/oracle/example/jms/saf-client/queue";

  public static void main(String[] args)
  {
    SAFClientProducer producer = null;

    try
    {
      producer = new SAFClientProducer(SAF_INITIAL_CONTEXT, SAF_CLIENT_CONFIG_FILE, PASSWORD_KEY, JMS_CF_JNDI, JMS_QUEUE_JNDI);
      producer.sendMessageBatch("Hello World!!", 10, 100);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    finally
    {
      try
      {
        if (producer != null)
        {
          producer.close();
        }
      }
      catch (JMSException e)
      {
        e.printStackTrace();
      }
    }
  }

  public SAFClientProducer(final String pInitialContext, final String pSAFConfigFileURL, final String pPasswordKey, final String pCFName, final String pQueueJNDIName) throws NamingException, JMSException
  {
    final InitialContext context;
    Hashtable<String, String> h = new Hashtable<String, String>(7);
    h.put(Context.INITIAL_CONTEXT_FACTORY, pInitialContext);
    h.put(Context.PROVIDER_URL, pSAFConfigFileURL);
    h.put(Context.SECURITY_CREDENTIALS, pPasswordKey);

    System.out.println("Getting context...");
    context = new InitialContext(h);

    System.out.println("Looking up CF jndi=[" + pCFName + "]...");
    QueueConnectionFactory connectionFactory = (QueueConnectionFactory) context.lookup(pCFName);

    System.out.println("Looking up Queue jndi=[" + pQueueJNDIName + "]");
    mDestination = (WLDestination) context.lookup(pQueueJNDIName);

    System.out.println("Creating connection...");
    mConnection = connectionFactory.createConnection();
  }
}
