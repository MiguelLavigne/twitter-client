package com.example.android.twitterclient;

public abstract class BasePresenter<View extends BaseView> {

    private View view;

    public final void takeView(View view) {
        if (view == null) {
            throw new NullPointerException("view == null");
        }
        if (this.view != null) {
            onDestroy();
        }
        this.view = view;
        onReady();
    }

    public final void dropView(View view) {
        if (view == null) {
            throw new NullPointerException("view == null");
        }
        if (this.view == null) {
            throw new IllegalStateException("internal view == null");
        }
        onDestroy();
        this.view = null;
    }

    protected View getView() {
        return view;
    }

    protected void onDestroy() {
    }

    protected void onReady() {
    }
}