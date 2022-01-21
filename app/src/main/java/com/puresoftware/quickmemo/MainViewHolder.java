package com.puresoftware.quickmemo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class MainViewHolder extends RecyclerView.ViewHolder {

    LinearLayout viewMainCard;
    TextView tvDateLeft;
    TextView tvTitleLeft;
    TextView tvContentLeft;

    TextView tvDateRight;
    TextView tvTitleRight;
    TextView tvContenRight;

    String TAG = MainViewHolder.class.getSimpleName();

    public MainViewHolder(Context context, View itemView) {
        super(itemView);

        tvTitleLeft = itemView.findViewById(R.id.tv_main_card_title_left);
        tvDateLeft = itemView.findViewById(R.id.tv_main_card_date_left);
        tvContentLeft = itemView.findViewById(R.id.tv_main_card_content_left);

        tvTitleRight = itemView.findViewById(R.id.tv_main_card_title_right);
        tvDateLeft = itemView.findViewById(R.id.tv_main_card_date_right);
        tvContentLeft = itemView.findViewById(R.id.tv_main_card_content_right);

        viewMainCard = itemView.findViewById(R.id.view_main_card);

        // 실제로 클릭 리스너로 값을 받는 곳. 이곳이 구현되어있어야 클릭의 실제 신호가 가능하다.
        viewMainCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = getAdapterPosition(); // 포지션 가져오기
                Log.i(TAG, position + "번 위치 입력");
                if (position != RecyclerView.NO_POSITION) {
                    Log.i(TAG, tvTitleLeft.getText().toString());
                    Log.i(TAG, tvTitleRight.getText().toString());
                }

            }
        });

    }
}

class Adapter extends RecyclerView.Adapter<MainViewHolder> {

    ArrayList<String> datas = new ArrayList<>();

    interface OnItemClickListener {
        void onItemClick(View v, int position); // 그런 걸 보여준다.
    }

    private OnItemClickListener mListener = null; // 최초 인스턴스

    public void setOnItemClickListeneeeeer(OnItemClickListener listener) {
        mListener = listener;
    }


    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.main_card_item, parent, false);
        MainViewHolder viewHolder = new MainViewHolder(context, view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        holder.tvTitleLeft.setText(datas.get(position));
        holder.tvTitleRight.setText(datas.get(position + 1));


    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    // ListView Adapter에서 만드는 MainActivity에서 Data를 가져오는 메소드 생성
    public void setArrayData(String strData) {
        datas.add(strData);
    }
}
