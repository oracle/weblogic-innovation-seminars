package com.oracle.example.jms.saf;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.*;

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
 * Date: Jan 15, 2011
 * Time: 7:35:38 AM
 */
@MessageDriven(
    messageListenerInterface = MessageListener.class,
    name = "SAFTargetListenerMDB",
    mappedName = "com.oracle.example.jms.saf.local-queue",
    activationConfig = {
        @ActivationConfigProperty(
            propertyName = "connectionFactoryJndiName",
            propertyValue = "com.oracle.example.jms.saf.cf"),
        @ActivationConfigProperty(
            propertyName = "destinationType",
            propertyValue = "javax.jms.Queue")
    })
public class SAFListenerBean implements MessageListener
{
  public SAFListenerBean()
  {
  }

  public void onMessage(Message message)
  {
    if (message instanceof TextMessage)
    {
      TextMessage msg = (TextMessage) message;

      try
      {
        System.out.println("SAFListenerBean.onMessage::" + msg.getText());
      }
      catch (JMSException e)
      {
        e.printStackTrace();
      }
    }

    try
    {
      Thread.sleep(500);
    }
    catch (Exception ignore)
    {
    }
  }
}
