package com.sunday.game.engine.scenario.render;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sunday.game.engine.common.AnimationSetting;
import com.sunday.game.engine.common.enums.Label;
import com.sunday.game.engine.control.EventProcessor;
import com.sunday.game.engine.databank.port.UserPort;
import com.sunday.game.engine.examples.Role;
import com.sunday.game.engine.model.AbstractModel;
import com.sunday.game.engine.scenario.physicprocess.PhysicSimulator;
import com.sunday.game.engine.scenario.render.independentrenders.*;
import com.sunday.game.engine.scenario.render.managers.CameraManager;
import com.sunday.game.engine.scenario.render.managers.DisplayManager;
import com.sunday.game.engine.scenario.render.managers.RendererManager;
import com.sunday.game.engine.view.viewlayers.MapViewLayer;
import com.sunday.game.engine.view.viewlayers.ScreenViewLayer;

import java.util.ArrayList;
import java.util.List;

public class ScenarioRenderer implements Disposable {

    private MapRenderer mapRender;
    private SpriteRenderer spriteRender;
    private StageRenderer stageRender;
    private TextureRenderer textureRender;
    private WorldRenderer worldRender;

    private OrthographicCamera sharedCamera;
    //  private FitViewport screenViewport;
    //private ScreenViewport screenViewport;
    private Viewport screenViewport;
    private Batch sharedBatch;

    private float viewportWidth; //units
    private float viewportHeight; //units

    private float worldWidth;//units
    private float worldHeight;//units

    private RendererManager rendererManager;
    private CameraManager cameraManager;
    private DisplayManager displayManager;

    public List<EventProcessor> getProcessors() {
        List<EventProcessor> eventProcessors = new ArrayList<>();
        eventProcessors.add(cameraManager);
        eventProcessors.add(rendererManager);
        eventProcessors.add(displayManager);
        return eventProcessors;
    }

    public ScenarioRenderer(PhysicSimulator physicSimulator) {

        rendererManager = new RendererManager();

        // aspectRatio = (float) Gdx.graphics.getHeight() / Gdx.graphics.getWidth();

        viewportHeight = 20.0f;
        viewportWidth = 20.0f;

        worldHeight = 800;
        worldWidth = 800;

        sharedBatch = new SpriteBatch();
        sharedCamera = new OrthographicCamera(viewportWidth, viewportHeight);
        cameraManager = new CameraManager(sharedCamera);
        //sharedCamera = new OrthographicCamera();
        screenViewport = new FitViewport(worldWidth, worldHeight, sharedCamera);
        screenViewport.apply();
        displayManager = new DisplayManager(cameraManager, screenViewport);

        sharedCamera.position.set(worldWidth / 2, worldHeight / 2, 0);
        sharedCamera.update();
        //screenViewport = new ScreenViewport(sharedCamera);
        spriteRender = new SpriteRenderer(sharedBatch);
//        stageRender = new StageRenderer(sharedBatch, screenViewport);
        stageRender = new StageRenderer();
        textureRender = new TextureRenderer(sharedBatch);
        worldRender = new WorldRenderer(sharedCamera);
        worldRender.combineWithWorld(physicSimulator.getWorld());
    }

    public void readFromDataBank(UserPort dataUserPort) {
        dataUserPort.getInstances(Texture.class);
    }

    public void readyToRenderRole(Role role) {
        switch (role.label) {
            case Map:
                renderMap(role);
                break;
            case Hero:
            case Enemy:
                renderSingleRole(role);
        }
    }

    private void renderMap(Role role) {
        if (role.label != Label.Map) return;
        role.abstractView.getViewLayers().forEach(e -> {
            if (e instanceof MapViewLayer) {
                renderMapViewLayer((MapViewLayer) e);
            }
        });
    }


    private void renderSingleRole(Role role) {
        if (role.label == Label.Map) return;
        role.abstractView.getViewLayers().forEach(e -> {
            if (e instanceof ScreenViewLayer) {
                renderScreenViewLayer((ScreenViewLayer) e, role.abstractModel);
            }
        });
    }

    private void renderScreenViewLayer(ScreenViewLayer e, AbstractModel model) {
        Texture component = (Texture) e.getViewComponent();
        if (component == null) return;
        if (component instanceof Texture) {
            Vector2 position = model.movementState.position;
            Vector2 dimension = model.outlook.dimension;
            textureRender.renderLater((Texture) component, position.x, position.y, dimension.x, dimension.y);
        }
    }

    private void renderMapViewLayer(MapViewLayer e) {
        Object component = e.getViewComponent();
        if (component == null) return;
        if (component instanceof TiledMap) {
            if (mapRender == null) {
                mapRender = new MapRenderer(sharedBatch, sharedCamera, (TiledMap) component);
            }
        }
    }

    public void render(float delta) {
        AnimationSetting.DeltaTime += delta;

        //sharedCamera.translate(-10.0f * delta, 0);
        //sharedCamera.rotate(10 * delta);
        sharedCamera.update();
        sharedBatch.setProjectionMatrix(sharedCamera.combined);

        if (rendererManager.DoRenderMap & mapRender != null) {
            mapRender.updateCamera(sharedCamera);
            mapRender.render(delta);
        }
        if (rendererManager.DoRenderSprite)
            spriteRender.render(delta);
        if (rendererManager.DoRenderTexture)
            textureRender.render(delta);
        if (rendererManager.DoRenderStage)
            stageRender.render(delta);
        if (rendererManager.DoRenderWorld)
            worldRender.render(delta);
    }

    @Override
    public void dispose() {
        mapRender.dispose();
        spriteRender.dispose();
        worldRender.dispose();
        stageRender.dispose();
        textureRender.dispose();
    }
}
