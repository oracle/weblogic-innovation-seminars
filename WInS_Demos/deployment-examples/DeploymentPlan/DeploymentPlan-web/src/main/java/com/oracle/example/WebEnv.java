/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oracle.example;

import java.io.IOException;

import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WebEnv extends HttpServlet {
  @EJB(name="ejb/ejbEnv")
  private EjbEnvRemote ejbEnv;

  protected void doGet(HttpServletRequest request,
                       HttpServletResponse response)
      throws ServletException, IOException {

    try {
      Context env = (Context) new InitialContext()
        .lookup("java:comp/env");

      String s = (String) env.lookup("webVar1");
      int i = ((Integer) env.lookup("webVar2")).intValue();

      response.getWriter().write(
        "webVar1: " + s + "\n");
      response.getWriter().write(
        "webVar2: " + i + "\n");

      response.getWriter().write(
        "ejbVar1: " + ejbEnv.getVar1() + "\n");
      response.getWriter().write(
        "ejbVar2: " + ejbEnv.getVar2() + "\n");
    } catch (NamingException e) {
      response.getWriter().write("NamingException");
    }
  }
}
