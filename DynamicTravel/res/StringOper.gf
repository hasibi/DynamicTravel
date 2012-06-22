resource StringOper = {
oper

  SS : Type = {s : Str} ;
  ss : Str -> SS = \x -> {s = x} ;
  cc : SS -> SS -> SS = \x,y -> ss (x.s ++ y.s) ;
  prefix : Str -> SS -> SS = \p,x -> ss (p ++ x.s) ;
  infix  : SS -> Str -> SS -> SS = \x,p,y -> ss (x.s ++ p ++ y.s) ;
}

