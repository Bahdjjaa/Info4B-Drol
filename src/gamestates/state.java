package gamestates;

import java.awt.event.MouseEvent;

import audios.AudioManager;
import main.Game;
import ui.MenuButton;

public class state {
	
	protected Game game;
	
	public state(Game game) {
		this.game = game;
	}
	
	public boolean isIn(MouseEvent e, MenuButton mb) {
		return mb.getBounds().contains(e.getX(), e.getY());
	}
	public Game getGame() {
		return game;
	}
	public void setGameState(Gamestate etat) {
		switch(etat) {
		case MENU -> game.getAudioManager().playSon(AudioManager.MENU_1);
		case PLAYING -> game.getAudioManager().setSonNiveau(game.getPlaying().getLevelManager().getLevelIndex());
		}
		
		Gamestate.state = etat;
	}
}
