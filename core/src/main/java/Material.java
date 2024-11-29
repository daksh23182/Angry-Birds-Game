package com.addy.AngryBird;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.addy.AngryBird.Collidable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public abstract class Material extends Sprite implements Collidable, Serializable {
    private static final long serialVersionUID = 1L; // Serialization version ID

    protected transient Texture texture; // Texture is marked transient for serialization
    protected Vector2 position;          // Position of the material
    protected Vector2 size;              // Size of the material
    protected int health;                // Health of the material
    protected String texturePath;        // Path to reload the texture after deserialization

    // Constructor to initialize Material
    public Material(String texturePath, Vector2 position, int health) {
        super(new Texture(texturePath));
        this.texturePath = texturePath;
        this.texture = getTexture(); // Cache the texture
        this.position = position;
        this.size = new Vector2(getWidth(), getHeight());
        this.health = health;

        // Set position and size for the Sprite
        setPosition(position.x, position.y);
        setSize(size.x, size.y);
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

    // Getter for size
    public Vector2 getSize() {
        return size;
    }

    // Setter for size
    public void setSize(float width, float height) {
        this.size = new Vector2(width, height);
        super.setSize(width, height);
    }

    // Setter for size using Vector2
    public void setSize(Vector2 size) {
        setSize(size.x, size.y);
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

    // Called when the material is destroyed
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

        // Reinitialize the transient texture field
        this.texture = new Texture(texturePath); // Reload texture from the saved path
        setTexture(texture);

        // Restore position and size of the Sprite
        setPosition(position);
        setSize(size);
    }
}
