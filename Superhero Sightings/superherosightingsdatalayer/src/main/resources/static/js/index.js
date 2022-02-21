$(document).ready(function(){
    var map;
    toggleSpeechBalloon();
    cycleSpeechBalloon();
    initMap();
    //loadSightingsIntoMarkers();
});

function toggleSpeechBalloon(){
    $(".nes-octocat").click(function(){
       $("#balloon1").toggle(); 
       $("#balloon2").hide();
        $("#balloon3").hide();
    });
}

function cycleSpeechBalloon(){
    
    $("#balloon1").show(); 
    $("#balloon2").hide();
    $("#balloon3").hide(); 
     
    $("#balloon1").click(function(){
       $("#balloon1").hide(); 
       $("#balloon2").show();
    });
    
    $("#balloon2").click(function(){
       $("#balloon2").hide(); 
       $("#balloon3").show();
    });
    
    $("#balloon3").click(function(){
       $("#balloon3").hide();
    });
}

function initMap(){
    
    //map options
    var options = {
        zoom:3,
        center: {lat:40.7128, lng:-74.0060}
    };
    
    //new map
    map = new google.maps.Map(document.getElementById('map'), options);
    
    var sightings = list;
    
    sightings.forEach(sighting => {
        
        var latitude = sighting.location.latitude;
        var longitude = sighting.location.longitude;
        var name = $("#sightingHero").val();
        var locationName = sighting.location.name;
        var sightingDate = sighting.sightingDate;
        
        var props = {
                    coords:{lat: latitude, lng: longitude},
                    content:name + "<br>" + locationName + "<br>" + sightingDate
                };
    
    
        addMarker(props);
    });
    
    
    /**
    var markers = [
        {
            coords:{lat:40.7128, lng:-74.0060},
            iconImage:'',
            content:'<h1>Hero</h1>'
        },
        {
            coords:{lat:42.8584, lng:-70.9300}
        }
    ];
    */
    //loop through markers
    /*
    for(var i =0; i < markers.length; i++){
        addMarker(markers[i]);
    }
    
    addMarker({coords:{lat:40.7128, lng:-74.0060},
        iconImage:'',
        content:'<h1>Hero</h1>'
    });
    addMarker({lat:42.8584, lng:-70.9300});
    
    //Add marker Function
    function addMarker(props){
            var marker = new google.maps.Marker({
            position:props.coords,
            map:map
        });
        //check for icon
        if(props.iconImage){
            //set icon
            marker.setIcon(props.iconImage);
        }
        
        //check content
        if(props.content){
            var infoWindow = new google.maps.InfoWindow({
                content:props.content
            });
            
            marker.addListener('click', function(){
                infoWindow.open(map, marker);
            });
        }
    }
    **/
    
}
/**

*/
function addMarker(props){
        var marker = new google.maps.Marker({
        position:props.coords,
        map:map
    });
    //check for icon
    if(props.iconImage){
        //set icon
        marker.setIcon(props.iconImage);
    }

    //check content
    if(props.content){
        var infoWindow = new google.maps.InfoWindow({
            content:props.content
        });

        marker.addListener('click', function(){
            infoWindow.open(map, marker);
        });
    }
}