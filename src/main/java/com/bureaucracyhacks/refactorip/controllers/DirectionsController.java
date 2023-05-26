package com.bureaucracyhacks.refactorip.controllers;

import com.bureaucracyhacks.refactorip.MapsUtility.Direction;
import com.bureaucracyhacks.refactorip.services.DirectionService;
import com.google.gson.Gson;
import com.google.maps.model.GeocodingResult;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class contains the endpoints for the directions API
 */
@RequiredArgsConstructor
@RequestMapping("/api/directions")
@RestController
public class DirectionsController
{

    /**
     * This method returns the directions for a given route
     *
     * @return the directions for a given route
     */
    private final DirectionService directionService;

//    @Autowired
//    private Root root;

    @PostMapping("/get-directions")
    public ResponseEntity<String> getDirections(@RequestBody String routes) throws IOException
    {
        //System.out.println(routes);
        //separator for the waypoints is a comma
        String[] waypoints = routes.split(";");
        //TODO make a call to DirectionsService
        List<GeocodingResult[]> coords  = directionService.getDirections(waypoints);
        //serialize the response into an array of class 'Direction'
        List<Direction> directions = new ArrayList<>();
        for (GeocodingResult[] coord : coords)
        {
            directions.add(new Direction(coord[0].geometry.location.lat + "", coord[0].geometry.location.lng + ""));
        }
        Gson gson = new Gson();
        //convert the response to JSON
        String json = gson.toJson(directions);

        //System.out.println(directions);
        //return the response as a JSON
        return ResponseEntity.ok(json);

    }
}
