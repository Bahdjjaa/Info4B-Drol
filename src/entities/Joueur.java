/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import static utils.Constent.Directions.*;
import static utils.Constent.PlayerConstants.GetSpriteAmount;
import static utils.Constent.PlayerConstants.IDLE;
import static utils.Constent.PlayerConstants.RUNNING;

/**
 *
 * @author bahdjjaa
 */
public class Joueur extends Entity {
    
    private BufferedImage [][] animations;
    private int tick, index, speed = 10;
    private int playerAction = IDLE;
    private int playerDir = -1;
    private boolean moving = false;
    
    public Joueur(float x, float y) {
        super(x, y);
        this.loadAnimations();
    }
     
    public void update(){
        updateAnimationTick();
        setAnimation();
        updatePos();
    }
    
    public void render(Graphics g){
        g.drawImage(this.animations[this.playerAction][this.index],(int)x ,(int)y,256, 160, null);
    }
    
    public void setDirection(int dir){
        this.playerDir = dir;
        moving = true;
    }
    
    public void setMoving(boolean moving){
        this.moving = moving;
    }
    
    private void updateAnimationTick() {
        this.tick++;
        if(this.tick >= this.speed){
            this.tick = 0; 
            this.index++;
            if(this.index >= GetSpriteAmount(this.playerAction)){
                this.index = 0;
            }
        }
        
    }
    
    private void setAnimation() {
        if(moving){
            this.playerAction = RUNNING;
        }else{
            this.playerAction = IDLE;
        }
    }

    private void updatePos() {
        if(moving){
            switch(playerDir){
                case LEFT:
                    this.x -= 5;
                    break;
                case RIGHT:
                    this.x += 5;
                    break;
                case UP:
                    this.y -= 5;
                    break;
                case DOWN:
                    this.y += 5;
                    break;
            }
        }
    }
    
    private void loadAnimations() {
        InputStream is = getClass().getResourceAsStream("../images/player_sprites.png");
        try{
            BufferedImage img = ImageIO.read(is);
            
            this.animations = new BufferedImage[9][6];
            for(int i = 0 ; i < this.animations.length; i++){
                for(int j = 0 ; j < this.animations[i].length; j++){
                     this.animations[i][j] = img.getSubimage(j*64, i*40, 64, 40);
                }
            }
           
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            try{
                is.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        } 
    }
    
}
