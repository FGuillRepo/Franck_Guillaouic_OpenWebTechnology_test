
package com.guillaouic.test.model.bookModel;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.guillaouic.test.database.converter.NestedItemConverter;

@Entity(tableName = "book_model")
public class Book implements Serializable
{
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private Long id;
    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("totalItems")
    @Expose
    private Integer totalItems;

    @TypeConverters({NestedItemConverter.class})
    @SerializedName("items")
    @Expose
    private List<Item> items = null;

    private final static long serialVersionUID = 6932446096851715464L;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public Integer getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @NonNull
    public Long getId() {
        return id;
    }

    public void setId(@NonNull Long id) {
        this.id = id;
    }
}
