package com.sunday.engine.examples.hero;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.Array;
import com.sunday.engine.environment.time.AnimationSetting;
import com.sunday.engine.model.property.Movement;
import com.sunday.engine.model.state.Direction;
import com.sunday.game.framework.GameFramework;

public class HeroAnimation {
    private Animation runningAnimation;
    private Animation fightingAnimation;
    private Animation standstillAnimation;
    private Animation jumpingAnimation;
    private float stateTime = 0.0f;

    public HeroAnimation() {
        float frameDuration = AnimationSetting.FrameDuration;
        Array<Texture> keyFrames = new Array<>();
        for (int i = 1; i <= 10; i++) {
            keyFrames.add(GameFramework.Resource.getAsset("hero/Hero" + i + ".png"));
        }
        runningAnimation = new Animation(frameDuration, keyFrames, Animation.PlayMode.LOOP);
        fightingAnimation = new Animation(frameDuration, keyFrames, Animation.PlayMode.LOOP_RANDOM);
        standstillAnimation = new Animation(frameDuration, keyFrames, Animation.PlayMode.NORMAL);
        jumpingAnimation = new Animation(frameDuration, keyFrames, Animation.PlayMode.LOOP_PINGPONG);
    }

    public Texture getKeyFrame(Movement movement) {
        Texture texture = null;
        Animation currentAnimation = null;
        switch (movement.action) {
            case Running:
                currentAnimation = runningAnimation;
                break;
            case Jumping:
                currentAnimation = jumpingAnimation;
                break;
            case Fighting:
                currentAnimation = fightingAnimation;
                break;
            case StandStill:
            default:
                currentAnimation = standstillAnimation;
        }
        if (currentAnimation != null) {
            texture = (Texture) currentAnimation.getKeyFrame(stateTime);
            if (movement.direction == Direction.Left) {
                //
            }
        }
        return texture;
    }

    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
    }
}
