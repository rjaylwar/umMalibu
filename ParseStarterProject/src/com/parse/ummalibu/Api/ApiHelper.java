package com.parse.ummalibu.api;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.JsonObject;
import com.parse.ummalibu.objects.UmberRequest;
import com.parse.ummalibu.responses.EventsResponse;
import com.parse.ummalibu.responses.NotificationsResponse;
import com.parse.ummalibu.responses.TalksResponse;
import com.parse.ummalibu.responses.UmLocationsResponse;
import com.parse.ummalibu.responses.UmberRequestResponse;
import com.parse.ummalibu.values.FieldNames;
import com.parse.ummalibu.volley.GsonVolleyRequester;
import com.parse.ummalibu.volley.JsonObjectVolleyRequester;
import com.parse.ummalibu.volley.StringObjectVolleyRequester;
import com.parse.ummalibu.volley.VolleyRequestListener;

/**
 * Created by rjaylward on 9/22/15.
 */
public class ApiHelper {

    AppCompatActivity mActivity;
    String PARSE_API_URL = "https://api.parse.com/1/classes";

    public ApiHelper(AppCompatActivity activity) {
        mActivity = activity;
    }

    public void getTalks(VolleyRequestListener<TalksResponse> uiListener) {
        String url = PARSE_API_URL + "/sermons";
        Log.d("get all sermons", url);

        GsonVolleyRequester<TalksResponse> volleyRequester = new GsonVolleyRequester<>(mActivity, TalksResponse.class);
        volleyRequester.makeGetRequest(mActivity, url, uiListener);
    }

    public void getTalksJson(VolleyRequestListener uiListener) {
        String url = PARSE_API_URL + "/sermons";
        Log.d("get all sermons", url);

        JsonObjectVolleyRequester volleyRequester = new JsonObjectVolleyRequester(mActivity);
        volleyRequester.makeGetRequest(mActivity, url, uiListener);
    }

    public void getUmberLocation(VolleyRequestListener uiListener) {
        String url = PARSE_API_URL + "/uber";
        Log.d("get umber location", url);

        JsonObjectVolleyRequester volleyRequester = new JsonObjectVolleyRequester(mActivity);
        volleyRequester.makeGetRequest(mActivity, url, uiListener);
    }

    public void getFrontpageTalks(int limit,VolleyRequestListener<TalksResponse> uiListener) {
        String url = PARSE_API_URL + "/sermons" + "?where={\"source\":\"direct\"}&limit=" + limit;
        Log.d("get all sermons", url);

        GsonVolleyRequester<TalksResponse> volleyRequester = new GsonVolleyRequester<>(mActivity, TalksResponse.class);
        volleyRequester.makeGetRequest(mActivity, url, uiListener);
    }

    public void getLocations(VolleyRequestListener<UmLocationsResponse> uiListener) {
        String url = PARSE_API_URL + "/pick_up_spots";
        Log.d("get all pick up spot", url);

        GsonVolleyRequester<UmLocationsResponse> volleyRequester = new GsonVolleyRequester<>(mActivity, UmLocationsResponse.class);
        volleyRequester.makeGetRequest(mActivity, url, uiListener);
    }

    public void getEvents(VolleyRequestListener<EventsResponse> uiListener) {
        String url = PARSE_API_URL + "/event";
        Log.d("get all events", url);

        GsonVolleyRequester<TalksResponse> volleyRequester = new GsonVolleyRequester<>(mActivity, TalksResponse.class);
        volleyRequester.makeGetRequest(mActivity, url, uiListener);
    }

    public void getNotifications(VolleyRequestListener uiListener) {
        String url = PARSE_API_URL + "/notification";
        Log.d("get all notifications", url);

        GsonVolleyRequester<NotificationsResponse> volleyRequester = new GsonVolleyRequester<>(mActivity, NotificationsResponse.class);
        volleyRequester.makeGetRequest(mActivity, url, uiListener);
    }

