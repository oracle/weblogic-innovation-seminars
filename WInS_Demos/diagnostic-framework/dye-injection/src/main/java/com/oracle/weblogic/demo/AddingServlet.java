package com.oracle.weblogic.demo;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import weblogic.diagnostics.context.DiagnosticContextHelper;
import weblogic.diagnostics.context.InvalidDyeException;

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
 * Created with IntelliJ IDEA because its awesome.
 * User: jeffreyawest
 * Date: 4/18/13
 * Time: 10:07 PM
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

    try
    {
      System.out.println("\nServlet::ContextId=" + DiagnosticContextHelper.getContextId());
      System.out.println("Servlet::isDyedWith(USER1)=" + DiagnosticContextHelper.isDyedWith(DiagnosticContextHelper.USER1));
      System.out.println("Servlet::isDyedWith(USER2)=" + DiagnosticContextHelper.isDyedWith(DiagnosticContextHelper.USER2));
      System.out.println("=======================");
      DiagnosticContextHelper.setDye(DiagnosticContextHelper.DYE_0, true);
      System.out.println("Servlet::isDyedWith(DYE_0)=" + DiagnosticContextHelper.isDyedWith(DiagnosticContextHelper.DYE_0));
      System.out.println("Servlet::isDyedWith(DYE_1)=" + DiagnosticContextHelper.isDyedWith(DiagnosticContextHelper.DYE_1));
      System.out.println("Servlet::isDyedWith(DYE_2)=" + DiagnosticContextHelper.isDyedWith(DiagnosticContextHelper.DYE_2));
    }
    catch (InvalidDyeException e)
    {
      e.printStackTrace();
    }

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


    try
    {
      System.out.println("\nServlet::isDyedWith(DYE_1)=" + DiagnosticContextHelper.isDyedWith(DiagnosticContextHelper.DYE_1));
    }
    catch (InvalidDyeException e)
    {
      e.printStackTrace();
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
