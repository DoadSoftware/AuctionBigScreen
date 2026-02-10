<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>

  <meta charset="utf-8" name="viewport" content="width=device-width, initial-scale=1">
  <title>Output Screen</title>
	
  <script type="text/javascript" src="<c:url value="/webjars/jquery/3.6.0/jquery.min.js"/>"></script>
  <script type="text/javascript" src="<c:url value="/webjars/bootstrap/5.1.3/js/bootstrap.min.js"/>"></script>
  <script type="text/javascript" src="<c:url value="/resources/javascript/index.js"/>"></script>
  <script type="text/javascript" src="<c:url value="/webjars/select2/4.0.13/js/select2.js"/>"></script>
  
  <link rel="stylesheet" href="<c:url value="/webjars/select2/4.0.13/css/select2.css"/>"/>
  <link rel="stylesheet" href="<c:url value="/webjars/bootstrap/5.1.3/css/bootstrap.min.css"/>"/>  
  <link href="<c:url value="/webjars/font-awesome/6.0.0/css/all.css"/>" rel="stylesheet">
  <script type="text/javascript">
	$(document).on("keydown", function(e){
	  
	  if($('#waiting_modal').hasClass('show')) {
		  e.cancelBubble = true;
		  e.stopImmediatePropagation();
    	  e.preventDefault();
		  return false;
	  }
	  
      var evtobj = window.event? event : e;
      
      switch(e.target.tagName.toLowerCase())
      {
      case "input": case "textarea":
    	 break;
      default:
    	  e.preventDefault();
	      var whichKey = '';
		  var validKeyFound = false;
	    
	      if(evtobj.ctrlKey) {
	    	  whichKey = 'Control';
	      }
	      if(evtobj.altKey) {
	    	  if(whichKey) {
	        	  whichKey = whichKey + '_Alt';
	    	  } else {
	        	  whichKey = 'Alt';
	    	  }
	      }
	      if(evtobj.shiftKey) {
	    	  if(whichKey) {
	        	  whichKey = whichKey + '_Shift';
	    	  } else {
	        	  whichKey = 'Shift';
	    	  }
	      }
	      
		  if(evtobj.keyCode) {
	    	  if(whichKey) {
	    		  if(!whichKey.includes(evtobj.key)) {
	            	  whichKey = whichKey + '_' + evtobj.key;
	    		  }
	    	  } else {
	        	  whichKey = evtobj.key;
	    	  }
		  }
		  validKeyFound = false;
		  if (whichKey.includes('_')) {
			  whichKey.split("_").forEach(function (this_key) {
				  switch (this_key) {
				  case 'Control': case 'Shift': case 'Alt':
					break;
				  default:
					validKeyFound = true;
					break;
				  }
			  });
		   } else {
			  if(whichKey != 'Control' && whichKey != 'Alt' && whichKey != 'Shift') {
				  validKeyFound = true;
			  }
		   }
			  
		   if(validKeyFound == true) {
			   console.log('whichKey = ' + whichKey);
			   processUserSelectionData('LOGGER_FORM_KEYPRESS',whichKey);
		   }
	      }
	  });
 	setInterval(() => {
 		processAuctionProcedures('READ-MATCH-AND-POPULATE');		
	}, 1000);
  </script>
