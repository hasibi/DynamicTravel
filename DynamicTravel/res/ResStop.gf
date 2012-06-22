resource ResStop = {

oper 
  TStop = { s : Str; r : Str; t : Str; alt : Str};
  mkStop : Str -> Str -> Str -> TStop = 
    \stop, region, track -> { s = stop; r = region; t = track; alt = stop}; 

}
