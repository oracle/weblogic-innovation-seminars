package com.oracle.weblogic.examples.mbeans;

import javax.management.MBeanRegistration;
import javax.management.MBeanServer;
import javax.management.ObjectName;

/**
 * Created with IntelliJ IDEA.
 * User: jeffrey.a.west
 * Date: 4/3/12
 * Time: 1:57 PM
 */
public class CounterMXBeanImpl implements CounterMXBean, MBeanRegistration
{
  private int value;

  @Override
  public int increment()
  {
    return ++value;
  }

  @Override
  public int reset()
  {
    value = 0;

    return value;
  }

  @Override
  public int getValue()
  {
    return value;
  }

  @Override
  public int setValue(int value)
  {
    this.value = value;
    return this.getValue();
  }

  @Override
  public ObjectName preRegister(MBeanServer pMBeanServer, ObjectName pObjectName) throws Exception
  {
    return pObjectName;
  }

  @Override
  public void postRegister(Boolean pBoolean)
  {
  }

  @Override
  public void preDeregister() throws Exception
  {
  }

  @Override
  public void postDeregister()
  {
  }
}
