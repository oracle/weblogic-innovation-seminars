package jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class InvoiceQueueSender
{
  private JmsTemplate jmsTemplate;

  public void setJmsTemplate(JmsTemplate jmsTemplate)
  {
    this.jmsTemplate = jmsTemplate;
  }

  public void sendMesage()
  {
    MessageCreator messageCreator = new MessageCreator()
    {
      public Message createMessage(Session session) throws JMSException
      {
        return session.createTextMessage("I am sending Invoice message");
      }
    };

    jmsTemplate.send("jms/testQueue", messageCreator);
  }
}