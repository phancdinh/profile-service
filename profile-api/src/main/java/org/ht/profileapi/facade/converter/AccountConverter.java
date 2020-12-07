package org.ht.profileapi.facade.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AccountConverter {
    private final ModelMapper modelMapper;

    public AccountConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public <T> T convertToEntity(Object source, Class<T> destinationType) {
        return modelMapper.map(source, destinationType);
    }

    public <T> T convertToResponse(Object source, Class<T> destinationType) {
        return modelMapper.map(source, destinationType);
    }
}
