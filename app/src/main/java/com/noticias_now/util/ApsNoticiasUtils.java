package com.noticias_now.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.noticias_now.model.UsuarioModel;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by ricarlo on 12/11/2016.
 */

public class ApsNoticiasUtils {

    private static final String TAG = "APS Noticias";

    public static Object jsonToObject(String obj, Class<?> classModel) {
        Gson gson = new Gson();
        return gson.fromJson(obj, classModel);
    }

    public static Object jsonToObject(String obj, Type classModel) {
        Gson gson = new Gson();
        return gson.fromJson(obj, classModel);
    }

    public static String objectToString(Object obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }

    public static String ObjetcToString(UsuarioModel user){
        Gson gson = new GsonBuilder().
                setPrettyPrinting().create();
        return gson.toJson(user);
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    /**
     * Method to read a String value of a given key in a given preferences file
     *
     * @param context      Context of application
     * @param file         Name of preferences file
     * @param key          Name of key to be read
     * @param defaultValue Value default to returned case the key not exists
     * @return String
     */
    public static String read(Context context, String file, String key, String defaultValue) {
        String value = null;

        try {
            SharedPreferences sharedPrefs = context.getApplicationContext().getSharedPreferences(file, Context.MODE_PRIVATE);
            value = sharedPrefs.getString(key, defaultValue);
        } catch (Exception e) {
            value = defaultValue;
            Log.e(TAG, e.getMessage(), e);
        }

        return value;
    }


    /**
     * Method to write a boolean value in a given key in a given file
     * preferences
     *
     * @param context Context of application
     * @param file    Name of preferences file
     * @param key     Name of key to be read
     * @param value   Value to be saved in the key
     * @return true | false
     */
    public static boolean write(Context context, String file, String key, String value) {
        boolean sucess = false;

        try {
            SharedPreferences sharedPrefs = context.getApplicationContext().getSharedPreferences(file, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putString(key, value);
            editor.commit();
            sucess = true;
        } catch (Exception e) {
            sucess = false;
            Log.e(TAG, e.getMessage(), e);
        }

        return sucess;
    }

    /**
     * Method to remove a particular key of a given file preferences
     *
     * @param context Context of application
     * @param file    Name of preferences file
     * @param key     Name of key to be read
     * @return true | false
     */
    public static boolean remove(Context context, String file, String key) {
        boolean sucess = false;

        try {
            SharedPreferences sharedPrefs = context.getApplicationContext().getSharedPreferences(file, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.remove(key);
            editor.commit();
            sucess = true;
        } catch (Exception e) {
            sucess = false;
            Log.e(TAG, e.getMessage(), e);
        }

        return sucess;
    }

    public static String converteDatePost(String input) throws ParseException {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.setTime(df.parse(input));

        String[] days = new String[7];
        days[0] = "Dom";
        days[1] = "Seg";
        days[2] = "Ter";
        days[3] = "Qua";
        days[4] = "Qui";
        days[5] = "Sex";
        days[6] = "Sáb";

        int dayOfWeek  = cal.get(Calendar.DAY_OF_WEEK); // SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY
        int hourOfDay  = cal.get(Calendar.HOUR_OF_DAY); // 24 hour clock
        int minute     = cal.get(Calendar.MINUTE);

        String _date = String.format("%s às %02d:%02d", days[dayOfWeek - 1], hourOfDay, minute);

        return _date;
    }
}
