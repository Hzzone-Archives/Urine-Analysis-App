package com.example.hzzone.urine_analysis_app;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.kyleduo.icomet.*;
import com.kyleduo.icomet.demo.Demo;
import com.kyleduo.icomet.message.Message;

import java.lang.reflect.Field;

public class ChatService extends Service {

    private static final String TAG = "ChatService";
    private static final String HOST_IP = "http://www.ideawu.com";
    private static ICometClient mClient;
    private String uname = "www";

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        if (mClient != null) {
            mClient.stopComet();
            mClient.stopConnect();
            mClient = null;
        }
    }

    @Override
    public int onStartCommand(Intent intent,int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        if (mClient.currStatus() == ICometClient.State.STATE_NEW) {
            ICometConf conf = new ICometConf();
            conf.host = HOST_IP;
            conf.port = "8100";
            conf.url = "stream";
            conf.iConnCallback = new MyConnCallback();
            conf.iCometCallback = new MyCometCallback();
            conf.channelAllocator = new MyChannelAllocator();
            new SubTask(conf).execute();
        }

        return Service.START_REDELIVER_INTENT;

    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public ChatService() {
        mClient = ICometClient.getInstance();
        // for reconnect test
        try {
            Field declaredField = mClient.getClass().getDeclaredField("DELAY");
            if (declaredField != null) {
                declaredField.setAccessible(true);
                try {
                    declaredField.set(mClient, new int[]{3, 5, 10});
                } catch (IllegalAccessException | IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public static void stopService() {
        mClient.stopComet();
    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    class SubTask extends AsyncTask<Void, Void, Void> {

        ICometConf conf;

        public SubTask(ICometConf conf) {
            super();
            this.conf = conf;
        }

        @Override
        protected Void doInBackground(Void... params) {
            mClient.prepare(conf);
            mClient.connect();
            return null;
        }

    }

    class MyConnCallback implements IConnCallback {

        @Override
        public void onDisconnect() {
            Log.d(TAG, "onDisconnect");
        }

        @Override
        public void onFail(String arg0) {
            Log.d(TAG, "onFail: "+arg0);
        }

        @Override
        public boolean onReconnect(int arg0) {
            Log.d(TAG, "onReconnect: "+"reconnect, times: " + arg0);
            return false;
        }

        @Override
        public void onReconnectSuccess(int arg0) {
            Log.d(TAG, "onReconnectSuccess: "+"reconnect success, times: " + arg0);
            mClient.comet();
        }

        @Override
        public void onStop() {
            Log.d(TAG, "onStop ");
        }

        @Override
        public void onSuccess() {
            Log.d(TAG, "onSuccess ");
            mClient.comet();
        }

    }

    class MyCometCallback implements ICometCallback {

        @Override
        public void onDataMsgArrived(Message.Content arg0) {
            Log.d(TAG, "onDataMsgArrived: "+arg0.toString());
        }

        @Override
        public void onErrorMsgArrived(Message arg0) {
            Log.d(TAG, "onErrorMsgArrived:"+arg0.toString());
        }

        @Override
        public void onMsgArrived(Message arg0) {
            Log.d(TAG, "onMsgArrived: "+arg0.toString());
        }

        @Override
        public void onMsgFormatError() {
            Log.d(TAG, "onMsgFormatError ");
        }

    }

    class MyChannelAllocator implements ChannelAllocator {

        @Override
        public Channel allocate() {
            Channel channel = new Channel();
            Log.d(TAG, "allocate: "+uname);
            channel.cname = uname;
            channel.seq = 0;
            channel.token = "";
            return channel;
        }

    }
}
