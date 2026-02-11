package com.auction.broadcaster;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.auction.containers.Data;
import com.auction.containers.Scene;
import com.auction.model.Player;
import com.auction.model.PlayerCount;
import com.auction.model.Squad;
import com.auction.model.Statistics;
import com.auction.model.StatsType;
import com.auction.model.Team;
import com.auction.service.AuctionService;
import com.auction.model.Auction;
import com.auction.util.AuctionFunctions;
import com.auction.util.AuctionUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PSL extends Scene{

	private String status;
	private String side2ValueToProcess = "";
	private String slashOrDash = "-";
	public String session_selected_broadcaster = "PSL";
	public Data data = new Data();
	public String which_graphics_onscreen = "BG",previous_Scene = "";
	public int current_layer = 2;
	private String logo_path = "C:\\Images\\Auction\\Logos\\";
	private String logobw_path = "C:\\Images\\Auction\\Logos_BW\\";
	private String logo_base_path = "C:\\Images\\Auction\\Logo_Base\\";
	
	private String base_path2 = "D:\\Everest_ISPL_Auction_2024\\COLOUR-2\\";
	private String base_path1 = "D:\\DOAD_In_House_Everest\\Everest_Cricket\\Everest_PSL_Auction_2026\\Base1\\";
	private String photo_path  = "C:\\Images\\Auction\\Photos\\";
	private String text1_path = "D:\\DOAD_In_House_Everest\\Everest_Cricket\\Everest_PSL_Auction_2026\\Text1\\";
	private String text2_path = "D:\\Everest_ISPL_Auction_2024\\TEXT_COLOUR-2\\";
	
	private String icon_path = "C:\\Images\\Auction\\Icons\\";
	private int value1 = 0;
	private int size=0,count=0;
	public int zoneSize = 0, current_index = 0;
	private boolean update_gfx = false;
	List<String> data_str = new ArrayList<String>();
	List<Player> squad = new ArrayList<Player>();
	public String whichDataType;
	
	public PSL() {
		super();
	}

	public PSL(String scene_path, String which_Layer) {
		super(scene_path, which_Layer);
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public Data updateData(Scene scene, Auction auction, AuctionService auctionService, PrintWriter print_writer) throws Exception
	{
		if(update_gfx == true) {
			if(which_graphics_onscreen.equalsIgnoreCase("PLAYERPROFILE")) {
				populatePlayerProfile(true, print_writer, "", data.getPlayer_id(),auctionService.getAllStats(),auction,
						auctionService,auctionService.getAllPlayer(), session_selected_broadcaster);
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
			}else if(which_graphics_onscreen.equalsIgnoreCase("RTM_SQUAD")) {
				populateRtmSquad(true,print_writer, "", auction,auctionService, session_selected_broadcaster);
			}else if(which_graphics_onscreen.equalsIgnoreCase("TOP_SOLD_TEAMS")) {
				populateTopSoldTeams(true,print_writer, "",value1, auction,auctionService, session_selected_broadcaster);
			}
//			else if(which_graphics_onscreen.equalsIgnoreCase("TOP_FIVE_SOLD")) {
//				populateTopFiveSold(true,print_writer, "", auction,auctionService, session_selected_broadcaster);
//			}else if(which_graphics_onscreen.equalsIgnoreCase("TOP_FIVE_SOLD_TEAMS")) {
//				populateTopFiveSoldTeams(true,print_writer, "",value1, auction,auctionService, session_selected_broadcaster);
//			}
//			else if(which_graphics_onscreen.equalsIgnoreCase("SQUAD")) {
//				populateSquad(true,print_writer, "",value1, auction,auctionService,session_selected_broadcaster);
//			}
		}
		
		return data;
	}
	
	public Object ProcessGraphicOption(String whatToProcess, Auction auction, Auction current_bid, AuctionService auctionService,
			PrintWriter print_writer, List<Scene> scenes, String valueToProcess) throws Exception {
		
		switch (whatToProcess.toUpperCase()) {
		case "RULE_CHANGEON":
			processAnimation(print_writer, "In", "COUNTINUE", session_selected_broadcaster, current_layer);
			which_graphics_onscreen = "FFFOUR_POINTERS";
			 break;

		case "POPULATE-FF-PLAYERPROFILE": case "POPULATE-FF-PLAYERPROFILE_CHNAGE_ON":
		case "POPULATE-SQUAD": case "POPULATE-REMAINING_PURSE_ALL": case "POPULATE-SINGLE_PURSE": case "POPULATE-TOP_SOLD": case "POPULATE-CRAWL": 
		case "POPULATE-SQUAD_ROLE": case "POPULATE-FF_IDENT": case "POPULATE-ONLY_SQUAD": case "POPULATE-RTM_AVAILABLE": case "POPULATE-SLOTS_REMAINING":	
		case "POPULATE-FF_ICONIC_PLAYERS":  case "POPULATE-FF_EIGHTPOINTERS": case "POPULATE-FF_POINTERS": case "POPULATE-FF_LAST_PLAYERS": case "POPULATE-RTM_SQUAD": 
		case "POPULATE-TOP_SOLD_TEAMS": case "POPULATE-TOP_FIVE_SOLD": case "POPULATE-TOP_FIVE_SOLD_TEAMS": case "POPULATE-ZONE_PLAYERS_STATS": case "POPULATE-CANCEL": 
		case "POPULATE-TOP_15_SOLD": case "POPULATE-TOP_15_SOLD_TEAMS": case "BASE_LOAD": case "POPULATE-FF_BG_IDENT": case "POPULATE-FF_RETAIN_PLAYERS":
		case "POPULATE-RETAINPLAYERS": case "POPULATE-SQUAD20":
			switch (session_selected_broadcaster.toUpperCase()) {
			case "HANDBALL": case "ISPL": case "PSL":
				switch(whatToProcess.toUpperCase()) {
				case "POPULATE-FF-PLAYERPROFILE_CHNAGE_ON":
					break;
				case "POPULATE-FF_BG_IDENT":
					current_layer = 5 - current_layer;
					break;
				case "POPULATE-FF-PLAYERPROFILE":
					scenes.get(0).setWhich_layer("1");
					scenes.get(0).setScene_path("D:/DOAD_In_House_Everest/Everest_Cricket/Everest_PSL_Auction_2026/Scenes/Player_Profile.sum");
					scenes.get(0).scene_load(print_writer,session_selected_broadcaster);
					break;
				default:
					print_writer.println("LAYER4*EVEREST*STAGE*DIRECTOR*In SHOW 71.0;");
					current_layer = 5 - current_layer;
					scenes.get(2).setWhich_layer(String.valueOf(current_layer));
					scenes.get(2).setScene_path(valueToProcess.split(",")[0]);
					scenes.get(2).scene_load(print_writer,session_selected_broadcaster);
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
				case "BASE_LOAD":
					print_writer.println("LAYER4*EVEREST*STAGE*DIRECTOR*In SHOW 71.0;");
					scenes.get(1).setWhich_layer("4");																																															
					scenes.get(1).setScene_path("D:/DOAD_In_House_Everest/Everest_Cricket/Everest_PSL_Auction_2026/Scenes/BG.sum");
					scenes.get(1).scene_load(print_writer,session_selected_broadcaster);
					which_graphics_onscreen = "BASE_LOAD";
					break;
				case "POPULATE-CANCEL":
					current_layer = 5 - current_layer;
					previous_Scene = which_graphics_onscreen;
					break;
				
				case "POPULATE-SQUAD_ROLE":
					populateSquadRole(print_writer, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]), auction,auctionService,session_selected_broadcaster);
					break;
				case "POPULATE-ONLY_SQUAD":
					value1 = Integer.valueOf(valueToProcess.split(",")[1]);
					populateOnlySquad(false,print_writer, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]), auction,auctionService,session_selected_broadcaster);
					break;
				case "POPULATE-SQUAD20":
					value1 = Integer.valueOf(valueToProcess.split(",")[1]);
					populateSquad20(false,print_writer, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]), auction,auctionService,session_selected_broadcaster);
					break;
				case "POPULATE-SQUAD":
					value1 = Integer.valueOf(valueToProcess.split(",")[1]);
					populateSquad(false,print_writer, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]), auction,auctionService,session_selected_broadcaster);
					break;
				case "POPULATE-RTM_SQUAD":
					populateRtmSquad(false,print_writer, valueToProcess.split(",")[0], auction,auctionService, session_selected_broadcaster);
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
				case "POPULATE-FF-PLAYERPROFILE_CHNAGE_ON":
					whichDataType = valueToProcess.split(",")[0];
					changeOnPlayerProfileData(print_writer, 2, auctionService.getAllStats(), auction, auctionService, 
							auctionService.getAllPlayer(), session_selected_broadcaster);
					break;
				case "POPULATE-FF-PLAYERPROFILE":
					data.setPlayer_id(Integer.valueOf(valueToProcess.split(",")[1]));
					whichDataType = valueToProcess.split(",")[2];
					populatePlayerProfile(false,print_writer,valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]),
							auctionService.getAllStats(),auction,auctionService,auctionService.getAllPlayer(), session_selected_broadcaster);
					break;
				case "POPULATE-TOP_SOLD_TEAMS":
					value1 = Integer.valueOf(valueToProcess.split(",")[1]);
					populateTopSoldTeams(false,print_writer, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]), auction,auctionService, session_selected_broadcaster);
					break;
				case "POPULATE-TOP_15_SOLD_TEAMS":
					value1 = Integer.valueOf(valueToProcess.split(",")[1]);
					populateTop15SoldTeams(false,print_writer, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]), auction,auctionService, session_selected_broadcaster);
					break;
				case "POPULATE-TOP_FIVE_SOLD_TEAMS":
					value1 = Integer.valueOf(valueToProcess.split(",")[1]);
					populateTopFiveSoldTeams(false,print_writer, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]), auction,auctionService, session_selected_broadcaster);
					break;
				case "POPULATE-RETAINPLAYERS":
					populateRetainPlayers(print_writer,valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]) ,  auction,  auctionService, 
							 session_selected_broadcaster);
					break;
