package com.oracle.demo.ops.test;

import com.oracle.demo.ops.Constants;
import com.oracle.demo.ops.domain.Address;
import com.oracle.demo.ops.domain.Parcel;
import com.oracle.demo.ops.domain.ParcelStatus;
import com.oracle.demo.ops.domain.Shipment;
import com.oracle.demo.ops.jms.StandaloneJmsMessageSender;

import javax.naming.NamingException;

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
 * * Created by IntelliJ IDEA.
 * User: jeffrey.a.west
 * Date: 3/7/11
 * Time: 6:08 PM
 */
public class ShipmentCreatorThread
    implements Runnable
{
  private int count;
  private boolean complete;
  private String senderName;
  private String recipientName;
  private StandaloneJmsMessageSender shipmentSender;
  private StandaloneJmsMessageSender eventSender;

  public ShipmentCreatorThread(int count, String senderName, String recipientName)
  {
    this.count = count;
    this.senderName = senderName;
    this.recipientName = recipientName;

    try
    {
      initializeJmsSender();
    }
    catch (NamingException e)
    {
      complete = true;
      e.printStackTrace();
    }
  }

  private void initializeJmsSender() throws NamingException
  {
    eventSender = new StandaloneJmsMessageSender(
        "t3://10.0.0.51:7101",
        "weblogic",
        "welcome1",
        Constants.CONNECTION_FACTORY_JNDI,
        Constants.EVENT_QUEUE_JNDI);

  }

  @Override
  public void run()
  {
    for (int x = 1; x <= count; x++)
    {
      System.out.println("Thread=[" + Thread.currentThread().getName() + "] Sending shpiment " + x);
      Shipment shipment = generateShipment();
      shipmentSender.forwardShipmentSAFE(shipment);
    }
  }

  private Shipment generateShipment()
  {
    return generateShipment(senderName, recipientName);
  }

  private static Shipment generateShipment(final String senderName, final String recipientName)
  {
    Shipment shipment = new Shipment();

    shipment.setFromAddress(new Address());
    shipment.getFromAddress().setAddressee(senderName);
    shipment.getFromAddress().setAddressLine1("Vernon Park Mall");
    shipment.getFromAddress().setCity("Kinston");
    shipment.getFromAddress().setState("NC");
    shipment.getFromAddress().setPostalCode("28501");

    shipment.setToAddress(new Address());
    shipment.getToAddress().setAddressee(recipientName);
    shipment.getToAddress().setAddressLine1("Southern Methodist University");
    shipment.getToAddress().setCity("Dallas");
    shipment.getToAddress().setState("TX");
    shipment.getToAddress().setPostalCode("75275");

    Parcel p = new Parcel();
    p.setContents("stuff");
    p.setWeight(10);
    p.setHeight(10);
    p.setWidth(10);
    p.setLength(10);
    p.setParcelStatus(ParcelStatus.BILLING_INFO_RECEIVED);
    shipment.getParcels().add(p);

    p = new Parcel();
    p.setContents("more stuff");
    p.setWeight(10);
    p.setHeight(10);
    p.setWidth(10);
    p.setLength(10);
    p.setParcelStatus(ParcelStatus.BILLING_INFO_RECEIVED);
    shipment.getParcels().add(p);

    return shipment;
  }

  public int getCount()
  {
    return count;
  }

  public void setCount(int count)
  {
    this.count = count;
  }

  public boolean isComplete()
  {
    return complete;
  }

  public void setComplete(boolean complete)
  {
    this.complete = complete;
  }

  public String getSenderName()
  {
    return senderName;
  }

  public void setSenderName(String senderName)
  {
    this.senderName = senderName;
  }

  public String getRecipientName()
  {
    return recipientName;
  }

  public void setRecipientName(String recipientName)
  {
    this.recipientName = recipientName;
  }
}
