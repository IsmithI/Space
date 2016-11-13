package com.gda.spaceGame.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.gda.spaceGame.entities.Player;

/**
 * Created by Smith on 27.10.2016.
 */
public class BackgroundController extends Actor {

    private Texture texture, texture2;
    private Vector2 position;
    private Player player;

    public BackgroundController(Player player) {
        texture = new Texture(Gdx.files.internal("background.gif"));
        this.player = player;
        position = new Vector2(player.getX(), player.getY());

        texture2 = new Texture(Gdx.files.internal("background.gif"));
        texture2.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        toBack();

//        if (Math.abs(player.getY() - position.y) > Gdx.graphics.getHeight()/2) {
//            if (player.getY() > position.y) position.y += Gdx.graphics.getHeight()/2;
//            else position.y -= texture.getHeight()/2;
//        }
//        if (Math.abs(player.getX() - position.x) > Gdx.graphics.getWidth()/2) {
//            if (player.getX() > position.x) position.x += Gdx.graphics.getWidth()/2;
//            else position.x -= texture.getWidth()/2;
//        }
//
//        for (int i = 0; i < 3; i++) {
//            for (int j = 0; j < 3; j++) {
//                batch.draw(texture, position.x - Gdx.graphics.getWidth() + Gdx.graphics.getWidth()*j,
//                        position.y + Gdx.graphics.getHeight() -  Gdx.graphics.getHeight()*i,
//                        Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//            }
//        }
    }
}
