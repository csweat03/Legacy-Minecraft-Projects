package me.xx.api.event;

import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Kyle
 * @since 2/1/2018
 **/
public class EventManager {

	public ConcurrentHashMap<Class<? extends Event>, List<Handler>> registry;
	private final Comparator<Handler> comparator = (h, h1) -> Byte.compare(h.priority, h1.priority);

	public static final EventManager INSTANCE = new EventManager();

	public EventManager() {
		this.registry = new ConcurrentHashMap<>();
	}

	public void register(Object... objs) {
		for (Object obj : objs) {
			for (Method m : obj.getClass().getDeclaredMethods()) {
				if (m.getParameterCount() != 1 || !m.isAnnotationPresent(Register.class)) {
					continue;
				}
				Class<? extends Event> eventClass = (Class<? extends Event>) m.getParameterTypes()[0];
				if (!this.registry.containsKey(eventClass)) {
					this.registry.put(eventClass, new CopyOnWriteArrayList<>());
				}
				this.registry.get(eventClass)
						.add(new Handler(m, obj, m.getDeclaredAnnotation(Register.class).priority()));
				this.registry.get(eventClass).sort(this.comparator);
			}
		}
	}

	public void unregister(Object... objs) {
		for (Object obj : objs) {
			for (List<Handler> list : this.registry.values()) {
				for (Handler data : list) {
					if (data.parent != obj) {
						continue;
					}
					list.remove(data);
				}
			}
		}
	}

	public <E extends Event> E dispatch(E event) {
		List<Handler> list = this.registry.get(event.getClass());
		if (!(list == null || list.isEmpty())) {
			for (Handler data : list) {
				if (event.skippingFutureCalls()) {
					break;
				}
				try {
					data.handler.invokeExact(data.parent, event);
				} catch (Throwable e1) {
					e1.printStackTrace();
				}
			}
		}
		return event;
	}

	public ConcurrentHashMap<Class<? extends Event>, List<Handler>> getRegistry() {
		return registry;
	}
}
