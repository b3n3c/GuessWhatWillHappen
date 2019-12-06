package apps.bingoo.com.guesswhatwillhappen;

import android.content.Intent;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

public class GetDataBase implements Runnable {
    volatile static ArrayList<String> urls=new ArrayList<String>();
    static Random randomGenerator = new Random();
    @Override
    public void run() {
         //to read each line
        //TextView t; //to show the result, please declare and find it inside onCreate()

        try {
            // Create a URL for the desired page
            URL url = new URL("https://dl.dropboxusercontent.com/s/j7pgvf9izbfc8f5/database.txt?dl=0"); //My text file location
            //First open the connection
            HttpURLConnection conn=(HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(60000); // timing out in a minute

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String str;
            while ((str = in.readLine()) != null) {
                urls.add(str);
            }
            in.close();
        } catch (Exception e) {
            Log.d("MyTag",e.toString());

        }

    }

    public static ArrayList<String> downloadData() {
        final ArrayList<String> urls_2 = urls;
        return urls_2;
    }

    public static int rv(int lengt, ArrayList<Integer> seen){
        while(lengt % 8 != 0) lengt -= 1;
        ArrayList<Integer> a = new ArrayList<Integer>();
        for (int i = 1; i < lengt; i += 8){
            if (!seen.contains(i)) a.add(i);
        }
        if (a.isEmpty()) return -1;
        else{
            int index = randomGenerator.nextInt(a.size());
            index = randomGenerator.nextInt(a.size());
            return a.get(index);
        }

    }
}
