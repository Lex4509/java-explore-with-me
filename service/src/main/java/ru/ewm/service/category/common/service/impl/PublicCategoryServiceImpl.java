package ru.ewm.service.category.common.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ewm.service.category.general.dto.CategoryDto;
import ru.ewm.service.category.general.mapper.CategoryMapper;
import ru.ewm.service.category.general.model.Category;
import ru.ewm.service.category.general.repository.CategoryRepository;
import ru.ewm.service.category.common.service.PublicCategoryService;
import ru.ewm.service.util.exception.NotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.ewm.service.category.general.mapper.CategoryMapper.toCategoryDto;

@Service
@RequiredArgsConstructor
@Transactional
public class PublicCategoryServiceImpl implements PublicCategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryDto> getCategories(Long from, Integer size) {
        PageRequest pageRequest = PageRequest.of(0, size);
        List<Category> foundCategories = categoryRepository
                .findAllByIdIsGreaterThanEqualOrderByIdAsc(from, pageRequest);
        return foundCategories.stream().map(CategoryMapper::toCategoryDto).collect(Collectors.toList());
    }

    @Override
    public CategoryDto getCategoryById(Long catId) {
        Optional<Category> foundCategory = categoryRepository.findById(catId);
        return toCategoryDto(categoryRepository
                .save(foundCategory
                        .orElseThrow(() -> new NotFoundException(catId, Category.class.getSimpleName()))));
    }
}
