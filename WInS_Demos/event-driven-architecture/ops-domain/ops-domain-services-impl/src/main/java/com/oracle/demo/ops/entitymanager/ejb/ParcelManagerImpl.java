package com.oracle.demo.ops.entitymanager.ejb;

import com.oracle.demo.ops.domain.Parcel;
import com.oracle.demo.ops.domain.ParcelStatus;
import com.oracle.demo.ops.entitymanager.ParcelManager;
import com.oracle.demo.ops.entitymanager.ParcelManagerRemote;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Local(ParcelManager.class)
@Stateless(name = "ParcelManagerBean", mappedName = "ejb/ParcelManager")
public class ParcelManagerImpl
    implements ParcelManager,
               ParcelManagerRemote,
               Serializable
{
  private static final Logger logger = Logger.getLogger(ParcelManagerImpl.class.getName());

  //  @PersistenceContext(unitName = "ops_domain_pu")
  private EntityManager em;

  public Parcel updateParcelStatusById(int id, ParcelStatus status)
  {
    logger.finest("ParcelManagerImpl.updateParcelStatusById(int=[" + id + "], ParcelStatus=[" + status + "])");

    Parcel parcel = em.find(Parcel.class, id);

    if (parcel == null)
    {
      Parcel newParcel = new Parcel();
      newParcel.setId(id);
      newParcel.setShipmentId(0);
      newParcel.setContents("money");

      newParcel.setHeight(1);
      newParcel.setWidth(2);
      newParcel.setLength(3);

      newParcel.setWeight(1);
      newParcel.setParcelStatus(status);
      em.persist(parcel);

      System.out.println("Unable to find parcel!!  Creating new one for demo...");
      return newParcel;
    }

    parcel.setParcelStatus(status);
    return parcel;
  }

  public List<Parcel> findAllParcels()
  {
    logger.finest("ParcelManagerImpl.findAllParcels() - enter");
    List<Parcel> list = em.createQuery("Select p from Parcel p order by p.id").getResultList();
    logger.finest("returning list of parcels with size=[" + list.size() + "] ");

    return list;
  }


  @Override
  public Parcel getParcelById(int parcelId)
  {
    logger.finest(this.getClass() + " Method=[getParcelById] id=[" + parcelId + "]");
    Parcel parcel = null;

    try
    {
      parcel = em.find(Parcel.class, parcelId);
    }
    catch (Exception e)
    {
      logger.log(Level.WARNING, "Error retrieving parcel with ID=[" + parcelId + "]: " + e.getMessage(), e);
      e.printStackTrace();
    }

    if (parcel == null)
    {
      logger.info("Unable to find parcel with id=[" + parcelId + "]");

    }
    else
    {
      logger.info("Found parcel with id=[" + parcelId + "]");
    }

    return parcel;
  }


  @Override
  public Parcel createParcel(Parcel parcel)
  {
    em.persist(parcel);
    com.oracle.demo.ops.domain.ParcelEvent event = new com.oracle.demo.ops.domain.ParcelEvent();
    event.setParcelId(parcel.getId());
    event.setLocation("ORIGIN");
    event.setMessage("New Parcel");
    event.setDate(Calendar.getInstance());
    event.setParcelStatus(ParcelStatus.BILLING_INFO_RECEIVED);
    em.persist(event);
    return parcel;
  }

  public EntityManager getEm()
  {
    return em;
  }

  public void setEm(EntityManager em)
  {
    this.em = em;
  }

  public List<Parcel> queryParcelsByContents(String inputParcelContents)
  {
    Query q = em.createQuery("Select p from Parcel p where p.contents=?1");

    q.setParameter(1, inputParcelContents);

    return q.getResultList();
  }

}
