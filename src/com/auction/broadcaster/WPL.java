package com.auction.broadcaster;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.auction.containers.Data;
import com.auction.containers.Scene;
import com.auction.model.Player;
import com.auction.model.PlayerCount;
import com.auction.model.Statistics;
import com.auction.model.Team;
import com.auction.service.AuctionService;
import com.auction.model.Auction;
import com.auction.util.AuctionFunctions;
import com.auction.util.AuctionUtil;

public class WPL extends Scene{

	private String status, side2ValueToProcess = "";
	private String slashOrDash = "-";
	public String session_selected_broadcaster = "WPL";
	public Data data = new Data();
	public String which_graphics_onscreen = "",which_data="",which_team="", rtm_googly_on_screen = "",which_crwaler_onscreen;
	public int current_layer = 2, whichSide = 1, whichSideNotProfile=1, rowHighlight = 1,prevRowHighlight = 1, rtmGooglyWhichSide = 1;
	public int player_id = 0,team_id=0,player_number=0;
	public int zoneSize = 0, current_index = 0;
	public int whichSideCrawler=1;
	public Statistics Statistics ;
	public Player p1,p2;
	List<Player> squad = new ArrayList<Player>();
	List<String> data_str = new ArrayList<String>();
	List<PlayerCount> player_count = new ArrayList<PlayerCount>();

	private String logo_path = "IMAGE*/Default/Essentials/Logos/";
	private String icon_path = "IMAGE*/Default/Essentials/Icons/";
	private String flag_path = "IMAGE*/Default/Essentials/Flags/";
	private String photo_path  = "C:\\Images\\AUCTION\\Photos\\";
	
	public boolean isProfileStatsOnScreen = false;
	
	public WPL() {
		super();
	}

