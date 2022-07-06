package com.puresoftware.quickmemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
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

    String TAG = MainViewHolder.class.getSimpleName();

    Adapter.OnItemClickListener listener;

    public MainViewHolder(Context context, View itemView) {
        super(itemView);

        tvTitleLeft = itemView.findViewById(R.id.tv_main_card_title_left);
        tvDateLeft = itemView.findViewById(R.id.tv_main_card_date_left);
        tvContentLeft = itemView.findViewById(R.id.tv_main_card_content);
        ivMainCard = itemView.findViewById(R.id.iv_main_card);
        ivMainCardDelete = itemView.findViewById(R.id.iv_main_card_delete);
        viewMainCard = itemView.findViewById(R.id.view_main_card);
        tvContentLeft.setFocusable(false);

        ivMainCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(view, position);
                }
            }
        });
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

    public String TAG = Adapter.class.getSimpleName();

    // 데이터
    ArrayList<Memo> datas = new ArrayList<>();

    // 메소드를 호출하기 전까지는 null 상태;

    // onclick가 끝이 나면 이 인터페이스를 통해 리턴받는다?
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public OnItemClickListener onItemClickListener = null;

    // 액티비티에서 호출시킬 리사이클러뷰
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
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

        datas.get(position);
        Log.i("TAG", "position:" + position); // 반복문 체계로 돌아가는 메소드임이 확실함.

        holder.tvTitleLeft.setText(datas.get(position).title);

        // 내만 이렇게 하는 듯.
        holder.listener = onItemClickListener;

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
}
