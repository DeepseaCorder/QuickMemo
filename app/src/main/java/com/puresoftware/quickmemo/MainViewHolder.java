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
import java.util.List;
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

    ArrayList<Memo> datas = new ArrayList<>();
    MainViewHolder selectHolder; // 선택될 홀더들. 이렇게 해서 뽑아야 아이템을 얻을 수 있다.

    Adapter.OnItemClickListener listener;
    Adapter.OnItemLongClickListener longListener;

    public MainViewHolder(Context context, View itemView) {
        super(itemView);

        tvTitleLeft = itemView.findViewById(R.id.tv_main_card_title_left);
        tvDateLeft = itemView.findViewById(R.id.tv_main_card_date_left);
        tvContentLeft = itemView.findViewById(R.id.tv_main_card_content);
        ivMainCard = itemView.findViewById(R.id.iv_main_card);
        ivMainCardDelete = itemView.findViewById(R.id.iv_main_card_delete);
        viewMainCard = itemView.findViewById(R.id.view_main_card);
        tvContentLeft.setFocusable(false);

        // click 구현부, 데이터들은 애초에 Main에서 처리.
        ivMainCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    if (listener != null) {
                        listener.onItemClick(view, position);
                    }
                }
            }
        });

        ivMainCard.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    if (longListener != null) {
                        longListener.onItemLongClick(view, position);
                    }
                }
                return false;
            }
        });
    }
}

class Adapter extends RecyclerView.Adapter<MainViewHolder> {

    public String TAG = Adapter.class.getSimpleName();

    // 데이터
    ArrayList<Memo> datas = new ArrayList<>();
    MainViewHolder holder;

    // onClick 인터페이스
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    // 리스너 객체, Holder에 전달해야 함.
    private OnItemClickListener onItemClickListener = null;

    // 액티비티에서 호출시킬 리사이클러뷰
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    // onLongClick 인터페이스
    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }

    // 리스너 객체, Holder에 전달해야 함.
    private OnItemLongClickListener onItemLongClickListener = null;

    // 액티비티에서 호출시킬 리사이클러뷰
    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.onItemLongClickListener = listener;
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

        holder.datas = datas;
        holder.selectHolder = holder;
        holder.listener = onItemClickListener; // 내만 이렇게 하는 듯.
        holder.longListener = onItemLongClickListener; // 내만 이렇게 하는 듯.

        datas.get(position);
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

    //잠금 컨텐츠에 대한 표시여부,
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


