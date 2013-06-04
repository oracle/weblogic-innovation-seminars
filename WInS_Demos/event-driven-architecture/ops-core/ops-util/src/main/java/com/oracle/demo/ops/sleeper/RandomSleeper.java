package com.oracle.demo.ops.sleeper;

import java.util.Random;

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
 * Date: 3/8/11
 * Time: 2:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class RandomSleeper
{
  public static void main(String[] args)
  {
    for (int x = 0; x < 10; x++)
    {
      sleepOnceOutOf(1, 10, 1000);
    }
  }

  public static void sleepOnceOutOf(int value, int outOf, long sleepTime)
  {
    int randomInt = new Random().nextInt(outOf);
    if (randomInt == value)
    {
      try
      {
        double percent = new Random().nextDouble();

        long randomSleep = (long) (percent * (double) sleepTime);
        System.out.println(Thread.currentThread().getName() + " is sleeping for [" + randomSleep + "]!!");
        Thread.sleep(randomSleep);
      }
      catch (InterruptedException e)
      {
        e.printStackTrace();
      }
    }
  }

  public static void sleepLessThan(int value, int outOf, long sleepTime)
  {
    int randomInt = new Random().nextInt(outOf);

    if (randomInt < value)
    {
      try
      {
        long randomSleep = (1 / (new Random().nextInt(100))) * sleepTime;
        System.out.println(Thread.currentThread().getName() + " is sleeping!!");
        Thread.sleep(randomSleep);
      }
      catch (InterruptedException e)
      {
        e.printStackTrace();
      }
    }
  }
}
