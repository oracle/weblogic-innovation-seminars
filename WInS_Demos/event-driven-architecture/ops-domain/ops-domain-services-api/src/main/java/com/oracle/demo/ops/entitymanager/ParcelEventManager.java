package com.oracle.demo.ops.entitymanager;

import com.oracle.demo.ops.domain.ParcelEvent;

import java.io.Serializable;
import java.util.List;

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
 * Date: Sep 23, 2011
 * Time: 10:01:06 AM
 */
public interface ParcelEventManager extends Serializable
{
  public ParcelEvent addParcelEvent(final ParcelEvent pEvent);

  public List<ParcelEvent> getParcelLogByParcelId(int pParcelId);

  public List<ParcelEvent> getParcelLogByParcelIdPaged(int pParcelId, int pFirstResult, int pMaxResults);

  public List<ParcelEvent> getAllParcelEvents();

  public List<ParcelEvent> getAllParcelEventsPaged(int pFirstResult, int pMaxResults);

  public ParcelEvent getParcelEventById(int inputParcelEventId);

  public List<ParcelEvent> getParcelEventsByLocation(String inputParcelLocation);

}
