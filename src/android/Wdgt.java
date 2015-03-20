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

  private static final String ACTION_CLICK = "ACTION_CLICK";

  @Override
  public void onUpdate(Context context, AppWidgetManager appWidgetManager,
      int[] appWidgetIds) {

    SharedPreferences SharedPref;
    String nombre = "";
    Uri foto;

    SharedPref = context.getSharedPreferences("datos", 0);

    if (SharedPref.contains("nombre")) {
      nombre = SharedPref.getString("nombre", "");
    }

    if (SharedPref.contains("foto")) {
      foto = Uri.parse(SharedPref.getString("foto", ""));
    }
    else {
      foto = Uri.parse("http://lorempixel.com/80/80");
    }

    // Get all ids
    ComponentName thisWidget = new ComponentName(context,
        Wdgt.class);
    int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
    for (int widgetId : allWidgetIds) {

      RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
          R.layout.widget_layout);

      remoteViews.setTextViewText(R.id.nombre, nombre);
      remoteViews.setImageViewUri(R.id.foto, foto);

      /*
      // Register an onClickListener
      Intent intent = new Intent(context, Wdgt.class);

      intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
      intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

      PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
          0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
      remoteViews.setOnClickPendingIntent(R.id.update, pendingIntent);
      appWidgetManager.updateAppWidget(widgetId, remoteViews);
      */
    }
  }
}
