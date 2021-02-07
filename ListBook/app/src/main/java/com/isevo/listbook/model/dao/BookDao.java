package com.isevo.listbook.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.isevo.listbook.model.Book;

import java.util.List;

@Dao
public interface BookDao {
    @Insert
    void insert(Book book);

    @Update
    void update(Book book);


    @Delete
    void delete(Book book);

    //brise sve
    @Query("DELETE FROM book_table")
    void DeleteAllNotes();


    @Query("SELECT * FROM book_table ORDER BY priority DESC")
    LiveData<List<Book>> getAllNotes();

}
