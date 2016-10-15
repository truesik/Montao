jQuery(document).ready(function(){ 
  $('#avatar-contain').click(function(){
        $( "#avatar-contain i" ).toggleClass( "fa-chevron-circle-down" );
        $( "#avatar-contain i" ).toggleClass( "fa-chevron-circle-up" );
        $('#popup-menu').animate({"height":"toggle"},
                                500,
                                'linear',
                                function(){})
  })
});