//				case "POPULATE-FF_EIGHTPOINTERS":
//					populateRULES(print_writer,session_selected_broadcaster);
//					break;
				case "POPULATE-ZONE_PLAYERS_STATS":	
					if(!which_graphics_onscreen.equalsIgnoreCase("ZONE-PLAYER_STATS") || 
							(!valueToProcess.equalsIgnoreCase("undefined") && !side2ValueToProcess.equalsIgnoreCase(valueToProcess.split(",")[1]))) {
						squad.clear();
						current_index = 0;
						squad = auction.getPlayersList().stream().filter(ply -> ply.getCategory().equalsIgnoreCase(valueToProcess.split(",")[1]))
							    .collect(Collectors.toList());
						Iterator<Player> squadIterator = squad.iterator();

						while (squadIterator.hasNext()) {
						    Player sq = squadIterator.next();						    
						    for (Player ply : auction.getPlayers()) {
						        if (sq.getPlayerId() == ply.getPlayerId() &&
						            (ply.getSoldOrUnsold().equalsIgnoreCase(AuctionUtil.RTM) || ply.getSoldOrUnsold().equalsIgnoreCase(AuctionUtil.SOLD) ||
						            		ply.getSoldOrUnsold().equalsIgnoreCase("RETAIN") || ply.getSoldOrUnsold().equalsIgnoreCase(AuctionUtil.UNSOLD))) {						                
						            squadIterator.remove();
						            break;
						        }
						    }
						}
						zoneSize = squad.size();
						side2ValueToProcess = valueToProcess.split(",")[1];
					}
					populateZoneSquad(print_writer, side2ValueToProcess, auction, auctionService, session_selected_broadcaster);
					break;
				case "POPULATE-TOP_SOLD":
					populateTopSold(false,print_writer, valueToProcess.split(",")[0], auction,auctionService, session_selected_broadcaster);
					break;
				case "POPULATE-TOP_15_SOLD":
					count = 5;
					populateTop15Sold(false,print_writer, valueToProcess.split(",")[0], auction,auctionService, session_selected_broadcaster);
					break;
				case "POPULATE-TOP_FIVE_SOLD":
					populateTopFiveSold(false,print_writer, valueToProcess.split(",")[0], auction,auctionService, session_selected_broadcaster);
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
				case "POPULATE-FF_RETAIN_PLAYERS":
					populateRetainPlayers(print_writer, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]) , auction,auctionService, session_selected_broadcaster);
					break;
				
				}
				//return JSONObject.fromObject(this_doad).toString();
			}
		System.out.println("whattoprocess " + whatToProcess);
		case "ANIMATE-OUT": case "CLEAR-ALL": case "ANIMATE-IN-PLAYERPROFILE": case "ANIMATE-IN-SQUAD": case "ANIMATE-IN-REMAINING_PURSE_ALL": case "ANIMATE-IN-SINGLE_PURSE":
		case "ANIMATE-IN-TOP_SOLD": case "ANIMATE-IN-CRAWL": case "ANIMATE-IN-FF_IDENT": case "ANIMATE-IN-RTM": case "ANIMATE-IN-SLOTS": case "ANIMATE-IN-ONLY_SQUAD":
		case "ANIMATE-IN-FF_ICONIC_PLAYERS": case "ANIMATE-IN-FF_EIGHTPOINTERS": case "ANIMATE-IN-FF_POINTERS": case "ANIMATE-IN-FF_LAST_PLAYERS": case "ANIMATE-IN-RTM_SQUAD": 
		case "ANIMATE-IN-TOP_SOLD_TEAMS": case "ANIMATE-IN-TOP_FIVE_SOLD": case "ANIMATE-IN-TOP_FIVE_SOLD_TEAMS": case "ANIMATE-IN-ZONE-PLAYER_STATS": case "ANIMATE-IN-SQUAD20":
		case "ANIMATE-IN-TOP_15_SOLD": case "TOP_15_AUCTION_CHANGEON": case "ANIMATE-IN-FF_BG_IDENT": case "ANIMATE-IN-FF_RETAIN_PLAYERS": case "ANIMATE-IN-PLAYERPROFILE_CHANGE_ON":
			switch (session_selected_broadcaster.toUpperCase()) {
			case "HANDBALL": case "ISPL": case "PSL":
				switch (whatToProcess.toUpperCase()) {
				case "TOP_15_AUCTION_CHANGEON":
					if(size <=count) {
						return "Can't proceed Further";
					}else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In CONTINUE;");
						count= count + 5;	
					}
					break;
				case "ANIMATE-IN-PLAYERPROFILE_CHANGE_ON":
					if(which_graphics_onscreen == "PLAYERPROFILE") {
						print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Sub_Head_Change START;");
						print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Bottom_Detail_Change START;");
						TimeUnit.MILLISECONDS.sleep(2000);
						changeOnPlayerProfileData(print_writer, 1, auctionService.getAllStats(), auction, auctionService, 
								auctionService.getAllPlayer(), session_selected_broadcaster);
						print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Sub_Head_Change SHOW 0.0;");
						print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Bottom_Detail_Change SHOW 0.0;");
						
						which_graphics_onscreen = "PLAYERPROFILE";
					}
					break;
				case "ANIMATE-IN-PLAYERPROFILE":
					update_gfx = true;
					
					print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In START;");
					TimeUnit.SECONDS.sleep(2);
					print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*LOOP START;");
					
					if(current_bid.getClock().getMatchTimeStatus().equalsIgnoreCase("START")) {
						processAnimation(print_writer, "Timer_In", "START", session_selected_broadcaster, 1);
					}
					
					which_graphics_onscreen = "PLAYERPROFILE";
					break;
					
				case "ANIMATE-IN-SQUAD": case "ANIMATE-IN-REMAINING_PURSE_ALL": case "ANIMATE-IN-FF_ICONIC_PLAYERS": case "ANIMATE-IN-FF_RETAIN_PLAYERS":
				case "ANIMATE-IN-SINGLE_PURSE": case "ANIMATE-IN-TOP_SOLD": case "ANIMATE-IN-CRAWL": case "ANIMATE-IN-FF_IDENT": case "ANIMATE-IN-FF_EIGHTPOINTERS": 
				case "ANIMATE-IN-FF_POINTERS": case "ANIMATE-IN-FF_LAST_PLAYERS": case "ANIMATE-IN-RTM": case "ANIMATE-IN-SLOTS": case "ANIMATE-IN-ONLY_SQUAD": 	
				case "ANIMATE-IN-RTM_SQUAD": case "ANIMATE-IN-TOP_15_SOLD": case "ANIMATE-IN-TOP_SOLD_TEAMS": case "ANIMATE-IN-TOP_FIVE_SOLD": 
				case "ANIMATE-IN-ZONE-PLAYER_STATS": case "ANIMATE-IN-TOP_FIVE_SOLD_TEAMS": case "ANIMATE-IN-FF_BG_IDENT": case "ANIMATE-IN-SQUAD20":
					System.out.println("whattoprocess2 " + whatToProcess);
					System.out.println("whichon scree" + which_graphics_onscreen);
					update_gfx = true;
					
					if(which_graphics_onscreen != "" && which_graphics_onscreen != "BG") {
						switch(which_graphics_onscreen) {
						case "ZONE-PLAYER_STATS":
							processAnimation(print_writer, "Out", "START", session_selected_broadcaster,(5-current_layer));
							break;
						case "SQUAD":
							processAnimation(print_writer, "Out", "START", session_selected_broadcaster,(5-current_layer));
							break;
						case "REMAINING_PURSE_ALL": case "SINGLE_PURSE": case "TOP_SOLD": case "RTM_SQUAD": case "TOP_SOLD_TEAMS":
						case "FF_IDENT": case "RTM": case "SLOTS": case "ONLY_SQUAD": case "FF_ICONIC_PLAYERS": case "TOP_FIVE_SOLD":
						case "TOP_FIVE_SOLD_TEAMS": case "TOP_15_SOLD": case "FF_LAST_PLAYERS": case "FF_POINTERS": case "FF_EIGHTPOINTERS":
						case "FF_RETAIN_PLAYERS":
							processAnimation(print_writer, "Out", "START", session_selected_broadcaster,(5-current_layer));
							break;
						}
						TimeUnit.MILLISECONDS.sleep(2250);
						processAnimation(print_writer, "In", "SHOW 0.0", session_selected_broadcaster,(5-current_layer));
						processAnimation(print_writer, "Out", "SHOW 0.0", session_selected_broadcaster,(5-current_layer));
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
				case "ANIMATE-IN-ONLY_SQUAD":
					print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
					print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*LOOP START;");
					which_graphics_onscreen = "ONLY_SQUAD";
					break;
				case "ANIMATE-IN-SQUAD20":
					print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
					print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*LOOP START;");
					which_graphics_onscreen = "SQUAD20";
					break;
				case "ANIMATE-IN-SQUAD":
					print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
					print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*LOOP START;");
					which_graphics_onscreen = "SQUAD";
					break;
				case "ANIMATE-IN-RTM_SQUAD":
					print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
					print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*LOOP START;");
					which_graphics_onscreen = "RTM_SQUAD";
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
				case "ANIMATE-IN-TOP_15_SOLD":
					print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
					print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*LOOP START;");
					which_graphics_onscreen = "TOP_15_SOLD";
					break;
				case "ANIMATE-IN-TOP_SOLD_TEAMS":
					print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
					print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*LOOP START;");
					which_graphics_onscreen = "TOP_SOLD_TEAMS";
					break;
				case "ANIMATE-IN-TOP_FIVE_SOLD_TEAMS":
					print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
					print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*LOOP START;");
					which_graphics_onscreen = "TOP_FIVE_SOLD_TEAMS";
					break;
				case "ANIMATE-IN-ZONE-PLAYER_STATS":
					print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
					print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*LOOP START;");
					which_graphics_onscreen = "ZONE-PLAYER_STATS";
					break;
				case "ANIMATE-IN-TOP_SOLD":
					print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
					print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*LOOP START;");
					which_graphics_onscreen = "TOP_SOLD";
					break;
				case "ANIMATE-IN-TOP_FIVE_SOLD":
					print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
					print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*LOOP START;");
					which_graphics_onscreen = "TOP_FIVE_SOLD";
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
				case "ANIMATE-IN-FF_RETAIN_PLAYERS":
					print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
					print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*LOOP START;");
					which_graphics_onscreen = "FF_RETAIN_PLAYERS";
					break;
				case "ANIMATE-IN-FF_LAST_PLAYERS":
					print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
					print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*LOOP START;");
					which_graphics_onscreen = "FF_LAST_PLAYERS";
					break;
				case "ANIMATE-IN-FF_POINTERS":
					print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
					print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*LOOP START;");
					which_graphics_onscreen = "FF_POINTERS";
					break;
				case "ANIMATE-IN-FF_EIGHTPOINTERS":
					print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*In START;");
					print_writer.println("LAYER" + current_layer + "*EVEREST*STAGE*DIRECTOR*LOOP START;");
					which_graphics_onscreen = "FF_EIGHTPOINTERS";
					break;
				case "CLEAR-ALL":
					print_writer.println("LAYER1*EVEREST*SINGLE_SCENE CLEAR;");
					print_writer.println("LAYER2*EVEREST*SINGLE_SCENE CLEAR;");
					print_writer.println("LAYER3*EVEREST*SINGLE_SCENE CLEAR;");
					print_writer.println("LAYER4*EVEREST*SINGLE_SCENE CLEAR;");
					which_graphics_onscreen = "";
					
					scenes.get(0).scene_load(print_writer, session_selected_broadcaster);
					scenes.get(1).scene_load(print_writer, session_selected_broadcaster);
					print_writer.println("LAYER4*EVEREST*STAGE*DIRECTOR*In START;");
					print_writer.println("LAYER4*EVEREST*STAGE*DIRECTOR*LOOP START;");
					which_graphics_onscreen = "BG";
					break;
				
				case "ANIMATE-OUT":
					switch(which_graphics_onscreen) {
					case "PLAYERPROFILE":
						processAnimation(print_writer, "Out", "START", session_selected_broadcaster,1);
						processAnimation(print_writer, "Result", "START", session_selected_broadcaster,1);
						TimeUnit.MILLISECONDS.sleep(1000);
						processAnimation(print_writer, "In", "SHOW 0.0", session_selected_broadcaster,1);
						processAnimation(print_writer, "Out", "SHOW 0.0", session_selected_broadcaster,1);
						processAnimation(print_writer, "Result", "SHOW 0.0", session_selected_broadcaster,1);
						break;
					case "SQUAD": 
						processAnimation(print_writer, "Out", "START", session_selected_broadcaster,2);
						processAnimation(print_writer, "Out", "START", session_selected_broadcaster,3);
						TimeUnit.SECONDS.sleep(4);
						processAnimation(print_writer, "In", "SHOW 0.0", session_selected_broadcaster,2);
						processAnimation(print_writer, "Out", "SHOW 0.0", session_selected_broadcaster,2);
						
						processAnimation(print_writer, "In", "SHOW 0.0", session_selected_broadcaster,3);
						processAnimation(print_writer, "Out", "SHOW 0.0", session_selected_broadcaster,3);
						break;
					
					case "REMAINING_PURSE_ALL": case "SINGLE_PURSE": case "TOP_SOLD": case "FF_IDENT": case "RTM": case "SLOTS": case "ONLY_SQUAD": case "SQUAD20":
					case "RTM_SQUAD": case "TOP_SOLD_TEAMS": case "TOP_FIVE_SOLD": case "TOP_FIVE_SOLD_TEAMS": case"ZONE-PLAYER_STATS": case "TOP_15_SOLD":
					case "BASE_LOAD": case "FF_ICONIC_PLAYERS": case "FF_LAST_PLAYERS": case "FF_POINTERS": case "FFFOUR_POINTERS": case "FF_EIGHTPOINTERS": case "FF_RETAIN_PLAYERS":
						processAnimation(print_writer, "Out", "START", session_selected_broadcaster, current_layer);
						TimeUnit.MILLISECONDS.sleep(2000);
						processAnimation(print_writer, "In", "SHOW 0.0", session_selected_broadcaster, current_layer);
						processAnimation(print_writer, "Out", "SHOW 0.0", session_selected_broadcaster, current_layer);
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
			break;
		}
		return null;
	}
	private void populateZoneSquad(PrintWriter print_writer,String ZoneName, Auction auction, AuctionService auctionService,
			String session_selected_broadcaster2) {
		int row = 0;
		
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogo " +logo_path + "ISPL" + AuctionUtil.PNG_EXTENSION + ";");
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgBase02 " + base_path2 +  "ISPL" + AuctionUtil.PNG_EXTENSION + ";");
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgBase01 " + base_path1 +  "ISPL" + AuctionUtil.PNG_EXTENSION + ";");
		
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " +"ISPL SEASON 3"+ ";");	
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " +"PLAYER AUCTION"+ ";");
		
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + ZoneName.replace("U19", "UNDER 19").toUpperCase() 
				+ " - PLAYERS TO BE AUCTIONED" + ";");
		
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamColor " + base_path1 +  "ISPL" + AuctionUtil.PNG_EXTENSION + ";");
		
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectTopRight "+"0;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$Squad_PurseRem*CONTAINER SET ACTIVE 0;");
		for(int i=1;i<=16;i++) {
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectDataType0" +i+" 0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$PlayersGrp$" + i + "*CONTAINER SET ACTIVE 0;");	
		}
		
		for(int k = current_index; k<= squad.size()-1 ;k++) {
			Player plyr = squad.get(k);
			row = row + 1;
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectDataType0" + row + " 2;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$PlayersGrp$" + row + "*CONTAINER SET ACTIVE 1;");	
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFrom0" +row+" " + "" + ";");

			if(plyr.getSurname() != null) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFirstname0" +row+" "+plyr.getFirstname()+ ";");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastname0" +row+" " +plyr.getSurname()+ ";");
			}else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFirstname0" +row+" "+plyr.getFirstname()+ ";");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastname0" +row+" " + "" + ";");
			}
			 
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage0" +row+" "+ photo_path + 
					plyr.getPhotoName() + AuctionUtil.PNG_EXTENSION + ";");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vPlayerInfo0" + row + " 0" + ";");
			
			if(plyr.getRole() != null &&
					!plyr.getRole().isEmpty()) {
				
				if(plyr.getRole().toUpperCase().equalsIgnoreCase("WICKET-KEEPER")) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerRole0" + row + " " + 
							icon_path + "Keeper" + AuctionUtil.PNG_EXTENSION + ";");
				}else {
					if(plyr.getRole().toUpperCase().equalsIgnoreCase("BATSMAN") || 
							plyr.getRole().toUpperCase().equalsIgnoreCase("BAT/KEEPER")) {
						if(plyr.getBatsmanStyle().toUpperCase().equalsIgnoreCase("RHB")) {
							
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerRole0" + row + " " + 
									icon_path + "Batsman" + AuctionUtil.PNG_EXTENSION + ";");
						}
						else if(plyr.getBatsmanStyle().toUpperCase().equalsIgnoreCase("LHB")) {
							
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerRole0" + row + " " + 
									icon_path + "Batsman_Lefthand" + AuctionUtil.PNG_EXTENSION + ";");
						}
					}else if(plyr.getRole().toUpperCase().equalsIgnoreCase("BOWLER")) {
						if(plyr.getBowlerStyle() == null) {
							
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerRole0" + row + " " + 
									icon_path + "FastBowler" + AuctionUtil.PNG_EXTENSION + ";");
						}else {
							switch(plyr.getBowlerStyle().toUpperCase()) {
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
					}else if(plyr.getRole().toUpperCase().equalsIgnoreCase("ALL-ROUNDER")) {
						if(plyr.getBowlerStyle() == null) {
							
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerRole0" + row + " " + 
									icon_path + "FastBowlerAllrounder" + AuctionUtil.PNG_EXTENSION + ";");
						}else {
							switch(plyr.getBowlerStyle().toUpperCase()) {
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
			
			if(row == 16) {
				break;
			}
		}
		current_index =(current_index + 16);
	}

	private void populateIconicPlayers(PrintWriter print_writer,String viz_scene, Auction auction,AuctionService auctionService, String session_selected_broadcaster) throws InterruptedException {
		
		
		int row = 0;
		List<Player> top_sold = new ArrayList<Player>();
		
		if(auction.getPlayers() != null) {
			top_sold = auction.getPlayers();
		}
		
		Collections.sort(top_sold,new AuctionFunctions.PlayerStatsComparator());
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " +"RETENTIONS"+ ";");
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " +" "+ ";");
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader HBL PSL PLAYER AUCTION;");	
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$HeaderGrp$Iconic*CONTAINER SET ACTIVE 0;");
		for(Player player : top_sold) {
			if(player.getSoldOrUnsold().equalsIgnoreCase("RETAIN")) {
				row = row + 1;
				if(row <= 6) {
					Player plyr = auction.getPlayersList().stream().filter(pyr -> pyr.getPlayerId() == player.getPlayerId()).findAny().orElse(null);
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectType0" + row + " " + "1" + ";");
					
					if(plyr.getSurname() != null) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFirstname0"+ row + " " + 
								plyr.getFirstname() + ";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastname0"+ row + " " + 
								plyr.getSurname() + ";");
					}else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFirstname0"+ row + " " + 
								plyr.getFirstname() + ";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastname0"+ row + " ;");
					}
					
					if(plyr.getRole() != null &&
							!plyr.getRole().isEmpty()) {
						
						if(plyr.getRole().toUpperCase().equalsIgnoreCase("WICKET-KEEPER")) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerRole0" + row + " " + 
									icon_path + "Keeper" + AuctionUtil.PNG_EXTENSION + ";");
						}else {
							if(plyr.getRole().toUpperCase().equalsIgnoreCase("BATSMAN") || 
									plyr.getRole().toUpperCase().equalsIgnoreCase("BAT/KEEPER")) {
								if(plyr.getBatsmanStyle().toUpperCase().equalsIgnoreCase("RHB")) {
									
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerRole0" + row + " " + 
											icon_path + "Batsman" + AuctionUtil.PNG_EXTENSION + ";");
								}
								else if(plyr.getBatsmanStyle().toUpperCase().equalsIgnoreCase("LHB")) {
									
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerRole0" + row + " " + 
											icon_path + "Batsman_Lefthand" + AuctionUtil.PNG_EXTENSION + ";");
								}
							}else if(plyr.getRole().toUpperCase().equalsIgnoreCase("BOWLER")) {
								if(plyr.getBowlerStyle() == null) {
									
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerRole0" + row + " " + 
											icon_path + "FastBowler" + AuctionUtil.PNG_EXTENSION + ";");
								}else {
									switch(plyr.getBowlerStyle().toUpperCase()) {
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
							}else if(plyr.getRole().toUpperCase().equalsIgnoreCase("ALL-ROUNDER")) {
								if(plyr.getBowlerStyle() == null) {
									
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerRole0" + row + " " + 
											icon_path + "FastBowlerAllrounder" + AuctionUtil.PNG_EXTENSION + ";");
								}else {
									switch(plyr.getBowlerStyle().toUpperCase()) {
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
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFrom0"+ row + " " + 
							plyr.getCategory().toUpperCase() + ";");
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo0" + row + " " + 
	    					logobw_path + auctionService.getTeams().get(plyr.getTeamId() - 1).getTeamName4() + AuctionUtil.PNG_EXTENSION + ";");
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage0" + row + " " + 
	    					photo_path + plyr.getPhotoName() + AuctionUtil.PNG_EXTENSION + ";");
					
					
					
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamColor0" + row + " " + 
	    					base_path1 + auctionService.getTeams().get(plyr.getTeamId() - 1).getTeamName4() + AuctionUtil.PNG_EXTENSION + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBidPrice0" + row + " "
							+ AuctionFunctions.ConvertToLakh(player.getSoldForPoints()) + ";");
					
					
					
				}
			}
		}
	}
	
	private void populateRetainPlayers(PrintWriter print_writer,String viz_scene,int id , Auction auction, AuctionService auctionService, 
			String session_selected_broadcaster) throws InterruptedException {
		
		int row = 0;
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$HeaderGrp$Iconic*CONTAINER SET ACTIVE 0;");
		for(int i = 1; i<=6;i++) {
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectType0" + i + " 2;");

			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectDataType0" + i + " 0;");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vPlayerInfo0" + i + " 0;");
			
		}
		
		
		for(Team tm : auctionService.getTeams()) {
		
			if(tm.getTeamId() == id) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogo_01 " + 
						logo_path + tm.getTeamBadge() + AuctionUtil.PNG_EXTENSION + ";");
				
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + tm.getTeamName1() + " - " + "RETENTIONS" + ";");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName HBL PSL PLAYER AUCTION;");
				
				
				
			}
		}
		for(Player player: auction.getPlayers()) {
			if(player.getSoldOrUnsold().equalsIgnoreCase("RETAIN") && player.getTeamId() == id) {
				
				row = row + 1;
				
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectType0" + row + " 1;");
				Player plyr = auction.getPlayersList().stream().filter(pyr -> pyr.getPlayerId() == player.getPlayerId()).findAny().orElse(null);
				
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamColor01 " + base_path1 + 
						auctionService.getTeams().get(plyr.getTeamId() - 1).getTeamBadge() + AuctionUtil.PNG_EXTENSION + ";");
				
				
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamTextColor01 " + text1_path + 
						auctionService.getTeams().get(plyr.getTeamId() - 1).getTeamBadge() + AuctionUtil.PNG_EXTENSION + ";");
				
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage0" + row + " " + 
    					photo_path + plyr.getPhotoName() + AuctionUtil.PNG_EXTENSION + ";");
				
				
				if(plyr.getNationality() != null) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFrom0"+ row + " " + 
							plyr.getNationality() + ";");
				}
				
				if(plyr.getSurname() != null) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFirstname0"+ row + " " + 
							plyr.getFirstname() + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastname0"+ row + " " + 
							plyr.getSurname() + ";");
				}else {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFirstname0"+ row + " " + 
							plyr.getFirstname() + ";");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastname0"+ row + " ;");
				}
				
				
				if(plyr.getNationality().equalsIgnoreCase("PAKISTAN")) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vOverseas0" + row + " 0;");
				}else {
					print_writer.println("LAYER" + current_layer + "EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vOverseas0" + row + " 1;");
				}
				
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo0" + row + " " + 
						logo_path + auctionService.getTeams().get(plyr.getTeamId() - 1).getTeamName4() + AuctionUtil.PNG_EXTENSION + ";");
				
				
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBidPrice0" + row + " "
						+ AuctionFunctions.formatAmountInCrore(player.getSoldForPoints()).split(",")[0] + ";");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tUnit0" + row + " "
						+ AuctionFunctions.formatAmountInCrore(player.getSoldForPoints()).split(",")[1] + ";");
				if(plyr.getRole() != null &&
						!plyr.getRole().isEmpty()) {
					
					if(plyr.getRole().toUpperCase().equalsIgnoreCase("WICKET-KEEPER")) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerRole0" + row + " " + 
								icon_path + "Keeper" + AuctionUtil.PNG_EXTENSION + ";");
					}else {
						if(plyr.getRole().toUpperCase().equalsIgnoreCase("BATSMAN") || plyr.getRole().toUpperCase().equalsIgnoreCase("BAT/KEEPER")) {
							if(plyr.getBatsmanStyle().toUpperCase().equalsIgnoreCase("RHB")) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerRole0" + row + " " + 
										icon_path + "Batsman" + AuctionUtil.PNG_EXTENSION + ";");
							}
							else if(plyr.getBatsmanStyle().toUpperCase().equalsIgnoreCase("LHB")) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerRole0" + row + " " + 
										icon_path + "Batsman_Lefthand" + AuctionUtil.PNG_EXTENSION + ";");
							}
						}else if(plyr.getRole().toUpperCase().equalsIgnoreCase("BOWLER")) {
							if(plyr.getBowlerStyle() == null) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerRole0" + row + " " + 
										icon_path + "FastBowler" + AuctionUtil.PNG_EXTENSION + ";");
							}else {
								switch(plyr.getBowlerStyle().toUpperCase()) {
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
						}else if(plyr.getRole().toUpperCase().equalsIgnoreCase("ALL-ROUNDER")) {
							if(plyr.getBowlerStyle() == null) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerRole0" + row + " " + 
										icon_path + "FastBowlerAllrounder" + AuctionUtil.PNG_EXTENSION + ";");
							}else {
								switch(plyr.getBowlerStyle().toUpperCase()) {
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
			}
		}
	}

	public void populatePlayerProfile(boolean is_this_updating,PrintWriter print_writer,String viz_scene, int playerId,List<Statistics> stats, Auction auction,
			AuctionService auctionService, List<Player> plr, String session_selected_broadcaster) throws InterruptedException 
	{

		print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + "" + ";");
		print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + "HBL PSL PLAYER AUCTION" + ";");
		
		print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBaePointsHead " + "BASE PRICE" + ";");
		
		print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectLogo 0 ;");
		
		if(auction.getPlayers() != null && auction.getPlayers().size() > 0) {
			if(data.isPlayer_sold_or_unsold() == false) {
				for(int i=auction.getPlayers().size()-1; i >= 0; i--) {
					if(playerId == auction.getPlayers().get(i).getPlayerId()) {
						if(auction.getPlayers().get(i).getSoldOrUnsold().equalsIgnoreCase(AuctionUtil.SOLD)||
								auction.getPlayers().get(i).getSoldOrUnsold().equalsIgnoreCase(AuctionUtil.RTM)) {

							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSoldUnsold 1 ;");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBidPrice " + 
									AuctionFunctions.formatRawAmount(auction.getPlayers().get(i).getSoldForPoints()).split(",")[0] + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSoldUnit " + 
									AuctionFunctions.formatRawAmount(auction.getPlayers().get(i).getSoldForPoints()).split(",")[1] + ";");
							
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgBase02_02 " + base_path2 + 
									auctionService.getTeams().get(auction.getPlayers().get(i).getTeamId() - 1).getTeamName4() + AuctionUtil.PNG_EXTENSION + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgBase01_02 " + base_path1 + 
									auctionService.getTeams().get(auction.getPlayers().get(i).getTeamId() - 1).getTeamName4() + AuctionUtil.PNG_EXTENSION + ";");
							
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogo_02 " + logo_path + 
									auctionService.getTeams().get(auction.getPlayers().get(i).getTeamId() - 1).getTeamName4() + AuctionUtil.PNG_EXTENSION + ";");
							
							if((auction.getPlayers().get(i).getSoldOrUnsold().equalsIgnoreCase(AuctionUtil.RTM))){
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$All$PricesGRp$Sold_Undold$Sold$RTM*CONTAINER SET ACTIVE 1;");
							}else {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$All$PricesGRp$Sold_Undold$Sold$RTM*CONTAINER SET ACTIVE 0;");
							}
							
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tUnsold "+ "SOLD;");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHead02 " + "" + ";");
							TimeUnit.MILLISECONDS.sleep(200);
							print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Result START;");
							print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Sub_Head_Change START;");
							data.setPlayer_sold_or_unsold(true);
							
							TimeUnit.MILLISECONDS.sleep(1000);
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHead " + "" + ";");
							print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Sub_Head_Change SHOW 0.0;");
							
						}else if(auction.getPlayers().get(i).getSoldOrUnsold().equalsIgnoreCase(AuctionUtil.UNSOLD)) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tUnsold " + "UNSOLD" + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSoldUnsold 0 ;");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHead02 " + "" + ";");
							print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Result START;");
							data.setPlayer_sold_or_unsold(true);
							
							TimeUnit.MILLISECONDS.sleep(1000);
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHead " + "" + ";");
							print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Sub_Head_Change SHOW 0.0;");
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
			
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPurse0" + (i+1) + " " + 
					AuctionFunctions.formatRawAmount(remaining) + ";");
			remaining=0;
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogo0" + (i+1) + " " + logo_path + 
					auctionService.getTeams().get(auction.getTeam().get(i).getTeamId()-1).getTeamName4() + AuctionUtil.PNG_EXTENSION + ";");
			
		}
		if(is_this_updating == false) {
			
			if(which_graphics_onscreen != "" && which_graphics_onscreen != "BG") {
				update_gfx = false;
			}
			int totalrtm = 0;
			
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Result SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Timer_In SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Sub_Head_Change SHOW 0.0;");
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*Bottom_Detail_Change SHOW 0.0;");
			
			data.setPlayer_sold_or_unsold(false);
			
			Player player = auctionService.getAllPlayer().stream().filter(plyr -> plyr.getPlayerId() == playerId).findAny().orElse(null);
			
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vPlayerPic 0;");
			
			if(player.getBasePrice().equalsIgnoreCase("42000")) {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBasePrice " + "4.200" + ";");
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBaseUnit " + "  CR" + ";");
			}else if(player.getBasePrice().equalsIgnoreCase("22000")) {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBasePrice " + "2.200" + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBaseUnit " + "  CR" + ";");
			}else if(player.getBasePrice().equalsIgnoreCase("11000")) {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBasePrice " + "1.100" + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBaseUnit " + "  CR" + ";");
			}else if(player.getBasePrice().equalsIgnoreCase("6000")) {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBasePrice " + "60" + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBaseUnit " + "  L" + ";");
			}
			
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFirstName " + player.getFirstname() + ";");
			if(player.getSurname() != null) {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastName " + player.getSurname() + ";");
			}else {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastName " + "" + ";");
			}
			
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRole " + player.getRole().replace("Batsman", "Batter").toUpperCase() + ";");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tCategory " + player.getNationality().toUpperCase() + ";");
			
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + photo_path + 
					player.getPhotoName() + AuctionUtil.PNG_EXTENSION + ";");
			
			if(player.getIconic() !=null && player.getIconic().equalsIgnoreCase(AuctionUtil.YES)) {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectIcon 1;");
			}else {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectIcon 2;");
			}
			
			if(player.getNationality().equalsIgnoreCase("PAKISTAN")) {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vOverseas 0;");
			}else {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vOverseas 1;");
			}
			
			if(player.getLastYearTeam() != null) {
				for(Player auc : auction.getPlayers()) {
					if(player.getLastYearTeam() == auc.getTeamId() && auc.getSoldOrUnsold().equalsIgnoreCase(AuctionUtil.RTM)) {
						totalrtm++;
					}
				}
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$All$PlayerDetails$Side01$Prefererd*CONTAINER SET ACTIVE " + (totalrtm < 2 ? "1" :"0") + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue " + 
						auctionService.getTeams().get(player.getLastYearTeam()-1).getTeamName1() + ";");
			}else {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$All$PlayerDetails$Side01$Prefererd*CONTAINER SET ACTIVE 0;");
			}
			
			switch (whichDataType) {
			case "PSL CAREER": case "T20I CAREER": case "T20 CAREER":
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHead " + 
						(whichDataType.equalsIgnoreCase("PSL CAREER") ? "PSL CAREER" : whichDataType.equalsIgnoreCase("T20I CAREER") ? "IT20 CAREER" : "T20 CAREER") + ";");
				
				StatsType statsType = auctionService.getStatsTypes().stream().filter(stype -> stype.getStats_short_name().equalsIgnoreCase(whichDataType)).findAny().orElse(null);
				Statistics stat = auctionService.getAllStats().stream().filter(st-> st.getPlayer_id() == playerId && statsType.getStats_id() == st.getStats_type_id()).findAny().orElse(null);
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tAgeHead02 " + "MATCHES" + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tAge02 " + 
						((stat.getMatches().equalsIgnoreCase("0") ? "-" : stat.getMatches()))+ ";");
				
				switch (player.getRole().toUpperCase()) {
				case "BATSMAN": case "BAT/KEEPER": case "WICKET-KEEPER":
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatStyleHead02 " + "RUNS" + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlStyleHead02 " + "STRIKE RATE" + ";");
					
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatStyle02 " + 
							((stat.getRuns().equalsIgnoreCase("0") ? "-" : stat.getRuns())) + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlStyle02 " + 
							((stat.getStrikeRate().equalsIgnoreCase("0") ? "-" : stat.getStrikeRate())) + ";");
					break;
				case "BOWLER":
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatStyleHead02 " + "WICKETS" + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlStyleHead02 " + "ECONOMY" + ";");
					
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatStyle02 " + 
							((stat.getWickets().equalsIgnoreCase("0") ? "-" : stat.getWickets())) + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlStyle02 " + 
							((stat.getEconomy().equalsIgnoreCase("0") ? "-" : stat.getEconomy())) + ";");
					break;
				case "ALL-ROUNDER":
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatStyleHead02 " + "RUNS" + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlStyleHead02 " + "WICKETS" + ";");
					
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatStyle02 " + 
							((stat.getRuns().equalsIgnoreCase("0") ? "-" : stat.getRuns())) + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlStyle02 " + 
							((stat.getWickets().equalsIgnoreCase("0") ? "-" : stat.getWickets())) + ";");
					break;
				}
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET 1 0;");
				break;

			default:
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHead " + "" + ";");
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tAgeHead02 AGE;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tAge02 " + (player.getAge() != null ? player.getAge() : "-") + ";");
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatStyleHead02 " + "BAT STYLE" + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlStyleHead02 " + "BOWL STYLE" + ";");
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatStyle02 " + (player.getBatsmanStyle() != null 
						? player.getBatsmanStyle() : "-") + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlStyle02 " + (player.getBowlerStyle() != null 
						? player.getBowlerStyle() : "-") + ";");

				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET 1 1;");
				break;
			}
			
		}
		
	}
	
	public void changeOnPlayerProfileData(PrintWriter print_writer, int whichSide,List<Statistics> stats, Auction auction, AuctionService auctionService, 
			List<Player> plr, String session_selected_broadcaster) throws InterruptedException 
	{
		int totalrtm = 0;
		Player player = auctionService.getAllPlayer().stream().filter(plyr -> plyr.getPlayerId() == data.getPlayer_id()).findAny().orElse(null);
		
		if(whichSide == 1) {
			if(player.getLastYearTeam() != null) {
				for(Player auc : auction.getPlayers()) {
					if(player.getLastYearTeam() == auc.getTeamId() && auc.getSoldOrUnsold().equalsIgnoreCase(AuctionUtil.RTM)) {
						totalrtm++;
					}
				}
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$All$PlayerDetails$Side0" + whichSide + "$Prefererd*CONTAINER SET ACTIVE " 
						+ (totalrtm < 2 ? "1" : "0") + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue " + 
						auctionService.getTeams().get(player.getLastYearTeam()-1).getTeamName1() + ";");
			}else {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$All$PlayerDetails$Side0" + whichSide + "$Prefererd*CONTAINER SET ACTIVE 0;");
			}
			
			switch (whichDataType) {
			case "ISPL S-1": case "ISPL S-2":
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHead " + 
						(whichDataType.equalsIgnoreCase("ISPL S-1") ? "ISPL SEASON 1" : "ISPL SEASON 2") + ";");
				
				StatsType statsType = auctionService.getStatsTypes().stream().filter(stype -> stype.getStats_short_name().equalsIgnoreCase(whichDataType)).findAny().orElse(null);
				Statistics stat = auctionService.getAllStats().stream().filter(st-> st.getPlayer_id() == data.getPlayer_id() && statsType.getStats_id() == st.getStats_type_id()).findAny().orElse(null);
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tAgeHead " + "MATCHES" + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tAge " + 
						((stat.getMatches().equalsIgnoreCase("0") ? "-" : stat.getMatches()))+ ";");
				
				switch (player.getRole().toUpperCase()) {
				case "BATSMAN": case "BAT/KEEPER": case "WICKET-KEEPER":
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatStyleHead " + "RUNS" + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlStyleHead " + "STRIKE RATE" + ";");
					
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatStyle " + 
							((stat.getRuns().equalsIgnoreCase("0") ? "-" : stat.getRuns())) + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlStyle " + 
							((stat.getStrikeRate().equalsIgnoreCase("0") ? "-" : stat.getStrikeRate())) + ";");
					break;
				case "BOWLER":
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatStyleHead " + "WICKETS" + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlStyleHead " + "ECONOMY" + ";");
					
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatStyle " + 
							((stat.getWickets().equalsIgnoreCase("0") ? "-" : stat.getWickets())) + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlStyle " + 
							((stat.getEconomy().equalsIgnoreCase("0") ? "-" : stat.getEconomy())) + ";");
					break;
				case "ALL-ROUNDER":
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatStyleHead " + "RUNS" + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlStyleHead " + "WICKETS" + ";");
					
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatStyle " + 
							((stat.getRuns().equalsIgnoreCase("0") ? "-" : stat.getRuns())) + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlStyle " + 
							((stat.getWickets().equalsIgnoreCase("0") ? "-" : stat.getWickets())) + ";");
					break;
				}
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET 1 0;");
				break;

			default:
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHead " + player.getPhotoName() + ";");
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tAgeHead AGE;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tAge " + (player.getAge() != null ? player.getAge() : "-") + ";");
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatStyleHead " + "BAT STYLE" + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlStyleHead " + "BOWL STYLE" + ";");
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatStyle " + (player.getBatsmanStyle() != null 
						? player.getBatsmanStyle() : "-") + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlStyle " + (player.getBowlerStyle() != null 
						? player.getBowlerStyle() : "-") + ";");

				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET 1 0;");
				break;
			}
		}else {
			if(player.getLastYearTeam() != null) {
				for(Player auc : auction.getPlayers()) {
					if(player.getLastYearTeam() == auc.getTeamId() && auc.getSoldOrUnsold().equalsIgnoreCase(AuctionUtil.RTM)) {
						totalrtm++;
					}
				}
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$All$PlayerDetails$Side0" + whichSide + "$Prefererd*CONTAINER SET ACTIVE " 
						+ (totalrtm < 2 ? "1" :"0") + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue02 " + 
						auctionService.getTeams().get(player.getLastYearTeam()-1).getTeamName1() + ";");
			}else {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$All$PlayerDetails$Side0" + whichSide + "$Prefererd*CONTAINER SET ACTIVE 0;");
			}
			
			switch (whichDataType) {
			case "ISPL S-1": case "ISPL S-2":
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHead02 " + 
						(whichDataType.equalsIgnoreCase("ISPL S-1") ? "ISPL SEASON 1" : "ISPL SEASON 2") + ";");
				
				StatsType statsType = auctionService.getStatsTypes().stream().filter(stype -> stype.getStats_short_name().equalsIgnoreCase(whichDataType)).findAny().orElse(null);
				Statistics stat = auctionService.getAllStats().stream().filter(st-> st.getPlayer_id() == data.getPlayer_id() && statsType.getStats_id() == st.getStats_type_id()).findAny().orElse(null);
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tAgeHead02 " + "MATCHES" + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tAge02 " + 
						((stat.getMatches().equalsIgnoreCase("0") ? "-" : stat.getMatches()))+ ";");
				
				switch (player.getRole().toUpperCase()) {
				case "BATSMAN": case "BAT/KEEPER": case "WICKET-KEEPER":
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatStyleHead02 " + "RUNS" + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlStyleHead02 " + "STRIKE RATE" + ";");
					
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatStyle02 " + 
							((stat.getRuns().equalsIgnoreCase("0") ? "-" : stat.getRuns())) + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlStyle02 " + 
							((stat.getStrikeRate().equalsIgnoreCase("0") ? "-" : stat.getStrikeRate())) + ";");
					break;
				case "BOWLER":
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatStyleHead02 " + "WICKETS" + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlStyleHead02 " + "ECONOMY" + ";");
					
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatStyle02 " + 
							((stat.getWickets().equalsIgnoreCase("0") ? "-" : stat.getWickets())) + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlStyle02 " + 
							((stat.getEconomy().equalsIgnoreCase("0") ? "-" : stat.getEconomy())) + ";");
					break;
				case "ALL-ROUNDER":
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatStyleHead02 " + "RUNS" + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlStyleHead02 " + "WICKETS" + ";");
					
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatStyle02 " + 
							((stat.getRuns().equalsIgnoreCase("0") ? "-" : stat.getRuns())) + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlStyle02 " + 
							((stat.getWickets().equalsIgnoreCase("0") ? "-" : stat.getWickets())) + ";");
					break;
				}
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET 1 0;");
				break;

			default:
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHead02 " + player.getPhotoName() + ";");
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tAgeHead02 AGE;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tAge02 " + (player.getAge() != null ? player.getAge() : "-") + ";");
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatStyleHead02 " + "BAT STYLE" + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlStyleHead02 " + "BOWL STYLE" + ";");
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatStyle02 " + (player.getBatsmanStyle() != null 
						? player.getBatsmanStyle() : "-") + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlStyle02 " + (player.getBowlerStyle() != null 
						? player.getBowlerStyle() : "-") + ";");

				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET 1 0;");
				break;
			}
		}
		
		
	}
	
	public static String getNumber(int NumberType) {
		switch(NumberType) {
		case 1: 
			return "1st";
		case 2: 
			return "2nd";
		case 3: 
			return "3rd";
		case 4: 
			return "4th";
		case 5: 
			return "5th";
		case 6: 
			return "6th";
		case 7: 
			return "7th";
		case 8: 
			return "8th";
		case 9: 
			return "9th";
		case 10: 
			return "10th";	
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
	
	public void populateTopSoldTeams(boolean is_this_updating,PrintWriter print_writer,String viz_scene,int team_id, Auction auction,AuctionService auctionService, String session_selected_broadcaster) throws InterruptedException 
	{
		int row = 0;
		List<Player> top_sold = new ArrayList<Player>();
		
		Team team = auctionService.getTeams().stream().filter(tm->tm.getTeamId() == Integer.valueOf(team_id)).findAny().orElse(null);
		
		if(auction.getPlayers() != null) {
			for(Player plyr : auction.getPlayers()) {
				if(team.getTeamId() == plyr.getTeamId()) {
					if(plyr.getSoldOrUnsold().equalsIgnoreCase("SOLD") || plyr.getSoldOrUnsold().equalsIgnoreCase("RTM")) {
						top_sold.add(plyr);
					}
				}	
			}
		}
		
		Collections.sort(top_sold,new AuctionFunctions.PlayerStatsComparator());
		
		if(is_this_updating == false) {
			
			if(which_graphics_onscreen != "" && which_graphics_onscreen != "BG") {
				update_gfx = false;
			}
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + team.getTeamName2() + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + team.getTeamName3() + ";");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + "TOP BUYS" + ";");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead01 " + "PLAYER" + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead02 " + "ZONE" + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead03 " + "PRICE" + ";");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogo " + 
					logo_path + team.getTeamName4() + AuctionUtil.PNG_EXTENSION + ";");
			
			for(int i=1; i<= 10; i++) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$Player$" + getNumber(i) + "*CONTAINER SET ACTIVE 0;");
			}
		}
		
		for(int m=0; m<= top_sold.size() - 1; m++) {
			if(!top_sold.get(m).getSoldOrUnsold().equalsIgnoreCase("BID")) {
				row = row + 1;
	        	if(row <= 10) {
	        		
	        		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$Player$" + getNumber(row) + "*CONTAINER SET ACTIVE 1;");
	        		
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
	        		
	        		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeam0" + row + " " + 
	        				auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getNationality().toUpperCase() + ";");
	        		
	        		if(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getIconic().equalsIgnoreCase(AuctionUtil.YES)) {
	        			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vIconic0" + row + " 1;");
	        		}else {
	        			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vIconic0" + row + " 0;");
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
	        		
	        		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPurse0" + row + " " + 
	        				AuctionFunctions.ConvertToLakh(top_sold.get(m).getSoldForPoints()) + ";");
	        		
//	        		if(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId()-1).getCategory().equalsIgnoreCase("FOREIGN")) {
//	    				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vInternational0" + row + " 1 ;");
//	    			}else {
//	    				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vInternational0" + row + " 0 ;");
//	    			}
	        	}
			}
		}
	}
	
	public void populateTopFiveSoldTeams(boolean is_this_updating,PrintWriter print_writer,String viz_scene,int team_id, Auction auction,AuctionService auctionService, String session_selected_broadcaster) throws InterruptedException 
	{
		int row = 0;
		List<Player> top_sold = new ArrayList<Player>();
		
		Team team = auctionService.getTeams().stream().filter(tm->tm.getTeamId() == Integer.valueOf(team_id)).findAny().orElse(null);
		
		if(auction.getPlayers() != null && auctionService.getSquads() != null) {
			for(Player plyr : auction.getPlayers()) {
				if(team.getTeamId() == plyr.getTeamId()) {
					if(plyr.getSoldOrUnsold().equalsIgnoreCase("SOLD") || plyr.getSoldOrUnsold().equalsIgnoreCase("RTM")) {
						top_sold.add(plyr);
					}
				}	
			}
			Set<Integer> squadPlayerIds = auctionService.getSquads().stream().map(Squad::getPlayer_id).collect(Collectors.toSet());
		    top_sold.removeIf(player -> squadPlayerIds.contains(player.getPlayerId()));
		}
		
		Collections.sort(top_sold,new AuctionFunctions.PlayerStatsComparator());
		
		if(is_this_updating == false) {
			
			if(which_graphics_onscreen != "" && which_graphics_onscreen != "BG") {
				update_gfx = false;
			}
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$HeaderGrp$Iconic*CONTAINER SET ACTIVE 0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + "TOP BUYS" + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + "" + ";");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + team.getTeamName1() + ";");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogoBase " + 
					logo_base_path + team.getTeamBadge() + AuctionUtil.PNG_EXTENSION + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogo_01 " + 
					logo_path + team.getTeamBadge() + AuctionUtil.PNG_EXTENSION + ";");
			
			//base for logo
    		
    		
    		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgBase01 " + base_path1 +  
    				team.getTeamName4() + AuctionUtil.PNG_EXTENSION + ";");
			
			for(int i=1; i<= 5; i++) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$PlayersGrp$1_to_5$" + i + "*CONTAINER SET ACTIVE 0;");
			}
		}
		
		for(int m=0; m<= top_sold.size() - 1; m++) {
			if(!top_sold.get(m).getSoldOrUnsold().equalsIgnoreCase("BID")) {
				row = row + 1;
	        	if(row <= 5) {
	        		
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$PlayersGrp$1_to_5$" + row + "*CONTAINER SET ACTIVE 1;");

	        		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectLogo0" + row + " 0;");
	        		
	        		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage0" + row + " " + 
        					photo_path + auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getPhotoName() + AuctionUtil.PNG_EXTENSION + ";");
	        		
	        		if(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getSurname() != null) {
	        			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFirstname0" + row + " " + 
	        					auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getFirstname() + ";");
	        			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastname0" + row + " " + 
	        					auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getSurname() + ";");
					}else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFirstname0" + row + " " + 
								auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getFirstname() + ";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastname0" + row + " " + ";");
					}
	        		
