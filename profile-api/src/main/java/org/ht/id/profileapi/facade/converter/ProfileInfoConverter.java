package org.ht.id.profileapi.facade.converter;

import org.ht.id.common.constant.DemoGraphicsAttribute;
import org.ht.id.profileapi.dto.request.internal.IdentityInfoRequest;
import org.ht.id.profileapi.dto.response.ProfileResponse;
import org.ht.id.profile.data.model.BasicInfo;
import org.ht.id.profile.data.model.ContactInfo;
import org.ht.id.profile.data.model.DemoGraphicsInfo;
import org.ht.id.profile.data.model.LegalInfo;
import org.ht.id.profile.data.model.Profile;
import org.ht.id.profile.data.model.internal.Address;
import org.ht.id.profile.data.model.internal.AddressContact;
import org.ht.id.profile.data.model.internal.BaseIdentityDocument;
import org.ht.id.profile.data.model.internal.HierarchyContact;
import org.ht.id.profile.data.model.internal.HierarchyDate;
import org.ht.id.profile.data.model.internal.UserName;
import org.ht.id.profileapi.dto.request.BasicInfoCreateRequest;
import org.ht.id.profileapi.dto.request.ContactInfoCreateRequest;
import org.ht.id.profileapi.dto.request.DemoGraphicsInfoCreateRequest;
import org.ht.id.profileapi.dto.request.DemoGraphicsInfoUpdateRequest;
import org.ht.id.profileapi.dto.request.LegalInfoCreateRequest;
import org.ht.id.profileapi.dto.request.internal.AddressContactRequest;
import org.ht.id.profileapi.dto.request.internal.HierarchyContactRequest;
import org.ht.id.profileapi.dto.request.internal.PassportRequest;
import org.ht.id.profileapi.dto.response.BasicInfoResponse;
import org.ht.id.profileapi.dto.response.ContactInfoResponse;
import org.ht.id.profileapi.dto.response.DemoGraphicsInfoResponse;
import org.ht.id.profileapi.dto.response.LegalInfoResponse;
import org.ht.id.profileapi.dto.response.internal.AddressContactResponse;
import org.ht.id.profileapi.dto.response.internal.CitizenIdentityResponse;
import org.ht.id.profileapi.dto.response.internal.HierarchyContactResponse;
import org.ht.id.profileapi.dto.response.internal.NationalIdentityResponse;
import org.ht.id.profileapi.dto.response.internal.PassportResponse;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Component
public class ProfileInfoConverter {

    public DemoGraphicsInfo convertToEntity(DemoGraphicsInfoCreateRequest request, DemoGraphicsAttribute attribute) {
        DemoGraphicsInfo.DemoGraphicsInfoBuilder builder = DemoGraphicsInfo.builder();
        return Optional.ofNullable(request)
                .map(r -> builder
                        .attribute(attribute)
                        .value(r.getValue())
                ).orElse(builder)
                .build();
    }

    public DemoGraphicsInfo convertToEntity(DemoGraphicsInfoUpdateRequest updateRequest, DemoGraphicsAttribute attribute) {
        DemoGraphicsInfo.DemoGraphicsInfoBuilder builder = DemoGraphicsInfo.builder();
        return Optional.ofNullable(updateRequest)
                .map(r -> builder
                        .attribute(attribute)
                        .value(r.getValue())
                ).orElse(builder)
                .build();
    }

    public BasicInfoResponse convertToResponse(BasicInfo source, String htId) {
        BasicInfoResponse.BasicInfoResponseBuilder builder = BasicInfoResponse.builder();
        return Optional.ofNullable(source)
                .map(info -> builder
                        .htId(htId)
                        .gender(info.getGender())
                        .nationalities(info.getNationalities())
                        .dob(convertToResponse(info.getDob()))
                        .fullName(convertToResponse(info.getUserName()))
                        .pob(convertToResponse(info.getPob()))
                        .hometown(convertToResponse(info.getHometown()))
                        .permanentAddress(convertToResponse(info.getPermanentAddress()))
                        .lastModifiedDate(info.getLastModifiedDate())
                )
                .orElse(builder)
                .build();
    }

