package com.addy.AngryBird;

public interface Collidable {
    int getHealth();
    void takeDamage(int damage);
    boolean isDestroyed();
}
