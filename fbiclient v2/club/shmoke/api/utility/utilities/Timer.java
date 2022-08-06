package club.shmoke.api.utility.utilities;

/**
 * @author Christian
 */
public class Timer {

    private long milli;

    public Timer() {
        reset();
    }

    public boolean hasReached(long ms) {
        return getLength() >= ms;
    }

    public void reset() {
        milli = getCurrentMS();
    }

    public long getLength() {
        return getCurrentMS() - milli;
    }

    private long getCurrentMS() {
        return System.nanoTime() / 1000000;
    }

}
