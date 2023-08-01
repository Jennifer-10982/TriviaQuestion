/*
 * Student Name: Jennifer Huynh
 * Student Number: 041086110
 * Lab Section: CST2335_022
 * Due Date: 07/08/23
 * Description: The class PlayerInformation uses the ROOM Database library to connect and create a database
 * to store values in the table. The class is used to reflect that data in the table.
 * */
package algonquin.cst2335.triviaquestion;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * The class PlayerInformation uses the ROOM Database library to connect and create a database
 * to store values in the table. The class is used to reflect that data in the table.
 */
@Entity
    public class PlayerInformation {
    /**
     * id is the primary key representing the id from the database that is automatically generated
     * when a new value is inserted into the dataset
     * playerName is the player's name represents the user. It can only contain alphanumerics.
     * counter is the total points that the user obtained during the game. It can only contain numbers
     */
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name="id")
        public int id;
        @ColumnInfo(name="PlayerName")
        String playerName;

        @ColumnInfo(name="Counter")
        String counter;

    /**
     * No argument constructor. Will initialize the variable to default values
     */
    public PlayerInformation(){

        };

    /**
     * Parameterized constructor to set the two values playerName and counter
     * @param p represents the player's name which is given by the user after each game. It con only contain alphanumerics.
     * @param c represents the total points that the user obtained during the game. It can only contain numbers
     */
        PlayerInformation(String p, String c){
            playerName = p;
            counter = c;
        }

    /**
     * This is a setter method that is used to initialize the variable id to the id that is the primary key representing the id from the database that is automatically generated
     * when a new value is inserted into the dataset
     * @param id is the primary key representing the id from the database that is automatically generated
     * when a new value is inserted into the dataset
     */
    public void setId(int id){
            this.id = id;
        }


    /**
     * This is a getter method use to return the values of the variable playerName where the playerName is the player's name represents the user. It can only contain alphanumerics.
     * @return playerName is the player's name represents the user. It can only contain alphanumerics.
     */
        public String getPlayerName(){
            return playerName;
        }

    /**
     * This is a getter method use to return the values of the variable counter where the counter is the total points that the user obtained during the game. It can only contain numbers
     * @return counter is the total points that the user obtained during the game. It can only contain numbers
     */

        public String getCounter(){
            return counter;
        }

    /**
     * This is a getter method use to return the id where it is the primary key representing the id from the database that is automatically generated
     * when a new value is inserted into the dataset
     * @return id where it is the primary key representing the id from the database that is automatically generated
     * when a new value is inserted into the dataset
     */
    public int getId(){
            return id;
        }

}
