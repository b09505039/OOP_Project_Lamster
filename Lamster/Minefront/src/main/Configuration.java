package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class Configuration {
	
	public Properties properties = new Properties();
	String actwidth;
	String actheight;
	String path = "res/setting/config.xml";		//config.xml is in res/setting folder

	public void saveConfiguration(String key, int value) {
		
		try {
			File file = new File(path);
			boolean exists = file.exists();		//initialization
			if(!exists) {
				file.createNewFile();			//when there is no config.xml in res/setting folder, then create one
			}
			OutputStream write = new FileOutputStream(path);		//set path as stream
			properties.setProperty(key, Integer.toString(value));	//set value to string
			properties.storeToXML(write, "Resolution");				//store Resolution in xml file
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void loadConfiguration(String path) {			//load information from "setting/config.xml"
		try {
			
			InputStream read = Configuration.class.getResourceAsStream(path);	//set path as stream
			//InputStream read = new FileInputStream(path);
			properties.loadFromXML(read);										//read properties from config.xml
			String width = properties.getProperty("width");						//window's width will be properties.getProperty("width")
			String height = properties.getProperty("height");					//window's height will be properties.getProperty("height")
			//System.out.println("Width = " + width + ", Height = " + height);
			setResolution(Integer.parseInt(width), Integer.parseInt(height));	//call setResolution method below
			read.close();
		} catch(FileNotFoundException e) {
			saveConfiguration("width", 800);									//if user doesn't set any resolution, resolution will be width = 800
			saveConfiguration("height", 600);									//if user doesn't set any resolution, resolution will be height = 600
			loadConfiguration(path);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setResolution(int width, int height) {	//setResolution can change the width and height in Display.java
//		if(width == 640 && height == 480) {
//			Display.selection = 0;
//		}
//		if(width == 800 && height == 600) {
//			Display.selection = 1;
//		}
//		if(width == 1024 && height == 768) {
//			Display.selection = 2;
//		}
		Display.width = width;
		Display.height = height;
	}
	
	public String getWidth() {				//get width from config.xml
		try {
			InputStream read = new FileInputStream(path);	//set path as stream
			properties.loadFromXML(read);
			actwidth = properties.getProperty("width");		//find the width in config.xml
			read.close();
		}catch (FileNotFoundException e) {
			actwidth = "640";								//if there is nothing, set width as 640
		}catch (IOException e1) {
			e1.printStackTrace();
		}
		return actwidth;
	}
	public String getHeight() {				//get height from config.xml
		try {
			InputStream read1 = new FileInputStream(path);	//set path as stream
			properties.loadFromXML(read1);
			actheight = properties.getProperty("height");	//find the height in config.xml		
			read1.close();
		}catch (FileNotFoundException e) {
			actheight = "480";								//if there is nothing, set height as 480
		}catch (IOException e1) {
			e1.printStackTrace();
		}
		return actheight;
	}

}
