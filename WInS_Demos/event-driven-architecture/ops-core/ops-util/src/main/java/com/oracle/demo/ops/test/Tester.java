package com.oracle.demo.ops.test;

import java.util.ArrayList;

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
 * Date: 3/7/11
 * Time: 5:53 PM
 */
public class Tester
{
  public static final int THREAD_COUNT = 1;
  public static final int MESSAGES_PER_THREAD = 1;

  public static void main(String[] args)
  {
    try
    {
      ArrayList<ShipmentCreatorThread> list = new ArrayList<ShipmentCreatorThread>(THREAD_COUNT);

      for (int x = 1; x <= THREAD_COUNT; x++)
      {
        ShipmentCreatorThread creatorThread = new ShipmentCreatorThread(MESSAGES_PER_THREAD, "Jeff West", "Davis Peden");
        Thread thread = new Thread(creatorThread, "ShipmentCreatorThread-" + x);
        //thread.start();
        list.add(creatorThread);
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  private static void log(String s)
  {
    System.out.println(s);
  }
}
