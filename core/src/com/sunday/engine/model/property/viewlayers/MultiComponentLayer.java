package com.sunday.engine.model.property.viewlayers;

import com.badlogic.gdx.utils.Disposable;
import com.sunday.engine.model.property.ViewLayer;

import java.util.ArrayList;
import java.util.Collections;

public abstract class MultiComponentLayer<T extends Disposable> implements ViewLayer {
    private boolean visible = true;
    private ArrayList<T> viewComponents = new ArrayList<>();

    public void addToViewComponents(T t) {
        viewComponents.add(t);
    }

    public void addToViewComponents(T... viewComponents) {
        Collections.addAll(this.viewComponents, viewComponents);
    }

    public ArrayList<T> getViewComponents() {
        if (visible)
            return viewComponents;
        else return null;
    }

    @Override
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    public void dispose() {
        viewComponents.clear();
    }
}
