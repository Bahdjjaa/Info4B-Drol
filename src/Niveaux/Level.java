package Niveaux;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.Frog;
import entities.Crabby;
import entities.Roi;
import main.Game;
import objets.Porte;

import static utils.HelpMethods.GetLevelData;
//import static utils.HelpMethods.GetCrabs;
import static utils.HelpMethods.GetRois;
import static utils.HelpMethods.GetFrogs;
import static utils.HelpMethods.GetPortes;


public class Level {
	
	private int numLevel;
	private BufferedImage img;
	private int[][] lvlData;
	private ArrayList<Roi> rois;
	private ArrayList<Frog> frogs;
	private ArrayList<Porte> portes;
    private int lvlTilesWide;
    private int maxTilesOffset;
    private int maxLvlOffsetX;
    private int maxEnnemies;

    
    
	public Level(BufferedImage img, int nLvl) {
		this.img = img;
		this.numLevel = nLvl;
		createLevelData();
		createEquipage();
		createObjets();
		calcLvlOffset();
		calcNbEnnemies();
		
		
	}

	private void calcLvlOffset() {
		lvlTilesWide = img.getWidth();
		maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;
		maxLvlOffsetX = Game.TILES_SIZE * maxTilesOffset;
	}
	
	private void calcNbEnnemies() {
		this.maxEnnemies = 10 * this.numLevel;
	}

	private void createLevelData() {
		lvlData = GetLevelData(img);
		
	}

	private void createEquipage() {
		rois = GetRois(img);
		frogs = GetFrogs(img);
		
	}
	
	private void createObjets() {
		portes = GetPortes(img);
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
	
	
	public ArrayList<Roi> getRois(){
		return rois;
	}
	
	public ArrayList<Frog> getFrogs(){
		return frogs;
	}
	public ArrayList<Porte> getPortes(){
		return portes;
	}
	public int getMaxEnnemies() {
		return maxEnnemies;
	}
}
