concrete QuerySwe of Query = DayTimeSwe, StopSwe ** open (R=ResStop) in {
flags 
  coding = utf8;
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
    Go => {s = "Jag vill åka från" ++ from.alt ++ "till" ++ to.alt ++ day.alt ++ "kl" ++ time.s };
    Lv => {s = "Jag vill åka från" ++ from.alt ++ day.alt ++ "kl" ++ time.s ++ "till" ++ to.alt };
    Ar => {s = "Jag ankom" ++ to.alt ++ day.alt ++ "kl" ++ time.s ++ "från" ++ from.alt }
  };
}
