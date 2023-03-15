package com.paya.paragon.base;

public abstract class BaseViewModelFragment<VM extends BaseViewModel> extends BaseFragment {

    public abstract VM onCreateViewModel();
}
