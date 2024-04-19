package ui;

import static utils.Constantes.UI.URMButtons.URM_DEFAULT_SIZE;
import static utils.Constantes.UI.URMButtons.URM_SIZE;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import utils.LoadSave;

public class NombreJoueursButtons extends PauseButton{
	private BufferedImage[] imgs;
	private int rowIndex,index ;
	private boolean mouseOver, mouseClicked;
	private boolean selected = false;
	
	public NombreJoueursButtons(int x, int y, int w, int h, int rowIndex) {
		super(x, y, w, h);
		this.rowIndex = rowIndex;
		loadImgs();
	}
	
	private void loadImgs() {
		BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.NB_JRS_BUTTONS);
		this.imgs = new BufferedImage[3];
		
		for(int i = 0 ; i < this.imgs.length; i++) {
			this.imgs[i] = temp.getSubimage(i * URM_DEFAULT_SIZE, rowIndex * URM_DEFAULT_SIZE, URM_DEFAULT_SIZE, URM_DEFAULT_SIZE);
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
		g.drawImage(this.imgs[index], x, y, URM_SIZE, URM_SIZE, null);
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
