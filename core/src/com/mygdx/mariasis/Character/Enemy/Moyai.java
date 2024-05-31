package com.mygdx.mariasis.Character.Enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.mygdx.mariasis.Mariasis;
import com.mygdx.mariasis.Screens.GameScreen;


public class Moyai extends Enemy {
    public Animation<TextureRegion> moyai;
    public boolean setToDestroy;
    public boolean destroyed;
    public boolean isWalkingLeft = true;

    public Moyai(GameScreen screen, float x, float y) {
        super(screen, x, y);
        TextureRegion region = screen.getMoyaiTextureAtlas().findRegion("Rock1_Run");
        setRegion(region);
        setColor(1, 1, 1, 1);
        setSize(region.getRegionWidth(), region.getRegionHeight());

        Array<TextureRegion> frames = new Array<>();
        for (int i = 0; i < 14; i++) {
            frames.add(new TextureRegion(getTexture(), 1+(38*i), 1, 38, 34));
        }
        setBounds(getX(),getY(),38/ Mariasis.PPM,34/Mariasis.PPM);
        moyai = new Animation<>(0.1f,frames);
        setToDestroy = false;
        destroyed = false;

    }

    @Override
    public void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(),getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2dy = world.createBody(bdef);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(18/Mariasis.PPM,15/Mariasis.PPM);
        fixtureDef.shape = shape;
        b2dy.createFixture(fixtureDef).setUserData(this);


        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-19/Mariasis.PPM, 19/Mariasis.PPM),new Vector2(19/Mariasis.PPM, 19/Mariasis.PPM));
        fixtureDef.shape = head;
        fixtureDef.isSensor = true;
        b2dy.createFixture(fixtureDef).setUserData(this);
    }

    public void update(float delta){
        TextureRegion region = moyai.getKeyFrame(stateTimer,true);
        if (isWalkingLeft){
            if (region.isFlipX()){
                region.flip(true,false);
            }
            b2dy.setLinearVelocity(-1f, 0f);
        }else {
            if (!region.isFlipX()){
                region.flip(true,false);
            }
            b2dy.setLinearVelocity(1f, 0f);
        }

        if (setToDestroy && !destroyed){
            world.destroyBody(b2dy);
            destroyed = true;
            setRegion(new TextureRegion(screen.getMoyai2TextureAtlas().findRegion("Rock1_Hit"),0,0,38,28));
            stateTimer = 0;
        }else if (!destroyed){
            setPosition(b2dy.getPosition().x - getWidth()/2,b2dy.getPosition().y-getHeight()/2);
            setRegion(region);
        }
        stateTimer += delta;
    }
    public void onHit(){
        Gdx.app.log("Dead","Noob");
        screen.GameOver();
    }

    public void dispose() {
        setToDestroy = true;
        screen.getPlayer().b2dy.setLinearVelocity(0,3);
    }

}
