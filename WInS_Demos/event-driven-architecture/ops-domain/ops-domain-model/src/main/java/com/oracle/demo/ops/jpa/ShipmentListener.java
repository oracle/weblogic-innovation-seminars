package com.oracle.demo.ops.jpa;


import com.oracle.demo.ops.domain.Parcel;
import com.oracle.demo.ops.domain.Shipment;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class ShipmentListener
{

  @PrePersist
  private void prePersist(Shipment shipment)
  {
    setParcelShipmentId(shipment);
  }

  @PreUpdate
  private void preUpdate(Shipment shipment)
  {
    setParcelShipmentId(shipment);
  }

  private void setParcelShipmentId(Shipment shipment)
  {
    for (Parcel parcel : shipment.getParcels())
    {
      if (parcel.getShipmentId() == 0)
      {
        parcel.setShipmentId(shipment.getId());
      }
    }
  }

}
