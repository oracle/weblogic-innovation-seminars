package com.oracle.weblogic.examples.ejb;

import weblogic.jms.extensions.WLDestination;
import weblogic.jms.extensions.WLMessageProducer;
import weblogic.jms.extensions.WLSession;

import javax.annotation.PreDestroy;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
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
 */
public class JMSProducer
{
  protected WLSession mSession;
  protected WLMessageProducer mProducer;
  protected Connection mConnection;
  protected WLDestination mDestination;

  public static final String JMS_CF_JNDI = "com/oracle/example/jms/base/cf";
  public static final String JMS_QUEUE_JNDI = "com/oracle/example/jms/base/queue";

  public static void main(String[] args)
  {
    JMSProducer jmsProducer = null;

    try
    {
      jmsProducer = new JMSProducer(
          Constants.WL_INITIAL_CONTEXT,
          Constants.JMS_ENDPOINT_ADDRESS,
          Constants.USERNAME,
          Constants.PASSWORD,
          JMS_CF_JNDI,
          JMS_QUEUE_JNDI);

      jmsProducer.beginSession(false);
      jmsProducer.sendMessageBatch("Hello World!", 1000, 0);
      jmsProducer.endSession();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    finally
    {
      if (jmsProducer != null)
      {
        try
        {
          jmsProducer.close();
        }
        catch (JMSException e)
        {
          e.printStackTrace();
        }
      }
    }
  }

  public void sendMessageBatch(String pMessageBase, int pMessageCount, long pIntervalTimeInMillis) throws JMSException
  {
    beginSession(false);

    for (int x = 1; x <= pMessageCount; x++)
    {
      String text = pMessageBase + "-" + x;
      sendMessage(text);
      sleep(pIntervalTimeInMillis);
    }

    endSession();
  }

  protected static void sleep(long time)
  {
    try
    {
      Thread.sleep(time);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  protected void sendMessage(String text) throws JMSException
  {
    if (mSession == null)
    {
      beginSession(false);
    }

    final Message sendMsg = mSession.createTextMessage(text);
    System.out.println("Sending TextMessage=[" + text + "]");
    mProducer.send(sendMsg);
  }

  protected void beginSession(boolean pTransacted) throws JMSException
  {
    System.out.println("Creating session...");
    mSession = (WLSession) mConnection.createSession(pTransacted, 0);

    System.out.println("Creating producer...");
    mProducer = (WLMessageProducer) mSession.createProducer(mDestination);
  }

  protected void endSession() throws JMSException
  {
    System.out.println("Closing Producer...");
    mProducer.close();

    System.out.println("Closing Session...");
    mSession.close();
  }

  protected void commitSession() throws JMSException
  {
    System.out.println("Committing session...");
    mSession.commit();
  }

  @PreDestroy
  protected void close() throws JMSException
  {
    System.out.println("Closing WL JMS Producer...");

    System.out.println("Closing Connection...");
    mConnection.close();
  }

  protected JMSProducer()
  {
  }

  public JMSProducer(String pInitialContext, String pURL, String pUsername, String pPassword, String pCFName, String pQueueJNDIName) throws NamingException, JMSException
  {
    final InitialContext context;
    Hashtable<String, String> h = new Hashtable<String, String>(7);
    h.put(Context.INITIAL_CONTEXT_FACTORY, pInitialContext);
    h.put(Context.PROVIDER_URL, pURL);
    h.put(Context.SECURITY_PRINCIPAL, pUsername);
    h.put(Context.SECURITY_CREDENTIALS, pPassword);

    System.out.println("Getting context with URL=[" + pURL + "]...");
    context = new InitialContext(h);

    System.out.println("Looking up CF jndi=[" + pCFName + "]...");
    QueueConnectionFactory connectionFactory = (QueueConnectionFactory) context.lookup(pCFName);

    System.out.println("Looking up Destination jndi=[" + pQueueJNDIName + "]");
    mDestination = (WLDestination) context.lookup(pQueueJNDIName);

    System.out.println("Creating connection...");
    mConnection = connectionFactory.createQueueConnection();
  }
}