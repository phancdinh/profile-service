package org.ht.profileapi.facade.converter;

import org.ht.common.constant.DemoGraphicsAttribute;
import org.ht.profile.data.model.BasicInfo;
import org.ht.profile.data.model.ContactInfo;
import org.ht.profile.data.model.DemoGraphicsInfo;
import org.ht.profile.data.model.LegalInfo;
import org.ht.profileapi.dto.request.DemoGraphicsInfoCreateRequest;
import org.ht.profileapi.dto.request.DemoGraphicsInfoUpdateRequest;
import org.ht.profileapi.dto.response.BasicInfoResponse;
import org.ht.profileapi.dto.response.ContactInfoResponse;
import org.ht.profileapi.dto.response.DemoGraphicsInfoResponse;
import org.ht.profileapi.dto.response.LegalInfoResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProfileInfoConverter {

    private final ModelMapper modelMapper;

    public ProfileInfoConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public <T> T convertToEntity(Object source, Class<T> destinationType) {
        return modelMapper.map(source, destinationType);
    }

    public DemoGraphicsInfo convert(DemoGraphicsInfoCreateRequest request, DemoGraphicsAttribute attribute) {
        DemoGraphicsInfo info = new DemoGraphicsInfo();
        info.setValue(request.getValue());
        info.setAttribute(attribute);
        return info;
    }

    public DemoGraphicsInfo convert(DemoGraphicsInfoUpdateRequest request, DemoGraphicsAttribute attribute) {
        DemoGraphicsInfo info = new DemoGraphicsInfo();
        info.setValue(request.getValue());
        info.setAttribute(attribute);
        return info;
    }


    public BasicInfoResponse convertToResponse(BasicInfo source, String htId) {
        BasicInfoResponse response = modelMapper.map(source, BasicInfoResponse.class);
        response.setHtId(htId);
        return response;
    }

    public ContactInfoResponse convertToResponse(ContactInfo source, String htId) {
        ContactInfoResponse response = modelMapper.map(source, ContactInfoResponse.class);
        response.setHtId(htId);
        return response;
    }
    public LegalInfoResponse convertToResponse(LegalInfo source, String htId) {
        LegalInfoResponse response = modelMapper.map(source, LegalInfoResponse.class);
        response.setHtId(htId);
        return response;
    }
    public DemoGraphicsInfoResponse convertToResponse(DemoGraphicsInfo source, String htId) {
        DemoGraphicsInfoResponse response = modelMapper.map(source, DemoGraphicsInfoResponse.class);
        response.setHtId(htId);
        return response;
    }
}
