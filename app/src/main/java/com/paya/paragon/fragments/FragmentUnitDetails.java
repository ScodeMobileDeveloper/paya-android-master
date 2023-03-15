package com.paya.paragon.fragments;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.paya.paragon.R;
import com.paya.paragon.adapter.UnitDetailsAdapter;
import com.paya.paragon.api.projectDetails.Units;
import com.paya.paragon.utilities.ExtendedFragment;

import java.util.ArrayList;

public class FragmentUnitDetails extends ExtendedFragment {
    private ArrayList<Units> units;
    private RecyclerView recyclerView;
    private UnitDetailsAdapter unitDetailsAdapter;
    public static int CONTACT_CLICK=100,FLOR_CLICK=101;

    @Override
    public View onCreateFragmentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_unit_details, null, false);
        view = declarations(view);
        return view;
    }

    private View declarations(View view) {
        recyclerView = view.findViewById(R.id.unit_details_recycler);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
         unitDetailsAdapter = new UnitDetailsAdapter(units, getActivity(), new UnitDetailsAdapter.OnItemClickListener() {
             @Override
             public void onContactClick() {
                 sendData(CONTACT_CLICK,units);
             }

             @Override
             public void onFlorClick(Units units) {
                 sendData(FLOR_CLICK,units);
             }
         });
        recyclerView.setAdapter(unitDetailsAdapter);
        return view;
    }

    public void setData(ArrayList<Units> units) {
        this.units = units;
    }


}
