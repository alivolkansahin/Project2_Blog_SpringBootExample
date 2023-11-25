package com.volkans.avsblog.service;

import com.volkans.avsblog.dto.request.CategoryCreateRequestDto;
import com.volkans.avsblog.dto.request.CategoryUpdateRequestDto;
import com.volkans.avsblog.entity.Category;
import com.volkans.avsblog.entity.enums.ECategoryStatus;
import com.volkans.avsblog.exception.AvsBlogException;
import com.volkans.avsblog.exception.ErrorType;
import com.volkans.avsblog.mapper.ICategoryMapper;
import com.volkans.avsblog.repository.CategoryRepository;
import com.volkans.avsblog.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService extends ServiceManager<Category,Long> {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        super(categoryRepository);
        this.categoryRepository = categoryRepository;
    }

    public Boolean existsByName(String name){
        return categoryRepository.existsByName(name);
    }

    public Category getCategoryById(Long id){
        if(!existsById(id)) throw new AvsBlogException(ErrorType.CATEGORY_NOT_FOUND_BY_ID);
        return findById(id).get();
    }

    public List<Category> getAll() {
        List<Category> categories = findAll();
        if(categories.isEmpty()) throw new AvsBlogException(ErrorType.NO_CATEGORIES_EXIST);
        return categories;
    }

    public Category create(CategoryCreateRequestDto dto) {
        if(existsByName(dto.getName().toLowerCase())) throw new AvsBlogException(ErrorType.CATEGORY_NAME_ALREADY_EXISTS);
        Category category = ICategoryMapper.INSTANCE.createDtoToCategory(dto);
        categoryRepository.save(category);
        return category;
    }

    public void deleteCategoryById(Long id) {
        if(!existsById(id)) throw new AvsBlogException(ErrorType.CATEGORY_NOT_FOUND_BY_ID);
        Category category = findById(id).get();
        if(category.getStatus().equals(ECategoryStatus.REMOVED)) throw new AvsBlogException(ErrorType.CATEGORY_ALREADY_DELETED);
        category.setStatus(ECategoryStatus.REMOVED);
        save(category);
    }

    public void updatePostById(Long id, CategoryUpdateRequestDto dto) {
        if(!existsById(id)) throw new AvsBlogException(ErrorType.CATEGORY_NOT_FOUND_BY_ID);
        Category category = findById(id).get();
        if(existsByName(dto.getName().toLowerCase()) && !category.getName().equals(dto.getName().toLowerCase()))
            throw new AvsBlogException(ErrorType.CATEGORY_NAME_ALREADY_EXISTS);
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        saveAndFlush(category);
    }
}
