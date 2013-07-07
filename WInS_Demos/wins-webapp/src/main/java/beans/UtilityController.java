package beans;

import com.oracle.example.utility.DeadlockProducerEJB;
import com.oracle.example.utility.JDBCHoggerEJB;
import com.oracle.example.utility.StuckThreadGeneratorEJB;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.*;
import java.util.logging.Logger;

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
 * User: jeffrey.a.west
 * Date: Jul 27, 2011
 * Time: 10:06:52 AM
 */

@Named
@SessionScoped
public class UtilityController implements Serializable
{
  static final long serialVersionUID = 43L;
  private static final Logger logger = Logger.getLogger(UtilityController.class.getName());

  @EJB
  private DeadlockProducerEJB deadlockProducerEJB;
  @EJB
  private JDBCHoggerEJB jdbcHoggerEJB;
  @EJB
  private StuckThreadGeneratorEJB stuckThreadGeneratorEJB;

  public UtilityController()
  {
  }

  private void messageComplete(String message)
  {
    FacesContext context = FacesContext.getCurrentInstance();

    context.addMessage(null, new FacesMessage(message, message));
  }

  public void demoJdbcHogger(ActionEvent actionEvent)
  {
    logger.info("demoJdbcHogger");
    try
    {
      jdbcHoggerEJB.doIt();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    messageComplete("JDBC Hogger Messages Sent!");
  }

  public void demoStuckThreads(ActionEvent actionEvent)
  {
    logger.info("demoStuckThreads");
    try
    {
      stuckThreadGeneratorEJB.doIt();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    messageComplete("Stuck Thread Generation has begun!");
  }

  public void generateDeadlock(ActionEvent actionEvent)
  {
    logger.info("generateDeadlock");

    try
    {
      deadlockProducerEJB.doIt();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    messageComplete("Deadlock Generation has begun!");
  }

  public HttpSession getSession()
  {
    return (HttpSession) getFacesContext().getExternalContext().getSession(true);
  }

  public String getSessionId()
  {
    return getSession().getId();
  }

  public String getWebLogicServerName()
  {
    return System.getProperty("weblogic.Name");
  }

  public FacesContext getFacesContext()
  {
    return FacesContext.getCurrentInstance();
  }

}
