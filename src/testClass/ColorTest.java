package testClass;

import topology.Color;

import static org.junit.jupiter.api.Assertions.*;


class ColorTest {

    Color c1 = new Color(255,255,255);
    Color c2 = new Color(255,255,255);
    Color c3 = new Color(0,0,0);
    Color c4 = new Color(0,0,125);
    Color c5 = new Color(0,132,0);



    @org.junit.jupiter.api.Test
    void getB() {
        assertEquals(255, Byte.toUnsignedInt(c1.getB()));
    }

    @org.junit.jupiter.api.Test
    void getG() {
        assertEquals(132, Byte.toUnsignedInt(c5.getG()));
    }

    @org.junit.jupiter.api.Test
    void getR() {
        assertEquals(125, Byte.toUnsignedInt(c4.getR()));
    }

    @org.junit.jupiter.api.Test
    void dist() {
        assertEquals(0, Color.dist(c1, c2));
        assertEquals(195075, Color.dist(c1, c3));
        assertEquals(146950, Color.dist(c1, c4));
    }

    @org.junit.jupiter.api.Test
    void set() {
    }

    @org.junit.jupiter.api.Test
    void isequalto() {
    }

}