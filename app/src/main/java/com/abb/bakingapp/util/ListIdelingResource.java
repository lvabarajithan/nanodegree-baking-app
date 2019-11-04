package com.abb.bakingapp.util;

import androidx.test.espresso.IdlingResource;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Abarajithan
 */
public class ListIdelingResource implements IdlingResource {

    private AtomicBoolean isIdle = new AtomicBoolean(false);

    private ResourceCallback callback = null;

    @Override
    public String getName() {
        return "RecyclerView";
    }

    @Override
    public boolean isIdleNow() {
        return isIdle.get();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.callback = callback;
    }

    public void setIdleState(boolean isIdleNow) {
        isIdle.set(isIdleNow);
        if (isIdleNow && callback != null) {
            callback.onTransitionToIdle();
        }
    }

}
