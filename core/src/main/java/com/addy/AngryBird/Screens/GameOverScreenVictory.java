package com.addy.AngryBird.Screens;

import com.addy.AngryBird.AngryBirdd;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

public class GameOverScreenVictory implements Screen {

    private final AngryBirdd game;
    private final Texture background;
    private final Texture gameOverImage;
    private final Music gameOverMusic;

    private float elapsedTime;

    public GameOverScreenVictory(AngryBirdd game) {
        this.game = game;
        this.elapsedTime = 0;

        background = new Texture("GameOverScreen.png");
        gameOverImage = new Texture("Victory.png");
        gameOverMusic = Gdx.audio.newMusic(Gdx.files.internal("gameOverMusic.mp3"));
        gameOverMusic.setLooping(false); // Play once, not looping
    }

    @Override
    public void show() {
        gameOverMusic.play();
    }

    @Override
    public void render(float delta) {
        elapsedTime += delta;

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        game.batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        float scaleFactor = 3f; // Adjust this value to increase or decrease the size
        float scaledWidth = gameOverImage.getWidth() * scaleFactor;
        float scaledHeight = gameOverImage.getHeight() * scaleFactor;

        float x = (Gdx.graphics.getWidth() - scaledWidth) / 2f;
        float y = (Gdx.graphics.getHeight() - scaledHeight) / 2f;
        game.batch.draw(gameOverImage, x, y, scaledWidth, scaledHeight);

        game.batch.end();

        if (elapsedTime >= 3 || Gdx.input.justTouched()) {
            gameOverMusic.stop();
            game.setScreen(new LevelSelectScreen(game));
        }
    }


    @Override
    public void resize(int width, int height) {

    }
    @Override
    public void pause() {
        if (gameOverMusic.isPlaying()) {
            gameOverMusic.pause();
        }
    }
    @Override
    public void resume() {
        if (!gameOverMusic.isPlaying()) {
            gameOverMusic.play();
        }
    }
    @Override
    public void hide() {
        gameOverMusic.stop();
    }
    @Override
    public void dispose() {
        // Safeguard against null references during disposal
        if (background != null) background.dispose();
        if (gameOverImage != null) gameOverImage.dispose();
        if (gameOverMusic != null) gameOverMusic.dispose();
    }
}
