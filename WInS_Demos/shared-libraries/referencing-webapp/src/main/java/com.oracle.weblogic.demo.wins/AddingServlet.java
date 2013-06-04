package com.oracle.weblogic.demo.wins;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * /*
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
 * Created with IntelliJ IDEA because its awesome.
 * User: jeffreyawest
 * Date: 5/31/13
 * Time: 12:39 PM
 * To change this template use File | Settings | File Templates.
 */

@WebServlet(name = "AddingServlet", urlPatterns = {"/AddingServlet"})
public class AddingServlet extends HttpServlet
{
  @EJB
  private AddingEJB myEJB;

  @Override
  protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException
  {
    handleRequest(httpServletRequest, httpServletResponse);
  }

  private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
  {
    System.out.println("---SERVLET START------------------------------------------------------------------------");

    int p1 = Integer.valueOf(request.getParameter("p1"));
    int p2 = Integer.valueOf(request.getParameter("p2"));

    PrintWriter writer = response.getWriter();

    int sum = myEJB.add(p1, p2);

    try
    {
      writer.println(p1 + "+" + p2 + "=" + sum);
    }
    finally
    {
      writer.close();
    }

    System.out.println("---SERVLET FINISH------------------------------------------------------------------------");

  }

  @Override
  protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException
  {
    handleRequest(httpServletRequest, httpServletResponse);
  }

  @Override
  protected void doPut(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException
  {
    handleRequest(httpServletRequest, httpServletResponse);
  }

  @Override
  public String getServletInfo()
  {
    return "Servlet that Adds";
  }
}
