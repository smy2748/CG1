//
//  Rasterizer.java
//  
//
//  Created by Joe Geigel on 1/21/10.
//  Copyright 2010 __MyCompanyName__. All rights reserved.
//

/**
 * 
 * A simple class for performing rasterization algorithms.
 *
 */

import java.util.*;

public class Rasterizer {
    
    /**
     * number of scanlines
     */
    int n_scanlines;
    
    /**
     * Constructor
     *
     * @param n - number of scanlines
     *
     */
    Rasterizer (int n)
    {
        n_scanlines = n;
    }
    
    /**
     * Draw a line from (x0,y0) to (x1, y1) on the simpleCanvas C.
     *
     * Implementation should be using the Midpoint Method
     *
     * You are to add the implementation here using only calls
     * to C.setPixel()
     *
     * @param x0 - x coord of first endpoint
     * @param y0 - y coord of first endpoint
     * @param x1 - x coord of second endpoint
     * @param y1 - y coord of second endpoint
     * @param C - The canvas on which to apply the draw command.
     */
    public void drawLine (int x0, int y0, int x1, int y1, simpleCanvas C)
    {

        //Vertical Line check
        if(x0 == x1){
            drawVerticalLine(y0,y1,x0,C);
        }



        //Swap so we always draw right to left
        if(x0 > x1){
            int tempx, tempy;

            tempx = x0;
            tempy = y0;

            x0 = x1;
            y0 = y1;

            y1 = tempy;
            x1= tempx;
        }

        //Horizontal line case
        if(y0 == y1){
            drawHorizontalLine(x0,x1,y0,C);
        }

        int dx = x1 - x0, dy = y1-y0;

        //Diagonal Optimizations
        if(dy == dx){
            drawPosDiagonal(x0, y0, x1, C);
        }
        else if((-1 * dy) == dx){
            drawNegDiagonal(x0,x1,y0,C);
        }
        //Draw large positive
        else if(dy > 0 && dy >= dx){
            drawLargePos(x0,x1,y0,y1,C);
        }
        //Draw small positive
        else if(dy > 0 && dy < dx){
            drawSmallPos(x0,x1,y0,y1,C);
        }
        //Draw small negative
        else if(dy < 0 && (-1 * dy) < dx ){
            drawSmallNeg(x0,x1,y0,y1,C);
        }
        //Draw Large Negative
        else{
            drawLargeNeg(x0,x1,y0,y1,C);
        }

    }

    /**
     * Draw a negative diagonal line from x0,y0 to x1.
     * As m=-1, this is a simple optimization
     * @param x0
     * @param x1
     * @param y0
     * @param c The canvas being written on
     */
    protected void drawNegDiagonal(int x0, int x1, int y0, simpleCanvas c){
        for(int x=x0, y=y0; x<= x1; x++, y-- ){
            c.setPixel(x,y);
        }
    }

    /**
     * Draw a small positive slope (0<m<1)
     * @param x0 Value of x0 coordinate
     * @param x1 Value of x1 coordinate
     * @param y0 Value of y0 coordinate
     * @param y1 Value of y1 coordinate
     * @param c The canvas being written on
     */
    protected void drawSmallPos(int x0, int x1, int y0, int y1, simpleCanvas c){
        int dx = x1 - x0, dy = y1-y0,
            dE = 2*dy,dNE = 2*(dy-dx),
            d = dE-dx;

        for(int x = x0, y=y0; x <= x1; ++x ){
            c.setPixel(x, y);

            if(d <= 0){
                d += dE;
            }else{
                y++;
                d += dNE;
            }
        }

    }

    /**
     * Draw a small negative slope (-1<m<0)
     * @param x0 Value of x0 coordinate
     * @param x1 Value of x1 coordinate
     * @param y0 Value of y0 coordinate
     * @param y1 Value of y1 coordinate
     * @param c The canvas being written on
     */
    protected void drawSmallNeg(int x0, int x1, int y0, int y1, simpleCanvas c){
        int dx = x1-x0,
                dy = y1-y0,
                dE = 2*dy,
                dSE = 2*(dy+dx),
                d = 2*dy + dx;

        for(int x=x0, y=y0; x <= x1; ++x){
            c.setPixel(x,y);

            if(d <= 0){
                d += dSE;
                --y;
            }
            else{
                d+= dE;
            }
        }

    }


    /**
     * Draw a large negative slope (-infinity < m < -1)
     * @param x0 Value of x0 coordinate
     * @param x1 Value of x1 coordinate
     * @param y0 Value of y0 coordinate
     * @param y1 Value of y1 coordinate
     * @param c The canvas being written on
     */
    protected void drawLargeNeg(int x0, int x1, int y0, int y1, simpleCanvas c){
        int dx = x1-x0,
                dy = y1-y0,
                dS = 2*dx,
                dSE = 2*(dx+dy),
                d = dy+2*dx;

        for(int x=x0, y=y0; y >= y1; --y){
            c.setPixel(x,y);

            if(d >= 0){
                d +=dSE;
                x++;
            }
            else{
                d+= dS;
            }
        }
    }

    /**
     * Draw a large positive slope (1<m< infinity)
     * @param x0 Value of x0 coordinate
     * @param x1 Value of x1 coordinate
     * @param y0 Value of y0 coordinate
     * @param y1 Value of y1 coordinate
     * @param c The canvas being written on
     */
    protected void drawLargePos(int x0, int x1, int y0, int y1, simpleCanvas c){
        int dx = x1-x0,
                dy=y1-y0,
                dN = -2*dx,
                dNE = 2*(dy-dx),
                d = dy-(2*dx);

        for(int x=x0, y=y0; y<=y1; ++y){
            c.setPixel(x,y);

            if(d <= 0){
                d += dNE;
                x++;
            }
            else{
                d += dN;
            }
        }
    }


    /**
     * Optimization for m=1
     * @param x0 Value of x0 coordinate
     * @param y0 Value of y0 coordinate
     * @param x1 Value of x1 coordinate (ending x value)
     * @param c The canvas being drawn on
     */
    protected void drawPosDiagonal(int x0,int y0, int x1, simpleCanvas c){
        for(int x=x0, y = y0; x <= x1; x++, y++){
            c.setPixel(x,y);
        }
    }


    /**
     * Draw a vertical line
     * @param y0 Coordinate for y0
     * @param y1 Coordinate value of y1
     * @param x Value of either x0 or x1
     * @param c The simpleCanvas being drawn on
     */
    protected void drawVerticalLine(int y0, int y1, int x, simpleCanvas c){

        //Swap so we are always drawing top to bottom
        if(y1 < y0){
            int tempy;
            tempy = y1;
            y1 = y0;
            y0 = tempy;
        }

        //Draw the line
        for(int i=y0; i<= y1; i++){
            c.setPixel(x,i);
        }
    }


    /**
     * Draw a horizontal line from x0 to x1 at height y on canvas c
     * @param x0
     * @param x1
     * @param y
     * @param c The canvas being written on
     */
    protected void drawHorizontalLine(int x0, int x1, int y, simpleCanvas c){
        for(int i=x0; i<=x1; i++){
            c.setPixel(i,y);
        }
    }
      
}
