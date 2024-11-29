package com.addy.AngryBird.Screens;

import com.addy.AngryBird.AngryBirdd;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

public class LoadGamePromptScreen1 implements Screen {
    private final AngryBirdd game;

    private final Texture background;
    private final Texture loadSavedGameButton;
    private final Texture newGameButton;

    // Button positions and sizes
    private final int buttonWidth = 500;
    private final int buttonHeight = 200;

    private final int loadButtonX = Gdx.graphics.getWidth() / 2 - buttonWidth / 2;
    private final int loadButtonY = Gdx.graphics.getHeight() / 2 + 50;

    private final int newGameButtonX = Gdx.graphics.getWidth() / 2 - buttonWidth / 2;
    private final int newGameButtonY = Gdx.graphics.getHeight() / 2 - 150;

    public LoadGamePromptScreen1(AngryBirdd game) {
        this.game = game;

        // Load textures
        background = new Texture("New.png"); // Add an appropriate background image
        loadSavedGameButton = new Texture("LoadSavedGame.png"); // Add a button texture for loading the saved game
        newGameButton = new Texture("NewGame.png"); // Add a button texture for starting a new game
    }

    @Override
    public void show() {
        // Called when the screen is shown
    }

    @Override
    public void render(float delta) {
        // Clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Begin rendering
        game.batch.begin();

        // Draw the background
        game.batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Draw the buttons
        game.batch.draw(loadSavedGameButton, loadButtonX, loadButtonY, buttonWidth, buttonHeight);
        game.batch.draw(newGameButton, newGameButtonX, newGameButtonY, buttonWidth, buttonHeight);

        // Handle button clicks
        if (Gdx.input.isTouched()) {
            int mouseX = Gdx.input.getX();
            int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY(); // Convert to screen coordinates

            // Check if Load Saved Game button is clicked
            if (mouseX > loadButtonX && mouseX < loadButtonX + buttonWidth &&
                mouseY > loadButtonY && mouseY < loadButtonY + buttonHeight) {
                if (Gdx.files.local("level1.save").exists()) {
                    game.setScreen(new PlayScreen1(game, "Level1.save")); // Load the saved game
                } else {
                    System.out.println("No saved game found.");
                }
            }

            // Check if New Game button is clicked
            if (mouseX > newGameButtonX && mouseX < newGameButtonX + buttonWidth &&
                mouseY > newGameButtonY && mouseY < newGameButtonY + buttonHeight) {
                game.setScreen(new PlayScreen1(game)); // Start a new game
            }
        }

        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        // Dispose of resources
        background.dispose();
        loadSavedGameButton.dispose();
        newGameButton.dispose();
    }
}
