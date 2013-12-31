package com.fastdtw.reports;

import java.util.ArrayList;

import com.fastdtw.timeseries.TimeSeries;
import com.fastdtw.util.DistanceFunction;
import com.fastdtw.util.DistanceFunctionFactory;
import com.fastdtw.util.Utils;
import com.fastdtw.dtw.TimeWarpInfo;

public class SignDTWReportAllUsers {
	public static String baseDir = "/Users/haquangtan/Projects/Sign/";
	public static DistanceFunction distFn;	 
	
	
	public static void main(String[] args) {
		   
	    distFn = DistanceFunctionFactory.getDistFnByName("EuclideanDistance");
	    double reportlByUser[][] = new double[7][7];
	    double reportlFinal[][] = new double[7][7];
	    ArrayList<String[]> arrSamples = new ArrayList<String[]>();
		arrSamples.clear();
		for(int u=1; u<=7; u++) {	
			reportlByUser = resetValues(reportlByUser);
			for(int i=1; i<=10; i++) {
				String[] arr  = new String[7];
				for(int j=1; j<=7; j++) {
					StringBuilder sb = new StringBuilder();
					sb.append(baseDir).append("User").append(u).append("/").append("sign").append(j).append("-").append(i).append(".csv");
					arr[j-1] = sb.toString();
				}
				arrSamples.add(arr);
			}			
		}
		
		reportlByUser = sumMatrixs(reportlByUser,testByUser(arrSamples));
		for(int t=0; t<7; t++) {
			for(int v=0; v<7; v++) {
				System.out.print("  " + roundToDecimals((reportlByUser[t][v]/4900)*100,2));
			}
			System.out.println("\n");
		}		
	}
	
	public static double[][] testByUser(ArrayList<String[]> arrSamples) {
		double result[][] = new double[7][7];
		for(int i = 0; i< arrSamples.size(); i++) {
			String[] templates = arrSamples.get(i);
			//double result[][] = new double[8][8];
			for(int g = 0; g<7; g++) {
				//Cu chi thu j
				
				for(int j=0; j<arrSamples.size(); j++)
				{					
					String[] samples = arrSamples.get(j);
					String sample = samples[g];
					int match = 0;
					double distance = 0;
					for(int k=0; k<templates.length; k++)
					{
						
						 TimeSeries tsI = new TimeSeries(sample, false, false, ',');
					     TimeSeries tsJ = new TimeSeries(templates[k], false, false, ',');
					     //System.out.println("   " + sample +  "  ===  " +templates[k]);
					     //tsI = Utils.quantizeTS(tsI);
					     //tsJ = Utils.quantizeTS(tsJ);
					     TimeWarpInfo info = com.fastdtw.dtw.DTW.getWarpInfoBetween(tsI, tsJ, distFn);
					    // System.out.println(info.getDistance() + " === " + sample + " == " + templates[k]);
					     if(k==0) {
					    	 match = 0;
					    	 distance = info.getDistance();
					     } else if(info.getDistance() < distance){
					    	 distance = info.getDistance();
					    	 match = k;
					     }					     
					}
					//System.out.println(distance + " === " + sample + " == " + templates[match]);
					//System.out.println("   =========================================== ");
					result[g][match] = result[g][match] + 1;					
				}		
				
			}
			
		}
		
		return result;
		
	}
	///////////////////////////
	public static double[][] sumMatrixs(double[][] matrix1, double[][] matrix2) {
		double result[][] = new double[7][7];
		for(int i=0; i<7; i++) {
			for(int j=0; j<7; j++) {
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
		double result[][] = new double[7][7];
		for(int i=0; i<7; i++) {
			for(int j=0; j<7; j++) {
				result[i][j] = (roundToDecimals((matrix[i][j]/100)*100,2));
			}
		}
		return result;
	}
	///////////////////////////////////////////////////
	public static double[][] resetValues(double[][] matrix) {
		double result[][] = new double[7][7];
		for(int i=0; i<7; i++) {
			for(int j=0; j<7; j++) {
				result[i][j] = 0;
			}
		}
		return result;
	}

}
