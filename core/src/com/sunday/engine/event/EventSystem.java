package com.sunday.engine.event;

import com.sunday.engine.common.SubSystem;
import com.sunday.engine.databank.SystemPort;
import com.sunday.engine.event.window.Window;

public class EventSystem extends SubSystem implements EventPoster {
    private Window window;
    private EventProcessor eventProcessor = new EventProcessor() {
        @Override
        public void processEvent(Event event) {
            systemPort.broadcast(event.getSource(), event.getSignal());
        }
    };

    public EventSystem(SystemPort systemPort) {
        super("EventSystem", systemPort);
        window = new Window();
        systemPort.addDataInstance(window);
    }

    @Override
    public void dispatchEvent(Event event) {
        systemPort.addDataInstance(event);
        eventProcessor.processEvent(event);
        systemPort.deleteDataInstance(event);
    }

    public void addEventProcessor(EventProcessor eventProcessor) {
        systemPort.addDataInstance(eventProcessor);
    }

    public void deleteEventPorcessor(EventProcessor eventProcessor) {
        systemPort.deleteDataInstance(eventProcessor);
    }

    public void addEventTransfer(EventTransfer eventTransfer) {
        systemPort.addDataInstance(eventTransfer);
        eventTransfer.setEventPoster(this);
    }

    public void deleteEventTransfer(EventTransfer eventTransfer) {
        systemPort.deleteDataInstance(eventTransfer);
    }

    public Window getWindow() {
        return window;
    }
}
