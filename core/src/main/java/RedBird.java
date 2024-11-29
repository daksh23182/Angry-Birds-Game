package com.addy.AngryBird;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.addy.AngryBird.Bird;

public class RedBird extends Bird {
    public RedBird(Texture texture, Vector2 position) {
        super("RedBird.png", position, 130, 130, 100); // Default size and health
    }

    public void useSpecialAbility() {

    }
}
