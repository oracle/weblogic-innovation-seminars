package com.oracle.demo.ops.event.spring.listener.topic;

import com.oracle.demo.ops.domain.ParcelEvent;
import com.oracle.demo.ops.entitymanager.ParcelEventManager;
import com.oracle.demo.ops.entitymanager.ParcelManager;
import com.oracle.demo.ops.entitymanager.ShipmentManager;

import javax.ejb.EJB;

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
 * Date: Feb 15, 2011
 * Time: 10:19:26 AM
 */
public abstract class AbstractEventHandler
{
  protected ParcelManager parcelManager;
  protected ParcelEventManager parcelEventManager;
  protected ShipmentManager shipmentManager;

  public void handleEvent(ParcelEvent pEvent)
      throws Exception
  {
    handleEventInternal(pEvent);
  }

  protected abstract void handleEventInternal(ParcelEvent pEvent)
      throws Exception;

  public void setParcelManager(ParcelManager parcelManager)
  {
    this.parcelManager = parcelManager;
  }

  public void setShipmentManager(ShipmentManager shipmentManager)
  {
    this.shipmentManager = shipmentManager;
  }

  public void setParcelEventManager(ParcelEventManager parcelEventManager)
  {
    this.parcelEventManager = parcelEventManager;
  }
}
