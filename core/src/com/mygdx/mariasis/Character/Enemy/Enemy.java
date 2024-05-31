package com.mygdx.mariasis.Character.Enemy;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.mariasis.Screens.GameScreen;

public abstract class Enemy extends Sprite {
    public World world;
    public GameScreen screen;
    public Body b2dy;
    public float stateTimer;

    public Enemy(GameScreen screen, float x, float y){
        this.world = screen.getWorld();
        this.screen = screen;
        setPosition(x,y);
        stateTimer = 0;
        defineEnemy();
    }
    public abstract void defineEnemy();

    public abstract void update(float delta);
}
