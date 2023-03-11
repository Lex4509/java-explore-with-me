package ru.ewm.service.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShortUserDto {
    private Long id;
    private String name;

    @Override
    public String toString() {
        return "ShortUserDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
