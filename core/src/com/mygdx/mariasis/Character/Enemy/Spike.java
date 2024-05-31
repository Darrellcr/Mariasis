package com.mygdx.mariasis.Character.Enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.mygdx.mariasis.Screens.GameScreen;
import com.mygdx.mariasis.Mariasis;

public class Spike extends Enemy{
    public Animation<TextureRegion> spike;

    public Spike(GameScreen screen, float x, float y) {
        super(screen, x, y);
        TextureRegion region = screen.getSpikeTextureAtlas().findRegion("Spikes_out");
        setRegion(region);
        setColor(1, 1, 1, 1);
        setSize(region.getRegionWidth(), region.getRegionHeight());

        Array<TextureRegion> frames = new Array<>();
        for (int i = 0; i < 8; i++) {
            frames.add(new TextureRegion(getTexture(), 619 + 44*i, 1, 44, 26));
        }
        setBounds(getX(),getY(),44/Mariasis.PPM,26/Mariasis.PPM);
        spike = new Animation<>(0.1f,frames);

    }

    @Override
    public void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(),getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2dy = world.createBody(bdef);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(15/Mariasis.PPM,12/Mariasis.PPM);
        fixtureDef.shape = shape;
        b2dy.createFixture(fixtureDef).setUserData(this);

    }

    public void update(float delta){
        setPosition(b2dy.getPosition().x - getWidth()/2,b2dy.getPosition().y-getHeight()/2);
        setRegion(spike.getKeyFrame(stateTimer,true));
        stateTimer += delta;
        b2dy.setLinearVelocity(0,b2dy.getLinearVelocity().y);
    }
    public void onHit(){
        Gdx.app.log("Dead","Noob");
        screen.GameOver();
    }

}

