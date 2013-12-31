package com.fastdtw.reports;

import java.util.ArrayList;

import com.fastdtw.timeseries.TimeSeries;
import com.fastdtw.util.DistanceFunction;
import com.fastdtw.util.DistanceFunctionFactory;
import com.fastdtw.util.Utils;
import com.fastdtw.dtw.TimeWarpInfo;

public class BasicGestureDTWReport {
	static final double[][] costMatrix = new double[8][8];
	public static String[] preGestureNameByUser = {"A_Template_Acceleration","C_Template_Acceleration","E_Template_Acceleration",
			"H_Template_Acceleration","J_Template_Acceleration","M_Template_Acceleration","R_Template_Acceleration","Z_Template_Acceleration"};
	public static String baseDir = "/home/tanhq/java/projects/dtw/src/Nokia/";
	public static DistanceFunction distFn;	 
	
	
	public static void main(String[] args) {
		   
	    distFn = DistanceFunctionFactory.getDistFnByName("EuclideanDistance");
	    double reportlByUser[][] = new double[8][8];
	    double reportlFinal[][] = new double[8][8];
	    
		for(int u=1; u<=8; u++) {
			String templateName = preGestureNameByUser[u-1];	
			ArrayList<String[]> arrSamples = new ArrayList<String[]>();
			arrSamples.clear();
			for(int d=1; d<=7; d++) {
				StringBuilder sb = new StringBuilder();
				sb.append(baseDir).append("U").append(u).append("-").append("D").append(d).append("/").append(templateName);				
				String templatesPath = sb.toString();
				//arrSamples.clear();
				for(int t=1; t<=10; t++) {
					String[] arr  = new String[8];
					for(int g=1; g<=8; g++) {
						StringBuilder sb1 = new StringBuilder();
						sb1.append(templatesPath).append(g).append("-").append(t).append(".txt");
						arr[g-1] = sb1.toString();
					}
					arrSamples.add(arr);					
				}
								
			}
			System.out.println(arrSamples.size());
			reportlByUser = sumMatrixs(reportlByUser,testByUser(arrSamples));
			reportlFinal = sumMatrixs(reportlFinal, addToFinalReport(reportlByUser));			
			for(int t=0; t<8; t++) {
				for(int v=0; v<8; v++) {
					System.out.print("  " + roundToDecimals((reportlByUser[t][v]/4900)*100,2));
				}
				System.out.println("\n");
			}
			System.out.println("  ==============================================  ");
			reportlByUser = resetValues(reportlByUser);
		}
		for(int t=0; t<8; t++) {
			for(int v=0; v<8; v++) {
				System.out.print("  " + roundToDecimals(reportlFinal[t][v]/8,2));
			}
			System.out.println("\n");
		}
		System.out.println("  ==============================================  ");
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
