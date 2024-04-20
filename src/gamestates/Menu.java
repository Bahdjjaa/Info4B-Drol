package gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JOptionPane;

import Network.ClientJoueur;
import Network.ServeurCentral;
import Network.packets.Packet00Login;
import entities.Joueur;
import entities.JoueurCooperatif;
import main.Game;
import modesjeu.Modejeu;
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
				if(mb.getState() == Gamestate.PLAYING) {
					// M O D E      S O L O
					if(Modejeu.mode == Modejeu.SOLO) {
						String name = JOptionPane.showInputDialog(game, "Entrez votre nom");
						Joueur joueur = new Joueur(200, 175, (int)(64 * Game.SCALE), (int)(40* Game.SCALE),name, game.getPlaying());
				    	joueur.loadLvlData(game.getPlaying().getLevelManager().getCurrentLevel().getLevelData());
				    	game.getPlaying().ajoutEntity(joueur);
				    	game.setRunning(true);
					}
					
					// M O D E    C O O P E R A T I F
					else if(Modejeu.mode == Modejeu.COOPERATIF) {
						if(JOptionPane.showConfirmDialog(game.getGamePanel(), "veux tu commencer le serveur ?") == 0) {
								game.setServeurSocket(new ServeurCentral(game));
								game.getServeurSocket().start();
							
						}
						game.setJoueurSocket(new ClientJoueur(game, "localhost"));
						game.getJoueurSocket().start();
						//game.setRunning(true);
				
						JoueurCooperatif joueur =  new JoueurCooperatif(200, 175, (int)(64 * Game.SCALE), (int)(40* Game.SCALE),
																		JOptionPane.showInputDialog(game, "Entre vontre nom"),game.getPlaying(),
																		null, -1);
						joueur.loadLvlData(game.getPlaying().getLevelManager().getCurrentLevel().getLevelData());
						joueur.setJoueurLocal(true);
						game.getPlaying().ajoutEntity(joueur);
						
						Packet00Login loginPacket = new Packet00Login(joueur.getUsername());
						
						if(game.getServeurSocket() != null) {
							game.getServeurSocket().ajoutConnexion(joueur, loginPacket);
						}
						loginPacket.writeData(game.getJoueurSocket());
					}
					game.getAudioManager().setSonNiveau(game.getPlaying().getLevelManager().getLevelIndex());
					
				}
				
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
