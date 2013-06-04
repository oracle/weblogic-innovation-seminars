package com.oracle.example.jms.saf;

import weblogic.jms.extensions.WLConnection;
import weblogic.jms.extensions.WLDestination;
import weblogic.jms.extensions.WLMessageProducer;
import weblogic.jms.extensions.WLQueueSession;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
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

@MessageDriven(
    messageListenerInterface = MessageListener.class,
    name = "SAFSourceForwarderMDB",
    mappedName = "com/oracle/example/jms/saf/local-queue",
    activationConfig = {
        @ActivationConfigProperty(
            propertyName = "connectionFactoryJndiName",
            propertyValue = "com/oracle/example/jms/saf/cf"),
        @ActivationConfigProperty(
            propertyName = "destinationType",
            propertyValue = "javax.jms.Queue")
    })
public class SAFForwarderBean implements MessageListener
{
  private final String mdbId = java.util.UUID.randomUUID().toString();

  public SAFForwarderBean()
  {
  }

  public void onMessage(Message message)
  {
    if (message instanceof TextMessage)
    {
      TextMessage msg = (TextMessage) message;

      try
      {
        System.out.println("SAFForwarder:: MDB=[" + mdbId + "] " + msg.getText());
        //msg.setText("[FW]" + msg.getText());
      }
      catch (JMSException e)
      {
        e.printStackTrace();
      }
    }

    try
    {
      forward(message, "com/oracle/example/jms/saf/cf", "saf/com/oracle/example/jms/saf/local-queue");
      Thread.sleep(1500);
    }
    catch (NamingException e)
    {
      e.printStackTrace();
    }
    catch (JMSException e)
    {
      e.printStackTrace();
    }
    catch (InterruptedException e)
    {
      e.printStackTrace();
    }
  }

  private void forward(Message message, String pCFName, String pQueueJNDIName) throws NamingException, JMSException
  {
    System.out.println("Forwarding message to queue=[" + pQueueJNDIName + "]");
    final InitialContext context;
    Hashtable<String, String> h = new Hashtable<String, String>(7);
    h.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");

    //System.out.println("Getting context...");
    context = new InitialContext(h);

    //System.out.println("Looking up CF jndi=[" + pCFName + "]...");
    QueueConnectionFactory connectionFactory = (QueueConnectionFactory) context.lookup(pCFName);

    //System.out.println("Looking up Queue jndi=[" + pQueueJNDIName + "]");
    WLDestination mQueue = (WLDestination) context.lookup(pQueueJNDIName);

    //System.out.println("Creating connection...");
    WLConnection mConnection = (WLConnection) connectionFactory.createQueueConnection();

    WLQueueSession mSession = (WLQueueSession) mConnection.createQueueSession(false, 0);

    //System.out.println("Creating producer...");
    WLMessageProducer mProducer = (WLMessageProducer) mSession.createProducer(mQueue);

    //System.out.println("forwarding message...");
    mProducer.send(message);
  }
}
