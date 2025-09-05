package com.doous.emite.apiconsole.shared.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/** Utilidad para convertir objetos a JSON y viceversa usando Jackson. */
public class JsonUtil {
  /**
   * Convierte un objeto Java a su representación JSON en formato String.
   *
   * @param obj Objeto a convertir.
   * @return Cadena JSON representando el objeto.
   * @throws RuntimeException si ocurre un error en la conversión.
   */
  public static String toJson(Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Error converting object to JSON", e);
    }
  }

  /**
   * Convierte un arreglo de bytes JSON a un objeto Java del tipo especificado.
   *
   * @param dataBytes Arreglo de bytes con el JSON.
   * @param valueType Clase del tipo de objeto a convertir.
   * @param <T> Tipo de objeto esperado.
   * @return Objeto Java convertido desde el JSON.
   * @throws RuntimeException si ocurre un error en la conversión.
   */
  public static <T> T fromJson(byte[] dataBytes, Class<T> valueType) {
    try {
      return new ObjectMapper().readValue(dataBytes, valueType);
    } catch (Exception e) {
      throw new RuntimeException("Error converting JSON to object", e);
    }
  }
}
