package servlets;

import beans.MyBean;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created with IntelliJ IDEA.
 * User: jeffrey.a.west
 * Date: 4/4/12
 * Time: 12:05 PM
 */

@WebServlet(description = "ReadServlet Description", urlPatterns = {"/read"})
public class ReadServlet extends HttpServlet
{
  @Inject
  private MyBean myBean;

  protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    PrintWriter out = response.getWriter();
    out.println("CDI Bean Value=" + myBean.getValue());
    out.println("Value from Session=" + request.getSession().getAttribute("value"));
    out.println("SessionID=" + request.getSession().getId());
  }
}
