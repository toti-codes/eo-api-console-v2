package com.doous.emite.apiconsole.nats.consumer;

import com.doous.emite.apiconsole.nats.domain.dto.AccountInfo;
import com.doous.emite.apiconsole.shared.domain.repository.PersonRepository;
import com.doous.emite.apiconsole.shared.util.JsonUtil;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.io.IOException;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Incoming;

/**
 * Consumer que escucha eventos para activar cuentas a través de NATS. Procesa los datos recibidos y
 * activa la cuenta si corresponde.
 */
@Slf4j
@ApplicationScoped
public class ActivateAccountConsumer {

  @Inject PersonRepository personRepository;

  /**
   * Procesa el mensaje recibido en el canal "data" para activar una cuenta.
   *
   * @param dataBytes Datos en formato byte[] que representan la información de la cuenta.
   * @throws IOException Si ocurre un error al deserializar los datos.
   */
  @WithTransaction
  @Incoming("activate-account")
  public Uni<Void> consume(byte[] dataBytes) throws IOException {

    var data = JsonUtil.fromJson(dataBytes, AccountInfo.class);

    System.out.println("Received data: " + data.getCiu());

    this.personRepository
        .getByCiu(data.getCiu())
        .subscribe()
        .with(
            configLegalPersonEntity -> {
              log.info("LegalPersonEntity: {}", configLegalPersonEntity);

              Optional.ofNullable(configLegalPersonEntity)
                  .ifPresent(
                      personEntity -> {
                        if (personEntity.suspend) {
                          personEntity.suspend = false;

                          this.personRepository
                              .activateAccount(personEntity.id)
                              .subscribe()
                              .with(
                                  persisted -> {
                                    log.info(
                                        "Account with CIU {} has been activated.", data.getCiu());

                                    // Aquí puedes agregar cualquier lógica adicional que necesites
                                    // después de activar la cuenta
                                  });
                        }
                      });
            });

    return Uni.createFrom().voidItem();
  }
}
