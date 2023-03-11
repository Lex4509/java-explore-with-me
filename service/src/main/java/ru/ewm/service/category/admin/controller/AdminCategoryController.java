package ru.ewm.service.category.admin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.ewm.service.category.general.dto.CreateCategoryDto;
import ru.ewm.service.category.admin.service.AdminCategoryService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(path = "/admin/categories")
@Slf4j
@Validated
@RequiredArgsConstructor
public class AdminCategoryController {
    private final AdminCategoryService adminCategoryService;

    @PostMapping
    public ResponseEntity<Object> addCategory(@RequestBody @NotNull @Valid CreateCategoryDto createCategoryDto) {
        log.info("Create category {}", createCategoryDto);
        return new ResponseEntity<>(adminCategoryService.addCategory(createCategoryDto), HttpStatus.CREATED);
    }

    @DeleteMapping("{catId}")
    public ResponseEntity<Object> deleteCategory(@PathVariable Long catId) {
        log.info("Delete category {}", catId);
        adminCategoryService.deleteCategory(catId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("{catId}")
    public ResponseEntity<Object> updateCategory(@PathVariable Long catId,
                                                 @RequestBody @NotNull @Valid CreateCategoryDto createCategoryDto) {
        log.info("Update category {}", catId);
        return new ResponseEntity<>(adminCategoryService.updateCategory(catId, createCategoryDto), HttpStatus.OK);
    }
}
