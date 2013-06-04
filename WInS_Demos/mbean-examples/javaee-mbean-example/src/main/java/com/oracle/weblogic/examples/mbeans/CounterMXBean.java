package com.oracle.weblogic.examples.mbeans;

/**
 * Created with IntelliJ IDEA.
 * User: jeffreyawest
 * Date: 6/19/12
 * Time: 4:08 PM
 */
public interface CounterMXBean
{
  public static final String MBEAN_NAMESPACE = "com.oracle.weblogic.examples";
  public static final String MBEAN_NAME = "CounterEE";
  public static final String MBEAN_TYPE = "CounterMXBean";

  public static final String MBEAN_OBJ_NAME_STR = MBEAN_NAMESPACE + ":Type=" + MBEAN_TYPE + ",Name=" + MBEAN_NAME;

  int increment();

  int reset();

  int getValue();

  int setValue(int value);
}
