$(function(){ 
    var isClicked =false;
    $('#mobile-search-button').click(function(){
        if(isClicked == false){
            isClicked = true;
            $(".logo , #log-in-button").css('display','none');
            $("#mobile-search").css('display','block');
            $("#mobile-search").focus();
            //Прописал смену класса тега i в #mobile-search-button в самом теге , т.е. в index.html
        }else{
            isClicked = false;
            $(".logo , #log-in-button").css('display','inline-block');
            $("#mobile-search").css('display','none');
        }
    });
});

