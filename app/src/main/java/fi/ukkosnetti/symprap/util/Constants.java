package fi.ukkosnetti.symprap.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Constants {

    public static final String SERVER_URL = "https://10.0.2.2:8090";

    public static final Integer PASSWORD_MINIMUM_LENGTH = 4;

    public static final String DATE_FORMAT = "MM/DD/yyyy";

    public static final DateFormat DATE_FORMATTER = new SimpleDateFormat(DATE_FORMAT);
}
