package com.oracle.demo.ops.entitymanager;

import com.oracle.demo.ops.domain.Shipment;

import java.util.Collection;

public interface ShipmentManagerRemote
{
  public Shipment findShipmentById(int id);

  public Shipment findShipmentByExternalId(String id);

  public Collection<Shipment> findAllShipments();

  public Collection<Shipment> findAllShipmentsPaged(int pFirstResult, int pMaxResults);

  public Shipment createShipment(Shipment shipment);
}
