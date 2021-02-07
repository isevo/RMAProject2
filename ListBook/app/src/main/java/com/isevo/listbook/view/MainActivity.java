package com.isevo.listbook.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.isevo.listbook.R;
import com.isevo.listbook.model.Book;
import com.isevo.listbook.view.adapter.BookAdapter;
import com.isevo.listbook.viewmodel.BookViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    public static final int ADD_NOTE_REQUEST=1;
    public static final int EDIT_NOTE_REQUEST=2;


    private BookViewModel noteViewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




            FloatingActionButton buttonAddNote=findViewById(R.id.button_add_note);
            buttonAddNote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(MainActivity.this, AddEditBookActivity.class);
                    startActivityForResult(intent,ADD_NOTE_REQUEST);
                }
            });

            RecyclerView recyclerView=findViewById(R.id.recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            //
            recyclerView.setHasFixedSize(true);
            BookAdapter adapter=new BookAdapter();
            recyclerView.setAdapter(adapter);

            noteViewModel = new ViewModelProvider(this,
                    ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(BookViewModel.class);
            noteViewModel.getAllBook().observe(this, new Observer<List<Book>>() {
                @Override
                public void onChanged(List<Book> books) {

                    adapter.setBooks(books);

                }
            });

            new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    noteViewModel.dalate(adapter.getBookAt(viewHolder.getAdapterPosition()));

                }
            }).attachToRecyclerView(recyclerView);





            adapter.setItemClickListener(new BookAdapter.OnItemClickInterface() {
                @Override
                public void onItemClick(Book book) {
                    Intent intent=new Intent(MainActivity.this, AddEditBookActivity.class);


                    intent.putExtra(AddEditBookActivity.EXTRA_ID,book.getId());
                    intent.putExtra(AddEditBookActivity.EXTRA_TITLE,book.getTitle());
                    intent.putExtra(AddEditBookActivity.EXTRA_DESCRIPTION,book.getDescription());
                    intent.putExtra(AddEditBookActivity.EXTRA_PRIORITY,book.getPriority());
                    intent.putExtra(AddEditBookActivity.EXTRA_IMAGE,book.getUriImage());
                    intent.putExtra(AddEditBookActivity.EXTRA_DATE,book.getDatum());
                    intent.putExtra(AddEditBookActivity.EXTRA_SPINNER,book.getCategory());
                    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+book.getUriImage()+"<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
                    // Picasso.with(context).load(object_array.get(position).getUrlToImage()).fit().placeholder(R.drawable.ic_launcher_background).into(image);
                    // Picasso.get().load(note.getId()).fit().placeholder(R.drawable.ic_launcher_background).into();
                    startActivityForResult(intent,EDIT_NOTE_REQUEST);
                }
            });

        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+resultCode);
        if (requestCode==ADD_NOTE_REQUEST && resultCode==RESULT_OK){

            String title=data.getStringExtra(AddEditBookActivity.EXTRA_TITLE);
            // System.out.println("main Activity title ++++++++++++++++"+title);


            String description=data.getStringExtra(AddEditBookActivity.EXTRA_DESCRIPTION);
            int priority=data.getIntExtra(AddEditBookActivity.EXTRA_PRIORITY,1);
            String datum=data.getStringExtra(AddEditBookActivity.EXTRA_DATE);
            //   System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!IMAGE ADD!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            String uriImage=data.getStringExtra(AddEditBookActivity.EXTRA_IMAGE);


            String category=data.getStringExtra(AddEditBookActivity.EXTRA_SPINNER);

            Book book=new Book(title,description,priority,uriImage,datum,category);
            noteViewModel.insert(book);

            // Picasso.get().load("http://i.imgur.com/DvpvklR.png").into();


            Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();


        }else   if (requestCode==EDIT_NOTE_REQUEST && resultCode==RESULT_OK)
        {

            int id=data.getIntExtra(AddEditBookActivity.EXTRA_ID,-1);

            if (id==-1){
                Toast.makeText(this, "Can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }
            String title=data.getStringExtra(AddEditBookActivity.EXTRA_TITLE);
            // System.out.println("main Activity title ++++++++++++++++"+title);


            String description=data.getStringExtra(AddEditBookActivity.EXTRA_DESCRIPTION);
            int priority=data.getIntExtra(AddEditBookActivity.EXTRA_PRIORITY,1);

            String uriImage=data.getStringExtra(AddEditBookActivity.EXTRA_IMAGE);
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!IMAGE EDIT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+uriImage);
            String datum=data.getStringExtra(AddEditBookActivity.EXTRA_DATE);
            String category=data.getStringExtra(AddEditBookActivity.EXTRA_SPINNER);


            Book book=new Book(title,description,priority,uriImage,datum,category);
            book.setId(id);
            noteViewModel.update(book);
            Toast.makeText(this, "Note Updated", Toast.LENGTH_SHORT).show();


        }

        else{

            Toast.makeText(this,"Note not saved",Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.delate_all_notes:noteViewModel.deleteAllNotes();
                Toast.makeText(this, "All notes delited", Toast.LENGTH_SHORT).show();
            default:
                return super.onOptionsItemSelected(item);
        }





    }

}