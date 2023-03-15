package com.paya.paragon.fragments.introSlider;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.paya.paragon.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ThirdFragment extends Fragment {


    public ThirdFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

      /*  new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SessionManager.setFirstTimeLaunch(getActivity(),false);
                Intent toSlider = new Intent(getActivity(), ActivityHome.class);
                startActivity(toSlider);
                getActivity().finish();
            }
        }, 2000);*/

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_third_intro, container, false);

    }

}
