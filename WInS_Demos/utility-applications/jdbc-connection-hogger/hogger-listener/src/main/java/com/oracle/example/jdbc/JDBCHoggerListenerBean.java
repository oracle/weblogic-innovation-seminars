package com.oracle.example.jdbc;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.lang.Exception;
import java.lang.Thread;
import java.sql.Connection;

/**
 * **************************************************************************
 * <p/>
 * This code is provided for example purposes only.  Oracle does not assume
 * any responsibility or liability for the consequences of using this code.
 * If you choose to use this code for any reason, including but not limited
 * to its use as an example you do so at your own risk and without the support
 * of Oracle.
 * <p/>
 * ****************************************************************************
 * User: jeffrey.a.west
 * Date: Jan 15, 2011
 * Time: 7:35:04 AM
 */
@MessageDriven(
    messageListenerInterface = MessageListener.class,
    name = "JDBCHoggerEJB",
    mappedName = "com.oracle.example.jms.util.jdbcHogger",
    activationConfig = {
        @ActivationConfigProperty(
            propertyName = "connectionFactoryJndiName",
            propertyValue = "com/oracle/example/jms/util/cf"),
        @ActivationConfigProperty(
            propertyName = "destinationType",
            propertyValue = "javax.jms.Topic")
    })
public class JDBCHoggerListenerBean implements MessageListener
{
  public JDBCHoggerListenerBean()
  {
  }

  public void onMessage(Message message)
  {
    Connection conn = null;

    try
    {
      String jndiName = "jdbc.ds.weblogic_examples";

      InitialContext ic = new InitialContext();
      DataSource ds = (DataSource) ic.lookup(jndiName);

      conn = ds.getConnection();
      System.out.println("Thread: " + Thread.currentThread().getName() + " | Got connection... sleeping...");
      Thread.sleep(20000);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    finally
    {
      if (conn != null)
      {
        System.out.println("Thread: " + Thread.currentThread().getName() + " | Releasing connection!");
        try
        {
          conn.close();
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
      }
    }

  }
}
