package com.puresoftware.quickmemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class WebTestActivity extends AppCompatActivity {

    Button btnEnter;

    String TAG = WebTestActivity.class.getSimpleName();

    ListView lvWebList;
    WebAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_test);

        btnEnter = findViewById(R.id.btnEnter);
        lvWebList = findViewById(R.id.lv_web_list);
        adapter = new WebAdapter();

        // 1) 객체 생성
        OkHttpClient client = new OkHttpClient();

        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 4) 웹서버에 요청하는 코드 (GET/POST)
                // get/post 코드 차이
                // GET: URL뒤에 ? 붙여서 파라미터 전달하는 방식
                // POST: 파라미터를 URL뒤에 안붙이고 별도의 영역에 첨부하는 방식

                // android okhttp post 전송

                // post파라미터 구성
//                RequestBody body = new FormBody.Builder()
//                        .add("user_name","lee")
//                        .add("user_age","28")
//                        .add("user_gender","male")
//                        .build();
////
////
//                Request request = new Request.Builder()
//                        .url("https://379b-58-239-206-38.ngrok.io/addmember")
//                        .post(body)
//                        .build();

//                Request request = new Request.Builder()
//                        .url("https://379b-58-239-206-38.ngrok.io/getmember?user_name=kim")
//                        .build();
                Request request = new Request.Builder()
                        .url("https://379b-58-239-206-38.ngrok.io/getmemberlist")
                        .build();


                // 5) 요청 날아가는 부분
                client.newCall(request).enqueue(callback);
            }
        });
    }

    // 2) callback 객체 생성
    private Callback callback = new Callback() {

//        ArrayList<UserVO> datas = new ArrayList<>();

        @Override
        public void onFailure(Call call, IOException e) {

        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {

            // 3) Log.i까지
            String body = response.body().string();
            Log.i(TAG, body);

            try {
                JSONArray jsonArray = new JSONArray(body);

                for(int i= 0; i<jsonArray.length(); i++){
                    JSONObject object = jsonArray.getJSONObject(i);
                    String name = object.getString("user_name");
                    int age = object.getInt("user_age");
                    String gender = object.getString("user_gender");

                    MemberDO data = new MemberDO(name, age, gender);

                    adapter.addItem(data);

                    Log.i(TAG,object.getString("user_name")+","+object.getInt("user_age")+","+object.getString("user_gender"));
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        lvWebList.setAdapter(adapter);
                    }
                });

            } catch (JSONException e) {
                e.printStackTrace();
            }

//            try {
//                // 응답으로 온 json문자열을 json객체로 만드는 작업
//                JSONObject jsonObject = new JSONObject(body);
//                String name = jsonObject.getString("user_name");
//                int age = jsonObject.getInt("user_age");
//                String gender = jsonObject.getString("user_gender");
//                Log.i(TAG, name + "," + age + "," + gender);
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }

//            // https://jinsangjin.tistory.com/15
//            try {
////                JSONArray jsonArray = new JSONArray(body);
////                JSONObject jsonObject = jsonArray.getJSONObject(1); // 1번 배열
////                String text = jsonObject.getString("user_id"); // user 가져오기
////                Log.i(TAG,"user_id:"+text);
//
//                JSONArray jsonArray = new JSONArray(body);
//                for (int i = 0; i < jsonArray.length(); i++) {
//                    JSONObject jsonObject = jsonArray.getJSONObject(i);
//
//                    long idx = jsonObject.getLong("idx");
//                    String user_id = jsonObject.getString("user_id");
//                    String user_pw = jsonObject.getString("user_pw");
//                    long user_birth = jsonObject.getLong("user_birth");
//                    long user_register_date = jsonObject.getLong("user_register_date");
//
//                    Log.i(TAG, "idx:" + idx +
//                            ",user_id:" + user_id +
//                            ",user_pw:" + user_pw +
//                            ",user_birth:" + user_birth +
//                            ",user_register_date:" + user_register_date + "\n");
//                }
//
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }


//            Gson gson = new Gson();
//            ArrayList<Object> data = gson.fromJson("UserVO",ArrayList.class);


//                ArrayList<UserVO> data = gson.fromJson("listVO3", ArrayList.class);
//
//                Log.i(TAG, data + "");
//                UserVO userVO = new UserVO();
//                userVO.setIdx(jsonObject.getLong("idx"));
//                userVO.setUser_id(jsonObject.getString("user_id"));
//                userVO.setUser_pw(jsonObject.getString("user_pw"));
//                userVO.setUser_birth(jsonObject.getLong("user_birth"));
//                userVO.setUser_register_date(jsonObject.getLong("user_register_date"));
//                datas.add(userVO);
//                Log.i(TAG, userVO + "");
//            Log.i(TAG, "data size: " + datas.size());
//            try {
//                JSONObject jsonObject = new JSONObject(body);
//                int idx = jsonObject.getInt("idx");
//                Log.i(TAG, "idx:" + idx);
//
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//            Log.i("gugu", "결과: " + body);

//            success => 로그인성공 페이지
//                    fail => 로그인실패 페이지

            // 깃허브 응답 테스트
        }
    };

    public class UserVO {

        long idx;
        String user_id;
        String user_pw;
        long user_birth;
        long user_register_date;

        public long getIdx() {
            return idx;
        }

        public String getUser_id() {
            return user_id;
        }

        public String getUser_pw() {
            return user_pw;
        }

        public long getUser_birth() {
            return user_birth;
        }

        public long getUser_register_date() {
            return user_register_date;
        }

        public void setIdx(long idx) {
            this.idx = idx;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public void setUser_pw(String user_pw) {
            this.user_pw = user_pw;
        }

        public void setUser_birth(long user_birth) {
            this.user_birth = user_birth;
        }

        public void setUser_register_date(long user_register_date) {
            this.user_register_date = user_register_date;
        }
    }
}