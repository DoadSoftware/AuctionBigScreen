package com.auction.broadcaster;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.auction.containers.Data;
import com.auction.containers.Scene;
import com.auction.model.Player;
import com.auction.model.Statistics;
import com.auction.model.Team;
import com.auction.service.AuctionService;
import com.auction.model.Auction;
import com.auction.util.AuctionFunctions;
import com.auction.util.AuctionUtil;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ISPL extends Scene{

	private String status;
	private String slashOrDash = "-";
	public String session_selected_broadcaster = "ISPL";
	public Data data = new Data();
	public String which_graphics_onscreen = "BG";
	public int current_layer = 2;
	private String logo_path = "C:\\Images\\AUCTION\\Logos\\";
	private String logo_base_path = "C:\\Images\\AUCTION\\Logo_Base\\";
	private String photo_path  = "C:\\Images\\AUCTION\\Photos\\";
	private String icon_path = "C:\\Images\\AUCTION\\Icons\\";
	private int value1 = 0;
	
	public ISPL() {
		super();
	}

	public ISPL(String scene_path, String which_Layer) {
		super(scene_path, which_Layer);
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public Data updateData(Scene scene, Auction auction,AuctionService auctionService, PrintWriter print_writer) throws InterruptedException
	{
		if(which_graphics_onscreen.equalsIgnoreCase("PLAYERPROFILE")) {
			populatePlayerProfile(true,print_writer, "", data.getPlayer_id(),auctionService.getAllStats(),auction,auctionService, session_selected_broadcaster);
		}else if(which_graphics_onscreen.equalsIgnoreCase("SQUAD")) {
			populateSquad(true,print_writer, "",value1, auction,auctionService,session_selected_broadcaster);
		}else if(which_graphics_onscreen.equalsIgnoreCase("REMAINING_PURSE_ALL")) {
			populateRemainingPurse(true,print_writer, "", auction,auctionService, session_selected_broadcaster);
		}else if(which_graphics_onscreen.equalsIgnoreCase("SINGLE_PURSE")) {
			populateRemainingPurseSingle(true,print_writer, "",value1,auction,auctionService, session_selected_broadcaster);
		}else if(which_graphics_onscreen.equalsIgnoreCase("TOP_SOLD")) {
			populateTopSold(true,print_writer, "", auction,auctionService, session_selected_broadcaster);
		}else if(which_graphics_onscreen.equalsIgnoreCase("RTM")) {
			populateRtmAvailable(true,print_writer, "", auction,auctionService, session_selected_broadcaster);
		}else if(which_graphics_onscreen.equalsIgnoreCase("SLOTS")) {
			populateSlotsRemaining(true,print_writer, "", auction,auctionService,session_selected_broadcaster);
		}
		return data;
	}
	
	public Object ProcessGraphicOption(String whatToProcess, Auction auction, AuctionService auctionService,
			PrintWriter print_writer, List<Scene> scenes, String valueToProcess) throws InterruptedException, NumberFormatException, IllegalAccessException, StreamReadException, DatabindException, IOException {
		switch (whatToProcess.toUpperCase()) {
		case "POPULATE-FF-PLAYERPROFILE": case "POPULATE-SQUAD": case "POPULATE-REMAINING_PURSE_ALL": case "POPULATE-SINGLE_PURSE":
		case "POPULATE-TOP_SOLD": case "POPULATE-CRAWL": case "POPULATE-SQUAD_ROLE": case "POPULATE-FF_IDENT": case "POPULATE-ONLY_SQUAD":
		case "POPULATE-RTM_AVAILABLE": case "POPULATE-SLOTS_REMAINING":	case "POPULATE-FF_ICONIC_PLAYERS":
			switch (session_selected_broadcaster.toUpperCase()) {
			case "HANDBALL": case "ISPL":
				switch(whatToProcess.toUpperCase()) {
				case "POPULATE-L3-INFOBAR":
					scenes.get(0).setScene_path(valueToProcess.split(",")[0]);
					scenes.get(0).scene_load(print_writer,session_selected_broadcaster);
					break;
				default:
					print_writer.println("LAYER3*EVEREST*STAGE*DIRECTOR*In SHOW 71.0;");
					current_layer = 3 - current_layer;
					scenes.get(1).setWhich_layer(String.valueOf(current_layer));
					scenes.get(1).setScene_path(valueToProcess.split(",")[0]);
					scenes.get(1).scene_load(print_writer,session_selected_broadcaster);
					break;
				}
				if(new File(AuctionUtil.AUCTION_DIRECTORY + AuctionUtil.AUCTION_JSON).exists()) {
					auction = new ObjectMapper().readValue(new File(AuctionUtil.AUCTION_DIRECTORY + 
							AuctionUtil.AUCTION_JSON), Auction.class);
					auction = AuctionFunctions.populateMatchVariables(auctionService, auction);
					auction.setTeamZoneList(AuctionFunctions.PlayerCountPerTeamZoneWise(auction.getTeam(), 
							auction.getPlayers(), auction.getPlayersList()));

				}
				switch (whatToProcess.toUpperCase()) {
				case "POPULATE-SQUAD_ROLE":
					populateSquadRole(print_writer, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]), auction,auctionService,session_selected_broadcaster);
					break;
				case "POPULATE-ONLY_SQUAD":
					value1 = Integer.valueOf(valueToProcess.split(",")[1]);
					populateOnlySquad(false,print_writer, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]), auction,auctionService,session_selected_broadcaster);
					break;
				case "POPULATE-SQUAD":
					value1 = Integer.valueOf(valueToProcess.split(",")[1]);
					populateSquad(false,print_writer, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]), auction,auctionService,session_selected_broadcaster);
					break;
				case "POPULATE-RTM_AVAILABLE":
					populateRtmAvailable(false,print_writer, valueToProcess.split(",")[0], auction,auctionService, session_selected_broadcaster);
					break;
				case "POPULATE-SLOTS_REMAINING":	
					populateSlotsRemaining(false,print_writer, valueToProcess.split(",")[0], auction,auctionService, session_selected_broadcaster);
					break;	
				case "POPULATE-REMAINING_PURSE_ALL":
					populateRemainingPurse(false,print_writer, valueToProcess.split(",")[0], auction,auctionService, session_selected_broadcaster);
					break;
				case "POPULATE-SINGLE_PURSE":
					value1 = Integer.valueOf(valueToProcess.split(",")[1]);
					populateRemainingPurseSingle(false,print_writer, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]), 
							auction,auctionService, session_selected_broadcaster);
					break;
				case "POPULATE-FF-PLAYERPROFILE":
					data.setPlayer_id(Integer.valueOf(valueToProcess.split(",")[1]));
					populatePlayerProfile(false,print_writer,valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]),
							auctionService.getAllStats(),auction,auctionService, session_selected_broadcaster);
					break;
				case "POPULATE-TOP_SOLD":
					populateTopSold(false,print_writer, valueToProcess.split(",")[0], auction,auctionService, session_selected_broadcaster);
					break;
				case "POPULATE-CRAWL":
					populateCrawl(false,print_writer, valueToProcess.split(",")[1],valueToProcess.split(",")[2], auction,auctionService,auctionService.getTeams(), session_selected_broadcaster);
					break;
				case "POPULATE-FF_IDENT":
					populateIdent(print_writer, valueToProcess.split(",")[0], session_selected_broadcaster);
					break;
				case "POPULATE-FF_ICONIC_PLAYERS":
					populateIconicPlayers(print_writer, valueToProcess.split(",")[0], auction,auctionService, session_selected_broadcaster);
					break;
				
				}
				//return JSONObject.fromObject(this_doad).toString();
			}
			
		case "ANIMATE-OUT": case "CLEAR-ALL": case "ANIMATE-IN-PLAYERPROFILE": case "ANIMATE-IN-SQUAD": case "ANIMATE-IN-REMAINING_PURSE_ALL": case "ANIMATE-IN-SINGLE_PURSE":
		case "ANIMATE-IN-TOP_SOLD": case "ANIMATE-IN-CRAWL": case "ANIMATE-IN-FF_IDENT": case "ANIMATE-IN-RTM": case "ANIMATE-IN-SLOTS": case "ANIMATE-IN-ONLY_SQUAD":
		case "ANIMATE-IN-FF_ICONIC_PLAYERS":
			switch (session_selected_broadcaster.toUpperCase()) {
			case "HANDBALL": case "ISPL":
				switch (whatToProcess.toUpperCase()) {
				case "ANIMATE-IN-PLAYERPROFILE": case "ANIMATE-IN-SQUAD": case "ANIMATE-IN-REMAINING_PURSE_ALL": 
				case "ANIMATE-IN-SINGLE_PURSE": case "ANIMATE-IN-TOP_SOLD": case "ANIMATE-IN-CRAWL": case "ANIMATE-IN-FF_IDENT":
				case "ANIMATE-IN-RTM": case "ANIMATE-IN-SLOTS": case "ANIMATE-IN-ONLY_SQUAD":
					
					if(which_graphics_onscreen != "" && which_graphics_onscreen != "BG") {
						switch(which_graphics_onscreen) {
						case "PLAYERPROFILE":
							processAnimation(print_writer, "Out", "START", session_selected_broadcaster,(3-current_layer));
							processAnimation(print_writer, "Result", "START", session_selected_broadcaster,(3-current_layer));
							break;
						case "SQUAD":
							processAnimation(print_writer, "Out", "START", session_selected_broadcaster,(3-current_layer));
							TimeUnit.MILLISECONDS.sleep(2250);
							break;
						case "REMAINING_PURSE_ALL": case "SINGLE_PURSE": case "TOP_SOLD":
						case "FF_IDENT": case "RTM": case "SLOTS": case "ONLY_SQUAD": case "FF_ICONIC_PLAYERS":
							processAnimation(print_writer, "Out", "START", session_selected_broadcaster,(3-current_layer));
							TimeUnit.SECONDS.sleep(1);
//							print_writer.println("LAYER" + (3-current_layer) + "*EVEREST*SINGLE_SCENE CLEAR;");
//							TimeUnit.SECONDS.sleep(1);
							break;
						case "CRAWL":	
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Scroller-MAIN*GEOMETRY*SCROLLER SET CRAWL 0;");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Scroller-MAIN*GEOMETRY*SCROLLER SET ACTIVE 0;");
//							TimeUnit.SECONDS.sleep(1);
//							print_writer.println("LAYER1*EVEREST*SINGLE_SCENE CLEAR;");
							which_graphics_onscreen = "";
							break;	
						}
					}else if(which_graphics_onscreen == "BG") {
						//print_writer.println("LAYER3*EVEREST*STAGE*DIRECTOR*Out START;");
						//TimeUnit.SECONDS.sleep(1);
					}
					break;
				}
				
				switch (whatToProcess.toUpperCase()) {
				case "ANIMATE-IN-CRAWL":
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Scroller-MAIN*GEOMETRY*SCROLLER SET CLEAR INVOKE;");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Scroller-MAIN*GEOMETRY*SCROLLER SET BUILD_QUEUE INVOKE;");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Scroller-MAIN*GEOMETRY*SCROLLER SET CRAWL 1;");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Scroller-MAIN*GEOMETRY*SCROLLER SET ACTIVE 1;");
					which_graphics_onscreen = "CRAWL";
					break;
				case "ANIMATE-IN-PLAYERPROFILE": 
					print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
					TimeUnit.SECONDS.sleep(2);
					print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*LOOP START;");
					which_graphics_onscreen = "PLAYERPROFILE";
					break;
				case "ANIMATE-IN-ONLY_SQUAD":
					print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
					print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*LOOP START;");
					which_graphics_onscreen = "ONLY_SQUAD";
					break;
				case "ANIMATE-IN-SQUAD":
					print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
					print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*LOOP START;");
					which_graphics_onscreen = "SQUAD";
					break;
				case "ANIMATE-IN-RTM":
					print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
					print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*LOOP START;");
					which_graphics_onscreen = "RTM";
					break;
				case "ANIMATE-IN-SLOTS":
					print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
					print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*LOOP START;");
					which_graphics_onscreen = "SLOTS";
					break;	
				case "ANIMATE-IN-REMAINING_PURSE_ALL":
					print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
					print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*LOOP START;");
					which_graphics_onscreen = "REMAINING_PURSE_ALL";
					break;
				case "ANIMATE-IN-SINGLE_PURSE":
					print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
					print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*LOOP START;");
					which_graphics_onscreen = "SINGLE_PURSE";
					break;
				case "ANIMATE-IN-TOP_SOLD":
					print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
					print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*LOOP START;");
					which_graphics_onscreen = "TOP_SOLD";
					break;
				case "ANIMATE-IN-FF_IDENT":
					print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
					print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*LOOP START;");
					which_graphics_onscreen = "FF_IDENT";
					break;
				case "ANIMATE-IN-FF_ICONIC_PLAYERS":
					print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
					print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*LOOP START;");
					which_graphics_onscreen = "FF_ICONIC_PLAYERS";
					break;
				
				case "CLEAR-ALL":
					print_writer.println("LAYER1*EVEREST*SINGLE_SCENE CLEAR;");
					print_writer.println("LAYER2*EVEREST*SINGLE_SCENE CLEAR;");
					which_graphics_onscreen = "";
					break;
				
				case "ANIMATE-OUT":
					switch(which_graphics_onscreen) {
					case "INFOBAR":
						processAnimation(print_writer, "Out", "START", session_selected_broadcaster,current_layer);
						which_graphics_onscreen = "";
						break;
					
					case "PLAYERPROFILE":
						processAnimation(print_writer, "Out", "START", session_selected_broadcaster,(3-current_layer));
						processAnimation(print_writer, "Result", "START", session_selected_broadcaster,(3-current_layer));
						TimeUnit.SECONDS.sleep(4);
						processAnimation(print_writer, "In", "SHOW 0.0", session_selected_broadcaster,(3-current_layer));
						processAnimation(print_writer, "Out", "SHOW 0.0", session_selected_broadcaster,(3-current_layer));
						processAnimation(print_writer, "Result", "SHOW 0.0", session_selected_broadcaster,(3-current_layer));
						break;
					case "SQUAD": 
						processAnimation(print_writer, "Out", "START", session_selected_broadcaster,1);
						processAnimation(print_writer, "Out", "START", session_selected_broadcaster,2);
						TimeUnit.SECONDS.sleep(4);
						processAnimation(print_writer, "In", "SHOW 0.0", session_selected_broadcaster,1);
						processAnimation(print_writer, "Out", "SHOW 0.0", session_selected_broadcaster,1);
						
						processAnimation(print_writer, "In", "SHOW 0.0", session_selected_broadcaster,2);
						processAnimation(print_writer, "Out", "SHOW 0.0", session_selected_broadcaster,2);
						break;
					
					case "REMAINING_PURSE_ALL": case "SINGLE_PURSE": case "TOP_SOLD": case "FF_IDENT": case "RTM": case "SLOTS":
					case "ONLY_SQUAD":	
						processAnimation(print_writer, "Out", "START", session_selected_broadcaster,(3-current_layer));
						TimeUnit.SECONDS.sleep(4);
						processAnimation(print_writer, "In", "SHOW 0.0", session_selected_broadcaster,(3-current_layer));
						processAnimation(print_writer, "Out", "SHOW 0.0", session_selected_broadcaster,(3-current_layer));
//						print_writer.println("LAYER3*EVEREST*STAGE*DIRECTOR*In START;");
//						print_writer.println("LAYER3*EVEREST*STAGE*DIRECTOR*Loop START;");
						
						break;
					case "CRAWL":
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Scroller-MAIN*GEOMETRY*SCROLLER SET CRAWL 0;");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Scroller-MAIN*GEOMETRY*SCROLLER SET ACTIVE 0;");
						which_graphics_onscreen = "";
						break;
					}
					break;
				}
				
			}
			
			
		}
		return null;
	}
	private void populateIconicPlayers(PrintWriter print_writer,String viz_scene, Auction auction,
			AuctionService auctionService, String session_selected_broadcaster) throws InterruptedException {
		
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogo " +logo_path
				+ "ISPL" + AuctionUtil.PNG_EXTENSION + ";");
		
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " +"ICON PLAYERS"+ ";");
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " +" "+ ";");
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader AUCTION 2025;");	
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + logo_path + "ISPL" 
				+ AuctionUtil.PNG_EXTENSION + ";");
		int row = 0;
		for(int i=0;i<auction.getPlayersList().size();i++) {
			
			Player player = auction.getPlayersList().get(i);
			
		 if(player.getIconic()!= null && player.getIconic().equalsIgnoreCase(AuctionUtil.YES)) {
			 row ++ ;
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage0" +row+" "+ photo_path + 
					player.getPhotoName() + AuctionUtil.PNG_EXTENSION + ";");
			String Icon = "";
			if(player.getRole().toUpperCase().equalsIgnoreCase("WICKET-KEEPER")) {
				 Icon = "Keeper";
			}else {
				if(player.getRole().toUpperCase().equalsIgnoreCase("BATSMAN") || player.getRole().toUpperCase().equalsIgnoreCase("BAT/KEEPER")) {
					if(player.getBatsmanStyle().toUpperCase().equalsIgnoreCase("RHB")) {
						 Icon = "Batsman";
					}
					else if(player.getBatsmanStyle().toUpperCase().equalsIgnoreCase("LHB")) {
						 Icon = "Batsman_Lefthand";
					}
				}else if(player.getRole().toUpperCase().equalsIgnoreCase("BOWLER")) {
					if(player.getBowlerStyle() == null) {
						 Icon = "FastBowler";
					}else {
						switch(player.getBowlerStyle().toUpperCase()) {
						case "RF": case "RFM": case "RMF": case "RM": case "RSM": case "LF": case "LFM": case "LMF": case "LM":
							 Icon = "FastBowler";
							break;
						case "ROB": case "RLB": case "LSL": case "WSL": case "LCH": case "RLG": case "WSR": case "LSO":
							 Icon = "SpinBowlerIcon";
							break;
						}
					}
				}else if(player.getRole().toUpperCase().equalsIgnoreCase("ALL-ROUNDER")) {
					if(player.getBowlerStyle() == null) {
						 Icon = "FastBowlerAllrounder";
					}else {
						switch(player.getBowlerStyle().toUpperCase()) {
						case "RF": case "RFM": case "RMF": case "RM": case "RSM": case "LF": case "LFM": case "LMF": case "LM":
							 Icon = "FastBowlerAllrounder";
							break;
						case "ROB": case "RLB": case "LSL": case "WSL": case "LCH": case "RLG": case "WSR": case "LSO":
							 Icon = "SpinBowlerAllrounder";
							break;
						}
					}
				}
			}
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerRole0" +row+" "+ icon_path +Icon + ";");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFirstname0" +row+" "+player.getFirstname()+ ";");
			if(player.getSurname() != null) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastname0" +row+" " +player.getSurname()+ ";");

			}else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastname0" +row+" "+ ";");
			}
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectDataType0" +row+" 0;");

			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vPlayerInfo0" +row+" "+"1;");

			if(player.getCategory().equalsIgnoreCase("U19")) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFrom0"+ row + " " + 
						"UNDER 19" + ";");
			}else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFrom0"+ row + " " + 
						player.getCategory().toUpperCase() + ";");
			}
			boolean player_found = false;
			for(Player plyr : auction.getPlayers()) {
				if(player.getPlayerId() == plyr.getPlayerId()) {
					if(plyr.getSoldOrUnsold().equalsIgnoreCase(AuctionUtil.SOLD) || plyr.getSoldOrUnsold().equalsIgnoreCase(AuctionUtil.RTM)) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectDataType0" +row+" 0;");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBidPrice0" +row+" "+ ConvertToLakh(plyr.getSoldForPoints())+";");
						
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogoBase0" +row+" "+ logo_base_path + 
								auction.getTeam().get(plyr.getTeamId() - 1).getTeamName4() + AuctionUtil.PNG_EXTENSION + ";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo0" +row+" "+ logo_path + 
								auction.getTeam().get(plyr.getTeamId() - 1).getTeamName4() + AuctionUtil.PNG_EXTENSION + ";");
						
					}else if (plyr.getSoldOrUnsold().equalsIgnoreCase(AuctionUtil.BID)) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead0" +row+" BASE PRICE ;");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBidPrice0" +row+" "+ "6 ;");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectDataType0" +row+" 1;");
					}
					player_found = true;
					break;
				}
			}
			if(!player_found) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBidPrice0" +row+" "+ "6 ;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectDataType0" +row+" 1;");
			}
		}
	  }
		TimeUnit.SECONDS.sleep(1);
		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 71.0;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.tga;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
		TimeUnit.SECONDS.sleep(1);
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
	}

	public void populatePlayerProfile(boolean is_this_updating,PrintWriter print_writer,String viz_scene, int playerId,List<Statistics> stats, Auction auction,AuctionService auctionService, String session_selected_broadcaster) throws InterruptedException 
	{
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + 
				"AUCTION 2025" + ";");
		
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBaePointsHead " + 
				"BASE PRICE" + ";");
		
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBasePrice " + 
				"3" + ";");
		
		if(auction.getPlayers() != null && auction.getPlayers().size() > 0) {
			if(data.isPlayer_sold_or_unsold() == false) {
				for(int i=auction.getPlayers().size()-1; i >= 0; i--) {
					if(playerId == auction.getPlayers().get(i).getPlayerId()) {
						if(auction.getPlayers().get(i).getSoldOrUnsold().equalsIgnoreCase(AuctionUtil.SOLD)||
								auction.getPlayers().get(i).getSoldOrUnsold().equalsIgnoreCase(AuctionUtil.RTM)) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSoldUnsold 1 ;");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBidPrice " + 
									ConvertToLakh(auction.getPlayers().get(i).getSoldForPoints()) + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + 
									auctionService.getTeams().get(auction.getPlayers().get(i).getTeamId() - 1).getTeamName4() + AuctionUtil.PNG_EXTENSION + ";");
							
							if((auction.getPlayers().get(i).getSoldOrUnsold().equalsIgnoreCase(AuctionUtil.RTM))){
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$PricesGRp$"
										+ "Sold_Undold$Sold$RTM*CONTAINER SET ACTIVE 1;");
							}else {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$PricesGRp$"
										+ "Sold_Undold$Sold$RTM*CONTAINER SET ACTIVE 0;");
							}
							
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tUnsold "+
									"SOLD;");
							
							TimeUnit.MILLISECONDS.sleep(200);
							print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Result START;");
							data.setPlayer_sold_or_unsold(true);
						}else if(auction.getPlayers().get(i).getSoldOrUnsold().equalsIgnoreCase(AuctionUtil.UNSOLD)) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSoldUnsold 0 ;");
							print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Result START;");
							data.setPlayer_sold_or_unsold(true);
						}
					}
				}
			}
		}
		
		int remaining=0;
		for(int i=0; i <= auction.getTeam().size()-1; i++) {
			remaining=Integer.valueOf(auction.getTeam().get(i).getTeamTotalPurse());
			if(auction.getPlayers() != null) {
				for(int j=0; j <= auction.getPlayers().size()-1; j++) {
					if(auction.getPlayers().get(j).getTeamId() == auction.getTeam().get(i).getTeamId()) {
						remaining = remaining - auction.getPlayers().get(j).getSoldForPoints();
					}
				}
			}
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPurse0" + (i+1) + " " + 
					ConvertToLakh(remaining) + ";");
			remaining=0;
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogo0" + (i+1) + " " + logo_path + 
					auctionService.getTeams().get(auction.getTeam().get(i).getTeamId()-1).getTeamName4() + AuctionUtil.PNG_EXTENSION + ";");
			
		}
		if(is_this_updating == false) {
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Result SHOW 0.0;");
			data.setPlayer_sold_or_unsold(false);
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vPlayerPic 0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFirstName " + 
					auctionService.getAllPlayer().get(playerId - 1).getFirstname() + ";");
			if(auctionService.getAllPlayer().get(playerId - 1).getSurname() != null) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastName " + 
						auctionService.getAllPlayer().get(playerId - 1).getSurname() + ";");
			}else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastName " + "" + ";");
			}
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + photo_path + 
					auctionService.getAllPlayer().get(playerId - 1).getPhotoName() + AuctionUtil.PNG_EXTENSION + ";");
			
			if(auctionService.getAllPlayer().get(playerId-1).getIconic()!=null && auctionService.getAllPlayer().get(playerId-1).getIconic().equalsIgnoreCase(AuctionUtil.YES)) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectIcon 1;");
			}else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectIcon 2;");
			}
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET 1 1;");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tAgeHead " + "AGE" + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatStyleHead " + "BATSMAN STYLE" + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlStyleHead " + "BOWLER STYLE" + ";");
			
			if(auctionService.getAllPlayer().get(playerId - 1).getAge()!=null && !auctionService.getAllPlayer().get(playerId - 1).getAge().isEmpty()) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tAge " + auctionService.getAllPlayer().get(playerId - 1).getAge() + " yrs" + ";");
			}else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tAge " + "-" + ";");
			}
			
			if(auctionService.getAllPlayer().get(playerId - 1).getRole()!=null && !auctionService.getAllPlayer().get(playerId - 1).getRole().isEmpty()) {
				
				if(auctionService.getAllPlayer().get(playerId - 1).getRole().equalsIgnoreCase("BATSMAN")) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRole " + "BATTER" + ";");
				}else {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRole " + 
							auctionService.getAllPlayer().get(playerId - 1).getRole().toUpperCase() + ";"); 	
				}
	
			}else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRole " + "-" + ";");
			}
			
			if(auctionService.getAllPlayer().get(playerId - 1).getLastYearTeam() != null) {
				if(auctionService.getAllPlayer().get(playerId - 1).getCategory().equalsIgnoreCase("U19")) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tCategory " + 
							"UNDER 19" + ";");
				}else {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tCategory " + 
							auctionService.getAllPlayer().get(playerId - 1).getCategory().toUpperCase() + ";");
				}
			}else {
				if(auctionService.getAllPlayer().get(playerId - 1).getCategory().equalsIgnoreCase("U19")) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tCategory " + 
							"UNDER 19 - UNCAPPED" + ";");
				}else {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tCategory " + 
							auctionService.getAllPlayer().get(playerId - 1).getCategory().toUpperCase() + " - UNCAPPED" + ";");
				}
			}
			
			

			if(auctionService.getAllPlayer().get(playerId - 1).getBatsmanStyle()!=null && 
					!auctionService.getAllPlayer().get(playerId - 1).getBatsmanStyle().isEmpty()) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatStyle " + auctionService.getAllPlayer().get(playerId - 1).getBatsmanStyle() + ";");
			}else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatStyle " + "-" + ";");			
			}
			
			
			if(auctionService.getAllPlayer().get(playerId - 1).getBowlerStyle()!=null && 
					!auctionService.getAllPlayer().get(playerId - 1).getBowlerStyle().isEmpty()) {
				String bowlerStyle=getBowlerType(auctionService.getAllPlayer().get(playerId - 1).getBowlerStyle());
				if(bowlerStyle==null||bowlerStyle.isEmpty()) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlStyle " + auctionService.getAllPlayer().get(playerId - 1).getBowlerStyle() + ";");	
				}else {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlStyle " + bowlerStyle + ";");		
				}
			}else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlStyle " + "-" + ";");
			}
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$Achivements$First*CONTAINER SET ACTIVE 0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$Achivements$Second*CONTAINER SET ACTIVE 0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$Achivements$Third*CONTAINER SET ACTIVE 0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$Achivements$Fourth*CONTAINER SET ACTIVE 0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFourth03 " + "" + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tAchiveHead " + "ACHIEVEMENTS" + ";");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vAchive 0;");
			
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 71.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.tga;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
		}
		
	}
	
	public static String getNumber(int NumberType) {
		switch(NumberType) {
		case 1: 
			return "First";
		case 2: 
			return "2nd";
		case 3: 
			return "3rd";
		case 4: 
			return "4th";
		case 5: 
			return "5th";
			
		}
		return "";
	}
	
	public void populateSquadRole(PrintWriter print_writer,String viz_scene, int teamId, Auction auction,AuctionService auctionService, String session_selected_broadcaster) throws InterruptedException 
	{
		int batter=0,bowler=0,all_rounder=0,wicket_keeper = 0;
		if(auction.getPlayers() != null) {
			for(Player plyr : auction.getPlayers()) {
				if(plyr.getTeamId() == teamId) {	
					if(auctionService.getAllPlayer().get(plyr.getPlayerId()-1).getRole().toUpperCase().equalsIgnoreCase("BATSMAN")) {
						batter++;
					}else if(auctionService.getAllPlayer().get(plyr.getPlayerId()-1).getRole().toUpperCase().equalsIgnoreCase("WICKET-KEEPER")) {
						wicket_keeper++;
					}else if(auctionService.getAllPlayer().get(plyr.getPlayerId()-1).getRole().toUpperCase().equalsIgnoreCase("BOWLER")) {
						bowler++;
					}else if(auctionService.getAllPlayer().get(plyr.getPlayerId()-1).getRole().toUpperCase().equalsIgnoreCase("ALL-ROUNDER")) {
						all_rounder++;
					}
				}
			}
		}
		
		System.out.println("BATSMAN "+batter+"\nBowler "+bowler+"\nWicket Keeper "+wicket_keeper+"\nall rounder "+all_rounder);
		
	}
	
	public void populateTopSold(boolean is_this_updating,PrintWriter print_writer,String viz_scene, Auction auction,AuctionService auctionService, String session_selected_broadcaster) throws InterruptedException 
	{
		int row = 0;
		List<Player> top_sold = new ArrayList<Player>();
		
		if(auction.getPlayers() != null) {
			for(Player plyr : auction.getPlayers()) {
				if(plyr.getSoldOrUnsold().equalsIgnoreCase("SOLD")) {
					top_sold.add(plyr);
				}	
			}
		}
		
		Collections.sort(top_sold,new AuctionFunctions.PlayerStatsComparator());
		
		if(is_this_updating == false) {
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + 
					"INDIAN STREET PREMIER LEAGUE" + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + 
					"" + ";");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + 
					"PLAYER AUCTION 2025" + ";");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead01 " + 
					"" + ";");
			
			for(int i=1; i<= 10; i++) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$Player" + i + "*CONTAINER SET ACTIVE 0;");
			}
		}
		
		for(int m=0; m<= top_sold.size() - 1; m++) {
			if(!top_sold.get(m).getSoldOrUnsold().equalsIgnoreCase("BID")) {
				row = row + 1;
	        	if(row <= 10) {
	        		
	        		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$Player" + row + "*CONTAINER SET ACTIVE 1;");
	        		
	        		if(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getSurname() != null) {
	        			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName0" + row + " " + 
	        					auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getFirstname() + ";");
	        			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName0" + row + " " + 
	        					auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getSurname() + ";");
					}else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName0" + row + " " + 
								auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getFirstname() + ";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName0" + row + " " + ";");
					}
	        		
	        		if(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getRole() != null &&
							!auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getRole().isEmpty()) {
						
						if(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getRole().toUpperCase().equalsIgnoreCase("WICKET-KEEPER")) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgIcon0" + row + " " + 
									icon_path + "Keeper" + AuctionUtil.PNG_EXTENSION + ";");
						}else {
							if(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getRole().toUpperCase().equalsIgnoreCase("BATSMAN") || 
									auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getRole().toUpperCase().equalsIgnoreCase("BAT/KEEPER")) {
								if(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getBatsmanStyle().toUpperCase().equalsIgnoreCase("RHB")) {
									
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgIcon0" + row + " " + 
											icon_path + "Batsman" + AuctionUtil.PNG_EXTENSION + ";");
								}
								else if(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getBatsmanStyle().toUpperCase().equalsIgnoreCase("LHB")) {
									
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgIcon0" + row + " " + 
											icon_path + "Batsman_Lefthand" + AuctionUtil.PNG_EXTENSION + ";");
								}
							}else if(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getRole().toUpperCase().equalsIgnoreCase("BOWLER")) {
								if(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getBowlerStyle() == null) {
									
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgIcon0" + row + " " + 
											icon_path + "FastBowler" + AuctionUtil.PNG_EXTENSION + ";");
								}else {
									switch(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getBowlerStyle().toUpperCase()) {
									case "RF": case "RFM": case "RMF": case "RM": case "RSM": case "LF": case "LFM": case "LMF": case "LM":
										
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgIcon0" + row + " " + 
												icon_path + "FastBowler" + AuctionUtil.PNG_EXTENSION + ";");
										break;
									case "ROB": case "RLB": case "LSL": case "WSL": case "LCH": case "RLG": case "WSR": case "LSO":
										
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgIcon0" + row + " " + 
												icon_path + "SpinBowlerIcon" + AuctionUtil.PNG_EXTENSION + ";");
										break;
									}
								}
							}else if(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getRole().toUpperCase().equalsIgnoreCase("ALL-ROUNDER")) {
								if(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getBowlerStyle() == null) {
									
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgIcon0" + row + " " + 
											icon_path + "FastBowlerAllrounder" + AuctionUtil.PNG_EXTENSION + ";");
								}else {
									switch(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getBowlerStyle().toUpperCase()) {
									case "RF": case "RFM": case "RMF": case "RM": case "RSM": case "LF": case "LFM": case "LMF": case "LM":
										
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgIcon0" + row + " " + 
												icon_path + "FastBowlerAllrounder" + AuctionUtil.PNG_EXTENSION + ";");
										break;
									case "ROB": case "RLB": case "LSL": case "WSL": case "LCH": case "RLG": case "WSR": case "LSO":
										
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgIcon0" + row + " " + 
												icon_path + "SpinBowlerAllrounder" + AuctionUtil.PNG_EXTENSION + ";");
										break;
									}
								}
							}
						}
					}else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgIcon0" + row + " " + "-" + ";");
					}
	        		
	        		
	        		
	        		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeam0" + row + " " + 
	        				auction.getTeam().get(top_sold.get(m).getTeamId()-1).getTeamName1() + ";");
	        		
	        		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPurse0" + row + " " + 
	    					ConvertToLakh(top_sold.get(m).getSoldForPoints()) + ";");
	        		
