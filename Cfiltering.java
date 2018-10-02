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
package a0;

//import arraylists. I'm gonna use them three tmes
import java.util.ArrayList;

public class Cfiltering {
	// this is a 2d matrix i.e. user*movie
	private int userMovieMatrix[][];
	// this is a 2d matrix i.e. user*movie
	private float userUserMatrix[][];

	/**
	 * Default Constructor.
	 */
	public Cfiltering() {
		// this is 2d matrix of size 1*1
		userMovieMatrix = new int[1][1];
		// this is 2d matrix of size 1*1
		userUserMatrix = new float[1][1];
	}
	/**
	 * constructor given numberOfUsers and numberOfMoives 
	 * constructing matrices of appropriate sizes 
	 * @param numberOfUsers
	 * @param numberOfMovies
	 */
	public Cfiltering(int numberOfUsers, int numberOfMovies) {
		/*
		 * two new matrixes with width users and depth movies one to compare
		 * movies to users and one to compare users to other users
		 */
		userMovieMatrix = new int[numberOfUsers][numberOfMovies];
		userUserMatrix = new float[numberOfUsers][numberOfUsers];

	}

	/**
	 * a function to populate the UserMovie matrix, given the 
	 * appropriate inputs
	 * 
	 * @param int rowNumber, int columnNumber, int ratingValue
	 */
	public void populateUserMovieMatrix(int rowNumber, int columnNumber,
			int ratingValue) {

		// assign the co-ordinate to the appropriate rating value
		userMovieMatrix[rowNumber][columnNumber] = ratingValue;
	}

	public void calculateSimilarityScore()
	// new variable to store the difference in score between any two users
	{
		double userUserScore = 0;
		// loop through each of the users
		for (int userOneIndex = 0; userOneIndex < 
			userMovieMatrix.length; userOneIndex++) {
			// reset the variable for a new pair
			userUserScore = 0;
			/*
			 * loop through every user we want to compare them to subtracting
			 * the first user's index because we don't want to compare the same
			 * users twice
			 */
			for (int userTwoIndex = 1; userTwoIndex < userMovieMatrix.length
					- userOneIndex; userTwoIndex++) {
				/*
				 * For every pair of users we compare, we need to check each of
				 * the movies they watched
				 */
				userUserScore = 0;
				for (int currentMovie = 0; currentMovie < 
				userMovieMatrix[userTwoIndex].length; currentMovie++) {
				/*
				 * variables for the current movie we're looking at and the
				 * next movie we want to compare it to
				*/
					int currentRating = userMovieMatrix[userOneIndex]
					[currentMovie];
					int nextRating = userMovieMatrix[userTwoIndex
					+ userOneIndex][currentMovie];
					/*
					 * take the differences of the ratings,square 
					 * them, and add
					 * them to the total userUserScure
					 * 
					 */
					float scoreDifference = currentRating - nextRating;
					scoreDifference = scoreDifference * scoreDifference;
					userUserScore += scoreDifference;

				}
				//take the sqrt of the total
				userUserScore = Math.sqrt(userUserScore);
				//reciprocal and add 1
				userUserScore = 1 / (userUserScore + 1);
				// round it to 4 decimal places in the array.
				userUserScore = Float
						.valueOf(String.format("%.4g%n", userUserScore));


				/*
				 * add the score at coords i,j to i,j and j,i in the userUser
				 * matrix (because the userUser score between (x,y) 
				 * will be the
				 * same as the score between (y,x)
				 */
				userUserMatrix[userOneIndex][userOneIndex+userTwoIndex]
				= userUserMatrix[userOneIndex+userTwoIndex][userOneIndex] 
				=  (float) userUserScore;

			}
		}
		/*
		 * manually fill in the diagonals to be 1(because the 
		 * score between any
		 * user and themselves will be 1)
		 */
		for (int i = 0; i < userUserMatrix.length; i++) 
		{
			userUserMatrix[i][i] = Float.valueOf("1.0000");
		}

	}

	/**
	 * Prints a representation of the userUser matrix
	 */
	public void printUserUserMatrix() {
		/*
		 * add two blanklines followed by the initial comment and another
		 * blankline
		 */
		String output = "\n\nuserUserMatrix is:\n";
		// loop through the rows
		for (int i = 0; i < userUserMatrix.length; i++) {
			// at the beginning of each row, add an open bracket
			output += "[";
			for (int j = 0; j < userUserMatrix[i].length; j++) {
				// add the current score formatted to 4 decimal places
				output += String.format("%.4f", (userUserMatrix[i][j]));
				/*
				 * if we're NOT at the last element of a row, we need to add a
				 * comma followed by a space
				 */
				if (j != userUserMatrix[i].length - 1) {
					output += ", ";
				}

			}
			/* at the end of each row, add a close bracket
			 *  followed by a newline 
			 */
			output += "]\n";
		}
		// print all the output we've stored
		System.out.printf(output);

	}

