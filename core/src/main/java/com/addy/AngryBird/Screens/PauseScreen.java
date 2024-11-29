package com.addy.AngryBird.Screens;

import com.addy.AngryBird.AngryBirdd;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

public class PauseScreen implements Screen {
    private static final int RESUME_BUTTON_WIDTH = 800;
    private static final int RESUME_BUTTON_HEIGHT = 240;
    private static final int MAIN_MENU_BUTTON_WIDTH = 800;
    private static final int MAIN_MENU_BUTTON_HEIGHT = 240;

    private static final int RESUME_BUTTON_X = 800;
    private static final int RESUME_BUTTON_Y = 600;
    private static final int MAIN_MENU_BUTTON_X = 800;
    private static final int MAIN_MENU_BUTTON_Y = 300;

    private AngryBirdd game;
    private Texture PauseScreenBg;
    private Texture GamePaused;
    private Texture ResumeInactive;
    private Texture ResumeActive;
    private Texture MainMenuInactive;
    private Texture MainMenuActive;

    public PauseScreen(AngryBirdd game) {
        this.game = game;
    }

    @Override
    public void show() {
        PauseScreenBg = new Texture("PauseScreen.png");
        GamePaused = new Texture("GamePaused.png");
        ResumeInactive = new Texture("ResumeInactive.png");
        ResumeActive = new Texture("ResumeActive.png");
        MainMenuInactive = new Texture("MainMenuInactive.png");
        MainMenuActive = new Texture("MainMenuActive.png");
    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        game.batch.draw(PauseScreenBg, 0, 0);
        game.batch.draw(GamePaused, 700, 1000);

        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY(); // Invert Y-axis

        if (mouseX > RESUME_BUTTON_X && mouseX < RESUME_BUTTON_X + RESUME_BUTTON_WIDTH
            && mouseY > RESUME_BUTTON_Y && mouseY < RESUME_BUTTON_Y + RESUME_BUTTON_HEIGHT) {
            game.batch.draw(ResumeActive, RESUME_BUTTON_X, RESUME_BUTTON_Y, RESUME_BUTTON_WIDTH, RESUME_BUTTON_HEIGHT);

            if (Gdx.input.isTouched()) {
                game.setScreen(new PlayScreen1(game)); // Resume the game
            }
        } else {
            game.batch.draw(ResumeInactive, RESUME_BUTTON_X, RESUME_BUTTON_Y, RESUME_BUTTON_WIDTH, RESUME_BUTTON_HEIGHT);
        }
        if (mouseX > MAIN_MENU_BUTTON_X && mouseX < MAIN_MENU_BUTTON_X + MAIN_MENU_BUTTON_WIDTH
            && mouseY > MAIN_MENU_BUTTON_Y && mouseY < MAIN_MENU_BUTTON_Y + MAIN_MENU_BUTTON_HEIGHT) {
            game.batch.draw(MainMenuActive, MAIN_MENU_BUTTON_X, MAIN_MENU_BUTTON_Y, MAIN_MENU_BUTTON_WIDTH, MAIN_MENU_BUTTON_HEIGHT);

            if (Gdx.input.isTouched()) {
                game.setScreen(new MainMenuScreen(game)); // Go to main menu
            }
        } else {
            game.batch.draw(MainMenuInactive, MAIN_MENU_BUTTON_X, MAIN_MENU_BUTTON_Y, MAIN_MENU_BUTTON_WIDTH, MAIN_MENU_BUTTON_HEIGHT);
        }

        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        PauseScreenBg.dispose();
        GamePaused.dispose();
        ResumeInactive.dispose();
        ResumeActive.dispose();
        MainMenuInactive.dispose();
        MainMenuActive.dispose();
    }
}
