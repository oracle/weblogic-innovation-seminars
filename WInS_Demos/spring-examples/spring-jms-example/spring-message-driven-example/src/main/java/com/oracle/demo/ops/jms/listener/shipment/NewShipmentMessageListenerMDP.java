package com.oracle.demo.ops.jms.listener.shipment;

import com.oracle.demo.ops.domain.Shipment;
import com.oracle.demo.ops.entitymanager.ShipmentManager;
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
 * Time: 4:00:30 PM
 */
public class NewShipmentMessageListenerMDP
    implements MessageListener
{
  private ShipmentManager shipmentManager;

  @Autowired
  public void setShipmentManager(ShipmentManager shipmentManager)
  {
    this.shipmentManager = shipmentManager;
  }

  @Override
  public void onMessage(Message message)
  {
    System.out.println("NewShipmentMessageListener.onMessage - Thread: " + Thread.currentThread().getName());

    if (message instanceof TextMessage)
    {
      TextMessage txtMessage = (TextMessage) message;

      try
      {
        Shipment shipment = MyMarshaller.unmarshallShipment(txtMessage);
        shipmentManager.createShipment(shipment);
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
  }
}
