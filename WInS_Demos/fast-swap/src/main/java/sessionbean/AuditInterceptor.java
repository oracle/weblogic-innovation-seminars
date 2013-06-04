package sessionbean;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import javax.ejb.PostActivate;
import javax.ejb.PrePassivate;

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
 * * Interceptor class.  The interceptor method is annotated with the
 *  @AroundInvoke annotation.
 */

public class AuditInterceptor {

  public AuditInterceptor() {}

  @AroundInvoke
  public Object audit(InvocationContext ic) throws Exception {
    System.out.println("Invoking method: "+ic.getMethod());
    return ic.proceed();
  }

  @PostActivate
  public void postActivate(InvocationContext ic) {
    System.out.println("Invoking method: "+ic.getMethod());
  }

  @PrePassivate
  public void prePassivate(InvocationContext ic) {
    System.out.println("Invoking method: "+ic.getMethod());
  }

}
