/*
 * Copyright (C) 2003-2013 eXo Platform SAS.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.fastdtw.util;

import com.fastdtw.timeseries.TimeSeries;
import com.fastdtw.timeseries.TimeSeriesPoint;

/**
 * Created by The eXo Platform SAS
 * Author : eXoPlatform
 *          tanhq@exoplatform.com
 * Aug 29, 2013  
 */
public class Utils {
  public static final double g = 9.8;
  
  public static TimeSeries quantizeTS(TimeSeries tsData) {
    if(tsData != null) {
      for(int i = 0; i< tsData.tsArray.size(); i++) {
        TimeSeriesPoint tsPoint = (TimeSeriesPoint) tsData.tsArray.get(i);
        for(int j = 0; j<tsPoint.size(); j++) {
          double value = tsPoint.get(j);
          tsPoint.set(j, calQuantizeValue(value));
        }
      }
    }
    return tsData;
  }
  
  public static int calQuantizeValue(double value) {
    int quantizeValue = 0;
    if(Double.compare(value, 0.0) == 0) {
      quantizeValue = 0;
    } else if(Double.compare(value, 2*g) >= 0){
      quantizeValue = 16;
    } else if(Double.compare(value, g) >= 0 && Double.compare(value, 2*g) < 0) {
      quantizeValue = (int) (11 + Math.round(4*(value-g)/g));
    } else if(Double.compare(value, 0.0) > 0 && Double.compare(value, g) < 0) {
      quantizeValue = (int) (10 - Math.round(9*(g-value)/g));
    } else if(Double.compare(value, -g) >= 0 && Double.compare(value, 0) < 0) {
      quantizeValue = (int) (-1 - Math.round(9*(0-value)/g));
    } else if (Double.compare(value, -2*g) >= 0 && Double.compare(value, -g) < 0){
      quantizeValue = (int) (-11 - Math.round(4*(-g-value)/g));      
    } else if (Double.compare(value, -2*g) <0) {        
      quantizeValue = -16;
    }
    return quantizeValue;
  }

}
