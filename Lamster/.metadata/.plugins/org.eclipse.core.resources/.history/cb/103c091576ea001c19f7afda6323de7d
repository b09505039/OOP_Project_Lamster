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
	String path = "res/setting/config.xml";

	public void saveConfiguration(String key, int value) {
		
		try {
			File file = new File(path);
			boolean exists = file.exists();
			if(!exists) {
				file.createNewFile();
			}
			OutputStream write = new FileOutputStream(path);//可能有問題
			properties.setProperty(key, Integer.toString(value));
			properties.storeToXML(write, "Resolution");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void loadConfiguration(String path) {///"setting/config.xml"
		try {
			
			InputStream read = Configuration.class.getResourceAsStream(path);
			//InputStream read = new FileInputStream(path);
			properties.loadFromXML(read);
			String width = properties.getProperty("width");
			String height = properties.getProperty("height");
			System.out.println("Width = " + width + ", Height = " + height);
			setResolution(Integer.parseInt(width), Integer.parseInt(height));
			read.close();
		} catch(FileNotFoundException e) {
			saveConfiguration("width", 800);
			saveConfiguration("height", 600);
			loadConfiguration(path);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setResolution(int width, int height) {
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
	
	public String getWidth() {
		try {
			InputStream read = new FileInputStream(path);//
			properties.loadFromXML(read);
			actwidth = properties.getProperty("width");		
			read.close();
		}catch (FileNotFoundException e) {
			actwidth = "640";
		}catch (IOException e1) {
			e1.printStackTrace();
		}
		return actwidth;
	}
	public String getHeight() {
		try {
			InputStream read1 = new FileInputStream(path);//
			properties.loadFromXML(read1);
			actheight = properties.getProperty("height");		
			read1.close();
		}catch (FileNotFoundException e) {
			actheight = "480";
		}catch (IOException e1) {
			e1.printStackTrace();
		}
		return actheight;
	}

}
