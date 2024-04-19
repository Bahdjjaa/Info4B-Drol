package ui;


import java.awt.Graphics;
import java.awt.image.BufferedImage;

import utils.LoadSave;
import static utils.Constantes.UI.VolumeButtons.*;

public class VolumeButton extends PauseButton {

	private BufferedImage[] imgs;
	private BufferedImage slider;
	private int index ;
	private boolean mouseOver, mousePressed;
	private int buttonX, minX, maxX;
	private float floatVal = 0;
	
	public VolumeButton(int x, int y, int w, int h) {
		super(x + w/2, y, VOLUME_WIDTH, h);
		bounds.x -= VOLUME_WIDTH/2;
		this.buttonX = x + w/2;
		this.x = x; 
		this.width = w;
		this.minX = x + VOLUME_WIDTH / 2;
		this.maxX = x + w - VOLUME_WIDTH / 2;
		loadImgs();
	}
	private void loadImgs() {
		BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.VOLUME_BUTTONS);
		this.imgs = new BufferedImage[3];
		
		for(int i = 0 ; i < this.imgs.length; i++) {
			this.imgs[i] = temp.getSubimage(i * VOLUME_DEFAULT_WIDTH, 0, VOLUME_DEFAULT_WIDTH, VOLUME_DEFAULT_HEIGHT);
		}
		
		slider = temp.getSubimage(3*VOLUME_DEFAULT_WIDTH, 0, SLIDER_DEFAULT_WIDTH, VOLUME_DEFAULT_HEIGHT);
		
	}

	public void update() {
		index = 0;
		if(mouseOver)
			index = 1;
		if(mousePressed)
			index = 2;
	}
	
	public void draw(Graphics g) {
		g.drawImage(slider, x, y, width, height, null);
		g.drawImage(imgs[index], buttonX - VOLUME_WIDTH/2, y, VOLUME_WIDTH, height, null);
		
	}
	
	public void changeX(int x) {
		if(x < minX)
			buttonX = minX;
		else if (x > maxX)
			buttonX = maxX;
		else
			buttonX = x;
		updateFloatValuer();
		bounds.x = buttonX - VOLUME_WIDTH/2;
	}
	
	private void updateFloatValuer() {
		float range = maxX - minX;
		float val = buttonX - minX;
		floatVal = val/range;
		
	}
	
	public float getFloatValue() {
		return floatVal;
	}
	public void resetBools() {
		mouseOver = false;
		mousePressed = false;
	}

	public boolean isMouseOver() {
		return mouseOver;
	}

	public void setMouseOver(boolean mouseOver) {
		this.mouseOver = mouseOver;
	}

	public boolean isMousePressed() {
		return mousePressed;
	}

	public void setMousePressed(boolean mousePressed) {
		this.mousePressed = mousePressed;
	}
	

}
