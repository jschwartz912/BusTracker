/*
 * GT Bus Tracker Application version 0.0.1
 * Released in 2016
 * Created by Nicolette Fink, Yueting Lee, Jared Moore, Jules Schwartz, Hualong Zhang
 *
 */

package juniordesignteam25.bustracker;

import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

/**
 * This class contains methods for finding the best bus routes from a user's location
 */
public class BestRouteFinder {

    private HttpSolrClient solr = new HttpSolrClient("http://localhost:8983/solr/CORE_NAME");
    private static HashMap<String, Stop> stops;
    private static boolean usingSolr = false;

    /**
     * Given a location finds the closest GT bus stop based on the euclidean distance
     * @param location The location from which we will find a bus stop
     * @return The name of the stop that is closest to the location (O(1) lookup to find lat/long)
     */
    public static String closestBusStopLatLong(double[] location) {
        if (!usingSolr && stops == null){
            buildLocationMap();
        }
        if (!usingSolr) {

            String closest = null;
            double min = Double.MAX_VALUE;
            double current;
            for (Stop i : stops.values()) {
                if ((current = latLonDistance(location, i.location)) < min) {
                    min = current;
                    closest = i.stopName;
                }
            }
            return closest;
        } else {
            return null;
            //TODO
        }
    }

    /**
     * Returns the name of the route connecting the two stops iff there exists one
     * @param start The stop name of the start location
     * @param end The stop name of the end location
     * @return The name of the route connecting the two stops with minimum stops.  Null if no such route exists
     */
    public static String singleRouteBetweenStops(String start, String end) {

        HashSet<String> startRoutes = stops.get(start).routes;
        HashSet<String> endRoutes = stops.get(end).routes;
        ArrayList<String> same = new ArrayList<>();
        for (String s : startRoutes) {
            if (endRoutes.contains(s)) {
                same.add(s);
            }
        }
        if (same.size() == 0) {
            return null;
        } else if (same.size() == 1) {
            return same.get(0);
        } else {
            int min = Integer.MAX_VALUE;
            String best = null;
            for (String s : same) {
                int first = stops.get(start).stopNumber[RouteFinderDataHolder.NAME_TO_INDEX.get(s)];
                int second = stops.get(end).stopNumber[RouteFinderDataHolder.NAME_TO_INDEX.get(s)];
                int difference = (second - first) % RouteFinderDataHolder.STOPS_PER_ROUTE.get(s);
                if (difference < min) {
                    best = s;
                    min = difference;
                }
            }
            return best;
        }
    }

    /**
     * When not using any spatial database, this method builds a local HashMap of the stop information provided
     * by GT.
     */
    private static void buildLocationMap() {
        try {
            stops = new HashMap<>();
            JSONArray mainArray = new JSONArray(RouteFinderDataHolder.ROUTE_JSON);
            for (int i = 0; i < mainArray.length(); i++) {
                JSONObject element = mainArray.getJSONObject(i);
                String routeName = element.getString("route_id");
                String stopName = element.getString("stop_name");
                if (stopName.equals("night")) {
                    continue;
                    // for now, we do not want to look at the Midnight Rambler
                }
                if (stops.containsKey(stopName)) {
                    stops.get(stopName).routes.add(routeName);
                    stops.get(stopName).stopNumber[RouteFinderDataHolder.NAME_TO_INDEX.get(stopName)] =
                            element.getInt("reference_stop_id");
                } else {
                    stops.put(stopName, new Stop(stopName, new double[]
                            {element.getDouble("stop_lat"), element.getDouble("stop_lon")}, routeName,
                            element.getInt("reference_stop_id")));
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Computes the distance between two latitude and longitude locations
     * @param first The first location
     * @param second The second location
     * @return The distance between the two locations in miles
     */
    private static double latLonDistance(double[] first, double[] second) {

        if (Arrays.equals(first, second)) {
            return 0;
        }
        double theta = first[1] - second[1];
        double dist = Math.sin(Math.toRadians(first[0])) * Math.sin(Math.toRadians(second[0])) +
                Math.cos(Math.toRadians(first[0])) * Math.cos(Math.toRadians(second[0])) *
                        Math.cos(Math.toRadians(theta));
        dist = Math.toDegrees(Math.acos(dist));
        dist = dist * 60 * 1.1515;
        return dist;
    }

    /**
     * An inner class to hold information about a bus stop
     */
    private static class Stop {
        /**
         * The name of the stop
         */
        public String stopName;
        /**
         * The lat/long of the stop
         */
        public double[] location;
        /**
         * A list of the GT bus routes that stop at this location
         */
        public HashSet<String> routes = new HashSet<>(4);
        /**
         * The number of the stop in order for that route
         */
        public int[] stopNumber = new int[4];

        public Stop(String stopName, double[] location, String route, int stopNumber) {
            this.stopName = stopName;
            this.location = location;
            routes.add(route);
            this.stopNumber[RouteFinderDataHolder.NAME_TO_INDEX.get(stopName)] = stopNumber;
        }
    }

}
