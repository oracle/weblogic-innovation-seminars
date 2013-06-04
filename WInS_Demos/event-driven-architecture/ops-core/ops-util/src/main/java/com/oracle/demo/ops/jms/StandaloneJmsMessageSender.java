package com.oracle.demo.ops.jms;

import com.oracle.demo.ops.Constants;
import com.oracle.demo.ops.domain.*;
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
public class StandaloneJmsMessageSender
{
  private InitialContext context;

  ConnectionFactory jmsConnectionFactory;
  Destination jmsDestination;
  Connection conn;
  Session session;
  MessageProducer messageProducer;

  static final String JMS_ENDPOINT = "t3://127.0.0.1:7101,127.0.0.1:7102";

  public static void main(String[] args)
  {
    try
    {
      int theID = (int) System.currentTimeMillis();

//      StandaloneJmsMessageSender shipmentSender = new StandaloneJmsMessageSender(
//          "t3://wins-vbox:7101,wins-vbox:7102",
//          "weblogic",
//          "welcome1",
//          Constants.CONNECTION_FACTORY_JNDI,
//          Constants.SHIPMENT_QUEUE_JNDI);
//
//      Shipment shipment = generateShipment("Jeff West", "Jeffrey West");
//      shipmentSender.forwardShipment(shipment);


      StandaloneJmsMessageSender eventSender = new StandaloneJmsMessageSender(
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


  public StandaloneJmsMessageSender(String pURL,
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

  public StandaloneJmsMessageSender(String connectionFactoryJNDI, String destinationJNDI) throws NamingException
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

  public void forwardShipmentSAFE(Shipment pShipment)
  {
    try
    {
      forwardShipment(pShipment);
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


  public void forwardEventSAFE(ParcelEvent event)
  {
    try
    {
      forwardEvent(event);
    }
    catch (JAXBException e)
    {
      e.printStackTrace();
    }
    catch (JMSException e)
    {
      e.printStackTrace();
    }
    catch (NamingException e)
    {
      e.printStackTrace();
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

  public void forwardEventMessage(TextMessage message) throws JAXBException, JMSException, NamingException
  {
    ParcelEvent event = MyMarshaller.unmarshalEvent(message);


    try
    {
      TextMessage newMessage = session.createTextMessage(message.getText());

      for (Enumeration em = message.getPropertyNames(); em.hasMoreElements(); )
      {
        String property = (String) em.nextElement();
        newMessage.setObjectProperty(property, message.getObjectProperty(property));
      }

      setJmsMessageProperties(event, newMessage);
      messageProducer.send(newMessage);
    }
    finally
    {
//      messageProducer.close();
//      session.close();
    }
  }

  private static Shipment generateShipment(final String senderName, final String recipientName)
  {
    Shipment shipment = new Shipment();

    shipment.setFromAddress(new Address());
    shipment.getFromAddress().setAddressee(senderName);
    shipment.getFromAddress().setAddressLine1("Vernon Park Mall");
    shipment.getFromAddress().setCity("Kinston");
    shipment.getFromAddress().setState("NC");
    shipment.getFromAddress().setPostalCode("28501");

    shipment.setToAddress(new Address());
    shipment.getToAddress().setAddressee(recipientName);
    shipment.getToAddress().setAddressLine1("Southern Methodist University");
    shipment.getToAddress().setCity("Dallas");
    shipment.getToAddress().setState("TX");
    shipment.getToAddress().setPostalCode("75275");

    Parcel p = new Parcel();
    p.setContents("stuff");
    p.setWeight(10);
    p.setHeight(10);
    p.setWidth(10);
    p.setLength(10);
    p.setParcelStatus(ParcelStatus.BILLING_INFO_RECEIVED);
    shipment.getParcels().add(p);

    p = new Parcel();
    p.setContents("more stuff");
    p.setWeight(10);
    p.setHeight(10);
    p.setWidth(10);
    p.setLength(10);
    p.setParcelStatus(ParcelStatus.BILLING_INFO_RECEIVED);
    shipment.getParcels().add(p);

    return shipment;
  }


}
