/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import main.Game;

/**
 *
 * @author bahdjjaa
 */
public class Constantes {
	
	public static final float GRAVITE = 0.01f * Game.SCALE;
	public static final int VITESSE_ANIMATION = 25;
	
	public static class ObjetsConstantes{
		public static final int TRAPE = 0;
		public static final int PORTE = 1;
		
		public static final int TRAPE_WIDTH_DEFAULT = 78;
		public static final int TRAPE_HEIGHT_DEFAULT = 58;
		public static final int PORTE_WIDTH_DEFAULT = 64;
		public static final int PORTE_HEIGHT_DEFAULT = 64;
		
		
		
		public static final int TRAPE_WIDTH = (int)(TRAPE_WIDTH_DEFAULT * Game.SCALE);
		public static final int TRAPE_HEIGHT = (int)(TRAPE_HEIGHT_DEFAULT * Game.SCALE);
		public static final int PORTE_WIDTH =(int)(PORTE_WIDTH_DEFAULT * Game.SCALE);
		public static final int PORTE_HEIGHT = (int)(PORTE_HEIGHT_DEFAULT * Game.SCALE);
		
		
		public static int GetSpriteAmount(int type) {
			switch(type) {
			case TRAPE:
			case PORTE:
				return 1;
			}
			return 0;
		}
	}
	
	public static class EquipageConstantes{
		public static final int ROI = 0;
		public static final int FROG = 1;
		
		public static final int ROI_WIDTH_DEFAULT = 78;
		public static final int ROI_HEIGHT_DEFAULT = 58;
		public static final int FROG_WIDTH_DEFAULT = 32;
		public static final int FROG_HEIGHT_DEFAULT = 32;
		
		public static final int ROI_WIDTH = (int)(ROI_WIDTH_DEFAULT * Game.SCALE);
		public static final int ROI_HEIGHT = (int)(ROI_HEIGHT_DEFAULT * Game.SCALE);
		public static final int FROG_WIDTH = (int)(FROG_WIDTH_DEFAULT * Game.SCALE);
		public static final int FROG_HEIGHT = (int)(FROG_HEIGHT_DEFAULT * Game.SCALE);
		
		
		public static int GetSpriteAmount(int type) {
			switch(type) {
			case ROI:
				return 8;
			case FROG:
				return 12;
			}
			
			return 0;
		}
		
	}
	
	public static class EnemyConstantes{
		public static final int CRABBY = 0;
		public static final int IDLE = 0;
		public static final int RUNNING = 1;
		public static final int ATTACK= 2;
		public static final int HIT = 3;
		public static final int DEAD = 4;
		
		public static final int CRABBY_WIDTH_DEFAULT = 72;
		public static final int CRABBY_HEIGHT_DEFAULT = 32;
		
		public static final int CRABBY_WIDTH = (int)(CRABBY_WIDTH_DEFAULT * Game.SCALE);
		public static final int CRABBY_HEIGHT = (int)(CRABBY_HEIGHT_DEFAULT * Game.SCALE);
		
		public static final int CRABBY_DRAWOFFSET_X = (int)(26 * Game.SCALE);
		public static final int CRABBY_DRAWOFFSET_Y = (int)(9 * Game.SCALE);
		
		public static int GetSpriteAmount(int enemy_type, int enemy_state) {
			switch(enemy_type) {
			case CRABBY:
				switch(enemy_state) {
				case IDLE:
					return 9;
				case RUNNING:
					return 6;
				case ATTACK:
					return 7;
				case HIT:
					return 4;
				case DEAD:
					return 5;
				}
			}
			return 0;
		}
		
		
		public static int GetMaxHealth(int enemy_type) {
			switch (enemy_type) {
			case CRABBY:
				return 10;
			default:
				return 1;
			}
		}
		
		public static int GetEnemyDamage(int enemy_type) {
			switch (enemy_type) {
			case CRABBY:
				return 15;
			default:
				return 0;
			}
		}
		
	}
	
	public static class Environment{
		public static final int BIG_CLOUD_WIDTH_DEFAULT = 448;
		public static final int BIG_CLOUD_HEIGHT_DEFAULT = 101;
		public static final int SMALL_CLOUD_WIDTH_DEFAULT = 74;
		public static final int SMALL_CLOUD_HEIGHT_DEFAULT = 24;
		
		public static final int BIG_CLOUD_WIDTH = (int)(BIG_CLOUD_WIDTH_DEFAULT * Game.SCALE);
		public static final int BIG_CLOUD_HEIGHT = (int)(BIG_CLOUD_HEIGHT_DEFAULT * Game.SCALE);
		public static final int SMALL_CLOUD_WIDTH = (int)(SMALL_CLOUD_WIDTH_DEFAULT * Game.SCALE);
		public static final int SMALL_CLOUD_HEIGHT = (int)(SMALL_CLOUD_HEIGHT_DEFAULT * Game.SCALE);
	}
	
	public static class UI{
		public static class Buttons{
			public static final int B_WIDTH_DEFAULT = 140;
			public static final int B_HEIGHT_DEFAULT = 56; 
			public static final int B_WIDTH = (int)(B_WIDTH_DEFAULT * Game.SCALE);
			public static final int B_HEIGHT = (int)(B_HEIGHT_DEFAULT * Game.SCALE);
		}
		
		public static class PauseButtons{
			public static final int SOUND_SIZE_DEFAULT = 42;
			public static final int SOUND_SIZE = (int)(SOUND_SIZE_DEFAULT * Game.SCALE);
		}
		
		public static class URMButtons{
			public static final int URM_DEFAULT_SIZE = 56;
			public static final int URM_SIZE = (int)(URM_DEFAULT_SIZE* Game.SCALE);
		}
		
		public static class VolumeButtons{
			public static final int VOLUME_DEFAULT_WIDTH = 28;
			public static final int VOLUME_DEFAULT_HEIGHT = 44;
			public static final int SLIDER_DEFAULT_WIDTH = 215;
			
			
			public static final int VOLUME_WIDTH = (int)( VOLUME_DEFAULT_WIDTH * Game.SCALE);
			public static final int VOLUME_HEIGHT = (int)( VOLUME_DEFAULT_HEIGHT * Game.SCALE);
			public static final int SLIDER_WIDTH = (int)( SLIDER_DEFAULT_WIDTH * Game.SCALE);
		}
		
	}
	
	
	
    
    public static class Directions{
        public static final int LEFT = 0;
        public static final int RIGHT = 1;
        public static final int UP = 2;
        public static final int DOWN = 3;
    }
    
    public static class PlayerConstants{
        
        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int JUMP = 2; 
        public static final int FALLING = 3;
        public static final int ATTACK= 4;
        public static final int HIT = 5;
        public static final int DEAD = 6; 
        
        public static int GetSpriteAmount(int player_action){
            switch(player_action){
            	case DEAD:
            		return 8;
                case RUNNING:
                    return 6;
                case IDLE:
                    return 5;
                case HIT:
                    return 4;
                case JUMP:
                case ATTACK:
                	return 3;
                case FALLING:
                default:
                    return 1;
                          
            }
        }
    }
    
}
