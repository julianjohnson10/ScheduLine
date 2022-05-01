package Utilities;

import java.time.ZoneId;
import java.util.Locale;

public class Locales {

    //Get OS default locale

    /**
     *
     * @return The user's default language setting.
     */
    public static String getLanguage(){
        final Locale currentLocale = Locale.getDefault();
        return currentLocale.getLanguage();
    }

    public static String getZoneId(){
        final ZoneId zoneId = ZoneId.systemDefault();
        return zoneId.toString();
    }

}
