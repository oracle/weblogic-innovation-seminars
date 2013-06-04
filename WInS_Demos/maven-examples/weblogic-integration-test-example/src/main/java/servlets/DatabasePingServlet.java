package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Statement;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.spi.InitialContextFactory;
import javax.sql.DataSource;

/**
 * File: AddingServlet.java
 * <p/>
 * Copyright (c) 1997-2012 Oracle and/or its affiliates. All rights reserved
 * <p/>
 * Oracle is a registered trademark of Oracle Corporation and/or its affiliates.
 * <p/>
 * User: jeffrey.a.west Date: 1/4/12 Time: 3:12 PM
 */
@WebServlet(name = "DatabasePing", urlPatterns = {"/DatabasePing"})
public class DatabasePingServlet extends HttpServlet
{

  @Override
  protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException
  {
    handleRequest(httpServletRequest, httpServletResponse);
  }

  private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
  {
    java.sql.Connection conn = null;
    PrintWriter writer = response.getWriter();

    try
    {
      DataSource ds;
      Context context = new InitialContext();
      ds = (javax.sql.DataSource) context.lookup("cgDatasource");
      conn = ds.getConnection();
      Statement stmt = conn.createStatement();
      stmt.executeQuery("SELECT 1 FROM DUAL");
      writer.println("SUCCESS");
      stmt.close();
    }
    catch (Exception e)
    {

      e.printStackTrace(writer);

    }
    finally
    {
      response.getWriter().close();
      if (conn != null)
      {
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
