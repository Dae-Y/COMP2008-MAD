package com.example.basicremotecall;

import android.app.Activity;
import android.net.Uri;

import java.net.HttpURLConnection;

public class APISearchThread extends Thread {

    private String searchkey;
    private String baseUrl;
    private RemoteUtilities remoteUtilities;
    private SearchResponseViewModel viewModel;
    public APISearchThread(String searchKey, Activity uiActivity, SearchResponseViewModel viewModel) {
        this.searchkey = searchKey;
        baseUrl ="https://pixabay.com/api/";
        remoteUtilities = RemoteUtilities.getInstance(uiActivity);
        this.viewModel = viewModel;
    }
    public void run(){
        String endpoint = getSearchEndpoint();
        HttpURLConnection connection = remoteUtilities.openConnection(endpoint);
        if(connection!=null){
            if(remoteUtilities.isConnectionOkay(connection)==true){
                String response = remoteUtilities.getResponseString(connection);
                connection.disconnect();
                try {
                    Thread.sleep(3000);
                }
                catch (Exception e){

                }
                viewModel.setResponse(response);
            }
        }

    }
    private String getSearchEndpoint(){
        String data = null;
        Uri.Builder url = Uri.parse(this.baseUrl).buildUpon();
        url.appendQueryParameter("key","23319229-94b52a4727158e1dc3fd5f2db");
        url.appendQueryParameter("q",this.searchkey);
        String urlString = url.build().toString();
        return urlString;
    }




}
