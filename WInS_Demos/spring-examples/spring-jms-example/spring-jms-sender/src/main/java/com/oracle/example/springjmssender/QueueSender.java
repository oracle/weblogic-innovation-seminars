/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oracle.example.springjmssender;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**
 *
 * @author ankitpan
 */
public class QueueSender
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
        return session.createTextMessage("Sending Messages");
      }
    };

    jmsTemplate.send("jms/testQueue", messageCreator);
  }
}
