package com.oracle.weblogic.examples.spring.jmx;

import javax.management.*;
import java.io.IOException;
import java.io.PrintWriter;
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
 * Date: Sep 22, 2011
 * Time: 2:14:18 PM
 */
public class SpringRestJMXBean
{
  private String stringValue;

  transient javax.management.MBeanServerConnection editMBeanServer;

  transient javax.management.MBeanServerConnection runtimeMBeanServer;

  public SpringRestJMXBean(String stringValue)
  {
    this.stringValue = stringValue;
  }

  public String getStringValue()
  {
    return stringValue;
  }

  public void setStringValue(String stringValue)
  {
    this.stringValue = stringValue;
  }

  public String doStuff()
  {
    StringWriter sWriter = new StringWriter();
    PrintWriter writer = new PrintWriter(sWriter);

    try
    {
      writer.println("Getting MBean info for[ops.spring.rest:name=OPSRestMgr]");
      MBeanInfo info = runtimeMBeanServer.getMBeanInfo(new ObjectName("ops.spring.rest:name=OPSRestMgr"));
      writer.println(info);
    }
    catch (InstanceNotFoundException e)
    {
      e.printStackTrace();
    }
    catch (IntrospectionException e)
    {
      e.printStackTrace();
    }
    catch (ReflectionException e)
    {
      e.printStackTrace();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    catch (MalformedObjectNameException e)
    {
      e.printStackTrace();
    }

    writer.flush();
    return sWriter.toString();
  }

  public MBeanServerConnection getEditMBeanServer()
  {
    return editMBeanServer;
  }

  public void setEditMBeanServer(MBeanServerConnection editMBeanServer)
  {
    System.out.println("Setting Edit MBean Server");

    this.editMBeanServer = editMBeanServer;
  }

  public MBeanServerConnection getRuntimeMBeanServer()
  {
    return runtimeMBeanServer;
  }

  public void setRuntimeMBeanServer(MBeanServerConnection runtimeMBeanServer)
  {
    System.out.println("Setting Runtime MBean Server");
    this.runtimeMBeanServer = runtimeMBeanServer;
  }
}
