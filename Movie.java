import java.util.*;
import java.io.*;

/**
 * Represents an object of type Movie.
 * A Movie object has a title, some Actors, and results for the twelve Bechdel tests.
 *
 * @author (Stella K.), Akshaya Seetharam, Vaibhavi James, Hannah Chiou
 * @version 15 Dec 2022
 */
public class Movie implements Comparable<Movie>
{
    //instance variables
    private String title;
    private Hashtable<Actor, String> allActors;
    private Vector<String> testResults;
    private double feministScore;

    /**
     * Constructor for objects of class Movie
     */
    public Movie(String title){
        this.title = title;
        allActors = new Hashtable<Actor, String>(20);
        testResults = new Vector<String>();
    }

    /**
     * Reads the input file ("nextBechdel_castGender.txt"), and adds all its Actors to this movie.
     * 
     * @param actorsFile - The file containing information on each actor who acted in the movie.
     */
    public void addAllActors(String actorsFile){
        try {
            // set up reading the words from the input actorsFile
            Scanner reader = new Scanner(new File(actorsFile));
            // process words from the textfile, one by one
            while (reader.hasNextLine()) {
                String line = reader.nextLine();

                String[] split = line.split(",");

                if(split[0].substring(1,split[0].length()-1).equals(title))
                {
                    addOneActor(line);
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error checking lines from "+ actorsFile);
        }
    }

    /**
     * Takes in a String, formatted as lines are in the input file ("nextBechdel_castGender.txt"), 
     * generates an Actor, and adds the object to the actors of this movie. 
     * 
     * @param line - A String representing the information of each Actor
     * @return The Actor that was just added to this movie
     */
    public Actor addOneActor(String line){
        String[] actorArray  = line.split(",");

        Actor newActor = new Actor(actorArray[1],actorArray[5]);
        allActors.put(newActor, actorArray[3]);
        return newActor;
    }

    /**
     * Tests this movie object with the input one and determines whether they are equal.
     * 
     * @return true if both objects are movies and have the same title, 
     * false in any other case.
     */
    public boolean equals(Object other) {
        if (other instanceof Movie) {
            return this.title.equals(((Movie) other).title); // Need explicit (Movie) cast to use .title
        } else {
            return false;
        }
    }

    /**
     * Returns a Linked List with all the actor names who played in this movie.
     * 
     * @return A LinkedList with the names of all the actors who played in this movie
     */
    public LinkedList getActors(){
        LinkedList<String> actorLinkedList = new LinkedList<String>();

        Enumeration<Actor> e = allActors.keys();
        while( e.hasMoreElements()) {
            Actor anActor = e.nextElement();
            String info = allActors.get(anActor);
            actorLinkedList.add(anActor.getName());
        }
        return actorLinkedList;
    }

    /**
     * Returns the movie's actors in a Hashtable
     * 
     * @return A Hashtable with all the actors who played in this movie
     */
    public Hashtable getAllActors(){
        return allActors;
    }

    /**
     * Returns the movie's title
     * 
     * @return The title of this movie
     */
    public String getTitle(){
        return this.title;
    }
    
    public double getFeministScore()
    {
        return this.feministScore;
    }

    /**
     * populates the testResults vector with "0" and "1"s, each representing the result of the 
     * coresponding test on this movie
     * 
     * @param results - A string consisting of of 0's and 1's. Each one of these values denotes the 
     * result of the corresponding test on this movie
     */
    public void setTestResults(String results){
        String[] testResultArray = results.split(",");
        for (int i = 0; i < testResultArray.length; i++){
            testResults.add(testResultArray[i]);
        }
    }

    /**
     * Returns a Vector with all the Bechdel test results for this movie
     * 
     * @return A Hashtable with all the actors who played in this movieA Vector with the Bechdel test 
     * results for this movie: A test result can be "1" or "0" indicating that this move passed or 
     * did not pass the corresponding test.
     */
    public Vector<String> getAllTestResults(){
        return testResults;
    }

    /**
     * Returns a string representation of this movie
     * 
     * @return a reasonable string representation of this movie: includes the title and the 
     * number of actors who played in it.
     */
    public String toString(){
        return "Title: " + this.title + " , "+ "Number of actors: " + allActors.size();
    }
    
    /**
     * Calculates the feminist score of this movie
     */
    public double feministScore()
    {
        feministScore = (Double.parseDouble(testResults.elementAt(3)) + Double.parseDouble(testResults.elementAt(4)) + 
        Double.parseDouble(testResults.elementAt(6)))/3;
        
        return feministScore;
    }
    
    /**
     * Compares two movie objects based on their feminist scores
     * 
     * @param   Movie object for comparison
     * @return  int representing how the two movies compare
     */
    public int compareTo(Movie other)
    {
       if(this.feministScore > other.getFeministScore())
       return -1;
       
       else if(this.feministScore < other.getFeministScore())
       return 1;
       
       else{
           return -(this.getTitle().compareTo(other.getTitle())); 
       }
    }

    public static void main (String[] args){
        //testing movie Alpha
        Movie alpha = new Movie("Alpha");
        alpha.addAllActors("small_castGender.txt");
        System.out.println("Getting the movie title: " + alpha.getTitle());
        System.out.println("Getting the actors: " + alpha.getActors() +"\n");
        System.out.println(alpha.toString() +"\n");
        System.out.println(alpha.getAllActors() +"\n");
        alpha.setTestResults("0,0,0,1,0,0,0,1,0,0,1,1,1");
        System.out.println(alpha.getAllTestResults() +"\n");

        System.out.println();

        //testing movie Beta
        Movie beta = new Movie("Beta");
        beta.addAllActors("small_castGender.txt");
        System.out.println(beta.toString() +"\n");
        System.out.println(beta.getActors() +"\n");
        System.out.println(beta.getAllActors() +"\n");
        beta.setTestResults("0,0,0,1,0,0,0,1,0,0,1,1,1");
        System.out.println(beta.getAllTestResults() +"\n");

        System.out.println();

        //testing movie Gamma
        Movie gamma = new Movie("Gamma");
        gamma.addAllActors("small_castGender.txt");
        System.out.println(gamma.toString() +"\n");
        System.out.println(gamma.getActors() +"\n");
        System.out.println(gamma.getAllActors() +"\n");
        gamma.setTestResults("0,0,0,1,0,0,0,1,0,0,1,1,1");
        System.out.println(gamma.getAllTestResults() +"\n");

    }

}
