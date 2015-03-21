package com.ionicframework.emergenciaa941042;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import android.util.Log;

import android.content.SharedPreferences;
import android.net.Uri;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

class DataElement {
  String text;
  int header;
  Uri img = null;

  DataElement(String t, int h) {
    text = t;
    header = h;
  }

  DataElement(String t, int h, Uri i) {
    text = t;
    header = h;
    img = i;
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
    Uri foto;
    String contacto = "";
    String telefono1 = "";
    String telefono2 = "";
    String grupoSanguineo = "";
    JSONArray enfermedades;

    SharedPref = ctxt.getSharedPreferences("datos", 0);

    if (SharedPref.contains("nombre")) {
      nombre = SharedPref.getString("nombre", "");
    }

    if (SharedPref.contains("foto")) {
      foto = Uri.parse(SharedPref.getString("foto", ""));
    }
    else {
      foto = Uri.parse("http://lorempixel.com/80/80");
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

    if (SharedPref.contains("grupoSanguineo")) {
      grupoSanguineo = SharedPref.getString("grupoSanguineo", "");
    }

    if (SharedPref.contains("enfermedades")) {
      try {
        enfermedades = new JSONArray(SharedPref.getString("enfermedades", ""));
      }
      catch(JSONException e) {
      }
    }
    else {
      try {
        enfermedades = new JSONArray("[]");
      }
      catch(JSONException e) {
      }
    }

    sData.clear();

    sData.add(new DataElement(nombre, 0, foto));
    sData.add(new DataElement("Persona de contacto", 1));
    sData.add(new DataElement(contacto, 0));
    sData.add(new DataElement(telefono1, 0));

    if (!telefono2.equals("")) {
      sData.add(new DataElement(telefono2, 0));
    }

    sData.add(new DataElement("Grupo sanguineo", 1));

    if (!grupoSanguineo.equals("")) {
      sData.add(new DataElement(grupoSanguineo, 0));
    }

    sData.add(new DataElement("Enfermedades", 1));

    for (int i = 0, l = enfermedades.length(); i < l; i += 1) {
      Log.v("WdgtData", enfermedades.getJSONObject(i).toString());
    }

    Log.v("WdgtData", Integer.toString(sData.size()));
  }


  @Override
  public int getCount() {
    return sData.size();
  }

  @Override
  public RemoteViews getViewAt(int position) {
    DataElement el = sData.get(position);
    RemoteViews row;

    if (el.img != null) {
      row = new RemoteViews(ctxt.getPackageName(), R.layout.foto);
      row.setTextViewText(R.id.nombre, el.text);
      row.setImageViewUri(R.id.foto, el.img);
    }
    else {
      row = new RemoteViews(ctxt.getPackageName(), R.layout.row);
      row.setTextViewText(android.R.id.text1, el.text);

      row.setInt(android.R.id.text1, "setBackgroundColor", android.graphics.Color.parseColor(el.header == 1 ? "#F5F5F5" : "white"));
    }

    Log.v("Wdgt-", el.text);

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
      return 2;
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
