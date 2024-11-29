package com.addy.AngryBird;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.addy.AngryBird.Material;

public class Metal extends Material {

    public Metal(Texture texture, Vector2 position) {
        super("Metal.png", position, 200);
    }

    @Override
    protected void onDestroyed() {
        System.out.println("Metal has been destroyed after immense force!");

    }
}
