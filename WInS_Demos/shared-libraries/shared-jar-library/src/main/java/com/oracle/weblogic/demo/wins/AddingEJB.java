package com.oracle.weblogic.demo.wins;

import javax.ejb.Stateless;

/**
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
 * Date: 4/18/13
 * Time: 11:11 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless
public class AddingEJB
{
  public AddingEJB()
  {
  }

  public int add(int p1, int p2)
  {
    return p1 + p2;
  }
}
