package com.addy.AngryBird.Screens;

import com.addy.AngryBird.*;
import com.addy.AngryBird.AimDetail;
import com.addy.AngryBird.AngryBirdd;
import com.addy.AngryBird.Bird;
import com.addy.AngryBird.BlueBird;
import com.addy.AngryBird.Glass;
import com.addy.AngryBird.MediumPig;
import com.addy.AngryBird.Metal;
import com.addy.AngryBird.Pig;
import com.addy.AngryBird.RedBird;
import com.addy.AngryBird.SmallPig;
import com.addy.AngryBird.BigPig;
import com.addy.AngryBird.Material;
import com.addy.AngryBird.LevelState;
import com.addy.AngryBird.Wood;
import com.addy.AngryBird.YellowBird;
import com.addy.AngryBird.Collidable;
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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;



public class PlayScreen3 implements Screen  {
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

    private final Queue<Bird> birdQueue = new LinkedList<>();
    private Bird currentBird;
    private Bird nextBird;

    private AimDetail birdPhysics;

    private boolean specialAbilityUsed = false;

    private SmallPig smallPig;
    private MediumPig mediumPig;
    private BigPig bigPig;

    private Wood wood;
    private Metal metal;
    private Glass glass;

    private Texture SaveGameButton;
    private final Vector2 saveButtonPosition = new Vector2(100, 900);
    private final Vector2 saveButtonSize = new Vector2(200, 100);

    private boolean gameOver = false;


    public PlayScreen3(AngryBirdd game) {
        this.game = game;

        playScreenBg = new Texture("PlayScreen.png");
        slingTexture = new Texture("SlingShot.png");
        SaveGameButton = new Texture("SaveGame.png");

        slingPosition = new Vector2(700, 200);
        slingLeftAnchor = new Vector2(slingPosition.x - slingWidth, slingPosition.y + 50);
        slingRightAnchor = new Vector2(slingPosition.x + slingWidth, slingPosition.y + 50);

        initializeBirdQueue();
        loadNextBird();

        shapeRenderer = new ShapeRenderer();

        smallPig = new SmallPig(new Texture("SmallPig.png"), new Vector2(1600, 700));
        smallPig.setSize(200,200);

        mediumPig = new MediumPig(new Texture("MediumPig.png"), new Vector2(2050, 200));
        mediumPig.setSize(230, 230);

        bigPig = new com.addy.AngryBird.BigPig(new Texture("BigPig.png"), new Vector2(1300, 700));
        bigPig.setSize(270,270);

        glass = new Glass(new Texture("Glass.png"), new Vector2(1950, 100));
        glass.setSize(new Vector2(75, 600));

        metal = new Metal(new Texture("Metal.png"),new Vector2(1200,500));
        metal.setSize(new Vector2(600,200));

    }
    public PlayScreen3(AngryBirdd game, String saveFile) {
        this(game); // Call the default constructor to initialize variables

        if (Gdx.files.local(saveFile).exists()) {
            System.out.println("Loading game state from: " + saveFile);
            loadLevel(saveFile); // Attempt to load the saved game
        } else {
            System.out.println("No save file found. Starting a new game.");
        }
    }

    private void initializeBirdQueue() {
        birdQueue.add(new RedBird(new Texture("RedBird.png"), new Vector2(slingPosition.x, 100)));
        birdQueue.add(new BlueBird(new Texture("BlueBird.png"), new Vector2(slingPosition.x, 100), 120, 120));
        birdQueue.add(new YellowBird(new Texture("YellowBird.png"), new Vector2(slingPosition.x, 100)));
        birdQueue.add(new RedBird(new Texture("RedBird.png"), new Vector2(slingPosition.x, 100)));
        birdQueue.add(new RedBird(new Texture("RedBird.png"), new Vector2(slingPosition.x, 100)));
        birdQueue.add(new RedBird(new Texture("RedBird.png"), new Vector2(slingPosition.x, 100)));
        birdQueue.add(new RedBird(new Texture("RedBird.png"), new Vector2(slingPosition.x, 100)));
        birdQueue.add(new YellowBird(new Texture("YellowBird.png"), new Vector2(slingPosition.x, 100)));
        birdQueue.add(new YellowBird(new Texture("YellowBird.png"), new Vector2(slingPosition.x, 100)));
        birdQueue.add(new BlueBird(new Texture("BlueBird.png"), new Vector2(slingPosition.x, 100), 120, 120));
        birdQueue.add(new BlueBird(new Texture("BlueBird.png"), new Vector2(slingPosition.x, 100), 120, 120));

    }


