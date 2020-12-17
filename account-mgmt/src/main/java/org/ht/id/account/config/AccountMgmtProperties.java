package org.ht.id.account.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.time.Duration;
import java.time.format.DateTimeParseException;
import java.util.Date;

@Component
@Getter
public class AccountMgmtProperties {

    @Value("${account-mgmt.configuration.activation.default-link}")
    private String accountActivationLink;

    @Value("${account-mgmt.configuration.activation.expire-period-days}")
    private int activationExpirePeriodDays;

    @Value("${account-mgmt.configuration.invitation.default-link}")
    private String accountInvitationLink;

    @Value("${account-mgmt.configuration.reset-password.default-link}")
    private String resetPasswordLink;

    @Value("${account-mgmt.configuration.activation.expire-period-duration}")
    private String activationExpiryPeriodDuration;

    // Get the expiry time in seconds
    private long getActivationExpiryDuration() {
        try {
            return Duration.parse(activationExpiryPeriodDuration).getSeconds();
        } catch (DateTimeParseException ex) {
            // By default, get from expire-period-days and convert number of days to total of seconds
            return activationExpirePeriodDays * 24 * 3600;
        }
    }

    // Get the expiry of activation datetime
    public Date getActivationExpiryDate() {
        Date currentDate = new Date();
        // Convert the current datetime to miliseconds
        return new Date(currentDate.getTime() + getActivationExpiryDuration() * 1000);
    }
}
