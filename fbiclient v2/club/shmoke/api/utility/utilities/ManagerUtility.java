package club.shmoke.api.utility.utilities;

import club.shmoke.api.event.EventManager;
import club.shmoke.main.cheats.fight.Killaura;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Christian
 */
public abstract class ManagerUtility<T> {

    public ManagerUtility() {
        EventManager.GET.register(this);
    }

    private final List<T> CONTENTS = new ArrayList<>();

    public abstract void initialize();

    public void addContent(T... impl) {
        Collections.addAll(CONTENTS, impl);
    }

    public List<T> getContents() {
        return CONTENTS;
    }

    public T getContent(Class clazz) {
        for (T t : CONTENTS) {
            if (t == clazz) {
                return t;
            }
        }
        return null;
    }

    public boolean hasContent(T impl) {
        for (T type : CONTENTS) if (type == impl) return true;
        return false;
    }
}
