package br.com.ricarlo.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by ricarlo on 12/11/2016.
 */

public class ApsNoticiasUtils {

    public static String converteDatePost(String input) throws ParseException {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.setTime(df.parse(input));

        String[] days = new String[7];
        days[0] = "Dom";
        days[1] = "Seg";
        days[2] = "Ter";
        days[3] = "Qua";
        days[4] = "Qui";
        days[5] = "Sex";
        days[6] = "Sáb";

        int dayOfWeek  = cal.get(Calendar.DAY_OF_WEEK); // SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY
        int hourOfDay  = cal.get(Calendar.HOUR_OF_DAY); // 24 hour clock
        int minute     = cal.get(Calendar.MINUTE);

        String _date = String.format("%s às %02d:%02d", days[dayOfWeek - 1], hourOfDay, minute);

        return _date;
    }
}
