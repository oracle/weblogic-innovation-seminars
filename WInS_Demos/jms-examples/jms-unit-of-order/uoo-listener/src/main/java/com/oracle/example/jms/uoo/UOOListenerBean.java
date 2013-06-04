package com.oracle.example.jms.uoo;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.*;

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
 * User: jeffrey.a.west
 * Date: Jan 15, 2011
 * Time: 7:35:04 AM
 */
@MessageDriven(
    messageListenerInterface = MessageListener.class,
    name = "UOOListenerEJB",
    mappedName = "com/oracle/example/jms/uoo/queue",
    activationConfig = {
        @ActivationConfigProperty(
            propertyName = "connectionFactoryJndiName",
            propertyValue = "com/oracle/example/jms/uoo/cf"),
        @ActivationConfigProperty(
            propertyName = "destinationType",
            propertyValue = "javax.jms.Queue")
    })
public class UOOListenerBean implements MessageListener
{
  private final String mdbId = java.util.UUID.randomUUID().toString();

  public UOOListenerBean()
  {
  }

  public void onMessage(Message message)
  {
    if (message instanceof TextMessage)
    {
      TextMessage msg = (TextMessage) message;

      try
      {
        String unitOfOrder = message.getStringProperty("JMS_BEA_UnitOfOrder");
        // Sleep for 2 seconds to demonstrate the messages are indeed
        // processes sequentially (in unit-of-order)
        Thread.sleep(2000);
        System.out.println("UOOListener:: MDB=[" + mdbId + "] UOO=[" + unitOfOrder + "] Message=[" + msg.getText() + "]");
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
  }
}
