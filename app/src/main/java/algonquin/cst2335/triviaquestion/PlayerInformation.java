package algonquin.cst2335.triviaquestion;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
    public class PlayerInformation {

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name="id")
        public int id;
        @ColumnInfo(name="PlayerName")
        String playerName;

        @ColumnInfo(name="Counter")
        String counter;

        public PlayerInformation(){

        };

        /*Parameterized constructor to set the two values playerName and counter*/
        PlayerInformation(String p, String c){
            playerName = p;
            counter = c;
        }

        /*This is a getter method use to return the values of the variable playerName*/
        public String getPlayerName(){
            return playerName;
        }

        /*This is a getter method use to return the values of the variable counter*/
        public String getCounter(){
            return counter;
        }

}
