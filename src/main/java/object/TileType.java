package object;

import javafx.scene.image.Image;

public enum TileType {

    NONE(null),
    TILE1(new Image("tile.png")),
    BOX1(new Image("quest.png"));

    final Image image;

    TileType(Image image) {
        this.image = image;
    }

    Image getImage() {
        return image;
    }

}
