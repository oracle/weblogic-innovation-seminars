package com.oracle.demo.ops.jpa;

import com.oracle.demo.ops.domain.BaseEntity;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Calendar;

public class BaseEntityListener
{

  @PrePersist
  private void prePersist(BaseEntity entity)
  {
    Calendar now = Calendar.getInstance();
    entity.setCreatedDate(now);
    entity.setModifiedDate(now);
  }

  @PreUpdate
  private void preUpdate(BaseEntity entity)
  {
    Calendar now = Calendar.getInstance();
    entity.setModifiedDate(now);
  }
}
