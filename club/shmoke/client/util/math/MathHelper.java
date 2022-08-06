package club.shmoke.client.util.math;

import net.minecraft.client.Minecraft;
import net.minecraft.util.MathUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

/**
 * @author Kyle
 * @since 8/31/2017
 **/
public class MathHelper
{
    public static int answer;
    private static Random random = new Random();

    public static int floor(double val) {
        return MathUtil.floor_double(val);
    }

    public static double roundDouble(final double value, final int places)
    {
        if (places < 0)
        {
            throw new IllegalArgumentException();
        }

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static double roundFloat(final double value, final int places)
    {
        if (places < 0)
        {
            throw new IllegalArgumentException();
        }

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static float getNumberWithinRange(float min, float max)
    {
        for (int i = 0; i < 10000; i++)
        {
            float number = max * (float) Math.random();

            if (number > min)
            {
                return number;
            }
        }

        return 1;
    }

    public static double getRandom(double min, double max) {
        return MathUtil.clamp_double(min + random.nextDouble() * max, min, max);
    }

    public static int getRandom(int min, int max) {
        return MathUtil.clamp_int(min + random.nextInt() * max, min, max);
    }

    public static int transitionTo(int from, int to)
    {
        if (from < to && Minecraft.getMinecraft().func_175610_ah() >= 60)
        {
            for (int i = 0; i < 3; i++)
            {
                from++;
            }
        }

        if (from > to && Minecraft.getMinecraft().func_175610_ah() >= 60)
        {
            for (int i = 0; i < 3; i++)
            {
                from--;
            }
        }

        if (from < to && Minecraft.getMinecraft().func_175610_ah() >= 40
                && Minecraft.getMinecraft().func_175610_ah() <= 59)
        {
            for (int i = 0; i < 4; i++)
            {
                from++;
            }
        }

        if (from > to && Minecraft.getMinecraft().func_175610_ah() >= 40
                && Minecraft.getMinecraft().func_175610_ah() <= 59)
        {
            for (int i = 0; i < 4; i++)
            {
                from--;
            }
        }

        if (from < to && Minecraft.getMinecraft().func_175610_ah() >= 0
                && Minecraft.getMinecraft().func_175610_ah() <= 39)
        {
            for (int i = 0; i < 6; i++)
            {
                from++;
            }
        }

        if (from > to && Minecraft.getMinecraft().func_175610_ah() >= 0
                && Minecraft.getMinecraft().func_175610_ah() <= 39)
        {
            for (int i = 0; i < 6; i++)
            {
                from--;
            }
        }

        return from;
    }

    public static float cap(float i, float j, float k)
    {
        if (i > j)
        {
            i = j;
        }

        if (i < k)
        {
            i = k;
        }

        return i;
    }

    public static int getMiddle(int i, int i1)
    {
        return (i + i1) / 2;
    }

    public static int parsedString(String args[])
    {
        if (args[1] == "+")
        {
            Integer first = Integer.parseInt(args[0]);
            Integer second = Integer.parseInt(args[2]);
            answer = first + second;
        }

        if (args[1] == "-")
        {
            Integer first = Integer.parseInt(args[0]);
            Integer second = Integer.parseInt(args[2]);
            answer = first - second;
        }

        if (args[1] == "*")
        {
            Integer first = Integer.parseInt(args[0]);
            Integer second = Integer.parseInt(args[2]);
            answer = first * second;
        }

        if (args[1] == "/")
        {
            Integer first = Integer.parseInt(args[0]);
            Integer second = Integer.parseInt(args[2]);
            answer = first / second;
        }

        return answer;
    }
}
