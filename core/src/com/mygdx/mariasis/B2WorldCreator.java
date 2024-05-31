package com.mygdx.mariasis;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.mariasis.Screens.GameScreen;

public class B2WorldCreator {
    public B2WorldCreator(GameScreen screen) {
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        for (MapObject object : screen.getMap().getLayers().get("trophy").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Trophy(screen,rect);
        }

        for (MapObject object : screen.getMap().getLayers().get("stone").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) /Mariasis.PPM , (rect.getY() + rect.getHeight() / 2) /Mariasis.PPM);

            body = screen.getWorld().createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 /Mariasis.PPM , rect.getHeight() / 2 /Mariasis.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }
        for (MapObject object : screen.getMap().getLayers().get("crate").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) /Mariasis.PPM, (rect.getY() + rect.getHeight() / 2) /Mariasis.PPM);

            body = screen.getWorld().createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 /Mariasis.PPM, rect.getHeight() / 2 /Mariasis.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }
        for (MapObject object : screen.getMap().getLayers().get("ground").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) /Mariasis.PPM, (rect.getY() + rect.getHeight() / 2)/Mariasis.PPM );

            body = screen.getWorld().createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 /Mariasis.PPM, rect.getHeight() / 2 /Mariasis.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

    }
}
