package com.example.tweeter.view.fragments.Login;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tweeter.R;
import com.example.tweeter.model.Response.LoginResponse;
import com.example.tweeter.model.request.LoginRequest;
import com.example.tweeter.model.request.RegisterRequest;
import com.example.tweeter.presenter.LoginPresenter;
import com.example.tweeter.presenter.RegisterPresenter;
import com.example.tweeter.view.MainActivity;
import com.example.tweeter.view.Tasks.LoginTask;
import com.example.tweeter.view.Tasks.RegisterTask;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


public class RegisterFragment extends Fragment implements  RegisterTask.Observer, LoginTask.Observer{
    private Bitmap newProfilePicture;
    private EditText firstName;
    private EditText lastName;
    private EditText password;
    private EditText alias;
    private Button imageSelector;
    private static EditText[] list;
    private Button submitButton;
    private ImageView profilePreview;



    public RegisterFragment() {
        // Required empty public constructor

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_register, container, false);

        firstName = view.findViewById(R.id.register_firstName);
        lastName = view.findViewById(R.id.register_lastName);
        password = view.findViewById(R.id.register_password);
        alias = view.findViewById(R.id.register_username);
        imageSelector = view.findViewById(R.id.register_imageSelector);
        submitButton = view.findViewById(R.id.register_submit);
        profilePreview = view.findViewById(R.id.register_profilePreview);
        submitButton.setEnabled(false);



        firstName.setHint("First Name");
        lastName.setHint("Last Name");
        password.setHint("Password");
        alias.setHint("Username");

        imageSelector.setOnClickListener((c) -> {
            selectImage(getContext());
        });

        list = new EditText[]{firstName, lastName, password, alias};
        for(EditText e : list){
            e.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    boolean seenUnfilled = false;

                    for(EditText e : list){
                        if(e.getText().toString().equals("")){
                            seenUnfilled = true;
                        }
                    }
                    if(newProfilePicture == null){
                        seenUnfilled = true;
                    }
                    System.out.println("Seen ulfilled = " + seenUnfilled);
                    submitButton.setEnabled(!seenUnfilled);
                }
            });

        }

        submitButton.setOnClickListener((c) -> {
            //FIXME create register task, then create main activity from user response
            String aliasString = alias.getText().toString();
            String passwordString = password.getText().toString();
            String firstNameString = firstName.getText().toString();
            String lastNameString = lastName.getText().toString();


            int size     = newProfilePicture.getRowBytes() * newProfilePicture.getHeight();
            ByteBuffer b = ByteBuffer.allocate(size);

            newProfilePicture.copyPixelsToBuffer(b);

            byte[] bytes = new byte[size];

            try {
                b.get(bytes, 0, bytes.length);
            } catch (BufferUnderflowException e) {
                // always happens
            }

            RegisterRequest request = new RegisterRequest(aliasString,passwordString,firstNameString,lastNameString,bytes);
            RegisterTask task = new RegisterTask(this,new RegisterPresenter());
            task.execute(request);


        });









        return view;
    }

    private void selectImage(Context context) { //THIS CODE IS TAKEN FROM a tutorial here https://medium.com/@hasangi/capture-image-or-choose-from-gallery-photos-implementation-for-android-a5ca59bc6883
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose your profile picture");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);

                } else if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , 1);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        newProfilePicture = selectedImage;
                        profilePreview.setImageBitmap(newProfilePicture);

                        boolean seenUnfilled = false;

                        for(EditText e : list){
                            System.out.println(e.toString() + " is e.sotring");
                            if(e.getText().toString().equals("")){
                                System.out.println("Seen unfilled!");
                                seenUnfilled = true;
                            }

                        }
                        System.out.println("seen Unfilled = " + seenUnfilled);
                        submitButton.setEnabled(!seenUnfilled);
                    }

                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage =  data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (selectedImage != null) {
                            Cursor cursor = getContext().getContentResolver().query(selectedImage,
                                    filePathColumn, null, null, null);
                            if (cursor != null) {
                                cursor.moveToFirst();

                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                String picturePath = cursor.getString(columnIndex);
                                newProfilePicture = BitmapFactory.decodeFile(picturePath);
                                profilePreview.setImageBitmap(newProfilePicture);
                                boolean seenUnfilled = false;

                                for(EditText e : list){

                                    System.out.println(e.toString() + " is e.sotring");
                                    if(e.getText().toString().equals("")){
                                        System.out.println(e.toString() + " is e.sotring");
                                        System.out.println("Seen unfilled!");
                                        seenUnfilled = true;
                                    }

                                }
                                System.out.println("seen Unfilled = " + seenUnfilled);
                                submitButton.setEnabled(!seenUnfilled);
                                cursor.close();
                            }
                        }

                    }
                    break;
            }
        }
    }

    @Override
    public void success() {
        LoginTask task = new LoginTask(new LoginPresenter(),this);
        String userNameString = alias.getText().toString();
        String passwordString = alias.getText().toString();
        LoginRequest request = new LoginRequest(userNameString,passwordString);
        task.execute(request);
    }

    @Override
    public void fail() {
        Toast.makeText(getContext(),"Unable to register",Toast.LENGTH_LONG).show();
    }

    @Override
    public void loginSuccessful(LoginResponse loginResponse) {
        Intent intent = new Intent(getContext(), MainActivity.class);
        Bundle extras = new Bundle();
        extras.putSerializable(LoginResponse.LOGIN_RESPONSE_KEY,loginResponse);
        intent.putExtras(extras);
        startActivity(intent);
    }

    @Override
    public void loginUnsuccessful(LoginResponse loginResponse) {
        Toast.makeText(getContext(),"Unable to login",Toast.LENGTH_LONG).show();
    }

    @Override
    public void handleException(Exception ex) {

    }
}