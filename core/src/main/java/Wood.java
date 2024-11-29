package com.addy.AngryBird;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.addy.AngryBird.Material;

public class Wood extends Material {

    public Wood(Texture texture, Vector2 position) {
        super("Wood.png", position, 100); // Initialize with 100 health
    }

    @Override
    protected void onDestroyed() {
        System.out.println("Wood has been destroyed! It splinters into pieces.");
        // Additional logic for wood destruction (e.g., particle effects) can be added here
    }

    public void setPosition(Vector2 position) {
        this.position = position; // Update the position field
        super.setPosition(position.x, position.y); // Update the Sprite's position
    }
}
