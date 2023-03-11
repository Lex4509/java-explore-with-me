package ru.ewm.service.category.general.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.ewm.service.category.general.model.Category;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByIdIsGreaterThanEqualOrderByIdAsc(Long from, Pageable pageable);
}
