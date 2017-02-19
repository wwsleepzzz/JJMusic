/*
 *
 *  *
 *  *  *
 *  *  *  * ===================================
 *  *  *  * Copyright (c) 2016.
 *  *  *  * 作者：安卓猴
 *  *  *  * 微博：@安卓猴
 *  *  *  * 博客：http://sunjiajia.com
 *  *  *  * Github：https://github.com/opengit
 *  *  *  *
 *  *  *  * 注意**：如果您使用或者修改该代码，请务必保留此版权信息。
 *  *  *  * ===================================
 *  *  *
 *  *  *
 *  *
 *
 */

package com.zzz.shiro.jjmusic.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.zzz.shiro.jjmusic.R;
import com.zzz.shiro.jjmusic.Song;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;


public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewHolder> {


  //自定義一個Listener 介面
  public interface AdapterOnItemClickListener {
    void aOnItemClick(View view, int position, MyRecyclerViewHolder holder);


    void aOnItemLongClick(View view, int position);
  }



  public Context mContext;
  public LayoutInflater mLayoutInflater;
  public AdapterOnItemClickListener mOnItemClickListener;
  public BtnListenerInterface btnListenerInterface;

  public List songList; //歌們


    /**
     * 建構子
     * @param mContext
     * @param songList
     */
  public MyRecyclerViewAdapter(Context mContext,List songList) {
    this.mContext = mContext;
    mLayoutInflater = LayoutInflater.from(mContext);

    this.songList = songList;

  }


  /**
   * 建立ViewHolder
   */
  @Override public MyRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View mView = mLayoutInflater.inflate(R.layout.item_main, parent, false);
    MyRecyclerViewHolder mViewHolder = new MyRecyclerViewHolder(mView);
    return mViewHolder;
  }

  /**
   * 綁定ViewHoler，给item中的控件設置data
   */
  @Override public void onBindViewHolder(final MyRecyclerViewHolder holder, final int position) {
    //將 Data 填入
    final Song song = (Song) songList.get(position);
    holder.mTextView.setText(song.getTitle());
//    holder.image_album.setImageBitmap(song.getPic());


//    Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.ggg); //xx
//    holder.image_album.setBackgroundResource(R.drawable.nopic);


    //===============

    AsyncTask asyncTask = new AsyncTask<Void, Void, Bitmap>() {
      @Override
      protected Bitmap doInBackground(Void... params) {
        Bitmap bitmap = song.getPic();
        return bitmap;
      }
      @Override
      protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        //加载完毕后判断该imageView等待的图片url是不是加载完毕的这张
        //如果是则为imageView设置图片,否则说明imageView已经被重用到其他item
        holder.image_album.setImageBitmap(song.getPic());
        holder.image_album.setBackgroundResource(R.drawable.nopic);

        if(holder.image_album.getTag()!=null && holder.image_album.getTag().equals("play")) {
          holder.image_play.setBackgroundResource(R.drawable.play);
        }
        else{
          holder.image_play.setBackgroundResource(0);
        }
      }
    }.execute();



    //設置Listener
    if (mOnItemClickListener != null) {
      holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          mOnItemClickListener.aOnItemClick(holder.itemView, position,holder);

//
//
//          RecyclerView recyclerView = (RecyclerView) holder.itemView
//          View vv = recyclerView.getChildAt(8);
//          if(vv ==null){
//            Log.d("adapter" ,"vv == null");
//          }
//          ImageView image_play = (ImageView) vv.findViewById(R.id.imageView2);
//          image_play.setBackgroundResource(0);

        }
      });

      holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
        @Override public boolean onLongClick(View v) {
          mOnItemClickListener.aOnItemLongClick(holder.itemView, position);
          return true;
        }
      });
    }

    holder.ib_edit.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Log.d("","RecyclerAdapter btn click");
        btnListenerInterface.onClick(position);
      }
    });


  }




  @Override public int getItemCount() {
    return songList.size();
  }



  public void setOnItemClickListener(AdapterOnItemClickListener listener) {
    this.mOnItemClickListener = listener;
  }



  public void setBtnClickListener(BtnListenerInterface listener) {
    this.btnListenerInterface = listener;
  }
}
