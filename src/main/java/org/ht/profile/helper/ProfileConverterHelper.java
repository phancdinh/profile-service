package org.ht.profile.helper;

import org.bson.types.ObjectId;
import org.ht.profile.data.model.BasicInfo;
import org.ht.profile.data.model.DemoGraphicsInfo;
import org.ht.profile.data.model.internal.Address;
import org.ht.profile.data.model.internal.HierarchyDate;
import org.ht.profile.data.model.internal.UserName;
import org.ht.profile.dto.request.BasicInfoCreateRequest;
import org.ht.profile.dto.request.DemoGraphicsInfoCreateRequest;
import org.ht.profile.dto.response.DemoGraphicsInfoResponse;
import org.springframework.beans.BeanUtils;

import java.util.Date;

public class ProfileConverterHelper {
    static public BasicInfo convert(BasicInfoCreateRequest profileRequest,
                                    ObjectId profileId) {
        BasicInfo basicInfo = new BasicInfo();
        BeanUtils.copyProperties(profileRequest, basicInfo);
        basicInfo.setProfileId(profileId);
        basicInfo.setPob(ProfileConverterHelper.convert(profileRequest.getPob()));
        basicInfo.setPermanentAddress(ProfileConverterHelper.convert(profileRequest.getPermanentAddress()));
        basicInfo.setHometown(ProfileConverterHelper.convert(profileRequest.getHometown()));
        basicInfo.setDob(ProfileConverterHelper.convert(profileRequest.getDob()));
        basicInfo.setUserName(ProfileConverterHelper.convertUserName(profileRequest.getFullName()));
        return basicInfo;
    }

    static public Address convert(String fullAddress) {
        Address address = new Address();
        address.setFullAddress(fullAddress);
        return address;
    }

    static public HierarchyDate convert(Date date) {
        HierarchyDate hierarchyDate = new HierarchyDate();
        hierarchyDate.setFullDate(date);
        return hierarchyDate;
    }

    static public UserName convertUserName(String fullName) {
        UserName userName = new UserName();
        userName.setFullName(fullName);
        return userName;
    }

    //Converter DemoGraphics Info
    static public DemoGraphicsInfo convert(DemoGraphicsInfoCreateRequest request) {
        DemoGraphicsInfo demoGraphicsInfo = new DemoGraphicsInfo();
        BeanUtils.copyProperties(request, demoGraphicsInfo);
        return demoGraphicsInfo;
    }

    static public DemoGraphicsInfoResponse convert(DemoGraphicsInfo demoGraphicsInfo) {
        DemoGraphicsInfoResponse demoGraphicsInfoResponse = new DemoGraphicsInfoResponse();
        BeanUtils.copyProperties(demoGraphicsInfo, demoGraphicsInfoResponse);
        return demoGraphicsInfoResponse;
    }
}
