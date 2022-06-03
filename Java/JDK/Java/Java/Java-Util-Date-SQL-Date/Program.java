// https://docs.oracle.com/javase/6/docs/api/java/text/SimpleDateFormat.html

/*

Date and Time Pattern			Result
"yyyy.MM.dd G 'at' HH:mm:ss z"		2001.07.04 AD at 12:08:56 PDT
"EEE, MMM d, ''yy"			Wed, Jul 4, '01
"h:mm a"				12:08 PM
"hh 'o''clock' a, zzzz"			12 o'clock PM, Pacific Daylight Time
"K:mm a, z"				0:08 PM, PDT
"yyyyy.MMMMM.dd GGG hh:mm aaa"		02001.July.04 AD 12:08 PM
"EEE, d MMM yyyy HH:mm:ss Z"		Wed, 4 Jul 2001 12:08:56 -0700
"yyMMddHHmmssZ"				010704120856-0700
"yyyy-MM-dd'T'HH:mm:ss.SSSZ"		2001-07-04T12:08:56.235-0700

*/

import static java.lang.System.out;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Program {
    public static void main(String[] args) throws ParseException {
        String strDate = new java.util.Date().toString();
        out.println(strDate); // Sun Nov 06 10:31:54 IST 2016 // Default       
        SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
        java.util.Date parsed = format.parse(strDate);

        // SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // java.util.Date parsed = format.parse("2016-12-31 23:59:59");

        java.sql.Date sqlDate = new java.sql.Date(parsed.getTime());
        out.println(sqlDate); 
        java.sql.Time sqlTime = new java.sql.Time(parsed.getTime());
        out.println(sqlTime);  
        java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(parsed.getTime());
        out.println(sqlTimestamp);
    }
}