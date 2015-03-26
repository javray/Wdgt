package com.ionicframework.emergenciaa941042;

import java.util.Random;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import android.content.SharedPreferences;
import android.net.Uri;

public class Wdgt extends AppWidgetProvider {

  public static String EXTRA_DATA = "com.ionicframework.emergenciaa941042.DATA";

  private static final String ACTION_CLICK = "ACTION_CLICK";

  @Override
  public void onUpdate(Context context, AppWidgetManager appWidgetManager,
      int[] appWidgetIds) {

    // Get all ids
    ComponentName thisWidget = new ComponentName(context,
        Wdgt.class);
    int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
    for (int widgetId : allWidgetIds) {

      RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
          R.layout.widget_layout);

      Intent svcIntent = new Intent(context, WdgtService.class);
      svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
      svcIntent.setData(Uri.parse(svcIntent.toUri(Intent.URI_INTENT_SCHEME)));

      Log.v("Wdgt", Uri.parse(svcIntent.toUri(Intent.URI_INTENT_SCHEME)).toString());

      remoteViews.setRemoteAdapter(widgetId, R.id.datos, svcIntent);

      // Register an onClickListener
      Intent intent = new Intent(context, Wdgt.class);

      intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
      intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

      PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
          0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
      remoteViews.setOnClickPendingIntent(R.id.refrescar, pendingIntent);

      //appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.datos);
      appWidgetManager.updateAppWidget(widgetId, remoteViews);
    }
    super.onUpdate(context, appWidgetManager, appWidgetIds);
  }
}
