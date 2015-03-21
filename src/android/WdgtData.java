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
    SharedPreferences SharedPref;
    String nombre = "";
    String contacto = "";
    String telefono1 = "";
    String telefono2 = "";

    SharedPref = ctxt.getSharedPreferences("datos", 0);

    if (SharedPref.contains("nombre")) {
      nombre = SharedPref.getString("nombre", "");
    }

    if (SharedPref.contains("contacto")) {
      contacto = SharedPref.getString("contacto", "");
    }

    if (SharedPref.contains("telefono1")) {
      telefono1 = SharedPref.getString("telefono1", "");
    }

    if (SharedPref.contains("telefono2")) {
      telefono1 = SharedPref.getString("telefono2", "");
    }

    sData.add(new DataElement("EmergenciAA", 1));
    sData.add(new DataElement(nombre, 0));
    sData.add(new DataElement("Persona de contacto", 1));
    sData.add(new DataElement(contacto, 0));
    sData.add(new DataElement(telefono1, 0));
    sData.add(new DataElement(telefono2, 0));
    sData.add(new DataElement("Grupo sanguineo", 1));

    Log.v("Wdgt", Integer.toString(sData.size()));
  }

  @Override
  public int getCount() {
    return sData.size();
  }

  @Override
  public RemoteViews getViewAt(int position) {
    RemoteViews row=new RemoteViews(ctxt.getPackageName(),
                                     R.layout.row);
    row.setTextViewText(android.R.id.text1, sData.get(position).text);

    Log.v("Wdgt-", sData.get(position).text);

    return row;
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

 @Override
  public boolean hasStableIds() {
      return true;
  }

 @Override
  public int getViewTypeCount() {
      return 1;
  }

 @Override
  public RemoteViews getLoadingView() {
      return null;
  }

 @Override
  public void onCreate() {

  }

  @Override
  public void onDestroy() {

  }

  @Override
  public void onDataSetChanged() {
    Log.v("Wdgt", "onDataSetChanged");
  }
}
