package org.denvot.enriching.strategies;

import org.denvot.enriching.EnrichmentType;
import org.denvot.enriching.data.User;
import org.denvot.enriching.data.UserRepository;
import org.denvot.enriching.entities.Message;

import java.util.Map;
import java.util.Optional;

public class MsisdnEnrichmentStrategy implements EnrichmentStrategy {
  private final String MSISDN_KEY = "msisdn";
  private final String FIRSTNAME_KEY = "firstName";
  private final String LASTNAME_KEY = "lastName";

  private final UserRepository userRepository;

  public MsisdnEnrichmentStrategy(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public EnrichmentType getEnrichmentType() {
    return EnrichmentType.MSISDN;
  }

  @Override
  public Message enrich(Message messageToEnrich) {
    String msisdn = messageToEnrich.getContent().get(MSISDN_KEY);

    if (msisdn == null) return messageToEnrich;

    Optional<User> associatedUserOpt = userRepository.findByMsisdn(msisdn);

    if (associatedUserOpt.isEmpty()) return messageToEnrich;

    User associatedUser = associatedUserOpt.get();
    Message enrichedMsg = Message.createCopy(messageToEnrich);
    Map<String, String> msgContent = enrichedMsg.getContent();

    msgContent.put(FIRSTNAME_KEY, associatedUser.firstName);
    msgContent.put(LASTNAME_KEY, associatedUser.lastName);

    return enrichedMsg;
  }
}
