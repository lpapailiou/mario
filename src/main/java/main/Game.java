package main;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.util.Duration;
import object.Character;
import object.Level;

public class Game {

    private final static int START = 15;
    private final static int GROUND = 12;
    private final static int SPEED = 10;
    private final static double JUMP_FORCE = 2.5;

    private GraphicsContext context;
    private Level level = new Level(START, GROUND);
    private Character character = new Character(START, GROUND);

    private int velocityX;
    private double velocityY = 1;
    Timeline timeline;

    public Game(GraphicsContext context) {
        this.context = context;
        render();
        timeline = new Timeline(new KeyFrame(Duration.millis(50), e -> {
            if (!character.isHasHitGround()) {
                applyGravity();
            } else {
                System.out.println("timeline stopped");
                timeline.stop();
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    void setVelocityX(int x) {
        velocityX = x;
    }

    void setVelocityY(int y) {
        if (level.isOnGround(character.getFloorLevel(), character.getLeftMargin())) {
            velocityY = -JUMP_FORCE;
        }
    }

    void render() {
        context.clearRect(0,0,1600,800);
        level.render(context);
        character.render(context, velocityX, velocityY);
    }

    public void stop() {
        timeline.stop();
    }

    private void move(double x, double y) {
        level.move(x, 0);
        character.move(0, y);
        render();
    }

    void applyGravity() {
        if (!level.isTileBelowCharacter(character.getFloorLevel())) {        // fall
            move(velocityX*SPEED, velocityY*SPEED);
            velocityY += character.getWeight();
        } else {
            if (level.isCollidingLeft(character.getFloorLevel(), character.getLeftMargin()-10, velocityY*SPEED)) {
                if (velocityX < 0) {
                    velocityX = 0;
                }
            }
            if (level.isCollidingRight(character.getFloorLevel(), character.getRightMargin(), velocityY*SPEED)) {
                if (velocityX > 0) {
                    velocityX = 0;
                }
            }

            if (level.isOnGround(character.getFloorLevel(), character.getLeftMargin())) {
                if (velocityY < 0) {
                    move(velocityX*SPEED, velocityY*SPEED);
                } else {
                    move(velocityX*SPEED, 0);
                    velocityY = 1;
                }
            } else {
                double distanceY;
                double velocity;
                double deltaY;
                if (velocityY < 0) {
                    distanceY = -level.getTopDistance(character.getTopLevel(), character.getLeftMargin());
                    velocity = velocityY*SPEED;
                    deltaY = (velocity < distanceY) ? distanceY : velocity;
                    if (deltaY == distanceY) {
                        velocityY = 1 + character.getWeight();
                    }
                } else {
                    distanceY = level.getBottomDistance(character.getFloorLevel(), character.getLeftMargin());
                    velocity = velocityY * SPEED;
                    deltaY = (velocity > distanceY) ? distanceY : velocity;
                }
                move(velocityX * SPEED, deltaY);
                velocityY += character.getWeight();
            }
        }
        render();
    }


}
