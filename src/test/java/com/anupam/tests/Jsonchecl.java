package com.anupam.tests;

public class Jsonchecl {

    /*{ { { } } { { } } }
    --  a a a b b a a b b b
    --  a a b a a b b b
    -- a a a b b b
    -- a  a b b
    -- a b
    -

    { [ { } {
     } { } ] }
     lenth/2 = 5

   a1 { [ { }
   a2 { } ] }

    a2[0] = a1[4]
    switch{
    case '{'
       a1[4] == }
        return false ;
    }

 { [ { } { } ] [ { } [ { } ] ] }
 a b a a1 a a1 b1 b a a1 b a a1 b1 b1 a


 single stack  --

 loop -- a1 -- alen
  stack emppty
  stact.pusk()

 stack.pop() - > a [i] ---
 stack.peek

 stack.lengh 0 - true





 a a1
 b b1






   -

     */

    public boolean isValidJson(String jsonbracs){
        char[] charjsonbracs = jsonbracs.toCharArray();
        if (charjsonbracs.length<=1)
            return false;
        if ((charjsonbracs.length)%2!=0)
            return false;
        else{

        }
        return true;

    }

    public static void main(String[] args) {

    }
}
