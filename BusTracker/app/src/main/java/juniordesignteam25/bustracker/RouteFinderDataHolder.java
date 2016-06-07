/*
 * GT Bus Tracker Application version 0.0.1
 * Released in 2016
 * Created by Nicolette Fink, Yueting Lee, Jared Moore, Jules Schwartz, Hualong Zhang
 *
 */

package juniordesignteam25.bustracker;

import java.util.HashMap;

/**
 * Holds some of the static data about the bus routes to decrease computation time
 */
public class RouteFinderDataHolder {

    /**
     * This contains how many stops each route has
     */
    public static final HashMap<String, Integer> STOPS_PER_ROUTE;
    /**
     * This contains the offset for reference_stop_id for each route
     */
    public static final HashMap<String, Integer> STOP_OFFSET;
    /**
     * This relates each route to an array index
     */
    public static final HashMap<String, Integer> NAME_TO_INDEX;
    /**
     * This is the string found at http://m.gatech.edu/api/buses/stop
     */
    public static final String ROUTE_JSON = "[{\"route_id\":\"blue\",\"stop_id\":\"fitthall_a\",\"stop_name\":\"" +
            "Fitten Hall\",\"stop_lat\":\"33.778274\",\"stop_lon\":\"-84.404191\",\"trip_id\":\"Counterclo\",\"" +
            "reference_stop_id\":\"1\"},{\"route_id\":\"blue\",\"stop_id\":\"mcmil8th\",\"stop_name\":\"McMillian St" +
            " & 8th St\",\"stop_lat\":\"33.779599\",\"stop_lon\":\"-84.404164\",\"trip_id\":\"Counterclo\",\"" +
            "reference_stop_id\":\"2\"},{\"route_id\":\"blue\",\"stop_id\":\"8thhemp\",\"stop_name\":\"8th St &" +
            " Hemphill Ave\",\"stop_lat\":\"33.779631\",\"stop_lon\":\"-84.40274\",\"trip_id\":\"Counterclo\",\"" +
            "reference_stop_id\":\"3\"},{\"route_id\":\"blue\",\"stop_id\":\"reccent\",\"stop_name\":\"CRC\",\"" +
            "stop_lat\":\"33.7751\",\"stop_lon\":\"-84.40265\",\"trip_id\":\"Counterclo\",\"reference_stop_id\":\"" +
            "4\"},{\"route_id\":\"blue\",\"stop_id\":\"studcentr\",\"stop_name\":\"Student Center\",\"stop_lat\":\"" +
            "33.77335\",\"stop_lon\":\"-84.39917\",\"trip_id\":\"Counterclo\",\"reference_stop_id\":\"5\"},{\"" +
            "route_id\":\"blue\",\"stop_id\":\"fershub\",\"stop_name\":\"Hub & Ferst Dr\",\"stop_lat\":\"33.772842" +
            "\",\"stop_lon\":\"-84.397359\",\"trip_id\":\"Counterclo\",\"reference_stop_id\":\"6\"},{\"route_id\":\"" +
            "blue\",\"stop_id\":\"cherfers\",\"stop_name\":\"Cherry St & Ferst Dr\",\"stop_lat\":\"33.77234\",\"" +
            "stop_lon\":\"-84.3957\",\"trip_id\":\"Counterclo\",\"reference_stop_id\":\"7\"},{\"route_id\":\"blue" +
            "\",\"stop_id\":\"naveapts_a\",\"stop_name\":\"North Avenue Apartments\",\"stop_lat\":\"33.77019\",\"" +
            "stop_lon\":\"-84.39174\",\"trip_id\":\"Counterclo\",\"reference_stop_id\":\"8\"},{\"route_id\":\"blue" +
            "\",\"stop_id\":\"technorth\",\"stop_name\":\"Techwood Dr & North Ave\",\"stop_lat\":\"33.771857\",\"" +
            "stop_lon\":\"-84.39192\",\"trip_id\":\"Counterclo\",\"reference_stop_id\":\"9\"},{\"route_id\":\"blue" +
            "\",\"stop_id\":\"3rdtech\",\"stop_name\":\"Techwood Dr & 3rd Street\",\"stop_lat\":\"33.774061\",\"" +
            "stop_lon\":\"-84.39192\",\"trip_id\":\"Counterclo\",\"reference_stop_id\":\"10\"},{\"route_id\":\"blue" +
            "\",\"stop_id\":\"4thtech\",\"stop_name\":\"Techwood Dr & 4th Street\",\"stop_lat\":\"33.775066\",\"" +
            "stop_lon\":\"-84.39194\",\"trip_id\":\"Counterclo\",\"reference_stop_id\":\"11\"},{\"route_id\":\"" +
            "blue\",\"stop_id\":\"5thtech\",\"stop_name\":\"Techwood Dr & 5th Street\",\"stop_lat\":\"33.776401\",\"" +
            "stop_lon\":\"-84.392123\",\"trip_id\":\"Counterclo\",\"reference_stop_id\":\"12\"},{\"route_id\":\"blue" +
            "\",\"stop_id\":\"fersfowl\",\"stop_name\":\"Ferst Dr & Fowler St\",\"stop_lat\":\"33.776949\",\"stop_lon" +
            "\":\"-84.394234\",\"trip_id\":\"Counterclo\",\"reference_stop_id\":\"13\"},{\"route_id\":\"blue\",\"" +
            "stop_id\":\"fersklau\",\"stop_name\":\"Ferst Dr & Cherry St\",\"stop_lat\":\"33.777634\",\"stop_lon\":\"" +
            "-84.39575\",\"trip_id\":\"Counterclo\",\"reference_stop_id\":\"14\"},{\"route_id\":\"blue\",\"stop_id\"" +
            ":\"fersatla\",\"stop_name\":\"Ferst Dr & Atlantic Dr\",\"stop_lat\":\"33.77832\",\"stop_lon\":\"" +
            "-84.398083\",\"trip_id\":\"Counterclo\",\"reference_stop_id\":\"15\"},{\"route_id\":\"blue\",\"stop_id" +
            "\":\"fersstat\",\"stop_name\":\"Ferst Dr & State St\",\"stop_lat\":\"33.778436\",\"stop_lon\":\"" +
            "-84.399961\",\"trip_id\":\"Counterclo\",\"reference_stop_id\":\"16\"},{\"route_id\":\"blue\",\"stop_id" +
            "\":\"fershemp_ob\",\"stop_name\":\"Ferst Dr & Hemphill Ave\",\"stop_lat\":\"33.778141\",\"stop_lon\":\"" +
            "-84.401829\",\"trip_id\":\"Counterclo\",\"reference_stop_id\":\"17\"},{\"route_id\":\"green\",\"stop_id\"" +
            ":\"tep_a\",\"stop_name\":\"TEP\",\"stop_lat\":\"33.7695\",\"stop_lon\":\"-84.402752\",\"trip_id\":\"" +
            "gr_to14th\",\"reference_stop_id\":\"18\"},{\"route_id\":\"green\",\"stop_id\":\"nara\",\"stop_name\":\"" +
            "NARA\",\"stop_lat\":\"33.77072\",\"stop_lon\":\"-84.403324\",\"trip_id\":\"tep\",\"reference_stop_id\":\"" +
            "19\"},{\"route_id\":\"green\",\"stop_id\":\"tranhub\",\"stop_name\":\"Transit Hub\",\"stop_lat\":\"" +
            "33.773226\",\"stop_lon\":\"-84.397016\",\"trip_id\":\"gr_to14th\",\"reference_stop_id\":\"20\"},{\"" +
            "route_id\":\"green\",\"stop_id\":\"studtech\",\"stop_name\":\"Student Center at Tech Pkwy\",\"stop_lat" +
            "\":\"33.773143\",\"stop_lon\":\"-84.3997\",\"trip_id\":\"gr_to14th\",\"reference_stop_id\":\"21\"},{\"" +
            "route_id\":\"green\",\"stop_id\":\"creccent\",\"stop_name\":\"CRC\",\"stop_lat\":\"33.774997\",\"" +
            "stop_lon\":\"-84.402359\",\"trip_id\":\"gr_to14th\",\"reference_stop_id\":\"22\"},{\"route_id\":\"" +
            "green\",\"stop_id\":\"fershemp\",\"stop_name\":\"Ferst Dr & Hemphill Ave\",\"stop_lat\":\"33.778363" +
            "\",\"stop_lon\":\"-84.401007\",\"trip_id\":\"gr_to14th\",\"reference_stop_id\":\"23\"},{\"route_id\":\"" +
            "green\",\"stop_id\":\"fersstat\",\"stop_name\":\"Ferst Dr & State St\",\"stop_lat\":\"33.778293\",\"" +
            "stop_lon\":\"-84.399279\",\"trip_id\":\"gr_to14th\",\"reference_stop_id\":\"24\"},{\"route_id\":\"green" +
            "\",\"stop_id\":\"ndec\",\"stop_name\":\"North Deck\",\"stop_lat\":\"33.780233\",\"stop_lon\":\"" +
            "-84.39905\",\"trip_id\":\"gr_to14th\",\"reference_stop_id\":\"25\"},{\"route_id\":\"green\",\"stop_id" +
            "\":\"10thhemp\",\"stop_name\":\"Hemphill Ave & 10th St\",\"stop_lat\":\"33.781619\",\"stop_lon\":\"" +
            "-84.404082\",\"trip_id\":\"gr_to14th\",\"reference_stop_id\":\"26\"},{\"route_id\":\"green\",\"stop_id" +
            "\":\"hempcurr\",\"stop_name\":\"Hemphill Ave & Curran St\",\"stop_lat\":\"33.784665\",\"stop_lon\":\"" +
            "-84.405937\",\"trip_id\":\"gr_to14th\",\"reference_stop_id\":\"27\"},{\"route_id\":\"green\",\"stop_id" +
            "\":\"14thbusy_a\",\"stop_name\":\"14th St Bus Yard\",\"stop_lat\":\"33.78617\",\"stop_lon\":\"-84.405241" +
            "\",\"trip_id\":\"gr_to14th\",\"reference_stop_id\":\"28\"},{\"route_id\":\"green\",\"stop_id\":\"" +
            "14thstat\",\"stop_name\":\"14th St & State St\",\"stop_lat\":\"33.78613\",\"stop_lon\":\"-84.398799" +
            "\",\"trip_id\":\"tep\",\"reference_stop_id\":\"29\"},{\"route_id\":\"green\",\"stop_id\":\"gcat\",\"" +
            "stop_name\":\"GCATT\",\"stop_lat\":\"33.78628\",\"stop_lon\":\"-84.39559\",\"trip_id\":\"tep\",\"" +
            "reference_stop_id\":\"30\"},{\"route_id\":\"green\",\"stop_id\":\"glc\",\"stop_name\":\"GLC\",\"" +
            "stop_lat\":\"33.78158\",\"stop_lon\":\"-84.39645\",\"trip_id\":\"tep\",\"reference_stop_id\":\"31\"}," +
            "{\"route_id\":\"green\",\"stop_id\":\"bakebldg\",\"stop_name\":\"Baker Bldg\",\"stop_lat\":\"33.780355" +
            "\",\"stop_lon\":\"-84.39928\",\"trip_id\":\"tep\",\"reference_stop_id\":\"32\"},{\"route_id\":\"green" +
            "\",\"stop_id\":\"fersstat_ob\",\"stop_name\":\"Ferst Dr & State St\",\"stop_lat\":\"33.778436\",\"" +
            "stop_lon\":\"-84.399961\",\"trip_id\":\"tep\",\"reference_stop_id\":\"33\"},{\"route_id\":\"" +
            "green\",\"stop_id\":\"fershemp_ob\",\"stop_name\":\"Ferst Dr & Hemphill Ave\",\"stop_lat\":\"" +
            "33.778141\",\"stop_lon\":\"-84.401829\",\"trip_id\":\"tep\",\"reference_stop_id\":\"34\"},{\"" +
            "route_id\":\"green\",\"stop_id\":\"reccent_ob\",\"stop_name\":\"CRC\",\"stop_lat\":\"33.7751\",\"" +
            "stop_lon\":\"-84.40265\",\"trip_id\":\"tep\",\"reference_stop_id\":\"35\"},{\"route_id\":\"green" +
            "\",\"stop_id\":\"studcent\",\"stop_name\":\"Student Center\",\"stop_lat\":\"33.77335\",\"stop_lon" +
            "\":\"-84.39917\",\"trip_id\":\"tep\",\"reference_stop_id\":\"36\"},{\"route_id\":\"green\",\"stop_id" +
            "\":\"hubfers\",\"stop_name\":\"HUB\\/Ferst Dr\",\"stop_lat\":\"33.772842\",\"stop_lon\":\"-84.397359" +
            "\",\"trip_id\":\"tep\",\"reference_stop_id\":\"37\"},{\"route_id\":\"green\",\"stop_id\":\"techtowe" +
            "\",\"stop_name\":\"Cherry St & Ferst Dr\",\"stop_lat\":\"33.77234\",\"stop_lon\":\"-84.3957\",\"" +
            "trip_id\":\"tep\",\"reference_stop_id\":\"38\"},{\"route_id\":\"night\",\"stop_id\":\"fitthall_a\",\"" +
            "stop_name\":\"Fitten Hall\",\"stop_lat\":\"33.778282\",\"stop_lon\":\"-84.404193\",\"trip_id\":\"night" +
            "\",\"reference_stop_id\":\"39\"},{\"route_id\":\"night\",\"stop_id\":\"8thhemp\",\"stop_name\":\"" +
            "8th St & Hemphill Ave\",\"stop_lat\":\"33.779665\",\"stop_lon\":\"-84.402895\",\"trip_id\":\"night" +
            "\",\"reference_stop_id\":\"40\"},{\"route_id\":\"night\",\"stop_id\":\"creccent_ob\",\"stop_name\":\"" +
            "Recreation Center\",\"stop_lat\":\"33.7751\",\"stop_lon\":\"-84.40265\",\"trip_id\":\"night\",\"" +
            "reference_stop_id\":\"41\"},{\"route_id\":\"night\",\"stop_id\":\"tranhub_b\",\"stop_name\":\"" +
            "Transit HUB\",\"stop_lat\":\"33.773302\",\"stop_lon\":\"-84.397026\",\"trip_id\":\"night\",\"" +
            "reference_stop_id\":\"42\"},{\"route_id\":\"night\",\"stop_id\":\"technorth_ob\",\"stop_name\":\"" +
            "Techwood Dr & North Ave\",\"stop_lat\":\"0\",\"stop_lon\":\"0\",\"trip_id\":\"night\",\"" +
            "reference_stop_id\":\"43\"},{\"route_id\":\"night\",\"stop_id\":\"bobfow\",\"stop_name\":\"" +
            "Bobby Dodd\\/Fowler\",\"stop_lat\":\"33.773913\",\"stop_lon\":\"-84.393282\",\"trip_id\":\"" +
            "tofittenhall\",\"reference_stop_id\":\"44\"},{\"route_id\":\"night\",\"stop_id\":\"" +
            "technorth_ib\",\"stop_name\":\"Techwood Dr & North Ave\",\"stop_lat\":\"33.771353\",\"" +
            "stop_lon\":\"-84.392075\",\"trip_id\":\"tofittenhall\",\"reference_stop_id\":\"45\"},{\"" +
            "route_id\":\"night\",\"stop_id\":\"creccent_ib\",\"stop_name\":\"Recreation Center\",\"" +
            "stop_lat\":\"33.774997\",\"stop_lon\":\"-84.402359\",\"trip_id\":\"tofittenhall\",\"" +
            "reference_stop_id\":\"46\"},{\"route_id\":\"red\",\"stop_id\":\"fitthall\",\"stop_name\":\"" +
            "Fitten Hall\",\"stop_lat\":\"33.778274\",\"stop_lon\":\"-84.404191\",\"trip_id\":\"Clockwise\",\"" +
            "reference_stop_id\":\"47\"},{\"route_id\":\"red\",\"stop_id\":\"mcmil8th\",\"stop_name\":\"" +
            "McMillian St & 8th St\",\"stop_lat\":\"33.779599\",\"stop_lon\":\"-84.404164\",\"trip_id\":\"Clockwise" +
            "\",\"reference_stop_id\":\"48\"},{\"route_id\":\"red\",\"stop_id\":\"8thhemp\",\"stop_name\":\"" +
            "8th St & Hemphill Ave\",\"stop_lat\":\"33.779631\",\"stop_lon\":\"-84.40274\",\"trip_id\":\"Clockwise" +
            "\",\"reference_stop_id\":\"49\"},{\"route_id\":\"red\",\"stop_id\":\"fershemp\",\"stop_name\":\"" +
            "Ferst Dr & Hemphill Ave\",\"stop_lat\":\"33.778363\",\"stop_lon\":\"-84.401007\",\"trip_id\":\"" +
            "Clockwise\",\"reference_stop_id\":\"50\"},{\"route_id\":\"red\",\"stop_id\":\"fersstat\",\"stop_name\":\"" +
            "Ferst Dr & State St\",\"stop_lat\":\"33.778293\",\"stop_lon\":\"-84.399279\",\"trip_id\":\"Clockwise" +
            "\",\"reference_stop_id\":\"51\"},{\"route_id\":\"red\",\"stop_id\":\"fersatla\",\"stop_name\":\"" +
            "Ferst Dr & Atlantic Dr\",\"stop_lat\":\"33.77819\",\"stop_lon\":\"-84.397491\",\"trip_id\":\"" +
            "Clockwise\",\"reference_stop_id\":\"52\"},{\"route_id\":\"red\",\"stop_id\":\"klaubldg\",\"" +
            "stop_name\":\"Ferst Dr & Cherry St\",\"stop_lat\":\"33.777439\",\"stop_lon\":\"-84.395508\",\"" +
            "trip_id\":\"Clockwise\",\"reference_stop_id\":\"53\"},{\"route_id\":\"red\",\"stop_id\":\"fersfowl" +
            "\",\"stop_name\":\"Ferst Dr & Fowler St\",\"stop_lat\":\"33.776925\",\"stop_lon\":\"-84.393671\",\"" +
            "trip_id\":\"Clockwise\",\"reference_stop_id\":\"54\"},{\"route_id\":\"red\",\"stop_id\":\"tech5th\",\"" +
            "stop_name\":\"Techwood Dr & 5th St\",\"stop_lat\":\"33.776401\",\"stop_lon\":\"-84.392123\",\"trip_id" +
            "\":\"Clockwise\",\"reference_stop_id\":\"55\"},{\"route_id\":\"red\",\"stop_id\":\"tech4th\",\"stop_name" +
            "\":\"Techwood Dr & 4th St\",\"stop_lat\":\"33.774954\",\"stop_lon\":\"-84.392049\",\"trip_id\":\"" +
            "Clockwise\",\"reference_stop_id\":\"56\"},{\"route_id\":\"red\",\"stop_id\":\"techbob\",\"stop_name\":\"" +
            "Techwood Dr & Bobby Dodd Way\",\"stop_lat\":\"33.773667\",\"stop_lon\":\"-84.39205\",\"trip_id\":\"" +
            "Clockwise\",\"reference_stop_id\":\"57\"},{\"route_id\":\"red\",\"stop_id\":\"technorth\",\"stop_name" +
            "\":\"Techwood Dr & North Ave\",\"stop_lat\":\"33.77145\",\"stop_lon\":\"-84.3921\",\"trip_id\":\"" +
            "Clockwise\",\"reference_stop_id\":\"58\"},{\"route_id\":\"red\",\"stop_id\":\"naveapts_a\",\"stop_name" +
            "\":\"North Avenue Apartments\",\"stop_lat\":\"33.76994\",\"stop_lon\":\"-84.391629\",\"trip_id\":\"" +
            "Clockwise\",\"reference_stop_id\":\"59\"},{\"route_id\":\"red\",\"stop_id\":\"ferstcher\",\"" +
            "stop_name\":\"Cherry St & Ferst Dr\",\"stop_lat\":\"33.772284\",\"stop_lon\":\"-84.39548\",\"trip_id" +
            "\":\"Clockwise\",\"reference_stop_id\":\"60\"},{\"route_id\":\"red\",\"stop_id\":\"centrstud\",\"" +
            "stop_name\":\"Student Center\",\"stop_lat\":\"33.77346\",\"stop_lon\":\"-84.399159\",\"trip_id\":\"" +
            "Clockwise\",\"reference_stop_id\":\"61\"},{\"route_id\":\"red\",\"stop_id\":\"hubfers\",\"stop_name" +
            "\":\"Hub & Ferst Dr\",\"stop_lat\":\"33.77276\",\"stop_lon\":\"-84.396983\",\"trip_id\":\"Clockwise" +
            "\",\"reference_stop_id\":\"62\"},{\"route_id\":\"red\",\"stop_id\":\"creccent\",\"stop_name\":\"CRC" +
            "\",\"stop_lat\":\"33.774997\",\"stop_lon\":\"-84.402359\",\"trip_id\":\"Clockwise\",\"" +
            "reference_stop_id\":\"63\"},{\"route_id\":\"trolley\",\"stop_id\":\"marta_a\",\"stop_name\":\"" +
            "MARTA Midtown Station\",\"stop_lat\":\"33.78082\",\"stop_lon\":\"-84.38641\",\"trip_id\":\"tohub" +
            "\",\"reference_stop_id\":\"64\"},{\"route_id\":\"trolley\",\"stop_id\":\"publix\",\"stop_name\":\"" +
            "Publix Supermarket\",\"stop_lat\":\"33.7806\",\"stop_lon\":\"-84.388818\",\"trip_id\":\"tohub\",\"" +
            "reference_stop_id\":\"65\"},{\"route_id\":\"trolley\",\"stop_id\":\"techsqua\",\"stop_name\":\"" +
            "Technology Square\",\"stop_lat\":\"33.77692\",\"stop_lon\":\"-84.38978\",\"trip_id\":\"tohub\",\"" +
            "reference_stop_id\":\"66\"},{\"route_id\":\"trolley\",\"stop_id\":\"tech5rec\",\"stop_name\":\"" +
            "Techwood Dr & 5th St\",\"stop_lat\":\"33.776896\",\"stop_lon\":\"-84.391581\",\"trip_id\":\"tohub\",\"" +
            "reference_stop_id\":\"67\"},{\"route_id\":\"trolley\",\"stop_id\":\"fersforec\",\"stop_name\":\"" +
            "Ferst Dr & Fowler St\",\"stop_lat\":\"33.776949\",\"stop_lon\":\"-84.394234\",\"trip_id\":\"tohub\",\"" +
            "reference_stop_id\":\"68\"},{\"route_id\":\"trolley\",\"stop_id\":\"ferschrec\",\"stop_name\":\"" +
            "Ferst Dr & Cherry St\",\"stop_lat\":\"33.777634\",\"stop_lon\":\"-84.39575\",\"trip_id\":\"tohub\",\"" +
            "reference_stop_id\":\"69\"},{\"route_id\":\"trolley\",\"stop_id\":\"fersatla\",\"stop_name\":\"" +
            "Ferst Dr & Atlantic Dr\",\"stop_lat\":\"33.77832\",\"stop_lon\":\"-84.398083\",\"trip_id\":\"tohub" +
            "\",\"reference_stop_id\":\"70\"},{\"route_id\":\"trolley\",\"stop_id\":\"fersherec\",\"stop_name\":\"" +
            "Ferst Dr & Hemphill Ave\",\"stop_lat\":\"33.778141\",\"stop_lon\":\"-84.401829\",\"trip_id\":\"" +
            "tohub\",\"reference_stop_id\":\"71\"},{\"route_id\":\"trolley\",\"stop_id\":\"recctr\",\"stop_name" +
            "\":\"Recreation Center - Ferst Dr\",\"stop_lat\":\"33.7751\",\"stop_lon\":\"-84.40265\",\"" +
            "trip_id\":\"tohub\",\"reference_stop_id\":\"72\"},{\"route_id\":\"trolley\",\"stop_id\":\"" +
            "studcentr\",\"stop_name\":\"Student Center\",\"stop_lat\":\"33.77335\",\"stop_lon\":\"-84.39917" +
            "\",\"trip_id\":\"tohub\",\"reference_stop_id\":\"73\"},{\"route_id\":\"trolley\",\"stop_id\":\"" +
            "tranhub_a\",\"stop_name\":\"Transit Hub\",\"stop_lat\":\"33.773261\",\"stop_lon\":\"-84.397058\",\"" +
            "trip_id\":\"tomarta\",\"reference_stop_id\":\"74\"},{\"route_id\":\"trolley\",\"stop_id\":\"centrstud" +
            "\",\"stop_name\":\"Student Center\",\"stop_lat\":\"33.77335\",\"stop_lon\":\"-84.39917\",\"trip_id\":\"" +
            "tomarta\",\"reference_stop_id\":\"75\"},{\"route_id\":\"trolley\",\"stop_id\":\"ferstdr\",\"stop_name\":\"" +
            "CRC\",\"stop_lat\":\"33.774997\",\"stop_lon\":\"-84.402359\",\"trip_id\":\"tomarta\",\"" +
            "reference_stop_id\":\"76\"},{\"route_id\":\"trolley\",\"stop_id\":\"fershemrt\",\"stop_name\":\"" +
            "Ferst Dr & Hemphill Ave\",\"stop_lat\":\"33.778363\",\"stop_lon\":\"-84.401007\",\"trip_id\":\"" +
            "tomarta\",\"reference_stop_id\":\"77\"},{\"route_id\":\"trolley\",\"stop_id\":\"fersatl_ib\",\"" +
            "stop_name\":\"Ferst Dr & Atlantic Dr\",\"stop_lat\":\"33.77819\",\"stop_lon\":\"-84.397491\",\"" +
            "trip_id\":\"tomarta\",\"reference_stop_id\":\"78\"},{\"route_id\":\"trolley\",\"stop_id\":\"ferschmrt" +
            "\",\"stop_name\":\"Ferst Dr & Cherry St\",\"stop_lat\":\"33.777439\",\"stop_lon\":\"-84.395508\",\"" +
            "trip_id\":\"tomarta\",\"reference_stop_id\":\"79\"},{\"route_id\":\"trolley\",\"stop_id\":\"" +
            "fersfomrt\",\"stop_name\":\"Ferst Dr & Fowler St\",\"stop_lat\":\"33.776925\",\"stop_lon\":\"" +
            "-84.393671\",\"trip_id\":\"tomarta\",\"reference_stop_id\":\"80\"},{\"route_id\":\"trolley\",\"" +
            "stop_id\":\"tech5mrt\",\"stop_name\":\"Techwood Dr & 5th St\",\"stop_lat\":\"33.776878\",\"stop_lon" +
            "\":\"-84.391914\",\"trip_id\":\"tomarta\",\"reference_stop_id\":\"81\"},{\"route_id\":\"trolley\",\"" +
            "stop_id\":\"techsqua_ib\",\"stop_name\":\"Technology Square\",\"stop_lat\":\"33.7768\",\"stop_lon\":\"" +
            "-84.38975\",\"trip_id\":\"tomarta\",\"reference_stop_id\":\"82\"},{\"route_id\":\"trolley\",\"" +
            "stop_id\":\"duprmrt\",\"stop_name\":\"College of Management\",\"stop_lat\":\"33.77678\",\"stop_lon" +
            "\":\"-84.38749\",\"trip_id\":\"tomarta\",\"reference_stop_id\":\"83\"},{\"route_id\":\"trolley\",\"" +
            "stop_id\":\"wpe7mrt\",\"stop_name\":\"Academy of Medicine\",\"stop_lat\":\"33.778536\",\"stop_lon" +
            "\":\"-84.38724\",\"trip_id\":\"tomarta\",\"reference_stop_id\":\"84\"}]";

    /**
     * Do some initial set up
     */
    static {
        STOPS_PER_ROUTE = new HashMap<>();
        STOPS_PER_ROUTE.put("blue", 17);
        STOPS_PER_ROUTE.put("red", 17);
        STOPS_PER_ROUTE.put("trolley", 21);
        STOPS_PER_ROUTE.put("green", 21);

        STOP_OFFSET = new HashMap<>();
        STOP_OFFSET.put("blue", 0);
        STOP_OFFSET.put("green", 17);
        STOP_OFFSET.put("red", 47);
        STOP_OFFSET.put("trolley", 63);

        NAME_TO_INDEX = new HashMap<>();
        NAME_TO_INDEX.put("blue", 0);
        NAME_TO_INDEX.put("red", 1);
        NAME_TO_INDEX.put("trolley", 2);
        NAME_TO_INDEX.put("green", 3);
    }
}
