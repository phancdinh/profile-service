package org.ht.id.account.bizservice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.ht.id.account.config.AccountMgmtProperties;
import org.ht.id.account.data.model.Activation;
import org.ht.id.account.data.service.ActivationDataService;
import org.ht.id.common.exception.DataNotExistingException;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import java.time.Duration;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class ActivationBizService {

    private final ActivationDataService activationDataService;
    private final AccountMgmtProperties accountApiProperties;

    public Activation create(Activation activation) {
        activation.setCreatedAt(new Date());
        activation.setExpiredAt(getActivationExpiryDate());
        return activationDataService.create(activation);
    }

    public Activation update(Activation activation) {
        activation.setConfirmedAt(new Date());
        return activationDataService.update(activation);
    }

    public Activation findById(ObjectId id) {
        return activationDataService.findById(id).orElseThrow(() -> {
            String error = String.format("Activation is not existed with id: %s", id);
            log.error(error);
            throw new DataNotExistingException(error);
        });
    }

    public boolean existedActivation(String email) {
        return activationDataService.findByEmail(email).stream()
                .anyMatch(a -> {
                    Date currentDate = new Date();
                    return currentDate.compareTo(a.getExpiredAt()) < 0;
                });
    }

    public String generateActivationLink(Activation activation) throws DataNotExistingException {
        //generate activation link
        return Optional.of(activation).map(a -> String.format(accountApiProperties.getAccountActivationLink(), a.getId(), getMd5Activation(a))).orElseThrow();
    }

    public boolean isValidActivation(Activation activation, String valueCheck) {
        return isMatchValueCheck(activation, valueCheck) && !isActivationExpired(activation);
    }

    private boolean isMatchValueCheck(Activation activation, String valueCheck) {
        return Optional.of(valueCheck).filter(p -> p.equals(getMd5Activation(activation))).isPresent();
    }

    private String getMd5Activation(Activation activation) {
        return generateMD5(activation.getId().toString(), activation.getHtId(), activation.getCreatedAt().toString());
    }

    private boolean isActivationExpired(Activation activation) {
        Date currentDate = new Date();
        return currentDate.compareTo(activation.getExpiredAt()) > 0;
    }

    private String generateMD5(String... data) {
        StringBuilder builder = new StringBuilder();
        for (String b : data) {
            builder.append(b);
        }
        return DigestUtils.md5DigestAsHex(builder.toString().getBytes());
    }

    // Get the expiry time in seconds
    private long getActivationExpiryDuration() {
        try {
            return Duration.parse(accountApiProperties.getActivationExpiryPeriodDuration()).getSeconds();
        } catch (DateTimeParseException ex) {
            // By default, get from expire-period-days and convert number of days to total of seconds
            return accountApiProperties.getActivationExpirePeriodDays() * 24 * 3600;
        }
    }

    // Get the expiry of activation datetime
    private Date getActivationExpiryDate() {
        Date currentDate = new Date();
        // Convert the current datetime to miliseconds
        return new Date(currentDate.getTime() + getActivationExpiryDuration() * 1000);
    }
}
