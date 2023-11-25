package com.volkans.avsblog.mapper;


import com.volkans.avsblog.dto.request.CategoryCreateRequestDto;
import com.volkans.avsblog.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ICategoryMapper {

    ICategoryMapper INSTANCE = Mappers.getMapper(ICategoryMapper.class);

    Category createDtoToCategory(CategoryCreateRequestDto dto);

}
