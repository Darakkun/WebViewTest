package ennbose.sinewers.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface LinkDao {

    @Insert
    fun insertLink(link: Link)

    @Query("SELECT * FROM link_table")
    fun getLink():List <Link>?

}