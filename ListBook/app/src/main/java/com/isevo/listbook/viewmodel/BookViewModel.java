package com.isevo.listbook.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.isevo.listbook.model.Book;
import com.isevo.listbook.view.BookRepository;

import java.util.List;

public class BookViewModel extends AndroidViewModel {


    private BookRepository repository;
    private LiveData<List<Book>> allBook;


    public BookViewModel(@NonNull Application application) {
        super(application);
        repository=new BookRepository(application);
        allBook =repository.getAllBooks();
    }



    public void insert(Book book){
        repository.insert(book);
    }


    public void update(Book book){
        repository.update(book);
    }

    public void dalate(Book book){
        repository.delete(book);

    }

    public void deleteAllNotes(){
        repository.deleteAllBooks();
    }

    public LiveData<List<Book>> getAllBook(){
        return allBook;
    }


}
