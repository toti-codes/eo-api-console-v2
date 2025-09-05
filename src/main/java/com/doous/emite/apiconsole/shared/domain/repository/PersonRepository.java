package com.doous.emite.apiconsole.shared.domain.repository;

import com.doous.emite.apiconsole.shared.domain.model.ConfigLegalPersonEntity;
import com.doous.emite.apiconsole.shared.domain.model.PersonEntity;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.hibernate.reactive.mutiny.Mutiny;

/**
 * Repositorio para operaciones relacionadas con la entidad PersonEntity y ConfigLegalPersonEntity.
 * Proporciona métodos para consultar y persistir entidades legales configuradas.
 */
@ApplicationScoped
public class PersonRepository implements PanacheRepository<PersonEntity> {

  @Inject
  Mutiny.SessionFactory sessionFactory;

  /**
   * Obtiene una entidad ConfigLegalPersonEntity por el CIU de la persona.
   *
   * @param ciu Identificador único de la persona.
   * @return Uni con la entidad ConfigLegalPersonEntity encontrada o null si no existe.
   */
  public Uni<ConfigLegalPersonEntity> getByCiu(String ciu) {
    return find("""
        SELECT DISTINCT c FROM ConfigLegalPersonEntity c
        JOIN FETCH c.person p
        WHERE p.ciu = ?1
        """, ciu).project(ConfigLegalPersonEntity.class).firstResult();
  }

  /**
   * Persiste una entidad ConfigLegalPersonEntity en la base de datos.
   *
   * @param id a persistir.
   * @return Uni con la entidad persistida.
   */
  public Uni<Void> activateAccount(Long id) {
    var sql = "UPDATE config_legal_person SET non_payment = B'0' WHERE idperson = ?1";
    return sessionFactory
        .withTransaction(
            session -> session.createNativeQuery(sql).setParameter(1, id).executeUpdate())
        .onItem().transform(i -> null);
  }
}
