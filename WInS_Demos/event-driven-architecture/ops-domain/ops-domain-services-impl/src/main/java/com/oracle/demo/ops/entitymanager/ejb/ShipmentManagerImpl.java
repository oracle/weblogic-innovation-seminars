package com.oracle.demo.ops.entitymanager.ejb;

import com.oracle.demo.ops.domain.*;
import com.oracle.demo.ops.entitymanager.ShipmentManager;
import com.oracle.demo.ops.entitymanager.ShipmentManagerRemote;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

@Local(ShipmentManager.class)
@Stateless(name = "ShipmentManagerBean", mappedName = "ejb/ShipmentManager")
public class ShipmentManagerImpl
    implements ShipmentManager,
               ShipmentManagerRemote,
               Serializable
{
  //  @PersistenceContext(unitName = "ops_domain_pu")
  private EntityManager em;

  public Shipment findShipmentById(int id)
  {
    return em.find(Shipment.class, id);
  }

  public Shipment findShipmentByExternalId(String id)
  {
    return (Shipment) em.createNamedQuery("Shipment.findByExternalReferenceId").setParameter("id", id).getSingleResult();
  }

  public Collection<Shipment> findAllShipments()
  {
    return em.createQuery("select s from Shipment s order by s.id").getResultList();
  }

  @Override
  public Collection<Shipment> findAllShipmentsPaged(int pFirstResult, int pMaxResults)
  {
    Query q = em.createQuery("select s from Shipment s order by s.id");
    q.setFirstResult(pFirstResult);
    q.setMaxResults(pMaxResults);

    return q.getResultList();
  }

  @Override
  public Shipment createShipment(Shipment shipment)
  {

    List<Parcel> parcels = shipment.getParcels();

    for (Parcel parcel : parcels)
    {
      //em.persist(parcel);

      com.oracle.demo.ops.domain.ParcelEvent event = new com.oracle.demo.ops.domain.ParcelEvent();
      event.setParcelId(parcel.getId());
      event.setLocation("ORIGIN");
      event.setMessage("New Parcel");
      event.setDate(Calendar.getInstance());
      event.setParcelStatus(ParcelStatus.BILLING_INFO_RECEIVED);

      //em.merge(listener);
    }

    System.out.println("FromAddressId=" + shipment.getFromAddress());
    System.out.println("ToAddressId=" + shipment.getToAddress());

    em.persist(shipment);
    return shipment;
  }


}