//	        		if(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId()-1).getCategory().equalsIgnoreCase("FOREIGN")) {
//	    				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vInternational0" + row + " 1 ;");
//	    			}else {
//	    				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vInternational0" + row + " 0 ;");
//	    			}
	        	}
			}
		}
		
		if(is_this_updating == false) {
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 71.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
		}
	}
	
	public void populateCrawl(boolean is_this_updating,PrintWriter print_writer,String data,String data2, Auction auction,AuctionService auctionService,
			List<Team> team, String session_selected_broadcaster) throws InterruptedException 
	{
		Map<String,  ArrayList<String>> prices = new HashMap<String, ArrayList<String>>();
		Map<String,  Integer> remainingPurse = new HashMap<String, Integer>();
		 
		int remaining = 0;
		 for(int i=0; i <= auction.getTeam().size()-1; i++) {
			 ArrayList<String> list= new ArrayList<String>();
			 remaining = Integer.valueOf(auction.getTeam().get(i).getTeamTotalPurse());
				for(int j=0; j <= auction.getPlayers().size()-1; j++) {
					if(auction.getPlayers().get(j).getSoldOrUnsold().equalsIgnoreCase("SOLD") &&
							auction.getPlayers().get(j).getTeamId() == auction.getTeam().get(i).getTeamId()) {
						list.add(auction.getPlayers().get(j).getFull_name());
						remaining = remaining - auction.getPlayers().get(j).getSoldForPoints();
					}
				}
				
				remainingPurse.put(auction.getTeam().get(i).getTeamName1(), remaining);
				if(!list.isEmpty()) {
					prices.put(auction.getTeam().get(i).getTeamName1(), list);
				}
				remaining = 0;
			}
		 
		if(is_this_updating == false) {
			if(data.equalsIgnoreCase("SOLD") && data2.equalsIgnoreCase("PURSE")) {
				if(prices.size() == 6) {
					 print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Scroller-MAIN*GEOMETRY*SCROLLER SET DATA "
							+ "D1|TextInfo=" + "ISPL AUCTION 2024" + "\nD2|TextInfo02= " + (String) prices.keySet().toArray()[0] + " :" + " \nD3|TextInfo03=" + 
							String.join(",", prices.get((String) prices.keySet().toArray()[0])) + "\nD4|Gap" +
							"\nD2|TextInfo02= " + (String) prices.keySet().toArray()[1] + " :" + " \nD3|TextInfo03=" + String.join(", ", prices.get((String) prices.keySet().toArray()[1])) + "\nD4|Gap" + 
							"\nD2|TextInfo02= " + (String) prices.keySet().toArray()[2] + " :" + " \nD3|TextInfo03=" + String.join(", ", prices.get((String) prices.keySet().toArray()[2])) + "\nD4|Gap" + 
							"\nD2|TextInfo02= " + (String) prices.keySet().toArray()[3] + " :" + " \nD3|TextInfo03=" + String.join(", ", prices.get((String) prices.keySet().toArray()[3])) + "\nD4|Gap" +
							"\nD2|TextInfo02= " + (String) prices.keySet().toArray()[4] + " :" + " \nD3|TextInfo03=" + String.join(", ", prices.get((String) prices.keySet().toArray()[4])) + "\nD4|Gap" +
							"\nD2|TextInfo02= " + (String) prices.keySet().toArray()[5] + " :" + " \nD3|TextInfo03=" + String.join(", ", prices.get((String) prices.keySet().toArray()[5])) + "\nD4|Gap" 
							+ "\nD1|TextInfo=" + "REMAINING PURSE" + "\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[0] + " :" + " \nD3|TextInfo03=" + 
							ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[0])) + " L" + "\nD4|Gap" +
							"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[1] + " :" + " \nD3|TextInfo03=" + ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[1])) + " L" + "\nD4|Gap" + 
							"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[2] + " :" + " \nD3|TextInfo03=" + ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[2])) + " L" + "\nD4|Gap" + 
							"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[3] + " :" + " \nD3|TextInfo03=" + ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[3])) + " L" + "\nD4|Gap" +
							"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[4] + " :" + " \nD3|TextInfo03=" + ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[4])) + " L" + "\nD4|Gap" +
							"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[5] + " :" + " \nD3|TextInfo03=" + ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[5])) + " L" + "\nD4|Gap;");
				 }else if(prices.size() == 5) {
					 print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Scroller-MAIN*GEOMETRY*SCROLLER SET DATA "
								+ "D1|TextInfo=" + "ISPL AUCTION 2024" + "\nD2|TextInfo02= " + (String) prices.keySet().toArray()[0] + " :" + " \nD3|TextInfo03=" + 
								String.join(", ", prices.get((String) prices.keySet().toArray()[0])) + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) prices.keySet().toArray()[1] + " :" + " \nD3|TextInfo03=" + String.join(", ", prices.get((String) prices.keySet().toArray()[1])) + "\nD4|Gap" + 
								"\nD2|TextInfo02= " + (String) prices.keySet().toArray()[2] + " :" + " \nD3|TextInfo03=" + String.join(", ", prices.get((String) prices.keySet().toArray()[2])) + "\nD4|Gap" + 
								"\nD2|TextInfo02= " + (String) prices.keySet().toArray()[3] + " :" + " \nD3|TextInfo03=" + String.join(", ", prices.get((String) prices.keySet().toArray()[3])) + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) prices.keySet().toArray()[4] + " :" + " \nD3|TextInfo03=" + String.join(", ", prices.get((String) prices.keySet().toArray()[4])) + "\nD4|Gap" 
								+ "\nD1|TextInfo=" + "REMAINING PURSE" + "\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[0] + " :" + " \nD3|TextInfo03=" + 
								ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[0])) + " L" + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[1] + " :" + " \nD3|TextInfo03=" + ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[1])) + " L" + "\nD4|Gap" + 
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[2] + " :" + " \nD3|TextInfo03=" + ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[2])) + " L" + "\nD4|Gap" + 
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[3] + " :" + " \nD3|TextInfo03=" + ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[3])) + " L" + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[4] + " :" + " \nD3|TextInfo03=" + ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[4])) + " L" + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[5] + " :" + " \nD3|TextInfo03=" + ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[5])) + " L" + "\nD4|Gap;");
				 }else if(prices.size() == 4) {
					 print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Scroller-MAIN*GEOMETRY*SCROLLER SET DATA "
								+ "D1|TextInfo=" + "ISPL AUCTION 2024" + "\nD2|TextInfo02= " + (String) prices.keySet().toArray()[0] + " :" + " \nD3|TextInfo03=" + 
								String.join(", ", prices.get((String) prices.keySet().toArray()[0])) + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) prices.keySet().toArray()[1] + " :" + " \nD3|TextInfo03=" + String.join(", ", prices.get((String) prices.keySet().toArray()[1])) + "\nD4|Gap" + 
								"\nD2|TextInfo02= " + (String) prices.keySet().toArray()[2] + " :" + " \nD3|TextInfo03=" + String.join(", ", prices.get((String) prices.keySet().toArray()[2])) + "\nD4|Gap" + 
								"\nD2|TextInfo02= " + (String) prices.keySet().toArray()[3] + " :" + " \nD3|TextInfo03=" + String.join(", ", prices.get((String) prices.keySet().toArray()[3])) + "\nD4|Gap" 
								+ "\nD1|TextInfo=" + "REMAINING PURSE" + "\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[0] + " :" + " \nD3|TextInfo03=" + 
								ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[0])) + " L" + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[1] + " :" + " \nD3|TextInfo03=" + ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[1])) + " L" + "\nD4|Gap" + 
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[2] + " :" + " \nD3|TextInfo03=" + ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[2])) + " L" + "\nD4|Gap" + 
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[3] + " :" + " \nD3|TextInfo03=" + ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[3])) + " L" + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[4] + " :" + " \nD3|TextInfo03=" + ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[4])) + " L" + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[5] + " :" + " \nD3|TextInfo03=" + ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[5])) + " L" + "\nD4|Gap;");
				 }else if(prices.size() == 3) {
					 print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Scroller-MAIN*GEOMETRY*SCROLLER SET DATA "
								+ "D1|TextInfo=" + "ISPL AUCTION 2024" + "\nD2|TextInfo02= " + (String) prices.keySet().toArray()[0] + " :" + " \nD3|TextInfo03=" + 
								String.join(", ", prices.get((String) prices.keySet().toArray()[0])) + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) prices.keySet().toArray()[1] + " :" + " \nD3|TextInfo03=" + String.join(", ", prices.get((String) prices.keySet().toArray()[1])) + "\nD4|Gap" + 
								"\nD2|TextInfo02= " + (String) prices.keySet().toArray()[2] + " :" + " \nD3|TextInfo03=" + String.join(", ", prices.get((String) prices.keySet().toArray()[2])) + "\nD4|Gap" 
								+ "\nD1|TextInfo=" + "REMAINING PURSE" + "\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[0] + " :" + " \nD3|TextInfo03=" + 
								ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[0])) + " L" + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[1] + " :" + " \nD3|TextInfo03=" + ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[1])) + " L" + "\nD4|Gap" + 
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[2] + " :" + " \nD3|TextInfo03=" + ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[2])) + " L" + "\nD4|Gap" + 
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[3] + " :" + " \nD3|TextInfo03=" + ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[3])) + " L" + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[4] + " :" + " \nD3|TextInfo03=" + ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[4])) + " L" + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[5] + " :" + " \nD3|TextInfo03=" + ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[5])) + " L" + "\nD4|Gap;");
				 }else if(prices.size() == 2) {
					 print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Scroller-MAIN*GEOMETRY*SCROLLER SET DATA "
								+ "D1|TextInfo=" + "ISPL AUCTION 2024" + "\nD2|TextInfo02= " + (String) prices.keySet().toArray()[0] + " :" + " \nD3|TextInfo03=" + 
								String.join(", ", prices.get((String) prices.keySet().toArray()[0])) + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) prices.keySet().toArray()[1] + " :" + " \nD3|TextInfo03=" + String.join(", ", prices.get((String) prices.keySet().toArray()[1])) + "\nD4|Gap" 
								+ "\nD1|TextInfo=" + "REMAINING PURSE" + "\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[0] + " :" + " \nD3|TextInfo03=" + 
								ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[0])) + " L" + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[1] + " :" + " \nD3|TextInfo03=" + ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[1])) + " L" + "\nD4|Gap" + 
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[2] + " :" + " \nD3|TextInfo03=" + ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[2])) + " L" + "\nD4|Gap" + 
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[3] + " :" + " \nD3|TextInfo03=" + ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[3])) + " L" + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[4] + " :" + " \nD3|TextInfo03=" + ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[4])) + " L" + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[5] + " :" + " \nD3|TextInfo03=" + ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[5])) + " L" + "\nD4|Gap;");
				 }else if(prices.size() == 1) {
					 print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Scroller-MAIN*GEOMETRY*SCROLLER SET DATA "
								+ "D1|TextInfo=" + "ISPL AUCTION 2024" + "\nD2|TextInfo02= " + (String) prices.keySet().toArray()[0] + " :" + " \nD3|TextInfo03=" + 
								String.join(", ", prices.get((String) prices.keySet().toArray()[0])) + "\nD4|Gap" 
								+ "\nD1|TextInfo=" + "REMAINING PURSE" + "\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[0] + " :" + " \nD3|TextInfo03=" + 
								ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[0])) + " L" + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[1] + " :" + " \nD3|TextInfo03=" + ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[1])) + " L" + "\nD4|Gap" + 
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[2] + " :" + " \nD3|TextInfo03=" + ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[2])) + " L" + "\nD4|Gap" + 
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[3] + " :" + " \nD3|TextInfo03=" + ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[3])) + " L" + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[4] + " :" + " \nD3|TextInfo03=" + ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[4])) + " L" + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[5] + " :" + " \nD3|TextInfo03=" + ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[5])) + " L" + "\nD4|Gap;");
				 }
			}else if(data.equalsIgnoreCase("PURSE") && data2.equalsIgnoreCase("SOLD")) {
				if(prices.size() == 6) {
					 print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Scroller-MAIN*GEOMETRY*SCROLLER SET DATA "
							+ "D1|TextInfo=" + "REMAINING PURSE" + "\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[0] + " :" + " \nD3|TextInfo03=" + 
							ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[0])) + " L" + "\nD4|Gap" +
							"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[1] + " :" + " \nD3|TextInfo03=" + ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[1])) + " L" + "\nD4|Gap" + 
							"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[2] + " :" + " \nD3|TextInfo03=" + ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[2])) + " L" + "\nD4|Gap" + 
							"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[3] + " :" + " \nD3|TextInfo03=" + ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[3])) + " L" + "\nD4|Gap" +
							"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[4] + " :" + " \nD3|TextInfo03=" + ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[4])) + " L" + "\nD4|Gap" +
							"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[5] + " :" + " \nD3|TextInfo03=" + ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[5])) + " L" + "\nD4|Gap" 
							+"\nD1|TextInfo=" + "ISPL AUCTION 2024" + "\nD2|TextInfo02= " + (String) prices.keySet().toArray()[0] + " :" + " \nD3|TextInfo03=" + 
							String.join(", ", prices.get((String) prices.keySet().toArray()[0])) + "\nD4|Gap" +
							"\nD2|TextInfo02= " + (String) prices.keySet().toArray()[1] + " :" + " \nD3|TextInfo03=" + String.join(", ", prices.get((String) prices.keySet().toArray()[1])) + "\nD4|Gap" + 
							"\nD2|TextInfo02= " + (String) prices.keySet().toArray()[2] + " :" + " \nD3|TextInfo03=" + String.join(", ", prices.get((String) prices.keySet().toArray()[2])) + "\nD4|Gap" + 
							"\nD2|TextInfo02= " + (String) prices.keySet().toArray()[3] + " :" + " \nD3|TextInfo03=" + String.join(", ", prices.get((String) prices.keySet().toArray()[3])) + "\nD4|Gap" +
							"\nD2|TextInfo02= " + (String) prices.keySet().toArray()[4] + " :" + " \nD3|TextInfo03=" + String.join(", ", prices.get((String) prices.keySet().toArray()[4])) + "\nD4|Gap" +
							"\nD2|TextInfo02= " + (String) prices.keySet().toArray()[5] + " :" + " \nD3|TextInfo03=" + String.join(", ", prices.get((String) prices.keySet().toArray()[5])) + "\nD4|Gap;");
				 }else if(prices.size() == 5) {
					 print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Scroller-MAIN*GEOMETRY*SCROLLER SET DATA "
								+ "D1|TextInfo=" + "REMAINING PURSE" + "\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[0] + " :" + " \nD3|TextInfo03=" + 
								ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[0])) + " L" + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[1] + " :" + " \nD3|TextInfo03=" + ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[1])) + " L" + "\nD4|Gap" + 
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[2] + " :" + " \nD3|TextInfo03=" + ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[2])) + " L" + "\nD4|Gap" + 
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[3] + " :" + " \nD3|TextInfo03=" + ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[3])) + " L" + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[4] + " :" + " \nD3|TextInfo03=" + ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[4])) + " L" + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[5] + " :" + " \nD3|TextInfo03=" + ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[5])) + " L" + "\nD4|Gap"
								+ "\nD1|TextInfo=" + "ISPL AUCTION 2024" + "\nD2|TextInfo02= " + (String) prices.keySet().toArray()[0] + " :" + " \nD3|TextInfo03=" + 
								String.join(", ", prices.get((String) prices.keySet().toArray()[0])) + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) prices.keySet().toArray()[1] + " :" + " \nD3|TextInfo03=" + String.join(", ", prices.get((String) prices.keySet().toArray()[1])) + "\nD4|Gap" + 
								"\nD2|TextInfo02= " + (String) prices.keySet().toArray()[2] + " :" + " \nD3|TextInfo03=" + String.join(", ", prices.get((String) prices.keySet().toArray()[2])) + "\nD4|Gap" + 
								"\nD2|TextInfo02= " + (String) prices.keySet().toArray()[3] + " :" + " \nD3|TextInfo03=" + String.join(", ", prices.get((String) prices.keySet().toArray()[3])) + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) prices.keySet().toArray()[4] + " :" + " \nD3|TextInfo03=" + String.join(", ", prices.get((String) prices.keySet().toArray()[4])) + "\nD4|Gap;");
				 }else if(prices.size() == 4) {
					 print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Scroller-MAIN*GEOMETRY*SCROLLER SET DATA "
								+ "D1|TextInfo=" + "REMAINING PURSE" + "\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[0] + " :" + " \nD3|TextInfo03=" + 
								ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[0])) + " L" + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[1] + " :" + " \nD3|TextInfo03=" + ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[1])) + " L" + "\nD4|Gap" + 
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[2] + " :" + " \nD3|TextInfo03=" + ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[2])) + " L" + "\nD4|Gap" + 
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[3] + " :" + " \nD3|TextInfo03=" + ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[3])) + " L" + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[4] + " :" + " \nD3|TextInfo03=" + ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[4])) + " L" + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[5] + " :" + " \nD3|TextInfo03=" + ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[5])) + " L" + "\nD4|Gap"
								+ "\nD1|TextInfo=" + "ISPL AUCTION 2024" + "\nD2|TextInfo02= " + (String) prices.keySet().toArray()[0] + " :" + " \nD3|TextInfo03=" + 
								String.join(", ", prices.get((String) prices.keySet().toArray()[0])) + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) prices.keySet().toArray()[1] + " :" + " \nD3|TextInfo03=" + String.join(", ", prices.get((String) prices.keySet().toArray()[1])) + "\nD4|Gap" + 
								"\nD2|TextInfo02= " + (String) prices.keySet().toArray()[2] + " :" + " \nD3|TextInfo03=" + String.join(", ", prices.get((String) prices.keySet().toArray()[2])) + "\nD4|Gap" + 
								"\nD2|TextInfo02= " + (String) prices.keySet().toArray()[3] + " :" + " \nD3|TextInfo03=" + String.join(", ", prices.get((String) prices.keySet().toArray()[3])) + "\nD4|Gap;");
				 }else if(prices.size() == 3) {
					 print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Scroller-MAIN*GEOMETRY*SCROLLER SET DATA "
								+ "D1|TextInfo=" + "REMAINING PURSE" + "\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[0] + " :" + " \nD3|TextInfo03=" + 
								ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[0])) + " L" + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[1] + " :" + " \nD3|TextInfo03=" + ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[1])) + " L" + "\nD4|Gap" + 
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[2] + " :" + " \nD3|TextInfo03=" + ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[2])) + " L" + "\nD4|Gap" + 
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[3] + " :" + " \nD3|TextInfo03=" + ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[3])) + " L" + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[4] + " :" + " \nD3|TextInfo03=" + ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[4])) + " L" + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[5] + " :" + " \nD3|TextInfo03=" + ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[5])) + " L" + "\nD4|Gap" 
								+ "\nD1|TextInfo=" + "ISPL AUCTION 2024" + "\nD2|TextInfo02= " + (String) prices.keySet().toArray()[0] + " :" + " \nD3|TextInfo03=" + 
								String.join(", ", prices.get((String) prices.keySet().toArray()[0])) + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) prices.keySet().toArray()[1] + " :" + " \nD3|TextInfo03=" + String.join(", ", prices.get((String) prices.keySet().toArray()[1])) + "\nD4|Gap" + 
								"\nD2|TextInfo02= " + (String) prices.keySet().toArray()[2] + " :" + " \nD3|TextInfo03=" + String.join(", ", prices.get((String) prices.keySet().toArray()[2])) + "\nD4|Gap;");
				 }else if(prices.size() == 2) {
					 print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Scroller-MAIN*GEOMETRY*SCROLLER SET DATA "
								+ "D1|TextInfo=" + "REMAINING PURSE" + "\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[0] + " :" + " \nD3|TextInfo03=" + 
								ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[0])) + " L" + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[1] + " :" + " \nD3|TextInfo03=" + ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[1])) + " L" + "\nD4|Gap" + 
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[2] + " :" + " \nD3|TextInfo03=" + ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[2])) + " L" + "\nD4|Gap" + 
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[3] + " :" + " \nD3|TextInfo03=" + ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[3])) + " L" + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[4] + " :" + " \nD3|TextInfo03=" + ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[4])) + " L" + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[5] + " :" + " \nD3|TextInfo03=" + ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[5])) + " L" + "\nD4|Gap" 
								+ "\nD1|TextInfo=" + "ISPL AUCTION 2024" + "\nD2|TextInfo02= " + (String) prices.keySet().toArray()[0] + " :" + " \nD3|TextInfo03=" + 
								String.join(", ", prices.get((String) prices.keySet().toArray()[0])) + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) prices.keySet().toArray()[1] + " :" + " \nD3|TextInfo03=" + String.join(", ", prices.get((String) prices.keySet().toArray()[1])) + "\nD4|Gap;");
				 }else if(prices.size() == 1) {
					 print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Scroller-MAIN*GEOMETRY*SCROLLER SET DATA "
								+ "D1|TextInfo=" + "REMAINING PURSE" + "\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[0] + " :" + " \nD3|TextInfo03=" + 
								ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[0])) + " L" + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[1] + " :" + " \nD3|TextInfo03=" + ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[1])) + " L" + "\nD4|Gap" + 
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[2] + " :" + " \nD3|TextInfo03=" + ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[2])) + " L" + "\nD4|Gap" + 
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[3] + " :" + " \nD3|TextInfo03=" + ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[3])) + " L" + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[4] + " :" + " \nD3|TextInfo03=" + ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[4])) + " L" + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[5] + " :" + " \nD3|TextInfo03=" + ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[5])) + " L" + "\nD4|Gap"
								+ "\nD1|TextInfo=" + "ISPL AUCTION 2024" + "\nD2|TextInfo02= " + (String) prices.keySet().toArray()[0] + " :" + " \nD3|TextInfo03=" + 
								String.join(", ", prices.get((String) prices.keySet().toArray()[0])) + "\nD4|Gap;");
				 }
			}else if(data.equalsIgnoreCase("SOLD") && data2.equalsIgnoreCase("NOTSELECTED")) {
				if(prices.size() == 6) {
					 print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Scroller-MAIN*GEOMETRY*SCROLLER SET DATA "
							+ "D1|TextInfo=" + "ISPL AUCTION 2024" + "\nD2|TextInfo02= " + (String) prices.keySet().toArray()[0] + " :" + " \nD3|TextInfo03=" + 
							String.join(", ", prices.get((String) prices.keySet().toArray()[0])) + "\nD4|Gap" +
							"\nD2|TextInfo02= " + (String) prices.keySet().toArray()[1] + " :" + " \nD3|TextInfo03=" + String.join(", ", prices.get((String) prices.keySet().toArray()[1])) + "\nD4|Gap" + 
							"\nD2|TextInfo02= " + (String) prices.keySet().toArray()[2] + " :" + " \nD3|TextInfo03=" + String.join(", ", prices.get((String) prices.keySet().toArray()[2])) + "\nD4|Gap" + 
							"\nD2|TextInfo02= " + (String) prices.keySet().toArray()[3] + " :" + " \nD3|TextInfo03=" + String.join(", ", prices.get((String) prices.keySet().toArray()[3])) + "\nD4|Gap" +
							"\nD2|TextInfo02= " + (String) prices.keySet().toArray()[4] + " :" + " \nD3|TextInfo03=" + String.join(", ", prices.get((String) prices.keySet().toArray()[4])) + "\nD4|Gap" +
							"\nD2|TextInfo02= " + (String) prices.keySet().toArray()[5] + " :" + " \nD3|TextInfo03=" + String.join(", ", prices.get((String) prices.keySet().toArray()[5])) + "\nD4|Gap;");
				 }else if(prices.size() == 5) {
					 print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Scroller-MAIN*GEOMETRY*SCROLLER SET DATA "
								+ "D1|TextInfo=" + "ISPL AUCTION 2024" + "\nD2|TextInfo02= " + (String) prices.keySet().toArray()[0] + " :" + " \nD3|TextInfo03=" + 
								String.join(", ", prices.get((String) prices.keySet().toArray()[0])) + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) prices.keySet().toArray()[1] + " :" + " \nD3|TextInfo03=" + String.join(", ", prices.get((String) prices.keySet().toArray()[1])) + "\nD4|Gap" + 
								"\nD2|TextInfo02= " + (String) prices.keySet().toArray()[2] + " :" + " \nD3|TextInfo03=" + String.join(", ", prices.get((String) prices.keySet().toArray()[2])) + "\nD4|Gap" + 
								"\nD2|TextInfo02= " + (String) prices.keySet().toArray()[3] + " :" + " \nD3|TextInfo03=" + String.join(", ", prices.get((String) prices.keySet().toArray()[3])) + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) prices.keySet().toArray()[4] + " :" + " \nD3|TextInfo03=" + String.join(", ", prices.get((String) prices.keySet().toArray()[4])) + "\nD4|Gap;");
				 }else if(prices.size() == 4) {
					 print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Scroller-MAIN*GEOMETRY*SCROLLER SET DATA "
								+ "D1|TextInfo=" + "ISPL AUCTION 2024" + "\nD2|TextInfo02= " + (String) prices.keySet().toArray()[0] + " :" + " \nD3|TextInfo03=" + 
								String.join(", ", prices.get((String) prices.keySet().toArray()[0])) + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) prices.keySet().toArray()[1] + " :" + " \nD3|TextInfo03=" + String.join(", ", prices.get((String) prices.keySet().toArray()[1])) + "\nD4|Gap" + 
								"\nD2|TextInfo02= " + (String) prices.keySet().toArray()[2] + " :" + " \nD3|TextInfo03=" + String.join(", ", prices.get((String) prices.keySet().toArray()[2])) + "\nD4|Gap" + 
								"\nD2|TextInfo02= " + (String) prices.keySet().toArray()[3] + " :" + " \nD3|TextInfo03=" + String.join(", ", prices.get((String) prices.keySet().toArray()[3])) + "\nD4|Gap;");
				 }else if(prices.size() == 3) {
					 print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Scroller-MAIN*GEOMETRY*SCROLLER SET DATA "
								+ "D1|TextInfo=" + "ISPL AUCTION 2024" + "\nD2|TextInfo02= " + (String) prices.keySet().toArray()[0] + " :" + " \nD3|TextInfo03=" + 
								String.join(", ", prices.get((String) prices.keySet().toArray()[0])) + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) prices.keySet().toArray()[1] + " :" + " \nD3|TextInfo03=" + String.join(", ", prices.get((String) prices.keySet().toArray()[1])) + "\nD4|Gap" + 
								"\nD2|TextInfo02= " + (String) prices.keySet().toArray()[2] + " :" + " \nD3|TextInfo03=" + String.join(", ", prices.get((String) prices.keySet().toArray()[2])) + "\nD4|Gap;");
				 }else if(prices.size() == 2) {
					 print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Scroller-MAIN*GEOMETRY*SCROLLER SET DATA "
								+ "D1|TextInfo=" + "ISPL AUCTION 2024" + "\nD2|TextInfo02= " + (String) prices.keySet().toArray()[0] + " :" + " \nD3|TextInfo03=" + 
								String.join(", ", prices.get((String) prices.keySet().toArray()[0])) + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) prices.keySet().toArray()[1] + " :" + " \nD3|TextInfo03=" + String.join(", ", prices.get((String) prices.keySet().toArray()[1])) + "\nD4|Gap;");
				 }else if(prices.size() == 1) {
					 print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Scroller-MAIN*GEOMETRY*SCROLLER SET DATA "
								+ "D1|TextInfo=" + "ISPL AUCTION 2024" + "\nD2|TextInfo02= " + (String) prices.keySet().toArray()[0] + " :" + " \nD3|TextInfo03=" + 
								String.join(", ", prices.get((String) prices.keySet().toArray()[0])) + "\nD4|Gap;");
				 }
			}else if(data.equalsIgnoreCase("PURSE") && data2.equalsIgnoreCase("NOTSELECTED")) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Scroller-MAIN*GEOMETRY*SCROLLER SET DATA "
						+ "D1|TextInfo=" + "REMAINING PURSE" + "\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[0] + " :" + " \nD3|TextInfo03=" + 
						ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[0])) + " L" + "\nD4|Gap" +
						"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[1] + " :" + " \nD3|TextInfo03=" + ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[1])) + " L" + "\nD4|Gap" + 
						"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[2] + " :" + " \nD3|TextInfo03=" + ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[2])) + " L" + "\nD4|Gap" + 
						"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[3] + " :" + " \nD3|TextInfo03=" + ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[3])) + " L" + "\nD4|Gap" +
						"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[4] + " :" + " \nD3|TextInfo03=" + ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[4])) + " L" + "\nD4|Gap" +
						"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[5] + " :" + " \nD3|TextInfo03=" + ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[5])) + " L" + "\nD4|Gap;");
			}
			
		}
	}
	
	public void populateRtmAvailable(boolean is_this_updating,PrintWriter print_writer,String viz_scene, Auction match,AuctionService auctionService, String session_selected_broadcaster) throws InterruptedException 
	{
		int row = 0,total = 0,rtmUsed =0;
		
		if(is_this_updating == false) {
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + 
					"PLAYER AUCTION 2025" + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + 
					"PURSE" + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + 
					"REMAINING" + ";");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead01 " + 
					"" + ";");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead02 " + 
					"RTM AVAILABLE" + ";");
			
