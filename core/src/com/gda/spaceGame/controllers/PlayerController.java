package com.gda.spaceGame.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.gda.spaceGame.entities.Bullet;
import com.gda.spaceGame.entities.Player;

import static com.gda.spaceGame.SpaceMain.SCALE;
import static com.gda.spaceGame.SpaceMain.gameState;
import static com.gda.spaceGame.controllers.GameState.FINISH;

/**
 * Created by smith on 10/18/16.
 */
public class PlayerController {

    private Player player;
    private float angle = 0f;
    private boolean touch = false;

    private Sprite gamepad, joystick;
    private Vector2 gamepadPos, joystickPos;

    private float timer = 0; //in milliseconds

    public PlayerController(Player player) {
        this.player = player;

        gamepad = new Sprite(new Texture(Gdx.files.internal("gamepad.png")));
        gamepad.setOrigin(0,0);
        gamepad.setScale(1/SCALE);

        gamepadPos = new Vector2();

        joystick = new Sprite(new Texture(Gdx.files.internal("joystick.png")));
        joystick.setOrigin(0,0);
        joystick.setScale(1/SCALE);

        joystickPos = new Vector2();
    }

    public void act(Batch batch, float alpha) {

        if (gameState != FINISH) {
            shipControlling(batch, alpha);
            shootControlling();
        }
        else player.setVisible(false);

        player.moveBy(player.getSpeed() * MathUtils.cosDeg(player.getAngle()), player.getSpeed() * MathUtils.sinDeg(player.getAngle()));

    }

    private void shootControlling() {
        int i;

        if (Gdx.input.isTouched(0) && Gdx.input.getX(0) < Gdx.graphics.getWidth()/2) i = 0;
        else if (Gdx.input.isTouched(1) && Gdx.input.getX(1) < Gdx.graphics.getWidth()/2) i = 1;
        else i = -1;

        if (i >= 0) {
            canShoot();
        }

    }

    private void shipControlling(Batch batch, float alpha) {
        int i = -1;

        if (Gdx.input.isTouched(0) && Gdx.input.getX(0) > Gdx.graphics.getWidth()/2) i = 0;
        else if (Gdx.input.isTouched(1) && Gdx.input.getX(1) > Gdx.graphics.getWidth()/2) i = 1;

        if (i >= 0) {
            if (!touch) {
                gamepadPos.set(Gdx.input.getX(i), Gdx.input.getY(i));
                joystickPos.set(Gdx.input.getX(i), Gdx.input.getY(i));
                touch = true;
            }

            angle = -MathUtils.radiansToDegrees * MathUtils.atan2(Gdx.input.getY(i) - gamepadPos.y, Gdx.input.getX(i) - gamepadPos.x);
            if (angle < 0) angle += 360;

            if (Math.hypot(Gdx.input.getY(i) - gamepadPos.y, Gdx.input.getX(i) - gamepadPos.x) < gamepad.getWidth() / 2 / SCALE) {
                joystickPos.x = Gdx.input.getX(i);
                joystickPos.y = Gdx.input.getY(i);
            } else {
                joystickPos.x = gamepadPos.x + gamepad.getWidth() / 2 / SCALE * MathUtils.cosDeg(-angle);
                joystickPos.y = gamepadPos.y + gamepad.getWidth() / 2 / SCALE * MathUtils.sinDeg(-angle);
            }

            draw(batch, alpha);
            changePlayerAngle();

            if (!Gdx.input.isTouched(i)) touch = false;
        }
        else touch = false;
    }

    public void draw(Batch batch, float parentAlpha) {
        batch.begin();
        gamepad.setPosition(gamepadPos.x - gamepad.getWidth() / 2 / SCALE, Gdx.graphics.getHeight() - gamepadPos.y - gamepad.getHeight() / 2 / SCALE);
        gamepad.draw(batch, parentAlpha);

        joystick.setPosition(joystickPos.x - joystick.getWidth() / 2 / SCALE, Gdx.graphics.getHeight() - joystickPos.y - joystick.getHeight() / 2 / SCALE);
        joystick.draw(batch, parentAlpha);
        batch.end();
    }

    private void canShoot() {
        if (timer <= 0) {
            timer += 0.5f;
            shoot();
        }
        timer -= Gdx.graphics.getDeltaTime();
    }

    private void shoot() {
        switch (player.getShootType()) {
            case SINGLE:
                player.getStage().addActor(new Bullet(
                        new Texture(Gdx.files.internal("particles/bullet1.png")),
                        20f,
                        player.getX() + player.getSprite().getWidth()/SCALE*MathUtils.cosDeg(player.getAngle()),
                        player.getY() + player.getSprite().getHeight()/SCALE*MathUtils.sinDeg(player.getAngle()),
                        player.getAngle()
                ));
        }
    }

    public Player getPlayer() {
        return player;
    }

    private void changePlayerAngle() {

        if (Math.abs(player.getAngle() - angle) > player.getTurn()) {

            if(Math.abs(player.getAngle() - angle) > 180) {
                if (player.getAngle() > angle) {
                    player.setAngle(player.getAngle() + player.getTurn());
                }
                else {
                    player.setAngle(player.getAngle() - player.getTurn());
                }
            }
            else {
                if (player.getAngle() > angle) {
                    player.setAngle(player.getAngle() - player.getTurn());
                }
                else {
                    player.setAngle(player.getAngle() + player.getTurn());
                }
            }

        }

    }
}