    private String convertToResponse(UserName userName) {
        return Optional.of(userName.getFullName()).orElse("");
    }

    public ContactInfoResponse convertToResponse(ContactInfo source, String htId) {
        ContactInfoResponse.ContactInfoResponseBuilder builder = ContactInfoResponse.builder();
        return Optional.ofNullable(source)
                .map(l -> builder
                        .htId(htId)
                        .postalAddresses(convertToAddressResponses(l.getPostalAddresses()))
                        .phoneNumbers(convertToResponses(l.getPhoneNumbers()))
                        .emails(convertToResponses(l.getEmails()))
                        .lastModifiedDate(l.getLastModifiedDate())
                )
                .orElse(builder)
                .build();
    }


    public List<AddressContactResponse> convertToAddressResponses(List<AddressContact> sources) {
        return Optional.ofNullable(sources)
                .map(list -> list.stream()
                        .map(this::convertToResponse)
                        .collect(toList()))
                .orElse(Collections.emptyList());
    }

    public AddressContactResponse convertToResponse(AddressContact source) {
        AddressContactResponse.AddressContactResponseBuilder builder = AddressContactResponse.builder();
        return Optional.ofNullable(source)
                .map(l -> builder
                        .fullAddress(source.getFullAddress())
                        .primary(source.isPrimary())
                        .tags(source.getTags())
                )
                .orElse(builder)
                .build();
    }

    public LegalInfoResponse convertToResponse(LegalInfo source, String htId) {
        LegalInfoResponse.LegalInfoResponseBuilder builder = LegalInfoResponse.builder();
        return Optional.ofNullable(source)
                .map(l -> builder
                        .htId(htId)
                        .nationalIdentity(convertToResponse(l.getNationalIdentity()))
                        .citizenIdentity(convertToCitizenResponse(l.getCitizenIdentity()))
                        .passports(convertToPassportResponses(l.getPassports()))
                        .lastModifiedDate(l.getLastModifiedDate())
                )
                .orElse(builder)
                .build();
    }

    public List<PassportResponse> convertToPassportResponses(List<BaseIdentityDocument> documents) {
        return Optional.ofNullable(documents)
                .map(list -> list.stream()
                        .map(this::convertToPassportResponse)
                        .collect(toList()))
                .orElse(Collections.emptyList());

    }

    public PassportResponse convertToPassportResponse(BaseIdentityDocument baseIdentityDocument) {
        PassportResponse.PassportResponseBuilder builder = PassportResponse.passportResponseBuilder();
        return Optional.ofNullable(baseIdentityDocument)
                .map(d -> builder
                        .number(d.getNumber())
                        .fullName(d.getFullName())
                        .gender(d.getGender())
                        .nationality(d.getNationality())
                        .issuedPlace(convertToResponse(d.getIssuedPlace()))
                        .dob(convertToResponse(d.getDob()))
                        .issuedDate(convertToResponse(d.getIssuedDate()))
                        .pob(convertToResponse(d.getPob()))
                        .expiryDate(convertToResponse(d.getExpiryDate()))
                ).orElse(builder)
                .build();
    }

    public NationalIdentityResponse convertToResponse(BaseIdentityDocument baseIdentityDocument) {
        NationalIdentityResponse.NationalIdentityResponseBuilder builder = NationalIdentityResponse.nationalIdentityResponseBuilder();
        return Optional.ofNullable(baseIdentityDocument)
                .map(d -> builder
                        .number(d.getNumber())
                        .fullName(d.getFullName())
                        .gender(d.getGender())
                        .nationality(d.getNationality())
                        .issuedPlace(convertToResponse(d.getIssuedPlace()))
                        .dob(convertToResponse(d.getDob()))
                        .issuedDate(convertToResponse(d.getIssuedDate()))
                        .homeTown(convertToResponse(d.getHomeTown()))
                        .permanentAddress(convertToResponse(d.getPermanentAddress()))
                ).orElse(builder)
                .build();
    }

