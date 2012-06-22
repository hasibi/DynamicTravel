concrete DayTimeEng of DayTime = open StringOper in {
lincat
  Digit, Number= { s : Str };
  Time = TTime;
  Day = TDay;
oper
  TDay = { s : Str; prep : Str; alt : Str };
  TTime = { s : Str };
lin
  -- When = mkDateTime;
  HourMin hour min = { s = hour.s ++ ":" ++ min.s } ;
  Hour hour = { s = hour.s ++ "o'clock" };
  
  Today     = mkDay "today" "" ;
  Tomorrow  = mkDay "tomorrow" "" ;

  Saturday  = mkDay "Saturday" "on" ;
  Sunday    = mkDay "Sunday" "on" ;
  Monday    = mkDay "Monday" "on" ;
  Tuesday   = mkDay "Tuesday" "on" ;
  Wednesday = mkDay "Wednesday" "on" ;
  Thursday  = mkDay "Thursday" "on";
  Friday    = mkDay "Friday" "on" ;
  
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

oper 
  mkDay : Str -> Str -> {s : Str; prep : Str; alt : Str} = \d,p -> {s = d; prep = p; alt = p ++ d};
}
