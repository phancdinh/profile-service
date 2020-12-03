package org.ht.profile.helper;

import org.bson.types.ObjectId;
import org.ht.profile.data.model.BasicInfo;
import org.ht.profile.data.model.ContactInfo;
import org.ht.profile.data.model.DemoGraphicsInfo;
import org.ht.profile.data.model.LegalInfo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ProfileConverterHelper {

    @Resource(name="profileMapper")
    private final ModelMapper profileMapper;

    public ProfileConverterHelper(ModelMapper profileMapper) {
        this.profileMapper = profileMapper;
    }

    public BasicInfo convert(BasicInfo basicInfoDto,
                             ObjectId profileId) {
        BasicInfo basicInfo = profileMapper.map(basicInfoDto, BasicInfo.class);
        basicInfo.setProfileId(profileId);
        return basicInfo;
    }

    public ContactInfo convert(ContactInfo contactInfoDto, ObjectId profileId) {
        ContactInfo contactInfo = profileMapper.map(contactInfoDto, ContactInfo.class);
        contactInfo.setProfileId(profileId);
        return contactInfo;
    }

    public DemoGraphicsInfo convert(DemoGraphicsInfo demoGraphicsInfoDto) {
        return profileMapper.map(demoGraphicsInfoDto, DemoGraphicsInfo.class);
    }

    public LegalInfo convert(LegalInfo legalInfoDto, ObjectId profileId) {
        LegalInfo legalInfo = profileMapper.map(legalInfoDto, LegalInfo.class);
        legalInfo.setProfileId(profileId);
        return legalInfo;
    }

    public <T> T convertToDto(Object source, String htId, Class<T> destinationType) {
        T data = profileMapper.map(source, destinationType);
        return data;
    }

    public DemoGraphicsInfo convert(DemoGraphicsInfo demoGraphicsInfo, String htId) {
        DemoGraphicsInfo dto = profileMapper.map(demoGraphicsInfo, DemoGraphicsInfo.class);
        return dto;
    }
}
