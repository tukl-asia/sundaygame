package com.sunday.engine.examples.enemy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.Array;
import com.sunday.engine.environment.time.AnimationSetting;
import com.sunday.game.framework.GameFramework;

public class SawAnimation {
    private Animation spinningAnimation;
    private float stateTime;

    public SawAnimation() {
        Array<Texture> keyFrames = new Array<>();
        for (int i = 1; i <= 20; i++) {
            keyFrames.add(GameFramework.Resource.getAsset("saws/saw" + i + ".png"));
        }
        spinningAnimation = new Animation(AnimationSetting.FrameDuration, keyFrames, Animation.PlayMode.LOOP);
    }

    public Texture getKeyFrame() {
        return (Texture) spinningAnimation.getKeyFrame(stateTime);
    }

    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
    }
}
