package com.sunday.game.world;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.sunday.engine.Engine;
import com.sunday.engine.common.context.CustomizedDataContext;
import com.sunday.engine.databank.Port;
import com.sunday.engine.environment.driver.DriverContext;
import com.sunday.engine.environment.driver.keyboard.KeyBoard;
import com.sunday.engine.environment.driver.keyboard.KeyBoardCondition;
import com.sunday.engine.examples.Label;
import com.sunday.engine.examples.Role;
import com.sunday.engine.model.AbstractModel;
import com.sunday.engine.model.property.MovementSignal;
import com.sunday.engine.model.property.PhysicBody;
import com.sunday.engine.model.property.PhysicDefinition;
import com.sunday.engine.model.property.PhysicReflectionSignal;
import com.sunday.engine.physic.CollisionCondition;
import com.sunday.engine.rule.Reaction;
import com.sunday.engine.rule.Rule;
import com.sunday.engine.scenario.Scenario;
import com.sunday.engine.scenario.ScenarioSystem;
import com.sunday.engine.scenario.ScopeType;

public class DemoCannon implements Screen {
    private Engine engine;

    public DemoCannon() {
        engine = new Engine();
        ScenarioSystem scenarioSystem = engine.getScenarioSystem();
        Scenario scenario = new Scenario(ScopeType.Game);
        Role cannon = new Role(Label.Hero, new CannonModel());
        Role bomb = new Role(Label.Hero, new BombModel());
        scenario.addRole(cannon);
        scenario.addRole(bomb);
        scenarioSystem.setRoot(scenario);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        engine.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        engine.resize(width, height);
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
        engine.dispose();
    }

    private class BombModel extends AbstractModel {

        public BombModel() {
            physicDefinition.bodyDef.gravityScale = 1.0f;
            physicDefinition.bodyDef.position.set(0, 0);
            physicDefinition.bodyDef.type = BodyDef.BodyType.DynamicBody;
            physicDefinition.bodyDef.linearVelocity.set(1.7f, 1);

            physicDefinition.fixtureDef.shape.setRadius(5);
            physicDefinition.fixtureDef.restitution = 0.5f;
            physicDefinition.fixtureDef.density = 0.05f;
        }

        @Override
        protected void connectWithInternal(Port port) {
            port.addDataInstance(new Rule<>(KeyBoardCondition.keyPressed("R"), new Reaction<DriverContext<KeyBoard>>() {
                @Override
                public void accept(DriverContext<KeyBoard> keyBoardDriverContext) {
                    PhysicBody physicBody = physicDefinition.physicBody;
                    float mass = physicBody.getMass();
                    Vector2 reverseImpulse = physicBody.getLinearVelocity().scl(-mass).add(1.7f * mass, 1 * mass);
                    physicBody.applyLinearImpulse(reverseImpulse, physicBody.getWorldCenter(), true);
                    movement.position.set(0, 0);
                    port.broadcast(movement, MovementSignal.ReLocated);
                }
            }));
        }

        @Override
        protected void disconnectWithInternal(Port port) {

        }

        @Override
        public void dispose() {

        }
    }

    private class CannonModel extends AbstractModel {
        public CannonModel() {
            physicDefinition.bodyDef.position.set(movement.position);
            physicDefinition.bodyDef.type = BodyDef.BodyType.StaticBody;
            physicDefinition.bodyDef.gravityScale = 0;
            physicDefinition.bodyDef.angle = -0.523f;

            EdgeShape shape = new EdgeShape();
            shape.set(new Vector2(0, 0), new Vector2(100, 100));
            physicDefinition.fixtureDef.shape = shape;
            physicDefinition.fixtureDef.restitution = 1;
            physicDefinition.fixtureDef.friction = 0;
        }

        @Override
        protected void connectWithInternal(Port port) {
            port.addDataInstance(new Rule<>(CollisionCondition.collideBetween(physicDefinition, PhysicDefinition.class), new Reaction<CustomizedDataContext<PhysicDefinition>>() {
                @Override
                public void accept(CustomizedDataContext<PhysicDefinition> physicDefinitionCustomizedDataContext) {
                    PhysicBody physicBody = physicDefinitionCustomizedDataContext.getData().physicBody;
                    physicBody.applyLinearImpulse(new Vector2(1.7f, 1), physicBody.getWorldCenter(), true);
                    port.broadcast(physicDefinition, PhysicReflectionSignal.Updated);
                }
            }));
        }

        @Override
        protected void disconnectWithInternal(Port port) {

        }

        @Override
        public void dispose() {

        }
    }
}
