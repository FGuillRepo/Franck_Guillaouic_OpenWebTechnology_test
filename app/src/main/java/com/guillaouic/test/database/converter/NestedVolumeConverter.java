package com.guillaouic.test.database.converter;

import android.arch.persistence.room.TypeConverter;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.guillaouic.test.pojo.bookModel.Item;
import com.guillaouic.test.pojo.bookModel.VolumeInfo;

import java.lang.reflect.Type;
import java.util.List;

/*
 *  Room NestedItemConverter : Use in Entity.
 * */

public class NestedVolumeConverter {
    private static Gson gson = new Gson();
    private static Type type = new TypeToken<VolumeInfo>(){}.getType();

    @TypeConverter
    public static VolumeInfo stringToNestedData(String json) {
        return gson.fromJson(json, type);
    }

    @TypeConverter
    public static String nestedDataToString(VolumeInfo nestedData) {
        return gson.toJson(nestedData, type);
    }
}