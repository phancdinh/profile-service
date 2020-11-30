package org.ht.profile.helper;

import org.bson.types.ObjectId;
import org.ht.profile.constants.DemoGraphicsInfoAttribute;
import org.ht.profile.data.exception.DataNotExistingException;
import org.ht.profile.data.model.BasicInfo;
import org.ht.profile.data.model.ContactInfo;
import org.ht.profile.data.model.DemoGraphicsInfo;
import org.ht.profile.data.model.LegalInfo;
import org.ht.profile.dto.BaseDto;
import org.ht.profile.dto.BasicInfoDto;
import org.ht.profile.dto.ContactInfoDto;
import org.ht.profile.dto.DemoGraphicsInfoDto;
import org.ht.profile.dto.LegalInfoDto;
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

    public BasicInfo convert(BasicInfoDto basicInfoDto,
                             ObjectId profileId) {
        BasicInfo basicInfo = profileMapper.map(basicInfoDto, BasicInfo.class);
        basicInfo.setProfileId(profileId);
        return basicInfo;
    }

    public ContactInfo convert(ContactInfoDto contactInfoDto, ObjectId profileId) {
        ContactInfo contactInfo = profileMapper.map(contactInfoDto, ContactInfo.class);
        contactInfo.setProfileId(profileId);
        return contactInfo;
    }

    public DemoGraphicsInfo convert(DemoGraphicsInfoDto demoGraphicsInfoDto) {
        return profileMapper.map(demoGraphicsInfoDto, DemoGraphicsInfo.class);
    }

    public LegalInfo convert(LegalInfoDto legalInfoDto, ObjectId profileId) {
        LegalInfo legalInfo = profileMapper.map(legalInfoDto, LegalInfo.class);
        legalInfo.setProfileId(profileId);
        return legalInfo;
    }

    public <T> T convertToDto(Object source, String htId, Class<T> destinationType) {
        T data = profileMapper.map(source, destinationType);
        if (data instanceof BaseDto) {
            ((BaseDto) data).setHtId(htId);
        }
        return data;
    }

    public DemoGraphicsInfoDto convert(DemoGraphicsInfo demoGraphicsInfo, String htId) {
        DemoGraphicsInfoDto dto = profileMapper.map(demoGraphicsInfo, DemoGraphicsInfoDto.class);
        dto.setAttribute(demoGraphicsInfo.getAttribute().name());
        dto.setHtId(htId);
        return dto;
    }
}
