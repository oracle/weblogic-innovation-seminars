package beans;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
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
 *
 * This code is provided under the following licenses:
 *
 * GNU General Public License (GPL-2.0)
 * COMMON DEVELOPMENT AND DISTRIBUTION LICENSE Version 1.0 (CDDL-1.0)
 *
 * <p/>
 * ****************************************************************************
 * User: jeffrey.a.west
 * Date: Jul 27, 2011
 * Time: 10:06:52 AM
 */

@ManagedBean
@SessionScoped
public class SessionController implements Serializable
{
  static final long serialVersionUID = 43L;
  private static final Logger logger = Logger.getLogger(SessionController.class.getName());
  private String inputKeyName;
  private String inputArrayLength;

  public SessionController()
  {
  }

  public void removeSelectedAttribute(ActionEvent pEvent)
  {

  }


  public void removeEntry(String pKey)
  {
    getSession().removeAttribute(pKey);
  }

  public void addNewEntry()
  {
    int value = 0;

    try
    {
      value = Integer.parseInt(inputArrayLength);
      getSession().setAttribute(inputKeyName, getIntegerArray(value));
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }

    inputKeyName = "";
    inputArrayLength = "";
  }

  private int[] getIntegerArray(int size)
  {
    int[] array = new int[size];
    for (int x = 0; x < size; x++)
    {
      array[x] = x;
    }
    return array;
  }

  public String getInputKeyName()
  {
    return inputKeyName;
  }

  public void setInputKeyName(String inputKeyName)
  {
    this.inputKeyName = inputKeyName;
  }

  public String getInputArrayLength()
  {
    return inputArrayLength;
  }

  public void setInputArrayLength(String inputArrayLength)
  {
    this.inputArrayLength = inputArrayLength;
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
    FacesContext ctx = FacesContext.getCurrentInstance();

    //ctx.get

    return ctx;
  }


  public List<Map.Entry> getSessionAttributes()
  {
    List<Map.Entry> atts = new ArrayList();

    Enumeration<String> attributes = getSession().getAttributeNames();

    //logger.info("Session Attributes:");
    //logger.info("======================================");
    while (attributes.hasMoreElements())
    {
      String att = attributes.nextElement();

      if (att.equals("com.sun.faces.renderkit.ServerSideStateHelper.LogicalViewMap")
          || att.equals("javax.faces.request.charset"))
      {
        continue;
      }

      Object value = getSession().getAttribute(att);
      atts.add(new AbstractMap.SimpleEntry(att, value));
      //logger.info("Att name=[" + att + "] ObjectType=[" + value.getClass() + "] value=[" + value + "]");
    }
    //logger.info("======================================");

    return atts;
  }

  public String invalidateSession()
  {
    invalidate();
    return "/index";
  }

  public void invalidateSessionListener(ActionEvent event)
  {
    invalidate();
  }

  private void invalidate()
  {
    logger.info("INVALIDATING SESSION!! ID=[" + getSessionId() + "]");

    getSession().invalidate();
  }


  public void invalidateSessionActionListener(ActionEvent event)
  {
    invalidateSession();
    getFacesContext().addMessage(null, new FacesMessage("Session Invalidated", "User Request"));
  }


}
