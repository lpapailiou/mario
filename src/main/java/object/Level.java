package object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Level {

    static final int SCALING = 50;
    int initialPosX;
    int posX;
    int posY;
    double offsetX;


    int[][] tiles = new int[50][16];

    public Level(int startIndex, int ground) {
        this.initialPosX = startIndex;
        this.posX = startIndex;
        generate(startIndex, ground);
    }

    private void generate(int startIndex, int ground) {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                if (j > ground && i != 22) {
                    if (j == ground+1 && (i == 3 || i == 4 || i == 5)) {
                        continue;
                    }
                    tiles[i][j] = 1;
                }
            }
        }
        posY = ground+1;
        tiles[startIndex-5][ground] = 1;
        //tiles[19][11] = 1;
        tiles[20][ground] = 1;
        tiles[20][ground-1] = 1;
        tiles[20][ground-2] = 1;
        tiles[20][ground-3] = 1;
        tiles[20][ground-4] = 1;
        tiles[startIndex+2][ground-2] = 2;
    }

    public void render(GraphicsContext context) {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                int tile = tiles[i][j];
                context.drawImage(TileType.values()[tile].getImage(), i*SCALING+offsetX, j*SCALING, SCALING, SCALING);
                int left = (int) (750-offsetX)/SCALING;
                int right = (int) (799-offsetX)/SCALING;
                if ((i == left || i == right) && tile == 1) {
                    context.drawImage(new Image("tile2.png"), i*SCALING+offsetX, j*SCALING, SCALING, SCALING);
                }
            }
        }
    }

    public void move(double x, double y) {
        offsetX+= (-x);
        int newPos = initialPosX + (int) -(offsetX/SCALING);
        int diff = posX-newPos;
        if (newPos >= 0 && newPos < tiles.length) {
        //if (Math.abs(diff) == 1) {
            int curr = posY;
            if (tiles[newPos][curr] != 0) {

                for (int i = posY; i >= 0; i--) {
                    if (tiles[newPos][i] != 0) {
                        curr = i;
                    } else {
                        break;
                    }
                }
            }
            if (tiles[newPos][curr] == 0) {

                for (int i = curr; i < tiles[newPos].length; i++) {
                    if (tiles[newPos][i] != 0) {
                        curr = i;
                    }
                }
            }
            posY = curr;
        }

        posX = newPos;
    }


    public boolean isTileBelowCharacter(double floor) {
        if (posX <0
                ||posX >= tiles.length) {
            return false;
        }
        for (int i=0; i < tiles[posX].length; i++) {
            if (i*SCALING >= floor && tiles[posX][i] != 0) {
                return true;
            }
        }
        return false;
    }

    public boolean isCollidingLeft(double floor, double margin, double speedY) {
        int x = (int) (margin-offsetX)/SCALING;
        int y = (int) (floor+speedY)/SCALING-1;
        if ((areCoordinatesValid(x,y) && tiles[x][y] != 0) || (areCoordinatesValid(x,y-1) && tiles[x][y-1] != 0)) {
            if (x*SCALING+SCALING+offsetX+10 >= margin) {
                return true;
            }
        }
        return false;
    }
    public boolean isCollidingRight(double floor, double margin, double speedY) {
        int x = (int) (margin-offsetX)/SCALING;
        int y = (int) (floor+speedY)/SCALING-1;
        if ((areCoordinatesValid(x,y) && tiles[x][y] != 0) || (areCoordinatesValid(x,y-1) && tiles[x][y-1] != 0)) {
            if (x*SCALING+offsetX <= margin) {
                return true;
            }
        }
        return false;
    }

    public boolean isOnGround(double floor, double margin) {
        int x = (int) (margin-offsetX)/SCALING;
        int x2 = (int) (margin+49-offsetX)/SCALING;
        int y = (int) floor/SCALING;
        boolean valid = false;

        if ((areCoordinatesValid(x,y) && tiles[x][y] != 0) || (areCoordinatesValid(x2,y) && tiles[x2][y] != 0)) {
            if (y*SCALING == floor) {
                valid = true;
            }
        }
        return valid;
    }

    boolean areCoordinatesValid(int x, int y) {
        if (x >= 0 && x < tiles.length && y >= 0 && y < tiles[x].length) {
            return true;
        }
        return false;
    }

    public double getBottomDistance(double floor, double margin) {
        int x = (int) (margin-offsetX)/SCALING;
        int x2 = (int) (margin+49-offsetX)/SCALING;
        int y = (int) (floor+SCALING)/SCALING;
        boolean valid = false;

        if ((areCoordinatesValid(x,y) && tiles[x][y] != 0) || (areCoordinatesValid(x2,y) && tiles[x2][y] != 0)) {
            if (y*SCALING >= floor) {
                valid = true;
            }
        }
        if (valid) {
            return (y*SCALING)-floor;
        }
        return SCALING;
    }

    public double getTopDistance(double top, double margin) {
        int x = (int) (margin-offsetX)/SCALING;
        int x2 = (int) (margin+49-offsetX)/SCALING;
        int y = (int) (top-SCALING*2)/SCALING;
        boolean valid = false;
        if ((areCoordinatesValid(x,y) && tiles[x][y] != 0) || (areCoordinatesValid(x2,y) && tiles[x2][y] != 0)) {
            if ((y*SCALING)+SCALING*2 <= top) {
                valid = true;
            }
        }
        if (valid) {
            return top-((y*SCALING)+SCALING*2);
        }
        return SCALING*10;
    }



}
