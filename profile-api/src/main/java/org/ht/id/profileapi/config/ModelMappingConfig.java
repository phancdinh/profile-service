package org.ht.id.profileapi.config;

import org.ht.id.profileapi.dto.request.internal.IdentityInfoRequest;
import org.ht.id.profileapi.dto.request.internal.PassportRequest;
import org.ht.id.profileapi.dto.response.BasicInfoResponse;
import org.ht.id.profileapi.dto.response.internal.CitizenIdentityResponse;
import org.ht.id.profileapi.dto.response.internal.NationalIdentityResponse;
import org.ht.id.profileapi.dto.response.internal.PassportResponse;
import org.ht.id.profile.data.model.BasicInfo;
import org.ht.id.profile.data.model.internal.BaseIdentityDocument;
import org.ht.id.profileapi.dto.request.BasicInfoCreateRequest;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMappingConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);

        modelMapper.addMappings(new PropertyMap<IdentityInfoRequest, BaseIdentityDocument>() {
            @Override
            protected void configure() {
                map().getPermanentAddress().setFullAddress(source.getPermanentAddress());
                map().getHomeTown().setFullAddress(source.getHomeTown());
                map().getIssuedPlace().setFullAddress(source.getIssuedPlace());
                map().getIssuedDate().setFullDate(source.getIssuedDate());
                map().getDob().setFullDate(source.getDob());
            }
        });


        modelMapper.addMappings(new PropertyMap<PassportRequest, BaseIdentityDocument>() {
            @Override
            protected void configure() {
                map().getPob().setFullAddress(source.getPob());
                map().getExpiryDate().setFullDate(source.getExpiryDate());
                map().getIssuedPlace().setFullAddress(source.getIssuedPlace());
                map().getIssuedDate().setFullDate(source.getIssuedDate());
                map().getDob().setFullDate(source.getDob());
            }
        });

        modelMapper.addMappings(new PropertyMap<BaseIdentityDocument, NationalIdentityResponse>() {
            @Override
            protected void configure() {
                map().setDob(source.getDob().getFullDate());
                map().setIssuedDate(source.getIssuedDate().getFullDate());
                map().setHomeTown(source.getHomeTown().getFullAddress());
                map().setIssuedPlace(source.getIssuedPlace().getFullAddress());
                map().setPermanentAddress(source.getPermanentAddress().getFullAddress());
            }
        });

        modelMapper.addMappings(new PropertyMap<BaseIdentityDocument, CitizenIdentityResponse>() {
            @Override
            protected void configure() {
                map().setDob(source.getDob().getFullDate());
                map().setIssuedDate(source.getIssuedDate().getFullDate());
                map().setHomeTown(source.getHomeTown().getFullAddress());
                map().setIssuedPlace(source.getIssuedPlace().getFullAddress());
                map().setPermanentAddress(source.getPermanentAddress().getFullAddress());
            }
        });

        modelMapper.addMappings(new PropertyMap<BaseIdentityDocument, PassportResponse>() {
            @Override
            protected void configure() {
                map().setDob(source.getDob().getFullDate());
                map().setIssuedDate(source.getIssuedDate().getFullDate());
                map().setPob(source.getPob().getFullAddress());
                map().setIssuedPlace(source.getIssuedPlace().getFullAddress());
            }
        });

        modelMapper.addMappings(new PropertyMap<BasicInfoCreateRequest, BasicInfo>() {
            @Override
            protected void configure() {
                map().getPermanentAddress().setFullAddress(source.getPermanentAddress());
                map().getHometown().setFullAddress(source.getHometown());
                map().getPob().setFullAddress(source.getPob());
                map().getDob().setFullDate(source.getDob());
                map().getUserName().setFullName(source.getFullName());
            }
        });

        modelMapper.addMappings(new PropertyMap<BasicInfo, BasicInfoResponse>() {
            @Override
            protected void configure() {
                map().setPermanentAddress(source.getPermanentAddress().getFullAddress());
                map().setHometown(source.getHometown().getFullAddress());
                map().setPob(source.getPob().getFullAddress());
                map().setDob(source.getDob().getFullDate());
                map().setFullName(source.getUserName().getFullName());
            }
        });

        return modelMapper;
    }
}
