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


        if(y0 == y1){
            drawHorizontalLine(x0,x1,y0,C);
        }

        int dx = x1 - x0, dy = y1-y0;

        if(dy == dx){
            drawPosDiagonal(x0, y0, x1, C);
        }
        else if((-1 * dy) == dx){
            drawNegDiagonal(x0,x1,y0,C);
        }
        else if(dy > 0 && dy >= dx){
            drawLargePos(x0,x1,y0,y1,C);
        }
        else if(dy > 0 && dy < dx){
            drawSmallPos(x0,x1,y0,y1,C);
        }
        else if(dy < 0 && (-1 * dy) < dx ){
            drawSmallNeg(x0,x1,y0,y1,C);
        }
        else{
            drawLargeNeg(x0,x1,y0,y1,C);
        }




    }


    protected void drawNegDiagonal(int x0, int x1, int y0, simpleCanvas c){
        for(int x=x0, y=y0; x<= x1; x++, y-- ){
            c.setPixel(x,y);
        }
    }
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

    protected void drawSmallNeg(int x0, int x1, int y0, int y1, simpleCanvas c){
        int dx, dy, sum;

        dx = x1 - x0;
        dy = y1 - y0;
        sum = 0;

        for(int x = x0, y = y0; x <x1; x++ ){
            c.setPixel(x,y);
            sum -= dy;

            if(sum > dx){
                sum -=dx;
                y--;
            }

        }
    }

    protected void drawLargeNeg(int x0, int x1, int y0, int y1, simpleCanvas c){
        int dx, idy, sum;

        dx = x1 -x0;
        idy =  y0 - y1;
        sum = 0;

        for(int x=x0, y=y0; y >= y1; y--){
            c.setPixel(x,y);



            if (sum >= idy){
                x++;
                sum -= idy;
            }
            sum += dx;
        }
    }

    protected void drawLargePos(int x0, int x1, int y0, int y1, simpleCanvas c){
        /*int dx = x1 - x0, dy = y1-y0,
                dN = 2*(dy-dx),dNE = -2*dx,
                d = dy - 2*dx;

        for(int x = x0, y=y0; y <= y1; ++y ){
            c.setPixel(x, y);

            if(d <= 0){
                d += dN;
            }else{
                x++;
                d += dNE;
            }
        }*/

        int dx,dy,ysum;

        dx = x1-x0;
        dy = y1-y0;
        ysum=0;

        for(int y=y0, x=x0; y<=y1;y++){
            c.setPixel(x,y);

            if(ysum >= dy){
                x++;
                ysum -= dy;

            }

            ysum +=dx;
        }
    }

    protected void drawPosDiagonal(int x0,int y0, int x1, simpleCanvas c){
        for(int x=x0, y = y0; x <= x1; x++, y++){
            c.setPixel(x,y);
        }
    }

    protected void drawVerticalLine(int y0, int y1, int x, simpleCanvas c){

        if(y1 < y0){
            int tempy;
            tempy = y1;
            y1 = y0;
            y0 = tempy;
        }
        for(int i=y0; i<= y1; i++){
            c.setPixel(x,i);
        }
    }

    protected void drawHorizontalLine(int x0, int x1, int y, simpleCanvas c){
        for(int i=x0; i<=x1; i++){
            c.setPixel(i,y);
        }
    }
      
}
