package com.oracle.weblogic.examples.spring.jmx;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

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
 * Time: 6:06:26 PM
 */
public class ObjectNameWrapper
{
  private String beanName;
  private String type;
  private String namespace;
  private String path;

  public String getBeanName()
  {
    return beanName;
  }

  public void setBeanName(String beanName)
  {
    this.beanName = beanName;
  }

  public String getType()
  {
    return type;
  }

  public void setType(String type)
  {
    this.type = type;
  }

  public String getNamespace()
  {
    return namespace;
  }

  public void setNamespace(String namespace)
  {
    this.namespace = namespace;
  }

  public String getPath()
  {
    return path;
  }

  public void setPath(String path)
  {
    this.path = path;
  }

  public String getFullName()
  {
    StringBuilder sb = new StringBuilder(getNamespace());
    sb.append(":Name=");
    sb.append(getBeanName());

    return sb.toString();
  }

  public ObjectName getObjectName()
      throws
      MalformedObjectNameException
  {
    return new ObjectName(getFullName());
  }
}
