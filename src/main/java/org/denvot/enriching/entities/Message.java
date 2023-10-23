package org.denvot.enriching.entities;

import org.denvot.enriching.EnrichmentType;

import java.util.HashMap;
import java.util.Map;

public class Message {
  private final Map<String, String> content;
  private final EnrichmentType enrichmentType;

  public Message(Map<String, String> content, EnrichmentType enrichmentType) {
    this.content = content;
    this.enrichmentType = enrichmentType;
  }

  public Map<String, String> getContent() {
    return content;
  }

  public EnrichmentType getEnrichmentType() {
    return enrichmentType;
  }

  public static Message createCopy(Message message) {
    Map<String, String> copiedContent = new HashMap<>();

    for (String key : message.content.keySet()) {
      copiedContent.put(key, message.content.get(key));
    }

    return new Message(copiedContent, message.getEnrichmentType());
  }
}
