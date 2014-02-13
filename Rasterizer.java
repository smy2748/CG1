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
        // YOUR IMPLEMENTATION GOES HERE
    }    
      
}
