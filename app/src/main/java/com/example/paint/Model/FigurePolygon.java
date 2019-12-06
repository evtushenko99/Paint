package com.example.paint.Model;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.os.Parcel;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class FigurePolygon extends FigureDrawble {
    private List<PointF> mPoints = new ArrayList<>(16);
    private Path mComplexPath;
    private Paint mPaint;
    private Paint mComplexPaint;

    public FigurePolygon(int color,  @NonNull MotionEvent event) {
        super(color, event);
        mComplexPath = new Path();
        init();
    }

    public FigurePolygon(Parcel in) {
        super(in);
        mPoints = in.createTypedArrayList(PointF.CREATOR);
        mComplexPath = new Path();
        init();
    }


    @Override
    public void draw(@NonNull Canvas canvas) {
        drawPolygon(canvas);
    }

    private void drawPolygon(Canvas canvas) {
        switch (mPoints.size()) {
            case 1:
                drawSinglePoint(mPoints.get(0), canvas);
                break;
            case 2:
                drawLine(mPoints.get(0), mPoints.get(1), canvas);
                break;
            default:
                drawComplexFigure(canvas);
        }
    }

    public PointF getPoint(int index) {
        while (index >= mPoints.size()) {
            mPoints.add(new PointF());
        }
        return mPoints.get(index);
    }


    private void drawLine(PointF one, PointF two, Canvas canvas) {
        //float size = Math.min(canvas.getWidth(), canvas.getHeight());
        canvas.drawLine(one.x, one.y, two.x, two.y, mPaint);
    }

    private void drawSinglePoint(PointF point, Canvas canvas) {
        // float size = Math.min(canvas.getWidth(), canvas.getHeight());
        float x = point.x;
        float y = point.y;
        canvas.drawPoint(x, y, mPaint);
    }

    private void drawComplexFigure(Canvas canvas) {
        mComplexPath.reset();
        //float size = Math.min(canvas.getWidth(), canvas.getHeight());
        for (PointF point : mPoints) {
            if (mComplexPath.isEmpty()) {
                mComplexPath.moveTo(point.x, point.y);
            } else {
                mComplexPath.lineTo(point.x, point.y);
            }
        }
        mComplexPath.close();
        canvas.drawPath(mComplexPath, mComplexPaint);
    }
    private void init() {
        mPaint = new Paint();
        mPaint.setColor(getColor());
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(getLineWidth());

        mComplexPaint = new Paint(mPaint);
        mComplexPaint.setStyle(Paint.Style.FILL);
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
    public static final Creator<FigurePolygon> CREATOR = new Creator<FigurePolygon>() {
        @Override
        public FigurePolygon createFromParcel(Parcel in) {
            return new FigurePolygon(in);
        }

        @Override
        public FigurePolygon[] newArray(int size) {
            return new FigurePolygon[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(mPoints);
        getFirstPoint().writeToParcel(dest,flags);
        dest.writeInt(getColor());
    }
}
