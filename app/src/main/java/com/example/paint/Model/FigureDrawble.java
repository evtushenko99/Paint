package com.example.paint.Model;

import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.MotionEvent;

import androidx.annotation.NonNull;

public abstract class FigureDrawble extends Drawable implements Parcelable {
    private int mColor;
    private PointF mFirstPoint = new PointF();


    private String mDrawingMode;

    public int getColor() {
        return mColor;
    }

    public float getLineWidth() {
        float lineWidth = 8f;
        return lineWidth;
    }


    public PointF getFirstPoint() {
        return mFirstPoint;
    }

    public FigureDrawble(int color, @NonNull MotionEvent event) {

        mFirstPoint.x = event.getX();
        mFirstPoint.y = event.getY();
        mColor = color;
    }
    public FigureDrawble(Parcel in) {
        mFirstPoint.readFromParcel(in);
        mColor = in.readInt();
    }
}

