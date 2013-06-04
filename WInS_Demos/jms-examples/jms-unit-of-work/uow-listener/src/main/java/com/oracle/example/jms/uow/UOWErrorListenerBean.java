package com.oracle.example.jms.uow;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.*;
import java.util.ArrayList;

/*****************************************************************************
 *
 * This code is provided for example purposes only.  Oracle does not assume
 * any responsibility or liability for the consequences of using this code.
 * If you choose to use this code for any reason, including but not limited
 * to its use as an example you do so at your own risk and without the support
 * of Oracle.
 *
 *****************************************************************************
 * User: jeffrey.a.west
 * Date: Jan 17, 2011
 * Time: 2:28:08 PM
 */
@MessageDriven(
    messageListenerInterface = MessageListener.class,
    name = "UOWErrorListenerEJB",
    mappedName = "com/oracle/example/jms/uow/error-queue",
    activationConfig = {
        @ActivationConfigProperty(
            propertyName = "connectionFactoryJndiName",
            propertyValue = "com/oracle/example/jms/uow/cf"),
        @ActivationConfigProperty(
            propertyName = "destinationType",
            propertyValue = "javax.jms.Queue")
    })
public class UOWErrorListenerBean implements MessageListener
{
  public UOWErrorListenerBean()
  {
  }

  public void onMessage(Message message)
  {
    String unitOfWorkId = null;

    try
    {
      unitOfWorkId = message.getStringProperty("JMS_BEA_UnitOfWork");
    }
    catch (JMSException e)
    {
      e.printStackTrace();
    }

    if (message instanceof TextMessage)
    {
      TextMessage msg = (TextMessage) message;

      try
      {
        System.out.println("UOW=[" + unitOfWorkId + "] Message Delivery Failed! Message=[" + msg.getText() + "]");
      }
      catch (JMSException e)
      {
        e.printStackTrace();
      }
    }

    if (message instanceof ObjectMessage)
    {
      try
      {
        ObjectMessage msg = (ObjectMessage) message;
        System.out.println("==========================================================");
        System.out.println("UOW=[" + unitOfWorkId + "] Message Delivery Failed!!!");

        ArrayList msgList = (ArrayList) (((ObjectMessage) msg).getObject());
        System.out.println("UOW=[" + unitOfWorkId + "] MessageCount=[" + msgList.size() + "]");

        if (msgList.size() > 0 && msgList.get(0) instanceof TextMessage)
        {
          ArrayList<TextMessage> txtMsgList = (ArrayList<TextMessage>) msgList;

          int i = 1;

          for (TextMessage txtMsg : txtMsgList)
          {
            System.out.println("+ Included Message [" + i++ + "]: " + txtMsg.getText());
          }

          System.out.println("==========================================================");
          msg.acknowledge();

        }
        else
        {
          //DEMO is expecting text messages...
        }
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
  }
}
