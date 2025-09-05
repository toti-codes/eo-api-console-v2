package com.doous.emite.apiconsole.shared.domain.model;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * Entidad que representa una persona jurídica en el sistema. Relacionada uno a uno con
 * PersonEntity.
 */
@Entity
@Table(name = "person")
public class LegalPersonEntity extends PanacheEntityBase {

  @Id
  @Column(name = "idperson")
  public Long id;

  @OneToOne
  @MapsId
  @JoinColumn(name = "idperson", referencedColumnName = "id")
  public PersonEntity person;

  public String name;

  @Column(name = "isclient")
  public boolean isClient;
}
