package com.bureaucracyhacks.refactorip.services;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class models the service for the directions API that uses Googles
 * directions API
 */
@Service
@Component
public class DirectionService
{
    //TODO define autowired dependencies for UserTasks , Tasks etc

    public DirectionService()
    {
        //TODO define autowired dependencies for UserTasks , Tasks etc
    }

    public List<GeocodingResult[]> getDirections(String[] in_waypoints) throws IOException
    {

        Dotenv dotenv = null;
        dotenv = Dotenv.configure().filename("config.env").load();
        String API_KEY = dotenv.get("API_KEY_MAPS");
        //System.out.println(API_KEY);

        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(API_KEY)
                .build();
        //list to store the waypoints

        List<GeocodingResult[]> waypoints = new ArrayList<>();
//        List<String> test_waypoints = new ArrayList<>();
//
//        test_waypoints.add("Marasesti str independentei nr 12");
//        test_waypoints.add("Tecuci str Gheorghe Petrascu nr 21");
//        test_waypoints.add("Marasesti str Republicii nr 1");


        for (String waypoint : in_waypoints)
        {
            try
            {
                GeocodingResult[] results = GeocodingApi.geocode(context,
                        waypoint).await();
                waypoints.add(results);
            } catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
        }
        //return waypoints;
//        StringBuilder url = new StringBuilder("https://maps.googleapis.com/maps/api/directions/json?language=ro&origin=");
//        url.append(waypoints.get(0)[0].geometry.location); //add the origin as the first waypoint
//        url.append("&destination=").append(waypoints.get(waypoints.size() - 1)[0].geometry.location); //add the destination as the last waypoint
//        url.append("&mode=walking&key=").append(API_KEY);
//
//        //add the waypoints in between if there are any
//        if (waypoints.size() > 2)
//        {
//            url.append("&waypoints="); //optimize the waypoints
//
//            for (int i = 1; i < waypoints.size() - 1; i++)
//            {
//                url.append(waypoints.get(i)[0].geometry.location);
//                if (i != waypoints.size() - 2)
//                {
//                    url.append("|");
//                }
//            }
//        }


//        System.out.println(url);
//        //make the request
//        URL request_url = new URL(url.toString());
//        HttpURLConnection connection = (HttpURLConnection) request_url.openConnection();
//        connection.setRequestMethod("GET");
//        connection.connect();
//        int code = connection.getResponseCode();
//        System.out.println("The response code is " + code);
//
//        //get the response
//        BufferedReader in = new BufferedReader(
//                new InputStreamReader(connection.getInputStream()));
//
//        String inputLine;
//
//        StringBuilder content = new StringBuilder();
//        while ((inputLine = in.readLine()) != null)
//        {
//            content.append(inputLine);
//        }
//        //System.out.println(content);
//        in.close();
//        //deserializing the response
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        Root root = gson.fromJson(String.valueOf(content), Root.class);
//        for (Route ruta : root.routes)
//        {
//            for (Leg leg : ruta.legs)
//            {
//                for (Step step : leg.steps)
//                {
//                    System.out.println(step.html_instructions);
//                    System.out.println(step.duration.text);
//                    System.out.println(step.distance.text);
//
//                }
//            }
//        }


        context.shutdown();

        return waypoints;
    }

}
