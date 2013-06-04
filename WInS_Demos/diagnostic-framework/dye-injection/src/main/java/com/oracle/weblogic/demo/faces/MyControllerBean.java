package com.oracle.weblogic.demo.faces;

import com.oracle.weblogic.demo.AddingEJB;
import weblogic.diagnostics.context.DiagnosticContextHelper;
import weblogic.diagnostics.context.InvalidDyeException;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

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
 * Time: 11:27 PM
 * To change this template use File | Settings | File Templates.
 */
@Named(value = "MyControllerBean")
@SessionScoped
public class MyControllerBean implements Serializable
{
  @EJB
  private AddingEJB adder;

  public MyControllerBean()
  {}

  public String getSum()
  {
    try
    {
      System.out.println("\nCONTROLLER::ContextId=" + DiagnosticContextHelper.getContextId());
      System.out.println("CONTROLLER::isDyedWith(USER1)=" + DiagnosticContextHelper.isDyedWith(DiagnosticContextHelper.USER1));
      System.out.println("CONTROLLER::isDyedWith(USER2)=" + DiagnosticContextHelper.isDyedWith(DiagnosticContextHelper.USER2));
      System.out.println("=======================");
      DiagnosticContextHelper.setDye(DiagnosticContextHelper.DYE_2, true);
      System.out.println("CONTROLLER::isDyedWith(DYE_0)=" + DiagnosticContextHelper.isDyedWith(DiagnosticContextHelper.DYE_0));
      System.out.println("CONTROLLER::isDyedWith(DYE_1)=" + DiagnosticContextHelper.isDyedWith(DiagnosticContextHelper.DYE_1));
      System.out.println("CONTROLLER::isDyedWith(DYE_2)=" + DiagnosticContextHelper.isDyedWith(DiagnosticContextHelper.DYE_2));
    }
    catch (InvalidDyeException e)
    {
      e.printStackTrace();
    }

    return String.valueOf(adder.add(1, 2));
  }

}
