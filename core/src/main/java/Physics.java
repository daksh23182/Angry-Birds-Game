package com.addy.AngryBird;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Physics {
    private final World world;
    private final Box2DDebugRenderer debugRenderer;
    private boolean debugEnabled = false;

    public Physics() {

        world = new World(new Vector2(0, -9.8f), true);

        debugRenderer = new Box2DDebugRenderer();
    }

    public World getWorld() {
        return world;
    }

    public void update(float deltaTime) {
        // Step the physics simulation
        world.step(deltaTime, 6, 2); // Velocity and position iterations
    }


    public void renderDebug(Camera camera) {
        if (debugEnabled) {
            debugRenderer.render(world, camera.combined);
        }
    }

    public void setDebugEnabled(boolean enabled) {
        this.debugEnabled = enabled;
    }


    public void setGravity(Vector2 gravity) {
        world.setGravity(gravity);
    }

    public Vector2 getGravity() {
        return world.getGravity();
    }

    public void setContactListener(ContactListener listener) {
        world.setContactListener(listener);
    }

    public void dispose() {
        world.dispose();
        debugRenderer.dispose();
    }
}
