package club.shmoke.client.util.math;

public class DelayHelper
{
    private long previousMS;

    public DelayHelper()
    {
        reset();
    }

    public boolean hasReached(long milliseconds)
    {
        return getCurrentMS() - this.previousMS >= milliseconds;
    }

    public void reset()
    {
        this.previousMS = getCurrentMS();
    }

    public long getPreviousMS()
    {
        return this.previousMS;
    }

    public long getTimePassed()
    {
        return this.getCurrentMS() - this.previousMS;
    }

    public long getCurrentMS()
    {
        return System.nanoTime() / 1000000L;
    }
}
