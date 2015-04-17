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
  String text2 = null;

  DataElement(String t, int h) {
    text = t;
    header = h;
  }

  DataElement(String t, int h, Uri i, String t2) {
    text = t;
    header = h;
    img = i;
    text2 = t2;
  }
}

public class WdgtData implements RemoteViewsService.RemoteViewsFactory {

  private Context ctxt=null;
  private int appWidgetId;
  private static final ArrayList<DataElement> sData = new ArrayList<DataElement>();
  private int actualizando = 0;

  public WdgtData(Context ctxt, Intent intent) {
      this.ctxt=ctxt;
      appWidgetId=intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                                      AppWidgetManager.INVALID_APPWIDGET_ID);
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
      row.setTextViewText(R.id.nombre, el.text + "\n" + el.text2);
      row.setImageViewUri(R.id.foto, el.img);
    }
    else {
      row = new RemoteViews(ctxt.getPackageName(), el.text.contains("SIP:") ? R.layout.row_sip : R.layout.row);
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
      return 3;
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
    Log.v("Wdgt", "Actualizando: " + Integer.toString(actualizando));

    if (actualizando == 1) {
      return;
    }

    actualizando = 1;

    SharedPreferences SharedPref;
    JSONObject textos = null;
    String idioma = "es";
    String nombre = "";
    String sip = "";
    Uri foto;
    String contacto = "";
    String telefono1 = "";
    String telefono2 = "";
    String grupoSanguineo = "";
    JSONArray enfermedades = null;
    String enfermedadesOtros = "";
    String discapacidad = "";
    JSONArray medicamentos = null;
    String medicamentosOtros = "";
    JSONArray alergias = null;
    String alergiasOtros = "";
    String otrosDatos = "";

    SharedPref = ctxt.getSharedPreferences("datos", 0);

    if (SharedPref.contains("textos")) {
      try {
        textos = new JSONObject(SharedPref.getString("textos", ""));
      }
      catch(Exception e) {
      }
    }

    if (SharedPref.contains("idioma")) {
      idioma = SharedPref.getString("idioma", "");
    }

    if (SharedPref.contains("nombre")) {
      nombre = SharedPref.getString("nombre", "");
    }

    if (SharedPref.contains("sip")) {
      sip = "SIP: " + SharedPref.getString("sip", "");
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
      telefono2 = SharedPref.getString("telefono2", "");
    }

    if (SharedPref.contains("grupoSanguineo")) {
      grupoSanguineo = SharedPref.getString("grupoSanguineo", "");
    }

    if (SharedPref.contains("enfermedades")) {
      try {
        enfermedades = new JSONArray(SharedPref.getString("enfermedades", ""));
      }
      catch(Exception e) {
      }
    }
    else {
      try {
        enfermedades = new JSONArray("[]");
      }
      catch(Exception e) {
      }
    }

    if (SharedPref.contains("enfermedadesOtros")) {
      enfermedadesOtros = SharedPref.getString("enfermedadesOtros", "");
    }

    if (SharedPref.contains("discapacidad")) {
      discapacidad = SharedPref.getString("discapacidad", "");
    }

    if (SharedPref.contains("medicamentos")) {
      try {
        medicamentos = new JSONArray(SharedPref.getString("medicamentos", ""));
      }
      catch(Exception e) {
      }
    }
    else {
      try {
        medicamentos = new JSONArray("[]");
      }
      catch(Exception e) {
      }
    }

    if (SharedPref.contains("medicamentosOtros")) {
      medicamentosOtros = SharedPref.getString("medicamentosOtros", "");
    }

    if (SharedPref.contains("alergias")) {
      try {
        alergias = new JSONArray(SharedPref.getString("alergias", ""));
      }
      catch(Exception e) {
      }
    }
    else {
      try {
        alergias = new JSONArray("[]");
      }
      catch(Exception e) {
      }
    }

    if (SharedPref.contains("alergiasOtros")) {
      alergiasOtros = SharedPref.getString("alergiasOtros", "");
    }

    if (SharedPref.contains("otrosDatos")) {
      otrosDatos = SharedPref.getString("otrosDatos", "");
    }

    sData.clear();

    sData.add(new DataElement(nombre, 0, foto, sip));
    //sData.add(new DataElement(sip, 0));

    try {
      sData.add(new DataElement(textos.getJSONObject("Persona de contacto").getString(idioma), 1));
    }
    catch (Exception e) {
      sData.add(new DataElement("Persona de contacto", 1));
    }
    sData.add(new DataElement(contacto, 0));
    sData.add(new DataElement(telefono1, 0));

    if (!telefono2.equals("")) {
      sData.add(new DataElement(telefono2, 0));
    }

    try {
      sData.add(new DataElement(textos.getJSONObject("Grupo Sanguineo").getString(idioma), 1));
    }
    catch (Exception e) {
      sData.add(new DataElement("Grupo Sanguineo", 1));
    }

    if (!grupoSanguineo.equals("")) {
      sData.add(new DataElement(grupoSanguineo, 0));
    }

    try {
      sData.add(new DataElement(textos.getJSONObject("Enfermedades").getString(idioma), 1));
    }
    catch (Exception e) {
      sData.add(new DataElement("Enfermedades", 1));
    }

    try {

      JSONObject enfermedad;
      int l = enfermedades.length();

      for (int i = 0; i < l; i += 1) {

        enfermedad = enfermedades.getJSONObject(i);

        if (enfermedad.getBoolean("checked") && i != (l -1)) {
          try {
            sData.add(new DataElement(textos.getJSONObject(enfermedad.getString("text")).getString(idioma), 0));
          }
          catch (Exception e) {
            sData.add(new DataElement(enfermedad.getString("text"), 0));
          }
        }

        Log.v("WdgtData", enfermedades.getJSONObject(i).toString());
      }

      if (enfermedades.getJSONObject(l -1).getBoolean("checked")) {
        sData.add(new DataElement(enfermedadesOtros, 0));
      }
    }
    catch(Exception e) {
    }

    try {
      sData.add(new DataElement(textos.getJSONObject("Discapacidad").getString(idioma), 1));
    }
    catch (Exception e) {
      sData.add(new DataElement("Discapacidad", 1));
    }

    if (!discapacidad.equals("")) {
      sData.add(new DataElement(discapacidad, 0));
    }

    try {
      sData.add(new DataElement(textos.getJSONObject("Medicamentos actuales").getString(idioma), 1));
    }
    catch (Exception e) {
      sData.add(new DataElement("Medicamentos actuales", 1));
    }

    try {

      JSONObject medicamento;
      int l = medicamentos.length();

      for (int i = 0; i < l; i += 1) {

        medicamento = medicamentos.getJSONObject(i);

        if (medicamento.getBoolean("checked") && i != (l -1)) {
          try {
            sData.add(new DataElement(textos.getJSONObject(medicamento.getString("text")).getString(idioma), 0));
          }
          catch (Exception e) {
            sData.add(new DataElement(medicamento.getString("text"), 0));
          }
        }

        Log.v("WdgtData", medicamentos.getJSONObject(i).toString());
      }

      if (medicamentos.getJSONObject(l -1).getBoolean("checked")) {
        sData.add(new DataElement(medicamentosOtros, 0));
      }
    }
    catch(Exception e) {
    }

    try {
      sData.add(new DataElement(textos.getJSONObject("Alergias").getString(idioma), 1));
    }
    catch (Exception e) {
      sData.add(new DataElement("Alergias", 1));
    }

    try {

      JSONObject alergia;
      int l = alergias.length();

      for (int i = 0; i < l; i += 1) {

        alergia = alergias.getJSONObject(i);

        if (alergia.getBoolean("checked") && i != (l -1)) {
          try {
            sData.add(new DataElement(textos.getJSONObject(alergia.getString("text")).getString(idioma), 0));
          }
          catch (Exception e) {
            sData.add(new DataElement(alergia.getString("text"), 0));
          }
        }

        Log.v("WdgtData", alergias.getJSONObject(i).toString());
      }

      if (alergias.getJSONObject(l -1).getBoolean("checked")) {
        sData.add(new DataElement(alergiasOtros, 0));
      }
    }
    catch(Exception e) {
    }

    try {
      sData.add(new DataElement(textos.getJSONObject("Otros datos de interes").getString(idioma), 1));
    }
    catch (Exception e) {
      sData.add(new DataElement("Otros datos de interés", 1));
    }

    if (!otrosDatos.equals("")) {
      sData.add(new DataElement(otrosDatos, 0));
    }

    actualizando = 0;
    Log.v("WdgtData", Integer.toString(sData.size()));
  }
}
