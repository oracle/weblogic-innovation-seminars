package com.oracle.weblogic.examples.mbeans.weblogic;

import com.oracle.weblogic.examples.mbeans.CounterMXBean;
import com.oracle.weblogic.examples.mbeans.CounterMXBeanImpl;

import javax.management.*;
import javax.naming.InitialContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Created with IntelliJ IDEA.
 * User: jeffreyawest
 * Date: 6/19/12
 * Time: 4:25 PM
 */
@WebListener
public class ApplicationMBeanLifeCycleListener implements ServletContextListener
{
  @Override
  public void contextInitialized(ServletContextEvent pServletContextEvent)
  {
    try
    {
      InitialContext ctx = new InitialContext();
      MBeanServer mbs = MBeanServer.class.cast(ctx.lookup("java:comp/env/jmx/runtime"));

      CounterMXBeanImpl mbean = new CounterMXBeanImpl();

      ObjectName oname = new ObjectName(CounterMXBean.MBEAN_OBJ_NAME_STR);

      ObjectInstance instance = mbs.registerMBean(mbean, oname);
      System.out.println("MBean Registered: " + instance.getObjectName().toString());

    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  @Override
  public void contextDestroyed(ServletContextEvent pServletContextEvent)
  {
    try
    {
      InitialContext ctx = new InitialContext();
      MBeanServer mbs = MBeanServer.class.cast(ctx.lookup("java:comp/env/jmx/runtime"));
      ObjectName oname = new ObjectName(CounterMXBean.MBEAN_OBJ_NAME_STR);

      if (mbs.isRegistered(oname))
      {
        mbs.unregisterMBean(oname);
        System.out.println("MBean Unregistered: " + oname.toString());
      }
    }
    catch (Exception e)
    {
      // Deal with exception
      e.printStackTrace();
    }
  }
}