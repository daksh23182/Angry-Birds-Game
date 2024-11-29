package com.addy.AngryBird;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.addy.AngryBird.Bird;

public class YellowBird extends Bird {
    public YellowBird(Texture texture, Vector2 position) {
        super("YellowBird.png", position, 150, 150, 150); // Default size and health
    }

    public void useSpecialAbility() {

    }
}
