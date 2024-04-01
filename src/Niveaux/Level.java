package Niveaux;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.Crabby;
import entities.Roi;
import main.Game;
import static utils.HelpMethods.GetLevelData;
import static utils.HelpMethods.GetCrabs;
import static utils.HelpMethods.GetRois;


public class Level {
	
	private BufferedImage img;
	private int[][] lvlData;
	private ArrayList<Crabby> crabs;
	private ArrayList<Roi> rois;
    private int lvlTilesWide;
    private int maxTilesOffset;
    private int maxLvlOffsetX;
    
    
	public Level(BufferedImage img) {
		this.img = img;
		createLevelData();
		createEnemies();
		createEquipage();
		calcLvlOffset();
		
	}

	private void calcLvlOffset() {
		lvlTilesWide = img.getWidth();
		maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;
		maxLvlOffsetX = Game.TILES_SIZE * maxTilesOffset;
	}

	private void createEnemies() {
		crabs = GetCrabs(img);
		
	}

	private void createLevelData() {
		lvlData = GetLevelData(img);
		
	}

	private void createEquipage() {
		rois = GetRois(img);
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
	
	public ArrayList<Roi> getRois(){
		return rois;
	}
}
