package org.ht.id.profile.bizservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
@Component
@Slf4j
public class IdGeneratorBizService {
    private long getUnsignedInt(long x) {
        return x & (-1L >>> 32);
    }

    public String generateId() {
        final int NUMBERS_OF_DIGITS = 16;
        long firstHashCode = UUID.randomUUID().hashCode();
        long secondHashCode = UUID.randomUUID().hashCode();
        return String.format("%s%s", getUnsignedInt(firstHashCode), getUnsignedInt(secondHashCode)).substring(0, NUMBERS_OF_DIGITS);
    }
}
