package main;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class ApplicationController implements Initializable {

    @FXML
    private Canvas screen;

    private List<KeyCode> codes = new LinkedList<>();

    Game game;


    public void initialize(URL location, ResourceBundle resources) {
        game = new Game(screen.getGraphicsContext2D());
    }

    void addKeyListener(Scene scene) {
        final KeyCombination comboUp = new KeyCodeCombination(KeyCode.UP);
        final KeyCombination comboDown = new KeyCodeCombination(KeyCode.DOWN);
        final KeyCombination comboLeft = new KeyCodeCombination(KeyCode.LEFT);
        final KeyCombination comboRight = new KeyCodeCombination(KeyCode.RIGHT);
        final KeyCombination comboUpLeft = new MultipleKeyCombo(KeyCode.UP, KeyCode.LEFT);
        final KeyCombination comboUpRight = new MultipleKeyCombo(KeyCode.UP, KeyCode.RIGHT);
        final KeyCombination comboDownLeft = new MultipleKeyCombo(KeyCode.DOWN, KeyCode.LEFT);
        final KeyCombination comboDowRight = new MultipleKeyCombo(KeyCode.DOWN, KeyCode.LEFT);

        scene.setOnKeyPressed(e -> {
            codes.add(e.getCode());
            e.consume();
        });

        scene.setOnKeyReleased(e -> {
            codes.remove(e.getCode());
            e.consume();
        });

        scene.addEventFilter(KeyEvent.KEY_PRESSED, key -> {
            if (comboUpLeft.match(key)) {
                game.setVelocityX(-1);
                game.setVelocityY(-1);
            } else if (comboUpRight.match(key)) {
                game.setVelocityX(1);
                game.setVelocityY(-1);
            } else if (comboDownLeft.match(key)) {

            } else if (comboDowRight.match(key)) {

            } else if (comboUp.match(key)) {
                game.setVelocityY(-1);
            } else if (comboDown.match(key)) {

            } else if (comboLeft.match(key)) {
                game.setVelocityX(-1);
            } else if (comboRight.match(key)) {
                game.setVelocityX(1);
            } else if (KeyCode.ENTER == key.getCode() || KeyCode.SPACE == key.getCode()) {
                game.stop();
                game = new Game(screen.getGraphicsContext2D());
            }
            key.consume();
        });

        scene.addEventFilter(KeyEvent.KEY_RELEASED, key -> {
            if (comboLeft.match(key)) {
                game.setVelocityX(0);
            } else if (comboRight.match(key)) {
                game.setVelocityX(0);
            }
            //key.consume();
        });
    }

    private class MultipleKeyCombo extends KeyCombination {
        private List<KeyCode> neededCodes;

        MultipleKeyCombo(KeyCode... codes) {
            neededCodes = Arrays.asList(codes);
        }

        @Override
        public boolean match(KeyEvent event) {
            return codes.containsAll(neededCodes);
        }
    }
}
