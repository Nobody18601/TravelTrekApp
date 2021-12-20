package com.android.traveltrek.ui;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import com.android.traveltrek.MainActivity;
import com.android.traveltrek.PostUploadInfo;
import com.android.traveltrek.R;
import com.android.traveltrek.databinding.FragmentPostBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;

public class PostFragment extends Fragment {

    ImageView img_post;
    private static int REQUEST_CODE = 1;
    String Storage_Path = "All_Image_Uploads/";
    byte bb[];
    // Root Database Name for Firebase Database.
    String Database_Path = "All_Image_Uploads_Database";

    Button add_post;
    EditText title_post,description_post,locationEditText;
    Uri FilePathUri;

    StorageReference storageReference;
    DatabaseReference databaseReference;


    ProgressDialog progressDialog ;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_post, container, false);
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference(Database_Path);

        add_post = (Button)view.findViewById(R.id.add_post);
        img_post = view.findViewById(R.id.post_img);
        title_post = view.findViewById(R.id.title_post);
        description_post = view.findViewById(R.id.description_post);
        locationEditText = view.findViewById(R.id.locationEditText);

        progressDialog = new ProgressDialog(getContext());

        CardView cardView = view.findViewById(R.id.pickerCard);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,
                        REQUEST_CODE);

            }
        });

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("MyNotifications", "MyNotifications", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getContext().getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        add_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UploadImageFileToFirebaseStorage(bb);

                /* Notification source code */
                NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(),"MyNotifications");
                builder.setContentTitle("Post Uploaded");
                builder.setContentText("Your post has successfully uploaded on recent posts section of TravelTrek app !!");
                builder.setPriority(NotificationCompat.PRIORITY_HIGH);
                builder.setCategory(NotificationCompat.CATEGORY_MESSAGE);
                builder.setSmallIcon(R.drawable.ic_baseline_notifications_24);
                builder.setAutoCancel(true);

                NotificationManagerCompat manager = NotificationManagerCompat.from(getContext());
                manager.notify(999,builder.build());

                /* ending */

            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {

                Bitmap bmp = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                bb = stream.toByteArray();
                
                img_post.setImageBitmap(bmp);

                FilePathUri = data.getData();

            }
        }
    }

    public void UploadImageFileToFirebaseStorage(byte[] bb) {

        // Checking whether FilePathUri Is empty or not.

            // Setting progressDialog Title.
            progressDialog.setTitle("Image is Uploading...");

            // Showing progressDialog.
            progressDialog.show();

            // Creating second StorageReference.
            StorageReference storageReference2nd = storageReference.child(Storage_Path + System.currentTimeMillis() + "." + "jpg");

            // Adding addOnSuccessListener to second StorageReference.
            storageReference2nd.putBytes(bb)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            storageReference2nd.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    // Getting image name from EditText and store into string variable.
                                    String titlePost = title_post.getText().toString().trim();
                                    String description = description_post.getText().toString().trim();
                                    String location = locationEditText.getText().toString().trim();

                                    // Hiding the progressDialog after done uploading.
                                    progressDialog.dismiss();
                                    // Showing toast message after done uploading.
                                    Toast.makeText(getContext(), "Post Uploaded Successfully ", Toast.LENGTH_LONG).show();

                                    @SuppressWarnings("VisibleForTests")
                                    PostUploadInfo imageUploadInfo = new PostUploadInfo(titlePost,description,location, uri.toString());

                                    // Getting image upload ID.
                                    String ImageUploadId = databaseReference.push().getKey();
                                    // Adding image upload id s child element into databaseReference.
                                    databaseReference.child(ImageUploadId).setValue(imageUploadInfo);



                                }
                            });
                             }
                    })
                    // If something goes wrong .
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {

                            // Hiding the progressDialog.
                            progressDialog.dismiss();

                            // Showing exception erro message.
                            Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })

                    // On progress change upload time.
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                            // Setting progressDialog Title.
                            progressDialog.setTitle("Post is Uploading...");

                        }
                    });

    }


}