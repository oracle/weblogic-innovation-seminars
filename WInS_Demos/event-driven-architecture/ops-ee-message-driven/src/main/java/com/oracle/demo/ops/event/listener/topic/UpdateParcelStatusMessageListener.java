package com.oracle.demo.ops.event.listener.topic;

import com.oracle.demo.ops.Constants;
import com.oracle.demo.ops.domain.ParcelEvent;
import com.oracle.demo.ops.entitymanager.ParcelManager;
import com.oracle.demo.ops.jms.JMSUtils;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.inject.Named;
import javax.jms.Message;
import javax.jms.MessageListener;

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
 * Date: Feb 19, 2011
 * Time: 9:01:08 AM
 */
@MessageDriven(
    messageListenerInterface = javax.jms.MessageListener.class,
    name = "UpdateParcelStatusMessageListener",
    mappedName = Constants.EVENT_TOPIC_JNDI,
    activationConfig = {
        @ActivationConfigProperty(propertyName = "connectionFactoryJndiName", propertyValue = Constants.CONNECTION_FACTORY_JNDI),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
        @ActivationConfigProperty(propertyName = "subscriptionDurability", propertyValue = "Durable"),
        @ActivationConfigProperty(propertyName = "topicMessagesDistributionMode", propertyValue = "One-Copy-Per-Application"),
        @ActivationConfigProperty(propertyName = "distributedDestinationConnection", propertyValue = "LocalOnly")
    })
public class UpdateParcelStatusMessageListener
    implements MessageListener
{
  @EJB(beanName = "ParcelManagerBean")
  protected ParcelManager parcelManager;

  @Override
  public void onMessage(Message message)
  {
    System.out.println(this.getClass() + "::onMessage()");
    ParcelEvent event = JMSUtils.getEventFromMessage(message);

    System.out.println("EE: Updating Parcel ID=[" + event.getParcelId() + "] to Status=[" + event.getParcelStatus() + "]");


    //EJB Code to update database
  }
}
