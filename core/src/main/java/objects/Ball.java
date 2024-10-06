package objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.edo.pong.Boot;
import com.edo.pong.GameScreen;
import helper.BodyHelper;
import helper.Constants;
import helper.ContactType;

public class Ball {
    private Body body;
    private float x, y, speed, velocityX, velocityY;
    private int width, height;
    private GameScreen gameScreen;
    private Texture texture;

    public Ball(GameScreen gameScreen) {
        // Place the ball in the middle of the screen
        this.x = Boot.INSTANCE.getScreenWidth() / 2;
        this.y = Boot.INSTANCE.getScreenHeight() / 2;
        this.speed = 5;
        this.velocityX = getRandomDirection();
        this.velocityY = getRandomDirection();

        this.texture = new Texture("fancy-ball.png");
        this.gameScreen = gameScreen;
        this.width = 32;
        this.height = 32;
        this.body = BodyHelper.createBody(x, y, width, height, false, 0, gameScreen.getWorld(), ContactType.BALL);
    }

    private float getRandomDirection() {
        return (Math.random() < 0.5 ? 1 : -1);
    }

    public void reverseVelocityX() {
        this.velocityX *= -1;
    }

    public void reverseVelocityY() {
        this.velocityY *= -1;
    }

    public void increaseSpeed() {
        this.speed *= 1.1f;
    }

    public void update() {
        x = body.getPosition().x * Constants.PPM - (width / 2);
        y = body.getPosition().y * Constants.PPM - (height / 2);

        this.body.setLinearVelocity(velocityX * speed, velocityY * speed);

        // Score
        if(x < 0) {
            gameScreen.getPlayerAI().score();
            reset();
        }

        if(x > Boot.INSTANCE.getScreenWidth()) {
            gameScreen.getPlayer().score();
            reset();
        }
    }

    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(texture, x, y, width, height);
    }

    public void reset() {
        this.velocityX = getRandomDirection();
        this.velocityY = getRandomDirection();
        this.speed = 5;
        this.body.setTransform(Boot.INSTANCE.getScreenWidth() / 2 / Constants.PPM, Boot.INSTANCE.getScreenHeight() / 2 / Constants.PPM, 0);
    }

    public float getY() {
        return y;
    }
}
