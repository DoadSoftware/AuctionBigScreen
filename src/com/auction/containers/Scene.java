package com.auction.containers;

import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

public class Scene {
	
	private String scene_path;
	private String broadcaster;
	private String which_layer;
	
	public Scene() {
		super();
	}

	public Scene(String scene_path, String which_layer) {
		super();
		this.scene_path = scene_path;
		this.which_layer = which_layer;
	}
	
	public String getScene_path() {
		return scene_path;
	}

	public void setScene_path(String scene_path) {
		this.scene_path = scene_path;
	}
	
	public String getBroadcaster() {
		return broadcaster;
	}

	public void setBroadcaster(String broadcaster) {
		this.broadcaster = broadcaster;
	}

	public String getWhich_layer() {
		return which_layer;
	}

	public void setWhich_layer(String which_layer) {
		this.which_layer = which_layer;
	}

	public void scene_load(PrintWriter print_writer, String broadcaster) throws InterruptedException
	{
		switch (broadcaster.toUpperCase()) {
		case "HANDBALL": case "ISPL": case "UTT":
			switch(this.which_layer.toUpperCase()) {
			case "1":
				print_writer.println("LAYER1*EVEREST*SINGLE_SCENE LOAD " + this.scene_path + ";");
				
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				TimeUnit.MILLISECONDS.sleep(500);
				break;
			case "2":
				print_writer.println("LAYER2*EVEREST*SINGLE_SCENE LOAD " + this.scene_path + ";");
				
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In STOP;");
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				TimeUnit.MILLISECONDS.sleep(500);
				break;
			case "3":
				print_writer.println("LAYER3*EVEREST*SINGLE_SCENE LOAD " + this.scene_path + ";");
				
//				print_writer.println("LAYER3*EVEREST*STAGE*DIRECTOR*In STOP;");
//				print_writer.println("LAYER3*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
				TimeUnit.MILLISECONDS.sleep(500);
				break;	
			}
			
			//print_writer.println("LAYER1*EVEREST*SINGLE_SCENE CLEAR;");
			break;
		case "DOAD_IN_HOUSE_VIZ":
			switch(which_layer.toUpperCase()) {
			case "FRONT_LAYER":
				print_writer.println("-1 RENDERER*FRONT_LAYER SET_OBJECT SCENE*" + scene_path + "\0");
				print_writer.println("-1 RENDERER*FRONT_LAYER INITIALIZE \0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*SCENE_DATA INITIALIZE \0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*UPDATE SET 1");
				break;
			case "MIDDLE_LAYER":
				print_writer.println("-1 RENDERER SET_OBJECT SCENE*" + scene_path + "\0");
				print_writer.println("-1 RENDERER INITIALIZE \0");
				print_writer.println("-1 RENDERER*SCENE_DATA INITIALIZE \0");
				print_writer.println("-1 RENDERER*UPDATE SET 1");
				break;
			}
			
			break;
		}
	}
}
