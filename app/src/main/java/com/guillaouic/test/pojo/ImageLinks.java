
package com.guillaouic.test.pojo;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.squareup.picasso.Picasso;

public class ImageLinks implements Serializable
{

    @SerializedName("smallThumbnail")
    @Expose
    private String smallThumbnail;
    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;
    private final static long serialVersionUID = -1554034074296523187L;

    public String getSmallThumbnail() {
        return smallThumbnail;
    }

    public void setSmallThumbnail(String smallThumbnail) {
        this.smallThumbnail = smallThumbnail;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }


    @BindingAdapter({"loadImage"})
    public static void loadImage(ImageView view, String image){
        Picasso.with(view.getContext()).load(image).into(view);
        // Picasso.with(view.getContext()).load(imageUrl).placeholder(R.drawable.placeholder).into(view);
    }

}
