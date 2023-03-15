package com.paya.paragon.utilities;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public abstract class ExtendedFragment extends Fragment {
    public abstract View onCreateFragmentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return onCreateFragmentView(inflater,container,savedInstanceState);
    }


    /*
     * Listener setup
     */
    public interface OnFragmentChangeListener{
        void onBack();
        void onCompleted();
        void onError();
        void onDataArrive(int requestCode, Object data);
    }
    OnFragmentChangeListener onFragmentChangeListener;
    public void setOnFragmentChangeListener(OnFragmentChangeListener onFragmentChangeListener){
        this.onFragmentChangeListener=onFragmentChangeListener;
    }
    public void fireBack(){
        if(onFragmentChangeListener!=null)
            onFragmentChangeListener.onBack();
    }
    public void fireError(){
        if(onFragmentChangeListener!=null)
            onFragmentChangeListener.onError();
    }
    public void fireCompleted(){
        if(onFragmentChangeListener!=null)
            onFragmentChangeListener.onCompleted();
    }
    public void fireDataArrive(int reqCode,Object o){
        if(onFragmentChangeListener!=null)
            onFragmentChangeListener.onDataArrive(reqCode,o);
    }
    public void sendData(int sendCode,Object data){
        fireDataArrive(sendCode,data);
    }
}
