package com.oracle.demo.ops.event.listener.topic;

import com.oracle.demo.ops.Constants;
import com.oracle.demo.ops.domain.ParcelEvent;
import com.oracle.demo.ops.entitymanager.ParcelEventManager;
import com.oracle.demo.ops.jms.JMSUtils;
import com.oracle.demo.ops.string.StringUtils;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
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

// http://download.oracle.com/docs/cd/E17904_01/web.1111/e15493/dist_topics.htm

@MessageDriven(
    messageListenerInterface = javax.jms.MessageListener.class,
    name = "AddParcelLogMessageListener",
    mappedName = Constants.EVENT_TOPIC_JNDI,
    activationConfig = {
        @ActivationConfigProperty(propertyName = "connectionFactoryJndiName", propertyValue = Constants.CONNECTION_FACTORY_JNDI),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
        @ActivationConfigProperty(propertyName = "subscriptionDurability", propertyValue = "Durable"),
        @ActivationConfigProperty(propertyName = "topicMessagesDistributionMode", propertyValue = "One-Copy-Per-Application"),
        @ActivationConfigProperty(propertyName = "distributedDestinationConnection", propertyValue = "LocalOnly")
    })

public class UpdateParcelLogMessageListener
    implements MessageListener
{
  @EJB(beanName = "ParcelEventManagerBean")
  protected ParcelEventManager parcelEventManager;

  @Override
  public void onMessage(Message message)
  {
    ParcelEvent event = JMSUtils.getEventFromMessage(message);
    String theString = StringUtils.toString(event);
    System.out.println("EE:: Add Event to Parcel Log:: " + theString);
  }
}