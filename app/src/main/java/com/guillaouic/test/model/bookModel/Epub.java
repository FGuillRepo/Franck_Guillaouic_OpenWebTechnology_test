
package com.guillaouic.test.model.bookModel;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Epub implements Serializable
{

    @SerializedName("isAvailable")
    @Expose
    private Boolean isAvailable;
    private final static long serialVersionUID = -1047361512002891265L;

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

}
