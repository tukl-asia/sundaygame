package com.sunday.game.World;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sunday.game.GameFramework.FocusedScreen;
import com.sunday.game.GameFramework.GameFlowManager;
import com.sunday.game.GameFramework.GameStatus;
import com.sunday.game.GameFramework.ResourceManager;
import com.sunday.game.Resource.ResourceDescriptorStorage;

public class GameLoading extends FocusedScreen {
    private SpriteBatch batch;
    private BitmapFont font;
    private ResourceManager resourceManager;
    private boolean finishing = false;
    private float waitingTime;

    @Override
    public void show() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(0, 0, 0, 1);
        resourceManager = ResourceManager.getInstance();
        resourceManager.loadResourceFromDescriptorStorage(new ResourceDescriptorStorage());
    }

    @Override
    public void render(float delta) {
        batch.begin();
        if (!finishing) {
            finishing = resourceManager.isFinishLoading();
        }
        if (finishing) {
            font.draw(batch, "LOADING GAME 100%", Gdx.graphics.getWidth() / 2-80, Gdx.graphics.getHeight() / 2);
            waitingTime += delta;
            if (waitingTime > 1.0) GameFlowManager.getInstance().setGameStatus(GameStatus.Loading.Intro);
        } else {
            font.draw(batch, "LOADING GAME " + (int) (100 * resourceManager.getLoadingProgress())+"%", Gdx.graphics.getWidth() / 2-80, Gdx.graphics.getHeight() );
        }

        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public InputAdapter getInputAdapter() {
        return new InputAdapter();
    }
}
