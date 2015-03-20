package com.ionicframework.emergenciaa941042;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class WdgtService extends RemoteViewsService {
  @Override
  public RemoteViewsFactory onGetViewFactory(Intent intent) {
    return(new WdgtData(this.getApplicationContext(),
                                 intent));
  }
}
