package com.bureaucracyhacks.refactorip.MapsUtility;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
@Component
public class Root
{
    public ArrayList<GeocodedWaypoint> geocoded_waypoints;
    public ArrayList<Route> routes;
    public String status;
}