    public CitizenIdentityResponse convertToCitizenResponse(BaseIdentityDocument baseIdentityDocument) {
        CitizenIdentityResponse.CitizenIdentityResponseBuilder builder = CitizenIdentityResponse.citizenIdentityResponseBuilder();
        return Optional.ofNullable(baseIdentityDocument)
                .map(d -> builder
                        .number(d.getNumber())
                        .fullName(d.getFullName())
                        .gender(d.getGender())
                        .nationality(d.getNationality())
                        .issuedPlace(convertToResponse(d.getIssuedPlace()))
                        .dob(convertToResponse(d.getDob()))
                        .issuedDate(convertToResponse(d.getIssuedDate()))
                        .homeTown(convertToResponse(d.getHomeTown()))
                        .permanentAddress(convertToResponse(d.getPermanentAddress()))
                ).orElse(builder)
                .build();
    }

    private Date convertToResponse(HierarchyDate dob) {
        return Optional.of(dob)
                .map(HierarchyDate::getFullDate)
                .orElse(null);
    }

    public String convertToResponse(Address address) {
        return Optional.of(address)
                .map(Address::getFullAddress)
                .orElse(null);
    }


    public DemoGraphicsInfoResponse convertToResponse(DemoGraphicsInfo source, String htId) {
        DemoGraphicsInfoResponse.DemoGraphicsInfoResponseBuilder builder = DemoGraphicsInfoResponse.builder();
        return Optional.ofNullable(source)
                .map(p -> builder
                        .htId(htId)
                        .value(source.getValue())
                        .lastModifiedDate(source.getLastModifiedDate())
                ).orElse(builder)
                .build();
    }

    public ProfileResponse convertToResponse(Profile profile) {
        ProfileResponse.ProfileResponseBuilder builder = ProfileResponse.builder();
        return Optional.ofNullable(profile)
                .map(p -> builder
                        .htId(p.getHtId())
                        .createdAt(profile.getCreatedAt())
                ).orElse(builder)
                .build();
    }

    public List<HierarchyContactResponse> convertToResponses(List<HierarchyContact> sources) {
        return Optional.ofNullable(sources)
                .map(list -> list.stream()
                        .map(this::convertToResponse)
                        .collect(toList()))
                .orElse(Collections.emptyList());
    }


    public HierarchyContactResponse convertToResponse(HierarchyContact hierarchyContact) {
        HierarchyContactResponse.HierarchyContactResponseBuilder builder = HierarchyContactResponse.builder();
        return Optional.ofNullable(hierarchyContact)
                .map(h ->
                        builder.primary(h.isPrimary())
                                .tags(h.getTags())
                                .value(h.getValue())
                                .verified(h.isVerified())
                ).orElse(builder)
                .build();
    }

    public BasicInfo convertToEntity(BasicInfoCreateRequest basicInfoCreateRequest) {
        BasicInfo.BasicInfoBuilder builder = BasicInfo.builder();
        return Optional.ofNullable(basicInfoCreateRequest)
                .map(createdRequest -> builder
                        .permanentAddress(Address.builder().fullAddress(createdRequest.getPermanentAddress()).build())
                        .hometown(Address.builder().fullAddress(createdRequest.getHometown()).build())
                        .pob(Address.builder().fullAddress(createdRequest.getPob()).build())
                        .dob(HierarchyDate.builder().fullDate(createdRequest.getDob()).build())
                        .userName(UserName.builder().fullName(createdRequest.getFullName()).build())
                        .nationalities(createdRequest.getNationalities())
                        .gender(createdRequest.getGender()))
                .orElse(builder)
                .build();
    }

    public LegalInfo convertToEntity(LegalInfoCreateRequest legalInfoCreateRequest) {
        LegalInfo.LegalInfoBuilder builder = LegalInfo.builder();
        return Optional.ofNullable(legalInfoCreateRequest)
                .map(createdRequest -> builder
                        .citizenIdentity(this.convertToEntity(createdRequest.getCitizenIdentity()))
                        .nationalIdentity(this.convertToEntity(createdRequest.getNationalIdentity()))
                        .passports(this.convertToEntities(createdRequest.getPassports()))
                )
                .orElse(builder)
                .build();
    }

