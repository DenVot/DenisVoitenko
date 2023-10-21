package org.denvot.enriching;

import org.denvot.enriching.entities.Message;
import org.denvot.enriching.strategies.EnrichmentStrategy;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EnrichmentServiceTest {
  @Test
  public void enrichShouldReturnOriginalMsgBecauseMsisdnNotFound() {
    EnrichmentService service = new EnrichmentService(new ArrayList<>());
    Message msg = new Message(new HashMap<>(), EnrichmentType.MSISDN);

     Message result = service.enrich(msg);

     assertEquals(msg, result);
  }

  @Test
  public void enrichShouldReturnOriginalMsgBecauseStrategyNotFoundNotFound() {
    EnrichmentService service = new EnrichmentService(new ArrayList<>());
    HashMap<String, String> content = new HashMap<>();
    content.put("msisdn", "88005553535");
    Message msg = new Message(content, EnrichmentType.MSISDN);

    Message result = service.enrich(msg);

    assertEquals(msg, result);
  }

  @Test
  public void enrichShouldReturnEnrichedMessage() {
    EnrichmentService service = new EnrichmentService(List.of(new EnrichmentStrategy() {
      @Override
      public EnrichmentType getEnrichmentType() {
        return EnrichmentType.MSISDN;
      }

      @Override
      public Message enrich(Message messageToEnrich) {
        return new Message(new HashMap<>(), EnrichmentType.MSISDN);
      }
    }));

    HashMap<String, String> content = new HashMap<>();
    content.put("msisdn", "88005553535");
    Message msg = new Message(content, EnrichmentType.MSISDN);

    Message result = service.enrich(msg);

    assertNotEquals(msg, result);
  }
}
