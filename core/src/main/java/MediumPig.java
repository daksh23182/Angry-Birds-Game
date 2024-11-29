package com.addy.AngryBird;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.addy.AngryBird.Pig;

public class MediumPig extends Pig {
    public MediumPig(Texture texture, Vector2 initialPosition) {
        super("MediumPig.png", initialPosition, 150, 150, 150); // Width, height, and health for MediumPig
    }

}
