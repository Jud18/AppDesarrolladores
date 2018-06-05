package com.admin.proyectos.appdesarrolladores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class AdapterComponente extends ArrayAdapter<Componente> {

    public AdapterComponente(Context context, ArrayList<Componente> componentes) {
        super(context, 0, componentes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Componente componente = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_componente, parent, false);
        }
        // Lookup view for data population
        TextView txtCom = (TextView) convertView.findViewById(R.id.txtCom);
        TextView txtTec = (TextView) convertView.findViewById(R.id.txtTec);
        TextView txtMin = (TextView) convertView.findViewById(R.id.txtMin);
        TextView txtMax = (TextView) convertView.findViewById(R.id.txtMax);
        TextView txtTim = (TextView) convertView.findViewById(R.id.txtEst);

        // Populate the data into the template view using the data object
        txtCom.setText(componente.getNombre());
        txtTec.setText(componente.getTecnologia());
        txtMin.setText(componente.getMinTime());
        txtMax.setText(componente.getMaxTime());
        txtTim.setText(componente.getEstimado());

        // Return the completed view to render on screen
        return convertView;
    }

}
