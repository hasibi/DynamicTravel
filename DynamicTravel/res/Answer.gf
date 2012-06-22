abstract Answer = DayTime, Stop ** {
flags startcat = Answer;
cat
  Answer;
  Vehicle;
  VhcTyp;
  Label;
  Tag;
fun
  -- take bus number 6 from Chalmers track A to Valand track B at 11 
  Routing : Vehicle -> Stop -> Stop -> Time -> Answer;
  Change     : Answer -> Answer -> Answer ; 
  Vhc : VhcTyp -> Label -> Vehicle ;
  Lbl : Number -> Label ;
  Buss, Tram : VhcTyp ;
  
  {-TripNumber : Number -> Answer;
  DepartureTime : Time -> Answer;
  ShortestTime : Number -> Answer;
  LeastChanges : Number -> Answer;-}
  
}
