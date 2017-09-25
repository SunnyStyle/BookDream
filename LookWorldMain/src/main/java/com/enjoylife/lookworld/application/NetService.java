package com.enjoylife.lookworld.application;


import com.enjoylife.lookworld.model.entities.movieEntities.Movieinfo;
import com.enjoylife.lookworld.model.entities.usBoxEntities.UsBoxEntity;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by wangfang on 2017/9/21.
 */

public interface NetService {

    @GET("/v2/movie/us_box")
    Observable<UsBoxEntity> getHotMovie();

    @GET("/v2/movie/in_theaters")
    Observable<Movieinfo> getInTheaters();

    @GET("/v2/movie/coming_soon")
    Observable<Movieinfo> getComingSoon();

    @GET("/v2/movie/top250")
    Observable<Movieinfo> getTop250();
}
