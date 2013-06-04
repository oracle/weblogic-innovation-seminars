package com.oracle.example.jms.consumer;

import weblogic.jms.extensions.*;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Hashtable;

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
 * Time: 8:01:56 AM
 */
public abstract class AbstractJMSConsumer {
    private WLSession mSession;
    private WLConnection mConnection;
    private WLDestination mQueue;
    final QueueConnectionFactory mQueueConnectionFactory;

    public AbstractJMSConsumer(String pInitialContext, String pURL, String pUsername, String pPassword, String pCFName, String pQueueJNDIName) throws NamingException, JMSException {
        final InitialContext context;
        Hashtable<String, String> h = new Hashtable<String, String>(5);
        h.put(Context.INITIAL_CONTEXT_FACTORY, pInitialContext);
        h.put(Context.PROVIDER_URL, pURL);
        h.put(Context.SECURITY_PRINCIPAL, pUsername);
        h.put(Context.SECURITY_CREDENTIALS, pPassword);

        System.out.println("Getting context");
        context = new InitialContext(h);

        System.out.println("Looking up CF reference...");
        mQueueConnectionFactory = (QueueConnectionFactory) context.lookup(pCFName);

        System.out.println("Looking up Queue...");
        mQueue = (WLDestination) context.lookup(pQueueJNDIName);
    }

    public abstract void begin() throws JMSException;

    public void beginReceiveLoop(int pMaxIterations) throws JMSException {
        initConnection();

        javax.jms.MessageConsumer feedMe = mSession.createConsumer(mQueue);


        int loopCounter = 0;

        do {
            loopCounter++;

            System.out.println("Blocking to Receive...");
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

            System.out.println("Message Count=[" + loopCounter + "] Messages left before end=[" + (pMaxIterations - loopCounter) + "]");
        }
        while (loopCounter < pMaxIterations);


        endConnection();
    }

    public void beginSingleReceive() throws JMSException {
        javax.jms.MessageConsumer feedMe = mSession.createConsumer(mQueue);

        initConnection();

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

    protected void endConnection()
            throws JMSException {
        mConnection.close();
    }

    protected MessageConsumer createConsumer() throws JMSException {
        return mSession.createConsumer(mQueue);
    }

    protected void initConnection()
            throws JMSException {
        System.out.println("Creating connection...");
        mConnection = (WLConnection) mQueueConnectionFactory.createQueueConnection();

        System.out.println("Starting connection...");
        mConnection.start();

        System.out.println("Creating session...");
        mSession = (WLSession) mConnection.createQueueSession(false, 0);
    }


}