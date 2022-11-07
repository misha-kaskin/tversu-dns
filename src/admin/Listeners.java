package admin;

import java.util.ArrayList;
import java.util.List;

public class Listeners {
    private static final List<Listener> listenerList = new ArrayList<>();

    public static void addListener(Listener listener) {
        listenerList.add(listener);
    }

    public static List<Listener> getListeners() {
        return List.copyOf(listenerList);
    }
}
