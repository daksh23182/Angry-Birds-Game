package com.addy.AngryBird;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Sling extends Sprite {
    private Vector2 position;

    public Sling(Texture texture, Vector2 position) {
        super(texture);
        this.position = position;
        setPosition(position.x, position.y);
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
        super.setPosition(position.x, position.y);
    }
}
