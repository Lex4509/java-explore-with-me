package ru.ewm.service.category.admin.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ewm.service.category.general.dto.CategoryDto;
import ru.ewm.service.category.general.dto.CreateCategoryDto;
import ru.ewm.service.category.general.model.Category;
import ru.ewm.service.category.general.repository.CategoryRepository;
import ru.ewm.service.category.admin.service.AdminCategoryService;
import ru.ewm.service.util.exception.NotFoundException;

import java.util.Optional;

import static ru.ewm.service.category.general.mapper.CategoryMapper.toCategory;
import static ru.ewm.service.category.general.mapper.CategoryMapper.toCategoryDto;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminCategoryServiceImpl implements AdminCategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryDto addCategory(CreateCategoryDto createCategoryDto) {
        Category categoryToSave = toCategory(createCategoryDto);
        return toCategoryDto(categoryRepository.save(categoryToSave));
    }

    @Override
    public void deleteCategory(Long catId) {
        try {
            categoryRepository.deleteById(catId);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(catId, Category.class.getSimpleName());
        }
    }

    @Override
    public CategoryDto updateCategory(Long catId, CreateCategoryDto createCategoryDto) {
        Optional<Category> foundCategory = categoryRepository.findById(catId);
        Category categoryToUpdate = foundCategory
                .orElseThrow(() -> new NotFoundException(catId, Category.class.getSimpleName()));
        categoryToUpdate.setName(createCategoryDto.getName());
        return toCategoryDto(categoryRepository.save(categoryToUpdate));
    }
}
