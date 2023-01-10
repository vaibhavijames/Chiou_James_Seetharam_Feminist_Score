import java.util.*;
import java.io.*;
import javafoundations.PriorityQueue;

/**
 * Represents a collection of Movie objects. The class also runs tests according to the different feminist movie tests and ranks
 * them using a Priority Queue. 
 *
 * @author Vaibhavi James, Akshaya Seetharam, Hannah Chiou
 * @version 15 Dec 2022
 */
public class MovieCollection
{
    private LinkedList<Actor> allActors;
    private LinkedList<Movie> allMovies;
    private String testsFileName;
    private String castsFileName;

    /**
     * Constructor for objects of class MovieCollection.
     * 
     * @param testsFileName The name of the file containing the data on the 12 tests
     * @param castsFileName The name of the file containig the data on the casts of movies
     */
    public MovieCollection(String testsFileName, String castsFileName)
    {
        allActors = new LinkedList<Actor>();
        allMovies = new LinkedList<Movie>();
        this.testsFileName = testsFileName;
        this.castsFileName = castsFileName;
    }

    /**
     * Returns the Linked List containing all of the actors in the MovieCollection.
     * 
     * @return A Linked List of all the actors in a MovieCollection.
     */
    public LinkedList<Actor> getActors()
    {
        return allActors;
    }

    /**
     * Returns the Linked List containing all of the movies in the MovieCollection.
     * 
     * @return A Linked List of all the movies in a MovieCollection.
     */
    public LinkedList<Movie> getMovies()
    {
        return allMovies;
    }

    /**
     * Returns the Linked List containing all of the actors' names in the MovieCollection.
     * 
     * @return A Linked List of all the actors' names.
     */
    public LinkedList<String> getActorNames()
    {
        LinkedList<String> names = new LinkedList<String>();
        for(int i=0; i<allActors.size(); i++)
        {
            names.add(allActors.get(i).getName());
        }
        return names;
    }

    /**
     * Returns the Linked List containing all of the movie titles in the MovieCollection.
     * 
     * @return A Linked List of all the movies' titles.
     */
    public LinkedList<String> getMovieTitles()
    {
        LinkedList<String> names = new LinkedList<String>();
        for(int i=0; i<allMovies.size(); i++)
        {
            names.add(allMovies.get(i).getTitle());
        }
        return names;
    }

