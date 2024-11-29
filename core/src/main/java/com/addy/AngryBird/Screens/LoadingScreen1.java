package com.addy.AngryBird.Screens;

import com.addy.AngryBird.AngryBirdd;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.Color;

public class LoadingScreen1 implements Screen {
    private AngryBirdd game;
    private Texture background;
    private BitmapFont loadingFont;
    private float elapsedTime;
    private Music loadingMusic;

    public LoadingScreen1(AngryBirdd game) {
        this.game = game;
        this.elapsedTime = 0;

        background = new Texture("LoadingScreen.png");

        loadingFont = new BitmapFont();
        loadingFont.setColor(Color.WHITE);
        loadingFont.getData().setScale(7);

        loadingMusic = Gdx.audio.newMusic(Gdx.files.internal("AngryBirdTheme.mp3"));
        loadingMusic.setLooping(true);
    }

    @Override
    public void show() {

        loadingMusic.play();
    }

    @Override
    public void render(float delta) {
        elapsedTime += delta;

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        game.batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


        GlyphLayout layout = new GlyphLayout(loadingFont, "Loading...");
        float x = Gdx.graphics.getWidth() - layout.width - 20;
        float y = layout.height + 20;
        loadingFont.draw(game.batch, layout, x, y);

        game.batch.end();


        if (elapsedTime >= 5) {
            loadingMusic.stop();
            game.setScreen(new MainMenuScreen(game));
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
        if (loadingMusic.isPlaying()) {
            loadingMusic.pause();
        }
    }

    @Override
    public void resume() {
        if (!loadingMusic.isPlaying()) {
            loadingMusic.play();
        }
    }

    @Override
    public void hide() {
        loadingMusic.stop();
    }

    @Override
    public void dispose() {
        background.dispose();
        loadingFont.dispose();
        loadingMusic.dispose();
    }
}
