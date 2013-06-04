package com.oracle.weblogic.examples.mbeans.rest;

import com.oracle.weblogic.examples.mbeans.CounterMXBean;

import javax.annotation.PostConstruct;
import javax.management.JMX;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.util.Hashtable;

/**
 * Created with IntelliJ IDEA.
 * User: jeffreyawest
 * Date: 6/20/12
 * Time: 12:49 PM
 */
@Path("/counter")
@Produces({MediaType.TEXT_PLAIN, MediaType.TEXT_HTML})
@Provider()
public class CounterResource
{
  private CounterMXBean counter;

  @PostConstruct
  private void init()
  {
    try
    {
      Hashtable env = new Hashtable();
//      env.put(Context.SECURITY_PRINCIPAL, "weblogic");
//      env.put(Context.SECURITY_CREDENTIALS, "Welcome1");

      InitialContext ctx = new InitialContext(env);

      MBeanServer mbs = MBeanServer.class.cast(ctx.lookup("java:comp/env/jmx/runtime"));
      ObjectName name = new ObjectName(CounterMXBean.MBEAN_OBJ_NAME_STR);
      counter = JMX.newMBeanProxy(mbs, name, CounterMXBean.class);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  @GET
  @Path("/error")
  public Response createTrackInJSON()
  {
    return Response.status(503).entity("error").build();
  }

  @GET
  @Path("/ping")
  public String getValueRoot()
  {
    return String.valueOf(counter.getValue());
  }

  @GET
  @Path("/value")          // server:port/counter/value
  public String getValue()
  {
    return String.valueOf(counter.getValue());
  }

  @GET
  @Path("/increment")
  public String incrementCounter()
  {
    return String.valueOf(counter.increment());
  }

  @GET
  @Path("/reset")
  public String resetCounter()
  {
    return String.valueOf(counter.reset());
  }

  @GET
  @Path("/set/{newValue}")
  public String setValue(@PathParam("newValue") int pNewValue)
  {
    return String.valueOf(counter.setValue(pNewValue));
  }
}
