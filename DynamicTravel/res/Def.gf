abstract Def = Stop, DayTime **{
flags startcat = Def ;
cat
  New;
  Def;
fun
  DefPlace : New -> Stop -> Def;
  DefDay : New -> Day -> Def;
  DefPlaceDay : New -> Stop -> Day -> Def;
  DefPlaceDayTime : New -> Stop -> Day -> Time -> Def;

  Home, Work, University, Here, Gym, Pool, Bar,
  Birthday, Party, AfterWork, Office, Weekend : New;
}