	public WPL(String scene_path, String which_Layer) {
		super(scene_path, which_Layer);
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public Data updateData(Auction auction, Auction session_curr_bid,AuctionService auctionService, PrintWriter print_writer) throws InterruptedException
	{
		if(data.isData_on_screen()) {
			if(data.isPlayer_sold_or_unsold() == false) {
				populatePlayerProfileDOUBLEEFF(true,print_writer, 2,side2ValueToProcess, 
						auction,session_curr_bid, auctionService, session_selected_broadcaster);
			}
			
			if(data.getPreviousBid() < session_curr_bid.getCurrentPlayers().getSoldForPoints() || 
					data.getPreviousBid() > session_curr_bid.getCurrentPlayers().getSoldForPoints()) {
				data.setPreviousBid(session_curr_bid.getCurrentPlayers().getSoldForPoints());
				
				BidChangeOn(print_writer, session_curr_bid, 2);
				print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Change$Bid_Value$Side1 START \0");
				TimeUnit.MILLISECONDS.sleep(600);
				BidChangeOn(print_writer, session_curr_bid, 1);
				print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Change$Bid_Value$Side1 SHOW 0.0 \0");
			}
		}
		return data;
	}
	
	public Object ProcessGraphicOption(String whatToProcess, Auction auction, Auction session_curr_bid, AuctionService auctionService,
			PrintWriter print_writer, List<Scene> scenes, String valueToProcess) throws Exception {
		System.out.println(whatToProcess.toUpperCase());
		switch (whatToProcess.toUpperCase()) {
		
		case "POPULATE-FF-PLAYERPROFILE":  case "POPULATE-FF_IDENT": case "POPULATE-REMAINING_PURSE_ALL":   case "POPULATE-FF-PLAYERPROFILE_DOUBLE":
		
		case "POPULATE-ONLY_SQUAD": case "POPULATE-FF_FIVE_TOP_BUYS_AUCTION":case "POPULATE-PROFILE_STATS_CHANGE":
		case "POPULATE-L3-FLIPPER":case "POPULATE-PROFILE_STATS":case "POPULATE-CURR_BID":case "POPULATE-PLAYERPROFILE_FF":
		case "POPULATE-L3-NAMESUPER":case "POPULATE-RTM_AVAILABLE":case "POPULATE-FF_FIVE_TOP_BUY_TEAM":
		case "POPULATE-FLIPPER_SQUAD": case "POPULATE-LOF_REMAINING_PURSE": case "POPULATE-LOF_REMAINING_SLOT": case "POPULATE-LOF_SQUAD_SIZE":
		
		//crawl
		case "POPULATE-CRAWLE_SQUAD":
		case "POPULATE-CRAWL-PURSE_REMAINING": case "POPULATE-CRAWL-SQUAD_SIZE": case "POPULATE-CRAWL_TOP_SOLD": 
		case "POPULATE-CRAWLER_TEAM_TOP_SOLD": case "POPULATE-L3-CRWLERFREETEXT":		
			switch (session_selected_broadcaster.toUpperCase()) {
			case "WPL":
				switch (whatToProcess.toUpperCase()) {
				
				case "POPULATE-FF-PLAYERPROFILE_DOUBLE":
					System.out.println("Whattoprocess " +  whatToProcess);
					System.out.println("valueto[process " +  valueToProcess);
					
					if(!which_graphics_onscreen.isEmpty()) {
						whichSideNotProfile = 2;
					}else {
						whichSideNotProfile = 1;
					}
					side2ValueToProcess = valueToProcess;
					//data.setPlayer_id(Integer.valueOf(valueToProcess));
					//Statistics = auctionService.getAllStats().stream().filter(st -> st.getPlayer_id() == Integer.valueOf(valueToProcess.split(",")[0])).findAny().orElse(null);
					p1 = auctionService.getAllPlayer().stream().filter(pl -> pl.getPlayerId() == Integer.valueOf(valueToProcess.split(",")[0])).findAny().orElse(null);
					p2 = auctionService.getAllPlayer().stream().filter(pl -> pl.getPlayerId() == Integer.valueOf(valueToProcess.split(",")[1])).findAny().orElse(null);
//					data.setWithPlayerPhoto(valueToProcess.split(",")[1].equalsIgnoreCase("With_Photo") ? 1 : 0);
					//data.setPlayer_id(Integer.valueOf(valueToProcess.split(",")[0]));
					populatePlayerProfileDOUBLEEFF(false,print_writer, whichSideNotProfile,valueToProcess, 
							auction,session_curr_bid, auctionService, session_selected_broadcaster);
					processPreviewFullFrames(print_writer, whatToProcess, whichSideNotProfile);
					
					break;
				
//				case "POPULATE-FF-PLAYERPROFILE":
//					data.setWithPlayerPhoto(valueToProcess.split(",")[1].equalsIgnoreCase("With_Photo") ? 1 : 0);
//					data.setPlayer_id(Integer.valueOf(valueToProcess.split(",")[0]));
//					populatePlayerProfile(false,print_writer,whichSide,Integer.valueOf(valueToProcess.split(",")[0]), 
//							auctionService.getAllStats(),auction, session_curr_bid,auctionService, 
//							session_selected_broadcaster);
//					TimeUnit.MILLISECONDS.sleep(500);
//					processPreview(print_writer, whatToProcess, whichSide);
//					break;
					
				case "POPULATE-FF-PLAYERPROFILE":
					if(!which_graphics_onscreen.isEmpty()) {
						whichSideNotProfile = 2;
					}else {
						whichSideNotProfile = 1;
					}
					side2ValueToProcess = valueToProcess;
					Statistics = auctionService.getAllStats().stream().filter(st -> st.getPlayer_id() == Integer.valueOf(valueToProcess.split(",")[0])).findAny().orElse(null);
//					data.setWithPlayerPhoto(valueToProcess.split(",")[1].equalsIgnoreCase("With_Photo") ? 1 : 0);
					//data.setPlayer_id(Integer.valueOf(valueToProcess.split(",")[0]));
					populatePlayerProfileFF(false,print_writer, whichSideNotProfile, Integer.valueOf(valueToProcess.split(",")[0]), 
							auction, auctionService, session_selected_broadcaster);
					processPreviewFullFrames(print_writer, whatToProcess, whichSideNotProfile);
					break;
				case "POPULATE-PROFILE_STATS_CHANGE":
					if(!which_graphics_onscreen.isEmpty()) {
						whichSideNotProfile = 2;
					}else {
						whichSideNotProfile = 1;
					}
					side2ValueToProcess = valueToProcess;
					populateProfileChange(print_writer,whichSideNotProfile);
					processPreviewFullFrames(print_writer, whatToProcess, whichSideNotProfile);
					break;
				case "POPULATE-ONLY_SQUAD":
					if(!which_graphics_onscreen.isEmpty()) {
						whichSideNotProfile = 2;
					}else {
						whichSideNotProfile = 1;
					}
					side2ValueToProcess = valueToProcess;
					populateSquad(print_writer, Integer.valueOf(valueToProcess.split(",")[0]), whichSideNotProfile, auction, auctionService, 
							session_selected_broadcaster);
					processPreviewFullFrames(print_writer, whatToProcess, whichSideNotProfile);
					break;
				case "POPULATE-FF_IDENT":
					if(!which_graphics_onscreen.isEmpty()) {
						whichSideNotProfile = 2;
					}else {
						whichSideNotProfile = 1;
					}
					populateIdent(print_writer,whichSideNotProfile,session_selected_broadcaster);
					processPreviewFullFrames(print_writer, whatToProcess, whichSideNotProfile);
					break;
					
				case "POPULATE-REMAINING_PURSE_ALL":
					if(!which_graphics_onscreen.isEmpty()) {
						whichSideNotProfile = 2;
					}else {
						whichSideNotProfile = 1;
					}
					side2ValueToProcess = valueToProcess;
					populateFFRTMAndPurseRemaining(print_writer, whichSideNotProfile, auction,auctionService,session_selected_broadcaster);
					processPreviewFullFrames(print_writer, whatToProcess, whichSideNotProfile);
					break;
				case "POPULATE-FF_RTM_AND_PURSE_REMAINING":
					if(!which_graphics_onscreen.isEmpty()) {
						whichSideNotProfile = 2;
					}else {
						whichSideNotProfile = 1;
					}
					side2ValueToProcess = valueToProcess;
					populateFFRTMAndPurseRemaining(print_writer, whichSideNotProfile, auction,auctionService,session_selected_broadcaster);
					processPreviewFullFrames(print_writer, whatToProcess, whichSideNotProfile);
					break;
				}
			}
		case "ANIMATE-IN-REMAINING_PURSE_ALL": case "ANIMATE-IN-FF_IDENT": case "ANIMATE-IN-PLAYERPROFILE":  case "ANIMATE-IN-PLAYERDOUBLEMAINPROFILE":
			
		case "ANIMATE-OUT-PROFILE": case "ANIMATE-OUT-RTM_GOOGLY": case "ANIMATE-OUT-RTM_AVAILABLE":
		case "ANIMATE-OUT": case "CLEAR-ALL":  case "ANIMATE-IN-SQUAD": 
		case "ANIMATE-IN-NAMESUPER": case "ANIMATE-IN-CURR_BID": case "ANIMATE-OUT-CRAWLER":
		case "ANIMATE-IN-RTM_AVAILABLE": case "ANIMATE-IN-PROFILE_STATS": case "ANIMATE-OUT-PLAYER_STAT":
		case "ANIMATE-IN-PLAYERPROFILE_FF": case "ANIMATE-IN-FLIPPER": case "ANIMATE-IN-TEAM_CURR_BID": 
		case "ANIMATE-IN-PROFILE_STATS_CHANGE":case "ANIMATE-IN-ZONE-PLAYER_STATS":
		case "ANIMATE-IN-FF_RTM_AND_PURSE_REMAINING": case "ANIMATE-IN-FF_TOP_BUYS_AUCTION": case "ANIMATE-IN-FF_TOP_BUY_TEAM": 
		case "ANIMATE-IN-FF_FIVE_TOP_BUYS_AUCTION": case "ANIMATE-IN-FF_FIVE_TOP_BUY_TEAM": case "ANIMATE-IN-FLIPPER_SQUAD":

		case "ANIMATE-IN-LOF_REMAINING_PURSE": case "ANIMATE-IN-LOF_TOP_SOLD": case "ANIMATE-IN-LOF_TEAM_TOP_SOLD":
		case "ANIMATE-IN-SQUAD-PLAYER": case "ANIMATE-IN-LOF_REMAINING_SLOT": case "ANIMATE-IN-LOF_SQUAD_SIZE": 
		case "ANIMATE-IN-LOF_RTM_REMAINING": case "ANIMATE-IN-LOF_SQUAD_SIZE_CATEGORY_WISE": case "ANIMATE-IN-LOF_SQUAD": 
		case "ANIMATE-IN-ZONEWISE_PLAYERS_SOLD": 
			
		case "ANIMATE-IN-CRAWL_REMAINING_PURSE": case "ANIMATE-IN-CRAWL_SQUAD_SIZE": case "ANIMATE-IN-CRAWL_REMAINING_RMT": 
		case "ANIMATE-IN-CRAWLER_TEAM_TOP_SOLD": case "ANIMATE-IN-CRAWL_TOP_SOLD": 
			
		case "ANIMATE-IN-ONLY_SQUAD":
			
			switch (session_selected_broadcaster.toUpperCase()) {
			case "WPL":
				switch (whatToProcess.toUpperCase()) {
				case "ANIMATE-OUT-PLAYER_STAT":
					if(isProfileStatsOnScreen) {
						print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*anim_InfoBar$In_Out$BottomStats CONTINUE\0");
						isProfileStatsOnScreen = false;
						TimeUnit.MILLISECONDS.sleep(2000);
						print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*anim_InfoBar$In_Out$BottomStats SHOW 0.0\0");
					}
					break;
				case "ANIMATE-IN-PROFILE_STATS_CHANGE":
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Change_Stats START\0");
					TimeUnit.MILLISECONDS.sleep(1000);
					populateProfileChange(print_writer,1);
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Change_Stats SHOW 0.0\0");

					break;
				case "ANIMATE-IN-CURR_BID":
					populateCurrentBid(print_writer, 1);
					if(data.getBid_result().equalsIgnoreCase("GAVEL") || data.getBid_result().equalsIgnoreCase("BID")) {
						if(!data.isBid_Start_or_not()) {
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*anim_Fullframe$Bid_Start_End$Side1 START \0");
						}
					}
					data.setBid_Start_or_not(true);
					data.setBid_result("BID");
					
//					TimeUnit.MILLISECONDS.sleep(2500);
//					populateCurrentBid(print_writer, 1);
//					TimeUnit.MILLISECONDS.sleep(500);
//					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Change$Bid_Value SHOW 0.0 \0");
					break;
				
//				case "ANIMATE-IN-PLAYERPROFILE": 
//					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Logo$In_Out CONTINUE \0");
//					print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*anim_InfoBar$In_Out$CenterData START \0");
//					
//					if(data.isPlayer_sold_or_unsold() == false) {
//						data.setBid_result("GAVEL");
//					}else {
//						print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Shift_PositionX START \0");
//						
//						if(data.getBid_result().equalsIgnoreCase(AuctionUtil.SOLD) || data.getBid_result().equalsIgnoreCase(AuctionUtil.RTM)) {
//							print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*anim_InfoBar$In_Out$Sold START \0");
//						}else if(data.getBid_result().equalsIgnoreCase(AuctionUtil.UNSOLD)) {
//							print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*anim_InfoBar$In_Out$Unsold START \0");
//						}
//					}
//					data.setData_on_screen(true);
//					break;
				
				//FF
				case "ANIMATE-IN-PLAYERPROFILE_FF":
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Logo$In_Out CONTINUE \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*anim_Profile START\0");
					which_graphics_onscreen = "PLAYERPROFILE_FF";
					break;
				case "ANIMATE-IN-FF_RTM_AND_PURSE_REMAINING": case "ANIMATE-IN-FF_TOP_BUYS_AUCTION":
				case "ANIMATE-IN-FF_TOP_BUY_TEAM": case "ANIMATE-IN-SQUAD":case "ANIMATE-IN-ZONE-PLAYER_STATS":
				case "ANIMATE-IN-FF_FIVE_TOP_BUYS_AUCTION": case "ANIMATE-IN-FF_FIVE_TOP_BUY_TEAM": 
					
				case "ANIMATE-IN-FF_IDENT": case "ANIMATE-IN-REMAINING_PURSE_ALL":	case "ANIMATE-IN-PLAYERPROFILE":
				case "ANIMATE-IN-ONLY_SQUAD":  case "ANIMATE-IN-PLAYERDOUBLEMAINPROFILE":
					
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Logo$In_Out CONTINUE \0");
					if(which_graphics_onscreen.isEmpty()) {
						
						print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*anim_Fullframes$In_Out$Essentials START\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*anim_Fullframes$In_Out$Header START\0");
						
						switch(whatToProcess.toUpperCase()) {
						case "ANIMATE-IN-FF_IDENT":
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*anim_Fullframe START\0");
							which_graphics_onscreen = "FF_IDENT";
							break;
						case "ANIMATE-IN-ONLY_SQUAD":
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*anim_Fullframe START\0");
							which_graphics_onscreen = "ONLY_SQUAD";
							break;
							
						case "ANIMATE-IN-REMAINING_PURSE_ALL":
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*anim_Fullframe START\0");
							which_graphics_onscreen = "REMAINING_PURSE_ALL";
							break;
						case "ANIMATE-IN-FF_RTM_AND_PURSE_REMAINING":
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*anim_Fullframes$In_Out$Team_Details START\0");
							which_graphics_onscreen = "FF_RTM_AND_PURSE_REMAINING";
							break;
						case "ANIMATE-IN-FF_TOP_BUYS_AUCTION":
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*anim_Fullframes$In_Out$TopBuys START\0");
							which_graphics_onscreen = "FF_TOP_BUYS_AUCTION";
							break;
						case "ANIMATE-IN-FF_FIVE_TOP_BUYS_AUCTION":
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*anim_Fullframes$In_Out$Top5_Buys START\0");
							which_graphics_onscreen = "FF_FIVE_TOP_BUYS_AUCTION";
							break;
						case "ANIMATE-IN-FF_FIVE_TOP_BUY_TEAM":
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*anim_Fullframes$In_Out$Top5_Buys START\0");
							which_graphics_onscreen = "FF_FIVE_TOP_BUY_TEAM";
							break;
						case "ANIMATE-IN-FF_TOP_BUY_TEAM":
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*anim_Fullframes$In_Out$TopBuys START\0");
							which_graphics_onscreen = "FF_TOP_BUY_TEAM";
							break;
						case "ANIMATE-IN-SQUAD":
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*anim_Fullframes$In_Out$Squad START\0");
							which_graphics_onscreen ="SQUAD";
							break;
						case "ANIMATE-IN-ZONE-PLAYER_STATS":
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*anim_Fullframes$In_Out$Squad START\0");
							which_graphics_onscreen = "ZONE-PLAYER_STATS";
							break;
						}
					}else {
						ChangeOn(print_writer, which_graphics_onscreen, whatToProcess);
						TimeUnit.MILLISECONDS.sleep(2000);
						switch (whatToProcess.toUpperCase()) {
						case "ANIMATE-IN-PLAYERDOUBLEMAINPROFILE":
							populatePlayerProfileDOUBLEEFF(false,print_writer, 1,side2ValueToProcess, auction,session_curr_bid, auctionService, session_selected_broadcaster);
							if(data.isPlayer_sold_or_unsold() == false) {
								data.setBid_result("GAVEL");
							}else {
//								if(data.getBid_result().equalsIgnoreCase(AuctionUtil.SOLD) || data.getBid_result().equalsIgnoreCase(AuctionUtil.RTM)) {
//									print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*anim_InfoBar$In_Out$Sold START \0");
//								}else if(data.getBid_result().equalsIgnoreCase(AuctionUtil.UNSOLD)) {
//									print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*anim_InfoBar$In_Out$Unsold START \0");
//								}
							}
							data.setData_on_screen(true);
							break;
						case "ANIMATE-IN-FF_IDENT":
							populateIdent(print_writer,1,session_selected_broadcaster);
							break;
						case "ANIMATE-IN-ONLY_SQUAD":
							populateSquad(print_writer, Integer.valueOf(side2ValueToProcess.split(",")[0]), 1, auction, auctionService, 
									session_selected_broadcaster);
							break;
						case "ANIMATE-IN-REMAINING_PURSE_ALL":
							populateFFRTMAndPurseRemaining(print_writer, 1, auction,auctionService,session_selected_broadcaster);
							break;	
						case "ANIMATE-IN-PLAYERPROFILE": 
							populatePlayerProfileFF(false,print_writer, 1, Integer.valueOf(side2ValueToProcess.split(",")[0]), 
									auction, auctionService, session_selected_broadcaster);
							break;
						case "ANIMATE-IN-PLAYERPROFILE_FF":
							Statistics = auctionService.getAllStats().stream().filter(st -> st.getPlayer_id() == Integer.valueOf(side2ValueToProcess.split(",")[0])).findAny().orElse(null);
							populatePlayerProfileFF(false,print_writer, 1, Integer.valueOf(side2ValueToProcess.split(",")[0]), 
									auction, auctionService, session_selected_broadcaster);
							break;
						case "ANIMATE-IN-SQUAD":
							populateSquad(print_writer, Integer.valueOf(side2ValueToProcess.split(",")[0]), 
									1, auction, auctionService, session_selected_broadcaster);
							break;
						case "ANIMATE-IN-FF_RTM_AND_PURSE_REMAINING":
							populateFFRTMAndPurseRemaining(print_writer, 1, auction, auctionService, session_selected_broadcaster);
							break;
						}
						TimeUnit.MILLISECONDS.sleep(2000);
						cutBack(print_writer, which_graphics_onscreen, whatToProcess);
						which_graphics_onscreen = whatToProcess.replace("ANIMATE-IN-", "");
					}
					break;
					
				case "ANIMATE-IN-RTM_AVAILABLE":
					print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*anim_InfoBar$In_Out$RTM START\0");
					rtm_googly_on_screen = "RTM";
					break;
				case "CLEAR-ALL":
					resetData(print_writer);
					
					TimeUnit.MILLISECONDS.sleep(400);
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Header$Side1$Select_HeaderType*FUNCTION*Omo*vis_con SET 0\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side1$Select_GraphicsType*FUNCTION*Omo*vis_con SET 4\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*anim_Fullframe$In_Out$Essentials START \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*anim_Fullframe$In_Out$Main$BS_Logo START \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Loop START \0");
					which_graphics_onscreen = "BG";
					
		            side2ValueToProcess ="";
		            rtmGooglyWhichSide = 1;
		            whichSideNotProfile = 1;
		            data.setBid_Start_or_not(false);
		            data.setPlayer_sold_or_unsold(false);
		            data.setData_on_screen(false);
		            isProfileStatsOnScreen = false;
					data.setBid_result("");
					break;
				case "ANIMATE-OUT-PROFILE":					
					print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*anim_InfoBar$In_Out$CenterData CONTINUE\0");
					if(isProfileStatsOnScreen) {
						print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*anim_InfoBar$In_Out$BottomStats CONTINUE\0");
						isProfileStatsOnScreen = false;
					}
				
					print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Shift_PositionX CONTINUE \0");
					
					if(data.isPlayer_sold_or_unsold() == false) {
						if(data.isBid_Start_or_not()) {
							print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*anim_InfoBar$In_Out$CurrentBid CONTINUE \0");
							data.setBid_Start_or_not(false);
						}
					}else {
						if(data.getBid_result().equalsIgnoreCase(AuctionUtil.SOLD) || data.getBid_result().equalsIgnoreCase(AuctionUtil.RTM)) {
							print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*anim_InfoBar$In_Out$Sold CONTINUE \0");
						}else if(data.getBid_result().equalsIgnoreCase(AuctionUtil.UNSOLD)) {
							print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*anim_InfoBar$In_Out$Unsold CONTINUE \0");
						}
					}
					if(rtm_googly_on_screen.equalsIgnoreCase("RTM")) {
						print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*anim_InfoBar$In_Out$RTM CONTINUE \0");
					}
					
					TimeUnit.MILLISECONDS.sleep(1200);
					print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*anim_InfoBar SHOW 0\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Shift_PositionX SHOW 0.0 \0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Change_InfoBar SHOW 0.0 \0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Change_Current_Bid SHOW 0.0 \0");
					data.setBid_Start_or_not(false);
					data.setPlayer_sold_or_unsold(false);
					data.setData_on_screen(false);
					data.setBid_result("");
					rtm_googly_on_screen = "";
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Logo$In_Out START \0");
					break;
				case "ANIMATE-OUT-RTM_AVAILABLE":
					switch (rtm_googly_on_screen) {
					case "RTM":
						print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*anim_InfoBar$In_Out$RTM CONTINUE \0");
						TimeUnit.MILLISECONDS.sleep(500);
						print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*anim_InfoBar$In_Out$RTM SHOW 0\0");
						break;
					}
					rtm_googly_on_screen = "";
					break;
				case "ANIMATE-OUT-CRAWLER":
					switch(which_crwaler_onscreen) {
					case "CRAWL_REMAINING_PURSE": case "CRAWL_SQUAD_SIZE": case "CRAWL_TOP_SOLD": case "CRAWLER_TEAM_TOP_SOLD": 
					case "CRAWL_SQUAD": case "FREEETEXTCRAWLER":
						print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*anim_Crawl$In_Out CONTINUE \0");
						which_crwaler_onscreen = "";
						TimeUnit.MILLISECONDS.sleep(1200);
						print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*anim_Crawl SHOW 0.0\0");
						break;
					}
					break;	
				
				case "ANIMATE-OUT":
					switch(which_graphics_onscreen) {
					case "ONLY_SQUAD": case "FF_IDENT": case "PLAYERPROFILE": case "REMAINING_PURSE_ALL": case "PLAYERDOUBLEMAINPROFILE":
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Header$Side2$Select_HeaderType*FUNCTION*Omo*vis_con SET 0\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side2$Select_GraphicsType*FUNCTION*Omo*vis_con SET 4\0");
						
						print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Change$Header START\0");
						switch(which_graphics_onscreen) {
						case "ONLY_SQUAD":
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Change$Squad START\0");
							break;
						case "FF_IDENT": 
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Change$MatchId START\0");
							break;
						case "PLAYERPROFILE": 
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Change$Profile START\0");
							break;
						case "REMAINING_PURSE_ALL":
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Change$PurseRemaining START\0");
							break;
						case "PLAYERDOUBLEMAINPROFILE":
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Change$ProfileDouble START\0");
							break;
						}
						
						print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Change$BS_Logo START\0");
						
						TimeUnit.MILLISECONDS.sleep(2000);
						
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Header$Side1$Select_HeaderType*FUNCTION*Omo*vis_con SET 0\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side1$Select_GraphicsType*FUNCTION*Omo*vis_con SET 4\0");
						
						print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*anim_Fullframe$In_Out$Main$BS_Logo SHOW 2.480\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Change$BS_Logo SHOW 0.0\0");
						
						print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*anim_Fullframe$In_Out$Header SHOW 2.480\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Change$Header SHOW 0.0\0");
						
						switch(which_graphics_onscreen) {
						case "ONLY_SQUAD":
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*anim_Fullframe$In_Out$Main$Squad SHOW 0.0\0");
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Change$Squad SHOW 0.0\0");
							break;
						case "FF_IDENT": 
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*anim_Fullframe$In_Out$Main$MatchId SHOW 0.0\0");
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Change$MatchId SHOW 0.0\0");
							break;
						case "PLAYERPROFILE": 
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*anim_Fullframe$In_Out$Main$Profile SHOW 0.0\0");
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Change$Profile SHOW 0.0\0");
							break;
						case "REMAINING_PURSE_ALL":
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*anim_Fullframe$In_Out$Main$PurseRemaining SHOW 0.0\0");
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Change$PurseRemaining SHOW 0.0\0");
							break;
						case "PLAYERDOUBLEMAINPROFILE":
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*anim_Fullframe$In_Out$Main$ProfileDouble SHOW 0.0 \0");
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Change$ProfileDouble SHOW 0.0\0");
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Change SHOW 0.0\0");
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*anim_Fullframe$Bid_Start_End$Side1  SHOW 0.0 \0");
							break;
						}
						which_graphics_onscreen = "BG";
						break;
					
					
					
						
					case "FF_RTM_AND_PURSE_REMAINING": case "FF_TOP_BUYS_AUCTION": case "FF_TOP_BUY_TEAM":
					case "SQUAD":case "ZONE-PLAYER_STATS": case "FF_FIVE_TOP_BUYS_AUCTION":
					case "FF_FIVE_TOP_BUY_TEAM":
						switch (which_graphics_onscreen) {
						case "FF_RTM_AND_PURSE_REMAINING":
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*anim_Fullframes$In_Out$Header CONTINUE\0");
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*anim_Fullframes$In_Out$Team_Details CONTINUE\0");
							break;
						case "FF_TOP_BUYS_AUCTION": case "FF_TOP_BUY_TEAM":
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*anim_Fullframes$In_Out$Header CONTINUE\0");
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*anim_Fullframes$In_Out$TopBuys CONTINUE\0");
							break;
						case "SQUAD": case "ZONE-PLAYER_STATS":
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*anim_Fullframes$In_Out$Header CONTINUE\0");
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*anim_Fullframes$In_Out$Squad CONTINUE\0");
							break;
						case "FF_FIVE_TOP_BUYS_AUCTION": case "FF_FIVE_TOP_BUY_TEAM":
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*anim_Fullframes$In_Out$Header CONTINUE\0");
							print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*anim_Fullframes$In_Out$Top5_Buys CONTINUE\0");
							break;
						}
						print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*anim_Fullframes$In_Out$Essentials CONTINUE\0");
						which_graphics_onscreen = "";
						TimeUnit.MILLISECONDS.sleep(2000);
						print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*anim_Fullframes SHOW 0\0");
						print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Logo$In_Out START \0");
						break;
					case "NAMESUPER":
						print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*anim_LowerThird$In_Out$CenterData$Essentials CONTINUE\0");
						print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*anim_LowerThird$In_Out$CenterData$Image CONTINUE\0");
						print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*anim_LowerThird$In_Out$CenterData$Data CONTINUE\0");
						which_graphics_onscreen = "";
						TimeUnit.MILLISECONDS.sleep(2000);
						print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*anim_LowerThird SHOW 0\0");
						break;
					case "FLIPPER": case "FLIPPER_SQUAD":
						print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Flipper CONTINUE \0");
						which_graphics_onscreen = "";
						TimeUnit.MILLISECONDS.sleep(2000);
						print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Flipper SHOW 0\0");
						break;
					}
					break;
				}
				
			}
		}
		return null;
	}
	private void populateProfileChange(PrintWriter print_writer, int whichSideNotProfile2) {
		
		if(side2ValueToProcess.equalsIgnoreCase("with_data")) {			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_Profile$Profile$Side1$Stats$Side" + whichSideNotProfile2 + "$SelectStyle*FUNCTION*Omo*vis_con SET 0\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_Profile$Profile$Side1$Stats$Side" + whichSideNotProfile2 + "$Style1$1$txt_StatHead"
					+ "*GEOM*TEXT SET WR - WEEK 15\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_Profile$Profile$Side1$Stats$Side" + whichSideNotProfile2 + "$Style1$1$txt_StatValue"
					+ "*GEOM*TEXT SET " + (Statistics.getRank().equalsIgnoreCase("NA") ? "-" : Statistics.getRank()) + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_Profile$Profile$Side1$Stats$Side" + whichSideNotProfile2 + "$Style1$2$txt_StatHead"
					+ "*GEOM*TEXT SET STYLE\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_Profile$Profile$Side1$Stats$Side" + whichSideNotProfile2 + "$Style1$2$txt_StatValue"
					+ "*GEOM*TEXT SET " + Statistics.getStyle() + " " + Statistics.getGrip() + "\0");
			
		}else if(side2ValueToProcess.equalsIgnoreCase("with_info")) {
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_Profile$Profile$Side1$Stats$Side" + whichSideNotProfile2 + "$SelectStyle*FUNCTION*Omo*vis_con SET 1\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_Profile$Profile$Side1$Stats$Side" + whichSideNotProfile2 + "$Style2$txt_StatValue"
					+ "*GEOM*TEXT SET " + (Statistics.getInfo1() != null ? Statistics.getInfo1() : "") + "\0");
			
		}
	}

	public void resetData(PrintWriter print_writer) {
		print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Loop SHOW 0.0 \0");
		print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*StartFlare SHOW 0.0 \0");
		print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*anim_Fullframe SHOW 0.0 \0");
		print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Change SHOW 0.0 \0");
		print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*EndFlare SHOW 0.0 \0");
		print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Audio SHOW 0.0 \0");
		
	}
	
	public void ChangeOn(PrintWriter print_writer, String whichGraphicOnScreen, String whatToProcess) throws InterruptedException {
		switch (which_graphics_onscreen.toUpperCase()) {
		//FF	
		case "BG":
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Change$BS_Logo$Change_Out START\0");
			break;
		case "PLAYERPROFILE":
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Change$Header$Change_Out START\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Change$Profile$Change_Out START\0");
			break;
		case "PLAYERDOUBLEMAINPROFILE":
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Change$Header$Change_Out START\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Change$ProfileDouble$Change_Out START\0");
			break;
		case "IDENT":
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Change$Header$Change_Out START\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Change$MatchId$Change_Out START\0");
			break;	
		case "ONLY_SQUAD": case "REMAINING_PURSE_ALL":
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Change$Header$Change_Out START\0");
			switch (which_graphics_onscreen.toUpperCase()) {
			case "ONLY_SQUAD":
				print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Change$Squad$Change_Out START\0");
				break;
			case "REMAINING_PURSE_ALL":
				print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Change$PurseRemaining$Change_Out START\0");
				break;
			}
			break;
		}
		TimeUnit.MILLISECONDS.sleep(1000);

		switch (whatToProcess.toUpperCase()) {	
		case "ANIMATE-IN-ONLY_SQUAD":
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Change$Header$Change_In START\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Change$Squad$Change_In START\0");
			break;	
		case "ANIMATE-IN-PLAYERDOUBLEMAINPROFILE":
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Change$Header$Change_In START\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Change$ProfileDouble$Change_In START\0");
			break;
		case "ANIMATE-IN-FF_IDENT":
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Change$Header$Change_In START\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Change$MatchId$Change_In START\0");
			break;
		case "ANIMATE-IN-PLAYERPROFILE":
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Change$Header$Change_In START\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Change$Profile$Change_In START\0");
			break;
		case "ANIMATE-IN-PLAYERPROFILE_FF":
			if(!which_graphics_onscreen.equalsIgnoreCase("PLAYERPROFILE_FF")) {
				print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Change$Profile START\0");
			}
			break;
		case "ANIMATE-IN-REMAINING_PURSE_ALL":
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Change$Header$Change_In START\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Change$PurseRemaining$Change_In START\0");
			break;
		}
	}
	public void cutBack(PrintWriter print_writer, String whichGraphicOnScreen, String whatToProcess) throws InterruptedException { 
		
		switch (whatToProcess.toUpperCase()) {
		case "ANIMATE-IN-REMAINING_PURSE_ALL":  case "ANIMATE-IN-FF_IDENT": case "ANIMATE-IN-ONLY_SQUAD": case "ANIMATE-IN-PLAYERPROFILE": 
		case "ANIMATE-IN-PLAYERDOUBLEMAINPROFILE":
		//FF
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*anim_Fullframe$In_Out$Header SHOW 2.480\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Change$Header SHOW 0.0\0");
			switch (whatToProcess.toUpperCase()) {
				case "ANIMATE-IN-PLAYERDOUBLEMAINPROFILE":
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*anim_Fullframe$In_Out$Main$ProfileDouble SHOW 2.480\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Change$ProfileDouble SHOW 0.0\0");
					break;
				case "ANIMATE-IN-FF_IDENT":
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*anim_Fullframe$In_Out$Main$MatchId SHOW 2.480\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Change$MatchId SHOW 0.0\0");
					break;
				case "ANIMATE-IN-ONLY_SQUAD":
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*anim_Fullframe$In_Out$Main$Squad SHOW 2.480\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Change$Squad SHOW 0.0\0");
					break;
				case "ANIMATE-IN-PLAYERPROFILE":
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*anim_Fullframe$In_Out$Main$Profile SHOW 2.480\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Change$Profile SHOW 0.0\0");
					break;
				case "ANIMATE-IN-REMAINING_PURSE_ALL": 	
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*anim_Fullframe$In_Out$Main$PurseRemaining SHOW 2.480\0");
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Change$PurseRemaining SHOW 0.0\0");
					break;
			}
			break;
		}
		switch (which_graphics_onscreen.toUpperCase()) {
		case "BG":
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*anim_Fullframe$In_Out$Main$BS_Logo SHOW 0.0\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Change$BS_Logo SHOW 0.0\0");
			break;
		case "FF_IDENT":
			if(!whatToProcess.equalsIgnoreCase("ANIMATE-IN-FF_IDENT")) {
				print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*anim_Fullframe$In_Out$Main$MatchId SHOW 0.0\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Change$MatchId SHOW 0.0\0");
			}
			break;	
		case "REMAINING_PURSE_ALL":
			if(!whatToProcess.equalsIgnoreCase("ANIMATE-IN-REMAINING_PURSE_ALL")) {
				print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*anim_Fullframe$In_Out$Main$PurseRemaining SHOW 0.0\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Change$PurseRemaining SHOW 0.0\0");
			}
			break;
		case "ONLY_SQUAD":
			if(!whatToProcess.equalsIgnoreCase("ANIMATE-IN-ONLY_SQUAD")) {
				print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*anim_Fullframe$In_Out$Main$Squad SHOW 0.0\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Change$Squad SHOW 0.0\0");
			}
			break;
		case "PLAYERDOUBLEMAINPROFILE":
			if(!whatToProcess.equalsIgnoreCase("ANIMATE-IN-PLAYERDOUBLEMAINPROFILE")) {
				print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*anim_Fullframe$In_Out$Main$ProflieDouble SHOW 0.0\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Change$ProflieDouble SHOW 0.0\0");
			}
			break;
		case "PLAYERPROFILE":
			if(!whatToProcess.equalsIgnoreCase("ANIMATE-IN-PLAYERPROFILE")) {
				print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*anim_Fullframe$In_Out$Main$Profile SHOW 0.0\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Change$Profile SHOW 0.0\0");
			}
			break;	
		}
		prevRowHighlight = rowHighlight;
	}
	
	public void populateIdent(PrintWriter print_writer,int which_side,String session_selected_broadcaster) throws IOException {
		
		 String filePath = "C:\\Sports\\Auction\\VENUE.txt";
		
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Header$Side" + whichSide + "$Select_HeaderType*FUNCTION*Omo*vis_con SET 0\0");
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + which_side + "$Select_GraphicsType*FUNCTION*Omo*vis_con SET 0\0");
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + which_side + "$Ident$HeaderAll$Header$Header1$txt_Header1*GEOM*TEXT SET PLAYER\0");
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + which_side + "$Ident$HeaderAll$Header$Header2$txt_Header2*GEOM*TEXT SET AUCTION 2026\0");
		
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + which_side + "$Ident$InfoGrp$Info1$txt_Info*GEOM*TEXT SET WORLD PADEL LEAGUE \r\n"
				+ "SEASON 4\0");
		
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            String line;

            while ((line = br.readLine()) != null) {
            	print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + which_side + "$Ident$InfoGrp$Info2$txt_Info*GEOM*TEXT SET "+ line +"\0");
        		 }

        }
	}
	public void populateFFRTMAndPurseRemaining(PrintWriter print_writer, int whichSide , Auction auction,AuctionService auctionService, String session_selected_broadcaster) {
		int squadSize=0,totalAmountSpent=0,row=0;
		
		//header
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Header$Side" + whichSide + "$Select_HeaderType*FUNCTION*Omo*vis_con SET 1\0");
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Header$Side" + whichSide + "$Select_HeaderType$HeaderType3$Header$txt_Header1"
				+ "*GEOM*TEXT SET " + "WORLD PADEL LEAGUE SEASON 4" + " \0");
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Header$Side" + whichSide + "$Select_HeaderType$HeaderType3$SubHeader$txt_SubHeader"
				+ "*GEOM*TEXT SET " + "PLAYER AUCTION" + " \0");
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Header$Side" + whichSide + "$Select_HeaderType$HeaderType3$LogoGrp$img_TeamLogo"
				+ "*TEXTURE*IMAGE SET " + logo_path + "TLogo" + "\0");
		
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + whichSide + "$Select_GraphicsType*FUNCTION*Omo*vis_con SET 3\0");
		
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + whichSide + "$PurseRemaining$DataAll$Title$PurseRemTitle$DataAll$txt_Title1"
				+ "*GEOM*TEXT SET TEAM\0");
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + whichSide + "$PurseRemaining$DataAll$Title$PurseRemTitle$DataAll$txt_Title2"
				+ "*GEOM*TEXT SET SQUAD SIZE\0");
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + whichSide + "$PurseRemaining$DataAll$Title$PurseRemTitle$DataAll$txt_Title3"
				+ "*GEOM*TEXT SET PURSE REMAINING\0");
		
		for(Team tm : auction.getTeam()) {
			row++;
			
			if(tm.getTeamName2() != null && tm.getTeamName3() != null) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + whichSide + "$PurseRemaining$DataAll$Team" + row + "$PurseRemData$Data$TeamGrp$txt_TeamFirstName"
						+ "*ACTIVE SET 1\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + whichSide + "$PurseRemaining$DataAll$Team" + row + "$PurseRemData$Data$TeamGrp$txt_TeamFirstName"
						+ "*GEOM*TEXT SET " + tm.getTeamName2() + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + whichSide + "$PurseRemaining$DataAll$Team" + row + "$PurseRemData$Data$TeamGrp$txt_TeamLastName"
						+ "*GEOM*TEXT SET " + tm.getTeamName3() + "\0");
			}else {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + whichSide + "$PurseRemaining$DataAll$Team" + row + "$PurseRemData$Data$TeamGrp$txt_TeamFirstName"
						+ "*ACTIVE SET 0\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + whichSide + "$PurseRemaining$DataAll$Team" + row + "$PurseRemData$Data$TeamGrp$txt_TeamLastName"
						+ "*GEOM*TEXT SET " + tm.getTeamName1() + "\0");
			}
			
			for(Player auc : auction.getPlayers()) {
				if(tm.getTeamId() == auc.getTeamId()) {
					squadSize++;
					totalAmountSpent += auc.getSoldForPoints();
				}
			}
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + whichSide + "$PurseRemaining$DataAll$Team" + row + "$PurseRemData$Data$txt_SquadSize"
					+ "*GEOM*TEXT SET " + squadSize + "\0");

			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + whichSide + "$PurseRemaining$DataAll$Team" + row + "$PurseRemData$Data$Logo$img_TeamLogo"
					+ "*TEXTURE*IMAGE SET " + logo_path + tm.getTeamName4() + "\0");
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + whichSide + "$PurseRemaining$DataAll$Team" + row + "$PurseRemData$Data$Value$txt_Value"
					+ "*GEOM*TEXT SET " + AuctionFunctions.ConvertToLakh((Integer.valueOf(tm.getTeamTotalPurse()) - totalAmountSpent)) + " K" + "\0");
			
			totalAmountSpent = 0;
			squadSize = 0;
		}
	}
	
	public void populatePlayerProfileDOUBLEEFF(boolean is_this_updating,PrintWriter print_writer,int which_side, String playerId, Auction auction,Auction session_curr_bid, 
			AuctionService auctionService, String session_selected_broadcaster) throws InterruptedException 
	{
		
		if(session_curr_bid.getCurrentPlayers() != null) {
			if(data.isData_on_screen() == true) {
				if(data.isPlayer_sold_or_unsold() == false) {
					PlayerSoldOrUnsold(print_writer, auction, playerId, which_side);
					
					if(data.isPlayer_sold_or_unsold() == true) {
						if(data.getBid_result() != null && !data.getBid_result().isEmpty()) {
							if(data.getBid_result().equalsIgnoreCase(AuctionUtil.SOLD) || data.getBid_result().equalsIgnoreCase(AuctionUtil.RTM)) {
								print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Change$AuctionStaus$Side1 START \0");
							}else if(data.getBid_result().equalsIgnoreCase(AuctionUtil.UNSOLD)) {
								print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Change$AuctionStaus$Side1 START \0");
							}
						}
						TimeUnit.MILLISECONDS.sleep(2000);
						PlayerSoldOrUnsold(print_writer, auction, playerId, 1);
					
						if(data.getBid_result() != null && !data.getBid_result().isEmpty()) {
							if(data.getBid_result().equalsIgnoreCase(AuctionUtil.SOLD) || data.getBid_result().equalsIgnoreCase(AuctionUtil.RTM)) {
								print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Change$AuctionStaus$Side1 SHOW 0.0 \0");
							}else if(data.getBid_result().equalsIgnoreCase(AuctionUtil.UNSOLD)) {
								print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Change$AuctionStaus$Side1 SHOW 0.0 \0");
							}
						}
					}
				}
			}else {
				if(data.isPlayer_sold_or_unsold() == false) {
					PlayerSoldOrUnsold(print_writer, auction, playerId, 1);
				}
			}
		}
		
		if(is_this_updating == false) {
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Header$Side" + whichSide + "$Select_HeaderType*FUNCTION*Omo*vis_con SET 0\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + which_side + "$Select_GraphicsType*FUNCTION*Omo*vis_con SET 2\0");
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side1$ProfileDouble$BasePrice$Price_Data$Side" + which_side + "$Select_Value"
					+ "*FUNCTION*Omo*vis_con SET 0 \0");
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + which_side + "$ProfileDouble$ImageGrp$1$img_Player*TEXTURE*IMAGE SET " 
					+ photo_path + p1.getPhotoName() + AuctionUtil.PNG_EXTENSION + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + which_side + "$ProfileDouble$ImageGrp$1$txt_FirstName*GEOM*TEXT SET " 
					+  p1.getFirstname() + "\0");
			
			if(p1.getSurname() != null) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + which_side + "$ProfileDouble$ImageGrp$1$txt_LastName*GEOM*TEXT SET " 
					    + p1.getSurname() + "\0");
			}else {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + which_side + "$ProfileDouble$ImageGrp$1$txt_LastName*GEOM*TEXT SET \0");
			}
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + which_side + "$ProfileDouble$ImageGrp$2$img_Player*TEXTURE*IMAGE SET " 
					+ photo_path + p2.getPhotoName() + AuctionUtil.PNG_EXTENSION + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + which_side + "$ProfileDouble$ImageGrp$2$txt_FirstName*GEOM*TEXT SET " 
					+  p2.getFirstname() + "\0");
			
			if(p2.getSurname() != null) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + which_side + "$ProfileDouble$ImageGrp$2$txt_LastName*GEOM*TEXT SET " 
					    + p2.getSurname() + "\0");
			}else {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + which_side + "$ProfileDouble$ImageGrp$2$txt_LastName*GEOM*TEXT SET \0");
			}
			
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + which_side + "$ProfileDouble$Stats$Top$1$txt_StatHead*GEOM*TEXT SET AGE\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + which_side + "$ProfileDouble$Stats$Top$1$txt_StatValue*GEOM*TEXT SET " + 
					p1.getAge() + "\0");
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + which_side + "$ProfileDouble$Stats$Top$2$txt_StatHead*GEOM*TEXT SET PLAYER POS.\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + which_side + "$ProfileDouble$Stats$Top$2$txt_StatValue*GEOM*TEXT SET " + 
					p1.getPlayer_position() + "\0");
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + which_side + "$ProfileDouble$Stats$Top$3$txt_StatHead*GEOM*TEXT SET HEIGHT\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + which_side + "$ProfileDouble$Stats$Top$3$txt_StatValue*GEOM*TEXT SET " + 
					p1.getHeight() + "\0");
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + which_side + "$ProfileDouble$Stats$Top$4$txt_StatHead*GEOM*TEXT SET RANK\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + which_side + "$ProfileDouble$Stats$Top$4$txt_StatValue*GEOM*TEXT SET " + 
					(Integer.valueOf(p1.getRank() ) > 0 ? p1.getRank() : "-") + "\0");
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + which_side + "$ProfileDouble$Stats$Bottom$1$txt_StatHead*GEOM*TEXT SET AGE\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + which_side + "$ProfileDouble$Stats$Bottom$1$txt_StatValue*GEOM*TEXT SET " + 
					p2.getAge() + "\0");
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + which_side + "$ProfileDouble$Stats$Bottom$2$txt_StatHead*GEOM*TEXT SET PLAYER POS.\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + which_side + "$ProfileDouble$Stats$Bottom$2$txt_StatValue*GEOM*TEXT SET " + 
					p2.getPlayer_position() + "\0");
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + which_side + "$ProfileDouble$Stats$Bottom$3$txt_StatHead*GEOM*TEXT SET HEIGHT\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + which_side + "$ProfileDouble$Stats$Bottom$3$txt_StatValue*GEOM*TEXT SET " + 
					p2.getHeight() + "\0");
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + which_side + "$ProfileDouble$Stats$Bottom$4$txt_StatHead*GEOM*TEXT SET RANK\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + which_side + "$ProfileDouble$Stats$Bottom$4$txt_StatValue*GEOM*TEXT SET " + 
					(Integer.valueOf(p2.getRank() ) > 0 ? p2.getRank() : "-")+ "\0");
			//baseprice
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + which_side + "$ProfileDouble$BasePrice$Price_Data$txt_Value*GEOM*TEXT SET " + 
					p1.getBasePrice()  + " K"+ "\0");
			
				if(p1.getBasePrice().equalsIgnoreCase("50")) {
					data.setPreviousBid(50000);
				}else if(p1.getBasePrice().equalsIgnoreCase("30")) {
					data.setPreviousBid(30000);
				}
			}
		}

	public void populatePlayerProfileFF(boolean is_this_updating,PrintWriter print_writer,int which_side, int playerId, Auction auction, 
			AuctionService auctionService, String session_selected_broadcaster) throws InterruptedException 
	{
		player_id = playerId; 
		
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Header$Side" + which_side + "$Select_HeaderType*FUNCTION*Omo*vis_con SET 0\0");
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + which_side + "$Select_GraphicsType*FUNCTION*Omo*vis_con SET 1\0");
		
		
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + which_side + "$Profile$ImageGrp$img_Player*TEXTURE*IMAGE SET " 
				+ photo_path + auctionService.getAllPlayer().get(playerId - 1).getPhotoName() + AuctionUtil.PNG_EXTENSION + "\0");
		//name tag
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + which_side + "$Profile$Name$FirstName$txt_FirstName*GEOM*TEXT SET " + auctionService.getAllPlayer().get(playerId - 1).getFirstname()  + "\0");
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + which_side + "$Profile$Name$LastName$txt_LastName*GEOM*TEXT SET " + auctionService.getAllPlayer().get(playerId - 1).getSurname() + "\0");
		
		//match
		
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + which_side + "$Profile$Stats$1$Stats$StatHead_Grp$txt_StatHead*GEOM*TEXT SET MATCHES\0");
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + which_side + "$Profile$Stats$1$Stats$txt_StatValue*GEOM*TEXT SET " + (Integer.valueOf(Statistics.getMatches() ) > 0 ? Statistics.getMatches(): "-") +"\0");
		
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + which_side + "$Profile$Stats$2$Stats$StatHead_Grp$txt_StatHead*GEOM*TEXT SET WINS\0");
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + which_side + "$Profile$Stats$2$Stats$txt_StatValue*GEOM*TEXT SET " + (Integer.valueOf(Statistics.getMatchWon() ) > 0 ? Statistics.getMatchWon(): "-") +"\0");
		
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + which_side + "$Profile$Stats$3$Stats$StatHead_Grp$txt_StatHead*GEOM*TEXT SET LOST\0");
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + which_side + "$Profile$Stats$3$Stats$txt_StatValue*GEOM*TEXT SET " + (Integer.valueOf(Statistics.getMatchLost() ) > 0 ? Statistics.getMatchLost(): "-") +"\0");
		
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + which_side + "$Profile$Stats$4$Stats$StatHead_Grp$txt_StatHead*GEOM*TEXT SET CONSECUTIVE WINS\0");
		//print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + which_side + "$Profile$Stats$4$Stats$txt_StatValue*GEOM*TEXT SET " + (Integer.valueOf(Statistics.getConsecutiveVictories() ) > 0 ? Statistics.getConsecutiveVictories(): "-") +"\0");
		
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + which_side + "$Profile$Stats$5$Stats$StatHead_Grp$txt_StatHead*GEOM*TEXT SET EFFECTIVENESS\0");
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + which_side + "$Profile$Stats$5$Stats$txt_StatValue*GEOM*TEXT SET " + (Statistics.getEffectiveness()  > 0 ? Statistics.getEffectiveness(): "-")  +"\0");
		
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + which_side + "$Profile$Stats$6$Stats$StatHead_Grp$txt_StatHead*GEOM*TEXT SET TITLES\0");
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + which_side + "$Profile$Stats$6$Stats$txt_StatValue*GEOM*TEXT SET " + (Integer.valueOf(Statistics.getTitles() ) > 0 ? Statistics.getTitles(): "-")  +"\0");
		
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + which_side + "$Profile$Stats$7$Stats$StatHead_Grp$txt_StatHead*GEOM*TEXT SET BEST RANK\0");
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + which_side + "$Profile$Stats$7$Stats$txt_StatValue*GEOM*TEXT SET " +  (Integer.valueOf(Statistics.getRank() ) > 0 ? Statistics.getRank(): "-") +"\0");
		
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + which_side + "$Profile$Stats$8$Stats$StatHead_Grp$txt_StatHead*GEOM*TEXT SET RACE\0");
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + which_side + "$Profile$Stats$8$Stats$txt_StatValue*GEOM*TEXT SET " +  (Integer.valueOf(Statistics.getRace() ) > 0 ? Statistics.getRace(): "-")  +"\0");
		
		
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + which_side + "$Profile$PlayerRole$txt_Role*GEOM*TEXT SET " 
				+ "AGE - " +  Statistics.getAge() + "\0");
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + which_side + "$Profile$PlayerRole$txt_Role02*GEOM*TEXT SET " 
				+ "HEIGHT - " + auctionService.getAllPlayer().get(playerId - 1).getHeight() + "\0");
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + which_side + "$Profile$PlayerRole$txt_Role03*GEOM*TEXT SET " 
				+ "RANKING - " + auctionService.getAllPlayer().get(playerId - 1).getRank() + "\0");
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + which_side + "$Profile$PlayerRole$txt_Role04*GEOM*TEXT SET " 
				+ "PLAYING POSITION - " + auctionService.getAllPlayer().get(playerId - 1).getPlayer_position() + "\0");
		
	}
	
	public void populateSquad(PrintWriter print_writer,int team_id, int which_side, Auction match, AuctionService auctionService, 
			String session_selected_broadcaster) throws Exception 
	{
		
		List<Player> teamList = new ArrayList<Player>();
		
		for(Player plyr : match.getPlayers()) {
			if(plyr.getTeamId() == team_id) {
				teamList.add(plyr);
			}
		}
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Header$Side" + whichSide + "$Select_HeaderType*FUNCTION*Omo*vis_con SET 0\0");
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + which_side + "$Select_GraphicsType*FUNCTION*Omo*vis_con SET 5\0");
		
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + which_side + "$Squad$TeamLogo$Side1$LogoGrp$img_TeamLogo"
				+ "*TEXTURE*IMAGE SET " + logo_path + auctionService.getTeams().get(team_id-1).getTeamName4() + "\0");
		
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + which_side + "$Squad$Data$1$Name$In_Team"
				+ "$Header$txt_StatHead*GEOM*TEXT SET " + "TEAM 1" + "\0");
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + which_side + "$Squad$Data$2$Name$In_Team"
				+ "$Header$txt_StatHead*GEOM*TEXT SET " + "TEAM 2" + "\0");
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + which_side + "$Squad$Data$3$Name$In_Team"
				+ "$Header$txt_StatHead*GEOM*TEXT SET " + "TEAM 3" + "\0");
		
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + which_side + "$Squad$Data$1$Name$Not_Yet"
				+ "$Header$txt_StatHead*GEOM*TEXT SET " + "TEAM 1" + "\0");
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + which_side + "$Squad$Data$2$Name$Not_Yet"
				+ "$Header$txt_StatHead*GEOM*TEXT SET " + "TEAM 2" + "\0");
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + which_side + "$Squad$Data$3$Name$Not_Yet"
				+ "$Header$txt_StatHead*GEOM*TEXT SET " + "TEAM 3" + "\0");
		
		if(teamList.size() > 0) {
			for(int i=0;i<=teamList.size()-1;i++) {
				int playerid1 = Integer.valueOf(teamList.get(i).getPlayersId().split(",")[0]);
				int playerid2 = Integer.valueOf(teamList.get(i).getPlayersId().split(",")[1]);
				
				
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + which_side + "$Squad$Data$" + (i+1) + "$Name$Select"
						+ "*FUNCTION*Omo*vis_con SET 0\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + which_side + "$Squad$Data$" + (i+1) + "$Name$In_Team"
						+ "$1$txt_FirstName*GEOM*TEXT SET " + match.getPlayersList().get(playerid1-1).getFirstname() + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + which_side + "$Squad$Data$" + (i+1) + "$Name$In_Team"
						+ "$1$txt_LastName*GEOM*TEXT SET " + match.getPlayersList().get(playerid1-1).getSurname() + "\0");
				
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + which_side + "$Squad$Data$" + (i+1) + "$Name$In_Team"
						+ "$2$txt_FirstName*GEOM*TEXT SET " + match.getPlayersList().get(playerid2-1).getFirstname() + "\0");
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + which_side + "$Squad$Data$" + (i+1) + "$Name$In_Team"
						+ "$2$txt_LastName*GEOM*TEXT SET " + match.getPlayersList().get(playerid2-1).getSurname() + "\0");
			}
		}
		
		for(int i=teamList.size()+1;i<=3;i++) {
			if(i<=3) {
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + which_side + "$Squad$Data$" + i + "$Name$Select"
						+ "*FUNCTION*Omo*vis_con SET 1\0");
			}
		}	
	}
	
	public void populateCurrentBid(PrintWriter print_writer,int which_side) {
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side1$ProfileDouble$BasePrice$Price_Data$Side" + which_side + "$Select_Value*FUNCTION*Omo*vis_con SET 1 \0");
		
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side1$ProfileDouble$BasePrice$Price_Data$Side"+ which_side + "$CurrentBid$txt_Lakh*GEOM*TEXT SET " + "K"+ " \0");
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side1$ProfileDouble$BasePrice$Price_Data$Side" + which_side + "$CurrentBid$txt_Lakh*GEOM*TEXT SET " + "K" + " \0");
		
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side1$ProfileDouble$BasePrice$Price_Data$Side"+ which_side + "$CurrentBid$ValueGrp$Side1$txt_CurrentBid"
				+ "*GEOM*TEXT SET " + AuctionFunctions.ConvertToLakh(data.getPreviousBid()) + " \0");
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side1$ProfileDouble$BasePrice$Price_Data$Side" + which_side + "$CurrentBid$ValueGrp$Side2$txt_CurrentBid"
				+ "*GEOM*TEXT SET " + AuctionFunctions.ConvertToLakh(data.getPreviousBid()) + " \0");
		
	}
	public void BidChangeOn(PrintWriter print_writer, Auction session_curr_bid, int which_side) {
		if(data.isBid_Start_or_not()) {
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side1$ProfileDouble$BasePrice$Price_Data$Side1$CurrentBid$ValueGrp$Side" + which_side + "$txt_CurrentBid*GEOM*TEXT SET " + 
					AuctionFunctions.ConvertToLakh(data.getPreviousBid()) + " \0");
		}
	}
	public void PlayerSoldOrUnsold(PrintWriter print_writer, Auction auction, String playerId,int which_side) {
		for(int i=auction.getPlayers().size()-1; i >= 0; i--) {
			if(String.valueOf(playerId).equalsIgnoreCase(auction.getPlayers().get(i).getPlayersId())) {
				if(auction.getPlayers().get(i).getSoldOrUnsold().equalsIgnoreCase(AuctionUtil.SOLD)) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side1$ProfileDouble$BasePrice$Price_Data$Side" + which_side + "$Select_Value*FUNCTION*Omo*vis_con SET 2 \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side1$ProfileDouble$BasePrice$Price_Data$Side" + which_side + "$Status$select_Status*FUNCTION*Omo*vis_con SET 1 \0");
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side1$ProfileDouble$BasePrice$Price_Data$Side" + which_side + "$Status$Sold$txt_Value"
							+ "*GEOM*TEXT SET " + AuctionFunctions.ConvertToLakh(auction.getPlayers().get(i).getSoldForPoints()) + " K" + " \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side1$ProfileDouble$BasePrice$Price_Data$Side" + which_side + "$Status$Sold$img_TeamLogo"
							+ "*TEXTURE*IMAGE SET " + logo_path + auction.getTeam().get(auction.getPlayers().get(i).getTeamId()-1).getTeamName4() + "\0");
					
					data.setBid_result(auction.getPlayers().get(i).getSoldOrUnsold());
					data.setPlayer_sold_or_unsold(true);
				}else if(auction.getPlayers().get(i).getSoldOrUnsold().equalsIgnoreCase(AuctionUtil.UNSOLD)) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side1$ProfileDouble$BasePrice$Price_Data$Side" + which_side + "$Select_Value*FUNCTION*Omo*vis_con SET 2 \0");
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side1$ProfileDouble$BasePrice$Price_Data$Side" + which_side + "$Status$select_Status*FUNCTION*Omo*vis_con SET 2 \0");
					
					//print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_Profile$Profile$Side" + which_side + "$Stats$Side1$SelectStyle$Status*FUNCTION*Omo*vis_con SET 1\0");
					
					data.setBid_result(auction.getPlayers().get(i).getSoldOrUnsold());
					data.setPlayer_sold_or_unsold(true);
				}else {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side1$ProfileDouble$BasePrice$Price_Data$Side" + which_side + "$Select_Value*FUNCTION*Omo*vis_con SET 0 \0");
				}
				break;
			}
		}
	}
	
	public void populatePlayerProfile(boolean is_this_updating, PrintWriter print_writer, int which_side, String playerId, List<Statistics> stats, Auction auction, 
			Auction session_curr_bid, AuctionService auctionService, String session_selected_broadcaster) throws InterruptedException 
	{
		if(session_curr_bid.getCurrentPlayers() != null) {
			if(data.isData_on_screen() == true) {
				if(data.isPlayer_sold_or_unsold() == false) {
					PlayerSoldOrUnsold(print_writer, auction, playerId, which_side);
					
					if(data.isPlayer_sold_or_unsold() == true) {
						if(data.getBid_result() != null && !data.getBid_result().isEmpty()) {
							if(data.getBid_result().equalsIgnoreCase(AuctionUtil.SOLD) || data.getBid_result().equalsIgnoreCase(AuctionUtil.RTM)) {
								print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Change_InfoBar$CurrentBid START \0");
								print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Change_InfoBar$Sold START \0");
							}else if(data.getBid_result().equalsIgnoreCase(AuctionUtil.UNSOLD)) {
								print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Shift_PositionX START \0");
								print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Change_InfoBar$Unsold START \0");
							}
						}
						TimeUnit.MILLISECONDS.sleep(2000);
						PlayerSoldOrUnsold(print_writer, auction, playerId, 1);
						print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*anim_InfoBar$In_Out$CurrentBid SHOW 0.0\0");
						if(data.getBid_result() != null && !data.getBid_result().isEmpty()) {
							if(data.getBid_result().equalsIgnoreCase(AuctionUtil.SOLD) || data.getBid_result().equalsIgnoreCase(AuctionUtil.RTM)) {
								print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*anim_InfoBar$In_Out$Sold SHOW 0.800\0");
							}else if(data.getBid_result().equalsIgnoreCase(AuctionUtil.UNSOLD)) {
								print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*anim_InfoBar$In_Out$Unsold SHOW 0.800\0");
							}
						}
						print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Change_InfoBar SHOW 0.0 \0");
					}
				}
			}else {
				if(data.isPlayer_sold_or_unsold() == false) {
					PlayerSoldOrUnsold(print_writer, auction, playerId, 1);
				}
			}
		}
		
//		if(is_this_updating == false) {
//			String Container = data.getWithPlayerPhoto()==0 ? "$Without_Image" : "$With_Image";;
//			
//			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + which_side + "$Select_GraphicsType*FUNCTION*Omo*vis_con SET 1\0");
//			
//			if(data.getWithPlayerPhoto() == 1) {
//				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_InfoBar$Shift_ForStats$CenterData$Side"+ which_side + Container + "$ImageGrp$ImageGrp_Out$"
//						+ "img_PlayerImage*TEXTURE*IMAGE SET " + photo_path + auctionService.getAllPlayer().get(playerId - 1).getPhotoName() + AuctionUtil.PNG_EXTENSION + "\0");
//			}
//			
//			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$gfx_FullFrames$Main$Side" + which_side + Container + "$Profile$Name$FirstName*GEOM*TEXT SET " + 
//					auctionService.getAllPlayer().get(playerId - 1).getFull_name() + " \0");
//			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$gfx_InfoBar$Shift_ForStats$CenterData$Side" + which_side + Container +  "$TopTextGrp$txt_Age*GEOM*TEXT SET " + 
//					(auctionService.getAllPlayer().get(playerId - 1).getAge()== null ? "" : auctionService.getAllPlayer().get(playerId - 1).getAge()+" YEARS" ) + " \0");
//			
//			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$gfx_InfoBar$Shift_ForStats$CenterData$Side"+ which_side + Container + "$BottomLine$Flag$"
//					+ "img_Flag*TEXTURE*IMAGE SET "+ flag_path + auctionService.getAllPlayer().get(playerId - 1).getNationality().trim().replace(" ", "_") + "\0");
//
//			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$gfx_InfoBar$Shift_ForStats$CenterData$Side" + which_side + Container +  
//					"$BottomLine$Flag$Select_Flag*FUNCTION*Omo*vis_con SET 1 \0");
//			
//			data.setPreviousBid(Integer.valueOf(auctionService.getAllPlayer().get(playerId - 1).getBasePrice()));
//			if(auctionService.getAllPlayer().get(playerId - 1).getBasePrice().equalsIgnoreCase("2000")) {
//				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$gfx_InfoBar$Shift_ForStats$CenterData$Side" + which_side + Container +  
//						"$BottomLine$BasePrice$txt_BasePriceValue*GEOM*TEXT SET 20L TOKENS\0");
//			}else if(auctionService.getAllPlayer().get(playerId - 1).getBasePrice().equalsIgnoreCase("1400")) {
//				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$gfx_InfoBar$Shift_ForStats$CenterData$Side" + which_side + Container + 
//						"$BottomLine$BasePrice$txt_BasePriceValue*GEOM*TEXT SET 14L TOKENS\0");
//			}else if(auctionService.getAllPlayer().get(playerId - 1).getBasePrice().equalsIgnoreCase("800")) {
//				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$gfx_InfoBar$Shift_ForStats$CenterData$Side" + which_side + Container + 
//						"$BottomLine$BasePrice$txt_BasePriceValue*GEOM*TEXT SET 8L TOKENS\0");
//			}else if(auctionService.getAllPlayer().get(playerId - 1).getBasePrice().equalsIgnoreCase("300")) {
//				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$gfx_InfoBar$Shift_ForStats$CenterData$Side" + which_side + Container + 
//						"$BottomLine$BasePrice$txt_BasePriceValue*GEOM*TEXT SET 3L TOKENS\0");
//			}
//		
//			if(data.isPlayer_sold_or_unsold() == false) {
//				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$gfx_InfoBar$Shift_ForStats$Right_Data$Side" + which_side + 
//						"$Select_GraphicsType*FUNCTION*Omo*vis_con SET 0 \0");
//			}
//		}
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
		print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Out START \0");	
	}
	
	public void processPreview(PrintWriter print_writer, String whatToProcess, int whichSide) {
		String previewCommand = "";
		
		if(whichSide == 1) {
			switch (whatToProcess.toUpperCase()) {
			case "POPULATE-FF-PLAYERPROFILE":
				previewCommand = previewCommand +  "anim_InfoBar$In_Out$CenterData 0.800 anim_InfoBar$In_Out$CenterData$Essentials 0.800 "
						+ "anim_InfoBar$In_Out$CenterData$Essentials$In 0.800 anim_InfoBar$In_Out$CenterData$Image 0.800 anim_InfoBar$In_Out$CenterData$Image$In 0.800 "
						+ "anim_InfoBar$In_Out$CenterData$Data 0.800 anim_InfoBar$In_Out$CenterData$Data$In 0.800 Shift_PositionX 0.800 ";
				if(data.isBid_Start_or_not()) {
					previewCommand += "anim_InfoBar$In_Out$CurrentBid 0.800 anim_InfoBar$In_Out$CurrentBid$In 0.800 ";
				}
				if(data.getBid_result()!=null && (data.getBid_result().equalsIgnoreCase(AuctionUtil.SOLD) || data.getBid_result().equalsIgnoreCase(AuctionUtil.RTM))) {
					previewCommand += "anim_InfoBar$In_Out$Sold 0.800 anim_InfoBar$In_Out$Sold$In 0.800 ";
				}else if(data.getBid_result()!= null && data.getBid_result().equalsIgnoreCase(AuctionUtil.UNSOLD)) {
					previewCommand += "anim_InfoBar$In_Out$Unsold 0.800 anim_InfoBar$In_Out$UnSold$In 0.800 ";
				}
				break;
			case "POPULATE-TEAM_CURR_BID":
				previewCommand = previewCommand + "anim_InfoBar$In_Out$CenterData 0.800 anim_InfoBar$In_Out$CenterData$In 0.800";
				break;
			case "POPULATE-PROFILE_STATS":
				previewCommand = previewCommand + "anim_InfoBar$In_Out$BottomStats 0.800 anim_InfoBar$In_Out$BottomStats$In 0.800";
				break;
			case "POPULATE-RTM_AVAILABLE":
				previewCommand = "anim_InfoBar$In_Out$CenterData 0.800 anim_InfoBar$In_Out$CenterData$Essentials 0.800 anim_InfoBar$In_Out$CenterData$Essentials$In 0.800 "
						+ "anim_InfoBar$In_Out$CenterData$Image 0.800 anim_InfoBar$In_Out$CenterData$Image$In 0.800 anim_InfoBar$In_Out$CenterData$Data 0.800 "
						+ "anim_InfoBar$In_Out$CenterData$Data$In 0.800 Shift_PositionX 0.800 anim_InfoBar$In_Out$Sold 0.800 anim_InfoBar$In_Out$RTM$Logo 0.800 "
						+ "anim_InfoBar$In_Out$RTM$Logo$In 0.500 anim_InfoBar$In_Out$RTM$Essentials 0.800 anim_InfoBar$In_Out$RTM$Essentials$In 0.700";
				break;
			}
		}else {
			if(data.isData_on_screen() && !whatToProcess.equalsIgnoreCase("POPULATE-FF-PLAYERPROFILE")) {
				previewCommand = previewCommand +  "anim_InfoBar$In_Out$CenterData 0.800 anim_InfoBar$In_Out$CenterData$Essentials 0.800 "
						+ "anim_InfoBar$In_Out$CenterData$Essentials$In 0.800 anim_InfoBar$In_Out$CenterData$Image 0.800 anim_InfoBar$In_Out$CenterData$Image$In 0.800 "
						+ "anim_InfoBar$In_Out$CenterData$Data 0.800 anim_InfoBar$In_Out$CenterData$Data$In 0.800 Shift_PositionX 0.800 ";
				if(data.isBid_Start_or_not()) {
					previewCommand += "anim_InfoBar$In_Out$CurrentBid 0.800 anim_InfoBar$In_Out$CurrentBid$In 0.800 ";
				}
				if(data.getBid_result()!=null && (data.getBid_result().equalsIgnoreCase(AuctionUtil.SOLD) || data.getBid_result().equalsIgnoreCase(AuctionUtil.RTM))) {
					previewCommand += "anim_InfoBar$In_Out$Sold 0.800 anim_InfoBar$In_Out$Sold$In 0.800 ";
				}else if(data.getBid_result()!= null && data.getBid_result().equalsIgnoreCase(AuctionUtil.UNSOLD)) {
					previewCommand += "anim_InfoBar$In_Out$Unsold 0.800 anim_InfoBar$In_Out$UnSold$In 0.800 ";
				}
			}
			switch (whatToProcess.toUpperCase()) {
			case "POPULATE-PROFILE_STATS":
				previewCommand = previewCommand+ "Change_InfoBar$BottomStats 0.800 Change_InfoBar$BottomStats$In 0.800";
				break;
			}	
		}
		print_writer.println("-1 RENDERER PREVIEW SCENE*/Default/Overlays " + "C:/Temp/Preview.png " + previewCommand + "\0");
		
	}
	public void processPreviewFullFrames(PrintWriter print_writer, String whatToProcess, int whichSide) {
		String previewCommand = "";
		if (whatToProcess.equalsIgnoreCase("POPULATE-PROFILE_STATS_CHANGE")) {
			previewCommand = "anim_Profile 2.800 anim_Profile$In_Out 1.700 anim_Profile$In_Out$Essentials 1.700 anim_Profile$In_Out$Essentials$In 1.400 "
					+ "anim_Profile$In_Out$Profile 2.800 anim_Profile$In_Out$Profile$In 2.800 Change_Stats 0.700 Change_Stats$Side1 0.700 Change_Stats$Side2 0.700 ";	
		}
		if(whichSide == 1) {
			switch (whatToProcess.toUpperCase()) {
			case "POPULATE-FF_IDENT":
				previewCommand = "anim_Ident 2.500 anim_Ident$In_Out 1.600 anim_Ident$In_Out$Ident 1.600 anim_Ident$In_Out$Ident$In 1.600 ";
				break;
			case "POPULATE-FF_RTM_AND_PURSE_REMAINING":
				previewCommand = "anim_Fullframes 2.800 anim_Fullframes$In_Out$Essentials$In 1.400 anim_Fullframes$In_Out$Header$In 1.000 "
						+ "anim_Fullframes$In_Out$Team_Details$In 1.500";
				break;
			case "POPULATE-PLAYERPROFILE_FF":
				previewCommand = "anim_Profile 2.800 anim_Profile$In_Out 2.800 anim_Profile$In_Out$Essentials 2.800 anim_Profile$In_Out$Essentials$In 2.800 "
						+ "anim_Profile$In_Out$Profile 2.800 anim_Profile$In_Out$Profile$In 2.800";
				break;
			case "POPULATE-FF_FIVE_TOP_BUYS_AUCTION": case "POPULATE-ONLY_SQUAD": case "POPULATE-FF_FIVE_TOP_BUY_TEAM":
				previewCommand = "anim_Fullframes 2.800 anim_Fullframes$In_Out$Essentials$In 1.400 anim_Fullframes$In_Out$Header$In 1.000 ";
				
				switch (whatToProcess.toUpperCase()) {
				case "POPULATE-FF_FIVE_TOP_BUYS_AUCTION": case "POPULATE-FF_FIVE_TOP_BUY_TEAM":
					previewCommand = previewCommand + "anim_Fullframes$In_Out$Top5_Buys$In 1.500";
					break;
				case "POPULATE-ONLY_SQUAD":
					previewCommand = previewCommand + "anim_Fullframes$In_Out$Squad$In 1.500";
					break;
				}
				break;
			}
		}else {
			
			switch (whatToProcess.toUpperCase()) {
			case "POPULATE-FF_IDENT":
				previewCommand = "anim_Fullframe$In_Out$Main$BS_Logo$In 0.0 Change 2.400 Change$MatchId 2.400 Change$MatchId$Change_In 2.400 Change$MatchId$Change_In$In 2.400";
				break;
			case "POPULATE-REMAINING_PURSE_ALL":
				previewCommand = "anim_Fullframe$In_Out$Main$BS_Logo$In 0.0 Change 2.400 Change$Header 2.160 Change$Header$Change_In 2.160 Change$PurseRemaining 2.200 Change$PurseRemaining$Change_In 2.200 Change$PurseRemaining$Change_In$In 2.200";
				break;
			case "POPULATE-FF-PLAYERPROFILE":
				previewCommand = "anim_Fullframe$In_Out$Main$BS_Logo$In 0.0 Change 2.400 Change$Header 0.0 Change$Profile 2.280 Change$Profile$Change_In 2.280 Change$Profile$Change_In$In 2.280";
				break;
				
			case "POPULATE-ONLY_SQUAD":
				previewCommand = "anim_Fullframe$In_Out$Main$BS_Logo$In 0.0 Change 2.400 Change$Header 0.0 Change$Squad 1.960 Change$Squad$Change_In 1.960 Change$Squad$Change_In$In 1.960";
				break;
			case "POPULATE-FF-PLAYERPROFILE_DOUBLE":
				previewCommand = "anim_Fullframe$In_Out$Main$BS_Logo$In 0.0 Change 2.400 Change$Header 0.0 Change$ProfileDouble 2.060 Change$ProfileDouble$Change_In 2.060 Change$ProfileDouble$Change_In$In 2.060";
				break;
			}
			
			System.out.println(whatToProcess + " - " + which_graphics_onscreen);
			
		}
		print_writer.println("-1 RENDERER PREVIEW SCENE*/Default/FullFrames " + "C:/Temp/Preview.tga " + previewCommand + "\0");
	}
	public void processPreviewLowerThirds(PrintWriter print_writer, String whatToProcess, int whichSide) {
		String previewCommand = "";
		
		if(whichSide == 1) {
			switch (whatToProcess.toUpperCase()) {
			case "POPULATE-L3-NAMESUPER":
				previewCommand = "anim_LowerThird 1.400 anim_LowerThird$In_Out 0.800 anim_LowerThird$In_Out$CenterData 0.800 "
						+ "anim_LowerThird$In_Out$CenterData$Essentials$In 0.600 anim_LowerThird$In_Out$CenterData$Image$In 0.600 "
						+ "anim_LowerThird$In_Out$CenterData$Data$In 0.600";
				break;
			case "POPULATE-L3-FLIPPER": case "POPULATE-FLIPPER_SQUAD":
				previewCommand = "Flipper$In_Out$In 0.560 Scroll 8.540 ";
				break;
			}
		}else {
			switch (whatToProcess.toUpperCase()) {
			case "POPULATE-PLAYERPROFILE_LT": case "POPULATE-L3-NAMESUPER":
				previewCommand = "Change_LowerThird 0.900 Change_LowerThird$Change_Out 0.600 Change_LowerThird$Chnage_In 0.900";
				break;
			case "POPULATE-L3-FLIPPER":
				previewCommand = "Flipper$In_Out$In 0.560 Scroll 8.540 ";
				break;
				
			}
		}
		print_writer.println("-1 RENDERER PREVIEW SCENE*/Default/Overlays " + "C:/Temp/Preview.png " + previewCommand + "\0");
	}
	
	public void processAnimation(PrintWriter print_writer, String animationName,String animationCommand, String which_broadcaster,int which_layer)
	{
		print_writer.println("-1 RENDERER*STAGE*DIRECTOR*In START \0");
	}
	
	public String toString() {
		return "Doad [status=" + status + ", slashOrDash=" + slashOrDash + "]";
	}
	
	
}