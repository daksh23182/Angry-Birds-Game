package com.addy.AngryBird.Screens;

import com.addy.AngryBird.AimDetail;
import com.addy.AngryBird.AngryBirdd;
import com.addy.AngryBird.Bird;
import com.addy.AngryBird.BlueBird;
import com.addy.AngryBird.RedBird;
import com.addy.AngryBird.Pig;
import com.addy.AngryBird.SmallPig;
import com.addy.AngryBird.MediumPig;
import com.addy.AngryBird.Wood;
import com.addy.AngryBird.YellowBird;
import com.addy.AngryBird.GameObject;
import com.addy.AngryBird.Collidable;
import com.addy.AngryBird.Material;
import com.addy.AngryBird.LevelState;
import com.addy.AngryBird.BigPig;
import com.addy.AngryBird.GameObject;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class PlayScreen1 implements Screen {
    private final AngryBirdd game;
    private final Texture playScreenBg;
    private final Texture slingTexture;
    private final ShapeRenderer shapeRenderer;

    private final Vector2 slingPosition;
    private final Vector2 slingLeftAnchor;
    private final Vector2 slingRightAnchor;
    private final float slingWidth = 20f;

    private boolean birdDragging = false;
    private final float maxDragDistance = 200f;

    private Texture PauseButtonActive;
    private Texture PauseButtonInactive;
    private Texture SaveGameButton;
    private final Vector2 saveButtonPosition = new Vector2(100, 900);
    private final Vector2 saveButtonSize = new Vector2(200, 100);

    private final Queue<Bird> birdQueue = new LinkedList<>();
    private Bird currentBird;
    private Bird nextBird;

    private AimDetail birdPhysics;

    private boolean specialAbilityUsed = false;

    private SmallPig smallPig;
    private MediumPig mediumPig;

    private Wood wood;

    private boolean gameOver = false;

    private Texture loadGameButton;
    private final Vector2 loadButtonPosition = new Vector2(Gdx.graphics.getWidth() - 220, Gdx.graphics.getHeight() - 120);



    public PlayScreen1(AngryBirdd game) {
        this.game = game;

        // Load textures
        playScreenBg = new Texture("PlayScreen.png");
        slingTexture = new Texture("SlingShot.png");
        SaveGameButton = new Texture("SaveGame.png");


        // Initialize positions
        slingPosition = new Vector2(700, 200);
        slingLeftAnchor = new Vector2(slingPosition.x - slingWidth, slingPosition.y + 50);
        slingRightAnchor = new Vector2(slingPosition.x + slingWidth, slingPosition.y + 50);

        // Initialize ShapeRenderer
        shapeRenderer = new ShapeRenderer();

        // Initialize game objects
        initializeBirdQueue();
        loadNextBird();

        smallPig = new SmallPig(new Texture("SmallPig.png"), new Vector2(1800, 325));
        smallPig.setSize(200, 200);

        mediumPig = new MediumPig(new Texture("MediumPig.png"), new Vector2(2050, 200));
        mediumPig.setSize(230, 230);

        wood = new Wood(new Texture("Wood.png"), new Vector2(1600, 200));
        wood.setSize(new Vector2(400, 150));
    }


    private void initializeBirdQueue() {
        birdQueue.add(new RedBird(new Texture("RedBird.png"), new Vector2(slingPosition.x, 100)));
        birdQueue.add(new BlueBird(new Texture("BlueBird.png"), new Vector2(slingPosition.x, 100), 120, 120));
        birdQueue.add(new YellowBird(new Texture("YellowBird.png"), new Vector2(slingPosition.x, 100)));
        birdQueue.add(new RedBird(new Texture("RedBird.png"), new Vector2(slingPosition.x, 100)));
    }
    public PlayScreen1(AngryBirdd game, String saveFile) {
        this(game); // Call the default constructor to initialize common elements

        // Load the saved game if a save file is provided
        if (saveFile != null && Gdx.files.local(saveFile).exists()) {
            loadLevel(saveFile);
        }
    }



    private float saveMessageTimer = 0;

    @Override
    public void render(float delta) {
        if (gameOver) {
            return;
        }
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        if (playScreenBg != null) {
            game.batch.draw(playScreenBg, 0, 0);
        }

        if (slingTexture != null) {
            game.batch.draw(slingTexture, slingPosition.x - 50, slingPosition.y - 50, 200, 250);
        }
        if (wood != null && wood.getTexture() != null) {
            game.batch.draw(wood.getTexture(), wood.getX(), wood.getY(), wood.getWidth(), wood.getHeight());
        }

        if (mediumPig != null && mediumPig.getTexture() != null) {
            game.batch.draw(mediumPig.getTexture(), mediumPig.getX(), mediumPig.getY(), mediumPig.getWidth(), mediumPig.getHeight());
        }

        if (smallPig != null && smallPig.getTexture() != null) {
            game.batch.draw(smallPig.getTexture(), smallPig.getX(), smallPig.getY(), smallPig.getWidth(), smallPig.getHeight());
        }

        if (nextBird != null && nextBird.getTexture() != null) {
            game.batch.draw(nextBird.getTexture(), slingPosition.x - 400, slingPosition.y - 50, 100, 100);
        }

        if (currentBird != null) {
            handleCurrentBird(delta);
        }

        game.batch.draw(SaveGameButton, saveButtonPosition.x, saveButtonPosition.y, saveButtonSize.x, saveButtonSize.y);

        // Detect Save Game button click
        if (Gdx.input.isTouched()) {
            int mouseX = Gdx.input.getX();
            int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY(); // Convert to screen coordinates

            // Save button click detection
            if (mouseX > saveButtonPosition.x && mouseX < saveButtonPosition.x + saveButtonSize.x &&
                mouseY > saveButtonPosition.y && mouseY < saveButtonPosition.y + saveButtonSize.y) {
                saveLevel(); // Trigger save logic
            }
        }

        if (saveMessageTimer > 0) {
            game.font.draw(game.batch, "Game Saved!", 1000, 800); // Adjust the position as needed
        }
        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

        game.batch.draw(PauseButtonActive, -300, 1100);

        if (mouseX > 50 && mouseX < 500 && mouseY > 1000 && mouseY < 1400) {
            if (Gdx.input.isTouched()) {
                game.setScreen(new PauseScreen(game));
            }
        }
        game.batch.end();

        if (saveMessageTimer > 0) {
            saveMessageTimer -= delta;
        }
    }


    private void handleCurrentBird(float delta) {
        if (birdPhysics == null || currentBird == null) {
            return;
        }

        if (!birdPhysics.isLaunched()) {
            if (Gdx.input.isTouched()) {
                handleBirdDragging();
            } else if (birdDragging) {
                launchBird();
            }
        } else {
            updateBirdPhysics(delta);

            if (currentBird instanceof YellowBird) {
                handleYellowBirdSpecialAbility();
            } else if (currentBird instanceof BlueBird) {
                handleBlueBirdSpecialAbility();
            }
        }

        // Ensure birdPosition is calculated correctly based on whether the bird is being dragged or not
        Vector2 birdPosition;
        if (birdDragging) {
            birdPosition = currentBird.getPosition();
        } else {
            if (birdPhysics != null) {
                birdPosition = birdPhysics.getPosition();
            } else {
                birdPosition = new Vector2(0, 0); // Default value if physics is null
                System.out.println("Warning: birdPhysics is null at this point!");
            }
        }

        game.batch.draw(currentBird.getTexture(), birdPosition.x - 50, birdPosition.y - 50, currentBird.getSize().x, currentBird.getSize().y);

        if (birdDragging) {
            game.batch.end();
            drawSlingLine(birdPosition);
            game.batch.begin();
        }
    }


    private void drawSlingLine(Vector2 birdPosition) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(0.5f, 0.3f, 0.1f, 1);
        shapeRenderer.line(slingLeftAnchor.x, slingLeftAnchor.y, birdPosition.x, birdPosition.y);
        shapeRenderer.line(slingRightAnchor.x, slingRightAnchor.y, birdPosition.x, birdPosition.y);
        shapeRenderer.end();
    }

    private void handleBirdDragging() {
        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
        Vector2 dragPosition = new Vector2(mouseX, mouseY);

        if (!birdDragging && dragPosition.dst(currentBird.getPosition()) <= 100) {
            birdDragging = true;
        }
        if (birdDragging) {
            if (dragPosition.dst(slingPosition) > maxDragDistance) {
                dragPosition = slingPosition.cpy().add(dragPosition.sub(slingPosition).nor().scl(maxDragDistance));
            }
            currentBird.setPosition(dragPosition);
        }
    }

    private void launchBird() {
        birdDragging = false;

        Vector2 launchVelocity = slingPosition.cpy().sub(currentBird.getPosition()).scl(10);
        birdPhysics.setVelocity(launchVelocity.x, launchVelocity.y);
        birdPhysics.launch();

        specialAbilityUsed = false;

        displayHealthStatus();
    }

    private boolean isCollision(Vector2 pos1, Vector2 size1, Vector2 pos2, Vector2 size2) {
        if (pos1 == null || size1 == null || pos2 == null || size2 == null) {
            return false;
        }

        float left1 = pos1.x;
        float right1 = pos1.x + size1.x;
        float bottom1 = pos1.y;
        float top1 = pos1.y + size1.y;

        float left2 = pos2.x;
        float right2 = pos2.x + size2.x;
        float bottom2 = pos2.y;
        float top2 = pos2.y + size2.y;

        return !(right1 < left2 || left1 > right2 || top1 < bottom2 || bottom1 > top2);
    }

    private void handleCollisionDamage(Collidable object1, Collidable object2) {
        int damage = Math.min(object1.getHealth(), object2.getHealth()); // Damage is the lesser of the two healths
        object1.takeDamage(damage);
        object2.takeDamage(damage);

        if (object1 instanceof GameObject && object1.isDestroyed()) {
            System.out.println(object1.getClass().getSimpleName() + " is destroyed!");
            ((GameObject) object1).dispose();
            removeGameObject((GameObject) object1);
        }

        if (object2 instanceof GameObject && object2.isDestroyed()) {
            System.out.println(object2.getClass().getSimpleName() + " is destroyed!");
            ((GameObject) object2).dispose();
            removeGameObject((GameObject) object2);
        }
    }

    private List<GameObject> gameObjects = new ArrayList<>();

    private void removeGameObject(GameObject gameObject) {
        gameObjects.remove(gameObject);
        System.out.println(gameObject.getClass().getSimpleName() + " removed.");
    }

    private void checkGameOver() {
        if (gameOver) return;

        boolean isVictory = (smallPig == null && mediumPig == null);
        boolean isDefeat = birdQueue.isEmpty() && (currentBird == null || currentBird.isDestroyed());

        if (isVictory) {
            System.out.println("Victory! All pigs are dead!");
            gameOver = true;
            Gdx.app.postRunnable(() -> game.setScreen(new GameOverScreenVictory(game)));
            return;
        } else if (isDefeat) {
            System.out.println("Defeat! All birds are exhausted!");
            gameOver = true;
            Gdx.app.postRunnable(() -> game.setScreen(new GameOverScreenDefeat(game)));
            return;
        }
    }


    private void updateBirdPhysics(float delta) {
        if (birdPhysics == null) return;

        birdPhysics.update(delta);

        if (wood != null && isCollision(birdPhysics.getPosition(), currentBird.getSize(), wood.getPosition(), wood.getSize())) {
            System.out.println("Bird collided with Wood!");
            handleCollisionDamage(currentBird, wood);

            if (wood.isDestroyed()) {
                System.out.println("Wood is destroyed!");
                wood.getTexture().dispose();
                wood = null; // Remove Wood

                if (smallPig != null) {
                    System.out.println("SmallPig deals 25 damage due to falling Wood!");
                    smallPig.takeDamage(25);

                    if (smallPig.isDestroyed()) {
                        System.out.println("SmallPig is destroyed due to falling Wood!");
                        smallPig.getTexture().dispose();
                        smallPig = null;
                    }
                }
            }

            if (currentBird.isDestroyed()) {
                System.out.println(currentBird.getClass().getSimpleName() + " is destroyed!");
                currentBird = null;
                birdPhysics = null;
                loadNextBird();
            }
        }

        if (smallPig != null && isCollision(birdPhysics.getPosition(), currentBird.getSize(), smallPig.getPosition(), smallPig.getSize())) {
            System.out.println("Bird collided with SmallPig!");
            handleCollisionDamage(currentBird, smallPig);

            if (smallPig.isDestroyed()) {
                System.out.println("SmallPig is destroyed!");
                smallPig.getTexture().dispose();
                smallPig = null;
            }

            if (currentBird.isDestroyed()) {
                System.out.println(currentBird.getClass().getSimpleName() + " is destroyed!");
                currentBird = null;
                birdPhysics = null;
                loadNextBird();
            }
        }

        if (mediumPig != null && isCollision(birdPhysics.getPosition(), currentBird.getSize(), mediumPig.getPosition(), mediumPig.getSize())) {
            System.out.println("Bird collided with MediumPig!");
            handleCollisionDamage(currentBird, mediumPig);

            if (mediumPig.isDestroyed()) {
                System.out.println("MediumPig is destroyed!");
                mediumPig.getTexture().dispose();
                mediumPig = null;
            }

            if (currentBird.isDestroyed()) {
                System.out.println(currentBird.getClass().getSimpleName() + " is destroyed!");
                currentBird = null;
                birdPhysics = null;
                loadNextBird();
            }
        }
        if (birdPhysics.getPosition().y <= 0 || birdPhysics.getPosition().x < 0 ||
            birdPhysics.getPosition().x > 2400 || birdPhysics.getPosition().y > 1500) {
            currentBird = null;
            birdPhysics = null;
            loadNextBird();
        }

        checkGameOver();
    }


    private void displayHealthStatus() {
        System.out.println("===== Health Status =====");

        if (currentBird != null) {
            System.out.println("Current Bird (" + currentBird.getClass().getSimpleName() + "): " + currentBird.getHealth());
        } else {
            System.out.println("No current bird.");
        }

        if (smallPig != null) {
            System.out.println("SmallPig: " + smallPig.getHealth());
        } else {
            System.out.println("SmallPig: Destroyed");
        }

        if (mediumPig != null) {
            System.out.println("MediumPig: " + mediumPig.getHealth());
        } else {
            System.out.println("MediumPig: Destroyed");
        }

        if (wood != null) {
            System.out.println("Wood: " + wood.getHealth());
        } else {
            System.out.println("Wood: Destroyed");
        }
        System.out.println("=========================");
    }

    private void handleYellowBirdSpecialAbility() {
        if (Gdx.input.justTouched() && !specialAbilityUsed) {
            specialAbilityUsed = true;

            Vector2 currentVelocity = birdPhysics.getVelocity();
            birdPhysics.setVelocity(currentVelocity.x * 2, currentVelocity.y * 2);
            System.out.println("YellowBird's special ability activated: Speed doubled!");
        }
    }

    private void handleBlueBirdSpecialAbility() {
        if (Gdx.input.justTouched() && !specialAbilityUsed) {
            specialAbilityUsed = true;

            currentBird.setSize(currentBird.getSize().x * 2, currentBird.getSize().y * 2);
            System.out.println("BlueBird's special ability activated: Size doubled!");
        }
    }

    private void loadNextBird() {
        // Dispose of the previous bird's texture if it exists
        if (currentBird != null) {
            Texture birdTexture = currentBird.getTexture();
            if (birdTexture != null) {
                birdTexture.dispose();
            }
        }

        // Ensure that the bird queue is not empty
        if (birdQueue.isEmpty()) {
            checkGameOver();  // No more birds to load, end the game
            return;
        }

        // Load the next bird from the queue
        currentBird = birdQueue.poll();
        nextBird = birdQueue.peek();

        // Ensure currentBird is not null before proceeding
        if (currentBird != null) {
            // Initialize bird physics (ensure AimDetail constructor is valid)
            birdPhysics = new AimDetail(currentBird.getPosition().x, slingPosition.y);

            // Make sure AimDetail is properly initialized
            if (birdPhysics != null) {
                currentBird.setPosition(new Vector2(slingPosition.x, slingPosition.y));
                displayHealthStatus();
            } else {
                // Handle case where AimDetail failed to initialize
                System.out.println("Error: Failed to initialize bird physics.");
                checkGameOver();
            }
        } else {
            checkGameOver();  // If no bird was loaded, end the game
        }
    }



    @Override
    public void dispose() {
        if (playScreenBg != null) playScreenBg.dispose();
        if (slingTexture != null) slingTexture.dispose();
        if (SaveGameButton != null) SaveGameButton.dispose();
        if (loadGameButton != null) loadGameButton.dispose();
        if (shapeRenderer != null) shapeRenderer.dispose();

        if (wood != null) wood.getTexture().dispose();
        if (smallPig != null) smallPig.getTexture().dispose();
        if (mediumPig != null) mediumPig.getTexture().dispose();
        if (currentBird != null) currentBird.getTexture().dispose();

        while (!birdQueue.isEmpty()) {
            Bird bird = birdQueue.poll();
            if (bird != null && bird.getTexture() != null) {
                bird.getTexture().dispose();
            }
        }
    }



    public void saveLevelState(String filename, List<Bird> birds, List<Pig> pigs, List<Material> materials, Bird currentBird, boolean gameOver) {

        LevelState levelState = new LevelState();
        levelState.birds = birds;
        levelState.pigs = pigs;
        levelState.materials = materials;
        levelState.currentBird = currentBird;
        levelState.gameOver = gameOver;

        try (FileOutputStream fileOut = new FileOutputStream(filename);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(levelState);
            System.out.println("Level state saved to " + filename);
        } catch (Exception e) {
            System.err.println("Error saving level state: " + e.getMessage());
        }
    }

    public void saveLevel() {

        List<Bird> birds = new ArrayList<>(birdQueue);
        List<Pig> pigs = new ArrayList<>();
        if (smallPig != null) pigs.add(smallPig);
        if (mediumPig != null) pigs.add(mediumPig);

        List<Material> materials = new ArrayList<>();
        if (wood != null) materials.add(wood);

        saveLevelState("Level1.save", birds, pigs, materials, currentBird, gameOver);

        System.out.println("Game state saved successfully!");
    }

    public void loadLevel(String filename) {
        try (FileInputStream fileIn = new FileInputStream(filename);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            LevelState levelState = (LevelState) in.readObject();

            // Restore birds
            birdQueue.clear();
            birdQueue.addAll(levelState.birds);


            // Restore pigs
            smallPig = null;
            mediumPig = null;
            for (Pig pig : levelState.pigs) {
                if (pig instanceof SmallPig) {
                    smallPig = (SmallPig) pig;
                    smallPig.setTexture(new Texture("SmallPig.png"));
                    smallPig.setSize(200, 200);
                    smallPig.setPosition(smallPig.getPosition()); // Restore position
                } else if (pig instanceof MediumPig) {
                    mediumPig = (MediumPig) pig;
                    mediumPig.setTexture(new Texture("MediumPig.png"));
                    mediumPig.setSize(230, 230);
                    mediumPig.setPosition(mediumPig.getPosition()); // Restore position
                }
            }

            // Restore materials
            wood = null;
            for (Material material : levelState.materials) {
                if (material instanceof Wood) {
                    wood = (Wood) material;
                    wood.setTexture(new Texture("Wood.png"));
                    wood.setSize(new Vector2(400, 150));
                    wood.setPosition(wood.getPosition()); // Restore position
                }
            }

            // Restore the current bird
            if (levelState.currentBird != null) {
                currentBird = levelState.currentBird;
                currentBird.setTexture(new Texture("BirdTexture.png")); // Replace with actual bird texture
                currentBird.setPosition(currentBird.getPosition()); // Restore position
            }

            // Restore game over status
            gameOver = levelState.gameOver;

            System.out.println("Game state loaded successfully from " + filename);
        } catch (Exception e) {
            System.err.println("Error loading level state: " + e.getMessage());
        }
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
    public void show() {
        PauseButtonActive = new Texture("PauseButtonActive.png");
        PauseButtonInactive = new Texture("PauseButtonInactive.png");
    }
}



