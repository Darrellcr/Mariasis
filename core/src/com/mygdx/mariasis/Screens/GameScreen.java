package com.mygdx.mariasis.Screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.mariasis.*;
import com.mygdx.mariasis.Character.Enemy.Moyai;
import com.mygdx.mariasis.Character.Enemy.Spike;
import com.mygdx.mariasis.Character.Enemy.Trunk;
import com.mygdx.mariasis.Character.Maria;


public class GameScreen implements Screen {
    final Mariasis game;
    private TextureAtlas mariaTextureAtlas;
    private TextureAtlas spikeTextureAtlas;
    private TextureAtlas trunkTextureAtlas;
    private TextureAtlas moyaiTextureAtlas;
    private TextureAtlas moyai2TextureAtlas;
    private Maria player;
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    //map
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    //box2d
    private World world;
    private Box2DDebugRenderer b2dr;
    private Music background;
    private Sound jump;
    private Sound mariaDeath;
    private Sound mariaWin;
    private Array<Spike> listSpike;
    private Array<Moyai> listMoyai;
    private Array<Trunk> listTrunk;

    public GameScreen(final Mariasis game) {
        this.game = game;
        background = Gdx.audio.newMusic(Gdx.files.internal("audio/bgn.mp3"));
        jump = Gdx.audio.newSound(Gdx.files.internal("audio/maria_jump.mp3"));
        mariaDeath = Gdx.audio.newSound(Gdx.files.internal("audio/maria_death.mp3"));
        mariaWin = Gdx.audio.newSound(Gdx.files.internal("audio/SoundWin.mp3"));
        background.setLooping(true);
        background.play();
        mariaTextureAtlas = new TextureAtlas("Character/mariaAll1.pack");
        spikeTextureAtlas = new TextureAtlas("Character/turtle.pack");
        trunkTextureAtlas = new TextureAtlas("Character/trunk.pack");
        moyaiTextureAtlas = new TextureAtlas("Character/moyai_run.pack");
        moyai2TextureAtlas = new TextureAtlas("Character/Rock1_Hit.pack");

        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(Mariasis.width/Mariasis.PPM,Mariasis.height/Mariasis.PPM,gamecam);
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("map/MariaSisMap.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1/Mariasis.PPM);
        gamecam.position.set(gamePort.getWorldWidth()/2,gamePort.getWorldHeight()/2,0);
        world = new World(new Vector2(0,-10),true);
        b2dr = new Box2DDebugRenderer();
        new B2WorldCreator(this);
        player = new Maria(world,this);

        // spike
        listSpike = new Array<>();
        listSpike.add(new Spike(this,704/Mariasis.PPM,300/Mariasis.PPM));
        listSpike.add(new Spike(this,2700/Mariasis.PPM,250/Mariasis.PPM));
        listSpike.add(new Spike(this,3200/Mariasis.PPM,250/Mariasis.PPM));
        listSpike.add(new Spike(this,3350/Mariasis.PPM,400/Mariasis.PPM));
        listSpike.add(new Spike(this,3900/Mariasis.PPM,400/Mariasis.PPM));
        listSpike.add(new Spike(this,4300/Mariasis.PPM,250/Mariasis.PPM));
        listSpike.add(new Spike(this,4675/Mariasis.PPM,250/Mariasis.PPM));
        listSpike.add(new Spike(this,4775/Mariasis.PPM,250/Mariasis.PPM));
        listSpike.add(new Spike(this,4875/Mariasis.PPM,250/Mariasis.PPM));
        listSpike.add(new Spike(this,4975/Mariasis.PPM,250/Mariasis.PPM));
        listSpike.add(new Spike(this,5060/Mariasis.PPM,250/Mariasis.PPM));
        listSpike.add(new Spike(this,6000/Mariasis.PPM,250/Mariasis.PPM));
        listSpike.add(new Spike(this,6180/Mariasis.PPM,250/Mariasis.PPM));
        listSpike.add(new Spike(this,6200/Mariasis.PPM,250/Mariasis.PPM));
        listSpike.add(new Spike(this,6325/Mariasis.PPM,250/Mariasis.PPM));
        listSpike.add(new Spike(this,6350/Mariasis.PPM,250/Mariasis.PPM));
        listSpike.add(new Spike(this,6500/Mariasis.PPM,250/Mariasis.PPM));
        listSpike.add(new Spike(this,6525/Mariasis.PPM,250/Mariasis.PPM));
        listSpike.add(new Spike(this,6550/Mariasis.PPM,250/Mariasis.PPM));
        listSpike.add(new Spike(this,6925/Mariasis.PPM,400/Mariasis.PPM));
        listSpike.add(new Spike(this,7000/Mariasis.PPM,400/Mariasis.PPM));
        listSpike.add(new Spike(this,7075/Mariasis.PPM,400/Mariasis.PPM));
        listSpike.add(new Spike(this,7800/Mariasis.PPM,400/Mariasis.PPM));
        listSpike.add(new Spike(this,8527/Mariasis.PPM,400/Mariasis.PPM));

        // moyai
        listMoyai = new Array<>();
        listMoyai.add(new Moyai(this,975/Mariasis.PPM,120/Mariasis.PPM));
        listMoyai.add(new Moyai(this,1000/Mariasis.PPM,120/Mariasis.PPM));
        listMoyai.add(new Moyai(this,2100/Mariasis.PPM,100/Mariasis.PPM));
        listMoyai.add(new Moyai(this,2200/Mariasis.PPM,100/Mariasis.PPM));
        listMoyai.add(new Moyai(this,5150/Mariasis.PPM,120/Mariasis.PPM));
        listMoyai.add(new Moyai(this,5500/Mariasis.PPM,120/Mariasis.PPM));
        listMoyai.add(new Moyai(this,7100/Mariasis.PPM,120/Mariasis.PPM));

        // trunk
        listTrunk = new Array<>();
        listTrunk.add(new Trunk(this,1885/Mariasis.PPM,250/Mariasis.PPM));
        listTrunk.add(new Trunk(this,2950/Mariasis.PPM,250/Mariasis.PPM));
        listTrunk.add(new Trunk(this,4575/Mariasis.PPM,250/Mariasis.PPM));
        listTrunk.add(new Trunk(this,8100/Mariasis.PPM,250/Mariasis.PPM));
        listTrunk.add(new Trunk(this,9500/Mariasis.PPM,250/Mariasis.PPM));

        world.setContactListener(new WorldContactListener());
    }

    public void handleInput(float delta){
        if ((Gdx.input.isKeyJustPressed(Input.Keys.W) ||Gdx.input.isKeyJustPressed(Input.Keys.SPACE))&&player.jump < 2){
            player.b2dy.applyLinearImpulse(new Vector2(0, 4),player.b2dy.getWorldCenter(),true);
            //player.jump++;
            jump.play();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) && player.b2dy.getLinearVelocity().x <= 2){
            player.b2dy.applyLinearImpulse(new Vector2(0.1f, 0),player.b2dy.getWorldCenter(),true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A) && player.b2dy.getLinearVelocity().x >= -2){
            player.b2dy.applyLinearImpulse(new Vector2(-0.1f, 0),player.b2dy.getWorldCenter(),true);
        }
    }

