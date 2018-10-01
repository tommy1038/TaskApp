package jp.techacademy.tomiyama.ryota.taskapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TaskAdapter extends BaseAdapter {

    private LayoutInflater mLayoutInflater;
    private List<Task> mTaskList;

    public static class ViewHolder{
        public TextView titleTextView;
        public TextView timestampTextView;
        public TextView categoryTextView;

        public ViewHolder(View view){
            titleTextView = view.findViewById(R.id.title);
            timestampTextView = view.findViewById(R.id.timestamp);
            categoryTextView = view.findViewById(R.id.category);
        }
    }

    public TaskAdapter(Context context) {
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setTaskList(List<Task> taskList) {
        mTaskList = taskList;
    }

    @Override
    public int getCount() {
        return mTaskList.size();
    }

    @Override
    public Object getItem(int position) {
        return mTaskList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mTaskList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        final ViewHolder viewHolder;

        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.task_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // 後でTaskクラスから情報を取得するように変更する
//        textView1.setText(mTaskList.get(position));
        viewHolder.titleTextView.setText(mTaskList.get(position).getTitle());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.JAPANESE);
        Date date = mTaskList.get(position).getDate();
        viewHolder.timestampTextView.setText(simpleDateFormat.format(date));

        viewHolder.categoryTextView.setText(mTaskList.get(position).getCategory());

        return convertView;
    }
}
