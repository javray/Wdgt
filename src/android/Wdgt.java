package com.javray.cordova.plugin;

import java.util.Random;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import android.content.res.Resources;

public class Wdgt extends AppWidgetProvider {

  private static final String ACTION_CLICK = "ACTION_CLICK";

  @Override
  public void onUpdate(Context context, AppWidgetManager appWidgetManager,
      int[] appWidgetIds) {

    // Get all ids
    ComponentName thisWidget = new ComponentName(context,
        Wdgt.class);
    int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
    for (int widgetId : allWidgetIds) {
      // create some random data
      int number = (new Random().nextInt(100));

      String package_name = getApplication().getPackageName();
      Resources resources = getApplication().getResources();

      RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
          resources.getIdentifier("widget_layout", "layout", package_name));
          //R.layout.widget_layout);
      Log.w("WidgetExample", String.valueOf(number));
      // Set the text
      //remoteViews.setTextViewText(R.id.update, String.valueOf(number));
      remoteViews.setTextViewText(resources.getIdentifier("update", "id", package_name), String.valueOf(number));

      // Register an onClickListener
      Intent intent = new Intent(context, Wdgt.class);

      intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
      intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

      PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
          0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
      //remoteViews.setOnClickPendingIntent(R.id.update, pendingIntent);
      remoteViews.setOnClickPendingIntent(resources.getIdentifier("update", "id", package_name), pendingIntent);
      appWidgetManager.updateAppWidget(widgetId, remoteViews);
    }
  }
}
