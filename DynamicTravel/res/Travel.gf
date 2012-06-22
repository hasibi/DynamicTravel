abstract Travel = Query, Answer, Def ** {
flags startcat = Stmt;
cat
  Stmt ;
  StopDay ;
  StopDayTime;
fun
  GoFromToStopDay, LeaveStopDay, ArriveStopDay : Stop -> StopDay -> Time -> Query;
  GoFromToStopDayTime : Stop -> StopDayTime -> Query;
  Ask : Query -> Stmt;
  Reply : Answer -> Stmt;
  Customize : Def -> Stmt ;

}
