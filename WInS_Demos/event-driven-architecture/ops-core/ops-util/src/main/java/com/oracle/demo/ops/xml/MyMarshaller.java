package com.oracle.demo.ops.xml;

import com.oracle.demo.ops.domain.ParcelEvent;
import com.oracle.demo.ops.domain.Shipment;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.io.StringWriter;

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
 * Time: 7:57:39 AM
 */
public class MyMarshaller
{
  static
  {
    try
    {
      jaxbContext_shipping = JAXBContext.newInstance("com.oracle.demo.ops.domain");
    }
    catch (JAXBException e)
    {
      e.printStackTrace();
    }

  }

  private static JAXBContext jaxbContext_shipping;


  public static ParcelEvent unmarshalEvent(final TextMessage textMessage)
      throws
      JMSException,
      JAXBException
  {
    String xmlString = textMessage.getText();
//    System.out.println("Message Text:" + xmlString);

    return unmarshalEvent(xmlString);
  }

  public static ParcelEvent unmarshalEvent(final String xmlString)
      throws
      JAXBException
  {
    jaxbContext_shipping = JAXBContext.newInstance("com.oracle.demo.ops.domain");
    Unmarshaller unmarshaller = jaxbContext_shipping.createUnmarshaller();
    Source reader = new StreamSource(new StringReader(xmlString));

    return (ParcelEvent) unmarshaller.unmarshal(reader);
  }

  public static Shipment unmarshallShipment(final String xmlString)
      throws
      JMSException,
      JAXBException
  {
    Unmarshaller unmarshaller = jaxbContext_shipping.createUnmarshaller();
    Source reader = new StreamSource(new StringReader(xmlString));

    return (Shipment) unmarshaller.unmarshal(reader);
  }

  public static Shipment unmarshallShipment(final TextMessage message)
      throws
      JMSException,
      JAXBException
  {
    return unmarshallShipment(message.getText());
  }

  public static String marshallObjectToString(final Object object)
      throws
      JAXBException
  {
    Marshaller marshaller = jaxbContext_shipping.createMarshaller();
    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

    StringWriter writer = new StringWriter();

    marshaller.marshal(object, writer);
    writer.flush();
    return writer.toString();
  }

}
