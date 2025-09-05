package com.doous.emite.apiconsole.nats.domain.dto;

import java.util.Map;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** DTO para notificaciones, contiene información sobre el template, destinatarios y parámetros. */
@Getter
@Setter
@NoArgsConstructor
public class NotifyData {
  private String template;
  private String[] to;
  private String[] bcc;
  private Map<String, Object> params;
}
