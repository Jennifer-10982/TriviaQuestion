package algonquin.cst2335.triviaquestion;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PlayerInformationDAO {

    @Insert
    void insertInformation(PlayerInformation player_info);

    @Query("SELECT * from PlayerInformation ORDER BY counter DESC LIMIT 10")
    List<PlayerInformation> getAllPlayerInfo();

    @Delete
    void deleteInformation(PlayerInformation player_info);
}
