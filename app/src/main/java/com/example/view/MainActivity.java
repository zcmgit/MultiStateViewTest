package com.example.view;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements MultiStateView.StateListener{

    private MultiStateView multiStateView;
    private TestHandler mHandler = new TestHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        multiStateView = (MultiStateView)findViewById(R.id.multistateview);
        multiStateView.setStateListener(this);
        multiStateView.getView(MultiStateView.VIEW_STATE_ERROR)
                .findViewById(R.id.retry)
                .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
                Message msg = mHandler.obtainMessage();
                msg.obj = multiStateView;
                mHandler.sendMessageDelayed(msg, 3000);
            }
        });
    }

    @Override
    public void onStateChanged(int viewState) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private static class TestHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            if (msg.obj instanceof MultiStateView) {
                ((MultiStateView) msg.obj).setViewState(MultiStateView.VIEW_STATE_CONTENT);
            }

            super.handleMessage(msg);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.error:
                multiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
                break;
            case R.id.empty:
                multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
                break;
            case R.id.loading:
                multiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
                break;
            case R.id.content:
                multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onPause() {
        mHandler.removeCallbacksAndMessages(null);
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
}
