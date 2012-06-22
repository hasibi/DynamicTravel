concrete QueryHttp of Query = DayTimeHttp, StopHttp ** open (R=ResStopHttp) in {
lincat
  Query = { s : Str} ;
lin
  GoFromTo = mkHttp Dep ;
  Leave    = mkHttp Dep ;
  Arrive   = mkHttp Arr ;

param 
SearchTyp = Dep | Arr ;

oper 
  
  mkHttp : SearchTyp -> R.TStop -> R.TStop -> TDay -> TTime -> { s : Str} =
    \ searchTyp, from, to, day, time -> 
    case searchTyp of {
    	Dep => {s = "date=" ++ day.s ++ "&time=" ++ time.s ++ 
        	 "&originId=" ++ from.s ++ "&destId=" ++ to.s };
    	Arr => {s = "date=" ++ day.s ++ "&time=" ++ time.s ++ 
        	 "&originId=" ++ from.s ++ "&destId=" ++ to.s ++ "&searchForArrival=1" }
    };
}
