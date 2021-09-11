let stompClient = null;

$(function () {

    $("#statuslabel").text("Connecting to weather Server...");

    //open websocket connection
    connect();

});


function connect() {
    let socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function () {
        //adjust status message
        $("#statuslabel").text("Connected to weather Server.");

        loadAvailableCities();
    });

    socket.onclose = function() {
        stompClient.disconnect();
        $("#statuslabel").text("Disconnected from weather server, refresh the page to reconnect.");
    };
}


function loadAvailableCities() {
    axios.get("/city")
        .then(response => renderAndSubscribeToCities(response.data))
        .catch(function (error) {
            $("#errorlabel").text("Error Loading cities !");
            setTimeout(function () {
                $("#errorlabel").text("");
            }, 5000);
        });
}

function renderAndSubscribeToCities(cities) {
    if (cities) {
        cities.forEach((city, i) => {
            subscribeToCityLiveWeather(city)
        });
    }
}

function subscribeToCityLiveWeather(city) {
    stompClient.subscribe("/topic/weather." + city, function (payload) {
        if (payload) {
            let body = JSON.parse(payload.body);
            let type = body.type;

            switch (type) {
                case "WEATHER_UPDATED":
                    handleWeatherUpdated(body.cityWeatherData);
                    break;
                case "WEATHER_UPDATE_ERROR":
                    handleWeatherUpdateError(body.cityName);
            }
        }
    });
}