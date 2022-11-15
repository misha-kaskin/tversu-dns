package models;

import java.util.List;
import java.util.Map;

public interface UsedForFront {
    String getTitle();

    List<String> getAttributes();

    String getModel();

    Integer getCost();

    Map<String, String> getAttributesWithValues();
}
