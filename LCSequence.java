package theLCS;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class LCSequence {
	/**This package finds the LCS through comparisons 
	 * of all sequences within a given input file.
	 * It then prints the LCS, the length of the LCS and pairwise comparisons to an output file.
	 * @author Anam Ahsan
	 * @param sourceFilepath is the file to be read
	 * @param destinationFilepath is the file to be written into
	 */
   
   public static int z = 0; //Global variable to count number of comparisons 
   public static int y = 0; //Global variable to count number of individual comparisons 
   
	public static void main(String[] args) throws IOException {
           
		//Command line arguments for user to enter a source and destination file path
      String sourceFilePath = args[0]; 
      String destinationFilepath = args[1];
     			   	 
      BufferedReader reader; //Initializes the reader
     	FileWriter outputFile = new FileWriter(destinationFilepath); //Initializing output file
     	//FileWriter outputFile = new FileWriter("C:\\Users\\Ahsan\\Desktop\\Algorithms2022\\Program 3\\RESULT.txt");
     	
     	try {
     	   reader = new BufferedReader(new FileReader(sourceFilePath)); //Initializing file to be read
    	   //reader = new BufferedReader(new FileReader("C:\\Users\\Ahsan\\Desktop\\Algorithms2022\\Program 3\\DynamicLab2Input.txt"));
    	   String line = reader.readLine(); //Read each line of file
    	   String [][] seq = new String [100][2]; //Creates an array to store strings within a file, in this program it can handle 100 strings only 
       	int i = -1; //initialize variable to parse input strings
       	
    	   while (line != null) {    	          	      
    	      // Parse string and store each sequence into array
    	      // Get and store length of each sequence into array 
       	   int start = line.indexOf("=") + 2;
       	   int length = line.length() - start;
       	   String l = Integer.toString(length);
       	   i++;
       	   
            if (line.substring(start).matches(".*\\d.*")) {
               FileWriter newFile = new FileWriter("C:\\Users\\Jameel Ahsan\\Desktop\\Algorithms2022\\Program 3\\RESULT_ERROR.txt");
               newFile.write("ERROR: Sequence contains integers");
               newFile.close();
               break;
             }
                    	   
       	   System.out.println("S" + (i+1) + ": " + line.substring(start) + " | Length: " + length + "\n");
       	   outputFile.write("S" + (i+1) + ": " + line.substring(start) + " | Length: " + length + "\n");
       	   
       	   seq[i][0] =  line.substring(start);
       	   seq[i][1] = l;       	       	      
       	   line = reader.readLine(); 
    	   }
        
    	  // Iterate through the array, so that all combinations occur, but no repeat combinations
        // Call on 'findLCS' method to get LCS for each pair of string    	   
    	   
    	   int end = i; //variable to indicate end of array
    	   int j = 0;   //variable to iterate through first string
    	   int k = j+1;  //variable to iterate through second string        
    	   
    	   while (i > -1) {    	   
    	      if (k > end) {              
    	         j++; 
               k = j + 1;
    	         i--;
    	      }
    	      
    	      //Print Sequences and calls findLCS method 
    	      else {
    	         System.out.println("\n"); 
    	         System.out.println("Sequence S" + (j+1) + " " + seq[j][0]); 
    	         System.out.println("Sequence S" + (k+1) + " " + seq[k][0]); 
    	         
    	         outputFile.write("\n");
    	         outputFile.write("COMPARISON: " + (z+1) + "\n");
    	         outputFile.write("Sequence S" + (j+1) + ": " + seq[j][0] + "\n"); 
    	         outputFile.write("Sequence S" + (k+1) + ": " + seq[k][0] + "\n"); 
    	         
    	         findLCS(seq[j][0], seq[j][1], seq[k][0], seq[k][1], outputFile);
    	         k++; 
    	      }
    	   }
    	   System.out.println("\n\nThere were " + z + " pairwise comparisons" ); //Print number of pairwise comparisons 
    	   outputFile.write("\n\nThere were " + z + " pairwise comparisons"  + "\n"); //Print number of pairwise comparisons
    	   //Close input reader
         reader.close();
         outputFile.close();
         
     	}
      //This allows exceptions to be caught and traced to the line of code
    	catch (IOException|StringIndexOutOfBoundsException e) {
    	   PrintWriter pw = new PrintWriter(new File("C:\\Users\\Jameel Ahsan\\Desktop\\Algorithms2022\\Program 3\\RESULT_ERROR.txt"));
    	   e.printStackTrace(pw);
    	   pw.close();
      	}   
   	}
	
	
   /*
    * This method iterates through the given strings and fills array according to matches  
    * Another array is filled specifically to identify the LCS
    * It calls the print method when arrays are filled.
  */
   public static void findLCS (String aSeq, String lengthA, String bSeq, String lengthB, FileWriter outputFile) throws IOException {
      
      //Extraction and conversion of string to integers and/or characters; 
      int lenA = Integer.valueOf(lengthA);
      int lenB = Integer.valueOf(lengthB);
      char[] a = aSeq.toCharArray();
      char[] b = bSeq.toCharArray();
      int [][] LCS = new int [lenA + 1][lenB + 1];  //Array to identify LCS specific to lengths of two sequences
      String [][] bLCS = new String [lenA + 1][lenB + 1]; //Array to hold LCS specific to lengths of two sequences
      int i, j;
      
      //Variable to track number of pairwise comparisons
      z++; 
      
      //Variable to count individual comparisons 
     
      
      //Fill arrays through comparisons of each character of sequences 
      //and according to previous comparisons to find LCS 
      
      for (i=1, j=0; (i <= lenA) && (j<=lenB) ;i++, j++) {
         LCS[i][0] = 0;
      }
     
      for (i=1; i <= lenA; i++){
         for(j=1; j<=lenB; j++) {            
           if (a[i-1] == b[j-1]) {
               LCS[i][j] = LCS[i-1][j-1] + 1; 
               bLCS[i][j] = "Y";
               y++;
            }
            else {
               y++;
               int o = Math.max(LCS[i-1][j], LCS[i][j-1]);
               if (o == LCS[i-1][j]){
                 LCS[i][j] = o;
                 bLCS[i][j] = "N";
               }
               else {
                  LCS[i][j] = o;
                  bLCS[i][j] = "Z";  
                  
               }
            }

         } 
      }
      System.out.println("The length of the LCS is: " + LCS[lenA][lenB]); //Print length of LCS
      outputFile.write("The length of the LCS is: " + LCS[lenA][lenB]  + "\n"); //Print length of LCS
      
      //Call function to print the LCS 
      System.out.print("The LCS is: ");
      outputFile.write("The LCS is: ");
      printLCS(bLCS, a, lenA, lenB, outputFile);
      outputFile.write("\nThere were " + y + " individual comparisons");
      y = 0;
      outputFile.write("\n");
   }
   
   /*
    * This method prints out the LCS by recursively calling the method to traverse through
    * the array that holds the LCS from bottom right to top left.
    * It prints using one string of the pair when matches are found.  
  */
   public static void printLCS(String [][] LCS, char [] a, int lenA, int lenB, FileWriter outputFile) throws IOException {
     
      if (lenA == 0 || lenB == 0) {
         return;
      }
      
      if (LCS[lenA][lenB] == "Y"){          
         printLCS(LCS, a, (lenA-1), (lenB-1), outputFile);
         System.out.print(a[lenA-1]);
         outputFile.write(a[lenA-1]);
      }
      
      else if (LCS[lenA][lenB] == "N"){
         printLCS(LCS, a, (lenA-1), (lenB), outputFile);
      }
      
      else {
         printLCS(LCS, a, (lenA), (lenB-1), outputFile);
      }

   }
   
} //end