package com.oracle.example.philosophers;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.lang.Thread;

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
 * User: jeffrey.a.west
 * Date: Jan 15, 2011
 * Time: 7:35:04 AM
 */
@MessageDriven(
    messageListenerInterface = MessageListener.class,
    name = "PhilosopherThinkThenEat",
    mappedName = "com/oracle/example/jms/util/deadlock",
    activationConfig = {
        @ActivationConfigProperty(
            propertyName = "connectionFactoryJndiName",
            propertyValue = "com/oracle/example/jms/util/cf"),
        @ActivationConfigProperty(
            propertyName = "destinationType",
            propertyValue = "javax.jms.Queue")
    })
public class PhilosopherThinkThenEat implements MessageListener
{
  private static final String EAT = "EAT";
  private static final String THINK = "THINK";

  @Resource(name = "jdbc.ds.weblogic_examples")
  private javax.sql.DataSource ds;

  public PhilosopherThinkThenEat()
  {
  }

  public void onMessage(Message message)
  {
    logMessage("Trying to acquite second lock on " + THINK);
    synchronized (THINK)
    {
      logMessage("First Acquired!! on " + EAT);
      try
      {
        logMessage("Sleeping " + Constants.SLEEP_TIME + "ms...");
        Thread.sleep(Constants.SLEEP_TIME);
      }
      catch (InterruptedException ignore)
      {
      }

      logMessage("Trying to acquite first Acquiring lock on " + EAT);
      synchronized (EAT)
      {
        logMessage("Second lock acquired on " + THINK);
        try
        {
          logMessage("Sleeping " + Constants.SLEEP_TIME + "ms...");
          Thread.sleep(Constants.SLEEP_TIME);
        }
        catch (InterruptedException ignore)
        {
        }
      }
    }
  }

  public static void logMessage(String pMessage)
  {
    System.out.println(Thread.currentThread().getName() + ": " + pMessage);
  }
}
