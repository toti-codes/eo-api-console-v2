package com.doous.emite.apiconsole.shared.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class JsonUtilTest {

  @Test
  void testToJson_returnsValidJsonString() {
    Dummy dummy = new Dummy();
    dummy.setId(1);
    dummy.setName("test");
    String json = JsonUtil.toJson(dummy);
    assertTrue(json.contains("\"id\":1"));
    assertTrue(json.contains("\"name\":\"test\""));
  }

  @Test
  void testFromJson_returnsValidObject() {
    String json = "{\"id\":2,\"name\":\"foo\"}";
    Dummy dummy = JsonUtil.fromJson(json.getBytes(), Dummy.class);
    assertEquals(2, dummy.getId());
    assertEquals("foo", dummy.getName());
  }

  @Test
  void testToJson_throwsExceptionOnInvalidObject() {
    assertThrows(RuntimeException.class, () -> JsonUtil.toJson(new Object() {
      // Jackson can't serialize this anonymous object
    }));
  }

  @Test
  void testFromJson_throwsExceptionOnInvalidJson() {
    byte[] invalidJson = "not a json".getBytes();
    assertThrows(RuntimeException.class, () -> JsonUtil.fromJson(invalidJson, Dummy.class));
  }

  // Clase auxiliar para pruebas
  public static class Dummy {
    private int id;
    private String name;

    public int getId() {
      return id;
    }

    public void setId(int id) {
      this.id = id;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }
  }
}
