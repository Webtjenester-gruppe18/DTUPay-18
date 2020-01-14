package Helpers;

import javax.xml.datatype.XMLGregorianCalendar;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class DateHelper {

    public static String formatUniversalDate(XMLGregorianCalendar xmlGregorianCalendar) {
        String formattedDate = Instant.parse (xmlGregorianCalendar.toString())
                .atZone ( ZoneId.of ( "Europe/Copenhagen" ) )
                .format (
                        DateTimeFormatter.ofLocalizedDateTime ( FormatStyle.LONG )
                                .withLocale ( Locale.getDefault() )
        );

        return formattedDate;
    }

}
