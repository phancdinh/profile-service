package org.ht.id.profile.data.service;

import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.ht.id.profile.data.model.ContactInfo;
import org.ht.id.profile.data.model.internal.HierarchyContact;
import org.ht.id.profile.data.repository.ContactInfoRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
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
        ContactInfo.ContactInfoBuilder builder = ContactInfo.builder();
        builder.htCode(htCode);

        if (!StringUtils.isEmpty(primaryEmail)) {
            builder.emails(Collections.singletonList(
                    HierarchyContact.builder()
                            .primary(true)
                            .value(primaryEmail)
                            .verified(false)
                            .build())
            );
        }

        if (!StringUtils.isEmpty(primaryPhone)) {
            builder.phoneNumbers(
                    Collections.singletonList(
                            HierarchyContact.builder()
                                    .value(primaryPhone)
                                    .primary(true)
                                    .verified(false)
                                    .build()
                    )
            );
        }
        ContactInfo contactInfo = builder.build();
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
                emails.add(HierarchyContact.builder()
                        .value(emailStr)
                        .primary(true)
                        .build()
                );
            }
            return contactInfo;
        }).map(contactInfoRepository::save);
    }

    public Optional<HierarchyContact> createContactEmail(ObjectId htCode, String email) {
        HierarchyContact additionalEmail = HierarchyContact.builder()
                .value(email)
                .primary(false)
                .build();

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

