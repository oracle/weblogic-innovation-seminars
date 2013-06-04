package com.oracle.example.jms.consumer;

import com.oracle.example.jms.Constants;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.TextMessage;
import javax.naming.NamingException;

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
 * Date: Jan 17, 2011
 * Time: 12:50:42 PM
 */
public class SimpleJMSConsumer extends AbstractJMSConsumer {
    public static final String JMS_CF_JNDI = "com/oracle/example/jms/uoo/cf";
    public static final String JMS_QUEUE_JNDI = "com/oracle/example/jms/uoo/queue";

    public static void main(String[] args) {
        try {
            SimpleJMSConsumer consumer = new SimpleJMSConsumer(Constants.WL_INITIAL_CONTEXT, Constants.JMS_ENDPOINT_ADDRESS, Constants.USERNAME, Constants.PASSWORD, JMS_CF_JNDI, JMS_QUEUE_JNDI);
            consumer.beginReceiveLoop(10);
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void begin() throws JMSException {
        initConnection();

        MessageConsumer feedMe = createConsumer();

        System.out.println("Attaching to Queue...");
        Message message = feedMe.receive();

        if (message instanceof TextMessage) {
            TextMessage msg = (TextMessage) message;

            try {
                System.out.println("Message Received! Text=[" + msg.getText() + "]");
            } catch (JMSException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Non TextMessage received.  Ignoring...");
        }

        endConnection();
    }

    public SimpleJMSConsumer(String pInitialContext, String pURL, String pUsername, String pPassword, String pCFName, String pQueueJNDIName)
            throws NamingException, JMSException {
        super(pInitialContext, pURL, pUsername, pPassword, pCFName, pQueueJNDIName);
    }
}
