concrete QueryEng of Query = DayTimeEng, StopEng ** open (R=ResStop) in {
lincat
  Query = { s : Str} ;
lin
  GoFromTo = mkQuery Go;
  Leave    = mkQuery Lv;
  Arrive   = mkQuery Ar;

param
  VerbCat = Go | Lv | Ar;
oper
  mkQuery : VerbCat -> R.TStop -> R.TStop -> TDay -> TTime -> { s : Str} = \ vCat, from, to, day, time ->
  case vCat of {
    Go => {s = "I want to go from" ++ from.alt ++ "to" ++ to.alt ++ day.alt ++ "at" ++ time.s };
    Lv => {s = "I want to leave" ++ from.alt ++ day.alt ++ "at" ++ time.s ++ "to" ++ to.alt };
    Ar => {s = "I want to arrive" ++ to.alt ++ day.alt ++ "at" ++ time.s ++ "from" ++ from.alt }
  };
}
