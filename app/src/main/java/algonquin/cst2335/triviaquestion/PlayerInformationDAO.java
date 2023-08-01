/*
 * Student Name: Jennifer Huynh
 * Student Number: 041086110
 * Lab Section: CST2335_022
 * Due Date: 07/08/23
 * Description: The interface PlayerInformationDAO takes care of the CRUD (create, retrieve, update, delete) functions.
 * The interface creates a DAO (data access object) that will be responsible for inserting and deleting player information
 * as well as retreiving all the player in the database.
 * */
package algonquin.cst2335.triviaquestion;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

/**
 * The interface PlayerInformationDAO takes care of the CRUD (create, retrieve, update, delete) functions.
 * The interface creates a DAO (data access object) that will be responsible for inserting and deleting player information
 * as well as retreiving all the player in the database.
 */
@Dao
public interface PlayerInformationDAO {

    /**
     * The insertInformation() is used to insert the data into the object player_info of type
     * PlayerInformation into the database
     * @param player_info is the information regarding the player which is their username, and their highscoore
     */
    @Insert
    void insertInformation(PlayerInformation player_info);

    /**
     * This is used to retrieve all the player and their information by running an SQL query.
     * The SQL query is retrieving only the first 10 information regarding the player in descending
     * order based on their highscore.
     * @return List object containing the player information which is their name and their highscore.
     */
    @Query("SELECT * from PlayerInformation ORDER BY counter DESC LIMIT 10")
    List<PlayerInformation> getAllPlayerInfo();

    /**
     * The method deleteInformation is used to delete the player's information that is stored in the database.
     * @param player_info is the information regarding the player which is their username, and their highscoore
     *
     */
    @Delete
    void deleteInformation(PlayerInformation player_info);
}
