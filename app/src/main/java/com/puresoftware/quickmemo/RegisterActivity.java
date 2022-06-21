package com.puresoftware.quickmemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {

    CheckBox cbPolicy;
    CheckBox cbPrivacy;
    CheckBox cbAllCheck;
    EditText edtEmail;
    EditText edtPW;
    EditText edtCheckPW;
    EditText edtBirth;
    TextView tvMessage;
    TextView btnOk;

    //    Matcher matcher;
    Matcher EmailMat, PWMat, PWCheckMAt, BirthMat;
    Pattern EmailPattern = Pattern.compile("^(.+)@(.+)$");
    Pattern PWPattern = Pattern.compile("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$");

    String TAG = RegisterActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        cbPolicy = findViewById(R.id.cb_register_activity_policy);
        cbPrivacy = findViewById(R.id.cb_register_activity_personal_privacy);
        cbAllCheck = findViewById(R.id.cb_register_activity_allcheck);
        edtEmail = findViewById(R.id.edt_register_activity_email);
        edtPW = findViewById(R.id.edt_register_activity_pw);
        edtCheckPW = findViewById(R.id.edt_register_activity_checkpw);
        edtBirth = findViewById(R.id.edt_register_activity_birth);
        tvMessage = findViewById(R.id.tv_register_activity_message);
        btnOk = findViewById(R.id.tv_register_activity_ok);

        // 전화번호(정규표현식)이메일(연동) 비밀번호(해시코드)

        // 이메일 => ~~~@~~~.com 있어야함
//       비밀번호 => 최소 10자리 이상 : 영어 대문자, 소문자, 숫자, 특수문자 중 2종류 조합:
//         생년월일 => 19900101 (8자리, 앞 4글자 1900이상, 1900:num1 + 01(1~12):num2 + 01(1~31):num3;

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email = edtEmail.getText().toString().trim();
                String PW = edtPW.getText().toString().trim();
                String checkPW = edtCheckPW.getText().toString().trim();
                String birth = edtBirth.getText().toString();


                //
                if (cbPolicy.isChecked() == true && cbPrivacy.isChecked() == true) {

                    if (Email.equals("") || PW.equals("") || checkPW.equals("") || birth.equals("")) {
                        Toast.makeText(RegisterActivity.this, "내용을 입력해 주세요.", Toast.LENGTH_SHORT).show();

                    } else {

                        EmailMat = EmailPattern.matcher(Email);
                        PWMat = PWPattern.matcher(PW);
                        PWCheckMAt = PWPattern.matcher(checkPW);
                        String year = birth.substring(0, 4); // 1999 99 99    0123 45 67      0~3 / 4~5 / 6~7
                        String month = birth.substring(4, 6);
                        String day = birth.substring(6, 8);

                        errorResult(EmailMat, PWMat, PWCheckMAt, year, month, day);




//                        if (EmailMat.matches() == true &&
//                                PWMat.matches() == true &&
//                                PWCheckMAt.matches() == true &&
//                                birth.length() > 8 &&
//                                PW.trim().equals(checkPW) &&
//                                Integer.parseInt(year) > 1900 &&
//                                Integer.parseInt(month) <= 12 &&
//                                Integer.parseInt(day) <= 31) {
//
//                            Log.i(TAG, "okokok");
//                        }

//                        int errorNumber = 0;
//                        errorNumber = (EmailMat.matches()) ? 0 : 1;
////                        errorNumber = (EmailMat.matches()) ? 0 : 2;
//                        errorNumber = (PWMat.matches()) ? 0 : 3;
//                        errorNumber = (PWCheckMAt.matches()) ? 0 : 4;
//                        errorNumber = (BirthMat.matches()) ? 0 : 5;


//                        if(이메일조건 && 비밀번호조건 && 생년월일조건) {
//
//                        } else {
//                            // 1) 뭘 만족안해서 else로 떨어졌는지 알아오기
//
//                            // 과제
////                            public interface ErrorMessageInterface {
////                                public static final String emailError = "이메일 유효하지 않습니다";
////                            }
////                            ErrorMessageInterface.emailError
//
//                            // 2) 원인별로 에러메시지 보여주기 (interface에 메시지상수담아두기, 에러메시지 담아서 활용해보기)
//
//                        }

//                        matcher = EmailPattern.matcher(Email);
//                        if (matcher.matches() == true) {
//                            Log.i(TAG, "Email 검증:" + "ok!");
//
//                            matcher = PWPattern.matcher(PW);
//                            if (matcher.matches() == true) {
//                                Log.i(TAG, "PW 검증:" + "ok!");
//
//                                matcher = PWPattern.matcher(checkPW);
//                                if (matcher.matches() == true) {
//                                    Log.i(TAG, "checkPW 검증:" + "ok!");
//
//                                    if (birth.length() >= 8) {
//                                        String year = birth.substring(0, 4); // 1999 99 99    0123 45 67      0~3 / 4~5 / 6~7
//                                        String month = birth.substring(4, 6);
//                                        String day = birth.substring(6, 8);
//                                        Log.i(TAG, year + month + day);
//
//                                        if (PW.trim().equals(checkPW)) {
//                                            Log.i(TAG, "비밀번호 서로 일치");
//
//                                            Log.i(TAG, "month: " + Integer.parseInt(month));
//                                            if (Integer.parseInt(year) > 1900 && Integer.parseInt(month) <= 12 && Integer.parseInt(day) <= 31) {
//                                                Log.i(TAG, "생년월일 일치");
//                                            } else {
//                                                Toast.makeText(getApplicationContext(), "옳바른 생년월일 형식이 아닙니다.", Toast.LENGTH_SHORT).show();
//                                            }
//                                        } else {
//                                            Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
//                                        }
//
//                                    }
//
//                                }
//
//
//                            }
//
//                        }

//
//                        OkHttpClient client = new OkHttpClient();
//
////                         post파라미터 구성
//                        RequestBody body = new FormBody.Builder()
//                                .add("userEmail", Email)
//                                .add("userPW", PW)
//                                .add("userBirth", birth)
//                                .build();
////
//                        Request request = new Request.Builder()
//                                .url("https://cfa1-58-239-206-38.ngrok.io/quick/register")
//                                .post(body)
//                                .build();
//
//                        client.newCall(request).enqueue(callback);
                    }

                } else {
                    Log.i(TAG, "not check");
                }
            }
        });
    }

    Callback callback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            Log.i(TAG, "register data failed");
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            Log.i(TAG, "register data succeced");

            // json
            String body = response.body().string();
            Log.i(TAG, body);
        }
    };

    public Toast errorResult(Matcher emailMat, Matcher pwMat, Matcher pwCheckMat, String year, String month, String Day) {

//        int errorNumber = 0;
//        errorNumber = (EmailMat.matches()) ? 0 : 1;
////        errorNumber = 데이터베이스에 해당 이메일이 있는 지 여부;
//        errorNumber = (PWMat.matches()) ? 0 : 3;
//        errorNumber = (PWCheckMAt.matches()) ? 0 : 4;
//        errorNumber = (Integer.parseInt(year) > 1900 && Integer.parseInt(month) <= 12 && Integer.parseInt(Day) <= 31) ? 0 : 5;

//        String errorMessage = "";
//        for (int i = 0; i <= 4; i++) {
//
////            errorMessage = (EmailMat.matches()) ? "이상 없음" : Message.emailError;
//////        errorMessage = 데이터베이스에 해당 이메일이 있는 지 여부;
////            errorMessage = (PWMat.matches()) ? "이상 없음" : Message.emailError;
////            errorMessage = (PWCheckMAt.matches()) ? "이상 없음" : Message.emailError;
////            errorMessage = (Integer.parseInt(year) > 1900 && Integer.parseInt(month) <= 12 && Integer.parseInt(Day) <= 31) ? "이상 없음" : Message.emailError;
//
//        }

//        String errorNumber = "0";
//        errorNumber = (EmailMat.matches()) ? "0" : "1";
////        errorNumber = 데이터베이스에 해당 이메일이 있는 지 여부;
//        errorNumber = (PWMat.matches()) ? "0" : "3";
//        errorNumber = (PWCheckMAt.matches()) ? "0" : "4";
//        errorNumber = (Integer.parseInt(year) > 1900 && Integer.parseInt(month) <= 12 && Integer.parseInt(Day) <= 31) ? "0" : "5";
//
//        for(int i = 0; i<=4; i++){
//            if(errorNumber.contains(String.valueOf(i))){
//
//            }
//
//
//        }

//        int errorNumber = 0;
////        errorNumber = 데이터베이스에 해당 이메일이 있는 지 여부;
//        errorNumber = (PWMat.matches()) ? 0 : 3;
//        errorNumber = (PWCheckMAt.matches()) ? 0 : 4;
//        errorNumber = (Integer.parseInt(year) > 1900 && Integer.parseInt(month) <= 12 && Integer.parseInt(Day) <= 31) ? 0 : 5;
//
//        for (int i = 0; i <= 4; i++) {
//
//            switch (i) {
//
//                case 0:
//                    errorNumber = (EmailMat.matches()) ? 0 : 1;
//
//            }
//        }
        String errorMessage = "null";

        if (EmailMat.matches() == false) {
            errorMessage = Message.emailError;

        } else if (PWMat.matches() == false) {
            errorMessage = Message.pwError;

        } else if (PWCheckMAt.matches() == false) {
            errorMessage = Message.pwCheckError;

        } else if ((Integer.parseInt(year) > 1900 && Integer.parseInt(month) <= 12 && Integer.parseInt(Day) < 31)) {
            errorMessage = Message.birthError;
        } else {
            errorMessage = "회원가입이 완료되었습니다!";
        }

        Toast toast = Toast.makeText(RegisterActivity.this, errorMessage, Toast.LENGTH_SHORT);
        toast.show();

        // 원래 기본이라면 빨간 텍스트 메세지로 해야 합니다.

        return toast;
    }
}