package com.oracle.example.jms.saf;

import com.oracle.example.jms.Constants;
import weblogic.jms.extensions.WLConnection;
import weblogic.jms.extensions.WLMessageProducer;
import weblogic.jms.extensions.WLSession;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.Session;

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
@Stateless(name = "SAFServerProducerEJB", mappedName = "ejb/SAFServerProducer")
@LocalBean
public class SAFServerProducerEJB
{
  public static final String JMS_CF_JNDI = "com/oracle/example/jms/saf/cf";
  public static final String JMS_QUEUE_JNDI = "com/oracle/example/jms/saf/local-queue";

  @Resource(name = JMS_CF_JNDI, type = ConnectionFactory.class)
  private ConnectionFactory connectionFactory;

  @Resource(name = JMS_QUEUE_JNDI, type = Queue.class)
  private Queue queue;

  private WLConnection connection;
  private WLSession session;
  private WLMessageProducer queueProducer;

  @PostConstruct
  public void initialize()
  {
    try
    {
      connection = (WLConnection) connectionFactory.createConnection();
      session = (WLSession) connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
      queueProducer = (WLMessageProducer) session.createProducer(queue);
    }
    catch (JMSException e)
    {
      if (connection != null)
      {
        try
        {
          connection.close();
        }
        catch (JMSException f)
        {
          e.printStackTrace();
        }
      }
    }
  }

  @PreDestroy
  public void closeConnection()
  {
    try
    {
      if (connection != null)
      {
        connection.close();
      }
    }
    catch (JMSException e)
    {
      e.printStackTrace();
    }
  }

  @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
  public void doIt()
  {
    int messageCount = 10;
    int intervalDelay = 1000;

    try
    {
      String batchId = java.util.UUID.randomUUID().toString();

      sendMessageBatch("WLS FTW! batch=[" + batchId + "]", messageCount, intervalDelay);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  public void sendMessageBatch(String pMessageBase, int pMessageCount, long pIntervalTimeInMillis) throws JMSException
  {
    beginSession(false);

    boolean even = false;

    for (int x = 1; x <= pMessageCount; x++)
    {
      even = (x % 2) != 1;
      String text = pMessageBase + "(" + x + ") " + (even ? "EVEN" : "ODD");
      queueProducer.send(session.createTextMessage(text));
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

  protected void beginSession(boolean pTransacted) throws JMSException
  {
    session = (WLSession) connection.createSession(pTransacted, 0);
    queueProducer = (WLMessageProducer) session.createProducer(queue);
  }

  protected void endSession() throws JMSException
  {
    queueProducer.close();
    session.close();
  }

  protected void commitSession() throws JMSException
  {
    session.commit();
  }

}