//	        		if(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getCategory().equalsIgnoreCase("U19")) {
//	        			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFrom0" + row + " " + 
//		        				"UNDER 19" + ";");
//	        		}else {
//	        			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFrom0" + row + " " + 
//		        				auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getCategory().toUpperCase() + ";");
//	        		}
	        		
	        		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFrom0" + row + " " + 
	        				auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getNationality().toUpperCase() + ";");
	        		
	        		if(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getIconic().equalsIgnoreCase(AuctionUtil.YES)) {
	        			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vPlayerInfo0" + row + " 1;");
	        		}else {
	        			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vPlayerInfo0" + row + " 0;");
	        		}
	        		
	        		if(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getNationality().equalsIgnoreCase("PAKISTAN")) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vOverseas0" + row + " 0;");
					}else {
						print_writer.println("LAYER" + current_layer + "EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vOverseas0" + row + " 1;");
					}
	        		
	        		if(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getRole() != null &&
							!auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getRole().isEmpty()) {
						
						if(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getRole().toUpperCase().equalsIgnoreCase("WICKET-KEEPER")) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerRole0" + row + " " + 
									icon_path + "Keeper" + AuctionUtil.PNG_EXTENSION + ";");
						}else {
							if(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getRole().toUpperCase().equalsIgnoreCase("BATSMAN") || 
									auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getRole().toUpperCase().equalsIgnoreCase("BAT/KEEPER")) {
								if(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getBatsmanStyle().toUpperCase().equalsIgnoreCase("RHB")) {
									
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerRole0" + row + " " + 
											icon_path + "Batsman" + AuctionUtil.PNG_EXTENSION + ";");
								}
								else if(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getBatsmanStyle().toUpperCase().equalsIgnoreCase("LHB")) {
									
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerRole0" + row + " " + 
											icon_path + "Batsman_Lefthand" + AuctionUtil.PNG_EXTENSION + ";");
								}
							}else if(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getRole().toUpperCase().equalsIgnoreCase("BOWLER")) {
								if(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getBowlerStyle() == null) {
									
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerRole0" + row + " " + 
											icon_path + "FastBowler" + AuctionUtil.PNG_EXTENSION + ";");
								}else {
									switch(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getBowlerStyle().toUpperCase()) {
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
							}else if(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getRole().toUpperCase().equalsIgnoreCase("ALL-ROUNDER")) {
								if(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getBowlerStyle() == null) {
									
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerRole0" + row + " " + 
											icon_path + "FastBowlerAllrounder" + AuctionUtil.PNG_EXTENSION + ";");
								}else {
									switch(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getBowlerStyle().toUpperCase()) {
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
	        		
	        		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName0" + row + " " + ";");
	        		
	        		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPrice0" + row + " " + 
	        				AuctionFunctions.ConvertToLakh(top_sold.get(m).getSoldForPoints()).split(",")[0] + ";");
	        		
	        		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tUnit0" + row + " " + 
	        				AuctionFunctions.formatAmountInCrore(top_sold.get(m).getSoldForPoints()).split(",")[1] + ";");
	        		
	        		//baseinphtot
	        		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamColor0" + row + " " + base_path1 +  
	        				auction.getTeam().get(top_sold.get(m).getTeamId()-1).getTeamName4() + AuctionUtil.PNG_EXTENSION + ";");
	        		
	        		//base2
