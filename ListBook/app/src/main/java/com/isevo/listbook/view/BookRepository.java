package com.isevo.listbook.view;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.isevo.listbook.model.Book;
import com.isevo.listbook.model.dao.BookDao;
import com.isevo.listbook.model.dao.BookDataBase;

import java.util.List;

public class BookRepository {

    private BookDao bookDao;
    private LiveData<List<Book>> allBooks;

    public BookRepository(Application application){
        BookDataBase database= BookDataBase.getInstance(application);

        //za njih room sam generira tijelo
        bookDao = database.bookDao();
        allBooks= bookDao.getAllNotes();


    }
    public void insert(Book book){
        new InsertBookeAsyncTask(bookDao).execute(book);

    }

    public void update(Book book){

        new UpdateBookAsyncTask(bookDao).execute(book);


    }

    public void delete(Book book){
        new DeleteBookAsyncTask(bookDao).execute(book);
    }

    public  void deleteAllBooks(){
        new DeleteAllBooksAsyncTask(bookDao).execute();

    }
    public LiveData<List<Book>> getAllBooks(){
        return allBooks;
    }

    //gornje metode se ne mogu izvrsavati u main tredu pa se na ovaj nacin onemugacava zamrzavanje app

    private static class  InsertBookeAsyncTask extends AsyncTask<Book,Void,Void>{
        private BookDao noteDao;

        private InsertBookeAsyncTask(BookDao noteDao){
            this.noteDao=noteDao;
        }

        @Override
        //jer dohvacamo samo jedan
        protected Void doInBackground(Book... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }


    private static class  UpdateBookAsyncTask extends AsyncTask<Book,Void,Void>{
        private BookDao bookDao;

        private UpdateBookAsyncTask (BookDao bookDao){
            this.bookDao=bookDao;
        }

        @Override
        protected Void doInBackground(Book... notes) {
            //jer dohvacamo samo jedan
            bookDao.update(notes[0]);
            return null;
        }
    }




    private static class DeleteBookAsyncTask extends AsyncTask<Book,Void,Void>{
        private BookDao noteDao;

        private DeleteBookAsyncTask(BookDao bookDao){
            this.noteDao=noteDao;
        }

        @Override
        protected Void doInBackground(Book... notes) {
            //jer dohvacamo samo jedan
            noteDao.delete(notes[0]);
            return null;
        }
    }



    private static class  DeleteAllBooksAsyncTask extends AsyncTask<Void,Void,Void> {
        private BookDao bookDao;

        private DeleteAllBooksAsyncTask(BookDao bookDao){
            this.bookDao=bookDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //jer dohvacamo samo jedan
            bookDao.DeleteAllNotes();
            return null;
        }
    }







}
