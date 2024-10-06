package com.edo.pong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import helper.Constants;
import objects.Ball;
import objects.Player;
import objects.PlayerAI;
import objects.Wall;

/** First screen of the application. Displayed after the application is created. */
public class GameScreen extends ScreenAdapter {
    private OrthographicCamera camera;
    private SpriteBatch spriteBatch;
    private World world; // Store all the Box2D bodies
    private Box2DDebugRenderer box2DDebugRenderer;
    private GameContactListener gameContactListener;

    // Game objects
    private Player player;
    private PlayerAI playerAI;
    private Ball ball;
    private Wall wallTop, wallBottom;

    public GameScreen(OrthographicCamera camera) {
        this.camera = camera;
        this.camera.position.set(new Vector3(Boot.INSTANCE.getScreenWidth() / 2, Boot.INSTANCE.getScreenHeight() / 2, 0)); // Set camera to the center of our game
        this.spriteBatch = new SpriteBatch();
        this.world = new World(new Vector2(0, 0), false); // Set the gravity
        this.box2DDebugRenderer = new Box2DDebugRenderer();
        this.gameContactListener = new GameContactListener(this);
        this.world.setContactListener(this.gameContactListener);

        this.player = new Player(16, Boot.INSTANCE.getScreenHeight() / 2, this);
        this.playerAI = new PlayerAI(Boot.INSTANCE.getScreenWidth() - 16, Boot.INSTANCE.getScreenHeight() / 2, this);
        this.ball = new Ball(this);
        this.wallTop = new Wall(32, this);
        this.wallBottom = new Wall(Boot.INSTANCE.getScreenHeight() - 32, this);
    }

    public void update() {


        this.camera.update();
        this.player.update();
        this.playerAI.update();
        this.ball.update();

        spriteBatch.setProjectionMatrix(camera.combined);

        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) { // Close game if escape is pressed
            Gdx.app.exit();
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            this.ball.reset();
        }
    }

    @Override
    public void render(float delta) {
        update();

        // Clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Start drawing
        spriteBatch.begin();

        this.player.render(spriteBatch);
        this.playerAI.render(spriteBatch);
        this.ball.render(spriteBatch);
        this.wallTop.render(spriteBatch);
        this.wallBottom.render(spriteBatch);

        // End drawing
        spriteBatch.end();

        this.box2DDebugRenderer.render(world, camera.combined.scl(Constants.PPM));
        world.step(1 /  60f, 6, 2);
    }

    public World getWorld() {
        return world;
    }

    public Ball getBall() {
        return ball;
    }

    public Player getPlayer() {
        return player;
    }

    public PlayerAI getPlayerAI() {
        return playerAI;
    }
}
