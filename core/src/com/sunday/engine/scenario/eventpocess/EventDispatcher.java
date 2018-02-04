package com.sunday.engine.scenario.eventpocess;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Disposable;
import com.sunday.engine.event.Event;
import com.sunday.engine.event.EventPoster;
import com.sunday.engine.event.EventProcessor;
import com.sunday.engine.scenario.Scenario;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class EventDispatcher implements Disposable, EventPoster {
    private ArrayList<EventProcessor> internalProcessors = new ArrayList<>();
    private Vector<Event> eventQueue = new Vector<>();
    private Scenario rootScenario;
    private boolean running;
    private boolean dispatching;

    public EventDispatcher(Scenario rootScenario) {
        this.rootScenario = rootScenario;
        running = true;
        dispatching = false;
    }

    public void addInternalEventProcessors(List<EventProcessor> eventProcessors) {
        internalProcessors.addAll(eventProcessors);
    }

    @Override
    public void dispatchEvent(Event event) {
        eventQueue.add(event);
    }

    public void dispatchEventQueue() {
        if (!running) return;
        dispatching = true;
        eventQueue.forEach(e -> {
                    Gdx.app.log("EventDispatcher", "Dispatch a Event : " + e.toString());
                    internalProcessors.forEach(eventProcessor -> eventProcessor.processEvent(e));
                    rootScenario.notifyAllRoles(e);
                }
        );
        eventQueue.clear();
        dispatching = false;
    }

    @Override
    public void dispose() {
        running = false;
        while (dispatching) ;
    }
}
