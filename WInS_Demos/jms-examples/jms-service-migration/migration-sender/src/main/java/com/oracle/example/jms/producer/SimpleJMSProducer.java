package com.oracle.example.jms.producer;

import com.oracle.example.jms.Constants;

import javax.jms.*;
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
 * <p/>
 * This code is provided under the following licenses:
 * <p/>
 * GNU General Public License (GPL-2.0)
 * COMMON DEVELOPMENT AND DISTRIBUTION LICENSE Version 1.0 (CDDL-1.0)
 * <p/>
 * <p/>
 * ****************************************************************************
 */
public class SimpleJMSProducer
{
  public static final String JMS_CF_JNDI = "com/oracle/example/jms/base/cf";
  public static final String JMS_QUEUE_JNDI = "com/oracle/example/jms/base/queue";

  public static final int QUEUE_DESTINATION = 1;
  public static final int TOPIC_DESTINATION = 2;

  protected int destinationType;
  protected Session session;
  protected MessageProducer messageProducer;

  public static void main(String[] args)
  {
    SimpleJMSProducer producer = null;

    try
    {
      producer = new SimpleJMSProducer(Constants.WL_INITIAL_CONTEXT, Constants.JMS_ENDPOINT_ADDRESS, Constants.USERNAME, Constants.PASSWORD, JMS_CF_JNDI, JMS_QUEUE_JNDI);
      long time = System.currentTimeMillis();

      for (int x = 1; x <= 10; x++)
      {
        String text = "Hello World! Message=[" + x + "] Batch=[" + time + "]";
        producer.sendTextMessage(text);
        System.out.println("Sent Message:: " + text);
      }
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
      if (producer != null)
      {
        producer.close();
      }
    }
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


  protected void close()
  {
    try
    {
      messageProducer.close();
      session.close();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  protected void sendTextMessages(final String pMessage, final int pMessageCount, final long pIntervalTimeInMillis) throws JMSException
  {
    boolean even = false;
    for (int x = 1; x <= pMessageCount; x++)
    {
      even = (x % 2) == 1;
      sendTextMessage(pMessage + "(" + x + ") " + (even ? "EVEN" : "ODD"));
      sleep(pIntervalTimeInMillis);
    }
  }

  protected void sendTextMessage(final String pMessage) throws JMSException
  {
    System.out.println("Sending TextMessage string=[" + pMessage + "]...");
    final Message jmsMsg = session.createTextMessage(pMessage);
    messageProducer.send(jmsMsg, DeliveryMode.PERSISTENT, 7, 0);
  }


  public SimpleJMSProducer(String pInitialContext, String pURL, String pUsername, String pPassword, String pConnectionFactoryJNDI, String pDestinationJNDI) throws NamingException, JMSException
  {
    final InitialContext context;

    Hashtable<String, String> h = new Hashtable<String, String>(7);
    h.put(Context.INITIAL_CONTEXT_FACTORY, pInitialContext);

    if (pURL != null)
    {
      h.put(Context.PROVIDER_URL, pURL);
    }

    if (pUsername != null)
    {
      h.put(Context.SECURITY_PRINCIPAL, pUsername);
    }

    if (pPassword != null)
    {
      h.put(Context.SECURITY_CREDENTIALS, pPassword);
    }

    System.out.println("Getting context...");
    context = new InitialContext(h);

    final ConnectionFactory jmsConnectionFactory;
    final Destination jmsDestination;

    System.out.println("Looking up CF reference...");
    jmsConnectionFactory = (ConnectionFactory) context.lookup(pConnectionFactoryJNDI);

    System.out.println("Looking up Queue...");
    jmsDestination = (Destination) context.lookup(pDestinationJNDI);

    if (jmsDestination instanceof Queue)
    {
      destinationType = QUEUE_DESTINATION;
    }
    else
    {
      destinationType = TOPIC_DESTINATION;
    }

    System.out.println("Creating Connection...");
    final Connection conn = jmsConnectionFactory.createConnection();

    System.out.println("Creating Session...");
    session = conn.createSession(false, 0);

    System.out.println("Creating message producer...");
    messageProducer = session.createProducer(jmsDestination);
  }
}