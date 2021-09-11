# Weather Live Application
#### A simple application to follow live weather changes for pre-selected set of cities. 
##### Based on openweathermap.org.

## The Idea behind the solution
 * WeatherLive simply queries live weather updates from Openweather map
 * The weather query is based on a preselected city list, that is defined in a properties file.
 * The app stores the query results in a Redis store
 * The app will repeat the query as defined every 5 seconds.
 * The App UI will connect via the backend via a websocket connection.
 * Once connected to the websocket, the app UI would load all available cities from the backend.
 * For every city loaded, the app UI will subscribe to the weather changes, so that the weather data for this city can be viewed in real-time.
 * On any error that occurs during runtime when data updates fail to be fetched from openweathermap, the city name will be marked in `RED` color on the UI.
 * Please notice that you don't need to refresh to get newer weather data, it is all updated via a websocket connection.
 * You can always use the input city name filter to view the desired city

## Technical Implementation of the solution
 * The solution is a small microservice based on Java 11/Springboot
 * The service uses `Redis` as a store for the weather data fetched from `openweathermap`.
 * The service is easily configured for a list of cities to be used for weather queries in the `application.properties` file.
 * Simply edit the property `app.cities` to do so.
 * Same scenario goes for changing the `openweathermap` application Id, simple edit the property `openweather.appId`
 * The service uses `STOMP` protocol for websocket connection.
 * Weather data are queried every `5 seconds` as configured in the `WeatherDataUpdater` Class.
 * Weather data is stored in a redis store once fetched.
 * The UI connects to the backend using `STOMP`, where it loads the available cities and subscribes for weather changes for them.
 * The service is covered by unit tests.

## Tech. stack used
 * Java 11
 * Springboot
 * SpringWebSocket
 * Lombok
 * Spring Cloud Open Feign
 * Jquery / Bootstrap / Sockjs / CSS / HTML / Axios
 * Redis
 * Docker

## Further developments overview
 * The feature could be further developed to a complete solution that can show real time weather for any city selected
 * It can be also used by coordinates to show a real time weather on a Map (Allowed by openweathermap)
 * The service can also be improved in a way that it fetches weather changes overtime and shows historic weather changes, and help make predictions for the future
 * It can also be used for data analysis and real time alerts for disasters and storms.



