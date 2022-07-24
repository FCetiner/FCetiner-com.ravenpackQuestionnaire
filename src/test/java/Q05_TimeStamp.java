import io.restassured.response.Response;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static io.restassured.RestAssured.given;


// create class UTCTimeExample1 to get the current UTC time by using SimpleDateFormat
public class Q05_TimeStamp {
    @Test
    public void timestampTest(){
        String endpoint="https://feed.ravenpack.com/1.0/json/";
        try {
            //1 First we get curent time before request
            String utcTimeFirst=getCurrentUtcTime().toString();
            System.out.println("utcTimeFirst = " + utcTimeFirst);
            //2  we send request and print it on the console
            Response response=given().when().get(endpoint);
            response.prettyPrint();
            //3 we get time after response
            String utcTimeSecond=getCurrentUtcTime().toString();
            System.out.println("utcTimeSecond = " + utcTimeSecond);

            //We find the latency time by the method we created by taking the int value of minutes vs seconds.
            int secondOfRequest=Integer.parseInt(utcTimeFirst.substring(17,19));
            int minuteOfRequest=Integer.parseInt(utcTimeFirst.substring(14,16));
            int secondOfResponse=Integer.parseInt(utcTimeSecond.substring(17,19));
            int minuteOfResponse=Integer.parseInt(utcTimeSecond.substring(14,16));

        int latency=findTimeDifferenceBetweenRequestAndResponse(minuteOfRequest,secondOfRequest,minuteOfResponse,secondOfResponse);
            System.out.println("latency = " + latency + " second");
        }
        catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private int findTimeDifferenceBetweenRequestAndResponse(int minuteOfRequest, int secondOfRequest, int minuteOfResponse, int secondOfResponse) {
     int sumofSecondofRequest=minuteOfRequest*60+secondOfRequest;
     int sumofSecondofResponse=minuteOfResponse*60+secondOfResponse;
     return sumofSecondofResponse-sumofSecondofRequest;
    }


    public static Date getCurrentUtcTime() throws ParseException {  // handling ParseException

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");

        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        SimpleDateFormat ldf = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");

        Date d1 = null;

        try {

            d1 = ldf.parse( sdf.format(new Date()) );
        }

        catch (java.text.ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        return d1;
    }
}