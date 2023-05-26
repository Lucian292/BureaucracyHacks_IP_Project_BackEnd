package com.bureaucracyhacks.refactorip.MapsUtility;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
@Component
public class GeocodedWaypoint
{
    public String geocoder_status;
    public String place_id;
    public ArrayList<String> types;
}
