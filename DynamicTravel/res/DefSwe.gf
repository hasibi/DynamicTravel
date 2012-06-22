concrete DefSwe of Def = StopSwe, DayTimeSwe **{
flags 
  coding = utf8;
lincat
  New, Def = { s : Str};
lin
  DefPlace new stop = {s = new.s ++ "betyder" ++ stop.s};
  DefDay new day = {s = new.s ++ "betyder" ++ day.s};
  DefPlaceDay new stop day = {s = new.s ++ "betyder" ++ stop.s ++ day.alt};
  DefPlaceDayTime new stop day time = {s = new.s ++ "betyder" ++ stop.s ++ day.alt ++ time.s};
  
  Home = { s = "hem"} ;
  Work = { s = "jobbet"} ;
  University = { s = "universitet"} ;
  Here = { s = "här"};
  Gym = { s = "gym"} ;
  Pool = { s = "pool"} ;
  Bar = { s = "bar"};
  Birthday = { s = "födelsedag"};
  Party = { s = "party"};
  AfterWork = { s = "after work"};  
  Office = { s = "kontor"};  
  Weekend = { s = "helg"} ;
}
