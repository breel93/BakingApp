package com.example.breezil.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViewsService;

public class WidgetService extends RemoteViewsService {

    public static Intent getIntent(Context context) {
        return new Intent(context, RemoteViewsService.class);
    }
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new BakingRemoteViewsFactory(this);
    }
}
