package com.addy.AngryBird;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public abstract class GameObject implements Serializable {
    private static final long serialVersionUID = 1L;

    protected transient Texture texture;
    protected Vector2 position;
    protected int width, height;
    protected String texturePath;

    public GameObject(Texture texture, Vector2 position, int width, int height) {
        this.texture = texture;
        this.position = position;
        this.width = width;
        this.height = height;
        this.texturePath = null;
    }


    public Texture getTexture() {
        return texture;
    }

    public void dispose() {
        if (texture != null) {
            texture.dispose();
            texture = null;
        }
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();


        if (texturePath != null) {
            texture = new Texture(texturePath);
        }
    }


}
