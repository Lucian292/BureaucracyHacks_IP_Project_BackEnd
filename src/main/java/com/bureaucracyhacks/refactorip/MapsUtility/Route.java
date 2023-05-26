package com.bureaucracyhacks.refactorip.MapsUtility;

import com.google.maps.model.Bounds;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
@Component
public class Route
{
    public Bounds bounds;
    public String copyrights;
    public ArrayList<Leg> legs;
    //public OverviewPolyline overview_polyline;
    public String summary;
    public ArrayList<String> warnings;
    public ArrayList<Object> waypoint_order;
}
