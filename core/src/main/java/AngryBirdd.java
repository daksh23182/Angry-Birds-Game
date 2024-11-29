package com.addy.AngryBird;

import com.addy.AngryBird.Screens.LoadingScreen1;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class AngryBirdd extends Game {
    public SpriteBatch batch;
    public BitmapFont font;

    @Override
    public void create() {
        batch = new SpriteBatch();
        this.setScreen(new LoadingScreen1(this));


        font = new BitmapFont();
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
