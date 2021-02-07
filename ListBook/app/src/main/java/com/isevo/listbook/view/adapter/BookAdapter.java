package com.isevo.listbook.view.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.isevo.listbook.R;
import com.isevo.listbook.model.Book;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookHolder> {
    private List<Book> books=new ArrayList<>();
    private OnItemClickInterface listener;


    @NonNull
    @Override
    public BookHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item,parent,false);
        return new BookHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BookHolder holder, int position) {
        Book currentNote=books.get(position);
        holder.textViewTitle.setText(currentNote.getTitle());
        holder.textViewDescription.setText(currentNote.getDescription());
        holder.textViewPriority.setText(String.valueOf(currentNote.getPriority()));

        holder.textViewDate.setText(String.valueOf(currentNote.getDatum()));
        System.out.println("NOTE ADPER???????????????????????????????????????????????????????????''");

        holder.textViewSpinner.setText(String.valueOf(currentNote.getCategory()));


        if (holder.imgView!=null) {
            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

            //    Picasso.get().load(notes.get(position).getUriImage()).fit().placeholder(R.drawable.ic_launcher_background).into(holder.imgView);
            holder.imgView.setImageURI(Uri.parse(String.valueOf(currentNote.getUriImage())));
            //  holder.imgView1.setImageURI(Uri.parse(String.valueOf(currentNote.getUriImage())));

        }
        else{


        }

    }

    @Override
    public int getItemCount() {
        return books.size();
    }


    public void setBooks(List<Book> books){
        this.books=books;
        notifyDataSetChanged();
    }

    public Book getBookAt(int position){
        return books.get(position);
    }


    public class BookHolder extends RecyclerView.ViewHolder {

        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewPriority;
        private ImageView imgView;
       // private ImageView imgView1;
        private TextView textViewDate;
        private TextView textViewSpinner;


        public BookHolder(@NonNull View itemView) {
            super(itemView);

            textViewTitle=itemView.findViewById(R.id.text_view_title);
            textViewDescription= itemView.findViewById(R.id.text_view_description);
            textViewPriority=itemView.findViewById(R.id.text_view_priority);
            imgView=(ImageView) itemView.findViewById(R.id.image_view);

            textViewDate=itemView.findViewById(R.id.datum);

            textViewSpinner=itemView.findViewById(R.id.spinner);

            int p=getAdapterPosition();
//            Picasso.with(itemView.getContext()).load(notes.get(p).getUriImage()).fit().placeholder(R.drawable.ic_launcher_background).into(imgView1);
            // imgView1=itemView.findViewById(R.id.image_view1);

            System.out.println("IMAGE VIEW --------------------------------"+imgView);

            //   Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(imgView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    if (listener!=null
                            && position!=RecyclerView.NO_POSITION){
                        listener.onItemClick(books.get(position));
                    }
                }
            });




        }
    }



    public interface OnItemClickInterface{
        void onItemClick(Book note);
    }

    public void setItemClickListener(OnItemClickInterface listener){
        this.listener=listener;
    }

}
