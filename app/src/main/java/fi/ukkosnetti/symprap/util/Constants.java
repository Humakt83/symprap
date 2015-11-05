package fi.ukkosnetti.symprap.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Constants {

    public static final String SERVER_URL = "https://10.0.2.2:8090";

    public static final Integer PASSWORD_MINIMUM_LENGTH = 4;

    public static final String DATE_FORMAT = "MM/dd/yyyy";

    public static final String TIME_FORMAT = "HH:mm";

    public static final String DATETIME_FORMAT = DATE_FORMAT + " " + TIME_FORMAT;

    public static final DateFormat DATE_FORMATTER = new SimpleDateFormat(DATE_FORMAT);

    public static final DateFormat TIME_FORMATTER = new SimpleDateFormat(TIME_FORMAT);

    public static final DateFormat DATETIME_FORMATTER = new SimpleDateFormat(DATETIME_FORMAT);

}
