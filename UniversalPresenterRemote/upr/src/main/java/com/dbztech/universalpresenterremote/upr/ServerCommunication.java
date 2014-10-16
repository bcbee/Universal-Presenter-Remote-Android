package com.dbztech.universalpresenterremote.upr;

import com.loopj.android.http.*;

import org.apache.http.Header;

/**
 * Created by Brendan on 4/6/2014.
 */

public class ServerCommunication {

    private static String serverAddress = "http://universalpresenterremote.com/";
    public static int tempToken = 0;
    public static int token = 0;
    public static int uid = 0;
    public static int controlMode = 0;
    public static boolean serverAvailable = false;
    public static String gcmtoken = "";

    public static void newToken() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(serverAddress+"NewSession", new TextHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                newTokenCallback(responseString);
            }
        });
    }

    public static void setupUid() {
        if (uid == 0) {
            uid = 9999 + (int)(Math.random()*999999999);
        }
    }

    public static void newTokenCallback(String response) {
        tempToken = Integer.parseInt(response);
        System.out.println(tempToken);
        checkStatus();
    }

    public static void checkStatus() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(serverAddress+"TempSession?token="+tempToken+"&holdfor="+uid+"&gcmtoken="+gcmtoken, new TextHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                serverAvailable = true;
                checkStatusCallback(responseString);
            }
        });
    }

    public static void checkStatusSync() {
        SyncHttpClient client = new SyncHttpClient();
        client.get(serverAddress+"TempSession?token="+tempToken+"&holdfor="+uid+"&gcmtoken="+gcmtoken, new TextHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                serverAvailable = true;
                checkStatusCallback(responseString);
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
                client.get(serverAddress+"SlideDown?token="+tempToken+"&holdfor="+uid, new TextHttpResponseHandler() {

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {

                    }
                });
                break;
            case 1:
                client.get(serverAddress+"SlideUp?token="+tempToken+"&holdfor="+uid, new AsyncHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    }
                });
                break;
            case 2:
                client.get(serverAddress+"PlayMedia?token="+tempToken+"&holdfor="+uid, new AsyncHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

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
