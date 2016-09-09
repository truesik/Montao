$(function(){ 
  $('#log-in-button').click(function(){
     // $('#cover').css('display','block');
	 // $('#cover').show('slow');
	 // $('#cover').fadeIn('slow');
	 $('#cover').slideDown('slow');
     $('#popup-window').css('display','inherit');
     $('#create-account-window').css('display','none');
  });
  $('#exit-area').click(function(){
	 // $('#cover').css('display','none'); 
     // $('#cover').hide('slow');
	 // $('#cover').fadeOut('slow');
     
     $('#cover').slideUp('slow');
  });
  $('#call-create-account-window-button').click(function(){
    $('#popup-window').fadeOut('fast',function(){
        $('#create-account-window').fadeIn('fast');
    });
    });
});