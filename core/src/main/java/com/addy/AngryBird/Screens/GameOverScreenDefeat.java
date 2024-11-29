package com.addy.AngryBird.Screens;

import com.addy.AngryBird.AngryBirdd;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

public class GameOverScreenDefeat implements Screen {

    private final AngryBirdd game;
    private final Texture background;
    private final Texture gameOverImage;
    private final Music gameOverMusic;

    private float elapsedTime;

    public GameOverScreenDefeat(AngryBirdd game) {
        this.game = game;
        this.elapsedTime = 0;

        // Load assets
        background = new Texture("GameOverScreen.png");
        gameOverImage = new Texture("Defeat.png");
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

        // Clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Begin rendering
        game.batch.begin();

        game.batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        float x = (Gdx.graphics.getWidth() - gameOverImage.getWidth()) / 2f;
        float y = (Gdx.graphics.getHeight() - gameOverImage.getHeight()) / 2f;
        game.batch.draw(gameOverImage, x, y, gameOverImage.getWidth(), gameOverImage.getHeight());

        game.batch.end();

        if (elapsedTime >= 3 || Gdx.input.justTouched()) {
            gameOverMusic.stop();
            game.setScreen(new MainMenuScreen(game)); // Transition to the main menu
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
        if (background != null) background.dispose();
        if (gameOverImage != null) gameOverImage.dispose();
        if (gameOverMusic != null) gameOverMusic.dispose();
    }
}
