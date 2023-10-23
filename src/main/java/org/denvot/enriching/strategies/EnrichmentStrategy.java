package org.denvot.enriching.strategies;

import org.denvot.enriching.EnrichmentType;
import org.denvot.enriching.entities.Message;

public interface EnrichmentStrategy {
  EnrichmentType getEnrichmentType();
  Message enrich(Message messageToEnrich);
}
