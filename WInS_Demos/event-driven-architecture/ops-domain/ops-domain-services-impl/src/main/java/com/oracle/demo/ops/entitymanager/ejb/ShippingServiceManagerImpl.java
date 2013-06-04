package com.oracle.demo.ops.entitymanager.ejb;

import com.oracle.demo.ops.domain.ShippingService;
import com.oracle.demo.ops.entitymanager.ShippingServiceManager;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
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
 * Date: Sep 15, 2011
 * Time: 10:03:33 AM
 */
@Local(ShippingServiceManager.class)
@Stateless(name = "ShippingServiceManagerBean", mappedName = "ejb/ShippingServiceManager")
public class ShippingServiceManagerImpl implements ShippingServiceManager
{
  //  @PersistenceContext(unitName = "ops_domain_pu")
  private EntityManager em;

  @Override
  public List<ShippingService> findAllShippingServices()
  {
    return em.createQuery("select s from ShippingService s").getResultList();
  }

  @Override
  public void addShippingService(ShippingService newShippingService)
  {
    em.persist(newShippingService);
  }

  @Override
  public void updateService(ShippingService selectedShippingService)
  {
    em.merge(selectedShippingService);
  }

  @Override
  public boolean warmCache()
  {
    // not applicable
    return true;
  }
}
