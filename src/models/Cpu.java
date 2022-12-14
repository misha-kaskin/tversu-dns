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
public class Cpu implements UsedForFront {
    private Integer cost;
    @StringAnnotation
    private String model;
    @StringAnnotation
    private String manufacture;
    @StringAnnotation
    private String socket;
    @StringAnnotation
    private String family;
    private Integer numberOfCores;
    private Integer year;
    @StringAnnotation
    private String typeOfMemory;
    private Double frequency;
    private Integer technicalProcess;

    @Override
    public String getTitle() {
        return Configs.CPU_TITLE;
    }

    @Override
    public List<String> getAttributes() {
        return List.of(
                "Цена",
                "Модель",
                "Производитель",
                "Сокет",
                "Семейство процессоров",
                "Количество производительных ядер",
                "Год релиза",
                "Тип памяти",
                "Базовая частота процессора, ГГц",
                "Техпроцесс, нм"
        );
    }

    @Override
    public Map<String, String> getAttributesWithValues() {
        return Map.of(
                "Цена", cost.toString(),
                "Модель", model,
                "Производитель", manufacture,
                "Сокет", socket,
                "Семейство процессоров", family,
                "Количество производительных ядер", numberOfCores.toString(),
                "Год релиза", year.toString(),
                "Тип памяти", typeOfMemory,
                "Базовая частота процессора, ГГц", frequency.toString(),
                "Техпроцесс, нм", technicalProcess.toString()
        );
    }
}
