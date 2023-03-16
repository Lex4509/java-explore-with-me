package ru.ewm.service.category.admin.service;

import ru.ewm.service.category.general.dto.CategoryDto;
import ru.ewm.service.category.general.dto.CreateCategoryDto;

public interface AdminCategoryService {
    CategoryDto addCategory(CreateCategoryDto createCategoryDto);

    void deleteCategory(Long catId);

    CategoryDto updateCategory(Long catId, CreateCategoryDto createCategoryDto);
}
