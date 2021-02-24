package com.safetynet.alerts.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class UtilsTest {

  private UtilsTest() {
    throw new IllegalStateException("Utility class");
  }

  /**
   * This method will transform an object to a json String. It will be used for the test
   *
   * @param obj the object we want to transform
   * @return the Json String
   */
  public static String asJsonString(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new IllegalStateException("Utility class");
    }
  }
}
