package com.addy.AngryBird;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.addy.AngryBird.Bird;

public class BlueBird extends Bird {
    public BlueBird(Texture texture, Vector2 position, float width, float height) {
        super("BlueBird.png", position, width, height, 200); // Default health of 80
    }

    public void useSpecialAbility() {

    }
}
