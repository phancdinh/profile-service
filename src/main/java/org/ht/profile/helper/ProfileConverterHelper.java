package org.ht.profile.helper;

import org.bson.types.ObjectId;
import org.ht.profile.data.model.BasicInfo;
import org.ht.profile.data.model.DemoGraphicsInfo;
import org.ht.profile.data.model.internal.Address;
import org.ht.profile.data.model.internal.AddressContact;
import org.ht.profile.data.model.internal.HierarchyContact;
import org.ht.profile.data.model.internal.HierarchyDate;
import org.ht.profile.data.model.internal.UserName;
import org.ht.profile.dto.request.BasicInfoCreateRequest;
import org.ht.profile.dto.request.ContactInfoCreateRequest;
import org.ht.profile.dto.request.DemoGraphicsInfoCreateRequest;
import org.ht.profile.dto.request.internal.AddressContactRequest;
import org.ht.profile.dto.request.internal.HierarchyContactRequest;
import org.ht.profile.dto.response.ContactInfoResponse;
import org.ht.profile.dto.response.DemoGraphicsInfoResponse;
import org.ht.profile.data.model.ContactInfo;
import org.ht.profile.dto.response.internal.AddressContactResponse;
import org.ht.profile.dto.response.internal.HierarchyContactResponse;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    static public DemoGraphicsInfoResponse convert(DemoGraphicsInfo demoGraphicsInfo, String htId) {
        DemoGraphicsInfoResponse demoGraphicsInfoResponse = new DemoGraphicsInfoResponse();
        BeanUtils.copyProperties(demoGraphicsInfo, demoGraphicsInfoResponse);
        demoGraphicsInfoResponse.setHtId(htId);
        return demoGraphicsInfoResponse;
    }

    static public ContactInfoResponse convert(ContactInfo contactInfo, String htId) {
        ContactInfoResponse contactInfoResponse = new ContactInfoResponse();
        BeanUtils.copyProperties(contactInfo, contactInfoResponse);
        List<AddressContactResponse> responses = contactInfo.getPostalAddresses().stream().map(AddressContactResponse::new).collect(Collectors.toList());
        contactInfoResponse.setPostalAddresses(responses);
        contactInfoResponse.setHtId(htId);
        return contactInfoResponse;
    }

    static public ContactInfo convert(ContactInfoCreateRequest contactInfoCreateRequest, ObjectId profileId) {
        ContactInfo contactInfo = new ContactInfo();
        contactInfo.setEmails(contactInfoCreateRequest.getEmails().stream().map(ProfileConverterHelper::convert).collect(Collectors.toList()));
        contactInfo.setPhoneNumbers(contactInfoCreateRequest.getPhoneNumbers().stream().map(ProfileConverterHelper::convert).collect(Collectors.toList()));
        contactInfo.setPostalAddresses(contactInfoCreateRequest.getPostalAddresses().stream().map(ProfileConverterHelper::convert).collect(Collectors.toList()));
        contactInfo.setProfileId(profileId);
        return contactInfo;
    }

    static public AddressContact convert(AddressContactRequest request) {
        AddressContact addressContact = new AddressContact();
        addressContact.setTags(request.getTags());
        addressContact.setPrimary(request.isPrimary());
        addressContact.setFullAddress(request.getValue());
        return addressContact;
    }

    static public HierarchyContact convert(HierarchyContactRequest request) {
        HierarchyContact hierarchyContact = new HierarchyContact();
        hierarchyContact.setTags(request.getTags());
        hierarchyContact.setPrimary(request.isPrimary());
        hierarchyContact.setValue(request.getValue());
        return hierarchyContact;
    }
}
