package com.doous.emite.apiconsole.shared.domain.model;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/** Entidad que representa una persona en el sistema. */
@Entity
@Table(name = "person")
public class PersonEntity extends PanacheEntity {

  @Column(name = "code_type_identity")
  public String codeTypeIdentity;

  public String ciu;

  public String address;

  @Column(name = "date_create")
  public Long createdAt;
}
