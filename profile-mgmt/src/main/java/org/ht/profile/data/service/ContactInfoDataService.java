package org.ht.profile.data.service;

import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.ht.profile.data.model.ContactInfo;
import org.ht.profile.data.model.internal.HierarchyContact;
import org.ht.profile.data.repository.ContactInfoRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.function.Predicate.not;

@Service
@Slf4j
public class ContactInfoDataService {

    private final ContactInfoRepository contactInfoRepository;

    public ContactInfoDataService(ContactInfoRepository contactInfoRepository) {
        this.contactInfoRepository = contactInfoRepository;
    }

    public Optional<ContactInfo> findByHtCode(ObjectId htCode) {
        return Optional.ofNullable(htCode)
                .flatMap(contactInfoRepository::findByHtCode);
    }

    public boolean existsByHtCode(ObjectId htCode) {
        return Optional.ofNullable(htCode)
                .map(contactInfoRepository::existsByHtCode)
                .orElse(false);
    }

    public ContactInfo create(ContactInfo contactInfo) {
        return Optional.ofNullable(contactInfo)
                .filter(not(o -> contactInfoRepository.existsByHtCode(o.getHtCode())))
                .map(contactInfoRepository::insert)
                .orElse(contactInfo);
    }

    public ContactInfo create(ObjectId htCode, String primaryEmail, String primaryPhone) {
        ContactInfo contactInfo = new ContactInfo();
        contactInfo.setHtCode(htCode);

        if (!StringUtils.isEmpty(primaryEmail)) {
            List<HierarchyContact> emails = new ArrayList<>();
            emails.add(new HierarchyContact(primaryEmail, true, null));
            contactInfo.setEmails(emails);
        }

        if (!StringUtils.isEmpty(primaryPhone)) {
            List<HierarchyContact> phones = new ArrayList<>();
            phones.add(new HierarchyContact(primaryPhone, true, null));
            contactInfo.setPhoneNumbers(phones);
        }

        return Optional.of(contactInfo)
                .filter(not(o -> contactInfoRepository.existsByHtCode(o.getHtCode())))
                .map(contactInfoRepository::insert)
                .orElse(contactInfo);
    }
}

