package object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Character {

    private boolean isReady = true;

    private static final Image IMG = new Image("character.png");
    private static final Image[] imgList = {IMG};
    //private static final Image[] imgList = {IMG, new Image("tile.png"), new Image("tile2.png")};
    private int index = 0;
    private static final int SCALING = 50;
    private static final double SCALING_Y = SCALING / 16 * 20;
    private static final double Y_OFFSET = SCALING_Y-SCALING;
    private static final double WEIGHT = 0.25;
    private boolean isDead = false;
    private boolean hasHitGround = false;
    private double x;
    private double y;

    public Character(int x, int y) {
        this.x = x * SCALING;   // 15
        this.y = y * SCALING;   // 12, 600
        //System.out.println("position mario: " + this.x + ", " + this.y);
    }

    public void render(GraphicsContext context, double velocityX, double velocityY) {
        context.drawImage(imgList[index], x, y - Y_OFFSET, SCALING, SCALING_Y);
        index = (index < imgList.length-1) ? (index+1) : 0;
    }

    public void move(double x, double y) {
        this.x = this.x + x;
        this.y = this.y + y;
        if (this.y >= 800 + Y_OFFSET) {
            hasHitGround = true;
        }
        //System.out.println("position mario: " + this.x + ", " + this.y);
    }

    public boolean isHasHitGround() {
        return hasHitGround;
    }

    public double getWeight() {
        return WEIGHT;
    }

    public double getFloorLevel() {
        return y+SCALING;
    }

    public double getTopLevel() {
        return (y+SCALING)-Y_OFFSET;
    }

    public double getLeftMargin() {
        return x;
    }
    public double getRightMargin() {
        return x+SCALING;
    }
}
