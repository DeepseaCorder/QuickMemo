package com.puresoftware.quickmemo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import jp.wasabeef.richeditor.RichEditor;

public class RichTextEditorActivity extends AppCompatActivity {

    RichEditor richEditor;

    Button btnBold;
    Button btnAlignment;
    Button btnColor;
    Button btncancelline;
    Button btnMitjule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rich_text_editor);

        richEditor = findViewById(R.id.richEditor);
        btnBold = findViewById(R.id.btn_rich_editor_bold);
        btnAlignment = findViewById(R.id.btn_rich_editor_alignment);
        btnColor = findViewById(R.id.btn_rich_editor_color);
        btnMitjule = findViewById(R.id.btn_rich_editor_mitgule);
        btncancelline = findViewById(R.id.btn_rich_editor_canceline);


        btnBold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                richEditor.setBold();

            }
        });

        btnAlignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                richEditor.setAlignRight();

            }
        });
        btnColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                richEditor.setTextColor(Color.BLUE);


            }
        });
        btnMitjule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                richEditor.setUnderline();

                richEditor.setHtml("fdsfdsfdsfkjggdf;lgkre;gldfdgkl;erg;ld<b>fdglkre;v,g</b><br><font color=\"#0000ff\" style=\"\"><b>dsfewfwefwfwfwfwesas</b><br><div style=\"text-align: right;\"><b>sdfsdfsdfsdfsfsfdsfsdf</b></div><div style=\"text-align: right;\"><b><br></b></div></font>");
            }
        });
        btncancelline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                richEditor.setStrikeThrough();
                String body = richEditor.getHtml();
                Log.i("gugu", "content: " + body);
            }
        });

    }
}