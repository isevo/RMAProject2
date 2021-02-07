package com.isevo.listbook.model.dao;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.isevo.listbook.model.Book;

@Database(entities = {Book.class},version=2, exportSchema = false)
public abstract class BookDataBase extends RoomDatabase {

    private static BookDataBase instance;
    public abstract BookDao bookDao();


    //samo jedna dretva moze pristupiti istovremeno
    public static synchronized  BookDataBase getInstance(Context context){
        //ako nema instance
        if (instance==null){
            instance= Room.databaseBuilder(context.getApplicationContext(),
                    BookDataBase.class,"note_database")
                    //za bazu sa vec upisanim elemntima
                    .addCallback(roomCallback)
                    .fallbackToDestructiveMigration().build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback=new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();

        }
    };



    private static  class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void> {
        private BookDao noteDao;
        private PopulateDbAsyncTask (BookDataBase db){
            noteDao=db.bookDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
//        noteDao.insert(new Note("Title 1","DESCRIPTION 1",1));
            //      noteDao.insert(new Note("Title 2","DESCRIPTION 2",2));
            return null;
        }
    }

}
