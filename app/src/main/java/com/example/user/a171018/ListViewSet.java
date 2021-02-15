package com.example.user.a171018;

import android.graphics.Bitmap;

public class ListViewSet {
    Bitmap bitmap;
    String titleStr;
    String typeStr;
    String subStr;

    public void setBitmap(Bitmap productImage) {
        bitmap = productImage;
    }
    public void setTitle(String title) {
        titleStr = title;
    }
    public void setType(String type) {
        typeStr = type;
    }
    public void setSub(String sub) {
        subStr = sub;
    }

    public Bitmap getBitmap() {
        return this.bitmap;
    }
    public String getTitle() {
        return this.titleStr;
    }
    public String getType() {
        return this.typeStr;
    }
    public String getSub() {
        return this.subStr;
    }
}
