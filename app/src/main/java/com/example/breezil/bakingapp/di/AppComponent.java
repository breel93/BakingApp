package com.example.breezil.bakingapp.di;

import android.app.Application;

import com.example.breezil.bakingapp.App;
import com.example.breezil.bakingapp.ui.StepDetailActivity;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;


@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        ApplicationModule.class,
        MainActivityModule.class,
        DetailActivityModule.class,
        StepDetailActivityModule.class

})
public interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance Builder application(Application application);
        AppComponent build();
    }
    void inject(App bakingApp);
}
