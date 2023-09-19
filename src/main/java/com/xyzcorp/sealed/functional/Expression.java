package com.xyzcorp.sealed.functional;

/*
 * Algebraic Data Type
*/
public sealed interface Expression permits Constant, Sum, Subtract, Multiply {
}


