jQuery(document).ready(function(){ 
  $('#avatar-contain').click(function(){
        $('#popup-menu').animate({"height":"toggle"},
                                500,
                                'linear',
                                function(){})
  })
});