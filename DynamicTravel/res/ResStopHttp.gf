resource ResStopHttp = {
oper 
  TStop = { s : Str} ;
  mkStop : Str -> { s : Str} = \st -> { s = st }; 
}
