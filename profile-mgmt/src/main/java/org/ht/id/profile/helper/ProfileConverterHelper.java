package org.ht.id.profile.helper;

import org.bson.types.ObjectId;
import org.ht.id.profile.data.model.BasicInfo;
import org.ht.id.profile.data.model.ContactInfo;
import org.ht.id.profile.data.model.DemoGraphicsInfo;
import org.ht.id.profile.data.model.LegalInfo;
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
                             ObjectId htCode) {
        BasicInfo basicInfo = profileMapper.map(basicInfoDto, BasicInfo.class);
        basicInfo.setHtCode(htCode);
        return basicInfo;
    }

    public ContactInfo convert(ContactInfo contactInfoDto, ObjectId htCode) {
        ContactInfo contactInfo = profileMapper.map(contactInfoDto, ContactInfo.class);
        contactInfo.setHtCode(htCode);
        return contactInfo;
    }

    public DemoGraphicsInfo convert(DemoGraphicsInfo demoGraphicsInfoDto) {
        return profileMapper.map(demoGraphicsInfoDto, DemoGraphicsInfo.class);
    }

    public LegalInfo convert(LegalInfo legalInfoDto, ObjectId htCode) {
        LegalInfo legalInfo = profileMapper.map(legalInfoDto, LegalInfo.class);
        legalInfo.setHtCode(htCode);
        return legalInfo;
    }

    public DemoGraphicsInfo convert(DemoGraphicsInfo demoGraphicsInfo, String htId) {
        return profileMapper.map(demoGraphicsInfo, DemoGraphicsInfo.class);
    }
}