//			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTotal " + 
//					"100" + ";");
		}
		
		for(int i=0; i <= match.getTeam().size()-1; i++) {
			if(match.getPlayers() != null) {
				for(int j=0; j <= match.getPlayers().size()-1; j++) {
					if(match.getPlayers().get(j).getTeamId() == match.getTeam().get(i).getTeamId()) {
						row = row + match.getPlayers().get(j).getSoldForPoints();
						total = total + 1;
					}
				}
			}
			
			for(Player auc : match.getPlayers()) {
				if(match.getTeam().get(i).getTeamId() == auc.getTeamId() && auc.getSoldOrUnsold().equalsIgnoreCase(AuctionUtil.RTM)) {
					rtmUsed++;
				}
			}
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSquadSize0" + (i+1) + " " + 
					(Integer.valueOf(auctionService.getTeams().get(match.getTeam().get(i).getTeamId()-1).getTeamTotalRTM()) - rtmUsed)+";");
			rtmUsed = 0;
//			if(match.getTeam().get(i).getTeamId() == 1 || match.getTeam().get(i).getTeamId() == 2) {
//				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName0" + (i+1) + " " + 
//						auctionService.getTeams().get(match.getTeam().get(i).getTeamId()-1).getTeamName3() + ";");
//			}else {
//				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName0" + (i+1) + " " + 
//						auctionService.getTeams().get(match.getTeam().get(i).getTeamId()-1).getTeamName2() + ";");
//			}
			
			if(is_this_updating == false) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName0" + (i+1) + " " + 
						auctionService.getTeams().get(match.getTeam().get(i).getTeamId()-1).getTeamName2() + ";");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName0" + (i+1) + " " + 
						auctionService.getTeams().get(match.getTeam().get(i).getTeamId()-1).getTeamName3() + ";");
				
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogoBase0" + (i+1) + " " + logo_base_path + 
						auctionService.getTeams().get(match.getTeam().get(i).getTeamId()-1).getTeamName4() + AuctionUtil.PNG_EXTENSION + ";");
				
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogo0" + (i+1) + " " + logo_path + 
						auctionService.getTeams().get(match.getTeam().get(i).getTeamId()-1).getTeamName4() + AuctionUtil.PNG_EXTENSION + ";");
			}
			
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPurse0" + (i+1) + " " + 
					ConvertToLakh((Integer.valueOf(match.getTeam().get(i).getTeamTotalPurse()) - row)) + ";");
			row = 0;
			total = 0;
		}
		
		if(is_this_updating == false) {
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 71.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
		}
	}
	
	public void populateSlotsRemaining(boolean is_this_updating,PrintWriter print_writer,String viz_scene, Auction match,AuctionService auctionService, String session_selected_broadcaster) throws InterruptedException 
	{
		int row = 0,total = 0,squadSize =0;
		
		if(is_this_updating == false) {
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + 
					"PLAYER AUCTION 2025" + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + 
					"PURSE" + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + 
					"REMAINING" + ";");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead01 " + 
					"" + ";");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead02 " + 
					"SLOTS REMAINING" + ";");
			
