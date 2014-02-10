/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oracle.example;

import javax.annotation.Resource;
import javax.ejb.Stateless;

@Stateless(mappedName = "ejb/ejbEnv")
public class EjbEnv implements EjbEnvRemote, EjbEnvLocal {
  @Resource
  private String var1;
  
  @Resource
  private int var2;

  public String getVar1() {
    return var1;
  }

  public int getVar2() {
    return var2;
  }
}

