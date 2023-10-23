package org.denvot.enriching.data;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class HashMapUserRepository implements UserRepository {
  private final ConcurrentHashMap<String, User> users;

  public HashMapUserRepository(ConcurrentHashMap<String, User> users) {
    this.users = users;
  }

  @Override
  public Optional<User> findByMsisdn(String msisdn) {
    if (!users.containsKey(msisdn)) return Optional.empty();

    return Optional.of(users.get(msisdn));
  }

  @Override
  public void updateUserByMsisdn(String msisdn, User user) {
    users.put(msisdn, user);
  }
}
