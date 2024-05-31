package com.mygdx.mariasis.Character.Enemy;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.mygdx.mariasis.Screens.GameScreen;
import com.mygdx.mariasis.Mariasis;

public class Trunk extends Enemy {
    public Animation<TextureRegion> shooting;
    public Array<TrunkBullet> bullets;
    public TextureAtlas textureAtlas;

    public float lastShotTime = 0;
    public boolean isFacingLeft;

    public Trunk(GameScreen screen, float x, float y, boolean isFacingLeft) {
        super(screen, x, y);
        this.isFacingLeft = isFacingLeft;
        this.textureAtlas = screen.getTrunkTextureAtlas();

        TextureRegion region = textureAtlas.findRegion("Attack");
        setRegion(region);
        setColor(1,1,1,1);
        setSize(region.getRegionWidth(), region.getRegionHeight());

        bullets = new Array<>();

        Array<TextureRegion> frames = new Array<>();
        for (int i = 0; i < 11; i++) {
            frames.add(new TextureRegion(getTexture(), 1+(i*64), 19, 64, 32));
        }
        shooting = new Animation<TextureRegion>(0.2f, frames);
        setBounds(getX(),getY(),64/Mariasis.PPM,32/Mariasis.PPM);
    }

    public Trunk(GameScreen screen, float x, float y) {
        this(screen, x, y, true);
    }

    @Override
    public void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2dy = world.createBody(bdef);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(15/ Mariasis.PPM, 14/Mariasis.PPM);
        fixtureDef.shape = shape;
        b2dy.createFixture(fixtureDef);
    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);
        for (TrunkBullet bullet : bullets) {
            bullet.draw(batch);
        }
    }

    public void update(float delta){
        setPosition(b2dy.getPosition().x - getWidth()/2,b2dy.getPosition().y-getHeight()/2);

        TextureRegion region = shooting.getKeyFrame(stateTimer, true);
        int currentFrameIndex = shooting.getKeyFrameIndex(stateTimer);
        if ((lastShotTime == 0 && currentFrameIndex == 7) || (lastShotTime > 0 && stateTimer - lastShotTime > 2.199f)) {
            shoot();
            lastShotTime = stateTimer;
        }
        if (!isFacingLeft && !region.isFlipX()) {
            region.flip(true, false);
            isFacingLeft = false;
        }

        for (TrunkBullet bullet : bullets) {
            if (bullet.isDestroyed) {
                bullets.removeValue(bullet, true);
                world.destroyBody(bullet.b2dy);
            }
            bullet.update(delta);
        }
        b2dy.setLinearVelocity(0,b2dy.getLinearVelocity().y);
        setRegion(region);
        stateTimer += delta;
    }

    public void shoot() {
        float bulletY = getY() + 10/Mariasis.PPM;
        float bulletX = (isFacingLeft)? getX() : getX()+getWidth();
        TrunkBullet bullet = new TrunkBullet(this, screen, bulletX, bulletY);
        bullets.add(bullet);
    }
}

