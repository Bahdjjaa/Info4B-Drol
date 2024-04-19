package ui;

import static utils.Constantes.UI.Buttons.*;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import utils.LoadSave;

public class ModeJeuButtons extends PauseButton{

	private BufferedImage[] imgs;
	private int rowIndex,index ;
	private boolean mouseOver, mousePressed;
	private boolean selected;
	
	public ModeJeuButtons(int x, int y, int w, int h, int rowIndex) {
		super(x, y, w, h);
		this.rowIndex = rowIndex;
		loadImgs();
	}
	
	private void loadImgs() {
		BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.MODE_JEU_BUTTONS);
		this.imgs = new BufferedImage[3];
		
		for(int i = 0 ; i < this.imgs.length; i++) {
			this.imgs[i] = temp.getSubimage(i * B_WIDTH_DEFAULT, rowIndex * B_HEIGHT_DEFAULT, B_WIDTH_DEFAULT, B_HEIGHT_DEFAULT);
		}
	}

	public void update() {
		index = 0;
		if(mouseOver)
			index = 1;
		if(mousePressed) {
			index = 2; 
		}
			
	}
	
	public void draw(Graphics g) {
		g.drawImage(this.imgs[index], x, y, B_WIDTH, B_HEIGHT, null);
	}
	
	public void resetBools() {
		if(!isSelected()) {
			mouseOver = false;
			mousePressed = false;
		}
		
	}

	public boolean isMouseOver() {
		return mouseOver;
	}
	
	public boolean isMousePressed() {
		return mousePressed;
	}
	
	public boolean isSelected() {
		return selected;
	}

	public void setMouseOver(boolean mouseOver) {
		this.mouseOver = mouseOver;
	}

	

	public void setMousePressed(boolean mousePressed) {
		this.mousePressed = mousePressed;
	}
	
	public void setSelected(boolean selected) {
		this.selected = selected;
	}


}
