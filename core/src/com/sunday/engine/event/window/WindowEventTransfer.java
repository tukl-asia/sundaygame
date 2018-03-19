package com.sunday.engine.event.window;

import com.sunday.engine.event.EventTransfer;

public class WindowEventTransfer extends EventTransfer {
    private Window window;

    public WindowEventTransfer(Window window) {
        this.window = window;
    }

    public void resize(int width, int height) {
        window.width = width;
        window.height = height;
        eventPoster.dispatchEvent(new WindowEvent(window, WindowSignal.Resized));
    }
}
