package com.oracle.weblogic.examples.spring.counter;

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
 * * Created with IntelliJ IDEA.
 * User: jeffrey.a.west
 * Date: 4/3/12
 * Time: 1:57 PM
 */
public class CounterBean
{
  private int value;

  public int increment()
  {
    return ++value;
  }

  public int reset()
  {
    value = 0;

    return value;
  }

  public int getValue()
  {
    return value;
  }

  public int setValue(int value)
  {
    this.value = value;
    return value;
  }
}
