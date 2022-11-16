package models;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CartDto {
    private String model;
    private Integer number;
    private Integer cost;
}
