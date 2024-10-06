package objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.edo.pong.GameScreen;

public class Player extends PlayerPaddle{
    public Player(float x, float y, GameScreen gameScreen) {
        super(x, y, gameScreen);
    }

    public void update() {
        super.update();

        if(Gdx.input.isKeyPressed(Input.Keys.W)) {
            velocityY = 1;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)) {
            velocityY = -1;
        }

        body.setLinearVelocity(0, velocityY * speed);
    }
}