//	        		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamColor02_0" + row + " " + base_path2 +  
//	        				auction.getTeam().get(top_sold.get(m).getTeamId()-1).getTeamName4() + AuctionUtil.PNG_EXTENSION + ";");
//	        		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamTextColor01_0" + row + " " + 
//        					text1_path + auction.getTeam().get(top_sold.get(m).getTeamId() - 1).getTeamName4() + AuctionUtil.PNG_EXTENSION + ";");
	        		
//	        		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamColor0" + row + " " + base_path2 +  
//	        				auction.getTeam().get(top_sold.get(m).getTeamId()-1).getTeamName4() + AuctionUtil.PNG_EXTENSION + ";");
//	        		
	        		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamTextColor02" + row + " " + text1_path +  
	        				auction.getTeam().get(top_sold.get(m).getTeamId()-1).getTeamName4() + AuctionUtil.PNG_EXTENSION + ";");
	        		
	     
	        		
	        		 print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$PlayersGrp$"+ row+"$Data_GRP$Top$Name*TEXTURE2 SET TEXTURE_PATH " + text1_path +  
		        				auction.getTeam().get(top_sold.get(m).getTeamId()-1).getTeamName4() + AuctionUtil.PNG_EXTENSION + ";");
	        		 print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$PlayersGrp$"+ row+"$Data_GRP$Top$Lastname*TEXTURE2 SET TEXTURE_PATH " + text1_path +  
		        				auction.getTeam().get(top_sold.get(m).getTeamId()-1).getTeamName4() + AuctionUtil.PNG_EXTENSION + ";");
	        		 print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$PlayersGrp$"+ row+"$Data_GRP$Top$Zone*TEXTURE2 SET TEXTURE_PATH " + text1_path +  
		        				auction.getTeam().get(top_sold.get(m).getTeamId()-1).getTeamName4() + AuctionUtil.PNG_EXTENSION + ";");
	        		
	        		//igteamcolourbase
	        		
//	        		if(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId()-1).getCategory().equalsIgnoreCase("FOREIGN")) {
//	    				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vInternational0" + row + " 1 ;");
//	    			}else {
//	    				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vInternational0" + row + " 0 ;");
//	    			}
	        	}
			}
		}
	}
	
	public void populateTop15SoldTeams(boolean is_this_updating,PrintWriter print_writer,String viz_scene,int team_id, Auction auction,AuctionService auctionService, String session_selected_broadcaster) throws InterruptedException 
	{
		int row = 0;
		List<Player> top_sold = new ArrayList<Player>();
		
		Team team = auctionService.getTeams().stream().filter(tm->tm.getTeamId() == Integer.valueOf(team_id)).findAny().orElse(null);
		
		if(auction.getPlayers() != null) {
			for(Player plyr : auction.getPlayers()) {
				if(team.getTeamId() == plyr.getTeamId()) {
					if(plyr.getSoldOrUnsold().equalsIgnoreCase("SOLD") || plyr.getSoldOrUnsold().equalsIgnoreCase("RTM")) {
						top_sold.add(plyr);
					}
				}	
			}
		}
		
		Collections.sort(top_sold,new AuctionFunctions.PlayerStatsComparator());
		
		if(is_this_updating == false) {
			
			if(which_graphics_onscreen != "" && which_graphics_onscreen != "BG") {
				update_gfx = false;
			}
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + 
					team.getTeamName2() + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + 
					team.getTeamName3() + ";");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + 
					"TOP BUYS" + ";");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead01 " + 
					"PLAYER" + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead02 " + 
					"ZONE" + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead03 " + 
					"PRICE" + ";");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogo " + 
					logo_path + team.getTeamName4() + AuctionUtil.PNG_EXTENSION + ";");
			
			for(int i=1; i<= 10; i++) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$Player$" + getNumber(i) + "*CONTAINER SET ACTIVE 0;");
			}
		}
		
		for(int m=0; m<= top_sold.size() - 1; m++) {
			if(!top_sold.get(m).getSoldOrUnsold().equalsIgnoreCase("BID")) {
				row = row + 1;
	        	if(row <= 10) {
	        		
	        		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$Player$" + getNumber(row) + "*CONTAINER SET ACTIVE 1;");
	        		
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
	        		
	        		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeam0" + row + " " + 
	        				auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getCategory().toUpperCase() + ";");
	        		
	        		if(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getIconic().equalsIgnoreCase(AuctionUtil.YES)) {
	        			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vIconic0" + row + " 1;");
	        		}else {
	        			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vIconic0" + row + " 0;");
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
	        		
	        		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPurse0" + row + " " + 
	        				AuctionFunctions.ConvertToLakh(top_sold.get(m).getSoldForPoints()) + ";");
	        		
//	        		if(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId()-1).getCategory().equalsIgnoreCase("FOREIGN")) {
//	    				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vInternational0" + row + " 1 ;");
//	    			}else {
//	    				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vInternational0" + row + " 0 ;");
//	    			}
	        	}
			}
		}
	}
	
	public void populateTopSold(boolean is_this_updating,PrintWriter print_writer,String viz_scene, Auction auction,AuctionService auctionService, String session_selected_broadcaster) throws InterruptedException 
	{
		int row = 0;
		List<Player> top_sold = new ArrayList<Player>();
		
		if(auction.getPlayers() != null) {
			for(Player plyr : auction.getPlayers()) {
				if(plyr.getSoldOrUnsold().equalsIgnoreCase("SOLD") || plyr.getSoldOrUnsold().equalsIgnoreCase("RTM")) {
					top_sold.add(plyr);
				}	
			}
			 for (Squad squad : auctionService.getSquads()) {
			        top_sold.removeIf(player -> player.getPlayerId() == squad.getPlayer_id());
			}
		}
		
		Collections.sort(top_sold,new AuctionFunctions.PlayerStatsComparator());
		
		if(is_this_updating == false) {
			
			if(which_graphics_onscreen != "" && which_graphics_onscreen != "BG") {
				update_gfx = false;
			}
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + "TOP BUYS" + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + "" + ";");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + "HBL PSL PLAYER AUCTION" + ";");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead01 " + "PLAYER" + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead02 " + "TEAM" + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead03 " + "PRICE" + ";");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogo_01 " + 
					logo_path + "EVENT" + AuctionUtil.PNG_EXTENSION + ";");
			
			for(int i=1; i<= 10; i++) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$Player$" + getNumber(i) + "*CONTAINER SET ACTIVE 0;");
			}
		}
		
		for(int m=0; m<= top_sold.size() - 1; m++) {
			if(!top_sold.get(m).getSoldOrUnsold().equalsIgnoreCase("BID")) {
				row = row + 1;
	        	if(row <= 10) {
	        		
	        		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$Player$" + getNumber(row) + "*CONTAINER SET ACTIVE 1;");
	        		
	        		if(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getSurname() != null) {
	        			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamFirstName0" + row + " " + 
	        					auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getFirstname() + ";");
	        			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamLastName0" + row + " " + 
	        					auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getSurname() + ";");
					}else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamFirstName0" + row + " " + 
								auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getFirstname() + ";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamLastName0" + row + " " + ";");
					}
	        		
	        		if(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getIconic().equalsIgnoreCase(AuctionUtil.YES)) {
	        			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vIconic0" + row + " 1;");
	        		}else {
	        			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vIconic0" + row + " 0;");
	        		}
	        		if(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getNationality().equalsIgnoreCase("PAKISTAN")) {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vOverseas0" + row + " 0;");
					}else {
						print_writer.println("LAYER" + current_layer + "EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vOverseas0" + row + " 1;");
					}
	        		
	        		if(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getRole() != null &&
							!auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getRole().isEmpty()) {
						
						if(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getRole().toUpperCase().equalsIgnoreCase("WICKET-KEEPER")) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgRole_0" + row + " " + 
									icon_path + "Keeper" + AuctionUtil.PNG_EXTENSION + ";");
						}else {
							if(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getRole().toUpperCase().equalsIgnoreCase("BATSMAN") || 
									auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getRole().toUpperCase().equalsIgnoreCase("BAT/KEEPER")) {
								if(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getBatsmanStyle().toUpperCase().equalsIgnoreCase("RHB")) {
									
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgRole_0" + row + " " + 
											icon_path + "Batsman" + AuctionUtil.PNG_EXTENSION + ";");
								}
								else if(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getBatsmanStyle().toUpperCase().equalsIgnoreCase("LHB")) {
									
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgRole_0" + row + " " + 
											icon_path + "Batsman_Lefthand" + AuctionUtil.PNG_EXTENSION + ";");
								}
							}else if(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getRole().toUpperCase().equalsIgnoreCase("BOWLER")) {
								if(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getBowlerStyle() == null) {
									
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgRole_0" + row + " " + 
											icon_path + "FastBowler" + AuctionUtil.PNG_EXTENSION + ";");
								}else {
									switch(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getBowlerStyle().toUpperCase()) {
									case "RF": case "RFM": case "RMF": case "RM": case "RSM": case "LF": case "LFM": case "LMF": case "LM":
										
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgRole_0" + row + " " + 
												icon_path + "FastBowler" + AuctionUtil.PNG_EXTENSION + ";");
										break;
									case "ROB": case "RLB": case "LSL": case "WSL": case "LCH": case "RLG": case "WSR": case "LSO":
										
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgRole_0" + row + " " + 
												icon_path + "SpinBowlerIcon" + AuctionUtil.PNG_EXTENSION + ";");
										break;
									}
								}
							}else if(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getRole().toUpperCase().equalsIgnoreCase("ALL-ROUNDER")) {
								if(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getBowlerStyle() == null) {
									
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgRole_0" + row + " " + 
											icon_path + "FastBowlerAllrounder" + AuctionUtil.PNG_EXTENSION + ";");
								}else {
									switch(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getBowlerStyle().toUpperCase()) {
									case "RF": case "RFM": case "RMF": case "RM": case "RSM": case "LF": case "LFM": case "LMF": case "LM":
										
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgRole_0" + row + " " + 
												icon_path + "FastBowlerAllrounder" + AuctionUtil.PNG_EXTENSION + ";");
										break;
									case "ROB": case "RLB": case "LSL": case "WSL": case "LCH": case "RLG": case "WSR": case "LSO":
										
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgRole_0" + row + " " + 
												icon_path + "SpinBowlerAllrounder" + AuctionUtil.PNG_EXTENSION + ";");
										break;
									}
								}
							}
						}
					}else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgRole_0" + row + " " + "-" + ";");
					}
	        		
	        		
	        		
	        		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeam0" + row + " " + 
	        				auction.getTeam().get(top_sold.get(m).getTeamId()-1).getTeamName1() + ";");
	        		
	        		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPurse0" + row + " " + 
	        				AuctionFunctions.ConvertToLakh(top_sold.get(m).getSoldForPoints()) + ";");
	        		
	        		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPurse0" + row + " " + 
							AuctionFunctions.formatAmountInCrore((Double.valueOf(top_sold.get(m).getSoldForPoints()))).split(",")[0]  + ";");
	        		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tUnit0" + row + " " + 
						"  " + 	AuctionFunctions.formatAmountInCrore((Double.valueOf(top_sold.get(m).getSoldForPoints()))).split(",")[1]  + ";");
					
//	        		if(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId()-1).getCategory().equalsIgnoreCase("FOREIGN")) {
//	    				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vInternational0" + row + " 1 ;");
//	    			}else {
//	    				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vInternational0" + row + " 0 ;");
//	    			}
	        	}
			}
		}
	}
	
	public void populateTop15Sold(boolean is_this_updating,PrintWriter print_writer,String viz_scene, Auction auction,AuctionService auctionService, String session_selected_broadcaster) throws InterruptedException 
	{
		int row = 0;
		List<Player> top_sold = new ArrayList<Player>();
		
		if(auction.getPlayers() != null) {
			for(Player plyr : auction.getPlayers()) {
				if(plyr.getSoldOrUnsold().equalsIgnoreCase("SOLD") || plyr.getSoldOrUnsold().equalsIgnoreCase("RTM")) {
					top_sold.add(plyr);
				}	
			}
		}
		
		Collections.sort(top_sold,new AuctionFunctions.PlayerStatsComparator());
		
		if(is_this_updating == false) {
			
			if(which_graphics_onscreen != "" && which_graphics_onscreen != "BG") {
				update_gfx = false;
			}
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + "" + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + "HBL PSL PLAYER AUCTION" + ";");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + "TOP BUYS" + ";");
			
//			for(int i=1; i<= 10; i++) {
//				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$Player$" + getNumber(i) + "*CONTAINER SET ACTIVE 0;");
//			}
		}
		
		for(int m=0; m<= top_sold.size() - 1; m++) {
			if(!top_sold.get(m).getSoldOrUnsold().equalsIgnoreCase("BID")) {
				row = row + 1;
	        	if(row <= 15) {
	        		size = row;
//	        		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$Player$" + getNumber(row) + "*CONTAINER SET ACTIVE 1;");
	        		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectLogo0" + row + " 1;");
	        		
	        		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo0" + row + " " + 
	    					logo_path + auction.getTeam().get(top_sold.get(m).getTeamId() - 1).getTeamName4() + AuctionUtil.PNG_EXTENSION + ";");
	        		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage0" + row + " " + 
	    					photo_path + auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getPhotoName() + AuctionUtil.PNG_EXTENSION + ";");
	        		
	        		if(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getSurname() != null) {
	        			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFirstname0" + row + " " + 
	        					auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getFirstname() + ";");
	        			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastname0" + row + " " + 
	        					auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getSurname() + ";");
					}else {
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFirstname0" + row + " " + 
								auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getFirstname() + ";");
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastname0" + row + " " + ";");
					}
	        		
//	        		if(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getCategory().equalsIgnoreCase("U19")) {
//	        			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFrom0" + row + " " + 
//	        					"UNDER 19" + ";");
//	        		}else {
//	        			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFrom0" + row + " " + 
//	        					auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getCategory().toUpperCase() + ";");
//	        		}
	        		
	        		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFrom0" + row + " " + 
        					auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getNationality().toUpperCase() + ";");
	        		
	        		if(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getIconic().equalsIgnoreCase(AuctionUtil.YES)) {
	        			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vPlayerInfo0" + row + " 1;");
	        		}else {
	        			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vPlayerInfo0" + row + " 0;");
	        		}
	        		
	        		if(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getRole() != null &&
							!auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getRole().isEmpty()) {
						
						if(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getRole().toUpperCase().equalsIgnoreCase("WICKET-KEEPER")) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerRole0" + row + " " + 
									icon_path + "Keeper" + AuctionUtil.PNG_EXTENSION + ";");
						}else {
							if(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getRole().toUpperCase().equalsIgnoreCase("BATSMAN") || 
									auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getRole().toUpperCase().equalsIgnoreCase("BAT/KEEPER")) {
								if(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getBatsmanStyle().toUpperCase().equalsIgnoreCase("RHB")) {
									
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerRole0" + row + " " + 
											icon_path + "Batsman" + AuctionUtil.PNG_EXTENSION + ";");
								}
								else if(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getBatsmanStyle().toUpperCase().equalsIgnoreCase("LHB")) {
									
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerRole0" + row + " " + 
											icon_path + "Batsman_Lefthand" + AuctionUtil.PNG_EXTENSION + ";");
								}
							}else if(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getRole().toUpperCase().equalsIgnoreCase("BOWLER")) {
								if(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getBowlerStyle() == null) {
									
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerRole0" + row + " " + 
											icon_path + "FastBowler" + AuctionUtil.PNG_EXTENSION + ";");
								}else {
									switch(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getBowlerStyle().toUpperCase()) {
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
							}else if(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getRole().toUpperCase().equalsIgnoreCase("ALL-ROUNDER")) {
								if(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getBowlerStyle() == null) {
									
									print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerRole0" + row + " " + 
											icon_path + "FastBowlerAllrounder" + AuctionUtil.PNG_EXTENSION + ";");
								}else {
									switch(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getBowlerStyle().toUpperCase()) {
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
	        		
	        		
	        		
	        		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName0" + row + " " + 
	        				auction.getTeam().get(top_sold.get(m).getTeamId()-1).getTeamName1() + ";");
	        		
	        		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPrice0" + row + " " + 
	        				AuctionFunctions.ConvertToLakh(top_sold.get(m).getSoldForPoints()) + ";");
	        		
//	        		if(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId()-1).getCategory().equalsIgnoreCase("FOREIGN")) {
//	    				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vInternational0" + row + " 1 ;");
//	    			}else {
//	    				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vInternational0" + row + " 0 ;");
//	    			}
	        	}
			}
		}
	}
	public void populateTopFiveSold(boolean is_this_updating,PrintWriter print_writer,String viz_scene, Auction auction,AuctionService auctionService, String session_selected_broadcaster) throws InterruptedException 
	{
		int row = 0;
		List<Player> top_sold = new ArrayList<Player>();
		
		if(auction.getPlayers() != null) {
			for(Player plyr : auction.getPlayers()) {
				if(plyr.getSoldOrUnsold().equalsIgnoreCase("SOLD") || plyr.getSoldOrUnsold().equalsIgnoreCase("RTM")) {
					top_sold.add(plyr);
				}	
			}
			for (Squad squad : auctionService.getSquads()) {
		        top_sold.removeIf(player -> player.getPlayerId() == squad.getPlayer_id());
		    }
		}
		
		Collections.sort(top_sold,new AuctionFunctions.PlayerStatsComparator());
		
		if(is_this_updating == false) {
			
			if(which_graphics_onscreen != "" && which_graphics_onscreen != "BG") {
				update_gfx = false;
			}
			
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$HeaderGrp$Iconic*CONTAINER SET ACTIVE 0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + "HBL PSL PLAYER AUCTION" + ";");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + "TOP BUYS" + ";");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogoBase " + 
					logo_base_path + "EVENT" + AuctionUtil.PNG_EXTENSION + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogo_01 " + 
					logo_path + "EVENT" + AuctionUtil.PNG_EXTENSION + ";");
			
			for(int i=1; i<= 5; i++) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$PlayersGrp$1_to_5$" + i + "*CONTAINER SET ACTIVE 0;");
			}
		}
		
		
		
		if(is_this_updating == false) {
			for(int m=0; m<= top_sold.size() - 1; m++) {
				if(!top_sold.get(m).getSoldOrUnsold().equalsIgnoreCase("BID")) {
					row = row + 1;
		        	if(row <= 5) {
		        		
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$PlayersGrp$1_to_5$" + row + "*CONTAINER SET ACTIVE 1;");

		        		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo0" + row + " 1;");
		        		
		        		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo0" + row + " " + 
	        					logo_path + auction.getTeam().get(top_sold.get(m).getTeamId() - 1).getTeamBadge() + AuctionUtil.PNG_EXTENSION + ";");
		        		
		        		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage0" + row + " " + 
	        					photo_path + auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getPhotoName() + AuctionUtil.PNG_EXTENSION + ";");
		        		
		        		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamTextColor0" + row + " " + 
	        					text1_path + auction.getTeam().get(top_sold.get(m).getTeamId() - 1).getTeamBadge() + AuctionUtil.PNG_EXTENSION + ";");
		        		
		        		if(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getSurname() != null) {
		        			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFirstname0" + row + " " + 
		        					auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getFirstname() + ";");
		        			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastname0" + row + " " + 
		        					auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getSurname() + ";");
						}else {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFirstname0" + row + " " + 
									auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getFirstname() + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastname0" + row + " " + ";");
						}
		        		
