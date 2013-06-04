package com.example;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@MessageDriven(mappedName = "jms/testQueue", activationConfig = {
    @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class MessageDrivenBean implements MessageListener
{

  public MessageDrivenBean()
  {
  }

  @Override
  public void onMessage(Message message)
  {
    try
    {
      TextMessage tm = (TextMessage) message;
      System.out.println("Hello " + tm.getText());
    }
    catch (JMSException jex)
    {
      System.out.println("Exception: " + jex);
    }
  }
}
