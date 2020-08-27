package com.lib.ui;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lib.base.view.bottom.LBottomListSheet;
import com.lib.base.view.bottom.LBottomSheet;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void bottomDialog(View view) {
//        LBottomSheet sheet = new LBottomSheet(this);
//        TextView textView = new TextView(this);
//        textView.setText("abv");
//        textView.setGravity(Gravity.CENTER);
//        sheet.setContentView(textView);
//        sheet.show();

        LBottomListSheet sheet1 = new LBottomListSheet(this);
        sheet1.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                return new ViewHolder(new TextView(MainActivity.this));
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                TextView textView = (TextView) viewHolder.itemView;
                textView.setText(i + "    行");
                textView.setBackgroundColor(Color.YELLOW);
                textView.setLayoutParams(new RecyclerView.LayoutParams(-1,-2));
            }

            @Override
            public int getItemCount() {
                return 100;
            }
        });
        sheet1.show();

    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
