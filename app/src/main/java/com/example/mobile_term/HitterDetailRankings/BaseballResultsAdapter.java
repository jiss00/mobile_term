package com.example.mobile_term.HitterDetailRankings;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mobile_term.R;

import java.util.List;

public class BaseballResultsAdapter extends BaseAdapter {
    private Context context;
    private List<BaseballResult> baseballResults;

    public BaseballResultsAdapter(Context context, List<BaseballResult> baseballResults) {
        this.context = context;
        this.baseballResults = baseballResults;
    }

    @Override
    public int getCount() {
        return baseballResults.size();
    }

    @Override
    public Object getItem(int position) {
        return baseballResults.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_baseball_result, null);
        }

        // 데이터 가져오기
        BaseballResult result = baseballResults.get(position);

        // 화면에 표시할 내용 설정
        TextView playerNameTextView = convertView.findViewById(R.id.textViewPlayerName);
        TextView teamNameTextView = convertView.findViewById(R.id.textViewTeamName);
        TextView avgTextView = convertView.findViewById(R.id.textViewAvg);

        playerNameTextView.setText(result.getPlayerName());
        teamNameTextView.setText(result.getTeamName());
        avgTextView.setText(result.getValue());

        return convertView;
    }
}
