package com.addy.AngryBird;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.addy.AngryBird.Collidable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Pig extends Sprite implements Collidable, Serializable {
    private static final long serialVersionUID = 1L;

    protected transient Texture texture; // Mark texture as transient for serialization
    protected Vector2 position;          // Position of the pig
    protected float width;               // Width of the pig
    protected float height;              // Height of the pig
    protected int health;                // Health of the pig
    protected String texturePath;        // Path to the pig's texture

    // Constructor to initialize the Pig
    public Pig(String texturePath, Vector2 position, float width, float height, int health) {
        super(new Texture(texturePath)); // Set texture for the Sprite
        this.texturePath = texturePath;  // Store the texture path for serialization
        this.texture = getTexture();    // Cache the texture
        this.position = position;
        this.width = width;
        this.height = height;
        this.health = health;

        // Set position and size of the Sprite
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

    // Called when the pig is destroyed
    protected void onDestroyed() {
        System.out.println(this.getClass().getSimpleName() + " is destroyed!");
    }

    // Serialization: Write object to output stream
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject(); // Serialize non-transient fields
    }

    // Deserialization: Read object from input stream
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject(); // Deserialize non-transient fields

        // Reinitialize the transient texture field
        this.texture = new Texture(texturePath); // Reload the texture from the saved path
        setTexture(texture); // Set the texture for the Sprite superclass

        // Restore the position and size of the Sprite
        setPosition(position);
        setSize(width, height);
    }
}
