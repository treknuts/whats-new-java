package com.xyzcorp.sealed.shapes;

/*
 * Notice non-sealed
 * An "in" to subclassing
 * For example a Circle can be a subclass of a Rectangle
 * We're at a crossroads between OO and Functional programming :')
*/
public non-sealed class Rectangle extends Shape {
    private final int length;
    private final int height;

    public Rectangle(int length, int height) {
        this.length = length;
        this.height = height;
    }

    @Override
    public int area() {
        return length * height;
    }
}