//			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTotal " + 
//					"100" + ";");
		}
		
		for(int i=0; i <= match.getTeam().size()-1; i++) {
			if(match.getPlayers() != null) {
				for(int j=0; j <= match.getPlayers().size()-1; j++) {
					if(match.getPlayers().get(j).getTeamId() == match.getTeam().get(i).getTeamId()) {
						row = row + match.getPlayers().get(j).getSoldForPoints();
						total = total + 1;
					}
				}
			}
			
			for(Player auc : match.getPlayers()) {
				if(match.getTeam().get(i).getTeamId() == auc.getTeamId()) {
					squadSize++;
				}
			}
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSquadSize0" + (i+1) + " " + 
					(16-squadSize) + ";");
			squadSize = 0;
			
//			if(match.getTeam().get(i).getTeamId() == 1 || match.getTeam().get(i).getTeamId() == 2) {
//				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName0" + (i+1) + " " + 
//						auctionService.getTeams().get(match.getTeam().get(i).getTeamId()-1).getTeamName3() + ";");
//			}else {
//				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName0" + (i+1) + " " + 
//						auctionService.getTeams().get(match.getTeam().get(i).getTeamId()-1).getTeamName2() + ";");
//			}
			
			if(is_this_updating == false) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName0" + (i+1) + " " + 
						auctionService.getTeams().get(match.getTeam().get(i).getTeamId()-1).getTeamName2() + ";");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName0" + (i+1) + " " + 
						auctionService.getTeams().get(match.getTeam().get(i).getTeamId()-1).getTeamName3() + ";");
				
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogoBase0" + (i+1) + " " + logo_base_path + 
						auctionService.getTeams().get(match.getTeam().get(i).getTeamId()-1).getTeamName4() + AuctionUtil.PNG_EXTENSION + ";");
				
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogo0" + (i+1) + " " + logo_path + 
						auctionService.getTeams().get(match.getTeam().get(i).getTeamId()-1).getTeamName4() + AuctionUtil.PNG_EXTENSION + ";");
			}
			
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPurse0" + (i+1) + " " + 
					ConvertToLakh((Integer.valueOf(match.getTeam().get(i).getTeamTotalPurse()) - row)) + ";");
			row = 0;
			total = 0;
		}
		
		if(is_this_updating == false) {
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 71.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
		}
	}
	
	public void populateRemainingPurse(boolean is_this_updating,PrintWriter print_writer,String viz_scene, Auction match,AuctionService auctionService, String session_selected_broadcaster) throws InterruptedException 
	{
		int row = 0,total = 0;
		
		if(is_this_updating == false) {
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + 
					"PLAYER AUCTION 2025" + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + 
					"PURSE" + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + 
					"REMAINING" + ";");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead01 " + 
					"" + ";");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead02 " + 
					"SQUAD SIZE" + ";");
			