//		        		if(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getCategory().equalsIgnoreCase("U19")) {
//		        			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFrom0" + row + " " + 
//			        				"UNDER 19" + ";");
//		        		}else {
//		        			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFrom0" + row + " " + 
//			        				auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getCategory().toUpperCase() + ";");
//		        		}
		        		
		        		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFrom0" + row + " " + 
		        				auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getNationality().toUpperCase() + ";");
		        		
		        		if(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getIconic().equalsIgnoreCase(AuctionUtil.YES)) {
		        			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vPlayerInfo0" + row + " 1;");
		        		}else {
		        			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vPlayerInfo0" + row + " 0;");
		        		}
		        		
		        		if(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getNationality().equalsIgnoreCase("PAKISTAN")) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vOverseas0" + row + " 0;");
						}else {
							print_writer.println("LAYER" + current_layer + "EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vOverseas0" + row + " 1;");
						}
		        		if(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getRole() != null &&
								!auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getRole().isEmpty()) {
							
							if(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getRole().toUpperCase().equalsIgnoreCase("WICKET-KEEPER")) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerRole0" + row + " " + 
										icon_path + "Keeper" + AuctionUtil.PNG_EXTENSION + ";");
							}else {
								if(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getRole().toUpperCase().equalsIgnoreCase("BATSMAN") || 
										auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getRole().toUpperCase().equalsIgnoreCase("BAT/KEEPER")) {
									if(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getBatsmanStyle().toUpperCase().equalsIgnoreCase("RHB")) {
										
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerRole0" + row + " " + 
												icon_path + "Batsman" + AuctionUtil.PNG_EXTENSION + ";");
									}
									else if(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getBatsmanStyle().toUpperCase().equalsIgnoreCase("LHB")) {
										
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerRole0" + row + " " + 
												icon_path + "Batsman_Lefthand" + AuctionUtil.PNG_EXTENSION + ";");
									}
								}else if(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getRole().toUpperCase().equalsIgnoreCase("BOWLER")) {
									if(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getBowlerStyle() == null) {
										
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerRole0" + row + " " + 
												icon_path + "FastBowler" + AuctionUtil.PNG_EXTENSION + ";");
									}else {
										switch(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getBowlerStyle().toUpperCase()) {
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
								}else if(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getRole().toUpperCase().equalsIgnoreCase("ALL-ROUNDER")) {
									if(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getBowlerStyle() == null) {
										
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerRole0" + row + " " + 
												icon_path + "FastBowlerAllrounder" + AuctionUtil.PNG_EXTENSION + ";");
									}else {
										switch(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId() -1).getBowlerStyle().toUpperCase()) {
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
		        		
		        		
		        		
		        		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName0" + row + " " + 
		        				auction.getTeam().get(top_sold.get(m).getTeamId()-1).getTeamName1() + ";");
		        		
		        		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamColor0" + row + " " + base_path1 +  
		        				auction.getTeam().get(top_sold.get(m).getTeamId()-1).getTeamBadge() + AuctionUtil.PNG_EXTENSION + ";");
		        		
		        		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamTextColor0" + row + " " + text1_path +  
		        				auction.getTeam().get(top_sold.get(m).getTeamId()-1).getTeamBadge() + AuctionUtil.PNG_EXTENSION + ";");
		        		
		        	
//		        		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamTextColor01_0" + row + " " + text1_path +  
//		        				auction.getTeam().get(top_sold.get(m).getTeamId()-1).getTeamBadge() + AuctionUtil.PNG_EXTENSION + ";");
		        		
		        		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo0" + row + " " + logo_path +  
		        				auction.getTeam().get(top_sold.get(m).getTeamId()-1).getTeamBadge() + AuctionUtil.PNG_EXTENSION + ";");
		        		
		        		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPrice0" + row + " " + 
		        				AuctionFunctions.formatAmountInCrore(top_sold.get(m).getSoldForPoints()).split(",")[0]  + ";");
		        		
		        		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tUnit0" + row + " " + 
		        				AuctionFunctions.formatAmountInCrore(top_sold.get(m).getSoldForPoints()).split(",")[1] + ";");
		        		
//		        		if(auctionService.getAllPlayer().get(top_sold.get(m).getPlayerId()-1).getCategory().equalsIgnoreCase("FOREIGN")) {
//		    				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vInternational0" + row + " 1 ;");
//		    			}else {
//		    				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vInternational0" + row + " 0 ;");
//		    			}
		        	}
				}
			}
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
							AuctionFunctions.ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[0])) + " L" + "\nD4|Gap" +
							"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[1] + " :" + " \nD3|TextInfo03=" + AuctionFunctions.ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[1])) + " L" + "\nD4|Gap" + 
							"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[2] + " :" + " \nD3|TextInfo03=" + AuctionFunctions.ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[2])) + " L" + "\nD4|Gap" + 
							"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[3] + " :" + " \nD3|TextInfo03=" + AuctionFunctions.ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[3])) + " L" + "\nD4|Gap" +
							"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[4] + " :" + " \nD3|TextInfo03=" + AuctionFunctions.ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[4])) + " L" + "\nD4|Gap" +
							"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[5] + " :" + " \nD3|TextInfo03=" + AuctionFunctions.ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[5])) + " L" + "\nD4|Gap;");
				 }else if(prices.size() == 5) {
					 print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Scroller-MAIN*GEOMETRY*SCROLLER SET DATA "
								+ "D1|TextInfo=" + "ISPL AUCTION 2024" + "\nD2|TextInfo02= " + (String) prices.keySet().toArray()[0] + " :" + " \nD3|TextInfo03=" + 
								String.join(", ", prices.get((String) prices.keySet().toArray()[0])) + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) prices.keySet().toArray()[1] + " :" + " \nD3|TextInfo03=" + String.join(", ", prices.get((String) prices.keySet().toArray()[1])) + "\nD4|Gap" + 
								"\nD2|TextInfo02= " + (String) prices.keySet().toArray()[2] + " :" + " \nD3|TextInfo03=" + String.join(", ", prices.get((String) prices.keySet().toArray()[2])) + "\nD4|Gap" + 
								"\nD2|TextInfo02= " + (String) prices.keySet().toArray()[3] + " :" + " \nD3|TextInfo03=" + String.join(", ", prices.get((String) prices.keySet().toArray()[3])) + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) prices.keySet().toArray()[4] + " :" + " \nD3|TextInfo03=" + String.join(", ", prices.get((String) prices.keySet().toArray()[4])) + "\nD4|Gap" 
								+ "\nD1|TextInfo=" + "REMAINING PURSE" + "\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[0] + " :" + " \nD3|TextInfo03=" + 
								AuctionFunctions.ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[0])) + " L" + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[1] + " :" + " \nD3|TextInfo03=" + AuctionFunctions.ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[1])) + " L" + "\nD4|Gap" + 
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[2] + " :" + " \nD3|TextInfo03=" + AuctionFunctions.ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[2])) + " L" + "\nD4|Gap" + 
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[3] + " :" + " \nD3|TextInfo03=" + AuctionFunctions.ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[3])) + " L" + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[4] + " :" + " \nD3|TextInfo03=" + AuctionFunctions.ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[4])) + " L" + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[5] + " :" + " \nD3|TextInfo03=" + AuctionFunctions.ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[5])) + " L" + "\nD4|Gap;");
				 }else if(prices.size() == 4) {
					 print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Scroller-MAIN*GEOMETRY*SCROLLER SET DATA "
								+ "D1|TextInfo=" + "ISPL AUCTION 2024" + "\nD2|TextInfo02= " + (String) prices.keySet().toArray()[0] + " :" + " \nD3|TextInfo03=" + 
								String.join(", ", prices.get((String) prices.keySet().toArray()[0])) + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) prices.keySet().toArray()[1] + " :" + " \nD3|TextInfo03=" + String.join(", ", prices.get((String) prices.keySet().toArray()[1])) + "\nD4|Gap" + 
								"\nD2|TextInfo02= " + (String) prices.keySet().toArray()[2] + " :" + " \nD3|TextInfo03=" + String.join(", ", prices.get((String) prices.keySet().toArray()[2])) + "\nD4|Gap" + 
								"\nD2|TextInfo02= " + (String) prices.keySet().toArray()[3] + " :" + " \nD3|TextInfo03=" + String.join(", ", prices.get((String) prices.keySet().toArray()[3])) + "\nD4|Gap" 
								+ "\nD1|TextInfo=" + "REMAINING PURSE" + "\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[0] + " :" + " \nD3|TextInfo03=" + 
								AuctionFunctions.ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[0])) + " L" + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[1] + " :" + " \nD3|TextInfo03=" + AuctionFunctions.ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[1])) + " L" + "\nD4|Gap" + 
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[2] + " :" + " \nD3|TextInfo03=" + AuctionFunctions.ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[2])) + " L" + "\nD4|Gap" + 
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[3] + " :" + " \nD3|TextInfo03=" + AuctionFunctions.ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[3])) + " L" + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[4] + " :" + " \nD3|TextInfo03=" + AuctionFunctions.ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[4])) + " L" + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[5] + " :" + " \nD3|TextInfo03=" + AuctionFunctions.ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[5])) + " L" + "\nD4|Gap;");
				 }else if(prices.size() == 3) {
					 print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Scroller-MAIN*GEOMETRY*SCROLLER SET DATA "
								+ "D1|TextInfo=" + "ISPL AUCTION 2024" + "\nD2|TextInfo02= " + (String) prices.keySet().toArray()[0] + " :" + " \nD3|TextInfo03=" + 
								String.join(", ", prices.get((String) prices.keySet().toArray()[0])) + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) prices.keySet().toArray()[1] + " :" + " \nD3|TextInfo03=" + String.join(", ", prices.get((String) prices.keySet().toArray()[1])) + "\nD4|Gap" + 
								"\nD2|TextInfo02= " + (String) prices.keySet().toArray()[2] + " :" + " \nD3|TextInfo03=" + String.join(", ", prices.get((String) prices.keySet().toArray()[2])) + "\nD4|Gap" 
								+ "\nD1|TextInfo=" + "REMAINING PURSE" + "\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[0] + " :" + " \nD3|TextInfo03=" + 
								AuctionFunctions.ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[0])) + " L" + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[1] + " :" + " \nD3|TextInfo03=" + AuctionFunctions.ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[1])) + " L" + "\nD4|Gap" + 
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[2] + " :" + " \nD3|TextInfo03=" + AuctionFunctions.ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[2])) + " L" + "\nD4|Gap" + 
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[3] + " :" + " \nD3|TextInfo03=" + AuctionFunctions.ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[3])) + " L" + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[4] + " :" + " \nD3|TextInfo03=" + AuctionFunctions.ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[4])) + " L" + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[5] + " :" + " \nD3|TextInfo03=" + AuctionFunctions.ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[5])) + " L" + "\nD4|Gap;");
				 }else if(prices.size() == 2) {
					 print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Scroller-MAIN*GEOMETRY*SCROLLER SET DATA "
								+ "D1|TextInfo=" + "ISPL AUCTION 2024" + "\nD2|TextInfo02= " + (String) prices.keySet().toArray()[0] + " :" + " \nD3|TextInfo03=" + 
								String.join(", ", prices.get((String) prices.keySet().toArray()[0])) + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) prices.keySet().toArray()[1] + " :" + " \nD3|TextInfo03=" + String.join(", ", prices.get((String) prices.keySet().toArray()[1])) + "\nD4|Gap" 
								+ "\nD1|TextInfo=" + "REMAINING PURSE" + "\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[0] + " :" + " \nD3|TextInfo03=" + 
								AuctionFunctions.ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[0])) + " L" + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[1] + " :" + " \nD3|TextInfo03=" + AuctionFunctions.ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[1])) + " L" + "\nD4|Gap" + 
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[2] + " :" + " \nD3|TextInfo03=" + AuctionFunctions.ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[2])) + " L" + "\nD4|Gap" + 
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[3] + " :" + " \nD3|TextInfo03=" + AuctionFunctions.ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[3])) + " L" + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[4] + " :" + " \nD3|TextInfo03=" + AuctionFunctions.ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[4])) + " L" + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[5] + " :" + " \nD3|TextInfo03=" + AuctionFunctions.ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[5])) + " L" + "\nD4|Gap;");
				 }else if(prices.size() == 1) {
					 print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Scroller-MAIN*GEOMETRY*SCROLLER SET DATA "
								+ "D1|TextInfo=" + "ISPL AUCTION 2024" + "\nD2|TextInfo02= " + (String) prices.keySet().toArray()[0] + " :" + " \nD3|TextInfo03=" + 
								String.join(", ", prices.get((String) prices.keySet().toArray()[0])) + "\nD4|Gap" 
								+ "\nD1|TextInfo=" + "REMAINING PURSE" + "\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[0] + " :" + " \nD3|TextInfo03=" + 
								AuctionFunctions.ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[0])) + " L" + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[1] + " :" + " \nD3|TextInfo03=" + AuctionFunctions.ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[1])) + " L" + "\nD4|Gap" + 
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[2] + " :" + " \nD3|TextInfo03=" + AuctionFunctions.ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[2])) + " L" + "\nD4|Gap" + 
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[3] + " :" + " \nD3|TextInfo03=" + AuctionFunctions.ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[3])) + " L" + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[4] + " :" + " \nD3|TextInfo03=" + AuctionFunctions.ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[4])) + " L" + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[5] + " :" + " \nD3|TextInfo03=" + AuctionFunctions.ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[5])) + " L" + "\nD4|Gap;");
				 }
			}else if(data.equalsIgnoreCase("PURSE") && data2.equalsIgnoreCase("SOLD")) {
				if(prices.size() == 6) {
					 print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Scroller-MAIN*GEOMETRY*SCROLLER SET DATA "
							+ "D1|TextInfo=" + "REMAINING PURSE" + "\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[0] + " :" + " \nD3|TextInfo03=" + 
							AuctionFunctions.ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[0])) + " L" + "\nD4|Gap" +
							"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[1] + " :" + " \nD3|TextInfo03=" + AuctionFunctions.ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[1])) + " L" + "\nD4|Gap" + 
							"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[2] + " :" + " \nD3|TextInfo03=" + AuctionFunctions.ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[2])) + " L" + "\nD4|Gap" + 
							"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[3] + " :" + " \nD3|TextInfo03=" + AuctionFunctions.ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[3])) + " L" + "\nD4|Gap" +
							"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[4] + " :" + " \nD3|TextInfo03=" + AuctionFunctions.ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[4])) + " L" + "\nD4|Gap" +
							"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[5] + " :" + " \nD3|TextInfo03=" + AuctionFunctions.ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[5])) + " L" + "\nD4|Gap" 
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
								AuctionFunctions.ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[0])) + " L" + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[1] + " :" + " \nD3|TextInfo03=" + AuctionFunctions.ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[1])) + " L" + "\nD4|Gap" + 
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[2] + " :" + " \nD3|TextInfo03=" + AuctionFunctions.ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[2])) + " L" + "\nD4|Gap" + 
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[3] + " :" + " \nD3|TextInfo03=" + AuctionFunctions.ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[3])) + " L" + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[4] + " :" + " \nD3|TextInfo03=" + AuctionFunctions.ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[4])) + " L" + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[5] + " :" + " \nD3|TextInfo03=" + AuctionFunctions.ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[5])) + " L" + "\nD4|Gap"
								+ "\nD1|TextInfo=" + "ISPL AUCTION 2024" + "\nD2|TextInfo02= " + (String) prices.keySet().toArray()[0] + " :" + " \nD3|TextInfo03=" + 
								String.join(", ", prices.get((String) prices.keySet().toArray()[0])) + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) prices.keySet().toArray()[1] + " :" + " \nD3|TextInfo03=" + String.join(", ", prices.get((String) prices.keySet().toArray()[1])) + "\nD4|Gap" + 
								"\nD2|TextInfo02= " + (String) prices.keySet().toArray()[2] + " :" + " \nD3|TextInfo03=" + String.join(", ", prices.get((String) prices.keySet().toArray()[2])) + "\nD4|Gap" + 
								"\nD2|TextInfo02= " + (String) prices.keySet().toArray()[3] + " :" + " \nD3|TextInfo03=" + String.join(", ", prices.get((String) prices.keySet().toArray()[3])) + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) prices.keySet().toArray()[4] + " :" + " \nD3|TextInfo03=" + String.join(", ", prices.get((String) prices.keySet().toArray()[4])) + "\nD4|Gap;");
				 }else if(prices.size() == 4) {
					 print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Scroller-MAIN*GEOMETRY*SCROLLER SET DATA "
								+ "D1|TextInfo=" + "REMAINING PURSE" + "\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[0] + " :" + " \nD3|TextInfo03=" + 
								AuctionFunctions.ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[0])) + " L" + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[1] + " :" + " \nD3|TextInfo03=" + AuctionFunctions.ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[1])) + " L" + "\nD4|Gap" + 
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[2] + " :" + " \nD3|TextInfo03=" + AuctionFunctions.ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[2])) + " L" + "\nD4|Gap" + 
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[3] + " :" + " \nD3|TextInfo03=" + AuctionFunctions.ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[3])) + " L" + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[4] + " :" + " \nD3|TextInfo03=" + AuctionFunctions.ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[4])) + " L" + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[5] + " :" + " \nD3|TextInfo03=" + AuctionFunctions.ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[5])) + " L" + "\nD4|Gap"
								+ "\nD1|TextInfo=" + "ISPL AUCTION 2024" + "\nD2|TextInfo02= " + (String) prices.keySet().toArray()[0] + " :" + " \nD3|TextInfo03=" + 
								String.join(", ", prices.get((String) prices.keySet().toArray()[0])) + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) prices.keySet().toArray()[1] + " :" + " \nD3|TextInfo03=" + String.join(", ", prices.get((String) prices.keySet().toArray()[1])) + "\nD4|Gap" + 
								"\nD2|TextInfo02= " + (String) prices.keySet().toArray()[2] + " :" + " \nD3|TextInfo03=" + String.join(", ", prices.get((String) prices.keySet().toArray()[2])) + "\nD4|Gap" + 
								"\nD2|TextInfo02= " + (String) prices.keySet().toArray()[3] + " :" + " \nD3|TextInfo03=" + String.join(", ", prices.get((String) prices.keySet().toArray()[3])) + "\nD4|Gap;");
				 }else if(prices.size() == 3) {
					 print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Scroller-MAIN*GEOMETRY*SCROLLER SET DATA "
								+ "D1|TextInfo=" + "REMAINING PURSE" + "\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[0] + " :" + " \nD3|TextInfo03=" + 
								AuctionFunctions.ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[0])) + " L" + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[1] + " :" + " \nD3|TextInfo03=" + AuctionFunctions.ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[1])) + " L" + "\nD4|Gap" + 
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[2] + " :" + " \nD3|TextInfo03=" + AuctionFunctions.ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[2])) + " L" + "\nD4|Gap" + 
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[3] + " :" + " \nD3|TextInfo03=" + AuctionFunctions.ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[3])) + " L" + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[4] + " :" + " \nD3|TextInfo03=" + AuctionFunctions.ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[4])) + " L" + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[5] + " :" + " \nD3|TextInfo03=" + AuctionFunctions.ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[5])) + " L" + "\nD4|Gap" 
								+ "\nD1|TextInfo=" + "ISPL AUCTION 2024" + "\nD2|TextInfo02= " + (String) prices.keySet().toArray()[0] + " :" + " \nD3|TextInfo03=" + 
								String.join(", ", prices.get((String) prices.keySet().toArray()[0])) + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) prices.keySet().toArray()[1] + " :" + " \nD3|TextInfo03=" + String.join(", ", prices.get((String) prices.keySet().toArray()[1])) + "\nD4|Gap" + 
								"\nD2|TextInfo02= " + (String) prices.keySet().toArray()[2] + " :" + " \nD3|TextInfo03=" + String.join(", ", prices.get((String) prices.keySet().toArray()[2])) + "\nD4|Gap;");
				 }else if(prices.size() == 2) {
					 print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Scroller-MAIN*GEOMETRY*SCROLLER SET DATA "
								+ "D1|TextInfo=" + "REMAINING PURSE" + "\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[0] + " :" + " \nD3|TextInfo03=" + 
								AuctionFunctions.ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[0])) + " L" + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[1] + " :" + " \nD3|TextInfo03=" + AuctionFunctions.ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[1])) + " L" + "\nD4|Gap" + 
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[2] + " :" + " \nD3|TextInfo03=" + AuctionFunctions.ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[2])) + " L" + "\nD4|Gap" + 
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[3] + " :" + " \nD3|TextInfo03=" + AuctionFunctions.ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[3])) + " L" + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[4] + " :" + " \nD3|TextInfo03=" + AuctionFunctions.ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[4])) + " L" + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[5] + " :" + " \nD3|TextInfo03=" + AuctionFunctions.ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[5])) + " L" + "\nD4|Gap" 
								+ "\nD1|TextInfo=" + "ISPL AUCTION 2024" + "\nD2|TextInfo02= " + (String) prices.keySet().toArray()[0] + " :" + " \nD3|TextInfo03=" + 
								String.join(", ", prices.get((String) prices.keySet().toArray()[0])) + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) prices.keySet().toArray()[1] + " :" + " \nD3|TextInfo03=" + String.join(", ", prices.get((String) prices.keySet().toArray()[1])) + "\nD4|Gap;");
				 }else if(prices.size() == 1) {
					 print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Scroller-MAIN*GEOMETRY*SCROLLER SET DATA "
								+ "D1|TextInfo=" + "REMAINING PURSE" + "\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[0] + " :" + " \nD3|TextInfo03=" + 
								AuctionFunctions.ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[0])) + " L" + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[1] + " :" + " \nD3|TextInfo03=" + AuctionFunctions.ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[1])) + " L" + "\nD4|Gap" + 
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[2] + " :" + " \nD3|TextInfo03=" + AuctionFunctions.ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[2])) + " L" + "\nD4|Gap" + 
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[3] + " :" + " \nD3|TextInfo03=" + AuctionFunctions.ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[3])) + " L" + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[4] + " :" + " \nD3|TextInfo03=" + AuctionFunctions.ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[4])) + " L" + "\nD4|Gap" +
								"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[5] + " :" + " \nD3|TextInfo03=" + AuctionFunctions.ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[5])) + " L" + "\nD4|Gap"
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
						AuctionFunctions.ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[0])) + " L" + "\nD4|Gap" +
						"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[1] + " :" + " \nD3|TextInfo03=" + AuctionFunctions.ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[1])) + " L" + "\nD4|Gap" + 
						"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[2] + " :" + " \nD3|TextInfo03=" + AuctionFunctions.ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[2])) + " L" + "\nD4|Gap" + 
						"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[3] + " :" + " \nD3|TextInfo03=" + AuctionFunctions.ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[3])) + " L" + "\nD4|Gap" +
						"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[4] + " :" + " \nD3|TextInfo03=" + AuctionFunctions.ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[4])) + " L" + "\nD4|Gap" +
						"\nD2|TextInfo02= " + (String) remainingPurse.keySet().toArray()[5] + " :" + " \nD3|TextInfo03=" + AuctionFunctions.ConvertToLakh(remainingPurse.get((String) remainingPurse.keySet().toArray()[5])) + " L" + "\nD4|Gap;");
			}
			
		}
	}
	
	public void populateRtmSquad(boolean is_this_updating,PrintWriter print_writer,String viz_scene, Auction match,AuctionService auctionService, String session_selected_broadcaster) throws InterruptedException 
	{
		int row = 0,total = 0,rtmUsed =0;
		
		if(is_this_updating == false) {
			
			if(which_graphics_onscreen != "" && which_graphics_onscreen != "BG") {
				update_gfx = false;
			}
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + "SEASON 3 PLAYER AUCTION" + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + "INDIAN STREET PREMIER LEAGUE" + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + "" + ";");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead01 " + "TEAMS" + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead02 " + "SQUAD SIZE" + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead03 " + "RTM REM." + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead04 " + "PURSE REM." + ";");
			
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
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRTM0" + (i+1) + " " + 
					(Integer.valueOf(auctionService.getTeams().get(match.getTeam().get(i).getTeamId()-1).getTeamTotalRTM()) - rtmUsed)+";");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSquadSize0" + (i+1) + " " + 
					total + ";");
			
			if(is_this_updating == false) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamFirstName0" + (i+1) + " " + 
						auctionService.getTeams().get(match.getTeam().get(i).getTeamId()-1).getTeamName2() + ";");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamLastName0" + (i+1) + " " + 
						auctionService.getTeams().get(match.getTeam().get(i).getTeamId()-1).getTeamName3() + ";");
				
