package ru.ewm.service.compilation.general.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.ewm.service.compilation.general.model.Compilation;

import java.util.List;

public interface CompilationRepository extends JpaRepository<Compilation, Long> {
    List<Compilation> findAllByIdIsGreaterThanEqualAndPinnedIs(Long from, boolean pinned, Pageable pageable);
}
