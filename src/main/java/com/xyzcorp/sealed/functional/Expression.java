package com.xyzcorp.sealed.functional;

/*
 * Algebraic Data Type
 * Each record permitted by the Expression takes Expressions as arguments
 * The Evaluator uses pattern matching to figure out what to do with the arguments
*/
public sealed interface Expression permits Constant, Sum, Subtract, Multiply {
}


