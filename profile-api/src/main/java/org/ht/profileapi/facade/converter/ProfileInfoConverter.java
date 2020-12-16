package org.ht.profileapi.facade.converter;

import org.ht.common.constant.DemoGraphicsAttribute;
import org.ht.profile.data.model.BasicInfo;
import org.ht.profile.data.model.ContactInfo;
import org.ht.profile.data.model.DemoGraphicsInfo;
import org.ht.profile.data.model.LegalInfo;
import org.ht.profile.data.model.Profile;
import org.ht.profile.data.model.internal.Address;
import org.ht.profile.data.model.internal.AddressContact;
import org.ht.profile.data.model.internal.BaseIdentityDocument;
import org.ht.profile.data.model.internal.HierarchyContact;
import org.ht.profile.data.model.internal.HierarchyDate;
import org.ht.profile.data.model.internal.UserName;
import org.ht.profileapi.dto.request.BasicInfoCreateRequest;
import org.ht.profileapi.dto.request.ContactInfoCreateRequest;
import org.ht.profileapi.dto.request.DemoGraphicsInfoCreateRequest;
import org.ht.profileapi.dto.request.DemoGraphicsInfoUpdateRequest;
import org.ht.profileapi.dto.request.LegalInfoCreateRequest;
import org.ht.profileapi.dto.request.internal.AddressContactRequest;
import org.ht.profileapi.dto.request.internal.HierarchyContactRequest;
import org.ht.profileapi.dto.request.internal.IdentityInfoRequest;
import org.ht.profileapi.dto.request.internal.PassportRequest;
import org.ht.profileapi.dto.response.BasicInfoResponse;
import org.ht.profileapi.dto.response.ContactInfoResponse;
import org.ht.profileapi.dto.response.DemoGraphicsInfoResponse;
import org.ht.profileapi.dto.response.LegalInfoResponse;
import org.ht.profileapi.dto.response.ProfileResponse;
import org.ht.profileapi.dto.response.internal.HierarchyContactResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Component
public class ProfileInfoConverter {

    private final ModelMapper modelMapper;

    public ProfileInfoConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public DemoGraphicsInfo convertToEntity(DemoGraphicsInfoCreateRequest request, DemoGraphicsAttribute attribute) {
        DemoGraphicsInfo info = new DemoGraphicsInfo();
        info.setValue(request.getValue());
        info.setAttribute(attribute);
        return info;
    }