    public ContactInfo convertToEntity(ContactInfoCreateRequest contactInfoCreateRequest) {
        ContactInfo.ContactInfoBuilder builder = ContactInfo.builder();
        return Optional.ofNullable(contactInfoCreateRequest)
                .map(createdRequest -> builder
                        .postalAddresses(this.convertToAddresses(createdRequest.getPostalAddresses()))
                        .phoneNumbers(this.convertToContactEntities(createdRequest.getPhoneNumbers()))
                        .emails(this.convertToContactEntities(createdRequest.getEmails()))
                )
                .orElse(builder)
                .build();
    }

    public List<AddressContact> convertToAddresses(List<AddressContactRequest> contactRequests) {
        return Optional.ofNullable(contactRequests)
                .map(list -> list.stream().map(this::convertToEntity).collect(toList()))
                .orElse(Collections.emptyList());
    }


    public List<HierarchyContact> convertToContactEntities(List<HierarchyContactRequest> contactRequests) {
        return Optional.ofNullable(contactRequests)
                .map(list -> list.stream().map(this::convertToEntity).collect(toList()))
                .orElse(Collections.emptyList());
    }


    public AddressContact convertToEntity(AddressContactRequest request) {
        AddressContact.AddressContactBuilder builder = AddressContact.addressContactBuilder();
        return Optional.ofNullable(request)
                .map(contactRequest -> builder
                        .fullAddress(contactRequest.getFullAddress())
                        .tags(contactRequest.getTags())
                        .primary(contactRequest.isPrimary())
                )
                .orElse(builder)
                .build();
    }

    public HierarchyContact convertToEntity(HierarchyContactRequest request) {
        HierarchyContact.HierarchyContactBuilder builder = HierarchyContact.builder();
        return Optional.ofNullable(request)
                .map(contactRequest -> builder
                        .primary(contactRequest.isPrimary())
                        .tags(contactRequest.getTags())
                        .value(contactRequest.getValue())
                )
                .orElse(builder)
                .build();
    }

    public BaseIdentityDocument convertToEntity(IdentityInfoRequest request) {
        BaseIdentityDocument.BaseIdentityDocumentBuilder builder = BaseIdentityDocument.builder();
        return Optional.ofNullable(request)
                .map(contactRequest -> builder
                        .number(contactRequest.getNumber())
                        .fullName(contactRequest.getFullName())
                        .gender(contactRequest.getGender())
                        .nationality(contactRequest.getNationality())
                        .homeTown(Address.builder().fullAddress(contactRequest.getHomeTown()).build())
                        .permanentAddress(Address.builder().fullAddress(contactRequest.getPermanentAddress()).build())
                        .issuedPlace(Address.builder().fullAddress(contactRequest.getIssuedPlace()).build())
                        .dob(HierarchyDate.builder().fullDate(contactRequest.getDob()).build())
                        .issuedDate(HierarchyDate.builder().fullDate(contactRequest.getIssuedDate()).build())
                )
                .orElse(builder)
                .build();
    }

    public List<BaseIdentityDocument> convertToEntities(List<PassportRequest> requests) {
        return Optional.ofNullable(requests)
                .map(list -> list.stream()
                        .map(this::convertToEntity)
                        .collect(toList()))
                .orElse(Collections.emptyList());
    }

    public BaseIdentityDocument convertToEntity(PassportRequest request) {
        BaseIdentityDocument.BaseIdentityDocumentBuilder builder = BaseIdentityDocument.builder();
        return Optional.ofNullable(request)
                .map(contactRequest -> builder
                        .number(contactRequest.getNumber())
                        .fullName(contactRequest.getFullName())
                        .gender(contactRequest.getGender())
                        .nationality(contactRequest.getNationality())
                        .issuedPlace(Address.builder().fullAddress(contactRequest.getIssuedPlace()).build())
                        .dob(HierarchyDate.builder().fullDate(contactRequest.getDob()).build())
                        .issuedDate(HierarchyDate.builder().fullDate(contactRequest.getIssuedDate()).build())
                        .expiryDate(HierarchyDate.builder().fullDate(contactRequest.getExpiryDate()).build())
                        .pob(Address.builder().fullAddress(contactRequest.getPob()).build())
                )
                .orElse(builder).build();
    }
}