//			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTotal " + 
//					"100" + ";");
		}
		
		for(int i=0; i <= match.getTeam().size()-1; i++) {
			if(match.getPlayers() != null) {
				for(int j=0; j <= match.getPlayers().size()-1; j++) {
					if(match.getPlayers().get(j).getTeamId() == match.getTeam().get(i).getTeamId()) {
						row = row + match.getPlayers().get(j).getSoldForPoints();
						total = total + 1;
					}
				}
			}
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSquadSize0" + (i+1) + " " + 
					total + ";");
			
//			if(match.getTeam().get(i).getTeamId() == 1 || match.getTeam().get(i).getTeamId() == 2) {
//				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName0" + (i+1) + " " + 
//						auctionService.getTeams().get(match.getTeam().get(i).getTeamId()-1).getTeamName3() + ";");
//			}else {
//				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName0" + (i+1) + " " + 
//						auctionService.getTeams().get(match.getTeam().get(i).getTeamId()-1).getTeamName2() + ";");
//			}
			
			if(is_this_updating == false) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName0" + (i+1) + " " + 
						auctionService.getTeams().get(match.getTeam().get(i).getTeamId()-1).getTeamName2() + ";");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName0" + (i+1) + " " + 
						auctionService.getTeams().get(match.getTeam().get(i).getTeamId()-1).getTeamName3() + ";");
				
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogoBase0" + (i+1) + " " + logo_base_path + 
						auctionService.getTeams().get(match.getTeam().get(i).getTeamId()-1).getTeamName4() + AuctionUtil.PNG_EXTENSION + ";");
				
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogo0" + (i+1) + " " + logo_path + 
						auctionService.getTeams().get(match.getTeam().get(i).getTeamId()-1).getTeamName4() + AuctionUtil.PNG_EXTENSION + ";");
			}
			
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPurse0" + (i+1) + " " + 
					ConvertToLakh((Integer.valueOf(match.getTeam().get(i).getTeamTotalPurse()) - row)) + ";");
			row = 0;
			total = 0;
		}
		
		if(is_this_updating == false) {
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 71.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
		}
	}
	
	public void populateRemainingPurseSingle(boolean is_this_updating,PrintWriter print_writer,String viz_scene,int team_id , Auction match,AuctionService auctionService, String session_selected_broadcaster) throws InterruptedException 
	{
		int row = 0;
		
		if(match.getPlayers() != null) {
			for(int j=0; j <= match.getPlayers().size()-1; j++) {
				if(match.getPlayers().get(j).getTeamId() == team_id) {
					row = row + match.getPlayers().get(j).getSoldForPoints();
				}
			}
		}
		
		if(is_this_updating == false) {
//			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + 
//					"ISPL PLAYER\nAUCTION" + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText01 " + 
					"PURSE REMAINING" + ";");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText02 " + 
					"100 L" + ";");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + logo_path + 
					match.getTeam().get(team_id-1).getTeamName4() + AuctionUtil.PNG_EXTENSION + ";");
		}
		
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBidPrice " + 
				ConvertToLakh((Integer.valueOf(match.getTeam().get(team_id-1).getTeamTotalPurse()) - row)) + ";");
		row = 0;
		
		
		if(is_this_updating == false) {
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 71.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
		}
	}
	
	public void populateOnlySquad(boolean is_this_updating,PrintWriter print_writer,String viz_scene,int team_id , Auction match,AuctionService auctionService, String session_selected_broadcaster) throws InterruptedException 
	{
		int row = 0;
		int remaining = 0;
		if(is_this_updating == false) {
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$PlayersGrp$1_to_4*CONTAINER SET ACTIVE 0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$PlayersGrp$5_to_8*CONTAINER SET ACTIVE 0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$PlayersGrp$9_to_12*CONTAINER SET ACTIVE 0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$PlayersGrp$13_to_16*CONTAINER SET ACTIVE 0;");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + 
					auctionService.getTeams().get(team_id - 1).getTeamName2() + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + 
					auctionService.getTeams().get(team_id - 1).getTeamName3() + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + 
					"SQUAD" + ";");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogo " + logo_path + 
					auctionService.getTeams().get(team_id - 1).getTeamName4() + AuctionUtil.PNG_EXTENSION + ";");
			
		}
		
		remaining = Integer.valueOf(auctionService.getTeams().get(team_id - 1).getTeamTotalPurse());
		
		if(match.getPlayers() != null) {
			for(int j=0; j <= match.getPlayers().size()-1; j++) {
				if(match.getPlayers().get(j).getTeamId() == team_id) {
					row = row + 1;
					remaining=remaining-match.getPlayers().get(j).getSoldForPoints();
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage0" + row + " " + 
							photo_path + auctionService.getAllPlayer().get(match.getPlayers().get(j).getPlayerId() -1).getPhotoName() + AuctionUtil.PNG_EXTENSION + ";");
					
					if(auctionService.getAllPlayer().get(match.getPlayers().get(j).getPlayerId() -1).getCategory().equalsIgnoreCase("U19")) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFrom0"+ row + " " + 
								"UNDER 19" + ";");
					}else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFrom0"+ row + " " + 
								auctionService.getAllPlayer().get(match.getPlayers().get(j).getPlayerId() -1).getCategory().toUpperCase() + ";");
					}
					if(auctionService.getAllPlayer().get(match.getPlayers().get(j).getPlayerId() -1).getIconic()!=null && auctionService.getAllPlayer().get(match.getPlayers().get(j).getPlayerId() -1).getIconic().equalsIgnoreCase(AuctionUtil.YES)) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vPlayerInfo0" + row + " 1" + ";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerInfo0" + row + " " + 
								icon_path + "Icon" + AuctionUtil.PNG_EXTENSION + ";");
						
					}else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vPlayerInfo0" + row + " 0" + ";");
					}
					
					if(auctionService.getAllPlayer().get(match.getPlayers().get(j).getPlayerId() -1).getSurname() != null) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFirstname0"+ row + " " + 
								auctionService.getAllPlayer().get(match.getPlayers().get(j).getPlayerId() -1).getFirstname() + ";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastname0"+ row + " " + 
								auctionService.getAllPlayer().get(match.getPlayers().get(j).getPlayerId() -1).getSurname() + ";");
					}else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFirstname0"+ row + " " + 
								auctionService.getAllPlayer().get(match.getPlayers().get(j).getPlayerId() -1).getFirstname() + ";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastname0"+ row + " ;");
					}
					
					if(auctionService.getAllPlayer().get(match.getPlayers().get(j).getPlayerId() - 1).getRole() != null &&
							!auctionService.getAllPlayer().get(match.getPlayers().get(j).getPlayerId() - 1).getRole().isEmpty()) {
						
						if(auctionService.getAllPlayer().get(match.getPlayers().get(j).getPlayerId()-1).getRole().toUpperCase().equalsIgnoreCase("WICKET-KEEPER")) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerRole0" + row + " " + 
									icon_path + "Keeper" + AuctionUtil.PNG_EXTENSION + ";");
						}else {
							if(auctionService.getAllPlayer().get(match.getPlayers().get(j).getPlayerId()-1).getRole().toUpperCase().equalsIgnoreCase("BATSMAN") || 
									auctionService.getAllPlayer().get(match.getPlayers().get(j).getPlayerId()-1).getRole().toUpperCase().equalsIgnoreCase("BAT/KEEPER")) {
								if(auctionService.getAllPlayer().get(match.getPlayers().get(j).getPlayerId()-1).getBatsmanStyle().toUpperCase().equalsIgnoreCase("RHB")) {
									
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerRole0" + row + " " + 
											icon_path + "Batsman" + AuctionUtil.PNG_EXTENSION + ";");
								}
								else if(auctionService.getAllPlayer().get(match.getPlayers().get(j).getPlayerId()-1).getBatsmanStyle().toUpperCase().equalsIgnoreCase("LHB")) {
									
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerRole0" + row + " " + 
											icon_path + "Batsman_Lefthand" + AuctionUtil.PNG_EXTENSION + ";");
								}
							}else if(auctionService.getAllPlayer().get(match.getPlayers().get(j).getPlayerId()-1).getRole().toUpperCase().equalsIgnoreCase("BOWLER")) {
								if(auctionService.getAllPlayer().get(match.getPlayers().get(j).getPlayerId()-1).getBowlerStyle() == null) {
									
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerRole0" + row + " " + 
											icon_path + "FastBowler" + AuctionUtil.PNG_EXTENSION + ";");
								}else {
									switch(auctionService.getAllPlayer().get(match.getPlayers().get(j).getPlayerId()-1).getBowlerStyle().toUpperCase()) {
									case "RF": case "RFM": case "RMF": case "RM": case "RSM": case "LF": case "LFM": case "LMF": case "LM":
										
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerRole0" + row + " " + 
												icon_path + "FastBowler" + AuctionUtil.PNG_EXTENSION + ";");
										break;
									case "ROB": case "RLB": case "LSL": case "WSL": case "LCH": case "RLG": case "WSR": case "LSO":
										
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerRole0" + row + " " + 
												icon_path + "SpinBowlerIcon" + AuctionUtil.PNG_EXTENSION + ";");
										break;
									}
								}
							}else if(auctionService.getAllPlayer().get(match.getPlayers().get(j).getPlayerId()-1).getRole().toUpperCase().equalsIgnoreCase("ALL-ROUNDER")) {
								if(auctionService.getAllPlayer().get(match.getPlayers().get(j).getPlayerId()-1).getBowlerStyle() == null) {
									
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerRole0" + row + " " + 
											icon_path + "FastBowlerAllrounder" + AuctionUtil.PNG_EXTENSION + ";");
								}else {
									switch(auctionService.getAllPlayer().get(match.getPlayers().get(j).getPlayerId()-1).getBowlerStyle().toUpperCase()) {
									case "RF": case "RFM": case "RMF": case "RM": case "RSM": case "LF": case "LFM": case "LMF": case "LM":
										
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerRole0" + row + " " + 
												icon_path + "FastBowlerAllrounder" + AuctionUtil.PNG_EXTENSION + ";");
										break;
									case "ROB": case "RLB": case "LSL": case "WSL": case "LCH": case "RLG": case "WSR": case "LSO":
										
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerRole0" + row + " " + 
												icon_path + "SpinBowlerAllrounder" + AuctionUtil.PNG_EXTENSION + ";");
										break;
									}
								}
							}
						}
					}else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerRole0" + row + " " + "-" + ";");
					}
					
