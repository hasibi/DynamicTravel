concrete TravelEng of Travel = QueryEng, AnswerEng, DefEng ** open (R=ResStop) in{
lincat
  Stmt = {s : Str} ;
  StopDay = { stop : R.TStop; day : TDay; alt : Str};
  StopDayTime = { stop : R.TStop; day : TDay; time : Str; alt : Str};

lin
  Ask q = q;
  Reply p = p;
  Customize d = d;

  GoFromToStopDay = mkQueryStopDay Go;
  LeaveStopDay = mkQueryStopDay Lv;
  ArriveStopDay = mkQueryStopDay Ar;
  
  GoFromToStopDayTime = mkQueryStopDayTime;

oper
  mkQueryStopDay : VerbCat -> R.TStop -> { stop : R.TStop; day : TDay; alt : Str} -> TTime -> { s : Str} =
	\ vCat, from, stopDay, time -> case vCat of {
	Go => {s = "I want to go from" ++ from.alt ++ "to" ++ stopDay.alt ++ time.s };
	Lv => {s = "I want to leave" ++ from.alt ++ time.s ++ "to" ++ stopDay.alt };
    Ar => {s = "I want to arrive" ++ stopDay.alt ++ time.s ++ "from" ++ from.alt }
  };
  mkQueryStopDayTime : R.TStop -> { stop : R.TStop; day : TDay; time : Str; alt : Str} -> { s : Str} =
    \ from, stopDayTime -> {s = "I want to go from" ++ from.alt ++ "to" ++ stopDayTime.alt };
    
  toStop : {s : Str} -> R.TStop -> R.TStop = 
  	\new, stop -> { s = stop.s; r = stop.r ; t = stop.t; alt = stop.alt | new.s};
  toWeekDay : {s : Str} -> TDay -> TDay = 
    \new, day -> { s = day.s; prep = day.prep; alt = day.alt | new.s };
  toStopDay : {s : Str} -> R.TStop -> TDay -> { stop : R.TStop; day : TDay; alt:Str} =
    \ new, st, d -> { stop =st; day = d; alt = new.s};
  toStopDayTime : {s : Str} -> R.TStop -> TDay -> Str -> { stop : R.TStop; day : TDay; time : Str; alt : Str} =
    \ new, st, d, t -> { stop = st; day = d; time = t; alt = new.s};
    
  toLabel : Str -> {s : Str} = \ str -> { s = str} ;
}
