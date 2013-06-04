package com.oracle.example.jms.uow;

import com.oracle.example.jms.Constants;
import com.oracle.example.util.CRC32Util;
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
import javax.jms.*;
import java.util.zip.CRC32;

/**
 * **************************************************************************
 * <p/>
 * This code is provided for example purposes only.  Oracle does not assume
 * any responsibility or liability for the consequences of using this code.
 * If you choose to use this code for any reason, including but not limited
 * to its use as an example you do so at your own risk and without the support
 * of Oracle.
 * <p/>
 * ****************************************************************************
 */
@Stateless(name = "UOWProducerEJB", mappedName = "ejb/UOWProducer")
@LocalBean
public class UOWProducerEJB
{
  public static final String JMS_CF_JNDI = "com/oracle/example/jms/uow/cf";
  public static final String JMS_QUEUE_JNDI = "com/oracle/example/jms/uow/queue";


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
          f.printStackTrace();
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

  public void doIt()
  {
    try
    {
      sendUnitOfWork(7, 750, 0, 0);
      sendUnitOfWork(15, 1500, 5000, 0);
      sendUnitOfWork(25, 1500, 30000, 0);
      sendIncompleteUnitOfWork(7, 1000, 0, 0);
      sendIncompleteUnitOfWork(7, 0, 0, 5000);

    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  private void sendUnitOfWork(int pMessageCount, long pIntervalTimeInMillis, long pLastMessageWaitTime, long pCommitWaitTime) throws JMSException
  {
    boolean transacted = false;

    if (pCommitWaitTime > 0)
    {
      transacted = true;
    }

    beginSession(transacted);

    String unitOfWorkId = java.util.UUID.randomUUID().toString();

    for (int i = 1; i <= pMessageCount; i++)
    {
      String text = "UOW message=[" + i + " of " + pMessageCount + "]";

      final Message sendMsg = session.createTextMessage(text);
      sendMsg.setStringProperty("JMS_BEA_UnitOfWork", unitOfWorkId);
      sendMsg.setIntProperty("JMS_BEA_UnitOfWorkSequenceNumber", i);

      if (i == pMessageCount)
      {
        System.out.println("Setting the UnitOfWorkEnd flag on last message num=[" + i + "]");
        sendMsg.setBooleanProperty("JMS_BEA_IsUnitOfWorkEnd", true);
        sleep(pLastMessageWaitTime);
      }

      System.out.println("Sending text: " + text);
      queueProducer.send(sendMsg);
      sleep(pIntervalTimeInMillis);
    }

    if (transacted)
    {
      sleep(pCommitWaitTime);
      commitSession();
    }

    endSession();
  }

  private void sendMixedUnitOfWork(int pGroupCount, int pMessageCount, long pIntervalTimeInMillis, long pLastMessageWaitTime, long pCommitWaitTime) throws JMSException
  {
    boolean transacted = false;

    if (pCommitWaitTime > 0)
    {
      transacted = true;
    }

    beginSession(transacted);

    String unitOfWorkIds[] = new String[pGroupCount];

    for (int i = 0; i < unitOfWorkIds.length; i++)
    {
      unitOfWorkIds[i] = (CRC32Util.getCRC32Checksum(java.util.UUID.randomUUID().toString()));
    }

    for (int i = 1; i <= pMessageCount; i++)
    {
      for (int n = 1; n < pGroupCount; n++)
      {

        String text = "UOW=[" + unitOfWorkIds[n] + "] message=[" + i + " of " + pMessageCount + "]";

        final Message sendMsg = session.createTextMessage(text);

        sendMsg.setStringProperty("JMS_BEA_UnitOfWork", unitOfWorkIds[n]);
        sendMsg.setIntProperty("JMS_BEA_UnitOfWorkSequenceNumber", i);

        if (i == pMessageCount)
        {
          System.out.println("Setting the UnitOfWorkEnd flag on last message UOW=[" + unitOfWorkIds[n] + "] message=[" + i + "]");
          sendMsg.setBooleanProperty("JMS_BEA_IsUnitOfWorkEnd", true);
          sleep(pLastMessageWaitTime);
        }

        System.out.println("Sending text: " + text);
        queueProducer.send(sendMsg);
        sleep(pIntervalTimeInMillis);
      }
    }

    if (transacted)
    {
      sleep(pCommitWaitTime);
      commitSession();
    }

    endSession();
  }

  private void sendIncompleteUnitOfWork(int pMessageCount,
                                        long pIntervalTimeInMillis,
                                        long pLastMessageWaitTime,
                                        long pCommitWaitTime) throws JMSException
  {
    boolean transacted = false;

    if (pCommitWaitTime > 0)
    {
      transacted = true;
    }

    beginSession(transacted);


    String unitOfWorkId = java.util.UUID.randomUUID().toString();

    for (int i = 1; i <= pMessageCount; i++)
    {
      String text = "UOW message=[" + i + " of " + pMessageCount + "]";

      final Message sendMsg = session.createTextMessage(text);
      sendMsg.setStringProperty("JMS_BEA_UnitOfWork", unitOfWorkId);
      sendMsg.setIntProperty("JMS_BEA_UnitOfWorkSequenceNumber", i);

      if (i == pMessageCount)
      {
        System.out.println("** NOT setting the UnitOfWorkEnd flag on last message num=[" + i + "]");
        //sendMsg.setBooleanProperty("JMS_BEA_IsUnitOfWorkEnd", true);
        sleep(pLastMessageWaitTime);
      }

      System.out.println("Sending text: " + text);
      queueProducer.send(sendMsg);
      sleep(pIntervalTimeInMillis);
    }

    if (transacted)
    {
      sleep(pCommitWaitTime);
      commitSession();
    }

    endSession();
  }

  private void sendNonUnitOfWorkMessages(int pMessageCount, long pIntervalTimeInMillis, long pLastMessageWaitTime, long pCommitWaitTime) throws JMSException
  {
    boolean transacted = false;

    if (pCommitWaitTime > 0)
    {
      transacted = true;
    }

    beginSession(transacted);

    for (int i = 1; i <= pMessageCount; i++)
    {
      String text = "Regular message=[" + i + " of " + pMessageCount + "]";

      final Message sendMsg = session.createTextMessage(text);

      if (i == pMessageCount)
      {
        sleep(pLastMessageWaitTime);
      }

      System.out.println("Sending text: " + text);
      queueProducer.send(sendMsg);
      sleep(pIntervalTimeInMillis);
    }

    if (transacted)
    {
      sleep(pCommitWaitTime);
      commitSession();
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

  @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
  public void sendNonUOW()
  {
    try
    {
      sendNonUnitOfWorkMessages(10, 1000, 0, 0);
    }
    catch (JMSException e)
    {
      e.printStackTrace();
    }
  }

  @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
  public void sendDiscreteUOW()
  {
    try
    {
      sendUnitOfWork(15, 1500, 5000, 0);
    }
    catch (JMSException e)
    {
      e.printStackTrace();
    }
  }

  @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
  public void sendMixedUOW()
  {
    try
    {
      sendMixedUnitOfWork(3, 5, 1500, 1000, 0);
    }
    catch (JMSException e)
    {
      e.printStackTrace();
    }
  }

  @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
  public void sendIncompleteUOW()
  {
    try
    {
      sendIncompleteUnitOfWork(7, 1000, 0, 0);
    }
    catch (JMSException e)
    {
      e.printStackTrace();
    }
  }

  @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
  public void sendSlowUOW()
  {
    try
    {
      sendUnitOfWork(25, 1500, 30000, 0);
    }
    catch (JMSException e)
    {
      e.printStackTrace();
    }
  }
}