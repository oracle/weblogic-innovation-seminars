/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oracle.example.springjmssender;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
/**
 *
 * @author ankitpan
 */
public class QueueSenderServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
    
protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
QueueSender sender = (QueueSender)ctx.getBean("jmsSender");
sender.sendMesage();

}
}
