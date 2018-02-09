package com.sunday.engine.event.driver;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector3;
import com.sunday.engine.driver.gamepad.GamePad;
import com.sunday.engine.driver.gamepad.GamePadSignal;

public class GamePadEvent extends DriverEvent {
    private Controller controller;
    private GamePadSignal operation;
    private int buttonCode;

    private int axisCode;
    private float axisMoveValue;

    private int povCode;
    private PovDirection povDirection;

    private boolean isXSliderMoved;
    private int sliderCode;
    private boolean sliderMoveValue;
    private int accelerometerCode;
    private Vector3 accelerometerMoveValue;

    public static GamePadEvent newConnectEvent(GamePad gamePadData) {
        GamePadEvent gamePadEvent = new GamePadEvent(gamePadData);
        gamePadEvent.operation = GamePadSignal.Connect;
        return gamePadEvent;
    }

    public static GamePadEvent newDisconnectEvent(GamePad gamePadData) {
        GamePadEvent gamePadEvent = new GamePadEvent(gamePadData);
        gamePadEvent.operation = GamePadSignal.Disconnect;
        return gamePadEvent;
    }

    public static GamePadEvent newButtonEvent(GamePad gamePadData, int buttonCode, boolean buttonDown) {
        GamePadEvent gamePadEvent = new GamePadEvent(gamePadData);
        gamePadEvent.operation = buttonDown ? GamePadSignal.ButtonDown : GamePadSignal.ButtonUp;
        gamePadEvent.buttonCode = buttonCode;
        return gamePadEvent;
    }

    public static GamePadEvent newAxisMoveEvent(GamePad gamePadData, int axisCode, float value) {
        GamePadEvent gamePadEvent = new GamePadEvent(gamePadData);
        gamePadEvent.operation = GamePadSignal.AxisMove;
        gamePadEvent.axisCode = axisCode;
        gamePadEvent.axisMoveValue = value;
        return gamePadEvent;
    }

    public static GamePadEvent newPovMoveEvent(GamePad gamePadData, int povCode, PovDirection povDirection) {
        GamePadEvent gamePadEvent = new GamePadEvent(gamePadData);
        gamePadEvent.operation = GamePadSignal.PovMove;
        gamePadEvent.povCode = povCode;
        gamePadEvent.povDirection = povDirection;
        return gamePadEvent;
    }

    public static GamePadEvent newSliderMoveEvent(GamePad gamePadData, boolean isXSliderMoved, int sliderCode, boolean value) {
        GamePadEvent gamePadEvent = new GamePadEvent(gamePadData);
        gamePadEvent.operation = isXSliderMoved ? GamePadSignal.XSliderMove : GamePadSignal.YSliderMove;
        gamePadEvent.isXSliderMoved = isXSliderMoved;
        gamePadEvent.sliderCode = sliderCode;
        gamePadEvent.sliderMoveValue = value;
        return gamePadEvent;
    }

    public static GamePadEvent newAccelerometerMoveEvent(GamePad gamePadData, int accelerometerCode, Vector3 value) {
        GamePadEvent gamePadEvent = new GamePadEvent(gamePadData);
        gamePadEvent.operation = GamePadSignal.AccelerometerMove;
        gamePadEvent.accelerometerCode = accelerometerCode;
        gamePadEvent.accelerometerMoveValue = value;
        return gamePadEvent;
    }

    public GamePadSignal getOperation() {
        return operation;
    }

    public int getButton() {
        return buttonCode;
    }

    public int getPov() {
        return povCode;
    }

    public PovDirection getPovDirection() {
        return povDirection;
    }

    public GamePadEvent(GamePad gamePadData) {
        super(gamePadData);
        this.controller = gamePadData.controller;
    }
}