package com.oracle.demo.ops.entitymanager;

import com.oracle.demo.ops.domain.Shipment;

import javax.ejb.Local;
import java.io.Serializable;
import java.util.Collection;

@Local
public interface ShipmentManager
    extends Serializable
{
  public Shipment findShipmentById(int id);

  public Shipment findShipmentByExternalId(String id);

  public Collection<Shipment> findAllShipments();

  public Collection<Shipment> findAllShipmentsPaged(int pFirstResult, int pMaxResults);

  public Shipment createShipment(Shipment shipment);
}
