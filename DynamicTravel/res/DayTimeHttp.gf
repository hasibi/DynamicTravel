concrete DayTimeHttp of DayTime =  open StringOper in {
lincat
  Digit, Number = { s : Str} ;
  Day = TDay ;
  Time = TTime ;
oper 
  TDay = { s : Str};
  TTime = { s : Str};

lin
  HourMin hour min = { s = hour.s ++ ":" ++ min.s ; } ;
  Hour hour = { s= hour.s ++ "- 0 0" ; } ;
  Today     = ss "8" ;
  Tomorrow  = ss "9" ;

  Saturday  = ss "7" ;
  Sunday    = ss "1" ;
  Monday    = ss "2" ;
  Tuesday   = ss "3" ;
  Wednesday = ss "4" ;
  Thursday  = ss "5" ;
  Friday    = ss "6" ;
  
  Num d = d ;
  Nums d n = {s = d.s ++ n.s};
  N0 = ss "0";
  N1 = ss "1";
  N2 = ss "2";
  N3 = ss "3";
  N4 = ss "4";
  N5 = ss "5";
  N6 = ss "6";
  N7 = ss "7";
  N8 = ss "8";
  N9 = ss "9";
}
