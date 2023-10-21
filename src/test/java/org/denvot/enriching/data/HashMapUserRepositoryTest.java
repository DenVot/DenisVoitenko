package org.denvot.enriching.data;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.*;

class HashMapUserRepositoryTest {
  @Test
  void findByMsisdn() {
    ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>();

    users.put("88005553535", new User("Denis", "Voitenko", "88005553535"));

    HashMapUserRepository repo = new HashMapUserRepository(users);

    assertNotNull(repo.findByMsisdn("88005553535"));
  }

  @Test
  void updateUserByMsisdn() {
    ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>();
    users.put("88005553535", new User("Denis", "Voitenko", "88005553535"));

    HashMapUserRepository repo = new HashMapUserRepository(users);
    User newUser = new User("A", "A", "88005553535");

    repo.updateUserByMsisdn("88005553535", newUser);

    assertTrue(repo.findByMsisdn("88005553535").isPresent());
    assertEquals(newUser, repo.findByMsisdn("88005553535").get());
  }
}