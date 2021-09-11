function handleWeatherUpdated(message) {
    renderCityWeather(message)
}

function handleWeatherUpdateError(message) {
    showCityError(message)
}