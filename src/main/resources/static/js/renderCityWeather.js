function showCityError(city) {
    if ($("#".concat(city)).length) {
        $("#".concat(city)).find('.city-name').css("color","red")
    }
}

function renderCityWeather(cityWeatherData) {

    let cityId = cityWeatherData.name

    if ($("#inputCityName").val() && !cityId.includes($("#inputCityName").val())) {
        return
    }

    let cityTile = null;

    if ($("#".concat(cityId)).length) {
        cityTile = $("#".concat(cityId))
    } else {
        cityTile = createNewTile(cityId)
        $("#weather-content").append(cityTile)
    }

    cityTile.find('.city-name')[0].innerHTML = cityId
    cityTile.find('.city-name').css("color","black")
    cityTile.find('.lupdated-v')[0].innerHTML = formatLastUpdated(cityWeatherData.dt)
    cityTile.find('.weather-desc')[0].innerHTML = cityWeatherData.weather[0].description
    cityTile.find('.weather-image')[0].src = "http://openweathermap.org/img/wn/" + cityWeatherData.weather[0].icon + "@2x.png"
    if (cityWeatherData.main) {
        cityTile.find('.tempreature')[0].innerHTML = cityWeatherData.main.temp_max + " / " + cityWeatherData.main.temp_min + " °C"
        cityTile.find('.rfeel-v')[0].innerHTML = cityWeatherData.main.feels_like + " °C"
        cityTile.find('.pressure-v')[0].innerHTML = cityWeatherData.main.pressure + "hpa"
        cityTile.find('.humidity-v')[0].innerHTML = cityWeatherData.main.humidity + "%"
    }
    if (cityWeatherData.wind) {
        cityTile.find('.wind-v')[0].innerHTML = cityWeatherData.wind.speed + " m/s"
    }
    if (cityWeatherData.clouds) {
        cityTile.find('.clouds-v')[0].innerHTML = cityWeatherData.clouds.all + "%"
    }
}

function createNewTile(elId) {
    return $($.parseHTML(
        '<div id="' + elId + '" class="col-lg-2 col-sm-4 weather-tile"> ' +
        '<div class="row tile-content"> ' +
        '<div class="col-6"> ' +
        '<div class="row"> ' +
        '<div class="col-12 city-name"> ' +
        '</div>' +
        '</div> ' +
        '<div class="row"> ' +
        '<div class="col-12 tempreature"> ' +
        '</div> ' +
        '</div> ' +
        '</div> ' +
        '<div class="col-6 weather-icon d-inline-flex justify-content-end p-0"> ' +
        '<img class="weather-image" width="70px" height="70px" ' +
        ' src=""/> ' +
        ' </div> ' +
        ' </div> ' +
        ' <div class="row details-content"> ' +
        ' <div class="col-12 weather-desc"></div> ' +
        ' <div class="col-6 details humidity">Humidity</div> ' +
        ' <div class="col-6 details humidity-v"></div> ' +
        ' <div class="col-6 details rfeel">Real Feel</div> ' +
        ' <div class="col-6 details rfeel-v"></div> ' +
        '<div class="col-6 details clouds">Clouds</div> ' +
        '<div class="col-6 details clouds-v"></div> ' +
        '<div class="col-6 details wind">Wind</div> ' +
        '<div class="col-6 details wind-v"></div> ' +
        '<div class="col-6 details pressure">Pressure</div> ' +
        '<div class="col-6 details pressure-v"></div> ' +
        '<div class="col-6 details lupdated">Last Updated</div> ' +
        '<div class="col-6 details lupdated-v"></div> ' +
        '</div>' +
        '</div>'))
}

function formatLastUpdated(uTs) {
    const date = new Date(uTs * 1000);
    const hours = date.getHours();
    const minutes = "0" + date.getMinutes();
    const seconds = "0" + date.getSeconds();
    return hours + ':' + minutes.substr(-2) + ':' + seconds.substr(-2);
}
