package com.example.paint.Model;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.os.Parcel;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class FigureLine extends FigureDrawble {
    private PointF mCurrentPointF = new PointF();
    private Paint mPaint;

    public FigureLine(int color,  @NonNull MotionEvent event) {
        super(color,  event);
        init();
    }

    public FigureLine(Parcel in) {
        super(in);
        mCurrentPointF.readFromParcel(in);
    }

    public void setLinePoint(float x, float y) {
        mCurrentPointF.x = x;
        mCurrentPointF.y = y;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        drawLine(getFirstPoint(), mCurrentPointF, canvas);

    }

    private void drawLine(PointF one, PointF two, Canvas canvas) {
        //float size = Math.min(canvas.getWidth(), canvas.getHeight());
        canvas.drawLine(one.x, one.y, two.x, two.y, mPaint);
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(getColor());
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(getLineWidth());

    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }


    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }


    // Требуется для Parcelable
    public static final Creator<FigureLine> CREATOR = new Creator<FigureLine>() {
        @Override
        public FigureLine createFromParcel(Parcel in) {
            return new FigureLine(in);
        }

        @Override
        public FigureLine[] newArray(int size) {
            return new FigureLine[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        mCurrentPointF.writeToParcel(dest, flags);
        getFirstPoint().writeToParcel(dest,flags);
        dest.writeInt(getColor());
    }
}

