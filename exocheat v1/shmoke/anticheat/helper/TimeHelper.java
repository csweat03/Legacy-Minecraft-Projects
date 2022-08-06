package club.shmoke.anticheat.helper;

public class TimeHelper
{
    private long previousMS;
    
    public TimeHelper() {
        reset();
    }
    
    public boolean hasReached(long milliseconds) {
        return getCurrentMS() - previousMS >= milliseconds;
    }

    public void reset() {
        previousMS = getCurrentMS();
    }

    public long getTimePassed() {
        return getCurrentMS() - previousMS;
    }
    
    private long getCurrentMS() {
        return System.nanoTime() / 1000000L;
    }
}