//					if(auctionService.getAllPlayer().get(match.getPlayers().get(j).getPlayerId() - 1).getCategory().equalsIgnoreCase("FOREIGN")) {
//						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vInternational0" + row + " 1" + ";");
//					}else {
//						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vInternational0" + row + " 0" + ";");
//					}
					
					if(row == 16) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$Remaining*CONTAINER SET ACTIVE 0;");
					}else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$Remaining*CONTAINER SET ACTIVE 1;");
					}
					
					if(row >=1 && row <= 4) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$PlayersGrp$1_to_4*CONTAINER SET ACTIVE 1;");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vFirst " + (row-1) + ";");
					}
					
					if(row >=5 && row <= 8) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$PlayersGrp$5_to_8*CONTAINER SET ACTIVE 1;");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vFirst 3;");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSecond " + (row-1) + ";");
					}
					
					if(row >=9 && row <= 12) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$PlayersGrp$9_to_12*CONTAINER SET ACTIVE 1;");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vFirst 3;");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSecond 3;");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vThird " + (row-1) + ";");
					}
					
					if(row >=13 && row <= 16) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$PlayersGrp$13_to_16*CONTAINER SET ACTIVE 1;");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vFirst 3;");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSecond 3;");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vThird 3;");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vFourth " + (row-1) + ";");
					}
				}
			}
		}
		
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRemainingHead PURSE REMAINING;");

		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRemainingValue " + ConvertToLakh(remaining) + ";");

		if(is_this_updating == false) {
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 71.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
		}
	}
	
	public void populateSquad(boolean is_this_updating,PrintWriter print_writer,String viz_scene,int team_id , Auction match,AuctionService auctionService, String session_selected_broadcaster) throws InterruptedException 
	{
		int row = 0;
		int remaining = 0;
		if(is_this_updating == false) {
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$PlayersGrp$1_to_4*CONTAINER SET ACTIVE 0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$PlayersGrp$5_to_8*CONTAINER SET ACTIVE 0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$PlayersGrp$9_to_12*CONTAINER SET ACTIVE 0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$PlayersGrp$13_to_16*CONTAINER SET ACTIVE 0;");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + 
					auctionService.getTeams().get(team_id - 1).getTeamName2() + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + 
					auctionService.getTeams().get(team_id - 1).getTeamName3() + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + 
					"SQUAD" + ";");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogo " + logo_path + 
					auctionService.getTeams().get(team_id - 1).getTeamName4() + AuctionUtil.PNG_EXTENSION + ";");
			
		}
		
		remaining = Integer.valueOf(auctionService.getTeams().get(team_id - 1).getTeamTotalPurse());
		
		if(match.getPlayers() != null) {
			for(int j=0; j <= match.getPlayers().size()-1; j++) {
				if(match.getPlayers().get(j).getTeamId() == team_id) {
					row = row + 1;
					remaining=remaining-match.getPlayers().get(j).getSoldForPoints();
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage0" + row + " " + 
							photo_path + auctionService.getAllPlayer().get(match.getPlayers().get(j).getPlayerId() -1).getPhotoName() + AuctionUtil.PNG_EXTENSION + ";");
					
					if(auctionService.getAllPlayer().get(match.getPlayers().get(j).getPlayerId() -1).getCategory().equalsIgnoreCase("U19")) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFrom0"+ row + " " + 
								"UNDER 19" + ";");
					}else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFrom0"+ row + " " + 
								auctionService.getAllPlayer().get(match.getPlayers().get(j).getPlayerId() -1).getCategory().toUpperCase() + ";");
					}
					if(auctionService.getAllPlayer().get(match.getPlayers().get(j).getPlayerId() -1).getIconic()!=null && auctionService.getAllPlayer().get(match.getPlayers().get(j).getPlayerId() -1).getIconic().equalsIgnoreCase(AuctionUtil.YES)) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vPlayerInfo0" + row + " 1" + ";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerInfo0" + row + " " + 
								icon_path + "Icon" + AuctionUtil.PNG_EXTENSION + ";");
						
					}else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vPlayerInfo0" + row + " 0" + ";");
					}
					
					if(auctionService.getAllPlayer().get(match.getPlayers().get(j).getPlayerId() -1).getSurname() != null) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFirstname0"+ row + " " + 
								auctionService.getAllPlayer().get(match.getPlayers().get(j).getPlayerId() -1).getFirstname() + ";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastname0"+ row + " " + 
								auctionService.getAllPlayer().get(match.getPlayers().get(j).getPlayerId() -1).getSurname() + ";");
					}else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFirstname0"+ row + " " + 
								auctionService.getAllPlayer().get(match.getPlayers().get(j).getPlayerId() -1).getFirstname() + ";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastname0"+ row + " ;");
					}
					
					if(auctionService.getAllPlayer().get(match.getPlayers().get(j).getPlayerId() - 1).getRole() != null &&
							!auctionService.getAllPlayer().get(match.getPlayers().get(j).getPlayerId() - 1).getRole().isEmpty()) {
						
						if(auctionService.getAllPlayer().get(match.getPlayers().get(j).getPlayerId()-1).getRole().toUpperCase().equalsIgnoreCase("WICKET-KEEPER")) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerRole0" + row + " " + 
									icon_path + "Keeper" + AuctionUtil.PNG_EXTENSION + ";");
						}else {
							if(auctionService.getAllPlayer().get(match.getPlayers().get(j).getPlayerId()-1).getRole().toUpperCase().equalsIgnoreCase("BATSMAN") || 
									auctionService.getAllPlayer().get(match.getPlayers().get(j).getPlayerId()-1).getRole().toUpperCase().equalsIgnoreCase("BAT/KEEPER")) {
								if(auctionService.getAllPlayer().get(match.getPlayers().get(j).getPlayerId()-1).getBatsmanStyle().toUpperCase().equalsIgnoreCase("RHB")) {
									
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerRole0" + row + " " + 
											icon_path + "Batsman" + AuctionUtil.PNG_EXTENSION + ";");
								}
								else if(auctionService.getAllPlayer().get(match.getPlayers().get(j).getPlayerId()-1).getBatsmanStyle().toUpperCase().equalsIgnoreCase("LHB")) {
									
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerRole0" + row + " " + 
											icon_path + "Batsman_Lefthand" + AuctionUtil.PNG_EXTENSION + ";");
								}
							}else if(auctionService.getAllPlayer().get(match.getPlayers().get(j).getPlayerId()-1).getRole().toUpperCase().equalsIgnoreCase("BOWLER")) {
								if(auctionService.getAllPlayer().get(match.getPlayers().get(j).getPlayerId()-1).getBowlerStyle() == null) {
									
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerRole0" + row + " " + 
											icon_path + "FastBowler" + AuctionUtil.PNG_EXTENSION + ";");
								}else {
									switch(auctionService.getAllPlayer().get(match.getPlayers().get(j).getPlayerId()-1).getBowlerStyle().toUpperCase()) {
									case "RF": case "RFM": case "RMF": case "RM": case "RSM": case "LF": case "LFM": case "LMF": case "LM":
										
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerRole0" + row + " " + 
												icon_path + "FastBowler" + AuctionUtil.PNG_EXTENSION + ";");
										break;
									case "ROB": case "RLB": case "LSL": case "WSL": case "LCH": case "RLG": case "WSR": case "LSO":
										
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerRole0" + row + " " + 
												icon_path + "SpinBowlerIcon" + AuctionUtil.PNG_EXTENSION + ";");
										break;
									}
								}
							}else if(auctionService.getAllPlayer().get(match.getPlayers().get(j).getPlayerId()-1).getRole().toUpperCase().equalsIgnoreCase("ALL-ROUNDER")) {
								if(auctionService.getAllPlayer().get(match.getPlayers().get(j).getPlayerId()-1).getBowlerStyle() == null) {
									
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerRole0" + row + " " + 
											icon_path + "FastBowlerAllrounder" + AuctionUtil.PNG_EXTENSION + ";");
								}else {
									switch(auctionService.getAllPlayer().get(match.getPlayers().get(j).getPlayerId()-1).getBowlerStyle().toUpperCase()) {
									case "RF": case "RFM": case "RMF": case "RM": case "RSM": case "LF": case "LFM": case "LMF": case "LM":
										
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerRole0" + row + " " + 
												icon_path + "FastBowlerAllrounder" + AuctionUtil.PNG_EXTENSION + ";");
										break;
									case "ROB": case "RLB": case "LSL": case "WSL": case "LCH": case "RLG": case "WSR": case "LSO":
										
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerRole0" + row + " " + 
												icon_path + "SpinBowlerAllrounder" + AuctionUtil.PNG_EXTENSION + ";");
										break;
									}
								}
							}
						}
					}else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerRole0" + row + " " + "-" + ";");
					}
					
