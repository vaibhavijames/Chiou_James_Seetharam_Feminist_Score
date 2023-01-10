/**
 * Represents an object of type Actor. An Actor has a name and a gender.
 *
 * @author (Stella K.), Akshaya Seetharam, Vaibhavi James, Hannah Chiou
 * @version 15 Dec 2022
 */
public class Actor
{
    //instance variables
    private String name;
    private String gender;

    /**
     * Constructor for objects of class Actor
     */
    public Actor(String name, String gender){
        this.name = name;
        this.gender = gender;
    }
    
    /**
     * Returns the name of this actor
     * 
     * @return The name of this actor
     */
    public String getName(){
        return this.name;
    }
    
    /**
     * Returns the gender of this actor
     * 
     * @return The gender of this actor
     */
    public String getGender(){
        return this.gender;
    }

    /**
     * Sets the gender of this actor
     * 
     * @param g The gender of this actor
     */
    public void setGender(String g){
        this.gender = g;
    }
    
    /**
     * Sets the name of this actor
     * 
     * @param n The name of this actor
     */
    public void setName(String n){
        this.name = n;
    }
    
    /**
     * This method is defined here because Actor (mutable) is used as a key in a Hashtable.
     * It makes sure that same Actors have always the same hash code.
     * So, the hash code of any object taht is used as key in a hash table,
     * has to be produced on an *immutable* quantity,
     * like a String (such a string is the name of the actor in our case)
     * 
     * @return an integer, which is the has code for the name of the actor
     */
    public int hashCode() {
        return name.hashCode();
    }

    /**
     * Tests this actor against the input one and determines whether they are equal.
     * Two actors are considered equal if they have the same name and gender.
     * 
     * @return true if both objects are of type Actor, 
     * and have the same name and gender, false in any other case.
     */
    public boolean equals(Object other) {
        if (other instanceof Actor) {
            return this.name.equals(((Actor) other).name) && 
            this.gender.equals(((Actor) other).gender); // Need explicit (Actor) cast to use .name
        } else {
            return false;
        }
    }
    
    /**
     * Retrns a string representation of this Actor. For easier testing, in the current version it 
     * returns only the name of the actor.
     * 
     * @return a string representation of this actor, containing their name and gender.
     */
    public String toString(){
        return "Name: " + this.name + " " + "Gender: " + this.gender;
    }
    
    public static void main (String[] args){
        Actor actor1 = new Actor("Jennifer", "Female");
        System.out.println("Creating new actor: Jennifer, female");
        System.out.println("Testing toString() => " + actor1.toString() +"\n");
        actor1.setGender("male");
        System.out.println("testing setGender() (setting to male): " + actor1.toString());
        actor1.setName("John");
        System.out.println("testing setName() (setting to John): " + actor1.toString());
        System.out.println("testing getName() (expecting John): " + actor1.getName());
        System.out.println("testing getgender() (expecting male): " + actor1.getGender());

    }
}
