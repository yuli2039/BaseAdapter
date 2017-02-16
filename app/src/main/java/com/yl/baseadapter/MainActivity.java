package com.yl.baseadapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnList(View v) {
        startActivity(new Intent(this, MultiItemListActivity.class));
    }

    public void btnRecycler(View v) {
        startActivity(new Intent(this, RecyclerViewActivity.class));
    }

    public void btnMultyRecycler(View v) {
        startActivity(new Intent(this, MultiItemRvActivity.class));
    }

}