//					if(auctionService.getAllPlayer().get(match.getPlayers().get(j).getPlayerId() - 1).getCategory().equalsIgnoreCase("FOREIGN")) {
//						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vInternational0" + row + " 1" + ";");
//					}else {
//						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vInternational0" + row + " 0" + ";");
//					}
					
				}
			}
		}
		
		if(row == 16) {
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$Remaining*CONTAINER SET ACTIVE 0;");
		}else {
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$Remaining*CONTAINER SET ACTIVE 1;");
		}
		
		if(row >=1 && row <= 4) {
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$PlayersGrp$1_to_4*CONTAINER SET ACTIVE 1;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vFirst " + (row-1) + ";");
		}
		
		if(row >=5 && row <= 8) {
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$PlayersGrp$5_to_8*CONTAINER SET ACTIVE 1;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vFirst 3;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSecond " + (row-1) + ";");
		}
		
		if(row >=9 && row <= 12) {
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$PlayersGrp$9_to_12*CONTAINER SET ACTIVE 1;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vFirst 3;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSecond 3;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vThird " + (row-1) + ";");
		}
		
		if(row >=13 && row <= 16) {
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$PlayersGrp$13_to_16*CONTAINER SET ACTIVE 1;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vFirst 3;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSecond 3;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vThird 3;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vFourth " + (row-1) + ";");
		}
		
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRemainingHead PURSE REMAINING;");

		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRemainingValue " + ConvertToLakh(remaining) + ";");

		if(is_this_updating == false) {
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 71.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
		}
	}
	
	public void populateZone(boolean is_this_updating,PrintWriter print_writer,String viz_scene,int team_id , Auction match,AuctionService auctionService, String session_selected_broadcaster) throws InterruptedException 
	{
		int row = 0, East = 0,North = 0,West = 0,South = 0, Central = 0, U19 = 0;
		int remaining=0;
		if(is_this_updating == false) {
			for(int i = 1; i <= 18; i++) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$"+ i +"*CONTAINER SET ACTIVE 0;");
			}
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + 
					"ISPL PLAYER\nAUCTION" + ";");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$8_to_14*CONTAINER SET ACTIVE 0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vAbove14 0;");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + logo_path + 
					auctionService.getTeams().get(team_id - 1).getTeamName4() + AuctionUtil.PNG_EXTENSION + ";");
			
		}
		remaining=Integer.valueOf(auctionService.getTeams().get(team_id - 1).getTeamTotalPurse());
		
		if(match.getPlayers() != null) {
			for(int j=0; j <= match.getPlayers().size()-1; j++) {
				if(match.getPlayers().get(j).getTeamId() == team_id) {
					row = row + 1;
					if(auctionService.getAllPlayer().get(match.getPlayers().get(j).getPlayerId() -1).getCategory().equalsIgnoreCase("North Zone")) {
						North = North + 1;
					}else if(auctionService.getAllPlayer().get(match.getPlayers().get(j).getPlayerId() -1).getCategory().equalsIgnoreCase("East Zone")) {
						East = East + 1;
					}else if(auctionService.getAllPlayer().get(match.getPlayers().get(j).getPlayerId() -1).getCategory().equalsIgnoreCase("West Zone")) {
						West = West + 1;
					}else if(auctionService.getAllPlayer().get(match.getPlayers().get(j).getPlayerId() -1).getCategory().equalsIgnoreCase("Central Zone")) {
						Central = Central + 1;
					}else if(auctionService.getAllPlayer().get(match.getPlayers().get(j).getPlayerId() -1).getCategory().equalsIgnoreCase("U19")) {
						U19 = U19 + 1;
					}else if(auctionService.getAllPlayer().get(match.getPlayers().get(j).getPlayerId() -1).getCategory().equalsIgnoreCase("South Zone")) {
						South = South + 1;
					}
					remaining=remaining-match.getPlayers().get(j).getSoldForPoints();
					if(auctionService.getAllPlayer().get(match.getPlayers().get(j).getPlayerId() -1).getTicker_name() != null) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayername0"+ row + " " + 
								auctionService.getAllPlayer().get(match.getPlayers().get(j).getPlayerId() -1).getTicker_name() + ";");
					}else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayername0"+ row + " " + 
								auctionService.getAllPlayer().get(match.getPlayers().get(j).getPlayerId() -1).getFirstname() + ";");
					}
					
					if(auctionService.getAllPlayer().get(match.getPlayers().get(j).getPlayerId() - 1).getRole() != null &&
							!auctionService.getAllPlayer().get(match.getPlayers().get(j).getPlayerId() - 1).getRole().isEmpty()) {
						
						if(auctionService.getAllPlayer().get(match.getPlayers().get(j).getPlayerId() - 1).getRole().equalsIgnoreCase("BATSMAN")) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerRole0" + row + " " + 
									"BATTER" + ";");
						}else {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerRole0" + row + " " + 
									auctionService.getAllPlayer().get(match.getPlayers().get(j).getPlayerId() - 1).getRole().toUpperCase() + ";");
						} 	
			
					}else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerRole0" + row + " " + "-" + ";");
					}
					
