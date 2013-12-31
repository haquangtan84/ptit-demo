package com.fastdtw.reports;

import java.util.ArrayList;

import com.fastdtw.timeseries.TimeSeries;
import com.fastdtw.util.DistanceFunction;
import com.fastdtw.util.DistanceFunctionFactory;
import com.fastdtw.util.Utils;
import com.fastdtw.dtw.TimeWarpInfo;

public class FRR {
	static final double[][] costMatrix = new double[8][8];
	public static String[] preGestureNameByUser = {"A_Template_Acceleration","C_Template_Acceleration","E_Template_Acceleration",
			"H_Template_Acceleration","J_Template_Acceleration","M_Template_Acceleration","R_Template_Acceleration","Z_Template_Acceleration"};
	public static String baseDir = "/Users/haquangtan/Projects/dtw/src/Nokia/";
	public static DistanceFunction distFn;	 
	
	
	public static void main(String[] args) {
		   
	    distFn = DistanceFunctionFactory.getDistFnByName("EuclideanDistance");
	    double warpDistances[][] = new double[8][7];
	    // Get max warp Distance for each of g
		/*for(int u=1; u<=8; u++) {
			String templateName = preGestureNameByUser[u-1];	
			ArrayList<String[]> arrSamples = new ArrayList<String[]>();
			arrSamples.clear();
			for(int d=1; d<=7; d++) {
				StringBuilder sb = new StringBuilder();
				sb.append(baseDir).append("U").append(u).append("-").append("D").append(d).append("/").append(templateName);				
				String templatesPath = sb.toString();
				double maxDistance = 0;
				for(int t=1; t<=10; t++) {
					StringBuilder sb1 = new StringBuilder();
					sb1.append(templatesPath).append(u).append("-").append(t).append(".txt");
					String gPath1 = sb1.toString();
					for(int v=1; v<=10; v++) {
						StringBuilder sb2 = new StringBuilder();
						sb2.append(templatesPath).append(u).append("-").append(v).append(".txt");
						String gPath2 = sb2.toString();
						TimeSeries tsI = new TimeSeries(gPath1, false, false, ' ');
					    TimeSeries tsJ = new TimeSeries(gPath2, false, false, ' ');					     
					    tsI = Utils.quantizeTS(tsI);
					    tsJ = Utils.quantizeTS(tsJ);
					    TimeWarpInfo info = com.fastdtw.dtw.DTW.getWarpInfoBetween(tsI, tsJ, distFn);
					    //System.out.println(info.getDistance());
					    if(info.getDistance() > maxDistance) {
					    	maxDistance = roundToDecimals(info.getDistance(),2);
					    }
					}										
				}
				warpDistances[u-1][d-1] = maxDistance;				
			}
			for(int i=0; i<7; i++) {
				System.out.print(warpDistances[u-1][i] + "  ");				
			}
			System.out.println("\n");
			System.out.println("  ====================================");
			
		}*/
		// Calculate FRR for each of guesture
		double[] thresholds = {0, 30, 60, 90, 120, 150, 180, 210, 230, 260, 290};
		double[] FRRPercentage = new double[11];
		for(int i=0; i<thresholds.length; i++) {
			double threshold = thresholds[i];
			double false_reject_rate = 0;
			for(int u=1; u<=8; u++) {
				String templateName = preGestureNameByUser[u-1];
				double counter = 0;
				double reject_rate_gesture = 0;
				for(int d=1; d<=7; d++) {
					StringBuilder sb = new StringBuilder();
					sb.append(baseDir).append("U").append(u).append("-").append("D").append(d).append("/").append(templateName);				
					String templatesPath = sb.toString();
					for(int t=1; t<=10; t++) {
						StringBuilder sb1 = new StringBuilder();
						sb1.append(templatesPath).append(u).append("-").append(t).append(".txt");
						String gPath1 = sb1.toString();
						for(int v=1; v<=10; v++) {
							StringBuilder sb2 = new StringBuilder();
							sb2.append(templatesPath).append(u).append("-").append(v).append(".txt");
							String gPath2 = sb2.toString();
							TimeSeries tsI = new TimeSeries(gPath1, false, false, ' ');
						    TimeSeries tsJ = new TimeSeries(gPath2, false, false, ' ');					     
						    tsI = Utils.quantizeTS(tsI);
						    tsJ = Utils.quantizeTS(tsJ);
						    TimeWarpInfo info = com.fastdtw.dtw.DTW.getWarpInfoBetween(tsI, tsJ, distFn);
						    if(info.getDistance() >= threshold) {
						    	counter ++;
						    }
						}			
					}
				}
				reject_rate_gesture = roundToDecimals((counter/700)*100, 2);
				false_reject_rate = false_reject_rate + reject_rate_gesture;
			}
			FRRPercentage[i] = roundToDecimals(false_reject_rate/8,2);
			
			
		}
		for(int i=0; i<thresholds.length; i++) {
			System.out.print("  " + FRRPercentage[i]);
		}
	}
	
	public static double[][] testByUser(ArrayList<String[]> arrSamples) {
		double result[][] = new double[8][8];
		for(int i = 0; i< arrSamples.size(); i++) {
			String[] templates = arrSamples.get(i);
			//double result[][] = new double[8][8];
			for(int g = 0; g<8; g++) {
				//Cu chi thu j
				
				for(int j=0; j<arrSamples.size(); j++)
				{					
					String[] samples = arrSamples.get(j);
					String sample = samples[g];
					int match = 0;
					double distance = 0;
					for(int k=0; k<templates.length; k++)
					{
						
						 TimeSeries tsI = new TimeSeries(sample, false, false, ' ');
					     TimeSeries tsJ = new TimeSeries(templates[k], false, false, ' ');
					     
					     tsI = Utils.quantizeTS(tsI);
					     tsJ = Utils.quantizeTS(tsJ);
					     int searchRadius = 50;
					     TimeWarpInfo info = com.fastdtw.dtw.DTW.getWarpInfoBetween(tsI, tsJ, distFn);
					     if(k==0) {
					    	 match = 0;
					    	 distance = info.getDistance();
					     } else if(info.getDistance() < distance){
					    	 distance = info.getDistance();
					    	 match = k;
					     }					     
					}
					result[g][match] = result[g][match] + 1;					
				}		
				
			}
			
		}
		
		return result;
		
	}
	///////////////////////////
	public static double[][] sumMatrixs(double[][] matrix1, double[][] matrix2) {
		double result[][] = new double[8][8];
		for(int i=0; i<8; i++) {
			for(int j=0; j<8; j++) {
				result[i][j] = (matrix1[i][j] + matrix2[i][j]);
			}
		}
		return result;
	}
	////////////////////////////
	public static double roundToDecimals(double d, int c)  
	{   
	   int temp = (int)(d * Math.pow(10 , c));  
	   return ((double)temp)/Math.pow(10 , c);  
	}
	////////////////////////////
	public static double[][] addToFinalReport(double[][] matrix) {
		double result[][] = new double[8][8];
		for(int i=0; i<8; i++) {
			for(int j=0; j<8; j++) {
				result[i][j] = (roundToDecimals((matrix[i][j]/4900)*100,2));
			}
		}
		return result;
	}
	///////////////////////////////////////////////////
	public static double[][] resetValues(double[][] matrix) {
		double result[][] = new double[8][8];
		for(int i=0; i<8; i++) {
			for(int j=0; j<8; j++) {
				result[i][j] = 0;
			}
		}
		return result;
	}

}
