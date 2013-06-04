import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.lang.instrument.Instrumentation;

/**
 * Created by IntelliJ IDEA.
 * User: jeffrey.a.west
 * Date: 3/28/12
 * Time: 4:27 PM
 */
@Named(value = "Controller")
@SessionScoped
public class Controller implements Serializable
{
  public String showMessage()
  {

    System.out.println("SHOWMESSAGE");
    return "showMessage";
  }
}
