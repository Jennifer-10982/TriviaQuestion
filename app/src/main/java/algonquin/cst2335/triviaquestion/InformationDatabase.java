/*
 * Student Name: Jennifer Huynh
 * Student Number: 041086110
 * Lab Section: CST2335_022
 * Due Date: 07/08/23
 * Description: This class is used to create the database itself to store the information of the player's username,
 * their id, and their highscore. The class extends RoomDatase to be able to inherit the necessary functions
 * and it is a way for the app to interact with the database.
 * */
package algonquin.cst2335.triviaquestion;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * This class is used to create the database itself to store the information of the player's username,
 * their id, and their highscore. The class extends RoomDatase to be able to inherit the necessary functions
 * and it is a way for the app to interact with the database.
 */
@Database(entities = {PlayerInformation.class}, version=2)
public abstract class InformationDatabase extends RoomDatabase {
    /**
     * The method is usese the PlayerInformationDAO class for querying data such as inserting,
     * retrieving information and deleting data from the database.
     * @return DAO is used for interacting with the database
     */
    public abstract  PlayerInformationDAO cmDAO();
}
