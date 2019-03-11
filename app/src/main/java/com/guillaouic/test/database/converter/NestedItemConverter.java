package com.guillaouic.test.database.converter;

import android.arch.persistence.room.TypeConverter;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.guillaouic.test.pojo.Item;

import java.lang.reflect.Type;
import java.util.List;

/*
 *  Room NestedItemConverter : Use in Entity.
 * */

public class NestedItemConverter {
    private static Gson gson = new Gson();
    private static Type type = new TypeToken<List<Item>>(){}.getType();

    @TypeConverter
    public static List<Item> stringToNestedData(String json) {
        return gson.fromJson(json, type);
    }

    @TypeConverter
    public static String nestedDataToString(List<Item> nestedData) {
        return gson.toJson(nestedData, type);
    }
}