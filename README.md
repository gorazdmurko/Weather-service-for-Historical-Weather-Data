# Weather Service for Historical Data
 outputs graph for historical temperatures in range of 24 hours

1. run ServerMain    -> starts RMI server
2. run MainApp       -> starts GUI & RMI client
3. run Tomcat server -> starts REST service


******************
*  DEPENDENCIES  *
******************

   - COMPONENT -               - DEPENDENCY -
1. DesktopGUI               * WeatherServiceITF
2. RestService              * WeatherApiAdapter, WeatherService, WeatherServiceITF
3. WeatherApiAdapter        * WeatherService, WeatherServiceITF
4. WeatherServer            * WeatherService, WeatherServiceITF, WeatherApiAdapter
5. WeatherService           * WeatherServiceITF
6. WeatherServiceITF        * /