//					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerRole0"+ row + " " + auctionService.getAllPlayer().get(match.getPlayers().get(j).getPlayerId() - 1).getRole() + ";");
					
					if(auctionService.getAllPlayer().get(match.getPlayers().get(j).getPlayerId() - 1).getCategory().equalsIgnoreCase("FOREIGN")) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vInternational0" + row + " 1" + ";");
					}else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vInternational0" + row + " 0" + ";");
					}
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$"+ row +"*CONTAINER SET ACTIVE 1;");
					if(row >=8) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$8_to_14*CONTAINER SET ACTIVE 1;");
					}
					if(row >14) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vAbove14 1 ;");
					}
					
				}
			}
		}
		
		System.out.println("East = " + East + " West = " + West + " North = " + North + " South = " + South + " Central = " + Central + " U19 = " + U19);
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRemainingValue " + ConvertToLakh(remaining) + ";");

		if(is_this_updating == false) {
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out STOP;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 71.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.png;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			TimeUnit.SECONDS.sleep(1);
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*Out SHOW 0.0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
		}
	}
	public void populateIdent(PrintWriter print_writer, String viz_scene, String session_selected_broadcaster)
	{
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + logo_path + "ISPL" 
				+ AuctionUtil.PNG_EXTENSION + ";");
		
	}
	
	public void AnimateInGraphics(PrintWriter print_writer, String whichGraphic) throws InterruptedException
	{
		
		switch(whichGraphic) {
		case "FFPLAYERPROFILE":	
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*In START \0");
			break;
		
		case "RESET":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Reset START \0");
			break;
		
		}	
	}	
	public void AnimateOutGraphics(PrintWriter print_writer, String whichGraphic)
	{
		switch(whichGraphic.toUpperCase()) {
		
		case "FFPLAYERPROFILE":
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Out START \0");
			break;
		
		}	
	}
	
	public void processAnimation(PrintWriter print_writer, String animationName,String animationCommand, String which_broadcaster,int which_layer)
	{
		switch(which_broadcaster.toUpperCase()) {
		case "HANDBALL":  case "ISPL":
			switch(which_layer) {
			case 1:
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*" + animationName + " " + animationCommand + ";");
				break;
				
			case 2:
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*" + animationName + " " + animationCommand + ";");
				//print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*LOOP START;");	
				break;
			}
			break;
		}
		
	}
	
	public String toString() {
		return "Doad [status=" + status + ", slashOrDash=" + slashOrDash + "]";
	}
	
	public static String ConvertToLakh(double num) {
		String str=String.format("%.1f", num/100000);
		if(str.contains(".")) {
			if(str.endsWith(".00")) {
				return  str.substring(0, str.length()-3) ;
			}else {
				return  str;
			}
		}
		return str;
	}
	
	public static String getBowlerType(String BowlerType) {
		switch(BowlerType.toUpperCase()) {
		case "RF": case "RFM": case "RMF": case "RM": case "RSM": case "LF": case "LFM": case "LMF": case "LM":
			return "PACE";
		case "ROB": case "RLB": case "RLG": case "WSR": case "LSL": case "WSL": case "LCH":  case "LSO":
			return "SPIN";
		}
		return "";
	}
}