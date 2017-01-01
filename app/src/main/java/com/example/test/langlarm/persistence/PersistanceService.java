package com.example.test.langlarm.persistence;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import static android.os.ParcelFileDescriptor.MODE_WORLD_READABLE;

public class PersistanceService {

    public static <T> List<T> loadList(Context mContext, String key, Class clazz) {




        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        List<T> list = new ArrayList();
        int size = sharedPreferences.getInt(key, 0);

        for (int i = 0; i < size; i++) {
            String string = sharedPreferences.getString(key + i, null);
            list.add((T) fromJson(string, clazz));
        }
        return list;
    }

    public static <T> boolean saveList(Activity activity, String key, List<T> items) {

//        FileOutputStream fOut = openFileOutput("file name here",MODE_WORLD_READABLE);


        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putInt(key, items.size());

        for (int i = 0; i < items.size(); i++) {
            editor.remove(key + i);
            editor.putString(key + i, toJson(items.get(i)));
        }
        return editor.commit();
    }

    private static <T> String toJson(T object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    private static <T> T fromJson(String json, Class object) {
        Gson gson = new Gson();
        return (T) gson.fromJson(json, object);
    }
}
