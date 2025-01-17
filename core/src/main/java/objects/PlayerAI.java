package objects;

import com.edo.pong.GameScreen;

public class PlayerAI extends PlayerPaddle{
    public PlayerAI(float x, float y, GameScreen gameScreen) {
        super(x, y, gameScreen);
    }

    @Override
    public void update() {
        super.update();

        // AI magic
        Ball ball = gameScreen.getBall();
        if(ball.getY() + 10 > y && ball.getY() - 10 > y) {
            velocityY = 1;
        }
        if(ball.getY() + 10 < y && ball.getY() - 10 < y) {
            velocityY = -1;
        }

        body.setLinearVelocity(0, velocityY * speed);
    }
}
