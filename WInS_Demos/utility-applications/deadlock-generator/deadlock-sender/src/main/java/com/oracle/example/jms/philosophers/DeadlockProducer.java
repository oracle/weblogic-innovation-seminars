package com.oracle.example.jms.philosophers;

import com.oracle.example.jms.Constants;
import com.oracle.example.jms.producer.WLJMSProducer;

import java.util.Date;

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
public class DeadlockProducer
    extends WLJMSProducer
{
  public static final String JMS_CF_JNDI = "com/oracle/example/jms/util/cf";
  public static final String JMS_QUEUE_JNDI = "com.oracle.example.jms.util.philosophers";

  public static void main(String[] args)
  {
    WLJMSProducer sender = null;
    try
    {
      sender = new WLJMSProducer(Constants.WL_INITIAL_CONTEXT, Constants.JMS_ENDPOINT_ADDRESS, Constants.USERNAME, Constants.PASSWORD, JMS_CF_JNDI, JMS_QUEUE_JNDI);
      sender.sendMessageBatch("Batch=[" + Constants.filenameDateFormatter.format(new Date()) + "] Hello World!!", 50, 0);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
}