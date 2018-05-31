package com.admin.proyectos.appdesarrolladores;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class FragPeticiones extends Fragment{

    public FragPeticiones() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_peticiones, container, false);

        ArrayList<ListviewContactItem> listContact = GetlistContact();

        ListView listView = (ListView) view.findViewById(R.id.contact_view);
        listView.setAdapter(new ListviewContactAdapter(getActivity(), listContact));

        return view;
    }

    private ArrayList<ListviewContactItem> GetlistContact(){
        ArrayList<ListviewContactItem> contactList = new ArrayList<ListviewContactItem>();

        ListviewContactItem contact = new ListviewContactItem();

        contact.setName("Topher");
        contact.setPhone("01213113568");
        contactList.add(contact);

        contact = new ListviewContactItem();
        contact.setName("Jean");
        contact.setPhone("01213869102");
        contactList.add(contact);

        contact = new ListviewContactItem();
        contact.setName("Andrew");
        contact.setPhone("01213123985");
        contactList.add(contact);

        contact = new ListviewContactItem();
        contact.setName("Judith");
        contact.setPhone("01213123985");
        contactList.add(contact);

        contact = new ListviewContactItem();
        contact.setName("Alvaro");
        contact.setPhone("01213123985");
        contactList.add(contact);

        contact = new ListviewContactItem();
        contact.setName("Armando");
        contact.setPhone("01213123985");
        contactList.add(contact);

        return contactList;
    }

}