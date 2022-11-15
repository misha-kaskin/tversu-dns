package models;

import handlers.Configs;
import handlers.StringAnnotation;
import lombok.*;

import java.util.List;
import java.util.Map;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Ram implements UsedForFront {
    private Integer cost;
    @StringAnnotation
    private String model;
    @StringAnnotation(size = 70)
    private String manufacture;
    @StringAnnotation(size = 80)
    private String typeOfMemory;
    private Integer memoryCapacity;
    private Double frequency;
    private Integer numberOfModules;

    @Override
    public String getTitle() {
        return Configs.RAM_TITLE;
    }

    @Override
    public List<String> getAttributes() {
        return List.of(
                "Цена",
                "Модель",
                "Производитель",
                "Тип памяти",
                "Объём одного модуля памяти, ГБ",
                "Тактовая частота, МГц",
                "Количество модулей в комплекте"
        );
    }

    @Override
    public Map<String, String> getAttributesWithValues() {
        return Map.of(
                "Цена", cost.toString(),
                "Модель", model,
                "Производитель", manufacture,
                "Тип памяти", typeOfMemory,
                "Объём одного модуля памяти, ГБ", memoryCapacity.toString(),
                "Тактовая частота, МГц", frequency.toString(),
                "Количество модулей в комплекте", numberOfModules.toString()
        );
    }
}
