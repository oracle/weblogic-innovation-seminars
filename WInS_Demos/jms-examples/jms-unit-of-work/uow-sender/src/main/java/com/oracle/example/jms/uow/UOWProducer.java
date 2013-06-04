package com.oracle.example.jms.uow;

import com.oracle.example.jms.Constants;
import com.oracle.example.jms.producer.WLJMSProducer;

import javax.jms.*;
import javax.naming.NamingException;

/*****************************************************************************
 *
 * This code is provided for example purposes only.  Oracle does not assume
 * any responsibility or liability for the consequences of using this code.
 * If you choose to use this code for any reason, including but not limited
 * to its use as an example you do so at your own risk and without the support
 * of Oracle.
 *
 *****************************************************************************
 */
public class UOWProducer extends WLJMSProducer
{

  public static final String JMS_CF_JNDI = "com/oracle/example/jms/uow/cf";
  public static final String JMS_QUEUE_JNDI = "com/oracle/example/jms/uow/queue";

  public static void main(String[] args)
  {
    try
    {
      if (args.length == 0 || args[0].toUpperCase().equals("NORMAL"))
      {
        UOWProducer sender = new UOWProducer(Constants.WL_INITIAL_CONTEXT, Constants.JMS_ENDPOINT_ADDRESS, Constants.USERNAME, Constants.PASSWORD, JMS_CF_JNDI, JMS_QUEUE_JNDI);
        sender.sendNonUnitOfWorkMessages(10, 1000, 0, 0);
      }

      if (args.length == 0 || args[0].toUpperCase().equals("DISCRETE_FAST"))
      {
        UOWProducer sender = new UOWProducer(Constants.WL_INITIAL_CONTEXT, Constants.JMS_ENDPOINT_ADDRESS, Constants.USERNAME, Constants.PASSWORD, JMS_CF_JNDI, JMS_QUEUE_JNDI);
        sender.sendUnitOfWork(7, 750, 0, 0);
      }

      if (args.length == 0 || args[0].toUpperCase().equals("DISCRETE_SLOW"))
      {
        UOWProducer sender = new UOWProducer(Constants.WL_INITIAL_CONTEXT, Constants.JMS_ENDPOINT_ADDRESS, Constants.USERNAME, Constants.PASSWORD, JMS_CF_JNDI, JMS_QUEUE_JNDI);
        sender.sendUnitOfWork(15, 1500, 5000, 0);
      }

      if (args.length == 0 || args[0].toUpperCase().equals("DISCRETE_WAIT"))
      {
        UOWProducer sender = new UOWProducer(Constants.WL_INITIAL_CONTEXT, Constants.JMS_ENDPOINT_ADDRESS, Constants.USERNAME, Constants.PASSWORD, JMS_CF_JNDI, JMS_QUEUE_JNDI);
        sender.sendUnitOfWork(25, 1500, 30000, 0);
      }

      if (args.length == 0 || args[0].toUpperCase().equals("INCOMPLETE"))
      {
        UOWProducer sender = new UOWProducer(Constants.WL_INITIAL_CONTEXT, Constants.JMS_ENDPOINT_ADDRESS, Constants.USERNAME, Constants.PASSWORD, JMS_CF_JNDI, JMS_QUEUE_JNDI);
        sender.sendIncompleteUnitOfWork(7, 1000, 0, 0);
      }

      if (args.length == 0 || args[0].toUpperCase().equals("INCOMPLETE_TX"))
      {
        UOWProducer sender = new UOWProducer(Constants.WL_INITIAL_CONTEXT, Constants.JMS_ENDPOINT_ADDRESS, Constants.USERNAME, Constants.PASSWORD, JMS_CF_JNDI, JMS_QUEUE_JNDI);
        sender.sendIncompleteUnitOfWork(7, 0, 0, 5000);
      }

    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  public UOWProducer(String pInitialContext, String pURL, String pUsername, String pPassword, String pCFName, String pQueueJNDIName) throws NamingException, JMSException
  {
    super(pInitialContext, pURL, pUsername, pPassword, pCFName, pQueueJNDIName);
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

      final Message sendMsg = mSession.createTextMessage(text);
      sendMsg.setStringProperty("JMS_BEA_UnitOfWork", unitOfWorkId);
      sendMsg.setIntProperty("JMS_BEA_UnitOfWorkSequenceNumber", i);

      if (i == pMessageCount)
      {
        System.out.println("Setting the UnitOfWorkEnd flag on last message num=[" + i + "]");
        sendMsg.setBooleanProperty("JMS_BEA_IsUnitOfWorkEnd", true);
        sleep(pLastMessageWaitTime);
      }

      System.out.println("Sending text: " + text);
      mProducer.send(sendMsg);
      sleep(pIntervalTimeInMillis);
    }

    if (transacted)
    {
      sleep(pCommitWaitTime);
      commitSession();
    }

    endSession();
  }

  private void sendIncompleteUnitOfWork(int pMessageCount, long pIntervalTimeInMillis, long pLastMessageWaitTime, long pCommitWaitTime) throws JMSException
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

      final Message sendMsg = mSession.createTextMessage(text);
      sendMsg.setStringProperty("JMS_BEA_UnitOfWork", unitOfWorkId);
      sendMsg.setIntProperty("JMS_BEA_UnitOfWorkSequenceNumber", i);

      if (i == pMessageCount)
      {
        System.out.println("** NOT setting the UnitOfWorkEnd flag on last message num=[" + i + "]");
        //sendMsg.setBooleanProperty("JMS_BEA_IsUnitOfWorkEnd", true);
        sleep(pLastMessageWaitTime);
      }

      System.out.println("Sending text: " + text);
      mProducer.send(sendMsg);
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

      final Message sendMsg = mSession.createTextMessage(text);

      if (i == pMessageCount)
      {
        sleep(pLastMessageWaitTime);
      }

      System.out.println("Sending text: " + text);
      mProducer.send(sendMsg);
      sleep(pIntervalTimeInMillis);
    }

    if (transacted)
    {
      sleep(pCommitWaitTime);
      commitSession();
    }

    endSession();
  }

}