    @Override
    public void show() {
        PauseButtonActive = new Texture("PauseButtonActive.png");

    }

    @Override
    public void render(float delta) {
        if (gameOver) {
            return;
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        game.batch.begin();
        game.batch.draw(playScreenBg, 0, 0);
        game.batch.draw(slingTexture, slingPosition.x - 50, slingPosition.y - 50, 200, 250);

        if (metal != null) {
            game.batch.draw(metal.getTexture(), metal.getX(), metal.getY(), metal.getWidth(), metal.getHeight());
        }
        if (glass != null) {
            game.batch.draw(glass.getTexture(), glass.getX(), glass.getY(), glass.getWidth(), glass.getHeight());
        }
        if (bigPig != null) {
            game.batch.draw(bigPig.getTexture(), bigPig.getX(),bigPig.getY(), bigPig.getWidth(), bigPig.getHeight());
        }

        if (mediumPig != null) {
            game.batch.draw(mediumPig.getTexture(), mediumPig.getX(), mediumPig.getY(), mediumPig.getWidth(), mediumPig.getHeight());
        }

        if (smallPig != null) {
            game.batch.draw(smallPig.getTexture(), smallPig.getX(), smallPig.getY(), smallPig.getWidth(), smallPig.getHeight());
        }

        if (nextBird != null) {
            game.batch.draw(nextBird.getTexture(), slingPosition.x - 400, slingPosition.y - 50, 100, 100);
        }

        if (currentBird != null) {
            handleCurrentBird(delta);
        }

        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

        game.batch.draw(PauseButtonActive, -300, 1100);

        if (mouseX > 50 && mouseX < 500 && mouseY > 1000 && mouseY < 1400) {
            if (Gdx.input.isTouched()) {
                game.setScreen(new PauseScreen3(game));
            }
        }

        game.batch.draw(SaveGameButton, saveButtonPosition.x, saveButtonPosition.y, saveButtonSize.x, saveButtonSize.y);

        // Detect Save Game button click
        if (Gdx.input.isTouched()) {

            // Save button click detection
            if (mouseX > saveButtonPosition.x && mouseX < saveButtonPosition.x + saveButtonSize.x &&
                mouseY > saveButtonPosition.y && mouseY < saveButtonPosition.y + saveButtonSize.y) {
                saveLevel(); // Trigger save logic
            }
        }

        game.batch.end();
    }

    @Override
    public void resize(int i, int i1) {

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
        playScreenBg.dispose();
        slingTexture.dispose();
        shapeRenderer.dispose();

        if (wood != null) {
            wood.getTexture().dispose();
            wood = null;
        }

        if (smallPig != null) {
            smallPig.getTexture().dispose();
            smallPig = null;
        }

        if (mediumPig != null) {
            mediumPig.getTexture().dispose();
            mediumPig = null;
        }

        if (currentBird != null) {
            currentBird.getTexture().dispose();
            currentBird = null;
        }

        while (!birdQueue.isEmpty()) {
            Bird bird = birdQueue.poll();
            if (bird != null && bird.getTexture() != null) {
                bird.getTexture().dispose();
            }
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

        Vector2 birdPosition = birdDragging ? currentBird.getPosition() : birdPhysics.getPosition();
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
    }

    private boolean isCollision(Vector2 pos1, Vector2 size1, Vector2 pos2, Vector2 size2) {
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
    private void checkGameOver() {
        if (gameOver) return;
        boolean isVictory = smallPig == null && mediumPig == null && bigPig == null;
        boolean isDefeat = birdQueue.isEmpty() && currentBird == null;

        if (isVictory) {
            System.out.println("Victory! All pigs are dead!");
            gameOver = true;
            Gdx.app.postRunnable(() -> game.setScreen(new GameOverScreenVictory(game)));
        } else if (isDefeat) {
            System.out.println("Defeat! All birds are exhausted!");
            gameOver = true;
            Gdx.app.postRunnable(() -> game.setScreen(new GameOverScreenDefeat(game)));
        }

    }




    private void updateBirdPhysics(float delta) {
        if (birdPhysics == null) {
            return;
        }

        birdPhysics.update(delta);

        if (metal != null && isCollision(birdPhysics.getPosition(), currentBird.getSize(), metal.getPosition(), metal.getSize())) {
            handleCollisionDamage(currentBird, metal);

            if (metal.isDestroyed()) {
                System.out.println("Metal is destroyed!");
                applyDamageToPigsOnMetalBreak();
                metal.getTexture().dispose();
                metal = null;
            }

            if (currentBird.isDestroyed()) {
                currentBird = null;
                birdPhysics = null;
                loadNextBird();
            }
        }

        if (glass != null && isCollision(birdPhysics.getPosition(), currentBird.getSize(), glass.getPosition(), glass.getSize())) {
            handleCollisionDamage(currentBird, glass);

            if (glass.isDestroyed()) {
                System.out.println("Glass is destroyed!");
                glass.getTexture().dispose();
                glass = null; // Remove Glass
            }

            if (currentBird.isDestroyed()) {
                currentBird = null;
                birdPhysics = null;
                loadNextBird();
            }

        }

        if (smallPig != null && isCollision(birdPhysics.getPosition(), currentBird.getSize(), smallPig.getPosition(), smallPig.getSize())) {
            handleCollisionDamage(currentBird, smallPig); // Apply damage to both Bird and SmallPig

            if (smallPig.isDestroyed()) {
                System.out.println("SmallPig is destroyed!");
                smallPig.getTexture().dispose();
                smallPig = null;
            }

            if (currentBird.isDestroyed()) {
                currentBird = null;
                birdPhysics = null;
                loadNextBird();
            }

        }

        if (mediumPig != null && isCollision(birdPhysics.getPosition(), currentBird.getSize(), mediumPig.getPosition(), mediumPig.getSize())) {
            handleCollisionDamage(currentBird, mediumPig);

            if (mediumPig.isDestroyed()) {
                System.out.println("MediumPig is destroyed!");
                mediumPig.getTexture().dispose();
                mediumPig = null;
                checkGameOver();
            }

            if (currentBird.isDestroyed()) {
                currentBird = null;
                birdPhysics = null;
                loadNextBird();
            }

        }


        if (bigPig != null && isCollision(birdPhysics.getPosition(), currentBird.getSize(), bigPig.getPosition(), bigPig.getSize())) {
            handleCollisionDamage(currentBird, bigPig);

            if (bigPig.isDestroyed()) {
                System.out.println("BigPig is destroyed!");
                bigPig.getTexture().dispose();
                bigPig = null;
                checkGameOver();
            }

            if (currentBird.isDestroyed()) {
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

        if (bigPig != null) {
            System.out.println("BigPig: " + bigPig.getHealth());
        } else {
            System.out.println("BigPig: Destroyed");
        }

        if (glass != null) {
            System.out.println("Glass: " + glass.getHealth());
        } else {
            System.out.println("Glass: Destroyed");
        }

        if (metal != null) {
            System.out.println("Metal: " + metal.getHealth());
        } else {
            System.out.println("Metal: Destroyed");
        }

        System.out.println("=========================");
    }

    private void handleCollisionDamage(Collidable object1, Collidable object2) {
        int damage = Math.min(object1.getHealth(), object2.getHealth()); // Calculate damage
        object1.takeDamage(damage);
        object2.takeDamage(damage);

        if (object1 instanceof GameObject && object1.isDestroyed()) {
            System.out.println(object1.getClass().getSimpleName() + " is destroyed!");
            ((GameObject) object1).dispose();
        }

        if (object2 instanceof GameObject && object2.isDestroyed()) {
            System.out.println(object2.getClass().getSimpleName() + " is destroyed!");
            ((GameObject) object2).dispose();
        }


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
        if (currentBird != null) {
            currentBird.getTexture().dispose();
        }

        currentBird = birdQueue.poll();
        nextBird = birdQueue.peek();

        if (currentBird != null) {
            birdPhysics = new AimDetail(currentBird.getPosition().x, slingPosition.y);
            currentBird.setPosition(new Vector2(slingPosition.x, slingPosition.y));
            System.out.println("New bird loaded.");
            displayHealthStatus();
        } else {
            checkGameOver();
        }
    }

    private void applyDamageToPigsOnMetalBreak() {
        System.out.println("Metal has broken! Applying damage to pigs above the metal block...");

        if (smallPig != null) {
            smallPig.takeDamage(50);
            System.out.println("SmallPig took 50 damage. Remaining health: " + smallPig.getHealth());

            if (smallPig.isDestroyed()) {
                System.out.println("SmallPig is destroyed due to metal breaking!");
                smallPig.getTexture().dispose();
                smallPig = null;
            }
        }

        if (bigPig != null) {
            bigPig.takeDamage(50);
            System.out.println("BigPig took 50 damage. Remaining health: " + bigPig.getHealth());

            if (bigPig.isDestroyed()) {
                System.out.println("BigPig is destroyed due to metal breaking!");
                bigPig.getTexture().dispose();
                bigPig = null;
            }
        }

    }

    private void saveLevel() {
        List<Bird> birds = new ArrayList<>(birdQueue);
        List<Pig> pigs = new ArrayList<>();
        if (smallPig != null) pigs.add(smallPig);
        if (mediumPig != null) pigs.add(mediumPig);
        if (bigPig != null) pigs.add(bigPig);

        List<Material> materials = new ArrayList<>();
        if (metal != null) materials.add(metal);
        if (glass != null) materials.add(glass);

        saveLevelState("Level3.save", birds, pigs, materials, currentBird, gameOver);
        System.out.println("Game state saved successfully!");
    }

    private void saveLevelState(String filename, List<Bird> birds, List<Pig> pigs, List<Material> materials, Bird currentBird, boolean gameOver) {
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

    private void loadLevel(String filename) {
        try (FileInputStream fileIn = new FileInputStream(filename);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            LevelState levelState = (LevelState) in.readObject();

            birdQueue.clear();
            birdQueue.addAll(levelState.birds);

            smallPig = null;
            mediumPig = null;
            bigPig = null;
            for (Pig pig : levelState.pigs) {
                if (pig instanceof SmallPig) {
                    smallPig = (SmallPig) pig;
                    smallPig.setTexture(new Texture("SmallPig.png"));
                } else if (pig instanceof MediumPig) {
                    mediumPig = (MediumPig) pig;
                    mediumPig.setTexture(new Texture("MediumPig.png"));
                } else if (pig instanceof BigPig) {
                    bigPig = (BigPig) pig;
                    bigPig.setTexture(new Texture("BigPig.png"));
                }
            }

            metal = null;
            glass = null;
            for (Material material : levelState.materials) {
                if (material instanceof Metal) {
                    metal = (Metal) material;
                    metal.setTexture(new Texture("Metal.png"));
                } else if (material instanceof Glass) {
                    glass = (Glass) material;
                    glass.setTexture(new Texture("Glass.png"));
                }
            }

            currentBird = levelState.currentBird;
            gameOver = levelState.gameOver;

            System.out.println("Game state loaded successfully from " + filename);
        } catch (Exception e) {
            System.err.println("Error loading level state: " + e.getMessage());
        }
    }
}





