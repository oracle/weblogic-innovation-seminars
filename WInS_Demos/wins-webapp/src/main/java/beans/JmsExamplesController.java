package beans;

import com.oracle.example.jms.distributed.queue.QueueProducerEJB;
import com.oracle.example.jms.distributed.topic.ClearScreenEJB;
import com.oracle.example.jms.distributed.topic.PDTProducerEJB;
import com.oracle.example.jms.distributed.topic.RDTProducerEJB;
import com.oracle.example.jms.saf.SAFServerProducerEJB;
import com.oracle.example.jms.uoo.UOOProducerEJB;
import com.oracle.example.jms.uow.UOWProducerEJB;

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
public class JmsExamplesController implements Serializable
{
  static final long serialVersionUID = 43L;
  private static final Logger logger = Logger.getLogger(JmsExamplesController.class.getName());

  @EJB
  private QueueProducerEJB queueProducerEJB;
  @EJB
  private ClearScreenEJB clearScreenEJB;
  @EJB
  private PDTProducerEJB pdtProducerEJB;
  @EJB
  private RDTProducerEJB rdtProducerEJB;
  @EJB
  private UOOProducerEJB uooProducerEJB;
  @EJB
  private UOWProducerEJB uowProducerEJB;
  @EJB
  private SAFServerProducerEJB safProducerEJB;

  public JmsExamplesController()
  {
  }

  private void messageComplete(String demoName)
  {
    FacesContext context = FacesContext.getCurrentInstance();
    String message = demoName + " Demo Complete/Successful";
    context.addMessage(null, new FacesMessage(message, message));
  }


  public void queueDemo(ActionEvent actionEvent)
  {
    queueProducerEJB.doIt();
    messageComplete("Distributed Queue");
  }

  public void pdtDemo(ActionEvent actionEvent)
  {
    pdtProducerEJB.doIt();
    messageComplete("Partitioned Distributed Topic");
  }

  public void rdtDemo(ActionEvent actionEvent)
  {
    rdtProducerEJB.doIt();
    messageComplete("Replicated Distributed Topic");
  }

  public void safDemo(ActionEvent actionEvent)
  {
    safProducerEJB.doIt();
    messageComplete("Store and Forward");
  }
  public void uooDemoMixed(ActionEvent actionEvent)
  {
    uooProducerEJB.sendMixedUOO();
    messageComplete("Mixed Unit of Order");
  }

  public void uooDemoDiscrete(ActionEvent actionEvent)
  {
    uooProducerEJB.sendDiscreteUOO();
    messageComplete("Discrete Unit of Order");
  }

  public void uooDemoNonUOO(ActionEvent actionEvent)
  {
    uooProducerEJB.sendRegularMessageBatch();
    messageComplete("Non-Unit of Order");
  }

  public void uowDemoNonUOW(ActionEvent actionEvent)
  {
    uowProducerEJB.sendNonUOW();
    messageComplete("Non-Unit of Work");
  }

  public void uowDemoMixed(ActionEvent actionEvent)
  {
    uowProducerEJB.sendMixedUOW();
    messageComplete("Mixed Unit of Work");
  }

  public void uowDemoDiscrete(ActionEvent actionEvent)
  {
    uowProducerEJB.sendDiscreteUOW();
    messageComplete("Discrete Unit of Work");
  }

  public void uowDemoIncomplete(ActionEvent actionEvent)
  {
    uowProducerEJB.sendIncompleteUOW();
    messageComplete("Incomplete Unit of Work");
  }

  public void uowDemoSlow(ActionEvent actionEvent)
  {
    uowProducerEJB.sendSlowUOW();
    messageComplete("Slow Unit of Work");
  }
}
