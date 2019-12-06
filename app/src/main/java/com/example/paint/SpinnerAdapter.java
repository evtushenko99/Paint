package com.example.paint;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class SpinnerAdapter extends BaseAdapter {
    private List<String> mStrings;

    public SpinnerAdapter(List<String> strings) {
        mStrings = strings;

    }

    @Override
    public int getCount() {
        return mStrings.size();
    }

    @Override
    public String getItem(int position) {
        return mStrings.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.spinner_item, parent, false);
            ViewHolder viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        }
        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.mTextView.setText(getItem(position));
        switch(getItem(position)){
            case "Line":
                viewHolder.mImageView.setImageDrawable(parent.getResources().getDrawable(R.drawable.ic_border_vertical_black_24dp));
                break;
            case "Curve line":
                viewHolder.mImageView.setImageDrawable(parent.getResources().getDrawable(R.drawable.ic_brush_black_24dp));
                break;
            case "Rectangle":
                viewHolder.mImageView.setImageDrawable(parent.getResources().getDrawable(R.drawable.ic_box_black_24dp));
                break;
            case "Polygon":
                viewHolder.mImageView.setImageDrawable(parent.getResources().getDrawable(R.drawable.ic_gesture_black_24dp));
                break;

        }
        return convertView;
    }

    private static class ViewHolder {
        private final TextView mTextView;
        private final ImageView mImageView;

        private ViewHolder(@NonNull View view) {
            mTextView = view.findViewById(R.id.spinner_text);
            mImageView = view.findViewById(R.id.spinner_image);
        }
    }
}
