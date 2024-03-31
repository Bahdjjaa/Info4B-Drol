package Niveaux;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.Crabby;
import main.Game;
import utils.LoadSave;
import static utils.HelpMethods.GetLevelData;
import static utils.HelpMethods.GetCrabs;
import static utils.HelpMethods.GetPlayerSpawn;

public class Level {
	
	private BufferedImage img;
	private int[][] lvlData;
	private ArrayList<Crabby> crabs;
    private int lvlTilesWide;
    private int maxTilesOffset;
    private int maxLvlOffsetX;
    private Point jrSpawn;
    
	public Level(BufferedImage img) {
		this.img = img;
		createLevelData();
		creayeEnemies();
		calcLvlOffset();
		calcPlayerSpawn();
	}
	
	private void calcPlayerSpawn() {
		jrSpawn = GetPlayerSpawn(img);
		
	}

	private void calcLvlOffset() {
		lvlTilesWide = img.getWidth();
		maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;
		maxLvlOffsetX = Game.TILES_SIZE * maxTilesOffset;
	}

	private void creayeEnemies() {
		crabs = GetCrabs(img);
		
	}

	private void createLevelData() {
		lvlData = GetLevelData(img);
		
	}

	public int getSpriteIndex(int x, int y) {
		return this.lvlData[y][x];
	}
	
	public int[][] getLevelData(){
		return this.lvlData;
	}
	
	public int getLvlOffset() {
		return maxLvlOffsetX;
	}
	
	public ArrayList<Crabby> getCrabs(){
		return crabs;
	}
	
	public Point getPlayerSpawn() {
		return jrSpawn;
	}
}
