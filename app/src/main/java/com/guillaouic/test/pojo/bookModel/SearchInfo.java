
package com.guillaouic.test.pojo.bookModel;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchInfo implements Serializable
{

    @SerializedName("textSnippet")
    @Expose
    private String textSnippet;
    private final static long serialVersionUID = 7438592621887092107L;

    public String getTextSnippet() {
        return textSnippet;
    }

    public void setTextSnippet(String textSnippet) {
        this.textSnippet = textSnippet;
    }

}
