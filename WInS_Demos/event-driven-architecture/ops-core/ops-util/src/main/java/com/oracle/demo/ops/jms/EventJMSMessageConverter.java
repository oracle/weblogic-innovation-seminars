package com.oracle.demo.ops.jms;

import com.oracle.demo.ops.domain.ParcelEvent;
import com.oracle.demo.ops.xml.MyMarshaller;

import javax.inject.Named;
import javax.jms.JMSException;
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
 * <p/>
 * This code is provided under the following licenses:
 * <p/>
 * GNU General Public License (GPL-2.0)
 * COMMON DEVELOPMENT AND DISTRIBUTION LICENSE Version 1.0 (CDDL-1.0)
 * <p/>
 * <p/>
 * ****************************************************************************
 * * Created by IntelliJ IDEA.
 * User: jeffrey.a.west
 * Date: 12/6/11
 * Time: 10:55 AM
 */
@Named
public class EventJMSMessageConverter
{
  public ParcelEvent convertMessage(TextMessage message) throws JAXBException, JMSException
  {
    return (ParcelEvent) MyMarshaller.unmarshalEvent((TextMessage) message);
  }
}
