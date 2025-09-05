package com.doous.emite.apiconsole.shared.domain.model;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.Formula;

/**
 * Entidad que representa la configuración legal de una persona. Relacionada uno a uno con
 * PersonEntity.
 */
@Entity
@Table(name = "config_legal_person")
public class ConfigLegalPersonEntity extends PanacheEntityBase {

  @Id
  @Column(name = "idperson")
  public Long id;

  @OneToOne
  @MapsId
  @JoinColumn(name = "idperson", referencedColumnName = "id")
  public PersonEntity person;

  // @Column(name = "non_payment")
  @Formula("CASE WHEN non_payment = '1'::bit THEN true ELSE false END")
  public boolean suspend;
}
