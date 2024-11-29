package com.addy.AngryBird.Screens;

import com.addy.AngryBird.AngryBirdd;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

public class MainMenuScreen implements Screen {

    private static final int EXIT_BUTTON_WIDTH = 600;
    private static final int EXIT_BUTTON_HEIGHT = 240;
    private static final int PLAY_BUTTON_WIDTH = 600;
    private static final int PLAY_BUTTON_HEIGHT = 240;


    private static final int EXIT_BUTTON_X = 900;
    private static final int EXIT_BUTTON_Y = 200;
    private static final int PLAY_BUTTON_X = 900;
    private static final int PLAY_BUTTON_Y = 500;

    AngryBirdd game;

    Texture exitButtonActive;
    Texture exitButtonInactive;
    Texture playButtonActive;
    Texture playButtonInactive;
    Texture MainMenuBg;
    Texture AngryBirdLogo;

    public MainMenuScreen(AngryBirdd game) {
        this.game = game;
        playButtonInactive = new Texture("play_button_inactive.png");
        playButtonActive = new Texture("play_button_Active.png");
        exitButtonActive = new Texture("exit_button_Active.png");
        exitButtonInactive = new Texture("exit_button_inactive.png");
        MainMenuBg = new Texture("MainMenuScreen.png");
        AngryBirdLogo = new Texture("AngryBirdsLogo.png");
    }

    @Override
    public void show() {
    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        game.batch.draw(MainMenuBg, 0, 0);
        game.batch.draw(AngryBirdLogo, 600, 750); // Centered logo for a 2400x1500 screen

        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY(); // Invert Y-axis

        if (mouseX > EXIT_BUTTON_X && mouseX < EXIT_BUTTON_X + EXIT_BUTTON_WIDTH
            && mouseY > EXIT_BUTTON_Y && mouseY < EXIT_BUTTON_Y + EXIT_BUTTON_HEIGHT) {
            game.batch.draw(exitButtonActive, EXIT_BUTTON_X, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);

            if (Gdx.input.isTouched()) {
                Gdx.app.exit();
            }
        } else {
            game.batch.draw(exitButtonInactive, EXIT_BUTTON_X, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
        }

        if (mouseX > PLAY_BUTTON_X && mouseX < PLAY_BUTTON_X + PLAY_BUTTON_WIDTH
            && mouseY > PLAY_BUTTON_Y && mouseY < PLAY_BUTTON_Y + PLAY_BUTTON_HEIGHT) {
            game.batch.draw(playButtonActive, PLAY_BUTTON_X, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);

            if (Gdx.input.isTouched()) {
                game.setScreen(new LevelSelectScreen(game));
            }
        } else {
            game.batch.draw(playButtonInactive, PLAY_BUTTON_X, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
        }

        game.batch.end();
    }
    @Override
    public void resize(int width, int height) {
        // Handle resizing logic if needed
    }
    @Override
    public void pause() {
        // Handle game pause if needed
    }
    @Override
    public void resume() {
        // Handle game resume if needed
    }
    @Override
    public void hide() {
        // Handle hiding the screen if needed
    }
    @Override
    public void dispose() {
        // Dispose textures to avoid memory leaks
        playButtonInactive.dispose();
        playButtonActive.dispose();
        exitButtonInactive.dispose();
        exitButtonActive.dispose();
        MainMenuBg.dispose();
        AngryBirdLogo.dispose();
    }
}
