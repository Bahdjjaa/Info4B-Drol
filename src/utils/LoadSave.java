package utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

import javax.imageio.ImageIO;


public class LoadSave {
	
	public static final String PLAYER_ATLAS = "../images/player_sprites.png";
	public static final String LEVEL_ATLAS = "../images/outside_sprites.png";
	public static final String MENU_BUTTONS = "../images/button_atlas.png";
	public static final String MENU_BACKGROUND = "../images/menu_background.png";
	public static final String PAUSE_BACKGROUND = "../images/pause.png";
	public static final String SOUND_BUTTONS = "../images/sound_button.png";
	public static final String URM_BUTTONS = "../images/urm_buttons.png";
	public static final String VOLUME_BUTTONS = "../images/volume_buttons.png";
	public static final String MENU_BACKGROUND_IMAGE = "../images/background_menu.png";
	public static final String PLAYING_BACKGROUND_IMAGE = "../images/playing_bg_img.png";
	public static final String BIG_CLOUDS = "../images/big_clouds.png";
	public static final String SMALL_CLOUDS = "../images/small_clouds.png";
	public static final String CRABBY_SPRITE = "../images/crabby_sprite.png";
	public static final String STATUS_BAR = "../images/health_power_bar.png";
	public static final String COMPLETED_IMG = "../images/completed_sprite.png";
	public static final String TRAPE = "../images/trape.png";
	public static final String GAMEOVER_SCREEN = "../images/death_screen.png";
	public static final String MENU_OPTIONS = "../images/options.png";
	public static final String MUSIC_BUTTON = "../images/music_button.png";
	public static final String PORTE = "../images/porte.png";
	
	
	//Membres à récupérer 
	public static final String ROI = "../images/king_sprite.png";
	public static final String FROG = "../images/frog_sprite.png";
	
	public static BufferedImage GetSpriteAtlas(String fileName) {
		BufferedImage img = null;
		InputStream is = LoadSave.class.getResourceAsStream(""+fileName);
        try{
            img = ImageIO.read(is);
           
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            try{
                is.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        } 
        return img;
	}
	
	public static BufferedImage[] GetAllLevels() {
		URL url = LoadSave.class.getResource("../images/nivs");
		File file = null;
		try {
			file = new  File(url.toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		File[] files = file.listFiles();
		File[] filesSorted = new File[files.length];
		
		for(int i = 0 ; i < filesSorted.length; i++) {
			for(int j = 0; j < files.length; j++) {
				if(files[j].getName().equals((i + 1)+ ".png"))
					filesSorted[i] = files[j];
			}
		}
		
		
		BufferedImage[] imgs = new BufferedImage[filesSorted.length];
		for(int i = 0 ; i < imgs.length; i++) {
			try {
				imgs[i] = ImageIO.read(filesSorted[i]);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return imgs;
	}
	
	
	
	

}