</head>
<body>
<form:form name="output_form" autocomplete="off" action="POST">
<div class="content py-5" style="background-color: #EAE8FF; color: #2E008B">
  <div class="container">
	<div class="row">
	 <div class="col-md-8 offset-md-2">
       <span class="anchor"></span>
         <div class="card card-outline-secondary">
           <div class="card-header">
             <h3 class="mb-0">Output</h3>
            <!--   <h3 class="mb-0">${licence_expiry_message}</h3>  -->
           </div>
          <div class="card-body">
          
			  <div id="select_graphic_options_div" style="display:none;">
			  </div>
			  <div id="captions_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
			  	<!--  <label class="col-sm-4 col-form-label text-left">${licence_expiry_message} </label> 
			    <label class="col-sm-4 col-form-label text-left">Match: ${session_match.matchFileName} </label> -->
			    <label class="col-sm-4 col-form-label text-left">IP Address: ${session_selected_ip} </label>
			    <label class="col-sm-4 col-form-label text-left">Port Number: ${session_port} </label>
			    <label class="col-sm-4 col-form-label text-left">Broadcaster: ${session_selected_broadcaster} </label>
			    
			    <div class="left">
			  	<button style="background-color:#f44336;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="animateout_graphic_btn" id="animateout_graphic_btn" onclick="processUserSelection(this)"> AnimateOut (-) </button>
			  	<button style="background-color:#f44336;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="clearall_graphic_btn" id="clearall_graphic_btn" onclick="processUserSelection(this)"> Clear All (Space)</button>
			  	</div>
			    <label class="col-sm-4 col-form-label text-left"> </label>
				<div class="left">
			    <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="playerprofile_graphic_btn" id="playerprofile_graphic_btn" onclick="processUserSelection(this)"> PlayerProfile (F1) </button>
			  		
			  	<!-- <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="playerprofile_change_on_graphic_btn" id="playerprofile_change_on_graphic_btn" onclick="processUserSelection(this)"> PlayerProfile Change ON </button>
			  	 -->
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="Ident_graphic_btn" id="Ident_graphic_btn" onclick="processUserSelection(this)"> Ident(F4) </button>
			  		
			  	<!-- <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
		  			name="rtm_available_graphic_btn" id="rtm_available_graphic_btn" onclick="processUserSelection(this)"> RTM Available </button>	
		  		
		  		<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="remaining_purse_single_graphic_btn" id="remaining_purse_single_graphic_btn" onclick="processUserSelection(this)"> Remaining Purse Single </button>	
			  		 -->
			  	<!-- <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="BG_Ident_graphic_btn" id="BG_Ident_graphic_btn" onclick="processUserSelection(this)"> BG </button> -->	
			  	 
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="squad_graphic_btn" id="squad_graphic_btn" onclick="processUserSelection(this)"> Squad Animation</button>	
			  		
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="Only_squad_graphic_btn" id="Only_squad_graphic_btn" onclick="processUserSelection(this)"> Squad</button>
			  	
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="remaining_purse_graphic_btn" id="remaining_purse_graphic_btn" onclick="processUserSelection(this)"> Remaining Purse All + Squad Size </button>		
			  	<!-- <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="player_category_graphic_btn" id="player_category_graphic_btn" onclick="processUserSelection(this)"> Player List Category India </button>
			  		
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="player_category_int_graphic_btn" id="player_category_int_graphic_btn" onclick="processUserSelection(this)"> Player List Category International </button>	
			  		 -->	
			  <!-- 	
			  		
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="cur_squad_graphic_btn" id="cur_squad_graphic_btn" onclick="processUserSelection(this)"> Remaining Purse All + Squad Size </button>	
			  		
			   -->
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="top_sold_graphic_btn" id="top_sold_graphic_btn" onclick="processUserSelection(this)"> Top 8 Sold Auction</button>
			  		
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="top_five_sold_graphic_btn" id="top_five_sold_graphic_btn" onclick="processUserSelection(this)"> Top 5 Sold Auction</button>
			  		
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="top_five_sold_teams_graphic_btn" id="top_five_sold_teams_graphic_btn" onclick="processUserSelection(this)"> Top 5 Sold Teams</button>	
			  		
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
		  			name="iconics_Player_graphic_btn" id="iconics_Player_graphic_btn" onclick="processUserSelection(this)"> Retain Player </button>	
			  		
			  	<!-- <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="top_sold_teams_graphic_btn" id="top_sold_teams_graphic_btn" onclick="processUserSelection(this)"> Top 6 Sold Teams</button> -->	
			  	
			  	<!-- 		
			  	
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
		  			name="slots_remzone_graphic_btn" id="slots_remzone_graphic_btn" onclick="processUserSelection(this)"> Zone Remaining </button>
			    
		  		<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
		  			name="retain_Player_graphic_btn" id="retain_Player_graphic_btn" onclick="processUserSelection(this)"> Four Retain Player </button>
		  			
		  		<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
		  			name="rtm_squad_graphic_btn" id="rtm_squad_graphic_btn" onclick="processUserSelection(this)"> RTM & SQUAD </button>
		  		<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
		  			name="lastyear_graphic_btn" id="lastyear_graphic_btn" onclick="processUserSelection(this)"> Last year </button>
			  	
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
		  			name="pointers_graphic_btn" id="pointers_graphic_btn" onclick="processUserSelection(this)"> Pointers 4 </button>
		  			
		  			<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
		  			name="pointerseight_graphic_btn" id="pointerseight_graphic_btn" onclick="processUserSelection(this)"> Pointers 8 </button> -->
			  	<!-- 
			  		
			  		 -->
			  			
			  	
		  		<!-- <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
		  			name="slots_rem_graphic_btn" id="slots_rem_graphic_btn" onclick="processUserSelection(this)"> Slots Remaining </button>	
			  	 -->
			  	<!-- <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
		  			name="iconic_Player_graphic_btn" id="iconic_Player_graphic_btn" onclick="processUserSelection(this)"> ICONIC PLAYERS </button> -->
		  			
		  		<!-- 	
		  		
		  		<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="four_teams_graphic_btn" id="four_teams_graphic_btn" onclick="processUserSelection(this)"> 4 Teams</button> -->
			  	<!-- 	
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="pool_graphic_btn" id="pool_graphic_btn" onclick="processUserSelection(this)"> Pool</button>	
			  		
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="pool_number_graphic_btn" id="pool_number_graphic_btn" onclick="processUserSelection(this)"> Pool Number</button> -->		
		  			
		  		<!-- <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="top_15_sold_graphic_btn" id="top_15_sold_graphic_btn" onclick="processUserSelection(this)"> Top 15 Sold Auction</button> -->
			  		
			  	<!-- <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="top_15_sold_teams_graphic_btn" id="top_15_sold_teams_graphic_btn" onclick="processUserSelection(this)"> Top 15 Sold Teams</button>		
			  	 -->
			  	<!-- <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="squad_with_role_count_graphic_btn" id="squad_with_role_count_graphic_btn" onclick="processUserSelection(this)"> Squad with role count </button>
			  		name="squad_graphic_btn" id="squad_graphic_btn" onclick="processUserSelection(this)"> Squad </button>-->
			  	</div>			  	
			  </div>
	       </div>
	    </div>
       </div>
    </div>
  </div>
</div>
<input type="hidden" id="which_keypress" name="which_keypress" value="${session_match.which_key_press}"/>
<input type="hidden" name="selected_broadcaster" id="selected_broadcaster" value="${session_selected_broadcaster}"/>
<input type="hidden" name="selected_which_layer" id="selected_which_layer" value="${selected_layer}"></input>
</form:form>
</body>
</html>