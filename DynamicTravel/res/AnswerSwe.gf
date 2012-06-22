concrete AnswerSwe of Answer = DayTimeSwe, StopSwe ** open (R=ResStop) in {
flags coding = utf8 ;
lincat
  Answer, Vehicle, VhcTyp, Label, Tag = { s: Str};
lin
  Routing = mkRoute;
  Change  r1 r2 = { s = r1.s ++ "och sedan" ++ r2.s} ; 
  Vhc vhc lbl = { s = vhc.s ++ lbl.s} ;
  Lbl n = { s = "nummer" ++ n.s};
  Buss = { s = "buss"} ;
  Tram = { s = "spårvagn"} ;

oper
  mkRoute : { s: Str} -> R.TStop -> R.TStop -> TTime -> { s: Str} =
    \vhclbl, from, to, time -> 
      {s = "Ta" ++ vhclbl.s ++ "från" ++ from.s ++ from.t ++ "till" ++ to.s ++ to.t ++ "kl" ++ time.s};
}
