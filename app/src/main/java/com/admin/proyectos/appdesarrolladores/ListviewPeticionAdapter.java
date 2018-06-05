package com.admin.proyectos.appdesarrolladores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class ListviewPeticionAdapter extends BaseAdapter {

    private static ArrayList<ListPeticionItem> listPeticion;
    private LayoutInflater mInflater;

    public ListviewPeticionAdapter(Context photosFragment, ArrayList<ListPeticionItem> results){
        listPeticion = results;
        mInflater = LayoutInflater.from(photosFragment);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return listPeticion.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return listPeticion.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ListviewPeticionAdapter.ViewHolder holder;

        if(convertView == null){
            convertView = mInflater.inflate(R.layout.frag_pet_item, null);
            holder = new ListviewPeticionAdapter.ViewHolder();
            holder.txtfolio = (TextView) convertView.findViewById(R.id.itemFolio);
            holder.txtname = (TextView) convertView.findViewById(R.id.itemName);
            holder.txtempresa = (TextView) convertView.findViewById(R.id.itemEmpresa);

            convertView.setTag(holder);
        } else {
            holder = (ListviewPeticionAdapter.ViewHolder) convertView.getTag();
        }

        holder.txtfolio.setText(listPeticion.get(position).getFolio());
        holder.txtname.setText(listPeticion.get(position).getNombre());
        holder.txtempresa.setText(listPeticion.get(position).getNomEmpresa());

        return convertView;
    }

    static class ViewHolder{
        TextView txtfolio, txtname, txtempresa, txttecno;
    }
}
