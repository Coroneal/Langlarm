package com.example.test.langlarm.persistence;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class PersistanceService {

    private static final String SHARED_PREFERENCES_NAME = "persistanceService";

    public static <T> List<T> loadList(Context context, String key, Class<T> clazz) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME,Context.MODE_PRIVATE);
        List<T> list = new ArrayList();
        int size = sharedPreferences.getInt(key, 0);

        for (int i = 0; i < size; i++) {
            String string = sharedPreferences.getString(key + i, null);
            list.add((T) fromJson(string, clazz));
        }
        return list;
    }

    public static <T> boolean saveList(Context context, String key, List<T> items) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
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

//
//    public static void save(Context context, String key, Object object) {
//        try {
//            FileOutputStream fileOutputStream = context.openFileOutput(key, Context.MODE_PRIVATE);
//            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
//            objectOutputStream.writeObject(object);
//            objectOutputStream.close();
//            fileOutputStream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    public static <T> List<T> loadList(Context context, String key, Class<T> clazz) {
//        List<T> list = new ArrayList();
//        try {
//            FileInputStream fileInputStream = context.openFileInput(key);
//            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
//            list = (List<T>) objectInputStream.readObject();
//            objectInputStream.close();
//            fileInputStream.close();
//        } catch (FileNotFoundException e) {
//            return list;
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        return list;
//    }
}
