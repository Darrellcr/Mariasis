package com.mygdx.mariasis;

import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.mariasis.Character.Enemy.Moyai;
import com.mygdx.mariasis.Character.Enemy.Spike;
import com.mygdx.mariasis.Character.Enemy.TrunkBullet;
import com.mygdx.mariasis.Character.Maria;

public class WorldContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture fixture1 = contact.getFixtureA();
        Fixture fixture2 = contact.getFixtureB();

        if (fixture1.getUserData() == "maria" || fixture2.getUserData() == "maria"){
            Fixture Maria = fixture1.getUserData() == "maria" ? fixture1 : fixture2;
            Fixture object = Maria == fixture1 ? fixture2 : fixture1;
            if (object.getUserData() != null && Trophy.class.isAssignableFrom(object.getUserData().getClass())) {
                ((Trophy) object.getUserData()).onHit();
            }else if (object.getUserData() != null && Spike.class.isAssignableFrom(object.getUserData().getClass())){
                ((Spike)object.getUserData()).onHit();
            }else if (object.getUserData() != null && TrunkBullet.class.isAssignableFrom(object.getUserData().getClass())){
                ((TrunkBullet)object.getUserData()).onHit();
            }else if(object.getUserData() != null && Moyai.class.isAssignableFrom(object.getUserData().getClass())){
                if (object.getShape() instanceof EdgeShape){
                    ((Moyai)object.getUserData()).dispose();
                }else {
                    ((Moyai)object.getUserData()).onHit();
                }
            }
        } else if (fixture1.getUserData() instanceof TrunkBullet || fixture2.getUserData() instanceof TrunkBullet) {
            Object bullet = (fixture1.getUserData() instanceof TrunkBullet)? fixture1.getUserData() : fixture2.getUserData();
            ((TrunkBullet) bullet).isDestroyed = true;
        } else if (fixture1.getUserData() instanceof Moyai || fixture2.getUserData() instanceof Moyai) {
            Fixture moyai = (fixture1.getUserData() instanceof Moyai) ? fixture1 : fixture2;
            if (fixture1.getUserData() instanceof Moyai && fixture2.getUserData() instanceof Moyai){
                boolean cek = ((Moyai) fixture1.getUserData()).isWalkingLeft;
                ((Moyai) fixture1.getUserData()).isWalkingLeft =!((Moyai) fixture1.getUserData()).isWalkingLeft;
                ((Moyai) fixture2.getUserData()).isWalkingLeft =!((Moyai) fixture1.getUserData()).isWalkingLeft;
            } else if (moyai.getShape() instanceof PolygonShape){
                boolean cek = ((Moyai) moyai.getUserData()).isWalkingLeft;
                ((Moyai) moyai.getUserData()).isWalkingLeft = !cek;
            }
        } else if (fixture1.getUserData() instanceof Maria || fixture2.getUserData() instanceof Maria) {
            Fixture maria = (fixture1.getUserData() instanceof Maria) ? fixture1 : fixture2;
            if (maria.getShape() instanceof EdgeShape){
                ((Maria)maria.getUserData()).jump = 0;
            }
        }

    }
    @Override
    public void endContact(Contact contact) {
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
}
