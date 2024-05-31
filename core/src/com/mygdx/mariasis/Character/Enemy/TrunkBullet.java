package com.mygdx.mariasis.Character.Enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.mariasis.Screens.GameScreen;
import com.mygdx.mariasis.Mariasis;

public class TrunkBullet extends Enemy {
    public TextureRegion bullet;
    public Trunk trunk;
    public boolean isDestroyed;

    public TrunkBullet(Trunk trunk, GameScreen screen, float x, float y) {
        super(screen, x, y);
        this.trunk = trunk;
        if (trunk.isFacingLeft) {
            b2dy.setLinearVelocity(-0.8f, 0f);
        } else {
            b2dy.setLinearVelocity(0.8f, 0f);
        }

        TextureRegion region = this.trunk.textureAtlas.findRegion("Bullet");
        setRegion(region);
        setColor(1,1,1,1);
        setSize(region.getRegionWidth(), region.getRegionHeight());

        bullet = new TextureRegion(getTexture(), 1,1,16,16);
        if (!trunk.isFacingLeft && !bullet.isFlipX()) {
            bullet.flip(true, false);
        }

        setRegion(bullet);
        setBounds(getX(), getY(), 16/ Mariasis.PPM, 16/Mariasis.PPM);

    }

    @Override
    public void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2dy = world.createBody(bdef);
        b2dy.setGravityScale(0f);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(5f/ Mariasis.PPM, 4/Mariasis.PPM);
        fixtureDef.shape = shape;
        b2dy.createFixture(fixtureDef).setUserData(this);
    }

    @Override
    public void update(float delta) {
        setPosition(b2dy.getPosition().x - getWidth()/2,b2dy.getPosition().y-getHeight()/2);
    }

    public void onHit() {
        Gdx.app.log("Dead", "Shot");
        screen.GameOver();
    }
}

