var match_data,session_auction;
let teams = [];  
var which_GFX = "";
let currentTeamIndex = 0,id = 0;  
let teamRotationInterval = null; 

// Function to start rotating teams
function startTeamRotation() {
    teamRotationInterval = setInterval(function() {
        if (teams && teams.length > 0) {
	        // Get the current team based on the index
	         let team = teams[(currentTeamIndex + 1) % teams.length];
	         id = team.teamId;
	        // Move to the next team, circularly
	        currentTeamIndex = (currentTeamIndex + 1) % teams.length;
	        processAuctionProcedures('POPULATE-SQUAD');
        }
    }, 15000);  
}

function stopTeamRotation() {
    if (teamRotationInterval) {
        clearInterval(teamRotationInterval);
        teamRotationInterval = null;
    }
}

function processWaitingButtonSpinner(whatToProcess) 
{
	switch (whatToProcess) {
	case 'START_WAIT_TIMER': 
		$('.spinner-border').show();
		$(':button').prop('disabled', true);
		break;
	case 'END_WAIT_TIMER': 
		$('.spinner-border').hide();
		$(':button').prop('disabled', false);
		break;
	}
	
}
function processUserSelectionData(whatToProcess,dataToProcess){
	switch (whatToProcess) {
	case 'LOGGER_FORM_KEYPRESS':
		switch(dataToProcess){
		case ' '://Space
			if(which_GFX === "POPULATE-SQUAD"){
				stopTeamRotation();
				which_GFX = "";
			 }
			processAuctionProcedures('CLEAR-ALL');
			break;
		case 'Alt_r':
			processAuctionProcedures('RE_READ_DATA');
			break;
			
		case '-'://189
			if(confirm('It will Also Delete Your Preview from Directory...\r\n \r\nAre You Sure To Animate Out? ') == true){
				if(which_GFX === "POPULATE-SQUAD"){
					stopTeamRotation();
					which_GFX = "";
				 }
				processAuctionProcedures('ANIMATE-OUT');	
			}
			break;
		case '9':
			processAuctionProcedures('TOP_15_AUCTION_CHANGEON');
			break;	
		case 'b':
			processAuctionProcedures('BASE_LOAD');
			break;	
		case 'F1':
			$("#captions_div").hide();
			$("#cancel_match_setup_btn").hide();
			$("#expiry_message").hide();
			stopTeamRotation();
			which_GFX = "";
			processAuctionProcedures('PLAYERPROFILE_GRAPHICS-OPTIONS');
			break;
		case 'F4':
			stopTeamRotation();
			which_GFX = "";
			processAuctionProcedures('POPULATE-FF_IDENT');
			break;
		}
		break;
	}
}
function initialiseForm(whatToProcess,dataToProcess)
{
	session_auction = dataToProcess;
	switch (whatToProcess) {
	case 'initialise':
		processUserSelection($('#select_broadcaster'));
		break;
	case 'UPDATE-MATCH-ON-OUTPUT-FORM':
		break;
	case 'UPDATE-CONFIG':
		document.getElementById('configuration_file_name').value = $('#select_configuration_file option:selected').val();
		document.getElementById('vizIPAddress').value = dataToProcess.ipAddress;
		document.getElementById('vizPortNumber').value = dataToProcess.portNumber;
		break;
	}
}
function processUserSelection(whichInput)
{	
	switch ($(whichInput).attr('name')) {	
	case 'animateout_graphic_btn':
		stopTeamRotation();
		which_GFX = "";
		if(confirm('It will Also Delete Your Preview from Directory...\r\n \r\nAre You Sure To Animate Out? ') == true){
			processAuctionProcedures('ANIMATE-OUT');	
		}
		break;
	case 'clearall_graphic_btn':
		stopTeamRotation();
		which_GFX = "";
		processAuctionProcedures('CLEAR-ALL');
		break;
	case 'top_five_sold_graphic_btn':
		stopTeamRotation();
		processAuctionProcedures('POPULATE-TOP_FIVE_SOLD');
		break;
	case 'top_sold_graphic_btn':
		stopTeamRotation();
		processAuctionProcedures('POPULATE-TOP_SOLD');
		break;
	case 'top_15_sold_graphic_btn':
		stopTeamRotation();
		processAuctionProcedures('POPULATE-TOP_15_SOLD');
		break;	
	case 'remaining_purse_graphic_btn':
		stopTeamRotation();
		which_GFX = "";
		processAuctionProcedures('POPULATE-REMAINING_PURSE_ALL');
		break;
	case 'rtm_squad_graphic_btn':	
		stopTeamRotation();
		which_GFX = "";
		processAuctionProcedures('POPULATE-RTM_SQUAD');
		break;
	case 'rtm_available_graphic_btn':	
		stopTeamRotation();
		which_GFX = "";
		processAuctionProcedures('POPULATE-RTM_AVAILABLE');
		break;
	case 'slots_rem_graphic_btn':	
		stopTeamRotation();
		which_GFX = "";
		processAuctionProcedures('POPULATE-SLOTS_REMAINING');
		break;	
	case 'Ident_graphic_btn':
		stopTeamRotation();
		which_GFX = "";
		processAuctionProcedures('POPULATE-FF_IDENT');
		break;
	case 'iconic_Player_graphic_btn':
		stopTeamRotation();
		which_GFX = "";
		processAuctionProcedures('POPULATE-FF_ICONIC_PLAYERS');
		break;
		
	case 'playerprofile_graphic_btn': case 'squad_graphic_btn': case 'remaining_purse_single_graphic_btn': case 'crawler_graphic_btn':
	case 'squad_with_role_count_graphic_btn': case 'Only_squad_graphic_btn': case 'top_sold_teams_graphic_btn': case 'top_five_sold_teams_graphic_btn':
	case 'top_15_sold_teams_graphic_btn':
		$("#captions_div").hide();
		$("#cancel_match_setup_btn").hide();
		$("#expiry_message").hide();
		
		switch ($(whichInput).attr('name')) {
		case 'squad_with_role_count_graphic_btn':
			stopTeamRotation();
			which_GFX = "";
			processAuctionProcedures('SQUAD-ROLE-COUNT_GRAPHICS-OPTIONS');
			break;
		case 'playerprofile_graphic_btn':
			stopTeamRotation();
			which_GFX = "";
			processAuctionProcedures('PLAYERPROFILE_GRAPHICS-OPTIONS');
			break;
		case 'squad_graphic_btn':
			processAuctionProcedures('SQUAD_GRAPHICS-OPTIONS');
			break;
		case 'Only_squad_graphic_btn':
			stopTeamRotation();
			which_GFX = "";
			processAuctionProcedures('ONLY_SQUAD_GRAPHICS-OPTIONS');
			break;
		case 'remaining_purse_single_graphic_btn':
			stopTeamRotation();
			which_GFX = "";
			processAuctionProcedures('SINGLE_PURSE_GRAPHICS-OPTIONS');
			break;
		case 'top_sold_teams_graphic_btn':
			stopTeamRotation();
			which_GFX = "";
			processAuctionProcedures('TOP_SOLD_TEAMS_GRAPHICS-OPTIONS');
			break;
		case 'top_15_sold_teams_graphic_btn':
			stopTeamRotation();
			which_GFX = "";
			processAuctionProcedures('TOP_15_SOLD_TEAMS_GRAPHICS-OPTIONS');
			break;
		case 'top_five_sold_teams_graphic_btn':
			stopTeamRotation();
			which_GFX = "";
			processAuctionProcedures('TOP_FIVE_SOLD_TEAMS_GRAPHICS-OPTIONS');
			break;
		case 'crawler_graphic_btn':
			addItemsToList('CRAWLER-OPTIONS',null);
			break;	
		}
		break;
		
	case 'populate_namesuper_btn': case 'populate_namesuper_player_btn': case 'populate_playerprofile_btn':	case 'populate_squad_btn':
	case 'populate_single_purse_btn': case 'populate_crawl_btn': case 'populate_squad_role_btn': case 'populate_only_squad_btn':
	case 'populate_top_sold_teams_btn': case 'populate_top_five_sold_teams_btn': case 'populate_top_15_sold_teams_btn':
		processWaitingButtonSpinner('START_WAIT_TIMER');
		switch ($(whichInput).attr('name')) {
		case 'populate_squad_role_btn':
			processAuctionProcedures('POPULATE-SQUAD_ROLE');
			break;
		case 'populate_namesuper_btn':
			processAuctionProcedures('POPULATE-L3-NAMESUPER');
			break;
		case 'populate_namesuper_player_btn':
			processAuctionProcedures('POPULATE-L3-NAMESUPER-PLAYER');
			break;
		case 'populate_playerprofile_btn':
			processAuctionProcedures('POPULATE-FF-PLAYERPROFILE');
			break;
		case 'populate_squad_btn':
			processAuctionProcedures('POPULATE-SQUAD');
			break;
		case 'populate_only_squad_btn':
			processAuctionProcedures('POPULATE-ONLY_SQUAD');
			break;
		case 'populate_single_purse_btn':
			processAuctionProcedures('POPULATE-SINGLE_PURSE');
			break;
		case 'populate_top_sold_teams_btn':
			processAuctionProcedures('POPULATE-TOP_SOLD_TEAMS');
			break;
		case 'populate_top_15_sold_teams_btn':
			processAuctionProcedures('POPULATE-TOP_15_SOLD_TEAMS');
			break;
		case 'populate_top_five_sold_teams_btn':
			processAuctionProcedures('POPULATE-TOP_FIVE_SOLD_TEAMS');
			break;
		case 'populate_crawl_btn':
			processAuctionProcedures('POPULATE-CRAWL');
			break;
		}
		break;
	case 'cancel_match_setup_btn':
		document.output_form.method = 'post';
		document.output_form.action = 'initialise';
	   	document.output_form.submit();
		break;
	case 'cancel_graphics_btn':
		$('#select_graphic_options_div').empty();
		document.getElementById('select_graphic_options_div').style.display = 'none';
		$("#captions_div").show();
		$("#cancel_match_setup_btn").show();
		processAuctionProcedures('POPULATE-CANCEL');
		break;
	case 'select_broadcaster':
		switch ($('#select_broadcaster :selected').val().toUpperCase()) {
		case 'HANDBALL': case 'ISPL': case 'UTT':
			$('#vizPortNumber').attr('value','1980');
			$('label[for=which_layer], select#which_layer').hide();
			break;
		}
		break;
	case 'load_scene_btn':
		if(checkEmpty($('#vizIPAddress'),'IP Address Blank') == false
			|| checkEmpty($('#vizPortNumber'),'Port Number Blank') == false) {
			return false;
		}
      	document.initialise_form.submit();
		break;
	case 'selectTeams':
		switch ($(whichInput).attr('name')) {
		case 'selectTeams':
			addItemsToList('POPULATE-PROFILE',match_data);
			break;
	}
	break;
	/*case 'selectTeams':
		switch ($(whichInput).attr('name')) {
		case 'selectTeams':
			addItemsToList('POPULATE-PROFILE',match_data);
			break;
	}
	break;*/
	}
}
function processAuctionProcedures(whatToProcess)
{
	var valueToProcess;
	switch(whatToProcess) {
	
	case 'READ-MATCH-AND-POPULATE':
		valueToProcess = $('#matchFileTimeStamp').val();
		break;
	case 'POPULATE-FF-PLAYERPROFILE':
		switch ($('#selected_broadcaster').val().toUpperCase()) {
		case 'HANDBALL':
			valueToProcess = 'D:/DOAD_In_House_Everest/Everest_Sports/Everest_Handball_Auction_2023/Scenes/PlayerProfile_Pic.sum' + ',' + $('#selectPlayerName option:selected').val();
			break;
		case 'ISPL': 
			valueToProcess = 'D:/DOAD_In_House_Everest/Everest_Cricket/Everest_ISPL_Auction_2024/Scenes/PlayerProfile_Pic.sum' + ',' + $('#selectPlayerName option:selected').val();
			break;
		case 'UTT': 
			valueToProcess = 'D:/DOAD_In_House_Everest/Everest_Sports/Everest_UTT_Auction_2025/Scenes/PlayerProfile_Pic.sum' + ',' + $('#selectPlayerName option:selected').val();
			break;	
		}
		break;
	case 'POPULATE-SQUAD': case 'POPULATE-SQUAD_ROLE':
		switch ($('#selected_broadcaster').val().toUpperCase()) {
		case 'HANDBALL':
			valueToProcess = 'D:/DOAD_In_House_Everest/Everest_Sports/Everest_Handball_Auction_2023/Scenes/Squad.sum' + ',' + $('#selectTeamName option:selected').val();
			break;
		case 'ISPL':
			let team_index;
			if(which_GFX=== "POPULATE-SQUAD"){
				team_index = id;
			}else{
				team_index =$('#selectTeamName option:selected').val();
			}
			valueToProcess = 'D:/DOAD_In_House_Everest/Everest_Cricket/Everest_ISPL_Auction_2024/Scenes/Squad.sum' + ',' + team_index;
			break;	
		case 'UTT':
			let team_index1;
			if(which_GFX=== "POPULATE-SQUAD"){
				team_index = id;
			}else{
				team_index =$('#selectTeamName option:selected').val();
			}
			valueToProcess = 'D:/DOAD_In_House_Everest/Everest_Sports/Everest_UTT_Auction_2025/Scenes/Squad.sum' + ',' + team_index;
			break;	
		}
		break;
	case 'POPULATE-ONLY_SQUAD':
		switch ($('#selected_broadcaster').val().toUpperCase()) {
		case 'HANDBALL':
			valueToProcess = 'D:/DOAD_In_House_Everest/Everest_Sports/Everest_Handball_Auction_2023/Scenes/Squad.sum' + ',' + $('#selectTeamName option:selected').val();
			break;
		case 'ISPL':
			valueToProcess = 'D:/DOAD_In_House_Everest/Everest_Cricket/Everest_ISPL_Auction_2024/Scenes/Squad.sum' + ',' + $('#selectTeamName option:selected').val();
			break;
		case 'UTT':
			valueToProcess = 'D:/DOAD_In_House_Everest/Everest_Sports/Everest_UTT_Auction_2025/Scenes/Squad.sum' + ',' + $('#selectTeamName option:selected').val();
			break;			
		}
		break;
	case 'POPULATE-SINGLE_PURSE':
		switch ($('#selected_broadcaster').val().toUpperCase()) {
		case 'HANDBALL':
			valueToProcess = 'D:/DOAD_In_House_Everest/Everest_Sports/Everest_Handball_Auction_2023/Scenes/RemainingPurse_Individual.sum' + ',' + $('#selectTeamName option:selected').val();
			break;
		case 'ISPL':
			valueToProcess = 'D:/DOAD_In_House_Everest/Everest_Cricket/Everest_ISPL_Auction_2024/Scenes/RemainingPurse_Individual.sum' + ',' + $('#selectTeamName option:selected').val();
			break;
		case 'UTT':
			valueToProcess = 'D:/DOAD_In_House_Everest/Everest_Sports/Everest_UTT_Auction_2025/Scenes/RemainingPurse_Individual.sum' + ',' + $('#selectTeamName option:selected').val();
			break;		
		}
		break;
	case 'POPULATE-TOP_FIVE_SOLD_TEAMS':
		switch ($('#selected_broadcaster').val().toUpperCase()) {
		case 'HANDBALL':
			valueToProcess = 'D:/DOAD_In_House_Everest/Everest_Sports/Everest_Handball_Auction_2023/Scenes/Top_Buys.sum' ;
			break;
		case 'ISPL':
			valueToProcess = 'D:/DOAD_In_House_Everest/Everest_Cricket/Everest_ISPL_Auction_2024/Scenes/TOP_5_Buys.sum' + ',' + $('#selectTeamName option:selected').val();
			break;
		case 'UTT':
			valueToProcess = 'D:/DOAD_In_House_Everest/Everest_Sports/Everest_UTT_Auction_2025/Scenes/TOP_5_Buys.sum' + ',' + $('#selectTeamName option:selected').val();
			break;		
		}
		break;
	case 'POPULATE-TOP_SOLD_TEAMS':
		switch ($('#selected_broadcaster').val().toUpperCase()) {
		case 'HANDBALL':
			valueToProcess = 'D:/DOAD_In_House_Everest/Everest_Sports/Everest_Handball_Auction_2023/Scenes/Top_Buys.sum' ;
			break;
		case 'ISPL':
			valueToProcess = 'D:/DOAD_In_House_Everest/Everest_Cricket/Everest_ISPL_Auction_2024/Scenes/Top_Buys.sum' + ',' + $('#selectTeamName option:selected').val();
			break;	
		case 'UTT':
			valueToProcess = 'D:/DOAD_In_House_Everest/Everest_Sports/Everest_UTT_Auction_2025/Scenes/Top_Buys.sum' + ',' + $('#selectTeamName option:selected').val();
			break;	
		}
		break;	
	case 'POPULATE-TOP_15_SOLD_TEAMS':
		switch ($('#selected_broadcaster').val().toUpperCase()) {
		case 'HANDBALL':
			valueToProcess = 'D:/DOAD_In_House_Everest/Everest_Sports/Everest_Handball_Auction_2023/Scenes/Top_Buys.sum' ;
			break;
		case 'ISPL':
			valueToProcess = 'D:/DOAD_In_House_Everest/Everest_Cricket/Everest_ISPL_Auction_2024/Scenes/TOP_15_Buys.sum' + ',' + 
				$('#selectTeamName option:selected').val();
			break;	
		case 'UTT':
			valueToProcess = 'D:/DOAD_In_House_Everest/Everest_Sports/Everest_UTT_Auction_2025/Scenes/TOP_15_Buys.sum' + ',' + 
				$('#selectTeamName option:selected').val();
			break;	
		}
		break;
	case 'POPULATE-CRAWL':
		switch ($('#selected_broadcaster').val().toUpperCase()) {
		case 'ISPL':
			valueToProcess = 'D:/DOAD_In_House_Everest/Everest_Cricket/Everest_ISPL_Auction_2024/Scenes/Ticker.sum' + ',' + 
			$('#selectCrawl1 option:selected').val() + ',' + $('#selectCrawl2 option:selected').val();
			break;
		case 'UTT':
			valueToProcess = 'D:/DOAD_In_House_Everest/Everest_Sports/Everest_UTT_Auction_2025/Scenes/Ticker.sum' + ',' + 
			$('#selectCrawl1 option:selected').val() + ',' + $('#selectCrawl2 option:selected').val();
			break;		
		}
		break;
	case 'POPULATE-TOP_FIVE_SOLD':
		switch ($('#selected_broadcaster').val().toUpperCase()) {
		case 'HANDBALL':
			valueToProcess = 'D:/DOAD_In_House_Everest/Everest_Sports/Everest_Handball_Auction_2023/Scenes/Top_Buys.sum' ;
			break;
		case 'ISPL':
			valueToProcess = 'D:/DOAD_In_House_Everest/Everest_Cricket/Everest_ISPL_Auction_2024/Scenes/TOP_5_Buys.sum' ;
			break;
		case 'UTT':
			valueToProcess = 'D:/DOAD_In_House_Everest/Everest_Sports/Everest_UTT_Auction_2025/Scenes/TOP_5_Buys.sum' ;
			break;			
		}
		break;
	case 'POPULATE-TOP_SOLD':
		switch ($('#selected_broadcaster').val().toUpperCase()) {
		case 'HANDBALL':
			valueToProcess = 'D:/DOAD_In_House_Everest/Everest_Sports/Everest_Handball_Auction_2023/Scenes/Top_Buys.sum' ;
			break;
		case 'ISPL':
			valueToProcess = 'D:/DOAD_In_House_Everest/Everest_Cricket/Everest_ISPL_Auction_2024/Scenes/Top_Buys.sum' ;
			break;
		case 'UTT':
			valueToProcess = 'D:/DOAD_In_House_Everest/Everest_Sports/Everest_UTT_Auction_2025/Scenes/Top_Buys.sum' ;
			break;		
		}
		break;
	case 'POPULATE-TOP_15_SOLD':
		switch ($('#selected_broadcaster').val().toUpperCase()) {
		case 'HANDBALL':
			valueToProcess = 'D:/DOAD_In_House_Everest/Everest_Sports/Everest_Handball_Auction_2023/Scenes/Top_Buys.sum' ;
			break;
		case 'ISPL':
			valueToProcess = 'D:/DOAD_In_House_Everest/Everest_Cricket/Everest_ISPL_Auction_2024/Scenes/TOP_15_Buys.sum' ;
			break;
		case 'UTT':
			valueToProcess = 'D:/DOAD_In_House_Everest/Everest_Sports/Everest_UTT_Auction_2025/Scenes/TOP_15_Buys.sum' ;
			break;		
		}
		break;
	case 'POPULATE-REMAINING_PURSE_ALL':
		switch ($('#selected_broadcaster').val().toUpperCase()) {
		case 'HANDBALL':
			valueToProcess = 'D:/DOAD_In_House_Everest/Everest_Sports/Everest_Handball_Auction_2023/Scenes/RemainingPurse_All.sum' ;
			break;
		case 'UTT':
			valueToProcess = 'D:/DOAD_In_House_Everest/Everest_Sports/Everest_UTT_Auction_2025/Scenes/RemainingPurse_All.sum' ;
			break;	
		}
		break;
	case 'POPULATE-RTM_SQUAD':
		switch ($('#selected_broadcaster').val().toUpperCase()) {
		case 'HANDBALL':
			valueToProcess = 'D:/DOAD_In_House_Everest/Everest_Sports/Everest_Handball_Auction_2023/Scenes/RemainingPurse_All.sum' ;
			break;
		case 'ISPL':
			valueToProcess = 'D:/DOAD_In_House_Everest/Everest_Cricket/Everest_ISPL_Auction_2024/Scenes/RemainingPurse_All02.sum' ;
			break;
		case 'UTT':
			valueToProcess = 'D:/DOAD_In_House_Everest/Everest_Sports/Everest_UTT_Auction_2025/Scenes/RemainingPurse_All02.sum' ;
			break;		
		}
		break;
	case 'POPULATE-RTM_AVAILABLE':
		switch ($('#selected_broadcaster').val().toUpperCase()) {
		case 'HANDBALL':
			valueToProcess = 'D:/DOAD_In_House_Everest/Everest_Sports/Everest_Handball_Auction_2023/Scenes/RemainingPurse_All.sum' ;
			break;
		case 'ISPL':
			valueToProcess = 'D:/DOAD_In_House_Everest/Everest_Cricket/Everest_ISPL_Auction_2024/Scenes/RemainingPurse_All.sum' ;
			break;	
		case 'UTT':
			valueToProcess = 'D:/DOAD_In_House_Everest/Everest_Sports/Everest_UTT_Auction_2025/Scenes/RemainingPurse_All.sum' ;
			break;	
		}
		break;
	case 'POPULATE-SLOTS_REMAINING':
		switch ($('#selected_broadcaster').val().toUpperCase()) {
		case 'HANDBALL':
			valueToProcess = 'D:/DOAD_In_House_Everest/Everest_Sports/Everest_Handball_Auction_2023/Scenes/RemainingPurse_All.sum' ;
			break;
		case 'ISPL':
			valueToProcess = 'D:/DOAD_In_House_Everest/Everest_Cricket/Everest_ISPL_Auction_2024/Scenes/RemainingPurse_All.sum' ;
			break;	
		case 'UTT':
			valueToProcess = 'D:/DOAD_In_House_Everest/Everest_Sports/Everest_UTT_Auction_2025/Scenes/RemainingPurse_All.sum' ;
			break;	
		}
		break;	
	case 'POPULATE-FF_IDENT':
		switch ($('#selected_broadcaster').val().toUpperCase()) {
		case 'ISPL':
			valueToProcess = 'D:/DOAD_In_House_Everest/Everest_Cricket/Everest_ISPL_Auction_2024/Scenes/Auction_Ident.sum' ;
			break;
		case 'UTT':
			valueToProcess = 'D:/DOAD_In_House_Everest/Everest_Sports/Everest_UTT_Auction_2025/Scenes/Auction_Ident.sum' ;
			break;		
		}
		break;
	case 'POPULATE-FF_ICONIC_PLAYERS':
		switch ($('#selected_broadcaster').val().toUpperCase()) {
		case 'ISPL':
			valueToProcess = 'D:/DOAD_In_House_Everest/Everest_Cricket/Everest_ISPL_Auction_2024/Scenes/Iconic_Players.sum' ;
			break;
		case 'UTT':
			valueToProcess = 'D:/DOAD_In_House_Everest/Everest_Sports/Everest_UTT_Auction_2025/Scenes/Iconic_Players.sum' ;
			break;		
		}
		break;
	case 'GET-CONFIG-DATA':
		valueToProcess = $('#select_configuration_file option:selected').val();
		break;
	
	}

	$.ajax({    
        type : 'Get',     
        url : 'processAuctionProcedures.html',     
        data : 'whatToProcess=' + whatToProcess + '&valueToProcess=' + valueToProcess, 
        dataType : 'json',
        success : function(data) {
			//match_data = data;
			
        	switch(whatToProcess) {
			case 'READ-MATCH-AND-POPULATE': case 'RE_READ_DATA':
			
				session_auction = data;
				if(data){
					initialiseForm("UPDATE-MATCH-ON-OUTPUT-FORM",data);
					//alert("match = " + $('#matchFileTimeStamp').val() + "Data = "+ data.matchFileTimeStamp)
					/*if($('#matchFileTimeStamp').val() != data.matchFileTimeStamp) {
						document.getElementById('matchFileTimeStamp').value = data.matchFileTimeStamp;
						initialiseForm("UPDATE-MATCH-ON-OUTPUT-FORM",data);
						//match_data = data;
					}*/
				}
				if(whatToProcess == 'RE_READ_DATA'){
					alert('Data is Loaded');
				}
				break;
			case 'GET-CONFIG-DATA':
				initialiseForm('UPDATE-CONFIG',data);
				break;
			case 'NAMESUPER_GRAPHICS-OPTIONS':
				addItemsToList('NAMESUPER-OPTIONS',data);
				match_data = data;
				break;
			case 'NAMESUPER_PLAYER_GRAPHICS-OPTIONS':
				addItemsToList('NAMESUPER_PLAYER-OPTIONS',data);
				addItemsToList('POPULATE-PLAYER',data);
				match_data = data;
				break;
			case 'PLAYERPROFILE_GRAPHICS-OPTIONS':
				addItemsToList('PLAYERPROFILE-OPTIONS',data);
				addItemsToList('POPULATE-PROFILE',data);
				match_data = data;
				break;
			case 'ONLY_SQUAD_GRAPHICS-OPTIONS':
				addItemsToList('ONLY_SQUAD-OPTIONS',data);
				addItemsToList('POPULATE-TEAM-ONLY_SQUAD',data);
				match_data = data;
				break;
			case 'SQUAD_GRAPHICS-OPTIONS': 
				addItemsToList('SQUAD-OPTIONS',data);
				addItemsToList('POPULATE-TEAM-SQUAD',data);
				match_data = data;
				break;
			case 'SQUAD-ROLE-COUNT_GRAPHICS-OPTIONS':
				addItemsToList('SQUAD-ROLE-COUNT-OPTIONS',data);
				addItemsToList('POPULATE-TEAM',data);
				match_data = data;
				break;
			case 'SINGLE_PURSE_GRAPHICS-OPTIONS':
				addItemsToList('SINGLE_PURSE-OPTIONS',data);
				addItemsToList('POPULATE-TEAM',data);
				match_data = data;
				break;
			case 'TOP_SOLD_TEAMS_GRAPHICS-OPTIONS':
				addItemsToList('TOP_SOLD-OPTIONS',data);
				addItemsToList('POPULATE-TEAM',data);
				match_data = data;
				break;
			case 'TOP_15_SOLD_TEAMS_GRAPHICS-OPTIONS':
				addItemsToList('TOP_15_SOLD-OPTIONS',data);
				addItemsToList('POPULATE-TEAM',data);
				match_data = data;
				break;
			case 'TOP_FIVE_SOLD_TEAMS_GRAPHICS-OPTIONS':
				addItemsToList('TOP_FIVE_SOLD-OPTIONS',data);
				addItemsToList('POPULATE-TEAM',data);
				match_data = data;
				break;
			case 'POPULATE-SQUAD': 
					if (which_GFX == 'POPULATE-SQUAD') {
						processAuctionProcedures('ANIMATE-IN-SQUAD');
					}else{
					  if(confirm('Animate In?') == true){
						$('#select_graphic_options_div').empty();
						document.getElementById('select_graphic_options_div').style.display = 'none';
						$("#captions_div").show();
						processAuctionProcedures('ANIMATE-IN-SQUAD');
						which_GFX = 'POPULATE-SQUAD';
						startTeamRotation(); 
					}
				}
				break;
			case 'TOP_15_AUCTION_CHANGEON':
				if(data.message){
						alert(data.message);
				}
				break;
			case 'POPULATE-FF-PLAYERPROFILE': case 'POPULATE-REMAINING_PURSE_ALL': case 'POPULATE-SINGLE_PURSE': case 'POPULATE-TOP_SOLD':
			case 'POPULATE-CRAWL': case 'POPULATE-SQUAD_ROLE': case 'POPULATE-FF_IDENT': case 'POPULATE-RTM_AVAILABLE': case 'POPULATE-ONLY_SQUAD':
			case 'POPULATE-SLOTS_REMAINING':case 'POPULATE-FF_ICONIC_PLAYERS': case 'POPULATE-RTM_SQUAD': case 'POPULATE-TOP_SOLD_TEAMS':
			case 'POPULATE-TOP_FIVE_SOLD': case 'POPULATE-TOP_FIVE_SOLD_TEAMS': case 'POPULATE-TOP_15_SOLD': case 'POPULATE-TOP_15_SOLD_TEAMS':
				if(confirm('Animate In?') == true){
					$('#select_graphic_options_div').empty();
					document.getElementById('select_graphic_options_div').style.display = 'none';
					$("#captions_div").show();
					
		        	switch(whatToProcess) {
					case 'POPULATE-RTM_SQUAD':
						processAuctionProcedures('ANIMATE-IN-RTM_SQUAD');	
						break;
					case 'POPULATE-ONLY_SQUAD':
						processAuctionProcedures('ANIMATE-IN-ONLY_SQUAD');	
						break;
					case 'POPULATE-SQUAD_ROLE':
						processAuctionProcedures('ANIMATE-IN-SQUAD_ROLE');	
						break;
					case 'POPULATE-CRAWL':
						processAuctionProcedures('ANIMATE-IN-CRAWL');				
						break;
					case 'POPULATE-TOP_SOLD_TEAMS':
						processAuctionProcedures('ANIMATE-IN-TOP_SOLD_TEAMS');				
						break;
					case 'POPULATE-TOP_15_SOLD_TEAMS':
						processAuctionProcedures('ANIMATE-IN-TOP_15_SOLD_TEAMS');				
						break;
					case 'POPULATE-TOP_FIVE_SOLD_TEAMS':
						processAuctionProcedures('ANIMATE-IN-TOP_FIVE_SOLD_TEAMS');				
						break;
					case 'POPULATE-TOP_SOLD':
						processAuctionProcedures('ANIMATE-IN-TOP_SOLD');				
						break;
					case 'POPULATE-TOP_15_SOLD':
						processAuctionProcedures('ANIMATE-IN-TOP_15_SOLD');				
						break;
					case 'POPULATE-TOP_FIVE_SOLD':
						processAuctionProcedures('ANIMATE-IN-TOP_FIVE_SOLD');				
						break;
					case 'POPULATE-REMAINING_PURSE_ALL':
						processAuctionProcedures('ANIMATE-IN-REMAINING_PURSE_ALL');				
						break;
					case 'POPULATE-RTM_AVAILABLE':
						processAuctionProcedures('ANIMATE-IN-RTM');				
						break;
					case 'POPULATE-SLOTS_REMAINING':
						processAuctionProcedures('ANIMATE-IN-SLOTS');				
						break;	
					case 'POPULATE-FF-PLAYERPROFILE':
						processAuctionProcedures('ANIMATE-IN-PLAYERPROFILE');				
						break;
					case 'POPULATE-SINGLE_PURSE':
						processAuctionProcedures('ANIMATE-IN-SINGLE_PURSE');				
						break;
					case 'POPULATE-FF_IDENT':
						processAuctionProcedures('ANIMATE-IN-FF_IDENT');				
						break;
					case 'POPULATE-FF_ICONIC_PLAYERS':
						processAuctionProcedures('ANIMATE-IN-FF_ICONIC_PLAYERS');
						break;
					}
				}
				break;
        	}
			processWaitingButtonSpinner('END_WAIT_TIMER');
	    },    
	    error : function(e) {    
	  	 	console.log('Error occured in ' + whatToProcess + ' with error description = ' + e);     
	    }    
	});
}
function addItemsToList(whatToProcess, dataToProcess)
{
	var select,option,header_text,div,table,tbody,row,max_cols;
	var cellCount = 0;

	switch (whatToProcess) {
	case 'POPULATE-TEAM':
		$('#selectTeamName').empty();
		
		dataToProcess.forEach(function(tm,index,arr1){
			$('#selectTeamName').append(
					$(document.createElement('option')).prop({
					value: tm.teamId,
					text: tm.teamName1
				}))
		});
		
		break;
	case 'POPULATE-TEAM-ONLY_SQUAD':
		  $('#selectTeamName').empty();

            // Store the team data globally
            teams = dataToProcess.map(function(tm) {
                return { teamId: tm.teamId, teamName: tm.teamName1 };
            });

            // Populate the dropdown with teams
            dataToProcess.forEach(function(tm) {
                $('#selectTeamName').append(
                    $(document.createElement('option')).prop({
                        value: tm.teamId,
                        text: tm.teamName1
                    })
                );
            });
		break;	
	case 'POPULATE-TEAM-SQUAD':
		  $('#selectTeamName').empty();

            // Store the team data globally
            teams = dataToProcess.map(function(tm) {
                return { teamId: tm.teamId, teamName: tm.teamName1 };
            });

            // Populate the dropdown with teams
            dataToProcess.forEach(function(tm) {
                $('#selectTeamName').append(
                    $(document.createElement('option')).prop({
                        value: tm.teamId,
                        text: tm.teamName1
                    })
                );
            });
			$('#selectTeamName').on('change', function() {
			    // Find the selected team index based on the dropdown selection
			    currentTeamIndex = $('#selectTeamName').prop('selectedIndex');
			});
		break;
	case 'POPULATE-PROFILE' :

		$('#selectPlayerName').empty();
		
		session_auction.playersList.forEach(function(plyr,index,arr1){
			if(plyr.playerId == session_auction.players[session_auction.players.length- 1].playerId){
				$('#selectPlayerName').append(
					$(document.createElement('option')).prop({
					value: plyr.playerId,
					text: plyr.playerNumber + ' - ' + plyr.full_name + ' - ' + plyr.category + ' - ' + plyr.role
				}))
			}
		});
		
		
		dataToProcess.forEach(function(plyr,index,arr1){
			if(plyr.playerId != session_auction.players[session_auction.players.length - 1].playerId){
				$('#selectPlayerName').append(
					$(document.createElement('option')).prop({
					value: plyr.playerId,
					text: plyr.playerNumber + ' - ' + plyr.full_name + ' - ' + plyr.category + ' - ' + plyr.role
				}))
			}
		});
		
		break;
		
	case 'POPULATE-PLAYER':
		$('#selectPlayer').empty();
		if(dataToProcess.homeTeamId ==  $('#selectTeam option:selected').val()){
			dataToProcess.homeSquad.forEach(function(hs,index,arr){
				$('#selectPlayer').append(
					$(document.createElement('option')).prop({
	                value: hs.playerId,
	                text: hs.full_name
		        }))					
			});
		}
		else {
			dataToProcess.awaySquad.forEach(function(as,index,arr){
				$('#selectPlayer').append(
					$(document.createElement('option')).prop({
	                value: as.playerId,
	                text: as.full_name
		        }))					
			});
		}
		
		break;
	case 'CRAWLER-OPTIONS':
		switch ($('#selected_broadcaster').val().toUpperCase()) {
		case 'ISPL': case 'UTT':
			$('#select_graphic_options_div').empty();
	
			header_text = document.createElement('h6');
			header_text.innerHTML = 'Select Graphic Options';
			document.getElementById('select_graphic_options_div').appendChild(header_text);
			
			table = document.createElement('table');
			table.setAttribute('class', 'table table-bordered');
					
			tbody = document.createElement('tbody');
	
			table.appendChild(tbody);
			document.getElementById('select_graphic_options_div').appendChild(table);
			
			row = tbody.insertRow(tbody.rows.length);
			
			select = document.createElement('select');
			select.style = 'width:100px';
			select.id = 'selectCrawl1';
			select.name = select.id;
			
			option = document.createElement('option');
			option.value = 'Sold';
			option.text = 'Sold';
			select.appendChild(option);
			
			option = document.createElement('option');
			option.value = 'Purse';
			option.text = 'Remaining Purse';
			select.appendChild(option);
			
			select.setAttribute('onchange',"processUserSelection(this)");
			row.insertCell(cellCount).appendChild(select);
			cellCount = cellCount + 1;
			
			select = document.createElement('select');
			select.style = 'width:100px';
			select.id = 'selectCrawl2';
			select.name = select.id;
			
			option = document.createElement('option');
			option.value = 'notSelected';
			option.text = '';
			select.appendChild(option);
			
			option = document.createElement('option');
			option.value = 'Purse';
			option.text = 'Remaining Purse';
			select.appendChild(option);
			
			option = document.createElement('option');
			option.value = 'Sold';
			option.text = 'Sold';
			select.appendChild(option);
			
			select.setAttribute('onchange',"processUserSelection(this)");
			row.insertCell(cellCount).appendChild(select);
			cellCount = cellCount + 1;
			
			option = document.createElement('input');
		    option.type = 'button';
			option.name = 'populate_crawl_btn';
		    option.value = 'Populate Crawl';
		    option.id = option.name;
		    option.setAttribute('onclick',"processUserSelection(this)");
		    
		    div = document.createElement('div');
		    div.append(option);

			option = document.createElement('input');
			option.type = 'button';
			option.name = 'cancel_graphics_btn';
			option.id = option.name;
			option.value = 'Cancel';
			option.setAttribute('onclick','processUserSelection(this)');
	
		    div.append(option);
		    
		    row.insertCell(cellCount).appendChild(div);
		    cellCount = cellCount + 1;
		    
			document.getElementById('select_graphic_options_div').style.display = '';
			break;
		}
		break;	
	case'NAMESUPER-OPTIONS': case 'NAMESUPER_PLAYER-OPTIONS':  case'PLAYERPROFILE-OPTIONS': case 'SQUAD-OPTIONS': case 'SINGLE_PURSE-OPTIONS':
	case 'SQUAD-ROLE-COUNT-OPTIONS': case 'ONLY_SQUAD-OPTIONS': case 'TOP_SOLD-OPTIONS': case 'TOP_FIVE_SOLD-OPTIONS':
	case 'TOP_15_SOLD-OPTIONS':
		switch ($('#selected_broadcaster').val().toUpperCase()) {
		case 'HANDBALL': case 'ISPL': case 'UTT':

			$('#select_graphic_options_div').empty();
	
			header_text = document.createElement('h6');
			header_text.innerHTML = 'Select Graphic Options';
			document.getElementById('select_graphic_options_div').appendChild(header_text);
			
			table = document.createElement('table');
			table.setAttribute('class', 'table table-bordered');
					
			tbody = document.createElement('tbody');
	
			table.appendChild(tbody);
			document.getElementById('select_graphic_options_div').appendChild(table);
			
			row = tbody.insertRow(tbody.rows.length);
			
			switch(whatToProcess){
				case 'SINGLE_PURSE-OPTIONS': case 'TOP_SOLD-OPTIONS': case 'TOP_FIVE_SOLD-OPTIONS': case 'TOP_15_SOLD-OPTIONS':
				switch ($('#selected_broadcaster').val().toUpperCase()) {
					case 'HANDBALL': case 'ISPL': case 'UTT':
						select = document.createElement('select');
						select.id = 'selectTeamName';
						select.name = select.id;
						
						select.setAttribute('onchange',"processUserSelection(this)");
						row.insertCell(cellCount).appendChild(select);
						cellCount = cellCount + 1;
						
						break;
					}
					break;
				case 'SQUAD-OPTIONS': 	case 'SQUAD-ROLE-COUNT-OPTIONS': case 'ONLY_SQUAD-OPTIONS':
					switch ($('#selected_broadcaster').val().toUpperCase()) {
						case 'HANDBALL': case 'ISPL': case 'UTT':
							select = document.createElement('select');
							select.id = 'selectTeamName';
							select.name = select.id;
							
							select.setAttribute('onchange',"processUserSelection(this)");
							row.insertCell(cellCount).appendChild(select);
							cellCount = cellCount + 1;
							
							break;
						} 
						break;
				case'NAMESUPER-OPTIONS':
					switch ($('#selected_broadcaster').val().toUpperCase()) {
					case 'DOAD_IN_HOUSE_EVEREST': case 'DOAD_IN_HOUSE_VIZ':
						select = document.createElement('select');
						select.style = 'width:130px';
						select.id = 'selectNameSuper';
						select.name = select.id;
						
						dataToProcess.forEach(function(ns,index,arr1){
							option = document.createElement('option');
							option.value = ns.namesuperId;
							option.text = ns.subHeader ;
							select.appendChild(option);
						});
						
						row.insertCell(cellCount).appendChild(select);
						cellCount = cellCount + 1;
						
						switch ($('#selected_broadcaster').val().toUpperCase()) {
						case 'DOAD_IN_HOUSE_EVEREST': 
							select = document.createElement('input');
							select.type = "text";
							select.id = 'namesuperScene';
							select.value = 'D:/DOAD_In_House_Everest/Everest_Cricket/EVEREST_APL2022/Scenes/LT_NameSuper.sum';
							
							row.insertCell(cellCount).appendChild(select);
							cellCount = cellCount + 1;
							
							break;
						}
						break;
					}
					break;
				
			case 'NAMESUPER_PLAYER-OPTIONS':
				switch ($('#selected_broadcaster').val().toUpperCase()) {
					case 'DOAD_IN_HOUSE_EVEREST': case 'DOAD_IN_HOUSE_VIZ':
						select = document.createElement('select');
						select.id = 'selectTeam';
						select.name = select.id;
						
						option = document.createElement('option');
						option.value = dataToProcess.homeTeamId;
						option.text = dataToProcess.homeTeam.shortname;
						select.appendChild(option);
						
						option = document.createElement('option');
						option.value = dataToProcess.awayTeamId;
						option.text = dataToProcess.awayTeam.shortname;
						select.appendChild(option);
					
						select.setAttribute('onchange',"processUserSelection(this)");
						row.insertCell(cellCount).appendChild(select);
						cellCount = cellCount + 1;
		
						select = document.createElement('select');
						select.style = 'width:100px';
						select.id = 'selectCaptainWicketKeeper';
						select.name = select.id;
						
						option = document.createElement('option');
						option.value = 'Captain';
						option.text = 'Captain';
						select.appendChild(option);
						
						select.setAttribute('onchange',"processUserSelection(this)");
						row.insertCell(cellCount).appendChild(select);
						cellCount = cellCount + 1;
						
						select = document.createElement('select');
						select.style = 'width:100px';
						select.id = 'selectPlayer';
						select.name = select.id;
						
						row.insertCell(cellCount).appendChild(select);
						cellCount = cellCount + 1;
						
						switch ($('#selected_broadcaster').val().toUpperCase()) {
							case 'DOAD_IN_HOUSE_EVEREST': 
								select = document.createElement('input');
								select.type = "text";
								select.id = 'namesuperplayerScene';
								select.value = 'D:/DOAD_In_House_Everest/Everest_Cricket/EVEREST_APL2022/Scenes/LT_NameSuper.sum';
								
								row.insertCell(cellCount).appendChild(select);
								cellCount = cellCount + 1;
								
								break;
						}
						break;
				}
				break;
			case'PLAYERPROFILE-OPTIONS':
				switch ($('#selected_broadcaster').val().toUpperCase()) {
					case 'HANDBALL': case 'ISPL': case 'UTT':
						select = document.createElement('select');
						select.id = 'selectPlayerName';
						select.name = select.id;
						
						select.setAttribute('onchange',"processUserSelection(this)");
						row.insertCell(cellCount).appendChild(select);
						//document.getElementById('extra_log_event_row_1').insertCell(0).appendChild(header_text).appendChild(select);	
						$(select).select2();
						cellCount = cellCount + 1;
						
						break;
				} 
				break;
				}
			
			option = document.createElement('input');
		    option.type = 'button';
			switch (whatToProcess) {
			
			case'NAMESUPER-OPTIONS':
			    option.name = 'populate_namesuper_btn';
			    option.value = 'Populate Namesuper';
				break;
			case 'NAMESUPER_PLAYER-OPTIONS':	
				option.name = 'populate_namesuper_player_btn';
			    option.value = 'Populate Namesuper-Player';
				break;
			
			case'PLAYERPROFILE-OPTIONS':
			    option.name = 'populate_playerprofile_btn';
			    option.value = 'Populate Playerprofile';
				break;
			case 'ONLY_SQUAD-OPTIONS':
				option.name = 'populate_only_squad_btn';
			    option.value = 'Populate Squad';
				break;
			case'SQUAD-OPTIONS':
			    option.name = 'populate_squad_btn';
			    option.value = 'Populate Squad';
				break;
			case 'SQUAD-ROLE-COUNT-OPTIONS':
				option.name = 'populate_squad_role_btn';
			    option.value = 'Populate Squad role';
				break;
			case 'SINGLE_PURSE-OPTIONS':
				option.name = 'populate_single_purse_btn';
			    option.value = 'Populate Single Purse';
				break;
			case 'TOP_SOLD-OPTIONS':
				option.name = 'populate_top_sold_teams_btn';
			    option.value = 'Populate Top Sold';
				break;
			case 'TOP_15_SOLD-OPTIONS':
				option.name = 'populate_top_15_sold_teams_btn';
			    option.value = 'Populate Top 15 Sold';
				break;
			case 'TOP_FIVE_SOLD-OPTIONS':
				option.name = 'populate_top_five_sold_teams_btn';
			    option.value = 'Populate Top 5 Sold';
				break;
			
			}
		    option.id = option.name;
		    option.setAttribute('onclick',"processUserSelection(this)");
		    
		    div = document.createElement('div');
		    div.append(option);

			option = document.createElement('input');
			option.type = 'button';
			option.name = 'cancel_graphics_btn';
			option.id = option.name;
			option.value = 'Cancel';
			option.setAttribute('onclick','processUserSelection(this)');
	
		    div.append(option);
		    
		    row.insertCell(cellCount).appendChild(div);
		    cellCount = cellCount + 1;
		    
			document.getElementById('select_graphic_options_div').style.display = '';

			break;
		}
		break;
	}
	
	
}
function checkEmpty(inputBox,textToShow) {

	var name = $(inputBox).attr('id');
	
	document.getElementById(name + '-validation').innerHTML = '';
	document.getElementById(name + '-validation').style.display = 'none';
	$(inputBox).css('border','');
	if(document.getElementById(name).value.trim() == '') {
		$(inputBox).css('border','#E11E26 2px solid');
		document.getElementById(name + '-validation').innerHTML = textToShow + ' required';
		document.getElementById(name + '-validation').style.display = '';
		document.getElementById(name).focus({preventScroll:false});
		return false;
	}
	return true;	
}