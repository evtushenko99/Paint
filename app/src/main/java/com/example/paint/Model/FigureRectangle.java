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

import com.example.paint.Rectangle;

public class FigureRectangle extends FigureDrawble {
    private Rectangle mRectangle;
    private Paint mPaint;
    private PointF mCurrentPointF = new PointF();
    public FigureRectangle(int color,  @NonNull MotionEvent event) {
        super(color, event);
        mRectangle = new Rectangle(getFirstPoint());
        init();

    }

    public FigureRectangle(Parcel in) {
        super(in);
        mRectangle = new Rectangle(getFirstPoint());
        init();
    }

    private void drawRectangle(Canvas canvas) {
        if (mRectangle != null) {
            mRectangle.setCurrent(mCurrentPointF);
            float left = Math.min(mRectangle.getOrigin().x, mRectangle.getCurrent().x);
            float right = Math.max(mRectangle.getOrigin().x, mRectangle.getCurrent().x);
            float top = Math.min(mRectangle.getOrigin().y, mRectangle.getCurrent().y);
            float bottom = Math.max(mRectangle.getOrigin().y, mRectangle.getCurrent().y);
            canvas.drawRect(left, top, right, bottom, mPaint);
        }
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        drawRectangle(canvas);
    }
    private void init() {
        mPaint = new Paint();
        mPaint.setColor(getColor());
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(getLineWidth());

    }

    public void setLinePoint(float x, float y) {
            mCurrentPointF.x = x;
            mCurrentPointF.y = y;
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
    public static final Creator<FigureRectangle> CREATOR = new Creator<FigureRectangle>() {
        @Override
        public FigureRectangle createFromParcel(Parcel in) {
            return new FigureRectangle(in);
        }

        @Override
        public FigureRectangle[] newArray(int size) {
            return new FigureRectangle[size];
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
