package com.oracle.demo.ops.entitymanager;

import com.oracle.demo.ops.domain.Parcel;
import com.oracle.demo.ops.domain.ParcelStatus;

import java.util.Collection;

public interface ParcelManagerRemote
{
  public Parcel updateParcelStatusById(int id, ParcelStatus status);

  public Collection<Parcel> findAllParcels();

  Parcel getParcelById(int parcelId);

  Parcel createParcel(Parcel parcel);
}
