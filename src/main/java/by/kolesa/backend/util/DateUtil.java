package by.kolesa.backend.util;

import lombok.experimental.UtilityClass;

import java.util.Calendar;

@UtilityClass
public class DateUtil {

    public String getCurrentDatePlusMinutes(int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, minutes);
        long timeInMillis = calendar.getTimeInMillis();
        long timeInSeconds = timeInMillis / 1000;
        return String.valueOf(timeInSeconds);
    }

}