//				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogoBase0" + (i+1) + " " + logo_base_path + 
//						auctionService.getTeams().get(match.getTeam().get(i).getTeamId()-1).getTeamName4() + AuctionUtil.PNG_EXTENSION + ";");
				
//				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogo_0" + (i+1) + " " + logobw_path + 
//						auctionService.getTeams().get(match.getTeam().get(i).getTeamId()-1).getTeamName4() + AuctionUtil.PNG_EXTENSION + ";");
				
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogo_0" + (i+1) + " " + logobw_path + 
						auctionService.getTeams().get(match.getTeam().get(i).getTeamId()-1).getTeamName4() + AuctionUtil.PNG_EXTENSION + ";");
				
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgBase02_0" + (i+1) + " " + base_path2 + 
						auctionService.getTeams().get(match.getTeam().get(i).getTeamId()-1).getTeamName4() + AuctionUtil.PNG_EXTENSION + ";");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgBase01_0" + (i+1) + " " + base_path1 + 
						auctionService.getTeams().get(match.getTeam().get(i).getTeamId()-1).getTeamName4() + AuctionUtil.PNG_EXTENSION + ";");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgText01_0" + (i+1) + " " + text1_path + 
						auctionService.getTeams().get(match.getTeam().get(i).getTeamId()-1).getTeamName4() + AuctionUtil.PNG_EXTENSION + ";");
			}
			
			if((Integer.valueOf(match.getTeam().get(i).getTeamTotalPurse()) - row) <= 0) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPurse0" + (i+1) + " " + 
						"-" + ";");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$Teams$"+ (i+1) +"$Purse02$Rupee*CONTAINER SET ACTIVE 0;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$Teams$"+ (i+1) +"$Purse02$Lakh*CONTAINER SET ACTIVE 0;");
			}else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPurse0" + (i+1) + " " + 
						AuctionFunctions.ConvertToLakh((Integer.valueOf(match.getTeam().get(i).getTeamTotalPurse()) - row)) + ";");
				
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$Teams$"+ (i+1) +"$Purse02$Rupee*CONTAINER SET ACTIVE 1;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$Teams$"+ (i+1) +"$Purse02$Lakh*CONTAINER SET ACTIVE 1;");
			}
//			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPurse0" + (i+1) + " " + 
//					AuctionFunctions.ConvertToLakh((Integer.valueOf(match.getTeam().get(i).getTeamTotalPurse()) - row)) + ";");
			row = 0;
			total = 0;
			rtmUsed = 0;
		}
	}
	
	public void populateRtmAvailable(boolean is_this_updating,PrintWriter print_writer,String viz_scene, Auction match,AuctionService auctionService, String session_selected_broadcaster) throws InterruptedException 
	{
		int row = 0,total = 0,rtmUsed =0;
		
		if(is_this_updating == false) {
			
			if(which_graphics_onscreen != "" && which_graphics_onscreen != "BG") {
				update_gfx = false;
			}
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + "SEASON 3 PLAYER AUCTION" + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + "INDIAN STREET PREMIER LEAGUE" + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + "" + ";");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead01 " + "TEAMS" + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead02 " + "RTM AVAILABLE" + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead03 " + "PURSE REMAINING" + ";");
			
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
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamFirstName0" + (i+1) + " " + 
						auctionService.getTeams().get(match.getTeam().get(i).getTeamId()-1).getTeamName2() + ";");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamLastName0" + (i+1) + " " + 
						auctionService.getTeams().get(match.getTeam().get(i).getTeamId()-1).getTeamName3() + ";");
				
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogoBase0" + (i+1) + " " + logo_base_path + 
						auctionService.getTeams().get(match.getTeam().get(i).getTeamId()-1).getTeamName4() + AuctionUtil.PNG_EXTENSION + ";");
				
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogo_0" + (i+1) + " " + logobw_path + 
						auctionService.getTeams().get(match.getTeam().get(i).getTeamId()-1).getTeamName4() + AuctionUtil.PNG_EXTENSION + ";");
				
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgBase02_0" + (i+1) + " " + base_path2 + 
						auctionService.getTeams().get(match.getTeam().get(i).getTeamId()-1).getTeamName4() + AuctionUtil.PNG_EXTENSION + ";");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgBase01_0" + (i+1) + " " + base_path1 + 
						auctionService.getTeams().get(match.getTeam().get(i).getTeamId()-1).getTeamName4() + AuctionUtil.PNG_EXTENSION + ";");
				
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgText01_0" + (i+1) + " " + text1_path + 
						auctionService.getTeams().get(match.getTeam().get(i).getTeamId()-1).getTeamName4() + AuctionUtil.PNG_EXTENSION + ";");
			}
			
			if((Integer.valueOf(match.getTeam().get(i).getTeamTotalPurse()) - row) <= 0) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPurse0" + (i+1) + " " + 
						"-" + ";");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$Teams$"+ (i+1) +"$Purse02$Rupee*CONTAINER SET ACTIVE 0;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$Teams$"+ (i+1) +"$Purse02$Lakh*CONTAINER SET ACTIVE 0;");
			}else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPurse0" + (i+1) + " " + 
						AuctionFunctions.ConvertToLakh((Integer.valueOf(match.getTeam().get(i).getTeamTotalPurse()) - row)) + ";");
				
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$Teams$"+ (i+1) +"$Purse02$Rupee*CONTAINER SET ACTIVE 1;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$Teams$"+ (i+1) +"$Purse02$Lakh*CONTAINER SET ACTIVE 1;");
			}
			
//			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPurse0" + (i+1) + " " + 
//					AuctionFunctions.ConvertToLakh((Integer.valueOf(match.getTeam().get(i).getTeamTotalPurse()) - row)) + ";");
			row = 0;
			total = 0;
		}
	}
	
	public void populateSlotsRemaining(boolean is_this_updating,PrintWriter print_writer,String viz_scene, Auction match,AuctionService auctionService, String session_selected_broadcaster) throws InterruptedException 
	{
		int row = 0,total = 0,squadSize =0;
		
		if(is_this_updating == false) {
			
			if(which_graphics_onscreen != "" && which_graphics_onscreen != "BG") {
				update_gfx = false;
			}
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + 
					"SEASON 3 PLAYER AUCTION" + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + 
					"INDIAN STREET PREMIER LEAGUE" + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + 
					"" + ";");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead01 " + 
					"" + ";");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead02 " + 
					"SLOTS REMAINING" + ";");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead03 " + 
					"PURSE REMAINING" + ";");
			
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
					(18-squadSize) + ";");
			squadSize = 0;
			
			if(is_this_updating == false) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamFirstName0" + (i+1) + " " + 
						auctionService.getTeams().get(match.getTeam().get(i).getTeamId()-1).getTeamName2() + ";");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamLastName0" + (i+1) + " " + 
						auctionService.getTeams().get(match.getTeam().get(i).getTeamId()-1).getTeamName3() + ";");
				
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogoBase0" + (i+1) + " " + logo_base_path + 
						auctionService.getTeams().get(match.getTeam().get(i).getTeamId()-1).getTeamName4() + AuctionUtil.PNG_EXTENSION + ";");
				
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogo0" + (i+1) + " " + logo_path + 
						auctionService.getTeams().get(match.getTeam().get(i).getTeamId()-1).getTeamName4() + AuctionUtil.PNG_EXTENSION + ";");
			}
			
			if((Integer.valueOf(match.getTeam().get(i).getTeamTotalPurse()) - row) <= 0) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPurse0" + (i+1) + " " + 
						"-" + ";");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$Teams$"+ (i+1) +"$Purse02$Rupee*CONTAINER SET ACTIVE 0;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$Teams$"+ (i+1) +"$Purse02$Lakh*CONTAINER SET ACTIVE 0;");
			}else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPurse0" + (i+1) + " " + 
						AuctionFunctions.ConvertToLakh((Integer.valueOf(match.getTeam().get(i).getTeamTotalPurse()) - row)) + ";");
				
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$Teams$"+ (i+1) +"$Purse02$Rupee*CONTAINER SET ACTIVE 1;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$Teams$"+ (i+1) +"$Purse02$Lakh*CONTAINER SET ACTIVE 1;");
			}
//			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPurse0" + (i+1) + " " + 
//					AuctionFunctions.ConvertToLakh((Integer.valueOf(match.getTeam().get(i).getTeamTotalPurse()) - row)) + ";");
			row = 0;
			total = 0;
		}
	}
	
	public void populateRemainingPurse(boolean is_this_updating,PrintWriter print_writer,String viz_scene, Auction match,AuctionService auctionService, String session_selected_broadcaster) throws InterruptedException 
	{
		int row = 0,total = 0;
		
		if(is_this_updating == false) {
			
			if(which_graphics_onscreen != "" && which_graphics_onscreen != "BG") {
				update_gfx = false;
			}
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + "PLAYER AUCTION" + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + "HBL PAKISTAN SUPER LEAGUE" + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + "" + ";");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead01 " + "TEAMS" + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead02 " + "SQUAD SIZE" + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead03 " + "PURSE REMAINING" + ";");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHeadLogo_01 " + 
					logo_path + "EVENT" + AuctionUtil.PNG_EXTENSION + ";");
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
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamFirstName0" + (i+1) + " " + 
						auctionService.getTeams().get(match.getTeam().get(i).getTeamId()-1).getTeamName2() + ";");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamLastName0" + (i+1) + " " + 
						auctionService.getTeams().get(match.getTeam().get(i).getTeamId()-1).getTeamName3() + ";");
				
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogo_0" + (i+1) + " " + logo_path + 
						auctionService.getTeams().get(match.getTeam().get(i).getTeamId()-1).getTeamBadge() + AuctionUtil.PNG_EXTENSION + ";");
			}
			
			if((Integer.valueOf(match.getTeam().get(i).getTeamTotalPurse()) - row) <= 0) {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPurse0" + (i+1) + " " + 
						"-" + ";");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$Teams$"+ (i+1) +"$Purse02$Rupee*CONTAINER SET ACTIVE 0;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$Teams$"+ (i+1) +"$Purse02$Lakh*CONTAINER SET ACTIVE 0;");
			}else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPurse0" + (i+1) + " " + 
						AuctionFunctions.formatAmountInCrore((Double.valueOf(match.getTeam().get(i).getTeamTotalPurse()) - row)).split(",")[0]  + ";");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tUnit0" + (i+1) + " " + 
						" " + AuctionFunctions.formatAmountInCrore((Double.valueOf(match.getTeam().get(i).getTeamTotalPurse()) - row)).split(",")[1]  + ";");
				
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$Teams$"+ (i+1) +"$Purse02$Rupee*CONTAINER SET ACTIVE 1;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$Teams$"+ (i+1) +"$Purse02$Lakh*CONTAINER SET ACTIVE 1;");
			}
			
//			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPurse0" + (i+1) + " " + 
//					AuctionFunctions.ConvertToLakh((Integer.valueOf(match.getTeam().get(i).getTeamTotalPurse()) - row)) + ";");
			row = 0;
			total = 0;
		}
	}
	
	public void populateRemainingPurseSingle(boolean is_this_updating,PrintWriter print_writer,String viz_scene,int team_id , Auction match,AuctionService auctionService, String session_selected_broadcaster) throws Exception 
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
			
			if(which_graphics_onscreen != "" && which_graphics_onscreen != "BG") {
				update_gfx = false;
			}
			
//			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + 
//					"ISPL PLAYER\nAUCTION" + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText02 " + 
					"PURSE REMAINING" + ";");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgBase02 " + base_path2 + 
					match.getTeam().get(team_id-1).getTeamName4() + AuctionUtil.PNG_EXTENSION + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgBase01 " + base_path1 + 
					match.getTeam().get(team_id-1).getTeamName4() + AuctionUtil.PNG_EXTENSION + ";");

//			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText02 " + 
//					"100 L" + ";");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogoBase " + logo_path + 
					match.getTeam().get(team_id-1).getTeamName4() + AuctionUtil.PNG_EXTENSION + ";");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogo " + logo_path + 
					match.getTeam().get(team_id-1).getTeamName4() + AuctionUtil.PNG_EXTENSION + ";");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + 
					match.getTeam().get(team_id-1).getTeamName2() + ";");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + 
					match.getTeam().get(team_id-1).getTeamName3() + ";");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + 
					"ISPL SEASON 3 PLAYER AUCTION" + ";");
		}
		
		if((Integer.valueOf(match.getTeam().get(team_id - 1).getTeamTotalPurse()) - row) <= 0) {
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBidPrice " + 
					"-" + ";");
//			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$Teams$"+ (i+1) +"$Purse02$Rupee*CONTAINER SET ACTIVE 0;");
//			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$Teams$"+ (i+1) +"$Purse02$Lakh*CONTAINER SET ACTIVE 0;");
		}else {
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBidPrice " + 
					AuctionFunctions.ConvertToLakh((Integer.valueOf(match.getTeam().get(team_id-1).getTeamTotalPurse()) - row)) + ";");
			
//			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$Teams$"+ (i+1) +"$Purse02$Rupee*CONTAINER SET ACTIVE 1;");
//			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$Teams$"+ (i+1) +"$Purse02$Lakh*CONTAINER SET ACTIVE 1;");
		}
		
