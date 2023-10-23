package org.denvot.enriching;

import org.denvot.enriching.data.HashMapUserRepository;
import org.denvot.enriching.data.User;
import org.denvot.enriching.entities.Message;
import org.denvot.enriching.strategies.MsisdnEnrichmentStrategy;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EndToEndTest {
  @Test
  public void EndToEnd() throws InterruptedException {
    ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>();

    users.put("88005553535", new User("Denis", "Voitenko", "88005553535"));

    HashMapUserRepository usersRepo = new HashMapUserRepository(users);
    EnrichmentService service = new EnrichmentService(List.of(new MsisdnEnrichmentStrategy(usersRepo)));
    List<Message> enrichmentResults = new CopyOnWriteArrayList<>();
    ExecutorService executorService = Executors.newFixedThreadPool(2);
    CountDownLatch latch = new CountDownLatch(2);

    HashMap<String, String> contentA = new HashMap<>();
    contentA.put("msisdn", "ABC");

    HashMap<String, String> contentB = new HashMap<>();
    contentB.put("msisdn", "88005553535");

    List<Message> msgs = List.of(
            new Message(contentB, EnrichmentType.MSISDN),
            new Message(contentA, EnrichmentType.MSISDN)
    );

    for (int i = 0; i < 2; i++) {
      final int msgIndex = i;
      executorService.submit(() -> {
        enrichmentResults.add(service.enrich(msgs.get(msgIndex)));
        latch.countDown();
      });
    }

    latch.await();

    for (Message result : enrichmentResults) {
      Map<String, String> content = result.getContent();
      if (content.get("msisdn").equals("ABC")) {
        assertFalse(content.containsKey("firstName"));
      } else {
        assertTrue(content.containsKey("firstName"));
      }
    }
  }
}
