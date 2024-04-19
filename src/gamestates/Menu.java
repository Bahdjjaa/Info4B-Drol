package gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import main.Game;
import ui.MenuButton;
import utils.LoadSave;

public class Menu extends state implements Statemethods {

	private MenuButton[] buttons = new MenuButton[3];
	private BufferedImage background, backgroundImg;
	private int menuX, menuY, menuWidth, menuHeight;
	
	public Menu(Game game) {
		super(game);
		loadButtons();
		loadBackgound();
	}

	private void loadBackgound() {
		this.background = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND);
		this.backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND_IMAGE);
		this.menuWidth = (int)(background.getWidth() * Game.SCALE);
		this.menuHeight = (int)(background.getHeight() * Game.SCALE);
		this.menuX = Game.GAME_WIDTH / 2- this.menuWidth / 2;
		this.menuY = (int) (45 * Game.SCALE);
		
		
	}

	private void loadButtons() {
		buttons[0] = new MenuButton(Game.GAME_WIDTH /2, (int) (150 * Game.SCALE), 0, Gamestate.PLAYING);
		buttons[1] = new MenuButton(Game.GAME_WIDTH /2, (int) (220 * Game.SCALE), 1, Gamestate.OPTIONS);
		buttons[2] = new MenuButton(Game.GAME_WIDTH /2, (int) (290 * Game.SCALE), 2, Gamestate.QUIT);
		
	}

	@Override
	public void update() {
		for(MenuButton mb : buttons)
			mb.update();
		
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(backgroundImg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
		g.drawImage(background, menuX, menuY, menuWidth, menuHeight, null);
		for(MenuButton mb : buttons)
			mb.draw(g);
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		for(MenuButton mb : buttons) {
			if(isIn(e, mb)) {
				mb.setMousePressed(true);
			}
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		for(MenuButton mb : buttons) {
			if(isIn(e, mb)) {
				if(mb.isMousePressed())
					mb.applyGamestate();
				if(mb.getState() == Gamestate.PLAYING)
					game.getAudioManager().setSonNiveau(game.getPlaying().getLevelManager().getLevelIndex());
				break;
			}
		}
		resetButtons();
		
	}

	private void resetButtons() {
		for(MenuButton mb : buttons) {
			mb.resetBools();
		}
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		for(MenuButton mb : buttons) {
			mb.setMouseOver(false);
		}
		for(MenuButton mb : buttons) {
			if(isIn(e,mb)) {
				mb.setMouseOver(true);
				break;
			}
		}
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER)
			Gamestate.state = Gamestate.PLAYING;
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