    public DemoGraphicsInfo convertToEntity(DemoGraphicsInfoUpdateRequest updateRequest, DemoGraphicsAttribute attribute) {
        DemoGraphicsInfo info = new DemoGraphicsInfo();
        info.setValue(updateRequest.getValue());
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

    public ProfileResponse convertToResponse(Profile profile) {
        ProfileResponse response = new ProfileResponse();
        response.setHtId(profile.getHtId());
        response.setCreatedAt(profile.getCreatedAt());
        return response;
    }

    public HierarchyContactResponse convertToResponse(HierarchyContact hierarchyContact) {
        HierarchyContactResponse response = new HierarchyContactResponse();
        response.setPrimary(hierarchyContact.isPrimary());
        response.setTags(hierarchyContact.getTags());
        response.setValue(hierarchyContact.getValue());
        response.setVerified(hierarchyContact.isVerified());
        return response;
    }

    public BasicInfo convertToEntity(BasicInfoCreateRequest basicInfoCreateRequest) {
        return Optional.ofNullable(basicInfoCreateRequest)
                .map(createdRequest -> {
                    BasicInfo basicInfo = new BasicInfo();
                    basicInfo.setPermanentAddress(new Address(createdRequest.getPermanentAddress()));
                    basicInfo.setHometown(new Address(createdRequest.getHometown()));
                    basicInfo.setPob(new Address(createdRequest.getPob()));
                    basicInfo.setDob(new HierarchyDate(createdRequest.getDob()));
                    basicInfo.setUserName(new UserName(createdRequest.getFullName()));
                    basicInfo.setNationalities(createdRequest.getNationalities());
                    basicInfo.setGender(createdRequest.getGender());
                    return basicInfo;
                }).orElse(new BasicInfo());
    }

    public LegalInfo convertToEntity(LegalInfoCreateRequest legalInfoCreateRequest) {
        return Optional.ofNullable(legalInfoCreateRequest)
                .map(createdRequest -> {
                    LegalInfo legalInfo = new LegalInfo();
                    legalInfo.setCitizenIdentity(this.convertToEntity(createdRequest.getCitizenIdentity()));
                    legalInfo.setNationalIdentity(this.convertToEntity(createdRequest.getNationalIdentity()));
                    legalInfo.setPassports(this.convertToEntities(createdRequest.getPassports()));
                    return legalInfo;
                }).orElse(new LegalInfo());
    }

    public ContactInfo convertToEntity(ContactInfoCreateRequest contactInfoCreateRequest) {
        return Optional.ofNullable(contactInfoCreateRequest)
                .map(createdRequest -> {
                    ContactInfo contactInfo = new ContactInfo();
                    contactInfo.setPostalAddresses(this.convertToAddresses(createdRequest.getPostalAddresses()));
                    contactInfo.setPhoneNumbers(this.convertToContacts(createdRequest.getPhoneNumbers()));
                    contactInfo.setEmails(this.convertToContacts(createdRequest.getEmails()));
                    return contactInfo;
                }).orElse(new ContactInfo());
    }

    public List<AddressContact> convertToAddresses(List<AddressContactRequest> contactRequests) {
        return Optional.ofNullable(contactRequests)
                .map(list -> list.stream().map(this::convertToEntity).collect(toList()))
                .orElse(Collections.emptyList());
    }


    public List<HierarchyContact> convertToContacts(List<HierarchyContactRequest> contactRequests) {
        return Optional.ofNullable(contactRequests)
                .map(list -> list.stream().map(this::convertToEntity).collect(toList()))
                .orElse(Collections.emptyList());
    }


    public AddressContact convertToEntity(AddressContactRequest request) {
        return Optional.ofNullable(request)
                .map(contactRequest -> {
                    AddressContact addressContact = new AddressContact();
                    addressContact.setPrimary(contactRequest.isPrimary());
                    addressContact.setTags(contactRequest.getTags());
                    addressContact.setFullAddress(contactRequest.getFullAddress());
                    return addressContact;
                })
                .orElse(new AddressContact());
    }

    public HierarchyContact convertToEntity(HierarchyContactRequest request) {
        return Optional.ofNullable(request)
                .map(contactRequest -> {
                    HierarchyContact contact = new HierarchyContact();
                    contact.setPrimary(contactRequest.isPrimary());
                    contact.setTags(contactRequest.getTags());
                    contact.setValue(contactRequest.getValue());
                    return contact;
                })
                .orElse(new HierarchyContact());
    }

    public BaseIdentityDocument convertToEntity(IdentityInfoRequest request) {
        return Optional.ofNullable(request)
                .map(contactRequest -> {
                    BaseIdentityDocument document = new BaseIdentityDocument();
                    document.setNumber(contactRequest.getNumber());
                    document.setFullName(contactRequest.getFullName());
                    document.setGender(contactRequest.getGender());
                    document.setNationality(contactRequest.getNationality());
                    document.setHomeTown(new Address(contactRequest.getHomeTown()));
                    document.setPermanentAddress(new Address(contactRequest.getPermanentAddress()));
                    document.setIssuedPlace(new Address(contactRequest.getIssuedPlace()));
                    document.setDob(new HierarchyDate(contactRequest.getDob()));
                    document.setIssuedDate(new HierarchyDate(contactRequest.getIssuedDate()));
                    return document;
                })
                .orElse(new BaseIdentityDocument());
    }

    public List<BaseIdentityDocument> convertToEntities(List<PassportRequest> requests) {
        return Optional.ofNullable(requests)
                .map(list -> list.stream()
                        .map(this::convertToEntity)
                        .collect(toList()))
                .orElse(Collections.emptyList());
    }

    public BaseIdentityDocument convertToEntity(PassportRequest request) {
        return Optional.ofNullable(request)
                .map(contactRequest -> {
                    BaseIdentityDocument document = new BaseIdentityDocument();
                    document.setNumber(contactRequest.getNumber());
                    document.setFullName(contactRequest.getFullName());
                    document.setGender(contactRequest.getGender());
                    document.setNationality(contactRequest.getNationality());
                    document.setIssuedPlace(new Address(contactRequest.getIssuedPlace()));
                    document.setDob(new HierarchyDate(contactRequest.getDob()));
                    document.setIssuedDate(new HierarchyDate(contactRequest.getIssuedDate()));
                    document.setExpiryDate(new HierarchyDate(contactRequest.getExpiryDate()));
                    document.setPob(new Address(contactRequest.getPob()));
                    return document;
                })
                .orElse(new BaseIdentityDocument());
    }
}