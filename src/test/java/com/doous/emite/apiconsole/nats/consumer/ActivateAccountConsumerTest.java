package com.doous.emite.apiconsole.nats.consumer;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.doous.emite.apiconsole.nats.domain.dto.AccountInfo;
import com.doous.emite.apiconsole.nats.domain.dto.NotifyData;
import com.doous.emite.apiconsole.shared.domain.model.ConfigLegalPersonEntity;
import com.doous.emite.apiconsole.shared.domain.repository.PersonRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.smallrye.mutiny.Uni;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ActivateAccountConsumerTest {

  @Mock private PersonRepository personRepository;

  private ActivateAccountConsumer consumer;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    consumer = new ActivateAccountConsumer();
    consumer.personRepository = personRepository;
  }

  @Test
  void testConsume_activatesAccountWhenSuspended() throws IOException {
    // Arrange
    var configLegalPersonEntity = new ConfigLegalPersonEntity();
    configLegalPersonEntity.id = 1L;
    configLegalPersonEntity.suspend = true;
    configLegalPersonEntity.person = null;
    String ciu = "12345";
    AccountInfo accountInfo = new AccountInfo();
    accountInfo.setCiu(ciu);
    accountInfo.setNotifyData(new NotifyData());
    byte[] dataBytes = new ObjectMapper().writeValueAsBytes(accountInfo);

    when(personRepository.getByCiu(ciu)).thenReturn(Uni.createFrom().item(configLegalPersonEntity));
    when(personRepository.activateAccount(anyLong())).thenReturn(Uni.createFrom().voidItem());

    // Act
    consumer.consume(dataBytes);

    // Assert
    assertFalse(configLegalPersonEntity.suspend);
    verify(personRepository).activateAccount(configLegalPersonEntity.id);
  }

  @Test
  void testConsume_doesNotActivateIfNotSuspended() throws IOException {
    var configLegalPersonEntity = new ConfigLegalPersonEntity();
    configLegalPersonEntity.id = 1L;
    configLegalPersonEntity.suspend = false;
    String ciu = "12345";
    AccountInfo accountInfo = new AccountInfo();
    accountInfo.setCiu(ciu);
    accountInfo.setNotifyData(new NotifyData());
    byte[] dataBytes = new ObjectMapper().writeValueAsBytes(accountInfo);

    when(personRepository.getByCiu(ciu)).thenReturn(Uni.createFrom().item(configLegalPersonEntity));

    consumer.consume(dataBytes);

    verify(personRepository, never()).activateAccount(configLegalPersonEntity.id);
  }

  /*@Test
  void testConsume_nullEntity() throws IOException {
    String ciu = "12345";
    AccountInfo accountInfo = new AccountInfo();
    accountInfo.setCiu(ciu);
    accountInfo.setNotifyData(new NotifyData());
    byte[] dataBytes = new ObjectMapper().writeValueAsBytes(accountInfo);

    when(personRepository.getByCiu(ciu)).thenReturn(Uni.createFrom().nullItem());

    consumer.consume(dataBytes);

    verify(personRepository, never()).persist(any());
  }*/
}
