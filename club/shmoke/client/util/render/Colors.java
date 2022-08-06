package club.shmoke.client.util.render;

import java.awt.Color;
import java.util.function.Supplier;

import club.shmoke.api.event.EventManager;
import club.shmoke.client.events.update.TickEvent;
import club.shmoke.api.event.interfaces.EventListener;
import club.shmoke.client.util.math.DelayHelper;

public enum Colors {
	
	FADING_GREEN(() -> Color.HSBtoRGB(0.283f, getFadingHue(), 0.82f)), NARROW_FADING_GREEN(
			() -> Color.HSBtoRGB(0.283f, getNarrowFadingHue(), 0.82f)), FADING_RED(
					() -> Color.HSBtoRGB(getFadingHue(), 0.14f, 1.0f)), FADING_BLUE(
							() -> Color.HSBtoRGB(0.283f, 0.82f, getFadingHue()));

	private static float FADING_HUE = 1F, NARROW_FADING_HUE = 1F;
	private final Supplier<Integer> colorSupplier;

	Colors(Supplier<Integer> colorSupplier) {
		this.colorSupplier = colorSupplier;
	}

	public int getColor() {
		return colorSupplier.get();
	}

	public static float getFadingHue() {
		return FADING_HUE;
	}

	public static float getNarrowFadingHue() {
		return NARROW_FADING_HUE;
	}
	
	static {
		EventManager.register(new Object() {
			private DelayHelper hueTimer = new DelayHelper(), narrowHueTimer = new DelayHelper();
			private boolean hueDarkening, narrowHueDarkening;

			@EventListener
			public void run(TickEvent event) {
				if (narrowHueTimer.hasReached(140)) {
					if (!narrowHueDarkening) {
						if (NARROW_FADING_HUE > 0.8) {
							NARROW_FADING_HUE -= 0.02;
						} else {
							narrowHueDarkening = true;
						}
					} else {
						if (NARROW_FADING_HUE < 1) {
							NARROW_FADING_HUE += 0.02;
						} else {
							narrowHueDarkening = false;
						}
					}
					narrowHueTimer.reset();
				}
				if (hueTimer.hasReached(70)) {
					if (!hueDarkening) {
						if (FADING_HUE > 0.6) {
							FADING_HUE -= 0.05;
						} else {
							hueDarkening = true;
						}
					} else {
						if (FADING_HUE < 1) {
							FADING_HUE += 0.05;
						} else {
							hueDarkening = false;
						}
					}
					hueTimer.reset();
				}
			}
		});
	}

}
