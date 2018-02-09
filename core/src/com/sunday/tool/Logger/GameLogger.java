package com.sunday.tool.logger;

import com.badlogic.gdx.ApplicationLogger;
import com.sunday.tool.ToolExtender;

import java.util.ArrayList;

public class GameLogger extends ToolExtender<GameLoggerUIController> implements ApplicationLogger {
    //    public static final int LOG_NONE = 0;
//    public static final int LOG_DEBUG = 3;
//    public static final int LOG_INFO = 2;
//    public static final int LOG_ERROR = 1;

    private ArrayList<LogMessage> logs = new ArrayList<>();
    private ArrayList<LogMessage> logBuffer = new ArrayList<>();

    private void disposeLogMessage(LogType type, String tag, String message) {
        LogMessage logMessage = new LogMessage(LogType.NONE, tag, message);
        logBuffer.add(logMessage);
        if (getController() != null) {
            logBuffer.forEach(e -> {
                getController().newLogMessage(e);
                logs.add(e);
            });
            logBuffer.clear();
        }
    }

    @Override
    public void log(String tag, String message) {
        disposeLogMessage(LogType.NONE, tag, message);
    }

    @Override
    public void log(String tag, String message, Throwable exception) {
        disposeLogMessage(LogType.NONE, tag, message);
    }

    @Override
    public void error(String tag, String message) {
        disposeLogMessage(LogType.ERROR, tag, message);
    }

    @Override
    public void error(String tag, String message, Throwable exception) {
        disposeLogMessage(LogType.ERROR, tag, message);
    }

    @Override
    public void debug(String tag, String message) {
        disposeLogMessage(LogType.DEBUG, tag, message);
    }

    @Override
    public void debug(String tag, String message, Throwable exception) {
        disposeLogMessage(LogType.DEBUG, tag, message);
    }

}
