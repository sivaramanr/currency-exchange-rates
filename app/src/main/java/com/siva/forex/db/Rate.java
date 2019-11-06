package com.siva.forex.db;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "rates",
        indices = {@Index(value = {"pair"}, unique = true)})
public class Rate {

    @PrimaryKey
    @NonNull
    public String pair;

    public double rate;

}
