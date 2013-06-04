package com.oracle.demo.ops.entitymanager;

import com.oracle.demo.ops.domain.Parcel;
import com.oracle.demo.ops.domain.ParcelStatus;

import javax.ejb.Local;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Local
public interface ParcelManager extends Serializable
{
  public Parcel updateParcelStatusById(int id, ParcelStatus status);

  public Collection<Parcel> findAllParcels();

  public Parcel getParcelById(int parcelId);

  public Parcel createParcel(Parcel parcel);

  public List<Parcel> queryParcelsByContents(String inputParcelContents);
}
