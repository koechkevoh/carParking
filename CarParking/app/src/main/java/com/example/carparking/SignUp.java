package com.example.carparking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.carparking.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignUp extends AppCompatActivity {


    Button btnSignUp;
    MaterialEditText editPhone,editPassword,editEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        btnSignUp = (Button)findViewById(R.id.btnSignUp);
        editPhone = (MaterialEditText)findViewById(R.id.editPhone);
        editPassword = (MaterialEditText)findViewById(R.id.editPassword);
        editEmail = (MaterialEditText)findViewById(R.id.editEmail);

        /**Init Firebase**/
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog dialog = new ProgressDialog(SignUp.this);
                dialog.setMessage("Please Waiting...");
                dialog.show();
                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        /**Check if Phone exists in database**/
                        if (dataSnapshot.child(editPhone.getText().toString()).exists())
                        {
                            dialog.dismiss();
                            Toast.makeText(SignUp.this, "Phone number already exists!!!", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            dialog.dismiss();
                            User user = new User(editEmail.getText().toString(),editPassword.getText().toString());
                            table_user.child(editPhone.getText().toString()).setValue(user);
                            Toast.makeText(SignUp.this, "SignUp successful!!!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
    }
}

