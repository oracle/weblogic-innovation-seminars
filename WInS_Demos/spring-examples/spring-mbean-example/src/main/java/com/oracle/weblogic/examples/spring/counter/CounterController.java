package com.oracle.weblogic.examples.spring.counter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
 * * Created with IntelliJ IDEA.
 * User: jeffrey.a.west
 * Date: 4/3/12
 * Time: 1:59 PM
 */

@Controller
@RequestMapping(value = "counter")
public class CounterController
{
  @Autowired
  private CounterBean counter;

  @RequestMapping(value = "/", method = RequestMethod.GET)
  @ResponseBody
  public int getValueRoot()
  {
    return counter.getValue();
  }

  @RequestMapping(value = "/value", method = RequestMethod.GET)
  @ResponseBody
  public int getValue()
  {
    return counter.getValue();
  }

  @RequestMapping(value = "/increment", method = RequestMethod.GET)
  @ResponseBody
  public int incrementCounter()
  {
    return counter.increment();
  }

  @RequestMapping(value = "/reset", method = RequestMethod.GET)
  @ResponseBody
  public int resetCounter()
  {
    return counter.reset();
  }

  @RequestMapping(value = "/set/{newValue}", method = RequestMethod.GET)
  @ResponseBody
  public int setValue(@PathVariable("newValue") int pNewValue)
  {
    return counter.setValue(pNewValue);
  }
}
