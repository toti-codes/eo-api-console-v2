package com.doous.emite.apiconsole.nats.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO que representa la información de la cuenta, incluyendo el CIU y los datos de notificación.
 */
@Getter
@Setter
@NoArgsConstructor
public class AccountInfo {
  private String ciu;
  private NotifyData notifyData;
}
