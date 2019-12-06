package com.example.paint.Model;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Parcel;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class FigureCurveLine extends FigureDrawble {
    private Paint mPaint;
    private PointF mCurrentPointF = new PointF();

    private Path mPath;
    public FigureCurveLine(int color, @NonNull MotionEvent event) {
        super(color,  event);
        mPath = new Path();
        mPath.moveTo(getFirstPoint().x, getFirstPoint().y);
        init();
    }

    public FigureCurveLine(Parcel in) {
        super(in);
        mCurrentPointF.readFromParcel(in);
        mPath = new Path();
        mPath.moveTo(getFirstPoint().x, getFirstPoint().y);
        init();
    }


    public void setLinePoint(float x, float y) {
            mCurrentPointF.x = x;
            mCurrentPointF.y = y;
    }

    private void drawCurveLine(Canvas canvas) {
        if (mPath != null) {
            mPath.lineTo(mCurrentPointF.x, mCurrentPointF.y);
            canvas.drawPath(mPath, mPaint);
        }
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        drawCurveLine(canvas);
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
    public static final Creator<FigureCurveLine> CREATOR = new Creator<FigureCurveLine>() {
        @Override
        public FigureCurveLine createFromParcel(Parcel in) {
            return new FigureCurveLine(in);
        }

        @Override
        public FigureCurveLine[] newArray(int size) {
            return new FigureCurveLine[size];
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
