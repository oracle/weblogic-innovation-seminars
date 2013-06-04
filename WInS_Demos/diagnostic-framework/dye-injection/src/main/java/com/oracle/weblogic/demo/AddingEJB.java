package com.oracle.weblogic.demo;

import weblogic.diagnostics.context.DiagnosticContextHelper;
import weblogic.diagnostics.context.InvalidDyeException;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

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
 * Time: 11:11 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless
public class AddingEJB
{
  public AddingEJB()
  {}

  public int add(int p1, int p2)
  {
    System.out.println("===EJB START========================================================================");

    try
    {
      DiagnosticContextHelper.setDye(DiagnosticContextHelper.DYE_1, true);

      System.out.println("\nEJB::ContextId=" + DiagnosticContextHelper.getContextId());
      System.out.println("EJB::isDyedWith(USER1)=" + DiagnosticContextHelper.isDyedWith(DiagnosticContextHelper.USER1));
      System.out.println("EJB::isDyedWith(USER2)=" + DiagnosticContextHelper.isDyedWith(DiagnosticContextHelper.USER2));
      System.out.println("=======================");
      System.out.println("EJB::isDyedWith(DYE_0)=" + DiagnosticContextHelper.isDyedWith(DiagnosticContextHelper.DYE_0));
      System.out.println("EJB::isDyedWith(DYE_1)=" + DiagnosticContextHelper.isDyedWith(DiagnosticContextHelper.DYE_1));
      System.out.println("EJB::isDyedWith(DYE_2)=" + DiagnosticContextHelper.isDyedWith(DiagnosticContextHelper.DYE_2));
    }
    catch (InvalidDyeException e)
    {
      e.printStackTrace();
    }

    System.out.println("===EJB FINISH========================================================================");

    return p1+p2;
  }
}
