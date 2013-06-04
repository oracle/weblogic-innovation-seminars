package com.oracle.demo.ops;

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
 * Date: Feb 20, 2011
 * Time: 8:54:43 AM
 */
public class Constants
{
  public static final String WL_INITIAL_CONTEXT = "weblogic.jndi.WLInitialContextFactory";

  public static final String CONNECTION_FACTORY_JNDI = "com.oracle.demo.ops.jms.cf";

  public static final String EVENT_TOPIC_JNDI = "com.oracle.demo.ops.jms.eventTopic";
  public static final String EVENT_QUEUE_JNDI = "com.oracle.demo.ops.jms.eventQueue";

  public static final String SHIPMENT_QUEUE_JNDI = "com.oracle.demo.ops.jms.shipmentQueue";

  public static final String FOREIGN_EVENT_TOPIC_JNDI = "foreign.com.oracle.demo.ops.jms.eventTopic";
  public static final String FOREIGN_CONNECTION_FACTORY_JNDI = "foreign.com.oracle.demo.ops.jms.cf";
}