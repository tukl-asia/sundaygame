package com.sunday.engine.environment.event.window;

import com.sunday.engine.common.SpecificSignal;

public enum WindowSignal implements SpecificSignal {
    Resized, Closed, Maximum, Minimum, Hide, Show
}