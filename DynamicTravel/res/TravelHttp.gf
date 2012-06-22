concrete TravelHttp of Travel = QueryHttp, AnswerHttp ** open (R=ResStopHttp) in {
lincat
  Stmt = {s :Str} ;
  StopDay = { stop : R.TStop; day : TDay};
  StopDayTime = { stop : R.TStop; day : TDay; time : Str};

lin
  GoFromToStopDay from stopDay =  mkHttp Dep from stopDay.stop stopDay.day ;
  LeaveStopDay from stopDay =  mkHttp Dep from stopDay.stop stopDay.day ;
  ArriveStopDay from stopDay =  mkHttp Arr from stopDay.stop stopDay.day ;
  
  GoFromToStopDayTime from stopDayTime =  mkHttp Dep from stopDayTime.stop stopDayTime.day { s = stopDayTime.time};
  
  Ask q = q;

oper

  toStopDay : R.TStop -> TDay -> { stop : R.TStop; day : TDay} = 
    \st, d -> { stop = st; day = d};
  toStopDayTime : R.TStop -> TDay -> Str -> { stop : R.TStop; day : TDay; time : Str} = 
    \st, d, t -> { stop = st; day = d; time = t};
  toLabel : Str -> {s : Str} = \ str -> { s = str} ;
}

