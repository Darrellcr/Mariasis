package com.mygdx.mariasis.Character;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.mariasis.Mariasis;
import com.mygdx.mariasis.Screens.GameScreen;


public class Maria extends Sprite {
    public enum State{FALLING,RUNNING,JUMPING,STANDING};
    public State currentState;
    public State previousState;
    public World world;
    public Body b2dy;
    public TextureRegion mariaIdle;
    public Animation<TextureRegion> mariaRun;
    public Animation<TextureRegion> mariaJump;
    public GameScreen screen;

    public boolean runningRight;
    public float stateTimer;
    public int jump;


    public Maria(World world, GameScreen screen){
        super(screen.getMariaTextureAtlas().findRegion("maria_idle"));
        this.screen = screen;
        this.world = world;
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;
        jump = 0;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i =0; i<=7;i++){
            frames.add(new TextureRegion(getTexture(),435+(i*45),0,45,63));
        }

        mariaRun = new Animation(0.1f,frames);
        frames.clear();
        for (int i = 0; i<=10;i++){
            frames.add(new TextureRegion(getTexture(), i*43,0,43,63));
        }

        mariaJump = new Animation(0.1f,frames);
        frames.clear();


        defineMaria();
        mariaIdle = new TextureRegion(getTexture(),797,0,39,60);
        setBounds(0,0,40/ Mariasis.PPM,46/Mariasis.PPM);
        setRegion(mariaIdle);
    }

    public void update(float delta) {
        setPosition(b2dy.getPosition().x - getWidth()/2,b2dy.getPosition().y-getHeight()/2);
        setRegion(getFrame(delta));
        if (getY() < 0) {
            screen.GameOver();
        }
    }

    public TextureRegion getFrame(float delta){
        currentState = getState();
        TextureRegion region;
        switch (currentState) {
            case JUMPING:
                region = mariaJump.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region = mariaRun.getKeyFrame(stateTimer,true);
                break;
            case FALLING:
            case STANDING:
            default:
                region = mariaIdle;
                break;
        }
        if ((b2dy.getLinearVelocity().x<0 || !runningRight) && !region.isFlipX()){
            region.flip(true,false);
            runningRight = false;
        }else if ((b2dy.getLinearVelocity().x>0 || runningRight) && region.isFlipX()){
            region.flip(true,false);
            runningRight = true;
        }
        stateTimer = (currentState == previousState) ? stateTimer+delta : 0;
        previousState = currentState;
        return region;

    }

    public State getState(){
        if (b2dy.getLinearVelocity().y>0){
            return  State.JUMPING;
        }else if (b2dy.getLinearVelocity().y<0){
            return State.FALLING;
        }else if (b2dy.getLinearVelocity().x != 0){
            return State.RUNNING;
        }else {
            return State.STANDING;
        }
    }

    public void defineMaria(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(32/Mariasis.PPM,100/Mariasis.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        this.b2dy = world.createBody(bdef);


        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(15/Mariasis.PPM,20/Mariasis.PPM);
        fixtureDef.shape = shape;
        b2dy.createFixture(fixtureDef).setUserData("maria");


        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-15/Mariasis.PPM, -20/Mariasis.PPM),new Vector2(15/Mariasis.PPM, -20/Mariasis.PPM));
        fixtureDef.shape = head;
        fixtureDef.isSensor = true;
        b2dy.createFixture(fixtureDef).setUserData(this);

    }
}
