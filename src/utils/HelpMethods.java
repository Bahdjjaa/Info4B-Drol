package utils;

import static utils.Constantes.EnemyConstantes.CRABBY;
import static utils.Constantes.EquipageConstantes.ROI;
import static utils.Constantes.EquipageConstantes.FROG;
import static utils.Constantes.ObjetsConstantes.PORTE;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import entities.Frog;
import entities.Crabby;
import entities.Roi;
import main.Game;
import objets.Porte;
import objets.Trape;

public class HelpMethods {
	
	public static boolean CanMoveHere(float x, float y, float width,float height, int[][] lvlData) {
		if(!isSolid(x, y, lvlData))
			if(!isSolid(x+width, y+height, lvlData))
				if(!isSolid(x+width, y, lvlData))
					if(!isSolid(x, y+height, lvlData))
						return true;
		return false;
	}
	
	private static boolean isSolid(float x, float y, int[][] lvlData) {
		int maxWidth = lvlData[0].length * Game.TILES_SIZE;
		if(x < 0 || x >= maxWidth) {
			return true;
		}
		if(y < 0 || y >=Game.GAME_HEIGHT) {
			return true;
		}
		float xIndex = x / Game.TILES_SIZE;
		float yIndex = y / Game.TILES_SIZE;
		
		return IsTileSolid((int)xIndex, (int)yIndex, lvlData);

	}
	
	public static boolean IsTileSolid(int xTile, int yTile, int[][] lvlData ) {
		int val = lvlData[yTile][xTile];
		if(val >= 48 || val < 0 || val != 11) {
			return true;
		}
		
		return false;
	}
	
	
	public static float GetEntityPosNextToWall(Rectangle2D.Float hitbox, float xSpeed) {
		int currentTile = (int)hitbox.x / Game.TILES_SIZE;
		if(xSpeed > 0) {
			//Right
			int tileXPos = currentTile * Game.TILES_SIZE;
			int xOffset = (int)(Game.TILES_SIZE - hitbox.width);
			return tileXPos + xOffset -1;
		}else {
			//Left
			return currentTile * Game.TILES_SIZE;
		}
	}
	
