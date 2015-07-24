package com.alexanderfinn.documentservice.configuration;

import com.alexanderfinn.documentservice.metadata.DocumentRepository;
import com.alexanderfinn.documentservice.metadata.InMemoryRepository;

/**
 * @author Alexander Finn
 */
public class Configuration {

  private final static Configuration instance = new Configuration();
  private final InMemoryRepository documentRepository;

  private Configuration() {
    this.documentRepository = new InMemoryRepository();
  }

  public DocumentRepository getDocumentRepository() {
    return this.documentRepository;
  }

  public static Configuration getInstance() {
    return instance;
  }
}
