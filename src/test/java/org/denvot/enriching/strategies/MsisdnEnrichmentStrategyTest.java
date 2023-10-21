package org.denvot.enriching.strategies;

import org.denvot.enriching.EnrichmentType;
import org.denvot.enriching.data.User;
import org.denvot.enriching.data.UserRepository;
import org.denvot.enriching.entities.Message;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MsisdnEnrichmentStrategyTest {

  @Test
  void getEnrichmentType() {
    UserRepository mockedRepo = mock(UserRepository.class);
    MsisdnEnrichmentStrategy strategy = new MsisdnEnrichmentStrategy(mockedRepo);

    assertEquals(EnrichmentType.MSISDN, strategy.getEnrichmentType());
  }

  @Test
  void enrichShouldReturnEnrichedMsg() {
    UserRepository mockedRepo = mock(UserRepository.class);
    MsisdnEnrichmentStrategy strategy = new MsisdnEnrichmentStrategy(mockedRepo);
    HashMap<String, String> content = new HashMap<>();

    content.put("msisdn", "88005553535");

    Message msg = new Message(content, EnrichmentType.MSISDN);

    when(mockedRepo.findByMsisdn("88005553535"))
            .thenReturn(Optional.of(new User("Denis", "Voitenko", "88005553535")));

    Message result = strategy.enrich(msg);

    assertNotEquals(msg, result);
  }
}