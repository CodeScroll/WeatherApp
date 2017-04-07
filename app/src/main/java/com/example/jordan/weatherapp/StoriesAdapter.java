package com.example.jordan.weatherapp;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class StoriesAdapter extends BaseAdapter {
    ArrayList<Story> mStories = new ArrayList<Story>();
    Context mContext;
    StoriesAdapter(Context context, ArrayList<Story> stories) {
        mStories = stories;
        mContext = context;
    }
    @Override
    public int getCount() {
        return mStories.size();
    }
    @Override
    public Object getItem(int i) {
        return mStories.get(i);
    }
    @Override
    public long getItemId(int i) {
        return i;
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v = LayoutInflater.from(mContext).inflate(R.layout.item, null);

        TextView tv1 = (TextView) v.findViewById(R.id.txtTitle);
        tv1.setText(mStories.get(i).getTitle());

        TextView tv2 = (TextView) v.findViewById(R.id.txtContent);
        tv2.setText(Html.fromHtml(mStories.get(i).getContent()));
        //tv2.setText(Html.fromHtml(mStories.get(i).getContent() , Html.FROM_HTML_MODE_LEGACY));

        //WebView myWebView = (WebView) v.findViewById(R.id.txtContent);
        //myWebView.loadData(mStories.get(i).getContent(), "text/html", null);


        TextView tv3 = (TextView) v.findViewById(R.id.txtDate);
        tv3.setText(mStories.get(i).getDate());

        weatherFunc(mStories.get(i).getTitle(),v);

        return v;
    }

    private boolean weatherFunc(String alfa, View v) {

        boolean sun = alfa.contains("Rain");

        if ( (alfa.contains("Rain") || alfa.contains("rain")) && ((alfa.contains("Sun") || alfa.contains("sun") || alfa.contains("Sunny") || alfa.contains("sunny"))) ) {

            ImageView mImageView = (ImageView) v.findViewById(R.id.imgOne);
            mImageView.setImageResource(R.mipmap.sun_cloud_rain);


        } else if ((alfa.contains("Rain") || alfa.contains("rain"))) {

            ImageView mImageView = (ImageView) v.findViewById(R.id.imgOne);
            mImageView.setImageResource(R.mipmap.cloud_rain);

        }else if( ( alfa.contains("Cloudy") || alfa.contains("cloudy") ) ){

                if( ( alfa.contains("Sun") || alfa.contains("sun") || alfa.contains("Sunny") || alfa.contains("sunny") ) ){

                    ImageView mImageView = (ImageView) v.findViewById(R.id.imgOne);
                    mImageView.setImageResource(R.mipmap.sun_cloud);
                }else{
                    ImageView mImageView = (ImageView) v.findViewById(R.id.imgOne);
                    mImageView.setImageResource(R.mipmap.cloud);

                }

        }else if( alfa.contains("Sun") || alfa.contains("sun") || alfa.contains("Sunny") || alfa.contains("sunny") ){

            ImageView mImageView = (ImageView) v.findViewById(R.id.imgOne);
            mImageView.setImageResource(R.mipmap.sun);
        }else{

            ImageView mImageView = (ImageView) v.findViewById(R.id.imgOne);
            mImageView.setImageResource(0);

        }


        return sun;
    }

}
