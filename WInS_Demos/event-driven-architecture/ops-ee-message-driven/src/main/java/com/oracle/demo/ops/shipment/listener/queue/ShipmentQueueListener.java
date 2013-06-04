package com.oracle.demo.ops.shipment.listener.queue;

import com.oracle.demo.ops.Constants;
import com.oracle.demo.ops.domain.ParcelEvent;
import com.oracle.demo.ops.domain.Shipment;
import com.oracle.demo.ops.entitymanager.ShipmentManager;
import com.oracle.demo.ops.xml.MyMarshaller;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.*;
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
 * Date: Feb 20, 2011
 * Time: 7:45:55 AM
 */

@MessageDriven(
    messageListenerInterface = MessageListener.class,
    name = "ShipmentQueueListenerMDB",
    mappedName = Constants.SHIPMENT_QUEUE_JNDI,
    activationConfig = {
        @ActivationConfigProperty(
            propertyName = "connectionFactoryJndiName",
            propertyValue = Constants.CONNECTION_FACTORY_JNDI),
        @ActivationConfigProperty(
            propertyName = "destinationType",
            propertyValue = "javax.jms.Queue")
    })
public class ShipmentQueueListener
    implements MessageListener
{
  @EJB(mappedName = "ejb/ShipmentManager")
  private ShipmentManager shipmentManager;

  @Override
  public void onMessage(Message message)
  {
    if (message instanceof TextMessage)
    {
      try
      {
        Shipment shipment = MyMarshaller.unmarshallShipment((TextMessage) message);
        shipmentManager.createShipment(shipment);
      }
      catch (JAXBException e)
      {
        e.printStackTrace();
      }
      catch (JMSException e)
      {
        e.printStackTrace();
      }
    }
  }
}
