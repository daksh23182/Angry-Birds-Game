package com.addy.AngryBird;

import com.badlogic.gdx.math.Vector2;

public class AimDetail {
    private Vector2 position;
    private Vector2 velocity;
    private final Vector2 gravity = new Vector2(0, -500f); // Gravity
    private boolean launched;

    public AimDetail(float initialX, float initialY) {
        this.position = new Vector2(initialX, initialY);
        this.velocity = new Vector2(0, 0);
        this.launched = false;
    }


    public void setVelocity(float vx, float vy) {
        this.velocity.set(vx, vy);
    }

    public void launch() {
        this.launched = true;
    }

    public boolean isLaunched() {
        return launched;
    }

    public void update(float delta) {
        if (launched) {
            velocity.add(gravity.x * delta, gravity.y * delta);
            position.add(velocity.x * delta, velocity.y * delta);

            // Clamp position to ground level
            if (position.y < 0) {
                position.y = 0;
                velocity.set(0, 0);
                launched = false;
            }
        }
    }

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

}
