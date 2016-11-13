package com.gda.spaceGame.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.gda.spaceGame.entities.Bullet;
import com.gda.spaceGame.entities.Player;

import static com.gda.spaceGame.SpaceMain.SCALE;

/**
 * Created by smith on 10/18/16.
 */
public class PlayerController implements InputProcessor {

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

        for (int i = 0; i < 2; i++) {

            if (Gdx.input.isTouched(i)) {

                if (Gdx.input.getX(i) > Gdx.graphics.getWidth() / 2) {

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

                    batch.begin();
                    gamepad.setPosition(gamepadPos.x - gamepad.getWidth() / 2 / SCALE, Gdx.graphics.getHeight() - gamepadPos.y - gamepad.getHeight() / 2 / SCALE);
                    gamepad.draw(batch, alpha);

                    joystick.setPosition(joystickPos.x - joystick.getWidth() / 2 / SCALE, Gdx.graphics.getHeight() - joystickPos.y - joystick.getHeight() / 2 / SCALE);
                    joystick.draw(batch, alpha);
                    batch.end();

                    changePlayerAngle();
                }

                if (Gdx.input.getX(i) < Gdx.graphics.getWidth() / 2) {
                    canShoot();
                }

            }

            if (!Gdx.input.isTouched(i) && Gdx.input.getX(i) > Gdx.graphics.getWidth()/2) touch = false;

        }

        player.moveBy(player.getSpeed() * MathUtils.cosDeg(player.getAngle()), player.getSpeed() * MathUtils.sinDeg(player.getAngle()));

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
                        10f,
                        player.getX() + player.getSprite().getWidth()/SCALE*MathUtils.cosDeg(player.getAngle()),
                        player.getY() + player.getSprite().getHeight()/SCALE*MathUtils.sinDeg(player.getAngle()),
                        player.getAngle()
                ));
        }
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

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
