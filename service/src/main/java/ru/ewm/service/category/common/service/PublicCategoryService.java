package ru.ewm.service.category.common.service;

import ru.ewm.service.category.general.dto.CategoryDto;

import java.util.List;

public interface PublicCategoryService {
    List<CategoryDto> getCategories(Long from, Integer size);

    CategoryDto getCategoryById(Long catId);
}
