concrete TravelSwe of Travel = QuerySwe, AnswerSwe, DefSwe ** open (R=ResStop) in{
flags 
  coding = utf8;
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
	Go => {s = "Jag vill åka från" ++ from.alt ++ "till" ++ stopDay.alt ++ time.s };
	Lv => {s = "Jag vill åka från" ++ from.alt ++ time.s ++ "till" ++ stopDay.alt };
    Ar => {s = "Jag ankom" ++ stopDay.alt ++ time.s ++ "från" ++ from.alt }
  };
  mkQueryStopDayTime : R.TStop -> { stop : R.TStop; day : TDay; time : Str; alt : Str} -> { s : Str} =
    \ from, stopDayTime -> {s = "Jag vill åka från" ++ from.alt ++ "till" ++ stopDayTime.alt };
  
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
