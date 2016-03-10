package com.cegrano.android.photopageview.demo;

import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextPaint;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cegrano.android.photopageview.BasePhotoPageAdapter;
import com.cegrano.android.photopageview.PhotoPageView;
import com.cegrano.android.photopageview.R;
import com.cegrano.android.photopageview.SimplePhotoPageAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        PhotoPageView pp = (PhotoPageView) findViewById(R.id.photo_page);
        SimplePhotoPageAdapter<Object> adapter =new SimplePhotoPageAdapter<Object>() {
            @Override
            public PhotoPageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = View.inflate(parent.getContext(),R.layout.item_photo,null);
                return new PhotoPageViewHolder(view,viewType);
            }

            @Override
            public void onBindViewHolder(PhotoPageViewHolder holder, int position) {
                ImageView iv = (ImageView) holder.itemView.findViewById(R.id.img);
                if (position == 0)
                    iv.setImageResource(R.mipmap.img);
                else if (position%2 == 0)
                    iv.setImageResource(R.color.colorAccent);
                else
                    iv.setImageResource(R.color.colorPrimary);
                ((TextView)holder.itemView.findViewById(R.id.id)).setText(position+"");
            }
        };
        adapter.addData(new Object());
        adapter.addData(new Object());
        adapter.addData(new Object());
        adapter.addData(new Object());
        adapter.addData(new Object());
        adapter.addData(new Object());
//        pp.setCanCycle(false);
        pp.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
