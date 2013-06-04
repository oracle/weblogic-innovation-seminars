package jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class InvoiceMDP implements MessageListener
{
  public void onMessage(Message message)
  {
    try
    {
      System.out.println(((TextMessage) message).getText());
      System.out.println("Hello");
    }
    catch (JMSException ex)
    {
      throw new RuntimeException(ex);
    }
  }
}