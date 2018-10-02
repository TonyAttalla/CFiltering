// **********************************************************
// Assignment0:
// UTORID: 1003974158
// UT Student #:1003974158
// Author: Tony Attalla
//
//
// Honor Code: I pledge that this program represents my own
// program code and that I have coded on my own. I received
// help from no one in designing and debugging my program.
// I have also read the plagiarism section in the course info
// sheet of CSC B07 and understand the consequences. In this semester
// we will select any three of your assignments from total of 5 and run it
// for plagiarism check. 
// *********************************************************
package driver;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import a0.Cfiltering;

public class CfilteringDriver {

  
  public static void main(String[] args) {
    try {

      // open file to read
      String fileName;
      Scanner in = new Scanner(System.in);
      System.out.println("Enter the name of input file? ");
      fileName = in.nextLine();
      FileInputStream fStream = new FileInputStream(fileName);
      BufferedReader br = new BufferedReader(new InputStreamReader(fStream));

      // Read dimensions: number of users and number of movies
      int numberOfUsers = Integer.parseInt(br.readLine());
      int numberOfMovies = Integer.parseInt(br.readLine());
      

      
      Cfiltering cfObject = new Cfiltering(numberOfUsers, numberOfMovies);

      // this is a blankline being read
      br.readLine();

      // read each line of movie ratings and populate the
      // userMovieMatrix
      String row;
      //initialize variables for the current row and column we're looking at
      int currColumn =0;
      int currRow = 0 ;
      while ((row = br.readLine()) != null) {
        // allRatings is a list of all String numbers on one row
        String allRatings[] = row.split(" ");
        for (String singleRating : allRatings) 
        {
            //convert the individual ratings of movies to an integer
        	int intRating = Integer.parseInt(singleRating);
          // make the String number into an integer
          // populate userMovieMatrix
        	cfObject.populateUserMovieMatrix(currRow, currColumn, intRating);
        	currColumn++;
         
       
        }
        //increment row
        currRow++;
        //reset column back to 0
        currColumn=0;
      }
     
     
      fStream.close();

    
      //CALCULATE THE SIMILARITY SCORE BETWEEN USERS.
      cfObject.calculateSimilarityScore();
      
      // PRINT OUT THE userUserMatrix
      cfObject.printUserUserMatrix();
      // TODO:3.) PRINT OUT THE MOST SIMILAR PAIRS OF USER AND THE MOST
      // DISSIMILAR
      cfObject.findAndprintMostSimilarPairOfUsers();
      // PAIR OF USERS.
      cfObject.findAndprintMostDissimilarPairOfUsers();
      //error handling
    } catch (FileNotFoundException e) {
      System.err.println("Do you have the input file in the root folder "
          + "of your project?");
      System.err.print(e.getMessage());
    } catch (IOException e) {
      System.err.print(e.getMessage());
    }
  }

}
