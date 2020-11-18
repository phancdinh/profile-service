package org.ht.profile.service;

import org.bson.types.ObjectId;
import org.ht.profile.converter.DemoGraphicsInfoConverter;
import org.ht.profile.dto.request.DemoGraphicsInfoCreateRequest;
import org.ht.profile.dto.request.DemoGraphicsInfoUpdateRequest;
import org.ht.profile.model.DemoGraphicsInfo;
import org.ht.profile.constants.DemoGraphicsInfoAttribute;
import org.ht.profile.repository.DemoGraphicsInfoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class DemoGraphicsInfoService {
    private final DemoGraphicsInfoRepository demoGraphicsInfoRepository;
    private final DemoGraphicsInfoConverter demoGraphicsInfoConverter;

    public DemoGraphicsInfoService(DemoGraphicsInfoRepository demoGraphicsInfoRepository, DemoGraphicsInfoConverter demoGraphicsInfoConverter) {
        this.demoGraphicsInfoRepository = demoGraphicsInfoRepository;
        this.demoGraphicsInfoConverter = demoGraphicsInfoConverter;
    }

    public DemoGraphicsInfo findByHtIdAndAttribute(ObjectId profileId, DemoGraphicsInfoAttribute demoGraphicsInfoAttribute) {
        DemoGraphicsInfo data = demoGraphicsInfoRepository.findByProfileIdAndAttribute(profileId, demoGraphicsInfoAttribute);
        if (data == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Not existed"));
        }
        return data;
    }

    public DemoGraphicsInfo create(ObjectId profileId, DemoGraphicsInfoAttribute demoGraphicsInfoAttribute, DemoGraphicsInfoCreateRequest request) {
        DemoGraphicsInfo existingDemoGraphicsInfo = demoGraphicsInfoRepository.findByProfileIdAndAttribute(profileId, demoGraphicsInfoAttribute);
        if(existingDemoGraphicsInfo != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("Value %s is existed!", demoGraphicsInfoAttribute.toString()));
        }
        DemoGraphicsInfo demoGraphicsInfo = demoGraphicsInfoConverter.convert(request);
        demoGraphicsInfo.setProfileId(profileId);
        demoGraphicsInfo.setAttribute(demoGraphicsInfoAttribute);
        return demoGraphicsInfoRepository.insert(demoGraphicsInfo);
    }

    public DemoGraphicsInfo update(ObjectId profileId, DemoGraphicsInfoAttribute demoGraphicsInfoAttribute, DemoGraphicsInfoUpdateRequest updateRequest) {
        DemoGraphicsInfo existingDemoGraphicsInfo = demoGraphicsInfoRepository.findByProfileIdAndAttribute(profileId, demoGraphicsInfoAttribute);
        if(existingDemoGraphicsInfo == null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("Value %s is not existed for Update!", demoGraphicsInfoAttribute.toString()));
        }
        existingDemoGraphicsInfo.setValue(updateRequest.getValue());
        return demoGraphicsInfoRepository.save(existingDemoGraphicsInfo);
    }

    public void delete(ObjectId profileId, DemoGraphicsInfoAttribute demoGraphicsInfoAttribute) {
        DemoGraphicsInfo existingDemoGraphicsInfo = demoGraphicsInfoRepository.findByProfileIdAndAttribute(profileId, demoGraphicsInfoAttribute);
        if(existingDemoGraphicsInfo == null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("Value is not existed for Remove!"));
        }
        demoGraphicsInfoRepository.deleteByProfileIdAndAttribute(profileId, demoGraphicsInfoAttribute);
    }
}
