package Utilities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Animation {

	private int slowness;
	private int frames;
	private int index = 0;
	private int count = 0;

	private BufferedImage[] img = new BufferedImage[25];
	private BufferedImage currentImg;

	//14 frame animation
	public Animation(int slowness, BufferedImage[] img){
		this.slowness = slowness;
		for(int i = 0; i < img.length; i++) {
			this.img[i] = img[i];
		}
		frames = img.length;
	}

	public void runAnimation(){
		index++;
		if(index > slowness){
			index = 0;
			nextFrame();
		}
	}

	public void nextFrame(){

		for(int i = 2; i < img.length; i++) {
			if(frames == i) {
				for(int j = 0; j < i; j++) {
					if(count == j)
						currentImg = img[j];
				}
				count++;
				if(count > frames)
					count = 0;
				break;
			}
		}
	}

	public void drawAnimation(Graphics g, double x, double y, int offset, int width, int height){
		g.drawImage(currentImg, (int)x - offset, (int)y, width, height, null);
	}

	public void setCount(int count){
		this.count = count;
	}
	public int getCount(){
		return count;
	}
	public int getSlowness(){
		return slowness;
	}
	public void setSlowness(int slowness){
		this.slowness = slowness;
	}
}