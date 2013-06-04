package com.oracle.weblogic.examples.spring.jmx;

import javax.management.*;
import java.io.IOException;

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
 * Date: Sep 23, 2011
 * Time: 5:58:38 PM
 */
public class WebLogicJMXWrapper
{
  private transient javax.management.MBeanServerConnection runtimeMBeanServerConnection;

  private String myString;

  public WebLogicJMXWrapper(javax.management.MBeanServerConnection runtimeMBeanServerConnection)
  {
    this.runtimeMBeanServerConnection = runtimeMBeanServerConnection;
  }

  public String getAttributeValue(ObjectNameWrapper nameWrapper, String attName)
  {
    try
    {
      ObjectName name = nameWrapper.getObjectName();
      System.out.println("Looking up MBean with name=[" + name.toString() + "]");
      Object obj = runtimeMBeanServerConnection.getAttribute(name, attName);
      return String.valueOf(obj);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }

    return null;
  }

  public void setAttributeValue(ObjectNameWrapper nameWrapper, String attName, String newValue)
      throws
      InstanceNotFoundException,
      IOException,
      InvalidAttributeValueException,
      ReflectionException,
      AttributeNotFoundException,
      MBeanException,
      MalformedObjectNameException
  {
    ObjectName name = nameWrapper.getObjectName();
    System.out.println("Looking up MBean with name=[" + name.toString() + "]");
    Attribute att = new Attribute(attName, newValue);
    runtimeMBeanServerConnection.setAttribute(name, att);
  }

  public String getMyString()
  {
    return myString;
  }

  public void setMyString(String myString)
  {
    this.myString = myString;
  }

}
