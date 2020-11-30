package org.ht.profileapi.helper;

import org.ht.profileapi.constants.DemoGraphicsAttribute;
import org.ht.profileapi.dto.request.DemoGraphicsInfoCreateRequest;
import org.ht.profileapi.dto.request.DemoGraphicsInfoUpdateRequest;
import org.ht.profile.dto.DemoGraphicsInfoDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProfileInfoConverterHelper {

    private final ModelMapper modelMapper;

    public ProfileInfoConverterHelper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public <T> T convertToResponse(Object source, Class<T> destinationType) {
        return modelMapper.map(source, destinationType);
    }

    public <T> T convertToDto(Object source, Class<T> destinationType) {
        return modelMapper.map(source, destinationType);
    }

    public DemoGraphicsInfoDto convert(DemoGraphicsInfoCreateRequest request, DemoGraphicsAttribute attribute) {
        DemoGraphicsInfoDto dto = modelMapper.map(request, DemoGraphicsInfoDto.class);
        dto.setAttribute(attribute.name());
        return dto;
    }

    public DemoGraphicsInfoDto convert(DemoGraphicsInfoUpdateRequest request, DemoGraphicsAttribute attribute) {
        DemoGraphicsInfoDto dto = modelMapper.map(request, DemoGraphicsInfoDto.class);
        dto.setAttribute(attribute.name());
        return dto;
    }
}
