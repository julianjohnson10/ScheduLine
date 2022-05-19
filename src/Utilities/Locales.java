package Utilities;

import java.time.ZoneId;

public class Locales {

    /**
     * Getter for the zone id.
     * @return the system default zone id
     */
    public static String getZoneId(){
        final ZoneId zoneId = ZoneId.systemDefault();
        return zoneId.toString();
    }

}
