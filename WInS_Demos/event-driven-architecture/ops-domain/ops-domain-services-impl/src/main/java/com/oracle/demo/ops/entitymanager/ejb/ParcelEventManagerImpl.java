package com.oracle.demo.ops.entitymanager.ejb;

import com.oracle.demo.ops.domain.ParcelEvent;
import com.oracle.demo.ops.entitymanager.ParcelEventManager;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
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
 * Date: Sep 23, 2011
 * Time: 10:29:41 AM
 */
@Local(ParcelEventManager.class)
@Stateless(name = "ParcelEventManagerBean", mappedName = "ejb/ParcelEventManager")
public class ParcelEventManagerImpl implements ParcelEventManager
{
  private static final Logger logger = Logger.getLogger(ParcelManagerImpl.class.getName());

  //  @PersistenceContext(unitName = "ops_domain_pu")
  private EntityManager em;

  @Override
  public ParcelEvent addParcelEvent(final com.oracle.demo.ops.domain.ParcelEvent pEvent)
  {
    logger.finest("Adding ParcelEvent: " + pEvent);
    em.persist(pEvent);
    return pEvent;
  }

  @Override
  public List<com.oracle.demo.ops.domain.ParcelEvent> getParcelLogByParcelId(int pParcelId)
  {
    logger.finest("ParcelManagerImpl.getParcelLogByParcelId(int=[" + pParcelId + "])");
/*

    try
    {
      Thread.sleep(1000);
    }
    catch (InterruptedException ignore)
    {

    }
*/

    Query q = em.createQuery("Select event from ParcelEvent event where event.parcelId = ?1 order by event.date asc");
    q.setParameter(1, pParcelId);
    List<com.oracle.demo.ops.domain.ParcelEvent> list = q.getResultList();

    logger.finest("ParcelManagerImpl.getParcelLogByParcelId(int=[" + pParcelId + "]): Returning list of size=[" + list.size() + "]");

    return list;
  }

  @Override
  public List<com.oracle.demo.ops.domain.ParcelEvent> getParcelLogByParcelIdPaged(int pParcelId, int pFirstResult, int pMaxResults)
  {
    Query q = em.createQuery("Select event from ParcelEvent event where event.parcelId = ?1 order by event.id asc");
    q.setParameter(1, pParcelId);
    q.setFirstResult(pFirstResult);
    q.setMaxResults(pMaxResults);
    List<com.oracle.demo.ops.domain.ParcelEvent> list = q.getResultList();
    return list;
  }

  @Override
  public List<com.oracle.demo.ops.domain.ParcelEvent> getAllParcelEvents()
  {
    return em.createQuery("Select event from ParcelEvent event order by event.parcelId").getResultList();
  }

  @Override
  public List<com.oracle.demo.ops.domain.ParcelEvent> getAllParcelEventsPaged(int pFirstResult, int pMaxResults)
  {
    Query q = em.createQuery("Select event from ParcelEvent event order by event.date");
    q.setFirstResult(pFirstResult);
    q.setMaxResults(pMaxResults);
    return q.getResultList();
  }

  @Override
  public ParcelEvent getParcelEventById(final int id)
  {
    logger.info("ParcelEventManagerImpl.getParcelEventById(int=[" + id + "])");

    ParcelEvent event = em.find(ParcelEvent.class, id);

    return event;
  }

  @Override
  public List<ParcelEvent> getParcelEventsByLocation(final String inputParcelLocation)
  {
    logger.info("ParcelEventManagerImpl.getParcelEventsByLocation(String=[" + inputParcelLocation + "])");

    Query q = em.createQuery("Select event from ParcelEvent event where event.location=?1");
    q.setParameter(1, inputParcelLocation);

    return q.getResultList();
  }
}
