package org.ht.profile.config;

import org.ht.profile.data.model.internal.BaseIdentityDocument;
import org.ht.profile.dto.request.internal.CitizenIdentityRequest;
import org.ht.profile.dto.request.internal.NationalIdentityRequest;
import org.ht.profile.dto.request.internal.PassportRequest;
import org.ht.profile.dto.response.internal.CitizenIdentityResponse;
import org.ht.profile.dto.response.internal.NationalIdentityResponse;
import org.ht.profile.dto.response.internal.PassportResponse;
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

        modelMapper.addMappings(new PropertyMap<NationalIdentityRequest, BaseIdentityDocument>() {
            @Override
            protected void configure() {
                map().getPermanentAddress().setFullAddress(source.getPermanentAddress());
                map().getHomeTown().setFullAddress(source.getHomeTown());
                map().getIssuedPlace().setFullAddress(source.getIssuedPlace());
                map().getIssuedDate().setFullDate(source.getIssuedDate());
                map().getDob().setFullDate(source.getDob());
            }
        });

        modelMapper.addMappings(new PropertyMap<CitizenIdentityRequest, BaseIdentityDocument>() {
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

        return modelMapper;
    }
}
