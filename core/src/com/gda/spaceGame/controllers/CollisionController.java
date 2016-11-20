package com.gda.spaceGame.controllers;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.gda.spaceGame.entities.Bullet;
import com.gda.spaceGame.entities.Player;
import com.gda.spaceGame.entities.enemies.Enemy;

/**
 * Created by Smith on 14.11.2016.
 */
public class CollisionController {

    private final Stage stage;
    private final Player player;

    public CollisionController(Stage stage, Player player) {
        this.stage = stage;
        this.player = player;
    }
}
