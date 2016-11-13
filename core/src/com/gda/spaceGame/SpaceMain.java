package com.gda.spaceGame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.gda.spaceGame.controllers.GameState;
import com.gda.spaceGame.screens.MainMenuScreen;

public class SpaceMain extends Game {

	public static float SCALE;
	public static GameState gameState;

	@Override
	public void create () {
		SCALE = 1536 / Gdx.graphics.getHeight();
		setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {

	}
}
