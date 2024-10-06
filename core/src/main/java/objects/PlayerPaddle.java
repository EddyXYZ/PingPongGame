package objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.edo.pong.GameScreen;
import helper.BodyHelper;
import helper.Constants;
import helper.ContactType;

public abstract class PlayerPaddle {
    protected Body body;
    protected float x, y, speed, velocityY;
    protected int width, height, score;
    protected Texture texture;
    protected GameScreen gameScreen;

    public PlayerPaddle(float x, float y, GameScreen gameScreen) {
        this.x = x;
        this.y = y;
        this.gameScreen = gameScreen;
        this.speed = 6;
        this.width = 16;
        this.height = 64;
        this.texture = new Texture("fancy-paddle-grey.png");
        this.body = BodyHelper.createBody(x, y, width, height, false, 100000, gameScreen.getWorld(), ContactType.PLAYER);
    }

    public void update() {
        x = body.getPosition().x * Constants.PPM - (width / 2);
        y = body.getPosition().y * Constants.PPM - (height / 2);
        velocityY = 0;
    }

    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(texture, x, y, width, height);
    }

    public void score() {
        this.score++;
    }
}
