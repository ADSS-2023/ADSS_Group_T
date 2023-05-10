package UtilSuper;
public class Location {
    private int x;
    private int y;
    private int shippingArea;
    public Location(int x,int y) {
        this.x = x;
        this.y = y;
        this.shippingArea = setArea();
    }
    private int setArea() {
        if (x == 0 && y == 0) {
            return 0;
        } else if (x > 0 && y >= 0) {
            return  1;
        } else if (x <= 0 && y > 0) {
            return  2;
        } else if (x < 0 && y <= 0) {
            return  3;
        } else if (x >= 0 && y < 0){
            return  4;
        }
        return 0;
    }
    public int getShippingArea() {
        return this.shippingArea;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public static int getShippingArea(int x,int y){

        if (x == 0 && y == 0) {
            return 0;
        } else if (x > 0 && y >= 0) {
            return  1;
        } else if (x <= 0 && y > 0) {
            return  2;
        } else if (x < 0 && y <= 0) {
            return  3;
        } else if (x >= 0 && y < 0){
            return  4;
        }
        return 0;
    }
}



//                      |
//                      |
//            2         |        1
//                      |
//                      |
//                      |
//       ---------------0-----------------
//                      |
//                      |
//                      |
//            3         |        4
//                      |
//                      |
//
