package com.puresoftware.quickmemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

    FrameLayout viewMainCard; // 메인카드
    TextView tvDateLeft; // 날짜
    TextView tvTitleLeft; // 타이틀
    RichEditor tvContentLeft; // 내용
    ImageView ivMainCard; // 백그라운드 이미지
    ImageView ivMainCardDelete; // 삭제모드 백그라운드 이미지

//    ArrayList<Memo> datas = new ArrayList<>(); // adapter에서 받을 데이터 저장
//    Memo memo; // MainViewHolder에 사용할 메모
//    Context context; // lockContent에 쓸 context;
//    Activity activity; // MainActivty에 finish()를 쓰기 위한 객체

//    int switchs = 0;// delete 기능 선택,해제 스위치

    String TAG = MainViewHolder.class.getSimpleName();

    public MainViewHolder(Context context, View itemView) {
        super(itemView);

//        // MainViewHolder의 context를 lockContent에 전달하기 위한 멤버.
//        if (this.context == null) {
//            this.context = context;
//        }

        tvTitleLeft = itemView.findViewById(R.id.tv_main_card_title_left);
        tvDateLeft = itemView.findViewById(R.id.tv_main_card_date_left);
        tvContentLeft = itemView.findViewById(R.id.tv_main_card_content);
        ivMainCard = itemView.findViewById(R.id.iv_main_card);
        ivMainCardDelete = itemView.findViewById(R.id.iv_main_card_delete);
        viewMainCard = itemView.findViewById(R.id.view_main_card);

        tvContentLeft.setFocusable(false);

//        // 일반 클릭
//        viewMainCard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                // 1st
//                int positon = getAdapterPosition();
//                memo = datas.get(positon);
//                lockContentTop(memo, context, activity);
//            }
//        });

//        // 롱 클릭
//        viewMainCard.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//
//                return true;
//            }
//        });
    }

    public void lockContentTop(Memo memo, Context context, Activity activity) {

        // 인텐트 데이터를 모아놓기
        Intent intent = new Intent();
        intent.putExtra("title", memo.title);
        intent.putExtra("content", memo.content);
        intent.putExtra("timestamp", memo.timestamp);
        intent.putExtra("lock", memo.star);
        intent.putExtra("star", memo.lock);

        // 락 모드가 true면 PIN을 입력하는 곳으로 가기.
        if (memo.lock == true) {
            intent.setClass(context, PINActivity.class);
            context.startActivity(intent);
            activity.finish(); // mainActivity

            // 락 모드가 false면 바로 수정창으로 가기.
        } else {
            intent.setClass(context, EditActivity.class);
            context.startActivity(intent);
            activity.finish(); // mainActivity
        }
    }
}

class Adapter extends RecyclerView.Adapter<MainViewHolder> {

    //    ArrayList<Memo> datas = new ArrayList<>();
    ArrayList<Memo> datas = new ArrayList<>();
    Activity activity;

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

//        holder.datas = datas; // 홀더에게 전달해 줄 데이터들.
//        holder.activity = activity;

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
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    // MainActivity에서 데이터 가져오기
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

//    // MainActivy에서 MainActivity의 객체 가져오기.
//    public void getMainActivity(Activity activity) {
//        this.activity = activity;
//    }
}
