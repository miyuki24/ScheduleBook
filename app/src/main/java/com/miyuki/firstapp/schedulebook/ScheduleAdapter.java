package com.miyuki.firstapp.schedulebook;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;

import io.realm.OrderedRealmCollection;
import io.realm.RealmBaseAdapter;

public class ScheduleAdapter extends RealmBaseAdapter<Schedule> {

    public ScheduleAdapter(@Nullable OrderedRealmCollection<Schedule> data) {
        super(data);
    }

    private int[] images = {R.drawable.image0, R.drawable.image1,R.drawable.image2};

    private static class ViewHolder{
        ImageView cardImage;
        TextView date;
        TextView title;
        TextView detail;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;

        if (view == null){
            view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.costom_cell,viewGroup,false);
            viewHolder = new ViewHolder();
            viewHolder.cardImage = (ImageView) view.findViewById(R.id.card_image);
            viewHolder.date = (TextView) view.findViewById(R.id.date);
            viewHolder.title = (TextView) view.findViewById(R.id.title);
            viewHolder.detail = (TextView) view.findViewById(R.id.detail);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Schedule schedule = adapterData.get(i);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String formatDate = sdf.format(schedule.date);
        viewHolder.date.setText(formatDate);
        viewHolder.title.setText(schedule.title);
        viewHolder.cardImage.setImageResource(images[(int) schedule.id % 3]);
        viewHolder.detail.setText(schedule.detail);

        return view;
    }
}
