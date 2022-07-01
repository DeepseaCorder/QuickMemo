package com.puresoftware.quickmemo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.puresoftware.quickmemo.artifacts.HomeDO;
import com.puresoftware.quickmemo.room.Memo;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import jp.wasabeef.richeditor.RichEditor;


public class MainViewHolder extends RecyclerView.ViewHolder {

    FrameLayout viewMainCard;
    TextView tvDateLeft;
    TextView tvTitleLeft;
    RichEditor tvContentLeft;
    ImageView ivMainCard;
    ImageView ivMainCardDelete;

    int switchs = 0;

    String TAG = MainViewHolder.class.getSimpleName();

    public MainViewHolder(Context context, View itemView) {
        super(itemView);

        tvTitleLeft = itemView.findViewById(R.id.tv_main_card_title_left);
        tvDateLeft = itemView.findViewById(R.id.tv_main_card_date_left);
        tvContentLeft = itemView.findViewById(R.id.tv_main_card_content);
        ivMainCard = itemView.findViewById(R.id.iv_main_card);
        ivMainCardDelete = itemView.findViewById(R.id.iv_main_card_delete);
        viewMainCard = itemView.findViewById(R.id.view_main_card);

        // 일반 클릭
        viewMainCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = getAdapterPosition(); // 포지션 가져오기
                Log.i(TAG, position + "번 위치 입력");

                // 포지션 값
                if (position != RecyclerView.NO_POSITION) {
                    Log.i(TAG, tvTitleLeft.getText().toString());
                }
            }
        });

        // 롱 클릭
        viewMainCard.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                int position = getAdapterPosition(); // 포지션 가져오기
                Log.i(TAG, position + "번 롱클릭 위치 입력");

                // 포지션 값
                if (position != RecyclerView.NO_POSITION) {
                    Log.i(TAG, tvTitleLeft.getText().toString());
                }

                if (switchs == 0) {
                    switchs = 1;
                    Toast.makeText(context, "삭제모드", Toast.LENGTH_SHORT).show();

                    // 오류 코드
                    viewMainCard.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int position = getAdapterPosition(); // 포지션 가져오기
                            ivMainCardDelete.setVisibility(View.VISIBLE);
                        }
                    });

                } else {
                    switchs = 0;
                    Toast.makeText(context, "일반모드", Toast.LENGTH_SHORT).show();

                }

//                if (viewMainCard.isLongClickable() == true) {
//                    Log.i(TAG, "롱터치 상태:" + "ok");
//
//                } else {
//                    Log.i(TAG, "롱터치 상태:" + "no");
//                }

                return true;
            }
        });
    }
}

class Adapter extends RecyclerView.Adapter<MainViewHolder> {

    ArrayList<Memo> datas = new ArrayList<>();

//    // MainActivity에 클릭 이벤트를 전달하기 위한 메소드 생성. 쓸모없음.
//    interface OnItemClickListener {
//        void onItemClick(View v, int position); // 그런 걸 보여준다.
//    }
//
//    private OnItemClickListener mListener = null; // 최초 인스턴스
//
//    public void setOnItemClickListeneeeeer(OnItemClickListener listener) {
//        mListener = listener;
//    }

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

        datas.get(position);
        Log.i("TAG", "position:" + position); // 반복문 체계로 돌아가는 메소드임이 확실함.

        holder.tvTitleLeft.setText(datas.get(position).title);

        if (datas.get(position).star == false && datas.get(position).lock == false) {
            holder.ivMainCard.setImageResource(R.drawable.home_memo_ex);
            lockContent(0, holder, datas, position);

        } else if (datas.get(position).star == true && datas.get(position).lock == false) {
            holder.ivMainCard.setImageResource(R.drawable.home_memo_impo);
            lockContent(0, holder, datas, position);

        } else if (datas.get(position).star == false && datas.get(position).lock == true) {
            holder.ivMainCard.setImageResource(R.drawable.home_memo_ex_lock);
            lockContent(1, holder, datas, position);

        } else if (datas.get(position).star == true && datas.get(position).lock == true) {
            holder.ivMainCard.setImageResource(R.drawable.home_memo_impo_lock);
            lockContent(1, holder, datas, position);
        }

//        holder.tvContentLeft.setHtml(datas.get(position).content);
//        holder.tvContentLeft.setInputEnabled(false);
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd a H:mm", Locale.KOREA);
//        holder.tvDateLeft.setText(sdf.format(datas.get(position).timestamp));
//        sdf = null;
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    // ListView Adapter에서 만드는 MainActivity에서 Data를 가져오는 메소드 생성
    public void setArrayData(Memo memo) {
        datas.add(memo);
    }

    // 잠금 컨텐츠에 대한 표시여부,
    public void lockContent(int lock, MainViewHolder holder, ArrayList<Memo> datas, int position) {

        switch (lock) {

            case 0:
                holder.tvContentLeft.setHtml(datas.get(position).content);
                holder.tvContentLeft.setInputEnabled(false);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd a H:mm", Locale.KOREA);
                holder.tvDateLeft.setText(sdf.format(datas.get(position).timestamp));
                sdf = null;
                break;

            case 1:
                holder.tvContentLeft.setHtml("");
                holder.tvContentLeft.setInputEnabled(false);
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy.MM.dd a H:mm", Locale.KOREA);
                holder.tvDateLeft.setText(sdf2.format(datas.get(position).timestamp));
                break;
        }
    }
}
