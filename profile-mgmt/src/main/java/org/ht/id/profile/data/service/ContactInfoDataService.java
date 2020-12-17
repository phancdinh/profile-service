package org.ht.id.profile.data.service;

import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.ht.id.profile.data.model.ContactInfo;
import org.ht.id.profile.data.model.internal.HierarchyContact;
import org.ht.id.profile.data.repository.ContactInfoRepository;
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
            emails.add(new HierarchyContact(primaryEmail, true, null, false));
            contactInfo.setEmails(emails);
        }

        if (!StringUtils.isEmpty(primaryPhone)) {
            List<HierarchyContact> phones = new ArrayList<>();
            phones.add(new HierarchyContact(primaryPhone, true, null, false));
            contactInfo.setPhoneNumbers(phones);
        }

        return Optional.of(contactInfo)
                .filter(not(o -> contactInfoRepository.existsByHtCode(o.getHtCode())))
                .map(contactInfoRepository::insert)
                .orElse(contactInfo);
    }

    public void updatePrimaryEmail(ObjectId htCode, String emailStr) {
        if (StringUtils.isEmpty(emailStr)) {
            return;
        }
        Optional<ContactInfo> contactInfoOptional = findByHtCode(htCode);
        contactInfoOptional.map(contactInfo -> {
            boolean existed = false;
            List<HierarchyContact> emails = contactInfo.getEmails();
            for (HierarchyContact email : emails) {
                email.setPrimary(false);
                if (email.getValue().equals(emailStr)) {
                    email.setPrimary(true);
                    existed = true;
                }
            }
            if (!existed) {
                emails.add(new HierarchyContact(emailStr, true));
            }
            return contactInfo;
        }).map(contactInfoRepository::save);
    }

    public Optional<HierarchyContact> createContactEmail(ObjectId htCode, String email) {
        HierarchyContact additionalEmail = new HierarchyContact(email, false);

        var updatedContact = Optional.ofNullable(htCode)
                .flatMap(this::findByHtCode)
                .filter(not(m -> m.getEmails().stream().anyMatch(e -> e.getValue().equalsIgnoreCase(email))))
                .map(contactInfo -> {
                    contactInfo.getEmails().add(additionalEmail);
                    return contactInfo;
                })
                .map(contactInfoRepository::save)
                .orElseThrow();

        return updatedContact.getEmails()
                .stream()
                .filter(e -> e.getValue().equalsIgnoreCase(email))
                .findFirst();
    }

    public List<ContactInfo> findByEmailAndPrimary(String email) {
        return contactInfoRepository.findByEmailAndPrimary(email);
    }
}

