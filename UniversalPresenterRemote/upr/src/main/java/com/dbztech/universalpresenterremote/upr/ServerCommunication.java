package com.dbztech.universalpresenterremote.upr;

import com.loopj.android.http.*;

/**
 * Created by Brendan on 4/6/2014.
 */

public class ServerCommunication {

    private static String serverAddress = "http://upr.dbztech.com/";
    public static int tempToken = 0;
    public static int token = 0;
    public static int uid = 0;
    public static int controlMode = 0;
    public static boolean serverAvailable = false;
    public static String gcmtoken = "";

    public static void newToken() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(serverAddress+"NewSession", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                newTokenCallback(response);
            }
        });
    }

    public static void setupUid() {
        uid = 9999 + (int)(Math.random()*999999999);
    }

    public static void newTokenCallback(String response) {
        tempToken = Integer.parseInt(response);
        System.out.println(tempToken);
        checkStatus();
    }

    public static void checkStatus() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(serverAddress+"TempSession?token="+tempToken+"&holdfor="+uid+"&gcmtoken="+gcmtoken, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                serverAvailable = true;
                checkStatusCallback(response);
            }
        });
    }

    public static void checkStatusSync() {
        SyncHttpClient client = new SyncHttpClient();
        client.get(serverAddress+"TempSession?token="+tempToken+"&holdfor="+uid+"&gcmtoken="+gcmtoken, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                serverAvailable = true;
                checkStatusCallback(response);
            }
        });
    }

    public static void reset() {
        token = 0;
        tempToken = 0;
    }

    public static void slideControl(int action) {
        AsyncHttpClient client = new AsyncHttpClient();
        switch (action) {
            case 0:
                client.get(serverAddress+"SlideDown?token="+tempToken+"&holdfor="+uid, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(String response) {

                    }
                });
                break;
            case 1:
                client.get(serverAddress+"SlideUp?token="+tempToken+"&holdfor="+uid, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(String response) {

                    }
                });
                break;
            case 2:
                client.get(serverAddress+"PlayMedia?token="+tempToken+"&holdfor="+uid, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(String response) {

                    }
                });
                break;
        }
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
