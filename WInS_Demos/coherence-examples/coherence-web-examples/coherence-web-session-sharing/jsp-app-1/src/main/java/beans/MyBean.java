package beans;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: jeffrey.a.west
 * Date: 4/4/12
 * Time: 12:08 PM
 */
@Named
@SessionScoped
public class MyBean implements Serializable
{
  private String value;

  public String getValue()
  {
    return value;
  }

  public void setValue(String value)
  {
    this.value = value;
  }
}
