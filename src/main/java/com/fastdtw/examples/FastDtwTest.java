/*
 * FastDtwTest.java   Jul 14, 2004
 *
 * Copyright (c) 2004 Stan Salvador
 * stansalvador@hotmail.com
 */

package com.fastdtw.examples;

import com.fastdtw.timeseries.TimeSeries;
import com.fastdtw.util.DistanceFunction;
import com.fastdtw.util.DistanceFunctionFactory;
import com.fastdtw.util.Utils;
import com.fastdtw.dtw.TimeWarpInfo;



public class FastDtwTest
{
   /**
    * This main method executes the FastDTW algorithm on two time series with a
    * specified radius. The time series arguments are file names for files that
    * contain one measurement per line (time measurements are an optional value
    * in the first column). After calculating the warp path, the warp
    * path distance will be printed to standard output, followed by the path
    * in the format "(0,0),(1,0),(2,1)..." were each pair of numbers in
    * parenthesis are indexes of the first and second time series that are
    * linked in the warp path
    *
    * @param args  command line arguments (see method comments)
    */
      public static void main(String[] args)
      {
        String dataFile1 = "/Users/haquangtan/Projects/dtw/src/sign/an/an1.data";
        String dataFile2 = "/Users/haquangtan/Projects/dtw/src/sign/an/an2.data";
        int searchRadius = 10;
        
        TimeSeries tsI = new TimeSeries(dataFile1, false, false, ',');
        TimeSeries tsJ = new TimeSeries(dataFile2, false, false, ',');
        //tsI = Utils.quantizeTS(tsI);
        //tsJ = Utils.quantizeTS(tsJ);
        
        final DistanceFunction distFn;
        distFn = DistanceFunctionFactory.getDistFnByName("EuclideanDistance");        
        
        final TimeWarpInfo info = com.fastdtw.dtw.FastDTW.getWarpInfoBetween(tsI, tsJ, searchRadius, distFn);

        System.out.println("Warp Distance: " + info.getDistance());
        System.out.println("Warp Path:     " + info.getPath());
         

      }  // end main()


}  // end class FastDtwTest
