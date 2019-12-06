package com.example.paint;

import android.graphics.PointF;

public class Rectangle {
    private PointF mOrigin;
    private PointF mCurrent;

    public Rectangle(PointF origin) {
        mOrigin = origin;
        mCurrent = origin;
    }

    public PointF getOrigin() {
        return mOrigin;
    }


    public PointF getCurrent() {
        return mCurrent;
    }

    public void setCurrent(PointF current) {
        mCurrent = current;
    }


}
