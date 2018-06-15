package utils;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;
public class DataGenerator {


    /**
     * Get day number from current week
     * @return
     */
    public static int getCurrentDate(){
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Get week number from current month
     * @return
     */
    public static int getCurrentWeek(){
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        return calendar.get(Calendar.WEEK_OF_MONTH);
    }

    /**
     * Used to generate random number btw min and max
     * @param min
     * @param max
     * @return
     */
    public static int numberRandomGenerator(int min, int max)
    {
        Random r = new Random();
        int Low = min;
        int High = max;
        return r.nextInt(High-Low) + Low; //random btw current month and 12
    }

    public static void main(String[] args)
    {


    }
}
