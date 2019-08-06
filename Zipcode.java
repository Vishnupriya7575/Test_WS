import java.util.Arrays;
import java.util.Comparator;

/**
 * 
 * @author Priya
 * 
 *
 */
public class Zipcode {

	    public static void main(String[] args) {


	    	/*
	        int[][] zipRanges1 = new int[][] {  new int[] { 94133, 94133 },
	                                            new int[] { 94226, 94399 },
	                                            new int[] { 94200, 94299 }

	                                        };
	    	 */

	    	
	        int[][] zipRanges1 = new int[][] {  new int[] { 11, 25 },
	                                            new int[] { 2, 6 },
	                                            new int[] { 3, 19 },
	                                            new int[] {8,34},
	                                            new int[] {2,12}

	                                        };
	    	 


	        Zipcode minMaxRange = new Zipcode();

	        int[][] normalizedZipRanges = minMaxRange.getTheUnionOfZipRanges(zipRanges1);

	        System.out.println("Normalized range:");
	        minMaxRange.printTheZipRanges(normalizedZipRanges);


	    }
	    
	    
	    private int[][] getTheUnionOfZipRanges(int[][] zipRanges)
	    {
	    	//sort the zipcode range list based on the lower bound in each range
	        this.sortZipCodeRangeArray(zipRanges);
	        
	        boolean zipRangeModified = true;
	        
	        int[][] newZipRanges = null;
	        int[][] originalZipRange = zipRanges;
	        
	        
	        while(zipRangeModified){
	        	
	        	newZipRanges = this.getTheNormalizedZipRanges(originalZipRange);
	        	
	        	int originalRangeLength = getLength(originalZipRange);
	        	int newRangeLength = getLength(newZipRanges);
	        	
	        	if(originalRangeLength != newRangeLength){
	        		
	        		zipRangeModified = true;
	        		
	        		originalZipRange = newZipRanges;
	        		
	        	}else{
	        		zipRangeModified = false;
	        	}
	        	
	        }
	        
	        
	        
	        return newZipRanges;
	    }
	    

	    /**
	     * Method to normalize a given zipcode range.
	     * 
	     * 
	     * @param zipRangesToNormalize
	     * @return
	     */
	    private int[][]  getTheNormalizedZipRanges(int[][] zipRangesToNormalize) {

	    	

	        //create an array to hold the new normalized zipcode range list.

	        int[][] newZipRanges = new int[zipRangesToNormalize.length][];

	        //this array will keep track of which ranges are removed from the original list
	        boolean[] rangePurged = new boolean[zipRangesToNormalize.length];

	        //variable to hold the index for the newZipRange array
	        int newZipIndex = 0;


	        //loop through the given zipcode range array
	        for (int i = 0; i < zipRangesToNormalize.length; i++) {

	            /*
	             * if the range at the current position is merged/combined into the previous range,
	             * skip to the next one in the list
	             */

	            if (rangePurged[i])
	                continue;


	            /*
	             * If we are at the last range in the list, then 
	             * just add the range to the new list 
	             */
	            if (i == (zipRangesToNormalize.length - 1) ){
	                newZipRanges[newZipIndex] = zipRangesToNormalize[i];
	                break;
	            }


	            /*
	             * Logic to normalize the zipcode range
	             * 
	             * Take the upperbound from the current range and compare it with
	             * the lowerbound from the next element. 
	             * 
	             * If the upperbound of the current range is lower than the lowerbound of the
	             * next range, then there are no overlaps. Add the current range to the new list and continue.
	             * 
	             * If the upperbound of the current range is equal are greater than the lowerbound of the 
	             * next range, then there are two possible options,
	             *    1). if the upperbound of the current rage is greater or equal to the upperbound of the next range,
	             *        then create a new range with the lower bound = lowerbound of the current range and upperbound = upperbound of current range
	             *    2). Otherwise, create a new range with lower bound = lowerbound of the current range and upperbound = upperbound of the next range    
	             * 
	             */


	            int minZip = zipRangesToNormalize[i][0];
	            int maxZip = zipRangesToNormalize[i][1];

	            int nextMinZip = zipRangesToNormalize[i + 1][0];
	            int nextMaxZip = zipRangesToNormalize[i + 1][1];

	            if (maxZip >= nextMinZip) {

	                if (maxZip >= nextMaxZip)
	                    newZipRanges[newZipIndex] = new int[] { minZip, maxZip };
	                else
	                    newZipRanges[newZipIndex] = new int[] { minZip, nextMaxZip };

	                //keep track of the ranges that are purged from the original list.

	                rangePurged[i+1] = true;

	            } else {

	                //if there is no overlap, add the current range to the new list

	                newZipRanges[newZipIndex] = zipRangesToNormalize[i];
	            }

	            newZipIndex++;

	        }

	        return Arrays.copyOf(newZipRanges, getLength(newZipRanges));

	    }

	    /**
	     * method to print the zipcode range to the console
	     * 
	     * @param zipRangesToPrint A 2d array representing the zipcode ranges to print
	     */

	    private void printTheZipRanges(int[][] zipRangesToPrint){

	        if(zipRangesToPrint == null || zipRangesToPrint.length <=0)
	            throw new IllegalArgumentException("zipRangesToPrint is empty or null");

	        for (int i = 0; i < zipRangesToPrint.length; i++) {


	            //I'm checking for null condition here because of the way I've created this array. 
	            //I've initially assigned it the same length as the 


	            if (zipRangesToPrint[i] != null)
	            {   

	                System.out.println("["+ zipRangesToPrint[i][0] + ", "
	                        + zipRangesToPrint[i][1]+"]");
	            }
	        }
	    }


	    /**
	     * Method to sort the zipcode range list based on the lower bound.
	     * 
	     * @param zipCodeRangesToSort   A 2d integer array representing the zipcode ranges that need to be sorted
	     * @return                      
	     */
	    private void sortZipCodeRangeArray(int[][] zipCodeRangesToSort){

	        Arrays.sort(zipCodeRangesToSort, new Comparator<int[]>() {

	            @Override
	            public int compare(int[] range1, int[] range2) {

	                int range1Min = range1[0];
	                int range2Min = range2[0];

	                if (range1Min > range2Min)
	                    return 1;

	                if (range1Min < range2Min)
	                    return -1;

	                return 0;
	            }

	        });
	    }
	    
	    private <T> int getLength(T[] arr){
	        int count = 0;
	        for(T el : arr)
	            if (el != null)
	                ++count;
	        return count;
	    }
	}
