package ru.ewm.service.category.general.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCategoryDto {
    @NotBlank(message = "Blank name")
    private String name;

    @Override
    public String toString() {
        return "CreateCategoryDto{" +
                "name='" + name + '\'' +
                '}';
    }
}