	/**
	 * This function finds and prints the most similar pair of users in the
	 * userUserMatrix.
	 */

	public void findAndprintMostSimilarPairOfUsers() {
		//empty string for the output
		String output = "";
		/*initialize the highest score to be -1(such that 
		 * the first score encountered will always be higher
		 */
		
		double highestScore = -1;
		/*
		 * An arraylist to store the values that have the same similarity 
		 * score(basically so that we don't print x,y has the same 
		 * similarity score as y,x
		 */
		ArrayList<String> countedSimilarites = new ArrayList<String>();
		//loop through every user
		for (int i = 0; i < userUserMatrix.length; i++) {
			//loop again to compare them to every other user
			for (int j = 0; j < userUserMatrix[i].length; j++) {
				/*
				 * As long as they arent the same user(because someone and
				 * themselves will always have a 1.000 similarityscore
				 * Similarly, we need to check that we're not counting 
				 * the same users twice
				 */
				if (i != j && !countedSimilarites
						.contains(Integer.toString(i) + Integer.toString(j))) {
					if (userUserMatrix[i][j] > highestScore) {
						/*if we have a new highest score, reset the output
						 * variable and make new output. Also we're using
						 * i and j +1 because the 0th index in the array
						 * corresponds to user 1, not user 0
						 */
						
						output = "\n\nThe most similar pairs of users from "
						+ "above userUserMatrix are:\nUser"
						+ Integer.toString(i + 1) + " and User"
						+ Integer.toString(j + 1);
						//update highest score
						highestScore = userUserMatrix[i][j];
					  /*
					   * If we have an identical highestscore between
					   * two other users, add to the output instead of
					   * resestting it
					   */
					} else if (userUserMatrix[i][j] == highestScore) {
						output += ",\nUser" + Integer.toString(i + 1)
								+ " and User" + Integer.toString(j + 1);
					}
					//append to the array of users we've already counted
					countedSimilarites
							.add(Integer.toString(j) + Integer.toString(i));

				}
			}
		}
		/*finally, append to the similarity score
		 * formatted to 4 decimals
		 */
		output += "\nwith similarity score of ";
		output += String.format("%.4f", highestScore);
		// print the output and a newline
		System.out.printf(output );

	}
	/**
	 * This function finds and prints the least similar pair of users in the
	 * userUserMatrix.
	 */
	public void findAndprintMostDissimilarPairOfUsers() {
		//empty string for the output
		String output = "";
		/*initialize the lowest score to be 2(such that 
		 * the first score encountered will always be lower
		 */
		double lowestScore = 2;
		/*
		 * An arraylist to store the values that have the same similarity 
		 * score(basically so that we don't print x,y has the same 
		 * similarity score as y,x
		 */
		ArrayList<String> countedSimilarites = new ArrayList<String>();
		//loop through every user
		for (int i = 0; i < userUserMatrix.length; i++) {
			//loop again to compare them to every other user
			for (int j = 0; j < userUserMatrix[i].length; j++) {
				/*
				 * As long as they arent the same user(because someone and
				 * themselves will always have a 1.000 similarityscore
				 * Similarly, we need to check that we're not counting 
				 * the same users twice
				 */
				if (i != j && !countedSimilarites
						.contains(Integer.toString(i) + Integer.toString(j))) {
					/*if we have a new lowest score, reset the output
					 * variable and make new output. Also we're using
					 * i and j +1 because the 0th index in the array
					 * corresponds to user 1, not user 0
					 */
					if (userUserMatrix[i][j] < lowestScore) {
						/*if we have a new highest score, reset the output
						 * variable and make new output. Also we're using
						 * i and j +1 because the 0th index in the array
						 * corresponds to user 1, not user 0
						 */
						output = "\n\n\nThe most dissimilar pairs of users "
						+ "from above userUserMatrix are:\nUser"
						+ Integer.toString(i + 1) + " and User"
						+ Integer.toString(j + 1);
						lowestScore = userUserMatrix[i][j];
					 /*
					  * If we have an identical lowest score between
				      * two other users, add to the output instead of
					  * resestting it
					  */
					} else if (userUserMatrix[i][j] == lowestScore) {
						output += ",\nUser" + Integer.toString(i + 1)
								+ " and User" + Integer.toString(j + 1);
					}
					//append to the array of users we've already counted
					countedSimilarites
							.add(Integer.toString(j) + Integer.toString(i));

				}
			}
		}
		/*finally, append to the similarity score
		 * formatted to 4 decimals
		 */
		output += "\nwith similarity score of ";
		output += String.format("%.4f", lowestScore);
		// print the output and a newline
		System.out.printf(output);
	}

}
