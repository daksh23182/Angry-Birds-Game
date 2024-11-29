package com.addy.AngryBird;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.addy.AngryBird.Collidable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public abstract class Bird extends Sprite implements Collidable, Serializable {
    private static final long serialVersionUID = 1L;

    protected transient Texture texture; // Mark texture as transient since it cannot be serialized
    protected Vector2 position;          // Position of the bird
    protected float width;               // Width of the bird
    protected float height;              // Height of the bird
    protected int health;                // Health of the bird
    public String texturePath;        // Path to the bird's texture

    public Bird(String texturePath, Vector2 position, float width, float height, int health) {
        super(new Texture(texturePath));
        this.texturePath = texturePath;
        this.texture = getTexture();
        this.position = position;
        this.width = width;
        this.height = height;
        this.health = health;

        // Set position and size of the sprite
        setPosition(position.x, position.y);
        setSize(width, height);
    }

    // Getter for position
    public Vector2 getPosition() {
        return position;
    }

    // Setter for position
    public void setPosition(Vector2 position) {
        this.position = position;
        super.setPosition(position.x, position.y);
    }

    // Setter for size
    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
        super.setSize(width, height);
    }

    // Getter for size
    public Vector2 getSize() {
        return new Vector2(width, height);
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            health = 0; // Ensure health doesn't go below zero
            onDestroyed();
        }
    }

    @Override
    public boolean isDestroyed() {
        return health <= 0;
    }

    // Called when the bird is destroyed
    protected void onDestroyed() {
        System.out.println(this.getClass().getSimpleName() + " is destroyed!");
    }

    // Serialize the object
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject(); // Serialize non-transient fields
    }

    // Deserialize the object
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject(); // Deserialize non-transient fields

        // Reinitialize transient fields
        this.texture = new Texture(texturePath); // Reload texture from saved path
        setTexture(texture);
        setPosition(position); // Restore position
        setSize(width, height); // Restore size
    }
}
