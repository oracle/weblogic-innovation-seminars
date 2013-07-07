package com.oracle.demo.ops.event.producer;

import com.oracle.demo.ops.Constants;
import com.oracle.demo.ops.domain.*;
import com.oracle.demo.ops.string.StringUtils;
import com.oracle.demo.ops.xml.MyMarshaller;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.bind.JAXBException;
import java.util.Calendar;
import java.util.Enumeration;
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
 * Date: Feb 20, 2011
 * Time: 7:57:04 AM
 */
public class OPSEventProducer
{
  private InitialContext context;

  ConnectionFactory jmsConnectionFactory;
  Destination jmsDestination;
  Connection conn;
  Session session;
  MessageProducer messageProducer;

  static final String JMS_ENDPOINT = "t3://wins-vbox:7101,wins-vbox:7102";

  public static void main(String[] args)
  {
    try
    {
      int theID = (int) System.currentTimeMillis();

      OPSEventProducer eventSender = new OPSEventProducer(
          JMS_ENDPOINT,
          "weblogic",
          "welcome1",
          Constants.CONNECTION_FACTORY_JNDI,
          Constants.EVENT_QUEUE_JNDI);

      ParcelEvent event = new ParcelEvent();
      event.setParcelId(2);
      event.setLocation("home");
      event.setParcelStatus(ParcelStatus.BILLING_INFO_RECEIVED);
      event.setDate(Calendar.getInstance());
      event.setMessage("Hello World");

      System.out.println("Sending Event: " + StringUtils.toString(event));
      eventSender.forwardEvent(event);

      eventSender = new OPSEventProducer(
          JMS_ENDPOINT,
          "weblogic",
          "welcome1",
          Constants.CONNECTION_FACTORY_JNDI,
          Constants.EVENT_QUEUE_JNDI);

      event = new ParcelEvent();
      event.setParcelId(3);
      event.setLocation("destination");
      event.setParcelStatus(ParcelStatus.DELIVERY_EXCEPTION);
      event.setDate(Calendar.getInstance());
      event.setMessage("Uh oh...");

      System.out.println("Sending Event: " + StringUtils.toString(event));
      eventSender.forwardEvent(event);

      eventSender = new OPSEventProducer(
          JMS_ENDPOINT,
          "weblogic",
          "welcome1",
          Constants.CONNECTION_FACTORY_JNDI,
          Constants.EVENT_QUEUE_JNDI);

      event = new ParcelEvent();
      event.setParcelId(3);
      event.setLocation("destination");
      event.setParcelStatus(ParcelStatus.DELIVERED);
      event.setDate(Calendar.getInstance());
      event.setMessage("Goodbye World");

      System.out.println("Sending Event: " + StringUtils.toString(event));
      eventSender.forwardEvent(event);
    }
    catch (NamingException e)
    {
      e.printStackTrace();
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


  public OPSEventProducer(String pURL,
                          String pUsername,
                          String pPassword,
                          String connectionFactoryName,
                          String destinationJNDI)
      throws NamingException
  {
    Hashtable<String, String> h = new Hashtable<String, String>(7);
    h.put(Context.INITIAL_CONTEXT_FACTORY, Constants.WL_INITIAL_CONTEXT);
    h.put(Context.PROVIDER_URL, pURL);
    h.put(Context.SECURITY_PRINCIPAL, pUsername);
    h.put(Context.SECURITY_CREDENTIALS, pPassword);

    context = new InitialContext(h);
    jmsConnectionFactory = (ConnectionFactory) context.lookup(connectionFactoryName);
    jmsDestination = (Destination) context.lookup(destinationJNDI);

    try
    {
      conn = jmsConnectionFactory.createConnection();
      session = conn.createSession(false, 0);
      messageProducer = session.createProducer(jmsDestination);
    }
    catch (JMSException e)
    {
      e.printStackTrace();
    }
  }

  public OPSEventProducer(String connectionFactoryJNDI, String destinationJNDI) throws NamingException
  {
    Hashtable<String, String> h = new Hashtable<String, String>(7);
    h.put(Context.INITIAL_CONTEXT_FACTORY, Constants.WL_INITIAL_CONTEXT);

    context = new InitialContext(h);
    jmsConnectionFactory = (ConnectionFactory) context.lookup(connectionFactoryJNDI);
    jmsDestination = (Destination) context.lookup(destinationJNDI);

    try
    {
      conn = jmsConnectionFactory.createConnection();
      session = conn.createSession(false, 0);
      messageProducer = session.createProducer(jmsDestination);
    }
    catch (JMSException e)
    {
      e.printStackTrace();
    }

  }

  public void forwardShipment(Shipment pShipment) throws NamingException, JMSException, JAXBException
  {
    try
    {
      String eventXmlString = MyMarshaller.marshallObjectToString(pShipment);
      TextMessage newMessage = session.createTextMessage(eventXmlString);

      messageProducer.send(newMessage);
    }
    finally
    {
      if (messageProducer != null)
      {
        messageProducer.close();
      }
      if (session != null)
      {
        session.close();
      }
    }
  }

  public void forwardEvent(ParcelEvent event) throws JAXBException, JMSException, NamingException
  {
    try
    {
      String eventXmlString = MyMarshaller.marshallObjectToString(event);
      TextMessage newMessage = session.createTextMessage(eventXmlString);

      setJmsMessageProperties(event, newMessage);
      messageProducer.send(newMessage);
    }
    finally
    {
      messageProducer.close();
      session.close();
    }
  }

  private void setJmsMessageProperties(ParcelEvent event, TextMessage newMessage)
      throws JMSException
  {
    newMessage.setStringProperty("location", event.getLocation());

    if (event.getDate() != null)
    {
      newMessage.setStringProperty("date", event.getDate().toString());
    }

    newMessage.setStringProperty("parcelId", String.valueOf(event.getParcelId()));

    if (event.getParcelStatus() != null)
    {
      newMessage.setStringProperty("parcelStatus", event.getParcelStatus().value());
    }
  }
}