    public void getUmberRequest(String objectId, VolleyRequestListener<UmberRequest> uiListener) {
        String url = PARSE_API_URL + "/uber_requests/" + objectId;
        Log.d("getting umber request", url);

        GsonVolleyRequester<UmberRequest> volleyRequester = new GsonVolleyRequester<>(mActivity, UmberRequest.class);
        volleyRequester.makeGetRequest(mActivity, url, uiListener);
    }

    public void getUmberRequests(VolleyRequestListener<UmberRequestResponse> uiListener) {
        String url = PARSE_API_URL + "/uber_requests";
        Log.d("make umber request", url);

        GsonVolleyRequester<UmberRequestResponse> volleyRequester = new GsonVolleyRequester<>(mActivity, UmberRequestResponse.class);
        volleyRequester.makeGetRequest(mActivity, url, uiListener);
    }

    public void makeUmberRequest(UmberRequest umberRequest, VolleyRequestListener uiListener){
        String url = PARSE_API_URL + "/uber_requests";
        Log.d("make umber request", url);

        JsonObject requestParams = new JsonObject();

        requestParams.addProperty(FieldNames.NAME, umberRequest.getName());
        requestParams.addProperty(FieldNames.CLAIMED, umberRequest.isClaimed());
        requestParams.addProperty(FieldNames.IS_PICKED_UP, false);

        requestParams.addProperty(FieldNames.PHONE_NUMBER, umberRequest.getPhoneNumber());
        requestParams.addProperty(FieldNames.RIDER_IMAGE_URL, umberRequest.getRiderImageUrl());
        requestParams.addProperty(FieldNames.LATITUDE, umberRequest.getLatitude());
        requestParams.addProperty(FieldNames.LONGITUDE, umberRequest.getLongitude());

        requestParams.addProperty(FieldNames.PICKUP_LOCATION_NAME, umberRequest.getPickUpLocation());
        requestParams.addProperty(FieldNames.PICKUP_LATITUDE, umberRequest.getPickupLat());
        requestParams.addProperty(FieldNames.PICKUP_LONGITUDE, umberRequest.getPickupLong());

        requestParams.addProperty(FieldNames.DESTINATION_NAME, umberRequest.getDestination());
        requestParams.addProperty(FieldNames.DESTINATION_LATITUDE, umberRequest.getDestinationLat());
        requestParams.addProperty(FieldNames.DESTINATION_LONGITUDE, umberRequest.getDestinationLong());

        Log.d("Request params", requestParams.toString());

        StringObjectVolleyRequester volleyRequester = new StringObjectVolleyRequester(mActivity);
        volleyRequester.makePostRequest(mActivity, url, requestParams, uiListener);

    }

    public void claimUmberRequest(UmberRequest request, VolleyRequestListener uiListener) {
        String url = PARSE_API_URL + "/uber_requests/" + request.getObjectId();
        Log.d("update umber request", url);

        JsonObject requestParams = new JsonObject();

        requestParams.addProperty(FieldNames.DRIVER_EMAIL, request.getDriverEmail());
        requestParams.addProperty(FieldNames.CLAIMED, request.isClaimed());
        requestParams.addProperty(FieldNames.ETA, request.getEta().getTime() / 1000);

        StringObjectVolleyRequester volleyRequester = new StringObjectVolleyRequester(mActivity);
        volleyRequester.makePutRequest(mActivity, url, requestParams, uiListener);
    }

    public void updateUmberRequestStatus(UmberRequest request, VolleyRequestListener uiListener) {
        String url = PARSE_API_URL + "/uber_requests/" + request.getObjectId();
        Log.d("update request status", url);

        JsonObject requestParams = new JsonObject();

        requestParams.addProperty(FieldNames.CLAIMED, request.isClaimed());
        requestParams.addProperty(FieldNames.STARTED, request.isStarted());
        requestParams.addProperty(FieldNames.IS_PICKED_UP, request.isPickedUp());
        requestParams.addProperty(FieldNames.IS_COMPLETE, request.isComplete());

        requestParams.addProperty(FieldNames.CANCELED, request.isCanceled());

        StringObjectVolleyRequester volleyRequester = new StringObjectVolleyRequester(mActivity);
        volleyRequester.makePostRequest(mActivity, url, requestParams, uiListener);
    }
}

