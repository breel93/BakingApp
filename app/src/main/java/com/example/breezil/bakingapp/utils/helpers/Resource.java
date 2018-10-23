package com.example.breezil.bakingapp.utils.helpers;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static com.example.breezil.bakingapp.utils.helpers.Resource.Status.ERROR;
import static com.example.breezil.bakingapp.utils.helpers.Resource.Status.LOADING;
import static com.example.breezil.bakingapp.utils.helpers.Resource.Status.SUCCESS;


/**
 * More from here Architecture Guide "https://developer.android.com/arch"
 * Helper code from googlesamples/android-architecture-components:
 * "https://github.com/breel93/android-architecture-components/blob/master/GithubBrowserSample/app/src/main/java/com/android/example/github/vo/Resource.kt"
 *
 */

public class Resource<T> {

    @NonNull
    public final Status status;

    @Nullable
    public final String message;

    @Nullable
    public final T data;

    public Resource(@NonNull Status status, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> Resource<T> success(@Nullable T data) {
        return new Resource<>(SUCCESS, data, null);
    }

    public static <T> Resource<T> error(String msg, @Nullable T data) {
        return new Resource<>(ERROR, data, msg);
    }

    public static <T> Resource<T> loading(@Nullable T data) {
        return new Resource<>(LOADING, data, null);
    }

    public enum Status {
        SUCCESS,
        ERROR,
        LOADING
    }

}
