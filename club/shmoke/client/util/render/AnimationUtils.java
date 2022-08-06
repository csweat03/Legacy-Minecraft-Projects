package club.shmoke.client.util.render;

public class AnimationUtils {

	long timeStarted = -1;
	double length = -1;

	int startInt = -1;
	int endInt = -1;

	boolean running = false;

	public void start(int mills, int start, int end, boolean positive) {

		timeStarted = System.currentTimeMillis();
		length = mills;

		if (positive) {
			startInt = start;
			endInt = end;
		} else {
			startInt = end;
			endInt = start;
		}
		if (startInt >= endInt - 0.1F || start >= end - 0.1F)
			running = false;
		else
			running = true;

	}

	public void start(int mills, int end, boolean positive) {

		start(mills, 0, end, positive);

	}

	public int getRounded() {

		return (int) Math.round(get());

	}

	public double get() {

		long millsPassed = System.currentTimeMillis() - timeStarted;

		if (millsPassed > length) {
			running = false;
			return endInt;
		}

		double percent = millsPassed / length;

		double end = (percent) * (endInt - startInt);

		return end + startInt;

	}
}
