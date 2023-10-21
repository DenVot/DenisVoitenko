package org.denvot.enriching.data;

import java.util.Optional;

public interface UserRepository {
  Optional<User> findByMsisdn(String msisdn);
  void updateUserByMsisdn(String msisdn, User user);
}
