package com.alexanderfinn.documentservice.utils;

import java.util.UUID;

/**
 * @author Alexander Finn
 */
public class TokenGenerator {

  public static String getUniqueToken() {
    return UUID.randomUUID().toString();
  }

}
