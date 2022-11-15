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
@Builder
@ToString
public class Gpu implements UsedForFront {
    private Integer cost;
    @StringAnnotation
    private String model;
    @StringAnnotation
    private String manufacture;
    @StringAnnotation
    private String graphicProcessor;
    private Integer memoryCapacity;
    @StringAnnotation
    private String manufactureOfProcessor;
    @StringAnnotation
    private String typeOfMemory;
    @StringAnnotation
    private String connectionInterface;

    public String getTitle() {
        return Configs.GPU_TITLE;
    }

    public List<String> getAttributes() {
        return List.of(
                "Цена",
                "Модель",
                "Производитель",
                "Графический процессор",
                "Объем видопамяти, ГБ",
                "Производитель графического процессора",
                "Тип памяти",
                "Интерфейс подключения"
        );
    }

    public Map<String, String> getAttributesWithValues() {
        return Map.of(
                "Цена", cost.toString(),
                "Модель", model,
                "Производитель", manufacture,
                "Графический процессор", graphicProcessor,
                "Объем видопамяти, ГБ", memoryCapacity.toString(),
                "Производитель графического процессора", manufactureOfProcessor,
                "Тип памяти", typeOfMemory,
                "Интерфейс подключения", connectionInterface
        );
    }
}
