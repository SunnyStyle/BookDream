package com.enjoylife.lookworld.ui.IVew;

import com.enjoylife.lookworld.model.entities.movieEntities.Movieinfo;
import com.enjoylife.lookworld.model.entities.usBoxEntities.UsBoxEntity;

/**
 * Created by wangfang on 2017/9/21.
 */

public interface DataView extends MvpView{
    void loadData(UsBoxEntity usBoxEntity, Movieinfo comingSoon, Movieinfo inThreatEntity);
}
