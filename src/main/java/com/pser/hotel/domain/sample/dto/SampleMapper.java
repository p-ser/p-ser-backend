package com.pser.hotel.domain.sample.dto;

import com.pser.hotel.domain.sample.domain.Sample;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SampleMapper {

    Sample toEntity(SampleCreateRequest request);

    SampleResponse toResponse(Sample sample);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    void updateSampleFromDto(SampleUpdateRequest request, @MappingTarget Sample sample);
}
