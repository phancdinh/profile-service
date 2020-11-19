package org.ht.profile.helper;

import org.ht.profile.dto.request.DemoGraphicsInfoCreateRequest;
import org.ht.profile.dto.response.BasicInfoResponse;
import org.ht.profile.dto.request.BasicInfoCreateRequest;
import org.ht.profile.dto.response.DemoGraphicsInfoResponse;
import org.ht.profile.model.BasicInfo;
import org.ht.profile.model.DemoGraphicsInfo;
import org.ht.profile.model.internal.Address;
import org.ht.profile.model.internal.HierarchyDate;
import org.ht.profile.model.internal.UserName;
import org.springframework.beans.BeanUtils;
import java.util.Date;

public class ProfileConverterHelper {
    static public BasicInfo convert(BasicInfoCreateRequest profileRequest) {
        BasicInfo basicInfo = new BasicInfo();
        BeanUtils.copyProperties(profileRequest, basicInfo);
        return basicInfo;
    }

    static public BasicInfoResponse convert(BasicInfo basicInfo) {
        BasicInfoResponse profileResponse = new BasicInfoResponse();
        BeanUtils.copyProperties(basicInfo, profileResponse);
        profileResponse.setHometown(basicInfo.getHometown().getFullAddress());
        profileResponse.setPermanentAddress(basicInfo.getPermanentAddress().getFullAddress());
        profileResponse.setPob(basicInfo.getPob().getFullAddress());
        profileResponse.setDob(basicInfo.getDob().getFullDate());
        profileResponse.setFullName(basicInfo.getUserName().getFullName());
        return profileResponse;
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