    /**
     * Reads from a file containing movie titles and test results and populates the appropriate data structures for the movie's test results
     * and for movie objects. 
     */
    private void readMovies()
    {
        try {
            Scanner reader = new Scanner(new File(testsFileName));
            // skip first line, which is the header of the file
            String skip = reader.nextLine();

            // now reading into file
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] split = line.split(",");

                // creating new Movie object based on movie title
                Movie movie = new Movie(split[0]);

                // iterating through test results in file to add to test results for the movie
                for(int i=1; i<split.length; i++)
                {
                    movie.setTestResults(split[i]);
                }
                allMovies.add(movie);
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error checking lines from "+ testsFileName);
        }
    }

    /**
     * Reads from a filename containing information about a movie's cast to add to the allActors Linked List. The method also keeps track of the
     * number of actors for a movie, if it is already in the allMovies list. 
     */
    private void readCasts()
    {
        try {
            Scanner reader = new Scanner(new File(castsFileName));
            // skip first line, which is the header of the file
            String skip = reader.nextLine();

            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] split = line.split(",");

                // creating new Actor object
                Actor actor = new Actor(split[1].replace("\"",""),split[5].replace("\"",""));

                allActors.add(actor);

                // getting title of movie, to check if it is in the allMovies list
                String title = split[0].replace("\"","");
                for (int i = 0; i < allMovies.size(); i ++){
                    // if the movie titles are equal (movie IS in allMovies), adds the actor to the Movie object
                    if (allMovies.get(i).getTitle().equals(title)){ 
                        allMovies.get(i).addOneActor(line); 
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error checking lines from "+ castsFileName);
        }
    }

    /**
     * Creates a Priority Queue with the most feminist movies enqueued first.
     * 
     * @return A Priority Queue with the most feminist movies enqueued first
     */
    public PriorityQueue<Movie> rankMovies()
    {
        PriorityQueue<Movie> queue = new PriorityQueue<Movie>();

        for(int i=allMovies.size()-1; i>0; i--)
        {
            allMovies.get(i).feministScore();
            queue.enqueue(allMovies.get(i));
        }

        return queue;
    }

    /**
     * Returns a Linked List of movies that have passed a specific test, with the test number being specified according to the header
     * of the tests files (ie. Bechdel is 1)
     * 
     * @param int The integer of the test, according to the header 
     */
    public LinkedList<Movie> findAllMoviesPassedTestNum(int n)
    {
        LinkedList<Movie> passedTest = new LinkedList<Movie>(); 
        try {
            Scanner reader = new Scanner(new File(testsFileName));

            String line = reader.nextLine(); 

            while (reader.hasNextLine()) {
                String line2 = reader.nextLine();
                String[] split = line2.split(",");
                if (Integer.parseInt(split[n]) == 0){
                    Movie passes = new Movie(split[0]); 
                    passedTest.add(passes); 
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error checking lines from "+ testsFileName);
        }
        return passedTest; 
    }

    /**
     * Returns a Linked List of all movies that pass the Bechdel test.
     * 
     * @return A Linked List of all movies that pass the Bechdel test.
     */
    public LinkedList<Movie> passBechdel(){
        return findAllMoviesPassedTestNum(1); 
    }

    /**
     * Returns a Linked List of all movies that passed either the Peirce OR Landau tests. 
     * 
     * @return A Linked List of all movies that passed either the Peirce OR Landau tests
     */
    public LinkedList<Movie> passPeirceOrLandau(){
        LinkedList<Movie> passedTest = new LinkedList<Movie>(); 
        try {
            Scanner reader = new Scanner(new File(testsFileName));

            // skipping first line aka header
            String line = reader.nextLine(); 

            // now reading into movie information in the file
            while (reader.hasNextLine()) {
                String line2 = reader.nextLine();
                String[] split = line2.split(",");
                if (Integer.parseInt(split[2]) == 0 || 
                Integer.parseInt(split[3]) == 0){ 
                    Movie passes = new Movie(split[0]); 
                    passedTest.add(passes); 
                }
            }
            reader.close();
        } 
        catch (IOException e) {
            System.out.println("Error checking lines from "+ testsFileName);
        }
        return passedTest; 
    }

    /**
     * Returns a Linked List of movies that passed the White test but NOT the Rees-Davies test. 
     *      
     * @return A Linked List of movies that passed the White test but NOT the Rees-Davies test
     */
    public LinkedList<Movie> passWhiteNotRD(){
        LinkedList<Movie> passedTest = new LinkedList<Movie>(); 
        try {
            Scanner reader = new Scanner(new File(testsFileName));

            // skipping first line aka header
            String line = reader.nextLine(); 

            // now reading into movie information in the file
            while (reader.hasNextLine()) {
                String line2 = reader.nextLine();
                String[] split = line2.split(",");
                if (Integer.parseInt(split[12]) == 0 &&
                Integer.parseInt(split[13]) == 1){ 
                    Movie passes = new Movie(split[0]); 
                    passedTest.add(passes); 
                }
            }
            reader.close();
        } 
        catch (IOException e) {
            System.out.println("Error checking lines from "+ testsFileName);
        }
        return passedTest; 
    }

    /**
     * Returns a String representation of a MovieCollection object.
     * 
     * @return A string representing MovieCollection
     */
    public String toString(){
        String s = "This movie collection contains " + allMovies.size() + " movies: \n";
        for (int i = 1; i < allMovies.size(); i++){
            s += allMovies.get(i) + "\n"; 
        }
        return s; 
    }

    public static void main(String[] args)
    {
        System.out.println("*=========TESTING MOVIECOLLECTION=========*"); 
        System.out.println("Creating new MovieCollection using nextBechdel_allTests and nextBechdel_castGender." + "\n"); 
        System.out.println("Testing readMovies and toString: \n"); 

        MovieCollection m1 = new MovieCollection("nextBechdel_allTests.txt", 
                "nextBechdel_castGender.txt"); 
        m1.readMovies();  
        System.out.println(m1 + "\n"); 

        System.out.println("Testing readCasts and toString: \n"); 

        m1.readCasts(); 
        System.out.println(m1 + "\n"); 

        System.out.println("Testing getActors and getActorNames: ");
        System.out.println("getActors: " + m1.getActors() + "\n");
        System.out.println("getActorNames: " + m1.getActorNames() + "\n"); 

        System.out.println("Testing getMovies and getMovieTitles: "); 
        System.out.println("getMovies: " + m1.getMovies() + "\n");
        System.out.println("getMovieTitles: " + m1.getMovieTitles() + "\n"); 

        System.out.println("Testing findAllMoviesPassedTestNum using Feldman (4): "); 
        LinkedList<Movie> moviesPassingFeldman = m1.findAllMoviesPassedTestNum(4); 
        System.out.println("The following " + moviesPassingFeldman.size() + " movies passed the Feldman Test: "); 
        for (int i=0; i<moviesPassingFeldman.size(); i++){
            System.out.println(moviesPassingFeldman.get(i).getTitle());
        }        

        System.out.print("\n"+ "Testing passBechdel() => "); 
        LinkedList<Movie> moviesPassingBechdel = m1.passBechdel(); 
        System.out.println("The following " + moviesPassingBechdel.size() + " movies passed the Bechdel Test: "); 
        for (int i=0; i<moviesPassingBechdel.size(); i++){
            System.out.println(moviesPassingBechdel.get(i).getTitle());
        }

        System.out.print("\n"+ "Testing passWhiteNotRD() => "); 
        LinkedList<Movie> moviesPassingWhiteNotRD = m1.passWhiteNotRD(); 
        System.out.println("The following " + moviesPassingWhiteNotRD.size() + " movies passed the White Test, but did *not* pass the Rees-Davies test: "); 
        for (int i=0; i<moviesPassingWhiteNotRD.size(); i++){
            System.out.println(moviesPassingWhiteNotRD.get(i).getTitle());
        }

        System.out.print("\n"+ "Testing passPeirceOrLandau() => "); 
        LinkedList<Movie> moviesPassingPeirceOrLandau = m1.passPeirceOrLandau(); 
        System.out.println("The following " + moviesPassingPeirceOrLandau.size() + " movies either passed the Peirce Test or the Landau test: "); 
        for (int i=0; i<moviesPassingPeirceOrLandau.size(); i++){
            System.out.println(moviesPassingPeirceOrLandau.get(i).getTitle());
        }

        System.out.println("\n"+"Testing rankMovies (most feminist movies first): "); 
        PriorityQueue<Movie> queue = m1.rankMovies();
        int size = queue.size();
        for(int i=0; i<size; i++)
        {
            Movie m = queue.dequeue(); 
            System.out.println(m.getTitle() + " score: " + m.getFeministScore()); 
        }
    }
}
