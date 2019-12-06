package com.example.paint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.paint.Model.FigureCurveLine;
import com.example.paint.Model.FigureDrawble;
import com.example.paint.Model.FigureLine;
import com.example.paint.Model.FigurePolygon;
import com.example.paint.Model.FigureRectangle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.view.MotionEvent.ACTION_CANCEL;
import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_MOVE;
import static android.view.MotionEvent.ACTION_UP;

public class DrawView extends View {
    private static final float STROKE_WIDHT = 10f;
    private List<FigureDrawble> mFigureList = new ArrayList<>(64);
    private int mModeLine;
    private int mModeCurveLine;
    private int mModeRectangle;

    private int mCurrentColor = Color.BLACK;

    private int choice = 1;

    private FigureRectangle mRectangleFigure = null;
    private FigureCurveLine mCurveLineFigure = null;
    private FigureLine mLineFigure = null;
    private FigurePolygon mPolygonFigure = null;

    public void setChoice(int choice) {
        this.choice = choice;
    }

    public void setCurrentColor(int currentColor) {
        mCurrentColor = currentColor;
    }

    public DrawView(Context context) {
        this(context, null);
    }

    public DrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        List<String> modes = Arrays.asList(context.getResources().getStringArray(R.array.spinner));
        mModeLine = modes.indexOf("Line");
        mModeCurveLine = modes.indexOf("Curve line");
        mModeRectangle = modes.indexOf("Rectangle");
        initpaint();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (choice == mModeLine) {
            drawLine(event);
        } else if (choice == mModeCurveLine) {
            drawCurveLine(event);
        } else if (choice == mModeRectangle) {
            drawRectangle(event);
        } else drawPolygon(event);

        return true;
    }

    private void drawPolygon(MotionEvent event) {
        PointF currentPoint = null;
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                mPolygonFigure = new FigurePolygon(mCurrentColor, event);
                mFigureList.add(mPolygonFigure);
                currentPoint = mPolygonFigure.getPoint(0);
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                int pointerId = event.getPointerId(event.getActionIndex());
                currentPoint = mPolygonFigure.getPoint(pointerId);
                break;
            case MotionEvent.ACTION_MOVE:
                for (int i = 0; i < event.getPointerCount(); i++) {
                    int pId = event.getPointerId(i);
                    mPolygonFigure.getPoint(pId).x = (event.getX(i));
                    mPolygonFigure.getPoint(pId).y = (event.getY(i));
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        if (currentPoint != null) {
            currentPoint.x = (event.getX(event.getActionIndex()));
            currentPoint.y = (event.getY(event.getActionIndex()));
        }
    }

    private void drawLine(MotionEvent event) {
        float eventX = event.getX();
        float eventY = event.getY();

        switch (event.getAction()) {
            case ACTION_DOWN:
                mLineFigure = new FigureLine(mCurrentColor, event);
                mFigureList.add(mLineFigure);
                break;
            case ACTION_MOVE:
                mLineFigure.setLinePoint(eventX, eventY);
                invalidate();
                break;
            case ACTION_UP:
            case ACTION_CANCEL:
                break;
            default:
                break;
        }
    }

    private void drawCurveLine(MotionEvent event) {
        float eventX = event.getX();
        float eventY = event.getY();
        switch (event.getAction()) {
            case ACTION_DOWN:
                mCurveLineFigure = new FigureCurveLine(mCurrentColor, event);
                mFigureList.add(mCurveLineFigure);
                break;
            case ACTION_MOVE:
                mCurveLineFigure.setLinePoint(eventX, eventY);
                invalidate();
                break;
            case ACTION_UP:
            case ACTION_CANCEL:
                break;
            default:
                break;
        }
    }

    private void drawRectangle(MotionEvent event) {
        float eventX = event.getX();
        float eventY = event.getY();
        int action = event.getAction();
        switch (action) {
            case ACTION_DOWN:
                mRectangleFigure = new FigureRectangle(mCurrentColor, event);
                mFigureList.add(mRectangleFigure);
                break;
            case ACTION_MOVE:
                mRectangleFigure.setLinePoint(eventX, eventY);
                invalidate();
                break;
            case ACTION_UP:
            case ACTION_CANCEL:
                break;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (FigureDrawble figureDrawble : mFigureList) {
            figureDrawble.draw(canvas);
        }
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(state);
        SavedState ourState = (SavedState) state;
        mFigureList = ourState.mDrawables;
        invalidate();
    }

    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState state = new SavedState(superState);
        state.mDrawables = mFigureList;
        return state;
    }

    public void clear() {
        mFigureList.clear();
        invalidate();
    }

    private void initpaint() {
        Paint paint = new Paint();
        paint.setColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        paint.setStrokeWidth(STROKE_WIDHT);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);

    }
    private static class SavedState extends BaseSavedState {

        // Необходимая часть Parcelable, создаёт один объект и массив
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel source) {
                return new SavedState(source);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };

        private List<FigureDrawble> mDrawables = new ArrayList<>(64);
        // Этот конструктор вызывается при восстановлении
        public SavedState(Parcel source) {
            super(source);
           // mDrawables = source.createTypedArrayList(FigureDrawble.CREATOR);
        }



        // А этот — при сохранении
        public SavedState(Parcelable superState) {
            super(superState);
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(mDrawables.size());
            out.writeTypedList(mDrawables);
        }
    }
   /* private float toRangeX(float x) {
        float localX = x - mDrawingBounds.left;
        return localX / mDrawingBounds.width();
    }

    private float toRangeY(float y) {
        float localY = y - mDrawingBounds.top;
        return localY / mDrawingBounds.height();
    }*/
}
