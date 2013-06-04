package com.oracle.demo.ops.event.spring.converter;

import com.oracle.demo.ops.xml.MyMarshaller;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
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
 * Date: May 20, 2011
 * Time: 12:53:38 PM
 */
public class ParcelLogEventMessageConverter
    implements MessageConverter
{
  @Override
  public Message toMessage(Object object, Session session)
      throws
      JMSException,
      MessageConversionException
  {
    throw new MessageConversionException("Not Implemented");
  }

  @Override
  public Object fromMessage(Message message)
      throws
      JMSException,
      MessageConversionException
  {
    //System.out.println("MessageConverter! ");
    try
    {
      return MyMarshaller.unmarshalEvent((TextMessage) message);
    }
    catch (JAXBException e)
    {
      throw new MessageConversionException("Error in MessageConverter: " + e.getMessage(), e);
    }
  }
}
