package il.ac.shenkar.qrmap;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Constants {

 //the changes made to value of below variable will be cleared on force stop,so whenever force stop occurs,the value of variable will be "isForceStopped=true" again.
     public static boolean isForceStopped =true;
     public static boolean pointFlag = false;
     public static boolean signInPage = false;
     public static boolean inOperation = false;
     public static boolean inDelOperation = false;
     public static String username;
     public static Integer QRid=0;
     public static String parseNumber="";
     private static URL  myurl;
     private static URLConnection connection;
     public static boolean isOnline() {
         try {
        	   myurl = new URL("https://parse.com");//https://www.parse.com
        	  
         } catch (MalformedURLException e2) {
             e2.printStackTrace();
         }
         try {
             connection = myurl.openConnection();
         } catch (IOException e) {
             e.printStackTrace();
         }
         connection.setConnectTimeout(3000);
         HttpURLConnection httpConnection = (HttpURLConnection) connection;
         int responseCode = -1;
         try {
             responseCode = httpConnection.getResponseCode();
         } catch (Exception e1) {                    
                 e1.printStackTrace();                   
         }
         if (!(responseCode == HttpURLConnection.HTTP_OK)) 
         {
             return false;
         }
         return true;
     }
     
 }