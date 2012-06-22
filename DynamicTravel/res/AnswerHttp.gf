concrete AnswerHttp of Answer = DayTimeHttp, StopHttp ** {
flags coding = utf8 ;
lincat
  Answer, Vehicle, VhcTyp, Label, Tag = { s: Str};
lin
  Routing = mkRoute ;
  Change  r1 r2 = { s = r1.s ++ r2.s} ; 
  Vhc vhc lbl = { s = lbl.s ++  vhc.s };
  Lbl n = n;
  Buss = { s = ",B|"} ;
  Tram = { s = ",S|"} ;

oper
  mkRoute : { s: Str} -> { s: Str} -> { s: Str} -> { s: Str} -> { s: Str} =
    \vhc, from, to, time -> 
      {s = "line =" ++ vhc.s ++ "& fromId =" ++ from.s ++ "& toId =" ++ to.s 
          ++ "& departureTime =" ++ time.s ++ "#"};
    -- "#" is a separator for changing routes, at the end of each route
}
