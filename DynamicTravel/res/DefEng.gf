concrete DefEng of Def = StopEng, DayTimeEng **{
flags
	  coding = utf8;
lincat
  New, Def = { s : Str};
lin
  DefPlace new stop = {s = new.s ++ "means" ++ stop.s};
  DefDay new day = {s = new.s ++ "means" ++ day.s};
  DefPlaceDay new stop day = {s = new.s ++ "means" ++ stop.s ++ day.alt};
  DefPlaceDayTime new stop day time = {s = new.s ++ "means" ++ stop.s ++ day.alt ++ "at" ++ time.s};

  Home = { s = "home"} ;
  Work = { s = "work"} ;
  University = { s = "university" | "uni"} ;
  Here = { s = "here"};
  Gym = { s = "gym"} ;
  Pool = { s = "pool"} ;
  Bar = { s = "bar"};
  Birthday = { s = "birthday"};
  Party = { s = "party"};
  AfterWork = { s = "after work"};  
  Office = { s = "office"};  
  Weekend = { s = "weekend"};
}
