package com.oracle.demo.ops.jms.listener.event.queue;

import com.oracle.demo.ops.domain.ParcelEvent;
import com.oracle.demo.ops.services.EventService;
import com.oracle.demo.ops.xml.MyMarshaller;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.xml.bind.JAXBException;

/**
  * **************************************************************************
 * <p/>
 * This code is provided for example purposes only.  Oracle does not assume
 * any responsibility or liability for the consequences of using this code.
 * If you choose to use this code for any reason, including but not limited
 * to its use as an example you do so at your own risk and without the support
 * of Oracle.
 *
 * This code is provided under the following licenses:
 *
 * GNU General Public License (GPL-2.0)
 * COMMON DEVELOPMENT AND DISTRIBUTION LICENSE Version 1.0 (CDDL-1.0)
 *
 * <p/>
 * ****************************************************************************
 * User: jeffrey.a.west
 * Date: Feb 15, 2011
 * Time: 9:58:46 PM
 */
public class EventForwarderMessageListenerMDP
    implements MessageListener
{
  private EventService EventService;

  @Autowired
  public void setForwarder(EventService eventForwarder)
  {
    this.EventService = eventForwarder;
  }

  @Override
  public void onMessage(Message message)
  {
    System.out.println(this.getClass().getName() + ":: Forwarding message...");

    if (message instanceof TextMessage)
    {
      try
      {
        ParcelEvent event = MyMarshaller.unmarshalEvent((TextMessage) message);
        EventService.publishEventToTopic(event);
      }
      catch (JMSException e)
      {
        e.printStackTrace();
      }
      catch (JAXBException e)
      {
        e.printStackTrace();
      }
    }
    else
    {
      System.out.println("EventQueueListener NOT TEXT MESSSAGE");
    }
  }
}