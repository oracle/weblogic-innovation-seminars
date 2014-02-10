/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oracle.example;

import javax.ejb.Remote;

/**
 *
 * @author ankitpan
 */
@Remote
public interface EjbEnvRemote {

    public String getVar1();

    public int getVar2();
}