    public void update(float delta){
        handleInput(delta);
        world.step(1/60f, 6, 2);

        player.update(delta);
        for (Spike spike: listSpike){
            spike.update(delta);
        }
        for (Moyai moyai: listMoyai){
            moyai.update(delta);
        }
        for (Trunk trunk: listTrunk){
            trunk.update(delta);
        }
        gamecam.position.x = player.b2dy.getPosition().x;
        gamecam.update();
        renderer.setView(gamecam);

    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.render();
       // b2dr.render(world,gamecam.combined);
        game.batch.setProjectionMatrix(gamecam.combined);

        game.batch.begin();
        for (Spike spike: listSpike){
            spike.draw(game.batch);
        }
        for (Moyai moyai: listMoyai) {
            if (!(moyai.destroyed && moyai.stateTimer>1.5)){
                moyai.draw(game.batch);
            }
        }
        for (Trunk trunk: listTrunk) {
            trunk.draw(game.batch);
        }
        player.draw(game.batch);
        game.batch.end();

    }

    public World getWorld() {
        return world;
    }

    public TiledMap getMap() {
        return map;
    }

    public TextureAtlas getMariaTextureAtlas() {
        return mariaTextureAtlas;
    }

    public TextureAtlas getSpikeTextureAtlas() {
        return spikeTextureAtlas;
    }

    public TextureAtlas getTrunkTextureAtlas() { return trunkTextureAtlas; }

    public TextureAtlas getMoyaiTextureAtlas(){
        return moyaiTextureAtlas;
    }

    public TextureAtlas getMoyai2TextureAtlas() {
        return moyai2TextureAtlas;
    }

    public void GameOver(){
        background.dispose();

        mariaDeath.play();
        game.setScreen(new GameOverScreen(game));
    }
    public void GameWin(){
        background.dispose();
        mariaWin.play();
        game.setScreen(new WinScreen(game));
    }

    public Maria getPlayer() {
        return player;
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        background.dispose();
    }

}
