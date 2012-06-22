abstract Query = DayTime, Stop ** {
flags startcat = Query;
cat
   Query ;
fun
  GoFromTo, Leave , Arrive   : Stop -> Stop -> Day -> Time -> Query ;
}