	public static float GetEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitbox, float airSpeed) {
		int currentTile = (int)hitbox.y / Game.TILES_SIZE;
		if(airSpeed > 0) {
			//Falling - touching floor
			int tileYPos = currentTile * Game.TILES_SIZE;
			int yOffset = (int)(Game.TILES_SIZE - hitbox.height);
			return tileYPos + yOffset -1;
		}else {
			//Jumping
			return currentTile * Game.TILES_SIZE;
		}
		
	}
	
	public static boolean IsEntityOnFloor(Rectangle2D.Float hitbox, int[][]lvlData) {
		//Check the pixel below bottomleft and bottomright
		if(!isSolid(hitbox.x, hitbox.y + hitbox.height + 1, lvlData))
			if(!isSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvlData))
				return false;
		return true;
	}
	
	public static boolean IsFloor(Rectangle2D.Float hitbox, float xSpeed, int[][] lvlData) {
		if(xSpeed > 0)
			return isSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvlData);
		return isSolid(hitbox.x + xSpeed, hitbox.y + hitbox.height + 1, lvlData);
	}
	
	public static boolean IsAllTilesWalkable(int xStart, int xEnd, int y, int[][] lvlData) {
		for(int i = 0; i < xEnd - xStart; i++) {
			if(IsTileSolid(xStart + i, y, lvlData))
				return false;
			if(!IsTileSolid(xStart+i, y+1, lvlData))
				return false;
		}
		return true;
	}
	
	public static boolean IsSightClear(int[][] lvlData, Rectangle2D.Float firstHitbox, Rectangle2D.Float secondHitbox, int yTile) {
		int firstXTile =(int) (firstHitbox.x / Game.TILES_SIZE);
		int secondXTile =(int) (secondHitbox.x / Game.TILES_SIZE);
		
		if(firstXTile > secondXTile) 
			return IsAllTilesWalkable(secondXTile, firstXTile, yTile, lvlData);
		else 
			return IsAllTilesWalkable(firstXTile, secondXTile, yTile, lvlData);
	
	}
	
	public static int[][] GetLevelData(BufferedImage img){	
		int [][] lvlData = new int[img.getHeight()][img.getWidth()];
		
		for(int i = 0 ; i < img.getHeight(); i++) {
			for(int j = 0 ; j < img.getWidth(); j++) {
				Color color = new Color(img.getRGB(j, i));
				int val = color.getRed();
				if(val >= 48) {
					val = 0;
				}
				lvlData[i][j] = val;
			}
		}
		return lvlData;
	}
	
	/*public static ArrayList<Crabby> GetCrabs(BufferedImage img){
		ArrayList<Crabby> list = new ArrayList<Crabby>(); 
		/*for(int i = 0 ; i < img.getHeight(); i++) {
			for(int j = 0 ; j < img.getWidth(); j++) {
				Color color = new Color(img.getRGB(j, i));
				int val = color.getGreen();
				if(val == CRABBY) {
					list.add(new Crabby(j * Game.TILES_SIZE, i * Game.TILES_SIZE));
				}
			}
		}
		
		return list;
	}*/
	
	public static ArrayList<Roi> GetRois(BufferedImage img){
		ArrayList<Roi> list = new ArrayList<Roi>(); 
		for(int i = 0 ; i < img.getHeight(); i++) {
			for(int j = 0 ; j < img.getWidth(); j++) {
				Color color = new Color(img.getRGB(j, i));
				int val = color.getBlue();
				if(val == ROI) {
					list.add(new Roi(j * Game.TILES_SIZE, i * Game.TILES_SIZE));
				}
			}
		}
		return list;
	}
	
	public static ArrayList<Frog> GetFrogs(BufferedImage img){
		ArrayList<Frog> list = new ArrayList<Frog>();
		for(int i = 0 ; i < img.getHeight(); i++) {
			for(int j = 0 ; j < img.getWidth(); j++) {
				Color color = new Color(img.getRGB(j, i));
				int val = color.getBlue();
				if(val == FROG) {
					list.add(new Frog(j * Game.TILES_SIZE, i * Game.TILES_SIZE));
				}
			}
		}
		return list;

	}
	
	public static ArrayList<Porte> GetPortes(BufferedImage img){
		ArrayList<Porte> list = new ArrayList<>();
		for(int i = 0 ; i < img.getHeight(); i++) {
			for(int j = 0 ; j < img.getWidth(); j++) {
				Color color = new Color(img.getRGB(j, i));
				int val = color.getGreen();
				if(val == PORTE) {
					list.add(new Porte(j * Game.TILES_SIZE, i * Game.TILES_SIZE));
				}
			}
		}
		return list;
	}
	
	
	/*private static Point GetPoint(BufferedImage img) {
		Random rnd = new Random();
		
		float x = rnd.nextFloat()*(Game.GAME_WIDTH + 1);
		float y = rnd.nextFloat()*(Game.GAME_HEIGHT + 1);
		
		Color color = new Color(img.getRGB((int)x, (int)y));
		int val = color.getRed();
		if(val == 11) {
			
		}
		Point pt = new Point();
		pt.x =(int)x;
		pt.y = (int)y;
		
		return pt;
	}*/
	
	
	/*public static ArrayList<Trape> GetTraps(BufferedImage img){
		ArrayList<Trape> list = new ArrayList<>();
		for(int i = 0 ; i < img.getHeight(); i++) {
			for(int j = 0 ; j < img.getWidth(); j++) {
				Color color = new Color(img.getRGB(j, i));
				int val = color.getBlue();
				if(val == TRAPE) {
					list.add(new Trape(j * Game.TILES_SIZE, i * Game.TILES_SIZE));
				}
			}
		}
		return list;
	}*/
	
	
	
	
}
