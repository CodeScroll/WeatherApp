package com.example.jordan.weatherapp;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FragmentTwo extends Fragment {

    View v;
    ImageView img;
    String userId;
    ArrayList<Contact> imageArry = new ArrayList<Contact>();
    ContactImageAdapter adapter;


    private static final int PICK_IMAGE = 1;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_two, container,false);

        final DataBaseHandler db = new DataBaseHandler(getActivity());

        Button btnSubmit = (Button) v.findViewById(R.id.btnDb);

        img = (ImageView) v.findViewById(R.id.imgUser);

        final EditText one=(EditText)v.findViewById(R.id.txtName);

        //---------------------------------------------------------------------------------------------------------------

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        img.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);

                return true;
            }
        });

        btnSubmit.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Bitmap image = image = ((BitmapDrawable) img.getDrawable()).getBitmap();

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte imageInByte[] = stream.toByteArray();
                Log.d("Insert: ", "Inserting ..");
                db.addContact(new Contact(one.getText().toString(), imageInByte));
            }
        });


        List<Contact> contacts = db.getAllContacts();
        for (Contact cn : contacts) {
            String log = "ID:" + cn.getID() + " Name: " + cn.getName()
                    + " ,Image: " + cn.getImage();

            Log.d("Result: ", log);

            imageArry.add(cn);

        }
        adapter = new ContactImageAdapter(getActivity(), R.layout.screen_list,
                imageArry);
        ListView dataList = (ListView) v.findViewById(R.id.list);
        dataList.setAdapter(adapter);


        //String imageUri = "https://graph.facebook.com/100000086725403/picture?type=large";
        //ImageView ivBasicImage = (ImageView) v.findViewById(R.id.imgUser);
        //Picasso.with(getActivity()).load(imageUri).into(ivBasicImage);

        return v;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {

            Uri selectedImage = data.getData();

            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getActivity().getContentResolver().query(selectedImage,filePathColumn, null, null, null);

            cursor.moveToFirst();

            ImageView img_user=(ImageView) v.findViewById(R.id.imgUser);

            try {

                img_user.setImageBitmap(BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(selectedImage)));

                //3img_user.setScaleType(ImageView.ScaleType.FIT_XY);


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            cursor.close();
        } else {
            Toast.makeText(getActivity(), "Try Again!!", Toast.LENGTH_SHORT)
                    .show();
        }

    }
}

