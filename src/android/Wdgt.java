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

import java.util.ArrayList;

class Datos {
  String texto;
  int cabecera;

  Datos(String t, Boolean c) {
    texto = t;
    cabecera = c;
  }
}


public class Wdgt extends AppWidgetProvider {

  private static final String ACTION_CLICK = "ACTION_CLICK";
  private static final ArrayList<Datos> sData = new ArrayList<Datos>();

  @Override
  public void onUpdate(Context context, AppWidgetManager appWidgetManager,
      int[] appWidgetIds) {

    SharedPreferences SharedPref;
    String nombre = "";
    Uri foto;
    String contacto = "";

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

    sData.add(new Datos("Persona de contacto", 1));

    if (SharedPref.contains("contacto")) {
      contacto = SharedPref.getString("contacto", "");
    }

    sData.add(new Datos(contacto, 0));

    // Get all ids
    ComponentName thisWidget = new ComponentName(context,
        Wdgt.class);
    int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
    for (int widgetId : allWidgetIds) {

      RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
          R.layout.widget_layout);

      Intent intent2 = new Intent(context, Wdgt.class);
      intent2.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
      intent2.setData(sData);

      remoteViews.setTextViewText(R.id.nombre, nombre);
      remoteViews.setImageViewUri(R.id.foto, foto);

      remoteViews.setRemoteAdapter(widgetId, R.id.datos, intent2);


      // Register an onClickListener
      Intent intent = new Intent(context, Wdgt.class);

      intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
      intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

      PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
          0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
      remoteViews.setOnClickPendingIntent(R.id.nombre, pendingIntent);
      appWidgetManager.updateAppWidget(widgetId, remoteViews);
    }
  }
}
