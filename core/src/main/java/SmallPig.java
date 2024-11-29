package com.addy.AngryBird;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.addy.AngryBird.Pig;

public class SmallPig extends Pig {
    public SmallPig(Texture texture, Vector2 initialPosition) {
        super("SmallPig.png", initialPosition, 100, 100, 100); // Width, height, and health for SmallPig
    }


}
