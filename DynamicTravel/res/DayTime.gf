abstract DayTime = {
cat
  --DateTime;
  Day ;
  Time ;
  Number ;
  Digit ;
fun
  --When : Day -> Time -> DateTime;     -- on Sunday at 11 : 24
  HourMin : Number -> Number -> Time;       -- hour : min
  Hour : Number -> Time ;                -- 7 o'clock 
  Today, Tomorrow,
   Saturday, Sunday, Monday, Tuesday, Wednesday, Thursday, Friday : Day ; 
  
  Num : Digit -> Number ;
  Nums : Digit -> Number -> Number;
  N0, N1, N2, N3, N4, N5, N6, N7, N8, N9 : Digit;
  
}
