package com.dbztech.universalpresenterremote.upr;

import com.loopj.android.http.*;

/**
 * Created by Brendan on 4/6/2014.
 */

public class ServerCommunication {

    private static String serverAddress = "http://10.0.0.14/";
    public static int tempToken = 0;
    public static int token = 0;
    public static int uid = 12847398;
    public static int controlMode = 0;
    public static boolean serverAvailable = false;

    public static void newToken() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(serverAddress+"NewSession", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                newTokenCallback(response);
            }
        });
    }

    public static void newTokenCallback(String response) {
        tempToken = Integer.parseInt(response);
        System.out.println(tempToken);
        checkStatus();
    }

    public static void checkStatus() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(serverAddress+"TempSession?token="+tempToken+"&holdfor="+uid, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                serverAvailable = true;
                checkStatusCallback(response);
            }
        });
    }

    public static void checkStatusCallback(String response) {
        int incoming = Integer.parseInt(response);
        controlMode = incoming;
        switch (incoming) {
            case 0:
                //Token invalid or session taken
                newToken();
                break;
            case 1:
                //Waiting
                token = tempToken;
                break;
            case 2:
                //Ready to control
                break;
        }
    }
}
