/*
 * GT Bus Tracker Application version 0.0.1
 * Released in 2016
 * Created by Nicolette Fink, Yueting Lee, Jared Moore, Jules Schwartz, Hualong Zhang
 *
 */
package juniordesignteam25.bustracker;

import android.app.Notification;
        import android.app.NotificationManager;
        import android.app.Service;
        import android.content.Context;
        import android.content.Intent;
        import android.os.IBinder;
        import android.support.v7.app.NotificationCompat;

        import java.util.ArrayList;

/**
 * Handles the logic behind creating notifications about the buses for the users.
 */

public class notificationCreatorService extends Service {
    public notificationCreatorService() {
    }

    /**
     * Creates a notification
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        System.out.println("Creating Notification");
        String stop = intent.getStringExtra("stop");
        String destination = intent.getStringExtra("destination");
        System.out.println("stop: "+stop+" destination:"+destination);
        String title = "BusTracker to " + destination;
        String content ="";
        ArrayList<String> times = obtainArrivalTimes(stop, destination);
        for(String time:times){
            content += "Bus at "+stop+" arriving at "+time+"\n";
        }
        if(content.equals("")){
            content = "No buses available at this time";
        }
        Notification noti = new NotificationCompat.Builder(this)
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(R.mipmap.ic_launcher)
                .build();
        NotificationManager noteManage = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        noteManage.notify(createNotificaionId(stop, destination), noti);

        return flags;
    }

    private int createNotificaionId(String stop, String destination){
        if(stop!=null && destination!=null) {
            return stop.hashCode() ^ destination.hashCode();
        } else {
            return 1;
        }
    }

    /**
     * Get the arrival times of buses at a stop that is going to the destination
     * @param stop  the stop to get the buses for
     * @param destination   the place the user wants to go
     * @return  Arraylist of times in String format. This is the format the notation will use.
     */
    private ArrayList<String> obtainArrivalTimes(String stop, String destination){
        // TODO: get arrival times of buses for a stop
        ArrayList<String> times = new ArrayList<>();
        return times;
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}