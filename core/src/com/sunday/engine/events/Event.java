package com.sunday.engine.events;


import com.sunday.engine.common.Data;

public class Event implements Data {
    private Object source;

    public Event(Object source) {
        this.source = source;
    }

    public Object getSource() {
        return source;
    }
}
