package edu.ktu.appsas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;


public class GuessHistoryAdapter extends BaseAdapter {
    List<GuessHistoryData> guessHistoryData;
    Context context;

    GuessHistoryAdapter(Context inContext, List<GuessHistoryData> inData){
        context = inContext;
        guessHistoryData = inData;
    }

    @Override
    public int getCount() {
        if(guessHistoryData != null){
            return guessHistoryData.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int id) {
        if(guessHistoryData != null){
            if(id >=0 && id < guessHistoryData.size()){
                return guessHistoryData.get(id);
            }
        }
        return null;
    }

    @Override
    public long getItemId(int id) {
        if (guessHistoryData != null){
            if (id >=0 && id <guessHistoryData.size()){
                return id;
            }
        }
        return -1;
    }

    @Override
    public View getView(int id, View view, ViewGroup viewGroup) {

        View result = view;
        if (result == null){
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            result = layoutInflater.inflate(R.layout.guessed_number_list_item, null); //turi laukus i  kuriuos reikia sudeti duomenis


        }

        GuessHistoryData data = (GuessHistoryData)getItem(id);
        if(data != null){
            TextView guessedNumber = result.findViewById(R.id.list_guessedNumber);
            TextView guessedResult = result.findViewById(R.id.list_result);
            TextView turnsLeft = result.findViewById(R.id.list_turns);

            guessedNumber.setText(Integer.toString(data.number));
            String resultString = "";
            if(data.result > 0)
            {
                resultString="Number was too high!";
            }
            else if (data.result < 0){
                resultString="Number was too low!";
            }
            guessedResult.setText(resultString);
            turnsLeft.setText("Turns left: " + data.turnsLeft);
        }

        return result;
    }
}
