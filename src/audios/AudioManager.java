package audios;

import java.io.IOException;
import java.net.URL;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioManager {

	public static int MENU_1 = 0;
	public static int LEVEL_1 = 1;
	public static int LEVEL_2 = 2;

	public static int DIE = 0;
	public static int JUMP = 1;
	public static int GAMEOVER = 2;
	public static int LVL_COMPLETED = 3;
	public static int ATTACK_ONE = 4;
	public static int ATTACK_TWO = 5;
	public static int ATTACK_THREE = 6;

	private Clip[] sons, effets;
	private int sonCourrant;
	private float volume = 1f;
	private boolean sonMute, effetMute;
	private Random rand = new Random();
	
	public AudioManager() {
		initSons();
		initEffets();
		playSon(MENU_1);
	}
	
	private void initSons() {
		String[] noms = {"menu", "level1", "level2"};
		sons = new Clip[noms.length];
		for(int i = 0 ; i < sons.length; i++) {
			sons[i] = getClip(noms[i]);
		}
	}
	
	private void initEffets() {
		String[] noms = {"dead", "jump", "gameover", "lvlcompleted", "attack1", "attack2", "attack3"};
		effets = new Clip[noms.length];
		for(int i = 0 ; i < effets.length; i++) 
			effets[i] = getClip(noms[i]);
		
		updateVolumeEffets();

	}
	
	private Clip getClip(String nom) {
		URL url = getClass().getResource("../audioRes/"+nom+".wav");
		AudioInputStream audio;
		try {
			audio = AudioSystem.getAudioInputStream(url);
			Clip c = AudioSystem.getClip();
			c.open(audio);
			return c;
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return null;
	}
	
	public void setVolume(float vol) {
		this.volume = vol;
		updateVolumeSons();
		updateVolumeEffets();
	}
	
	public void stopSon() {
		if(sons[sonCourrant].isActive()) 
			sons[sonCourrant].stop();
	}
	
	public void setSonNiveau(int nivIndex) {
		if(nivIndex%2 == 0) 
			playSon(LEVEL_1);
		else
			playSon(LEVEL_2);
		
	}
	public void setNiveauComplete() {
		stopSon();
		playEffet(LVL_COMPLETED);
	}
	
	public void getSonAttack() {
		int dep  = 4;
		dep+= rand.nextInt(3);
		playEffet(dep);
	}
	
	public void playEffet(int effet) {
		effets[effet].setMicrosecondPosition(0);
		effets[effet].start();
	}
	
	public void playSon(int son) {
		stopSon();
		
		sonCourrant = son;
		updateVolumeSons();
		
		sons[sonCourrant].setMicrosecondPosition(0);
		sons[sonCourrant].loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void sonsMute() {
		this.sonMute = !sonMute;
		for(Clip c :sons) {
			BooleanControl booleanCtrl = (BooleanControl)c.getControl(BooleanControl.Type.MUTE);
			booleanCtrl.setValue(sonMute);
		}
	}
	
	public void effetsMute() {
		this.effetMute = !effetMute;
		for(Clip c :effets) {
			BooleanControl booleanCtrl = (BooleanControl)c.getControl(BooleanControl.Type.MUTE);
			booleanCtrl.setValue(effetMute);
		}
		if(!effetMute)
			playEffet(JUMP);
	}
	
	private void updateVolumeSons() {
		FloatControl gainCtrl = (FloatControl)sons[sonCourrant].getControl(FloatControl.Type.MASTER_GAIN);
		float range = gainCtrl.getMaximum() - gainCtrl.getMinimum();
		float gain = (range * volume) + gainCtrl.getMinimum();
		gainCtrl.setValue(gain);
	}
	
	private void updateVolumeEffets() {
		for(Clip c : effets) {
			FloatControl gainCtrl = (FloatControl)c.getControl(FloatControl.Type.MASTER_GAIN);
			float range = gainCtrl.getMaximum() - gainCtrl.getMinimum();
			float gain = (range * volume) + gainCtrl.getMinimum();
			gainCtrl.setValue(gain);
		}
		
	}
	
	
	
	
	
	
	
	
	
	
}
