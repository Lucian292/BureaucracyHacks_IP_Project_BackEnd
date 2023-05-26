package com.bureaucracyhacks.refactorip.MapsUtility;

public class Direction
{
    public String lat;
    public String lng;

    public Direction(String lat, String lng)
    {
        this.lat = lat;
        this.lng = lng;
    }

    public String getLat()
    {
        return lat;
    }

    public void setLat(String lat)
    {
        this.lat = lat;
    }

    public String getLng()
    {
        return lng;
    }

    public void setLng(String lng)
    {
        this.lng = lng;
    }
}
