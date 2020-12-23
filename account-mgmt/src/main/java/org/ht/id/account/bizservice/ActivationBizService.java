package org.ht.id.account.bizservice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ht.id.account.config.AccountMgmtProperties;
import org.ht.id.account.config.AccountMgtMessageProperties;
import org.ht.id.account.data.model.Activation;
import org.ht.id.account.data.service.ActivationDataService;
import org.ht.id.common.EncryptUtil;
import org.ht.id.common.exception.DataNotExistingException;
import org.ht.id.common.exception.EncryptFailureException;
import org.springframework.stereotype.Component;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
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
    private final AccountMgtMessageProperties accountMgtMessageProperties;

    public Activation create(Activation activation) {
        activation.setCreatedAt(new Date());
        activation.setExpiredAt(getActivationExpiryDate());
        return activationDataService.create(activation);
    }

    public Activation update(Activation activation) {
        activation.setConfirmedAt(new Date());
        return activationDataService.update(activation);
    }

    public Activation findById(String id) {
        return activationDataService.findById(id).orElseThrow(() -> {
            String error = accountMgtMessageProperties.getMessageWithArgs("validate.activation.not.existed", id);
            log.error(error);
            throw new DataNotExistingException(error);
        });
    }

    public boolean anyMatch(String email) {
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
        try {
            return EncryptUtil.md5(activation.getId().toString(), activation.getHtId(), activation.getCreatedAt().toString());
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            throw new EncryptFailureException("Could not create activation code");
        }
    }

    private boolean isActivationExpired(Activation activation) {
        Date currentDate = new Date();
        return currentDate.compareTo(activation.getExpiredAt()) > 0;
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
