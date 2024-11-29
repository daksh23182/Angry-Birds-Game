package com.addy.AngryBird;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.addy.AngryBird.Material;

public class Glass extends Material {

    public Glass(Texture texture, Vector2 position) {
        super("Glass.png", position, 150);
    }

    @Override
    protected void onDestroyed() {
        System.out.println("Glass has shattered into fragments!");

    }
}
