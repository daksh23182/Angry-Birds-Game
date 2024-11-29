package com.addy.AngryBird;

import java.io.Serializable;
import java.util.List;
import com.addy.AngryBird.Bird;
import com.addy.AngryBird.Pig;
import com.addy.AngryBird.Material;
import com.addy.AngryBird.Bird;

public class LevelState implements Serializable {
    private static final long serialVersionUID = 1L;

    public List<Bird> birds;             // List of birds
    public List<Pig> pigs;               // List of pigs
    public List<Material> materials;     // List of materials (e.g., wood, glass)
    public Bird currentBird;             // The bird currently in use
    public boolean gameOver;             // Whether the game is over
}
