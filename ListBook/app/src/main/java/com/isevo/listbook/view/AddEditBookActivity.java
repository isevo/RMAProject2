package com.isevo.listbook.view;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.isevo.listbook.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddEditBookActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    public static final String EXTRA_ID="com.isevo.exampleapp.EXTRA_ID";
    public static final String EXTRA_TITLE="com.isevo.exampleapp.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION="com.isevo.exampleapp.EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY="com.isevo.exampleapp.EXTRA_PRIORITY";
    public static final String EXTRA_IMAGE="com.isevo.exampleapp.EXTRA_IMAGE";
    public static final  String EXTRA_DATE="com.isevo.exampleapp.WXTRA_DATE";
    public static  final String EXTRA_SPINNER="com.isevo.exampleapp.EXTRA_SPINNER";



    private static final int PERMISSION_CODE = 1000;
    private static final int IMAGE_CAPTURE_CODE = 1001;


    Uri image_uri;

    private EditText editTextTitle;
    private EditText editTextDescription;
    private NumberPicker numberPickerPriority;
    private Spinner spinner;
    private  EditText date;
    private Button button;

    // ImageView image_view;
    String category="";
    String item="";
    int br=0;

    private ImageView imageView;
    private Button capture_image;
    Intent data=new Intent();



    private String Uri_1="1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        editTextTitle=findViewById(R.id.edit_text_title);
        editTextDescription=findViewById(R.id.edit_text_description);
        numberPickerPriority=findViewById(R.id.number_picker_pririty);

        date=findViewById(R.id.datum);
        //  image_view=findViewById(R.id.image_view1);

        spinner=(Spinner)findViewById(R.id.spinner);

        spinner.setOnItemSelectedListener(AddEditBookActivity.this);

        List<String> categories=new ArrayList<String>();
        categories.add("Strano");
        categories.add("Domace");
        ArrayAdapter<String> dataAdapter=new ArrayAdapter<String>(AddEditBookActivity.this, android.R.layout.simple_spinner_item,categories);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(dataAdapter);



        date.addTextChangedListener(new TextWatcher() {
            private String current = "";
            private String ddmmyyyy = "DDMMYYYY";
            private Calendar cal = Calendar.getInstance();


            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]", "");
                    String cleanC = current.replaceAll("[^\\d.]", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {
                        sel++;
                    }
                    //Fix for pressing delete next to a forward slash
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8){
                        clean = clean + ddmmyyyy.substring(clean.length());
                    }else{
                        //This part makes sure that when we finish entering numbers
                        //the date is correct, fixing it otherwise
                        int day  = Integer.parseInt(clean.substring(0,2));
                        int mon  = Integer.parseInt(clean.substring(2,4));
                        int year = Integer.parseInt(clean.substring(4,8));

                        if(mon > 12) mon = 12;
                        cal.set(Calendar.MONTH, mon-1);

                        year = (year<1900)?1900:(year>2100)?2100:year;
                        cal.set(Calendar.YEAR, year);
                        // ^ first set year for the line below to work correctly
                        //with leap years - otherwise, date e.g. 29/02/2012
                        //would be automatically corrected to 28/02/2012

                        day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
                        clean = String.format("%02d%02d%02d",day, mon, year);
                    }

                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    date.setText(current);
                    date.setSelection(sel < current.length() ? sel : current.length());



                }
            }


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {}
        });
        button=findViewById(R.id.button_close);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });














        imageView=findViewById(R.id.image_view);
        capture_image=findViewById(R.id.takeImage);


        capture_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddEditBookActivity.this, "KLIK!!", Toast.LENGTH_SHORT).show();
                if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.CAMERA)== PackageManager.PERMISSION_DENIED ||
                            checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_DENIED){

                        String[] permission={Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};


                        requestPermissions(permission,PERMISSION_CODE);


                    }
                    else{
                        openCAmera();

                    }
                }else{
                    openCAmera();
                }

            }

        });



        numberPickerPriority.setMinValue(1);
        numberPickerPriority.setMaxValue(10);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        Intent intent=getIntent();

        //Picasso.with(this).load(intent.getStringExtra(EXTRA_IMAGE)).fit().placeholder(R.drawable.ic_launcher_background).into(image_);

        if (intent.hasExtra(EXTRA_ID)){
            setTitle("EDIT NOTE");
            editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            editTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            numberPickerPriority.setValue(intent.getIntExtra(EXTRA_PRIORITY,1));
            date.setText(intent.getStringExtra(EXTRA_DATE));
            System.out.println("----------------------------------------->"+intent.getStringExtra(EXTRA_IMAGE));

            Picasso.with(this).load(intent.getStringExtra(EXTRA_IMAGE)).fit().placeholder(R.drawable.ic_launcher_background).into(imageView);
            Uri_1=intent.getStringExtra(EXTRA_IMAGE);

            if (intent.getStringExtra(EXTRA_SPINNER).equals("Domace")) {

                spinner.setSelection(intent.getIntExtra(EXTRA_SPINNER, 1));

            }
            else{
                spinner.setSelection(intent.getIntExtra(EXTRA_SPINNER,0));

            }


        }else {
            setTitle("Add Note");
        }


    }

    private void openCAmera() {
        ContentValues values=new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"New picture");
        values.put(MediaStore.Images.Media.DESCRIPTION,"New picture");
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent cameraIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,image_uri);
        startActivityForResult(cameraIntent,IMAGE_CAPTURE_CODE);





    }


    private void saveNote(){
        String title=editTextTitle.getText().toString();
        String desciption=editTextDescription.getText().toString();
        int priority=numberPickerPriority.getValue();
        // String riImg=image_uri.toString();
        String datum=date.getText().toString();
        category= spinner.getSelectedItem().toString();

        String riImg="";


        if (Uri_1!="1"){
            riImg=Uri_1;

        }


        if (image_uri!=null){
            riImg=image_uri.toString();
        }






        if (riImg=="")
        {
            System.out.println("riImg je "+riImg);
        }
        if(image_uri==null){
            System.out.println("imge_uri:::::"+image_uri);
        }

        System.out.println("IMAGE URL:"+riImg);



        //ako se unese prazan string
        if (title.trim().isEmpty()||desciption.trim().isEmpty() || riImg==""){
            Toast.makeText(this, "Insert title ,description and picture", Toast.LENGTH_SHORT).show();
            return;
        }



        //ako sve prode dobro


        data.putExtra(EXTRA_TITLE,title);
        data.putExtra(EXTRA_DESCRIPTION,desciption);
        data.putExtra(EXTRA_PRIORITY,priority);
        data.putExtra(EXTRA_DATE,datum);

        data.putExtra(EXTRA_SPINNER,item);



        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        //System.out.println(data.getStringExtra(EXTRA_TITLE));
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        // System.out.println("IMAGE URI: "+riImg);

        int id=getIntent().getIntExtra(EXTRA_ID,-1);

        if (id!=-1){
            data.putExtra(EXTRA_ID,id);
        }



        if (riImg!=null) {

            data.putExtra(EXTRA_IMAGE, riImg);
        }
        // System.out.println("EXTRA_IMAGE:>>>>>>>>>>>>>>>"+EXTRA_IMAGE);

        setResult(RESULT_OK,data);
        finish();
    }
    //ZA DOPUŠTENJE KAMERI DA SLIKA
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case PERMISSION_CODE:{
                if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    openCAmera();
                }else{
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
            }


        }



    }


    //poziva se kad se uslika

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        if (resultCode == RESULT_OK) {

            imageView.setImageURI(image_uri);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_note:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        item = parent.getItemAtPosition(position).toString();
        br=position;
        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
