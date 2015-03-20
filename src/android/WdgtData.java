package com.ionicframework.emergenciaa941042;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import android.util.Log;

import android.content.SharedPreferences;

import java.util.ArrayList;

class DataElement {
  String text;
  int header;

  DataElement(String t, int h) {
    text = t;
    header = h;
  }
}

public class WdgtData implements RemoteViewsService.RemoteViewsFactory {

  private Context ctxt=null;
  private int appWidgetId;
  private static final ArrayList<DataElement> sData = new ArrayList<DataElement>();

  public WdgtData(Context ctxt, Intent intent) {
      this.ctxt=ctxt;
      appWidgetId=intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                                      AppWidgetManager.INVALID_APPWIDGET_ID);
  }

  @Override
  public void onCreate() {
    SharedPreferences SharedPref;
    String contacto = "";

    SharedPref = ctxt.getSharedPreferences("datos", 0);

    if (SharedPref.contains("contacto")) {
      contacto = SharedPref.getString("contacto", "");
    }

    sData.add(new DataElement("Persona de contacto", 1));
    sData.add(new DataElement(contacto, 0));
  }

  @Override
  public void onDestroy() {
    // no-op
  }

  @Override
  public int getCount() {
    return(sData.size());
  }

  @Override
  public RemoteViews getViewAt(int position) {
    RemoteViews row=new RemoteViews(ctxt.getPackageName(),
                                     R.layout.row);
    row.setTextViewText(android.R.id.text1, sData.get(position).text);

    Intent i=new Intent();
    Bundle extras=new Bundle();

    extras.putString(Wdgt.EXTRA_DATA, sData.get(position).text);
    Log.v("Wdgt", sData.get(position).text);
    i.putExtras(extras);
    row.setOnClickFillInIntent(android.R.id.text1, i);

    return(row);
  }

  @Override
  public RemoteViews getLoadingView() {
    return(null);
  }

  @Override
  public int getViewTypeCount() {
    return(1);
  }

  @Override
  public long getItemId(int position) {
    return(position);
  }

  @Override
  public boolean hasStableIds() {
    return(true);
  }

  @Override
  public void onDataSetChanged() {
    // no-op
  }
}
