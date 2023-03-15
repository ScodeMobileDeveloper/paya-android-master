package com.paya.paragon.base;

public abstract class BaseViewModelActivity<VM extends BaseViewModel> extends BaseAppCompactActivity {

    public abstract VM onCreateViewModel();
}
