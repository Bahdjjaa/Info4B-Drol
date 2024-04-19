package ui;

import static utils.Constantes.UI.Buttons.*;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import utils.LoadSave;

public class ModeJeuButtons extends PauseButton{

	private BufferedImage[] imgs;
	private int rowIndex,index ;
	private boolean mouseOver, mouseClicked;
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
		if(isSelected()) {
			index = 2; 
		}
			
	}
	
	public void draw(Graphics g) {
		g.drawImage(this.imgs[index], x, y, B_WIDTH, B_HEIGHT, null);
	}
	
	public void resetBools() {
		if(!isSelected()) {
			mouseOver = false;
			mouseClicked = false;
		}
		
	}

	public boolean isMouseOver() {
		return mouseOver;
	}
	
	public boolean isMouseClicked() {
		return mouseClicked;
	}
	
	public boolean isSelected() {
		return selected;
	}

	public void setMouseOver(boolean mouseOver) {
		this.mouseOver = mouseOver;
	}

	

	public void setMouseClicked(boolean mouseClicked) {
		this.mouseClicked = mouseClicked;
	}
	
	public void setSelected(boolean selected) {
		this.selected = selected;
	}


}
