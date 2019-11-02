package com.siva.forex.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RateDAO {

    @Query("SELECT * FROM rates")
    public LiveData<List<Rate>> selectAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void store(Rate... rates);

}
