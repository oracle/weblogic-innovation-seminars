package com.oracle.example.jms.base.topic.clearscreen;

import javax.ejb.ActivationConfigProperty;
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
 * *****************************************************************************
 * User: jeffrey.a.west
 * Date: Jan 17, 2011
 * Time: 12:50:42 PM
 */

@MessageDriven(
    messageListenerInterface = MessageListener.class,
    name = "ClearScreenBean",
    mappedName = "com/oracle/example/jms/base/clearscreen",
    activationConfig = {
        @ActivationConfigProperty(
            propertyName = "connectionFactoryJndiName",
            propertyValue = "com/oracle/example/jms/base/cf"),
        @ActivationConfigProperty(
            propertyName = "destinationType",
            propertyValue = "javax.jms.Topic"),
        @ActivationConfigProperty(
            propertyName = "topicMessagesDistributionMode",
            propertyValue = "One-Copy-Per-Server")
    })
public class ClearScreenBean
    implements MessageListener
{
  public static final boolean DEBUG = false;

  public ClearScreenBean()
  {
  }

  public void onMessage(Message message)
  {
    for (int x = 0; x < 60; x++)
    {
      System.out.println("");
    }
  }
}
