package org.denvot.enriching;

import org.denvot.enriching.entities.Message;
import org.denvot.enriching.strategies.EnrichmentStrategy;

import java.util.List;

public class EnrichmentService {
  private final List<EnrichmentStrategy> strategies;

  public EnrichmentService(List<EnrichmentStrategy> strategies) {
    this.strategies = strategies;
  }

  public Message enrich(Message message) {
    EnrichmentType enrichmentType = message.getEnrichmentType();

    for (EnrichmentStrategy strategy : strategies) {
      if (strategy.getEnrichmentType() == enrichmentType) {
        return strategy.enrich(message);
      }
    }

    return message;
  }
}
