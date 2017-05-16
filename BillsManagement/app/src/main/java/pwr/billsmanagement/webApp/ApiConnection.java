package pwr.billsmanagement.webApp;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by E6520 on 2017-05-10.
 */

public class ApiConnection {
    HttpURLConnection connection=null;

    public ApiConnection(){
    }

    public void connect(URL url){
        try {
            connection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }



}
