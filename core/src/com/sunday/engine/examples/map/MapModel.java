package com.sunday.engine.examples.map;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.sunday.engine.databank.Port;
import com.sunday.engine.model.AbstractModel;
import com.sunday.engine.model.property.viewlayers.MapViewLayer;

public class MapModel extends AbstractModel {

    public MapModel() {
        outlook.dimension.set(1000, 1000);
        outlook.viewLayers.add(new MapViewLayer("TileMap/sTest/sTest.tmx"));

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(0, 0);

        physicDefinition.setBodyDef(bodyDef);

        ChainShape chainShape = new ChainShape();
        chainShape.createChain(new Vector2[]{new Vector2(-200, 0), new Vector2(1000, 0)});

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = chainShape;
        fixtureDef.friction = 2.0f;
        fixtureDef.restitution = 0;
        fixtureDef.density = 15f;

        physicDefinition.setBodyDef(bodyDef);
    }

    @Override
    protected void disconnectWithInternal(Port port) {

    }

    @Override
    protected void connectWithInternal(Port port) {

    }

    @Override
    public void dispose() {

    }
}
