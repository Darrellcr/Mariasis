package com.mygdx.mariasis;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.mariasis.Screens.GameScreen;

public class Trophy {
    public World world;
    public TiledMap tiledMap;
    public GameScreen screen;
    public Rectangle rectangle;
    public Body body;
    public Fixture fixture;

    public Trophy (GameScreen screen, Rectangle rectangle){
        this.world = screen.getWorld();
        this.tiledMap = screen.getMap();
        this.screen = screen;
        this.rectangle = rectangle;

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((rectangle.getX() + rectangle.getWidth() / 2) / Mariasis.PPM, (rectangle.getY() + rectangle.getHeight() / 2) /Mariasis.PPM);

        body = world.createBody(bdef);

        shape.setAsBox(rectangle.getWidth() / 2 /Mariasis.PPM, rectangle.getHeight() / 2 /Mariasis.PPM);
        fdef.shape = shape;
        fixture = body.createFixture(fdef);
        fixture.setUserData(this);
    }

    public void onHit(){
        Gdx.app.log("Trophy","Collision");
        screen.GameWin();
    }
}

