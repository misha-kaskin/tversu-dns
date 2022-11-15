package handlers;

public interface Configs {
    String DB_URL = "jdbc:postgresql://localhost:5432/tversu_dns";
    String DB_USER = "sa";
    String DB_PASSWORD = "password";

    String DB_SCHEMA = "schema.sql";
    String DB_DATA = "data.sql";

    String RAM_TITLE = "ОЗУ";
    String CPU_TITLE = "Процессоры";
    String GPU_TITLE = "Видеокарты";

    String EMPTY_FILTER_FIELD = "Фильтр отсутствует";
    String SORT_BY_COST_DESC = " ORDER BY cost DESC";
    String SORT_BY_COST_ASC = " ORDER BY cost ASC";
}
