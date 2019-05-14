package edu.ktu.appsas;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AchievementsAdapter extends ArrayAdapter<AchievementsEntry> {
    ArrayList<AchievementsEntry> dataList;
    Activity activity;

    Context context;


    public AchievementsAdapter(Context context, ArrayList<AchievementsEntry> d) {
        super(context, R.layout.achievements_item_list);
        this.context = context;
        dataList = d;
    }

    public int getCount() {
        if (dataList != null) {
            return dataList.size();
        }
        return 0;
    }

    public long getItemId(int position) {
        return position;
    }

    public AchievementsEntry getItem(int position) {
        if (dataList != null) {
            return dataList.get(position);
        }
        return null;
    }


    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View vi = convertView;
        if (vi == null) {
            LayoutInflater li = LayoutInflater.from(getContext());
            vi = li.inflate(R.layout.achievements_item_list, null);
        }

        TextView nameText = (TextView) vi.findViewById(R.id.listview_name);
        TextView numberText = (TextView) vi.findViewById(R.id.listview_number);
        TextView turnsText = (TextView) vi.findViewById(R.id.listview_turns);
        TextView difficultyText = (TextView) vi.findViewById(R.id.listview_difficulty);

        AchievementsEntry le = dataList.get(position);

        nameText.setText(le.getName());
        numberText.setText(Integer.toString(le.getNumber()));
        turnsText.setText(Integer.toString(le.gerTurns()));
        difficultyText.setText(le.getDifficulty());

        return vi;
    }


}
