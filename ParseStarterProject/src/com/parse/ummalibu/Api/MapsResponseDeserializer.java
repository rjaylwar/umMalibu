package com.parse.ummalibu.api;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.parse.ummalibu.responses.MapsResponse;

import java.lang.reflect.Type;

/**
 * Created by rjaylward on 9/28/15.
 */
public class MapsResponseDeserializer implements JsonDeserializer<MapsResponse> {
    @Override
    public MapsResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        MapsResponse mapsResponse = new MapsResponse();

        mapsResponse.setDistance(json.getAsJsonObject().getAsJsonArray("routes").get(0).getAsJsonObject()
                .getAsJsonArray("legs").get(0).getAsJsonObject()
                .getAsJsonObject("distance")
                .getAsJsonPrimitive("text").getAsString());

        mapsResponse.setDistanceValue(json.getAsJsonObject().getAsJsonArray("routes").get(0).getAsJsonObject()
                .getAsJsonArray("legs").get(0).getAsJsonObject()
                .getAsJsonObject("distance")
                .getAsJsonPrimitive("value").getAsDouble());

        mapsResponse.setTripTime(json.getAsJsonObject().getAsJsonArray("routes").get(0).getAsJsonObject()
                .getAsJsonArray("legs").get(0).getAsJsonObject()
                .getAsJsonObject("duration")
                .getAsJsonPrimitive("text").getAsString());

        mapsResponse.setPath(json.getAsJsonObject().getAsJsonArray("routes").get(0).getAsJsonObject()
                .getAsJsonObject("overview_polyline")
                .getAsJsonPrimitive("points").getAsString());

        return mapsResponse;
    }
}