//		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBidPrice " + 
//				AuctionFunctions.ConvertToLakh((Integer.valueOf(match.getTeam().get(team_id-1).getTeamTotalPurse()) - row)) + ";");
		row = 0;
		match.setTeamZoneList(AuctionFunctions.PlayerCountPerTeamZoneWise(match.getTeam(), match.getPlayers(), match.getPlayersList()));
		PlayerCount teamZone = match.getTeamZoneList().get(team_id-1);
		for(int j=1;j<=6;j++) {
			
			String zoneName = (j == 1 ? "NORTH ZONE" :
                j == 2 ? "EAST ZONE" :
                j == 3 ? "WEST ZONE" :
                j == 4 ? "SOUTH ZONE" :
                j == 5 ? "CENTRAL ZONE" : "UNDER 19");
			
			// Zone values
            String zoneValue = (j == 1 ? String.valueOf(teamZone.getNorthZone()) :
                    j == 2 ? String.valueOf(teamZone.getEastZone()) :
                    j == 3 ? String.valueOf(teamZone.getWestZone()) :
                    j == 4 ? String.valueOf(teamZone.getSouthZone()) :
                    j == 5 ? String.valueOf(teamZone.getCentralZone()) : String.valueOf(teamZone.getU19()));
            
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead0" + j+" " +
					zoneName + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue0" + j+" " +
					zoneValue + ";");
			
		}
		//TOTAL TEAM SIZE
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText01 SQUAD SIZE ;");
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSquadSize " +
				(teamZone.getEastZone() + teamZone.getWestZone() + teamZone.getNorthZone() + teamZone.getSouthZone() +
				 teamZone.getCentralZone() + teamZone.getU19()) + ";");
	}
	
	public void populateSquad20(boolean is_this_updating,PrintWriter print_writer,String viz_scene,int team_id , Auction match,AuctionService auctionService, String session_selected_broadcaster) throws InterruptedException 
	{
		int row = 0,squadsize = 0;
		int remaining = 0;
		data_str.clear();
		
		data_str = AuctionFunctions.getSquadDataInZone(match, team_id);
		
		if(is_this_updating == false) {
			
			if(which_graphics_onscreen != "" && which_graphics_onscreen != "BG") {
				update_gfx = false;
			}
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + 
					auctionService.getTeams().get(team_id - 1).getTeamName1() + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + 
					auctionService.getTeams().get(team_id - 1).getTeamName1() + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + 
					"SQUAD" + ";");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogoBase " + logo_base_path + 
					auctionService.getTeams().get(team_id - 1).getTeamBadge() + AuctionUtil.PNG_EXTENSION + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogo_01 " + logo_path + 
					auctionService.getTeams().get(team_id - 1).getTeamBadge() + AuctionUtil.PNG_EXTENSION + ";");
			//base1&2
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgBase01 " + base_path1 + 
					auctionService.getTeams().get(team_id - 1).getTeamBadge() + AuctionUtil.PNG_EXTENSION + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgBase02 " + base_path2 + 
					auctionService.getTeams().get(team_id - 1).getTeamBadge() + AuctionUtil.PNG_EXTENSION + ";");
			
			//base
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamColor01 " + base_path1 + 
					auctionService.getTeams().get(team_id - 1).getTeamBadge() + AuctionUtil.PNG_EXTENSION + ";");
			
			//text colour
	
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamText01 " + text1_path + 
					auctionService.getTeams().get(team_id - 1).getTeamBadge() + AuctionUtil.PNG_EXTENSION + ";");
			
			 
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$PlayersGrp$9_to_12$11$2$Data_GRP*CONTAINER SET ACTIVE 1;");
			
		}
		
		remaining = Integer.valueOf(auctionService.getTeams().get(team_id - 1).getTeamTotalPurse());
		
		for(int k=1;k<=20;k++) {
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectDataType0" + k + " 0;");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vDataType0" + k + " 2;");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$"+ k+"$Player$Base*TEXTURE2 SET TEXTURE_PATH " + base_path1 +  
					 auctionService.getTeams().get(team_id - 1).getTeamBadge() + AuctionUtil.PNG_EXTENSION + ";");
			
		}
		
		for(int k=0;k<=data_str.size()-1;k++) {
			row = row + 1;
			
			if(data_str.get(k).equalsIgnoreCase("NZ") || data_str.get(k).equalsIgnoreCase("SZ") || data_str.get(k).equalsIgnoreCase("CZ") || 
					data_str.get(k).equalsIgnoreCase("EZ") || data_str.get(k).equalsIgnoreCase("WZ") || data_str.get(k).equalsIgnoreCase("U19")
					|| data_str.get(k).equalsIgnoreCase("ZONE")) {
				
				if(row >= 13 && row <= 16) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$PlayersGrp$13_to_16$" + row + "$1"
							+ "$REMAINING*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$PlayersGrp$13_to_16$" + row + "$1"
							+ "$Pic*CONTAINER SET ACTIVE 1;");
				}else {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$PlayersGrp$13_to_16$" + row + "$1"
							+ "$REMAINING*CONTAINER SET ACTIVE 1;");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$PlayersGrp$13_to_16$" + row + "$1"
							+ "$Pic*CONTAINER SET ACTIVE 0;");
				}
				
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectDataType0" + row + " 0;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelect_Players " + row + ";");
				
				
				switch (data_str.get(k)) {
				case "NZ":
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgZone0" + row + " " + 
							icon_path + "NORTH" + AuctionUtil.PNG_EXTENSION + ";");
					break;
				case "EZ":
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgZone0" + row + " " + 
							icon_path + "EAST" + AuctionUtil.PNG_EXTENSION + ";");
					break;
				case "WZ":
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgZone0" + row + " " + 
							icon_path + "WEST" + AuctionUtil.PNG_EXTENSION + ";");
					break;	
				case "SZ":
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgZone0" + row + " " + 
							icon_path + "SOUTH" + AuctionUtil.PNG_EXTENSION + ";");
					break;
				case "CZ":
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgZone0" + row + " " + 
							icon_path + "CENTRAL" + AuctionUtil.PNG_EXTENSION + ";");
					break;
				case "U19":
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgZone0" + row + " " + 
							icon_path + "U19" + AuctionUtil.PNG_EXTENSION + ";");
					break;
				case "ZONE":
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgZone0" + row + " " + 
							icon_path + row + AuctionUtil.PNG_EXTENSION + ";");
					break;	
				}
			}else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelect_Players " + row + ";");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectDataType0" + row + " 2;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$PlayersGrp$1_to_6$"+ row+"$2$Data_GRP$Name*TEXTURE2 SET TEXTURE_PATH " + text1_path +  
						 auctionService.getTeams().get(team_id - 1).getTeamName4() + AuctionUtil.PNG_EXTENSION + ";");
				 print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$PlayersGrp$1_to_6$"+ row+"$2$Data_GRP$Lastname*TEXTURE2 SET TEXTURE_PATH " + text1_path +  
						 auctionService.getTeams().get(team_id - 1).getTeamName4() + AuctionUtil.PNG_EXTENSION + ";");
				 print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$PlayersGrp$1_to_6$"+ row+"$2$Data_GRP$Zone*TEXTURE2 SET TEXTURE_PATH " + text1_path +  
						 auctionService.getTeams().get(team_id - 1).getTeamName4() + AuctionUtil.PNG_EXTENSION + ";");				 
				for(Player plyr : match.getPlayersList()) {
					if(plyr.getPlayerId() == Integer.valueOf(data_str.get(k))) {
						
						squadsize++;
						
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vDataType0" + row + " 1;");
						
						
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFrom0"+ row + " " + 
								"" + ";");
						if(plyr.getIconic()!=null && plyr.getIconic().equalsIgnoreCase(AuctionUtil.YES)) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vPlayerInfo0" + row + " 1" + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerInfo0" + row + " " + 
									icon_path + "Icon" + AuctionUtil.PNG_EXTENSION + ";");
							
						}else {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vPlayerInfo0" + row + " 0" + ";");
						}
						
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vPlayerInfo0" + row + " 0" + ";");
						
						if(plyr.getNationality().equalsIgnoreCase("PAKISTAN")) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vOverseas0" + row + " 0;");
						}else {
							print_writer.println("LAYER" + current_layer + "EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vOverseas0" + row + " 1;");
						}
						
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPurse0" + row 
								 + " " + AuctionFunctions.formatAmountInCrore(plyr.getSoldForPoints()).split(",")[0] + ";");
						
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tUnit0" + row 
								 + " " + AuctionFunctions.formatAmountInCrore(plyr.getSoldForPoints()).split(",")[1] + ";");

						
						if(plyr.getSurname() != null) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName0"+ row + " " + 
									plyr.getFirstname() + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName0"+ row + " " + 
									plyr.getSurname() + ";");
						}else {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName0"+ row + " " + 
									plyr.getFirstname() + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName0"+ row + " ;");
						}
						
						if(plyr.getRole() != null &&
								!plyr.getRole().isEmpty()) {
							
							if(plyr.getRole().toUpperCase().equalsIgnoreCase("WICKET-KEEPER")) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgIcon0" + row + " " + 
										icon_path + "Keeper" + AuctionUtil.PNG_EXTENSION + ";");
							}else {
								if(plyr.getRole().toUpperCase().equalsIgnoreCase("BATSMAN") || 
										plyr.getRole().toUpperCase().equalsIgnoreCase("BAT/KEEPER")) {
									if(plyr.getBatsmanStyle().toUpperCase().equalsIgnoreCase("RHB")) {
										
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgIcon0" + row + " " + 
												icon_path + "Batsman" + AuctionUtil.PNG_EXTENSION + ";");
									}
									else if(plyr.getBatsmanStyle().toUpperCase().equalsIgnoreCase("LHB")) {
										
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgIcon0" + row + " " + 
												icon_path + "Batsman_Lefthand" + AuctionUtil.PNG_EXTENSION + ";");
									}
								}else if(plyr.getRole().toUpperCase().equalsIgnoreCase("BOWLER")) {
									if(plyr.getBowlerStyle() == null) {
										
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgIcon0" + row + " " + 
												icon_path + "FastBowler" + AuctionUtil.PNG_EXTENSION + ";");
									}else {
										switch(plyr.getBowlerStyle().toUpperCase()) {
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
								}else if(plyr.getRole().toUpperCase().equalsIgnoreCase("ALL-ROUNDER")) {
									if(plyr.getBowlerStyle() == null) {
										
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgIcon0" + row + " " + 
												icon_path + "FastBowlerAllrounder" + AuctionUtil.PNG_EXTENSION + ";");
									}else {
										switch(plyr.getBowlerStyle().toUpperCase()) {
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
						
						break;
					}
				}
			}
		}
		
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSquadSize " + squadsize + ";");
		
		squadsize = 0;
		
		PlayerCount teamZone = match.getTeamZoneList().get(team_id-1);
		for(int j=1;j<=6;j++) {
			
			String zoneName = (j == 1 ? "NZ" :
                j == 2 ? "CZ" :
                j == 3 ? "EZ" :
                j == 4 ? "WZ" :
                j == 5 ? "SZ" : "U19");
			
			// Zone values
            String zoneValue = (j == 1 ? String.valueOf(teamZone.getNorthZone()) :
                    j == 2 ? String.valueOf(teamZone.getCentralZone()) :
                    j == 3 ? String.valueOf(teamZone.getEastZone()) :
                    j == 4 ? String.valueOf(teamZone.getWestZone()) :
                    j == 5 ? String.valueOf(teamZone.getSouthZone()) : String.valueOf(teamZone.getU19()));
            
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tZone0" + j+" " +
					zoneName + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tZoneValue0" + j+" " +
					zoneValue + ";");
			
		}
		//TOTAL TEAM SIZE
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSquadSize " +
				(teamZone.getEastZone() + teamZone.getWestZone() + teamZone.getNorthZone() + teamZone.getSouthZone() +
				 teamZone.getCentralZone() + teamZone.getU19()) + ";");
		
//		if(data_str.get(12).equalsIgnoreCase("ZONE") && data_str.get(13).equalsIgnoreCase("ZONE") && data_str.get(14).equalsIgnoreCase("ZONE")
//				&& data_str.get(15).equalsIgnoreCase("ZONE") || data_str.get(13).equalsIgnoreCase("ZONE") && data_str.get(14).equalsIgnoreCase("ZONE")
//				&& data_str.get(15).equalsIgnoreCase("ZONE") || data_str.get(14).equalsIgnoreCase("ZONE") && data_str.get(15).equalsIgnoreCase("ZONE") 
//				|| data_str.get(15).equalsIgnoreCase("ZONE")) {
//			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$Remaining*CONTAINER SET ACTIVE 1;");
//			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vFourth 2;");
//			
//			if(match.getPlayers() != null) {
//				for(int j=0; j <= match.getPlayers().size()-1; j++) {
//					if(match.getPlayers().get(j).getTeamId() == team_id) {
//						remaining = remaining - match.getPlayers().get(j).getSoldForPoints();
//					}
//				}
//			}
//			
//		}else {
//			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$Remaining*CONTAINER SET ACTIVE 0;");
//			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vFourth 3;");
//		}
		
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectTopRight 2;");
		
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRemainingHead PURSE REMAINING;");

		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRemainingValue " + AuctionFunctions.ConvertToLakh(remaining) + ";");

	}
	public void populateOnlySquad(boolean is_this_updating,PrintWriter print_writer,String viz_scene,int team_id , Auction match,AuctionService auctionService, String session_selected_broadcaster) throws InterruptedException 
	{
		int row = 0,squadsize = 0;
		int remaining = 0;
		data_str.clear();
		
		data_str = AuctionFunctions.getSquadDataInZone(match, team_id);
		
		if(is_this_updating == false) {
			
			if(which_graphics_onscreen != "" && which_graphics_onscreen != "BG") {
				update_gfx = false;
			}
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + 
					auctionService.getTeams().get(team_id - 1).getTeamName2() + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + 
					auctionService.getTeams().get(team_id - 1).getTeamName3() + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + 
					"SQUAD" + ";");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogoBase " + logo_base_path + 
					auctionService.getTeams().get(team_id - 1).getTeamBadge() + AuctionUtil.PNG_EXTENSION + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogo_01 " + logo_path + 
					auctionService.getTeams().get(team_id - 1).getTeamBadge() + AuctionUtil.PNG_EXTENSION + ";");
			//base1&2
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgBase01 " + base_path1 + 
					auctionService.getTeams().get(team_id - 1).getTeamBadge() + AuctionUtil.PNG_EXTENSION + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgBase02 " + base_path2 + 
					auctionService.getTeams().get(team_id - 1).getTeamBadge() + AuctionUtil.PNG_EXTENSION + ";");
			
			//base
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamColor01 " + base_path1 + 
					auctionService.getTeams().get(team_id - 1).getTeamBadge() + AuctionUtil.PNG_EXTENSION + ";");
			
			//text colour
	
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamTextColor01 " + text1_path + 
					auctionService.getTeams().get(team_id - 1).getTeamBadge() + AuctionUtil.PNG_EXTENSION + ";");
			
			 
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$PlayersGrp$9_to_12$11$2$Data_GRP*CONTAINER SET ACTIVE 1;");
			
		}
		
		remaining = Integer.valueOf(auctionService.getTeams().get(team_id - 1).getTeamTotalPurse());
		
		for(int k=1;k<=18;k++) {
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectDataType0" + row + " 0;");
		}
		
		for(int k=0;k<=data_str.size()-1;k++) {
			row = row + 1;
			
			if(data_str.get(k).equalsIgnoreCase("NZ") || data_str.get(k).equalsIgnoreCase("SZ") || data_str.get(k).equalsIgnoreCase("CZ") || 
					data_str.get(k).equalsIgnoreCase("EZ") || data_str.get(k).equalsIgnoreCase("WZ") || data_str.get(k).equalsIgnoreCase("U19")
					|| data_str.get(k).equalsIgnoreCase("ZONE")) {
				
				if(row >= 13 && row <= 16) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$PlayersGrp$13_to_16$" + row + "$1"
							+ "$REMAINING*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$PlayersGrp$13_to_16$" + row + "$1"
							+ "$Pic*CONTAINER SET ACTIVE 1;");
				}else {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$PlayersGrp$13_to_16$" + row + "$1"
							+ "$REMAINING*CONTAINER SET ACTIVE 1;");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$PlayersGrp$13_to_16$" + row + "$1"
							+ "$Pic*CONTAINER SET ACTIVE 0;");
				}
				
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectDataType0" + row + " 0;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelect_Players " + row + ";");
				
				
				switch (data_str.get(k)) {
				case "NZ":
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgZone0" + row + " " + 
							icon_path + "NORTH" + AuctionUtil.PNG_EXTENSION + ";");
					break;
				case "EZ":
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgZone0" + row + " " + 
							icon_path + "EAST" + AuctionUtil.PNG_EXTENSION + ";");
					break;
				case "WZ":
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgZone0" + row + " " + 
							icon_path + "WEST" + AuctionUtil.PNG_EXTENSION + ";");
					break;	
				case "SZ":
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgZone0" + row + " " + 
							icon_path + "SOUTH" + AuctionUtil.PNG_EXTENSION + ";");
					break;
				case "CZ":
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgZone0" + row + " " + 
							icon_path + "CENTRAL" + AuctionUtil.PNG_EXTENSION + ";");
					break;
				case "U19":
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgZone0" + row + " " + 
							icon_path + "U19" + AuctionUtil.PNG_EXTENSION + ";");
					break;
				case "ZONE":
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgZone0" + row + " " + 
							icon_path + row + AuctionUtil.PNG_EXTENSION + ";");
					break;	
				}
			}else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelect_Players " + row + ";");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectDataType0" + row + " 2;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$PlayersGrp$1_to_6$"+ row+"$2$Data_GRP$Name*TEXTURE2 SET TEXTURE_PATH " + text1_path +  
						 auctionService.getTeams().get(team_id - 1).getTeamName4() + AuctionUtil.PNG_EXTENSION + ";");
				 print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$PlayersGrp$1_to_6$"+ row+"$2$Data_GRP$Lastname*TEXTURE2 SET TEXTURE_PATH " + text1_path +  
						 auctionService.getTeams().get(team_id - 1).getTeamName4() + AuctionUtil.PNG_EXTENSION + ";");
				 print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$PlayersGrp$1_to_6$"+ row+"$2$Data_GRP$Zone*TEXTURE2 SET TEXTURE_PATH " + text1_path +  
						 auctionService.getTeams().get(team_id - 1).getTeamName4() + AuctionUtil.PNG_EXTENSION + ";");				 
				for(Player plyr : match.getPlayersList()) {
					if(plyr.getPlayerId() == Integer.valueOf(data_str.get(k))) {
						
						squadsize++;
						
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage0" + row + " " + 
								photo_path + plyr.getPhotoName() + AuctionUtil.PNG_EXTENSION + ";");
						
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFrom0"+ row + " " + 
								"" + ";");
						if(plyr.getIconic()!=null && plyr.getIconic().equalsIgnoreCase(AuctionUtil.YES)) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vPlayerInfo0" + row + " 1" + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerInfo0" + row + " " + 
									icon_path + "Icon" + AuctionUtil.PNG_EXTENSION + ";");
							
						}else {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vPlayerInfo0" + row + " 0" + ";");
						}
						
						if(plyr.getSoldOrUnsold().equalsIgnoreCase("RETAIN")) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vPlayerInfo0" + row + " 1" + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerInfo0" + row + " " + 
									icon_path + "Retain" + AuctionUtil.PNG_EXTENSION + ";");
							
						}else {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vPlayerInfo0" + row + " 0" + ";");
						}
						
						if(plyr.getNationality().equalsIgnoreCase("PAKISTAN")) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vOverseas0" + row + " 0;");
						}else {
							print_writer.println("LAYER" + current_layer + "EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vOverseas0" + row + " 1;");
						}
						
						if(plyr.getSurname() != null) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFirstname0"+ row + " " + 
									plyr.getFirstname() + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastname0"+ row + " " + 
									plyr.getSurname() + ";");
						}else {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFirstname0"+ row + " " + 
									plyr.getFirstname() + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastname0"+ row + " ;");
						}
						
						if(plyr.getRole() != null &&
								!plyr.getRole().isEmpty()) {
							
							if(plyr.getRole().toUpperCase().equalsIgnoreCase("WICKET-KEEPER")) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerRole0" + row + " " + 
										icon_path + "Keeper" + AuctionUtil.PNG_EXTENSION + ";");
							}else {
								if(plyr.getRole().toUpperCase().equalsIgnoreCase("BATSMAN") || 
										plyr.getRole().toUpperCase().equalsIgnoreCase("BAT/KEEPER")) {
									if(plyr.getBatsmanStyle().toUpperCase().equalsIgnoreCase("RHB")) {
										
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerRole0" + row + " " + 
												icon_path + "Batsman" + AuctionUtil.PNG_EXTENSION + ";");
									}
									else if(plyr.getBatsmanStyle().toUpperCase().equalsIgnoreCase("LHB")) {
										
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerRole0" + row + " " + 
												icon_path + "Batsman_Lefthand" + AuctionUtil.PNG_EXTENSION + ";");
									}
								}else if(plyr.getRole().toUpperCase().equalsIgnoreCase("BOWLER")) {
									if(plyr.getBowlerStyle() == null) {
										
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerRole0" + row + " " + 
												icon_path + "FastBowler" + AuctionUtil.PNG_EXTENSION + ";");
									}else {
										switch(plyr.getBowlerStyle().toUpperCase()) {
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
								}else if(plyr.getRole().toUpperCase().equalsIgnoreCase("ALL-ROUNDER")) {
									if(plyr.getBowlerStyle() == null) {
										
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerRole0" + row + " " + 
												icon_path + "FastBowlerAllrounder" + AuctionUtil.PNG_EXTENSION + ";");
									}else {
										switch(plyr.getBowlerStyle().toUpperCase()) {
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
						
						break;
					}
				}
			}
		}
		
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSquadSize " + squadsize + ";");
		
		squadsize = 0;
		
		PlayerCount teamZone = match.getTeamZoneList().get(team_id-1);
		for(int j=1;j<=6;j++) {
			
			String zoneName = (j == 1 ? "NZ" :
                j == 2 ? "CZ" :
                j == 3 ? "EZ" :
                j == 4 ? "WZ" :
                j == 5 ? "SZ" : "U19");
			
			// Zone values
            String zoneValue = (j == 1 ? String.valueOf(teamZone.getNorthZone()) :
                    j == 2 ? String.valueOf(teamZone.getCentralZone()) :
                    j == 3 ? String.valueOf(teamZone.getEastZone()) :
                    j == 4 ? String.valueOf(teamZone.getWestZone()) :
                    j == 5 ? String.valueOf(teamZone.getSouthZone()) : String.valueOf(teamZone.getU19()));
            
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tZone0" + j+" " +
					zoneName + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tZoneValue0" + j+" " +
					zoneValue + ";");
			
		}
		//TOTAL TEAM SIZE
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSquadSize " +
				(teamZone.getEastZone() + teamZone.getWestZone() + teamZone.getNorthZone() + teamZone.getSouthZone() +
				 teamZone.getCentralZone() + teamZone.getU19()) + ";");
		
