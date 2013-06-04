package com.oracle.example.jms.distributed.topic;

import com.oracle.example.jms.Constants;
import weblogic.jms.extensions.WLConnection;
import weblogic.jms.extensions.WLMessageProducer;
import weblogic.jms.extensions.WLSession;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jms.*;
import java.util.Date;
import java.util.logging.Logger;

/**
 * /*
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
 * Date: 5/1/13
 * Time: 10:52 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "PDTProducerEJB", mappedName = "ejb/PDTProducer")
@LocalBean
public class PDTProducerEJB
{
  public static final String JMS_CF_JNDI = "com/oracle/example/jms/base/cf";
  public static final String JMS_TOPIC_JNDI = "com/oracle/example/jms/base/partitioned-topic";

  private static final Logger logger = Logger.getLogger(PDTProducerEJB.class.getName());

  @Resource(name = JMS_CF_JNDI, type = ConnectionFactory.class)
  private ConnectionFactory connectionFactory;

  @Resource(name = JMS_TOPIC_JNDI, type = Topic.class)
  private Topic topic;

  private WLConnection connection;
  private WLSession session;
  private WLMessageProducer queueProducer;

  @PostConstruct
  public void initialize()
  {
    try
    {
      connection = (WLConnection) connectionFactory.createConnection();
      session = (WLSession) connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
      queueProducer = (WLMessageProducer) session.createProducer(topic);
    }
    catch (JMSException e)
    {
      if (connection != null)
      {
        try
        {
          connection.close();
        }
        catch (JMSException f)
        {
          e.printStackTrace();
        }
      }
    }
  }

  @PreDestroy
  public void closeConnection()
  {
    try
    {
      if (connection != null)
      {
        connection.close();
      }
    }
    catch (JMSException e)
    {
      e.printStackTrace();
    }
  }

  @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
  public void doIt()
  {
    int BATCH_SIZE = 4;
    long sleepTime = 500;
    String batchName=Constants.filenameDateFormatter.format(new Date());

    try
    {
      for (int x = 0; x < BATCH_SIZE; x++)
      {
        queueProducer.send(session.createTextMessage("Batch=[" + batchName + "] Partitioned Distributed Topic!! [ " + x + " / " + BATCH_SIZE + " ]"));

        try
        {
          Thread.sleep(sleepTime);
        }
        catch (InterruptedException ignore)
        {
        }
      }
    }
    catch (JMSException e)
    {
      e.printStackTrace();
    }

  }
}
