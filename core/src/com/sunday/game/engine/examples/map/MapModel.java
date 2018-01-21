package com.sunday.game.engine.examples.map;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.sunday.game.engine.common.viewlayers.MapViewLayer;
import com.sunday.game.engine.events.EventProcessor;
import com.sunday.game.engine.databank.port.HolderPort;
import com.sunday.game.engine.model.AbstractModel;

public class MapModel extends AbstractModel {

    public MapModel() {
        outlook.shape = Shape.Type.Chain;
        outlook.dimension.set(1000, 1000);
        outlook.viewLayers.add(new MapViewLayer("TileMap/sTest/sTest.tmx"));

        BodyDef bodyDef = physicReflection.bodyDef;
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(0, 0);

        ChainShape chainShape = new ChainShape();
        chainShape.createChain(new Vector2[]{new Vector2(-200, 0), new Vector2(1000, 0)});

        FixtureDef fixtureDef = physicReflection.fixtureDef;
        fixtureDef.shape = chainShape;
        fixtureDef.friction = 2.0f;
        fixtureDef.restitution = 0;
        fixtureDef.density = 15f;
    }

    @Override
    protected void initDataSynchronize(HolderPort holderPort) {

    }

    @Override
    public void dispose() {

    }
}
