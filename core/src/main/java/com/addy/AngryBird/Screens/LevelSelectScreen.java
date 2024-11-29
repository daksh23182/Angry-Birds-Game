package com.addy.AngryBird.Screens;

import com.addy.AngryBird.AngryBirdd;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

public class LevelSelectScreen implements Screen {

    private AngryBirdd game;
    private Texture LevelSelectScreenBg;
    private Texture Level1;
    private Texture Level2;
    private Texture Level3;

    private static final int BUTTON_WIDTH = 600;
    private static final int BUTTON_HEIGHT = 300;
    private static final int LEVEL_Y = 600; // Same Y position for all levels
    private static final int LEVEL1_X = 500;
    private static final int LEVEL2_X = 1000;
    private static final int LEVEL3_X = 1500;

    public LevelSelectScreen(AngryBirdd game) {
        this.game = game;
    }

    @Override
    public void show() {
        LevelSelectScreenBg = new Texture("LevelSelectScreenBg.png");
        Level1 = new Texture("Level1.png");
        Level2 = new Texture("Level2.png");
        Level3 = new Texture("Level3.png");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY(); // Invert Y-axis

        game.batch.begin();

        // Draw the background
        game.batch.draw(LevelSelectScreenBg, 0, 0);

        // Check and handle Level 1 button hover and click
        if (isButtonHovered(mouseX, mouseY, LEVEL1_X, LEVEL_Y)) {
            if (Gdx.input.isTouched()) {
                if (Gdx.files.local("Level1.save").exists()) {
                    // If a saved game exists, go to LoadGamePromptScreen1
                    game.setScreen(new LoadGamePromptScreen1(game));
                } else {
                    // Otherwise, start a new game
                    game.setScreen(new PlayScreen1(game));
                }
            }
        }
        game.batch.draw(Level1, LEVEL1_X, LEVEL_Y, BUTTON_WIDTH, BUTTON_HEIGHT);

        // Check and handle Level 2 button hover and click
        if (isButtonHovered(mouseX, mouseY, LEVEL2_X, LEVEL_Y)) {
            if (Gdx.input.isTouched()) {
                if (Gdx.files.local("Level2.save").exists()) {
                    // If a saved game exists, go to LoadGamePromptScreen2
                    game.setScreen(new LoadGamePromptScreen2(game));
                } else {
                    // Otherwise, start a new game
                    game.setScreen(new PlayScreen2(game));
                }
            }
        }
        game.batch.draw(Level2, LEVEL2_X, LEVEL_Y, BUTTON_WIDTH, BUTTON_HEIGHT);

        // Check and handle Level 3 button hover and click
        if (isButtonHovered(mouseX, mouseY, LEVEL3_X, LEVEL_Y)) {
            if (Gdx.input.isTouched()) {
                if (Gdx.files.local("Level3.save").exists()) {
                    // If a saved game exists, go to LoadGamePromptScreen3
                    game.setScreen(new LoadGamePromptScreen3(game));
                } else {
                    // Otherwise, start a new game
                    game.setScreen(new PlayScreen3(game));
                }
            }
        }
        game.batch.draw(Level3, LEVEL3_X, LEVEL_Y, BUTTON_WIDTH, BUTTON_HEIGHT);

        game.batch.end();
    }

    private boolean isButtonHovered(int mouseX, int mouseY, int buttonX, int buttonY) {
        return mouseX > buttonX && mouseX < buttonX + BUTTON_WIDTH
            && mouseY > buttonY && mouseY < buttonY + BUTTON_HEIGHT;
    }

    @Override
    public void resize(int width, int height) {
        // Handle resizing if necessary
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
        LevelSelectScreenBg.dispose();
        Level1.dispose();
        Level2.dispose();
        Level3.dispose();
    }
}
