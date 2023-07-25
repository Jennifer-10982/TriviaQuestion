package algonquin.cst2335.triviaquestion;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {PlayerInformation.class}, version=2)
public abstract class InformationDatabase extends RoomDatabase {
    public abstract  PlayerInformationDAO cmDAO();
}
