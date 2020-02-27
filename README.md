# jsoup-scrap-meteo
This application is made for collecting and monitoring METAR reports for specific airports. 

METAR is routine weather report issued at specific time intervals. It consists of 
ICAO four letter abbreviation for airport and relevant current weather conditions in 
encrypted form. 

Application uses: 
- Jsoup library for scraping raw METAR reports from 
  [allmetsat.com](https://www.allmetsat.com/)
- Quartz library for most efficient data loading 
- [Mivek library](https://github.com/mivek/MetarParser) for translating raw reports 
- Server side uses two ThreadPoolExecutor-s, "outer" and "inner". "Outer" is for 
  receiving client side requests and "inner" for executing asycn scrape requests 
- Client side uses one minute poling in order for reports to be
  UpToDate

At the first page user can choose which airport he wants 
to monitor. After saving to active list application is going to make initial 
METAR request from mentioned site. From that request application fetch time when 
last METAR was made and it counts next execution time. Based on that it schedules 
quartz job.
