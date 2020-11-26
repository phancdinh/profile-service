package org.ht.profile.helper;

import org.bson.types.ObjectId;
import org.ht.profile.data.model.BasicInfo;
import org.ht.profile.data.model.ContactInfo;
import org.ht.profile.data.model.DemoGraphicsInfo;
import org.ht.profile.data.model.LegalInfo;
import org.ht.profile.dto.request.BasicInfoCreateRequest;
import org.ht.profile.dto.request.ContactInfoCreateRequest;
import org.ht.profile.dto.request.DemoGraphicsInfoCreateRequest;
import org.ht.profile.dto.request.LegalInfoCreateRequest;
import org.ht.profile.dto.response.BasicInfoResponse;
import org.ht.profile.dto.response.ContactInfoResponse;
import org.ht.profile.dto.response.DemoGraphicsInfoResponse;
import org.ht.profile.dto.response.LegalInfoResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProfileConverterHelper {

    private final ModelMapper modelMapper;

    public ProfileConverterHelper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public BasicInfo convert(BasicInfoCreateRequest profileRequest,
                                    ObjectId profileId) {
        BasicInfo basicInfo = modelMapper.map(profileRequest, BasicInfo.class);
        basicInfo.setProfileId(profileId);
        return basicInfo;
    }
    public BasicInfoResponse convert(BasicInfo info,
                                     String htId) {
        BasicInfoResponse response = modelMapper.map(info, BasicInfoResponse.class);
        response.setHtId(htId);
        return response;
    }

    public DemoGraphicsInfo convert(DemoGraphicsInfoCreateRequest request) {
        return modelMapper.map(request, DemoGraphicsInfo.class);
    }

    public DemoGraphicsInfoResponse convert(DemoGraphicsInfo demoGraphicsInfo, String htId) {
        DemoGraphicsInfoResponse demoGraphicsInfoResponse = modelMapper.map(demoGraphicsInfo, DemoGraphicsInfoResponse.class);
        demoGraphicsInfoResponse.setHtId(htId);
        return demoGraphicsInfoResponse;
    }

    public ContactInfoResponse convert(ContactInfo contactInfo, String htId) {
        ContactInfoResponse contactInfoResponse = modelMapper.map(contactInfo, ContactInfoResponse.class);
        contactInfoResponse.setHtId(htId);
        return contactInfoResponse;
    }

    public ContactInfo convert(ContactInfoCreateRequest contactInfoCreateRequest, ObjectId profileId) {
        ContactInfo contactInfo = modelMapper.map(contactInfoCreateRequest, ContactInfo.class);
        contactInfo.setProfileId(profileId);
        return contactInfo;
    }

    public LegalInfo convert(LegalInfoCreateRequest request, ObjectId profileId) {
        LegalInfo creation = modelMapper.map(request, LegalInfo.class);
        creation.setProfileId(profileId);
        return creation;
    }

    public LegalInfoResponse convert(LegalInfo info, String htId) {
        LegalInfoResponse response = modelMapper.map(info, LegalInfoResponse.class);
        response.setHtId(htId);
        return response;
    }
}
