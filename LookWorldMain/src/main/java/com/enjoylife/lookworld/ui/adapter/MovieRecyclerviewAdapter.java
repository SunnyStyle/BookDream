package com.enjoylife.lookworld.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.enjoylife.lookworld.model.entities.movieEntities.Movieinfo;
import com.enjoylife.lookworld.model.entities.movieEntities.SubjectMovie;
import com.enjoylife.lookworld.model.entities.usBoxEntities.Subject;
import com.enjoylife.lookworld.model.entities.usBoxEntities.UsBoxEntity;
import com.enjoylife.bookdream.R;
import com.enjoylife.lookworld.application.Log;
import com.enjoylife.lookworld.application.MyApplication;
import com.enjoylife.bookdream.databinding.MovieRecyclerviewItemBinding;

import java.util.List;

/**
 * Created by wangfang on 2017/9/21.
 */

public class MovieRecyclerviewAdapter extends RecyclerView.Adapter<MovieRecyclerviewAdapter.MovieHolder>{
    private String[] _movieTitle;
    private Context mContext;
    private List<Object> _list;
    Log log = Log.YLog();

    public MovieRecyclerviewAdapter(Context context, String[] movieTitle, List<Object> lists) {
        mContext = context;
        _movieTitle = movieTitle;
        _list = lists;
        log.d("_list.size() = " + _list.size());
    }

    @Override
    public MovieRecyclerviewAdapter.MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        log.d("onCreateViewHolder() 333333333333" + _list.size());
        MovieRecyclerviewItemBinding bing = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.movie_recyclerview_item,parent,false);
        return new MovieHolder(bing);
    }

    @Override
    public void onBindViewHolder(MovieRecyclerviewAdapter.MovieHolder holder, int position) {
        log.d("position = " + position);
        if(position == 2){
            UsBoxEntity usBoxEntity = (UsBoxEntity) _list.get(position);
            List<Subject> subject_ = usBoxEntity.getSubjectList();
            MyApplication.getPicasso().with(mContext.getApplicationContext())
                    .load(subject_.get(0).getSubject().getImages().getLarge()).into(holder.binding.imagePicOne);
            MyApplication.getPicasso().with(mContext.getApplicationContext())
                    .load(subject_.get(1).getSubject().getImages().getLarge()).into(holder.binding.imagePicTwo);
            MyApplication.getPicasso().with(mContext.getApplicationContext())
                    .load(subject_.get(2).getSubject().getImages().getLarge()).into(holder.binding.imagePicThree);
            holder.binding.tvMovieNameOne.setText(subject_.get(0).getSubject().getTitle());
            holder.binding.tvMovieNameTwo.setText(subject_.get(1).getSubject().getTitle());
            holder.binding.tvMovieNameThree.setText(subject_.get(2).getSubject().getTitle());
        }else{
            Movieinfo moveInfo = (Movieinfo) _list.get(position);
            List<SubjectMovie> subject_ = moveInfo.getSubjects();
            log.d("subject_.get(0) = " + subject_.get(0));
            log.d("subject_.get(0).getSubject() = " + subject_.get(0).getImages());
            log.d("holder.binding.imagePicOne = " + holder.binding.imagePicOne);
            MyApplication.getPicasso().with(mContext.getApplicationContext())
                    .load(subject_.get(0).getImages().getLarge()).into(holder.binding.imagePicOne);
            MyApplication.getPicasso().with(mContext.getApplicationContext())
                    .load(subject_.get(1).getImages().getLarge()).into(holder.binding.imagePicTwo);
            MyApplication.getPicasso().with(mContext.getApplicationContext())
                    .load(subject_.get(2).getImages().getLarge()).into(holder.binding.imagePicThree);
            holder.binding.tvMovieNameOne.setText(subject_.get(0).getTitle());
            holder.binding.tvMovieNameTwo.setText(subject_.get(1).getTitle());
            holder.binding.tvMovieNameThree.setText(subject_.get(2).getTitle());

        }


    }

    @Override
    public int getItemCount() {
        return _movieTitle.length;
    }

    public static class MovieHolder extends RecyclerView.ViewHolder{
        public MovieRecyclerviewItemBinding getBinding() {
            return binding;
        }

        public void setBinding(MovieRecyclerviewItemBinding binding) {
            this.binding = binding;
        }

        static MovieRecyclerviewItemBinding binding;
        int index;

        public MovieHolder(MovieRecyclerviewItemBinding itemBinding) {
            super(itemBinding.getRoot());
            binding = itemBinding;
        }

        /*// 创建一个静态获取方法
        static MovieHolder create(LayoutInflater inflater, ViewGroup parent) {
            binding = DataBindingUtil.inflate(inflater, R.layout.movie_recyclerview_item, parent, false);
            return new MovieHolder(binding);
        }*/

    }
}
