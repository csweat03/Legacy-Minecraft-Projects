package me.xx.utility;

/**
 * @author Kyle
 * @since 1/7/2018
 */
public class Stopwatch {
	
	private long current;

	public Stopwatch() {
		this.current = System.currentTimeMillis();
	}

	public boolean hasReached(final long delay) {
		return System.currentTimeMillis() - this.current >= delay;
	}

	public boolean hasReached(final long delay, boolean reset) {
		if (reset)
			reset();
		return System.currentTimeMillis() - this.current >= delay;
	}

	public void reset() {
		this.current = System.currentTimeMillis();
	}

	public long getTimePassed() {
		return System.currentTimeMillis() - this.current;
	}
}