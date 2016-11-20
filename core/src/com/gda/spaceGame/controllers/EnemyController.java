package com.gda.spaceGame.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.gda.spaceGame.entities.enemies.Enemy;

/**
 * Created by Smith on 13.11.2016.
 */
public class EnemyController {

    private Vector2 playerPosition;
    private final Stage stage;

    private int difficulty = 0;
    private float timer;

    public EnemyController(PlayerController playerController, Stage stage) {
        playerPosition = new Vector2(playerController.getPlayer().getX(), playerController.getPlayer().getY());
        this.stage = stage;

        defineEnemySpawnTime();
    }

    public void act() {
        for (Actor actor : stage.getActors()) {
            if (actor instanceof Enemy) {
                ((Enemy) actor).move(playerPosition);
            }
        }

        timer -= Gdx.graphics.getDeltaTime();
        if (timer <= 0) {
            defineEnemySpawnTime();
            spawnEnemy();
        }
    }

    private void spawnEnemy() {
        switch (difficulty) {
            case 0:
                addEnemy(enemyLevel0());
        }
    }

    public void defineEnemySpawnTime() {
        switch (difficulty) {
            case 0:
                timer = 5f;
                break;
            case 1:
                timer = 3f;
                break;
        }
    }

    public void addEnemy(Enemy enemy) {
        float randomAngle = MathUtils.random(0, 359);
        enemy.setPosition(playerPosition.x + Gdx.graphics.getWidth()*3/2 * MathUtils.cosDeg(randomAngle),
                            playerPosition.y + Gdx.graphics.getWidth()*3/2 * MathUtils.sinDeg(randomAngle));
        stage.addActor(enemy);
    }

    public Enemy enemyLevel0() {
        return new Enemy(0, new Texture(Gdx.files.internal("enemies/enemy_ship_1.png")), 11f, 1f, 5);
    }

    public void updatePlayerPosition(float x, float y) {
        playerPosition.set(x, y);
    }

    public int getDifficulty() {
        return difficulty;
    }
}
