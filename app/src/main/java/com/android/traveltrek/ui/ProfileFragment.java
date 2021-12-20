package com.android.traveltrek.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.traveltrek.AboutActivity;
import com.android.traveltrek.R;
import com.android.traveltrek.databinding.FragmentProfileBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProfileFragment extends Fragment {

    TextView name, mail;
    ImageView img;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        LinearLayout log_out = (LinearLayout) view.findViewById(R.id.profilelogout);
        name = view.findViewById(R.id.profilename);
        mail = view.findViewById(R.id.profileid);
        img = view.findViewById(R.id.profileimage);

        LinearLayout about = (LinearLayout)view.findViewById(R.id.profileabout);
        LinearLayout feedback = (LinearLayout)view.findViewById(R.id.profilefeedback);
        LinearLayout contact = (LinearLayout)view.findViewById(R.id.profilecontact);


        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(getContext());
        if(signInAccount!=null){
            name.setText(signInAccount.getDisplayName());
            mail.setText(signInAccount.getEmail());
            Picasso.get().load(signInAccount.getPhotoUrl()).into(img);
        }

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String email = user.getEmail();
            mail.setText(email);
        }

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel: 9555444389"));
                startActivity(intent);
            }
        });

        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getContext(),LoginActivity.class);
                startActivity(intent);
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AboutActivity.class);
                startActivity(intent);
            }
        });

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  showDialog();
            }
        });

        return view;
    }

    private void showDialog(){
        final AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setTitle("Feedback form");
        dialog.setMessage("Provide us your valuable feedback!");

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View reg_layout  = inflater.inflate(R.layout.send_feedback,null);
        final EditText edtEmail = reg_layout.findViewById(R.id.edtEmail);
        final EditText edtName = reg_layout.findViewById(R.id.edtName);
        final EditText edtFeedback = reg_layout.findViewById(R.id.edtFeedback);

        dialog.setView(reg_layout);

        dialog.setPositiveButton("SEND", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (TextUtils.isEmpty(edtEmail.getText().toString())){
                    Toast.makeText(getContext(), "Please type your email...", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(edtName.getText().toString())){
                    Toast.makeText(getContext(), "Name field cannot be empty...", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(edtEmail.getText().toString())){
                    Toast.makeText(getContext(), "feedback field cannot be empty...", Toast.LENGTH_SHORT).show();
                    return;
                }

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference();
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Object value = snapshot.getValue();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getContext(), "Failed to read value.", Toast.LENGTH_SHORT).show();
                    }
                });

                myRef.child("FeedbackUsers").child(edtName.getText().toString()).child("Email").setValue(edtEmail.getText().toString());
                myRef.child("FeedbackUsers").child(edtName.getText().toString()).child("Feedback").setValue(edtFeedback.getText().toString());
                myRef.child("FeedbackUsers").child(edtName.getText().toString()).child("Name").setValue(edtName.getText().toString());

                Toast.makeText(getContext(), "Thank you for your feedback!", Toast.LENGTH_SHORT).show();

            }
        });

        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

}