//		if(data_str.get(12).equalsIgnoreCase("ZONE") && data_str.get(13).equalsIgnoreCase("ZONE") && data_str.get(14).equalsIgnoreCase("ZONE")
//				&& data_str.get(15).equalsIgnoreCase("ZONE") || data_str.get(13).equalsIgnoreCase("ZONE") && data_str.get(14).equalsIgnoreCase("ZONE")
//				&& data_str.get(15).equalsIgnoreCase("ZONE") || data_str.get(14).equalsIgnoreCase("ZONE") && data_str.get(15).equalsIgnoreCase("ZONE") 
//				|| data_str.get(15).equalsIgnoreCase("ZONE")) {
//			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$Remaining*CONTAINER SET ACTIVE 1;");
//			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vFourth 2;");
//			
//			if(match.getPlayers() != null) {
//				for(int j=0; j <= match.getPlayers().size()-1; j++) {
//					if(match.getPlayers().get(j).getTeamId() == team_id) {
//						remaining = remaining - match.getPlayers().get(j).getSoldForPoints();
//					}
//				}
//			}
//			
//		}else {
//			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$Remaining*CONTAINER SET ACTIVE 0;");
//			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vFourth 3;");
//		}
		
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectTopRight 2;");
		
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRemainingHead PURSE REMAINING;");

		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRemainingValue " + AuctionFunctions.ConvertToLakh(remaining) + ";");

	}
	
	public void populateSquad(boolean is_this_updating,PrintWriter print_writer,String viz_scene,int team_id , Auction match,AuctionService auctionService, String session_selected_broadcaster) throws InterruptedException 
	{
		int row = 0,squadsize = 0;
		int remaining = 0;
		data_str.clear();
		
		data_str = AuctionFunctions.getSquadDataInZone(match, team_id);
		
		if(is_this_updating == false) {
			
			if(which_graphics_onscreen != "" && which_graphics_onscreen != "BG") {
				update_gfx = false;
			}
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + 
					auctionService.getTeams().get(team_id - 1).getTeamName2() + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + 
					auctionService.getTeams().get(team_id - 1).getTeamName3() + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + 
					"SQUAD" + ";");
			
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogo_01 " + logo_path + 
					auctionService.getTeams().get(team_id - 1).getTeamBadge() + AuctionUtil.PNG_EXTENSION + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogoBase " + logo_base_path + 
					auctionService.getTeams().get(team_id - 1).getTeamBadge() + AuctionUtil.PNG_EXTENSION + ";");
			
			//base1&2
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamColor01 " + base_path1 + 
					auctionService.getTeams().get(team_id - 1).getTeamBadge() + AuctionUtil.PNG_EXTENSION + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgBase02 " + base_path2 + 
					auctionService.getTeams().get(team_id - 1).getTeamBadge() + AuctionUtil.PNG_EXTENSION + ";");
			
			//base
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamColor01 " + base_path1 + 
					auctionService.getTeams().get(team_id - 1).getTeamBadge() + AuctionUtil.PNG_EXTENSION + ";");
			
			//text colour
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamTextColor01 " + text1_path + 
					auctionService.getTeams().get(team_id - 1).getTeamBadge() + AuctionUtil.PNG_EXTENSION + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$PlayersGrp$9_to_12$11$2$Data_GRP*CONTAINER SET ACTIVE 1;");
			
		}
		
		remaining = Integer.valueOf(auctionService.getTeams().get(team_id - 1).getTeamTotalPurse());
		
		for(int k=1;k<=18;k++) {
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectDataType0" + row + " 0;");
		}
		
		for(int k=0;k<=data_str.size()-1;k++) {
			row = row + 1;
			
			if(data_str.get(k).equalsIgnoreCase("NZ") || data_str.get(k).equalsIgnoreCase("SZ") || data_str.get(k).equalsIgnoreCase("CZ") || 
					data_str.get(k).equalsIgnoreCase("EZ") || data_str.get(k).equalsIgnoreCase("WZ") || data_str.get(k).equalsIgnoreCase("U19")
					|| data_str.get(k).equalsIgnoreCase("ZONE")) {
				
				if(row >= 13 && row <= 16) {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$PlayersGrp$13_to_16$" + row + "$1"
							+ "$REMAINING*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$PlayersGrp$13_to_16$" + row + "$1"
							+ "$Pic*CONTAINER SET ACTIVE 1;");
				}else {
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$PlayersGrp$13_to_16$" + row + "$1"
							+ "$REMAINING*CONTAINER SET ACTIVE 1;");
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$PlayersGrp$13_to_16$" + row + "$1"
							+ "$Pic*CONTAINER SET ACTIVE 0;");
				}
				
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectDataType0" + row + " 0;");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelect_Players " + row + ";");
				
				switch (data_str.get(k)) {
				case "NZ":
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgZone0" + row + " " + 
							icon_path + "NORTH" + AuctionUtil.PNG_EXTENSION + ";");
					break;
				case "EZ":
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgZone0" + row + " " + 
							icon_path + "EAST" + AuctionUtil.PNG_EXTENSION + ";");
					break;
				case "WZ":
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgZone0" + row + " " + 
							icon_path + "WEST" + AuctionUtil.PNG_EXTENSION + ";");
					break;	
				case "SZ":
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgZone0" + row + " " + 
							icon_path + "SOUTH" + AuctionUtil.PNG_EXTENSION + ";");
					break;
				case "CZ":
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgZone0" + row + " " + 
							icon_path + "CENTRAL" + AuctionUtil.PNG_EXTENSION + ";");
					break;
				case "U19":
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgZone0" + row + " " + 
							icon_path + "U19" + AuctionUtil.PNG_EXTENSION + ";");
					break;
				case "ZONE":
					print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgZone0" + row + " " + 
							icon_path + row + AuctionUtil.PNG_EXTENSION + ";");
					break;	
				}
			}else {
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelect_Players " + row + ";");
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectDataType0" + row + " 2;");
				
				print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$PlayersGrp$1_to_6$"+ row+"$2$Data_GRP$Name*TEXTURE2 SET TEXTURE_PATH " + text1_path +  
						 auctionService.getTeams().get(team_id - 1).getTeamName4() + AuctionUtil.PNG_EXTENSION + ";");
				 print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$PlayersGrp$1_to_6$"+ row+"$2$Data_GRP$Lastname*TEXTURE2 SET TEXTURE_PATH " + text1_path +  
						 auctionService.getTeams().get(team_id - 1).getTeamName4() + AuctionUtil.PNG_EXTENSION + ";");
				 print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$PlayersGrp$1_to_6$"+ row+"$2$Data_GRP$Zone*TEXTURE2 SET TEXTURE_PATH " + text1_path +  
						 auctionService.getTeams().get(team_id - 1).getTeamName4() + AuctionUtil.PNG_EXTENSION + ";");	
				
				for(Player plyr : match.getPlayersList()) {
					if(plyr.getPlayerId() == Integer.valueOf(data_str.get(k))) {
						
						squadsize++;
						
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage0" + row + " " + 
								photo_path + plyr.getPhotoName() + AuctionUtil.PNG_EXTENSION + ";");
						
						print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFrom0"+ row + " " + 
								"" + ";");
						if(plyr.getIconic()!=null && plyr.getIconic().equalsIgnoreCase(AuctionUtil.YES)) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vPlayerInfo0" + row + " 1" + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerInfo0" + row + " " + 
									icon_path + "Icon" + AuctionUtil.PNG_EXTENSION + ";");
							
						}else {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vPlayerInfo0" + row + " 0" + ";");
						}
						
						
						if(plyr.getNationality().equalsIgnoreCase("PAKISTAN")) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vOverseas0" + row + " 0;");
						}else {
							print_writer.println("LAYER" + current_layer + "EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vOverseas0" + row + " 1;");
						}
						
//						if(plyr.getSoldOrUnsold().equalsIgnoreCase("RETAIN")) {
//							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vPlayerInfo0" + row + " 1" + ";");
//							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerInfo0" + row + " " + 
//									icon_path + "Retain" + AuctionUtil.PNG_EXTENSION + ";");
//							
//						}
						
						if(plyr.getSurname() != null) {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFirstname0"+ row + " " + 
									plyr.getFirstname() + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastname0"+ row + " " + 
									plyr.getSurname() + ";");
						}else {
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFirstname0"+ row + " " + 
									plyr.getFirstname() + ";");
							print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastname0"+ row + " ;");
						}
						
						if(plyr.getRole() != null &&
								!plyr.getRole().isEmpty()) {
							
							if(plyr.getRole().toUpperCase().equalsIgnoreCase("WICKET-KEEPER")) {
								print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerRole0" + row + " " + 
										icon_path + "Keeper" + AuctionUtil.PNG_EXTENSION + ";");
							}else {
								if(plyr.getRole().toUpperCase().equalsIgnoreCase("BATSMAN") || 
										plyr.getRole().toUpperCase().equalsIgnoreCase("BAT/KEEPER")) {
									if(plyr.getBatsmanStyle().toUpperCase().equalsIgnoreCase("RHB")) {
										
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerRole0" + row + " " + 
												icon_path + "Batsman" + AuctionUtil.PNG_EXTENSION + ";");
									}
									else if(plyr.getBatsmanStyle().toUpperCase().equalsIgnoreCase("LHB")) {
										
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerRole0" + row + " " + 
												icon_path + "Batsman_Lefthand" + AuctionUtil.PNG_EXTENSION + ";");
									}
								}else if(plyr.getRole().toUpperCase().equalsIgnoreCase("BOWLER")) {
									if(plyr.getBowlerStyle() == null) {
										
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerRole0" + row + " " + 
												icon_path + "FastBowler" + AuctionUtil.PNG_EXTENSION + ";");
									}else {
										switch(plyr.getBowlerStyle().toUpperCase()) {
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
								}else if(plyr.getRole().toUpperCase().equalsIgnoreCase("ALL-ROUNDER")) {
									if(plyr.getBowlerStyle() == null) {
										
										print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerRole0" + row + " " + 
												icon_path + "FastBowlerAllrounder" + AuctionUtil.PNG_EXTENSION + ";");
									}else {
										switch(plyr.getBowlerStyle().toUpperCase()) {
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
						
						break;
					}
				}
			}
		}
		
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSquadSize " + squadsize + ";");
		
		squadsize = 0;
		
		PlayerCount teamZone = match.getTeamZoneList().get(team_id-1);
		for(int j=1;j<=6;j++) {
			
			String zoneName = (j == 1 ? "NZ" :
                j == 2 ? "CZ" :
                j == 3 ? "EZ" :
                j == 4 ? "WZ" :
                j == 5 ? "SZ" : "U19");
			
			// Zone values
            String zoneValue = (j == 1 ? String.valueOf(teamZone.getNorthZone()) :
                    j == 2 ? String.valueOf(teamZone.getCentralZone()) :
                    j == 3 ? String.valueOf(teamZone.getEastZone()) :
                    j == 4 ? String.valueOf(teamZone.getWestZone()) :
                    j == 5 ? String.valueOf(teamZone.getSouthZone()) : String.valueOf(teamZone.getU19()));
            
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tZone0" + j+" " +
					zoneName + ";");
			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tZoneValue0" + j+" " +
					zoneValue + ";");
			
		}
		//TOTAL TEAM SIZE
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSquadSize " +
				(teamZone.getEastZone() + teamZone.getWestZone() + teamZone.getNorthZone() + teamZone.getSouthZone() +
				 teamZone.getCentralZone() + teamZone.getU19()) + ";");
		
//		if(data_str.get(12).equalsIgnoreCase("ZONE") && data_str.get(13).equalsIgnoreCase("ZONE") && data_str.get(14).equalsIgnoreCase("ZONE")
//				&& data_str.get(15).equalsIgnoreCase("ZONE") || data_str.get(13).equalsIgnoreCase("ZONE") && data_str.get(14).equalsIgnoreCase("ZONE")
//				&& data_str.get(15).equalsIgnoreCase("ZONE") || data_str.get(14).equalsIgnoreCase("ZONE") && data_str.get(15).equalsIgnoreCase("ZONE") 
//				|| data_str.get(15).equalsIgnoreCase("ZONE")) {
//			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$Remaining*CONTAINER SET ACTIVE 1;");
//			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vFourth 2;");
//			
//			if(match.getPlayers() != null) {
//				for(int j=0; j <= match.getPlayers().size()-1; j++) {
//					if(match.getPlayers().get(j).getTeamId() == team_id) {
//						remaining = remaining - match.getPlayers().get(j).getSoldForPoints();
//					}
//				}
//			}
//			
//		}else {
//			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main$All$Remaining*CONTAINER SET ACTIVE 0;");
//			print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vFourth 3;");
//		}
		
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelectTopRight 2;");
		
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRemainingHead PURSE REMAINING;");

		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRemainingValue " + AuctionFunctions.formatRawAmount(remaining) + ";");
	}
	
	public void populateZone(boolean is_this_updating,PrintWriter print_writer,String viz_scene,int team_id , Auction match,AuctionService auctionService, String session_selected_broadcaster) throws InterruptedException 
	{
		int row = 0, East = 0,North = 0,West = 0,South = 0, Central = 0, U19 = 0;
		int remaining=0;
		if(is_this_updating == false) {
			
			if(which_graphics_onscreen != "" && which_graphics_onscreen != "BG") {
				update_gfx = false;
			}
			
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
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRemainingValue " + AuctionFunctions.ConvertToLakh(remaining) + ";");

	}
	public void populateIdent(PrintWriter print_writer, String viz_scene, String session_selected_broadcaster)
	{
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage " + logo_path + "EVENT" 
				+ AuctionUtil.PNG_EXTENSION + ";");
		
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFirstName " + "HBL PSL" + ";");
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastName " + "PLAYER AUCTION" + ";");
		
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText01 " + "EXPO CENTER, LAHORE" + ";");
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText02 " + "" + ";");
		
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText03 " + "" + ";");
		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tText04;");
		
		
	}
	
//	public void populateRULES(PrintWriter print_writer,String session_selected_broadcaster)
//	{
//		
//		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgText01_01 " + 
//				logo_path + "EVENT" + AuctionUtil.PNG_EXTENSION + ";");
//		
//		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " +"RULES"+ ";");
//		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " +" "+ ";");
//		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader HBL PSL PLAYER AUCTION;");
//		
//		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSelect 7;");
//		
//		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPointer01 " +"TEAMS CAN RETAIN UP TO FOUR PLAYERS FROM LAST SEASON"+ ";");
//		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPointer02 " +"THE NEW FRANCHISES WILL BE ALLOWED TO SELECT AND RETAIN A MAXIMUM OF FOUR PLAYERS "
//				+ "FROM THE LIST OF RELEASED PLAYERS"+ ";");
//		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPointer03 " +"EACH FRANCHISE MAY RETAIN A MAXIMUM OF FOUR PLAYERS "
//				+ "LIMITED TO ONE PLAYER PER CATEGORY I.E. FROM AMONGST PLATINUM, DIAMOND, GOLD, SILVER"
//				+ "AND EMERGING"+ ";");
//		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPointer04 " +"THE TOTAL AMOUNT SPENT ON RETAINED PLAYERS WILL BE DEDUCTED FROM THE FRANCHISE’S "
//				+ "SALARY CAP OF PKR 450,000,000 FOR THE PSL 2026 SEASON"+ ";");
//		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPointer05 " +"A TEAM MUST PICK AT LEAST TWO LOCAL PLAYERS WHO ARE UNDER THE AGE OF 23 AND HAVE NOT "
//				+ "BEEN CAPPED AT THE SENIOR INTERNATIONAL LEVEL FOR PAKISTAN"+ ";");
//		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPointer06 " +"AT THE END OF THE AUCTION, EACH TEAM MUST HAVE AT LEAST 16 PLAYERS ON ITS ROSTER, AND A "
//				+ "MAXIMUM OF 20 PLAYERS"+ ";");
//		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPointer07 " +"AT ANY TIME, A PLAYING XI MUST INCLUDE A MINIMUM OF THREE FOREIGN PLAYERS AND MAY "
//				+ "INCLUDE A MAXIMUM OF FOUR FOREIGN PLAYERS"+ ";");
//		print_writer.println("LAYER" + current_layer + "*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPointer08 " +"RULES"+ ";");
//		
//	}
	
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
		case "HANDBALL":  case "ISPL": case "PSL":
			switch(which_layer) {
			case 1:
				print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*" + animationName + " " + animationCommand + ";");
				break;
			case 2:
				print_writer.println("LAYER2*EVEREST*STAGE*DIRECTOR*" + animationName + " " + animationCommand + ";");
				break;
			case 3:
				print_writer.println("LAYER3*EVEREST*STAGE*DIRECTOR*" + animationName + " " + animationCommand + ";");
				break;
			case 4:
				print_writer.println("LAYER4*EVEREST*STAGE*DIRECTOR*" + animationName + " " + animationCommand + ";");
				break;
			}
			break;
		}
		which_graphics_onscreen = "BG";
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