concrete AnswerEng of Answer = DayTimeEng, StopEng ** open (R=ResStop) in {
flags coding = utf8 ;
lincat
  Answer, Vehicle, VhcTyp, Label, Tag = { s: Str};
lin
  Routing = mkRoute;
  Change  r1 r2 = { s = r1.s ++ "then" ++ r2.s} ; 
  Vhc vhc lbl = { s = vhc.s ++ lbl.s} ;
  Lbl n = { s = "number" ++ n.s};
  Buss = { s = "bus"} ;
  Tram = { s = "tram"} ;
  
  
 -- TripNumber : Number -> Answer;
 -- DepartureTime : Time -> Ans;
 -- ShortestTime : Number -> Ans;
 -- LeastChanges : Number -> Ans;
  

oper
  mkRoute : { s: Str} -> R.TStop -> R.TStop -> TTime -> { s: Str} =
    \vhc, from, to, time -> 
      {s = "Take" ++ vhc.s ++ "from" ++ from.s ++ from.t ++ "to" ++ to.s ++ to.t ++ "at" ++ time.s};
}
