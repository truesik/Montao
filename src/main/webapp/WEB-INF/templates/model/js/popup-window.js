$(function(){ 
  $('#log-in-button').click(function(){
     // $('#cover').css('display','block');
	 // $('#cover').show('slow');
	 // $('#cover').fadeIn('slow');
	 $('#cover').slideDown('slow');
  });
  $('#exit-area').click(function(){
	 // $('#cover').css('display','none'); 
     // $('#cover').hide('slow');
	 // $('#cover').fadeOut('slow');
     $('#cover').slideUp('slow');
  });
  $('#create-account-button').click(function(){
    $('#popup-window').fadeOut('fast');
      
    });
});