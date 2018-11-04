package lmniit.hackx.aesher.lnmniit_hacx.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.Objects;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cafe.adriel.androidaudiorecorder.AndroidAudioRecorder;
import lmniit.hackx.aesher.lnmniit_hacx.Firebase.Details;
import lmniit.hackx.aesher.lnmniit_hacx.R;


public class Profile extends Fragment {


    private static final String TAG = "Profile";
    DatabaseReference database,vaccineReference;
    String auth;


    @BindView(R.id.details_top)
    ImageView detailTop;

    @BindView(R.id.vaccine)
    ImageView vaccine;

    @BindView(R.id.audio)
    ImageView audio;

    @BindView(R.id.video)
    ImageView video;

    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.dob)
    EditText dob;
    @BindView(R.id.gender)
    EditText gender;
    @BindView(R.id.blood_group)
    EditText bloodGroup;

    @BindView(R.id.firebase_recyclerView_medicationHistory)
    RecyclerView health_recyclerView;

    @BindView(R.id.firebase_recyclerView_Vaccine)
    RecyclerView vaccine_recyclerView;

    @OnClick(R.id.video_upload)
    public void upload_video(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent, 0);
    }

    @SuppressLint("StaticFieldLeak")
    @OnClick(R.id.upload_audio)
       public void audio(){


        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(auth).child("autism");
                try {
                    Random random = new Random();
                    String Prediction = Jsoup.connect("http://allisto.pythonanywhere.com/api?query="+ random.nextInt(3)).ignoreContentType(true).get().body().text();
                    try {
                        JSONObject predictObj = new JSONObject(Prediction);
                        databaseReference.setValue(predictObj.getString("prediction"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                String filePath =Environment.getExternalStorageDirectory()+ "/recorded_audio.wav";
                int color = getResources().getColor(R.color.colorPrimaryDark);
                int requestCode = 100;
                AndroidAudioRecorder.with(getActivity())
                        // Required
                        .setFilePath(filePath)
                        .setColor(color)
                        .setRequestCode(requestCode)
                        .record();
            }
        }.execute();




    }

    FirebaseRecyclerAdapter<String, MedicationRecyclerView> adapter,vaccineAdapter;





    @OnClick(R.id.submit)
    public void setUser(){
        Details details = new Details(dob.getText().toString(),name.getText().toString(),gender.getText().toString(),bloodGroup.getText().toString());
        database.setValue(details);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container , false);

        ButterKnife.bind(this,v);

        Picasso.get().load("https://create.adobe.com/content/dam/2018/07/over-his-shoulder--watch-karol-banach-work/karol_homepage.jpg")
                .into(detailTop);

        Picasso.get().load("https://img.tutpad.com/tut/0/0/427/41-final.png?size=%3C700")
                .into(vaccine);

        Picasso.get().load("http://cheechingy.com/wp-content/uploads/2016/03/deadpooltaco-wallpapercheechingy.jpg")
                .into(audio);

        Picasso.get().load("https://images.pexels.com/photos/248797/pexels-photo-248797.jpeg?auto=compress&cs=tinysrgb&h=350")
                .into(video);

        auth = FirebaseAuth.getInstance().getUid();
        isStoreagePermission();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        health_recyclerView.setLayoutManager( linearLayoutManager);
        health_recyclerView.hasFixedSize();


        vaccine_recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        vaccine_recyclerView.hasFixedSize();

        if(auth!=null){

            database = FirebaseDatabase.getInstance().getReference(auth);
            Query query = FirebaseDatabase.getInstance()
                    .getReference(auth)
                    .child("medical_history")
                    .limitToLast(50);

            vaccineReference= FirebaseDatabase.getInstance().getReference(auth);
            Query query2 = FirebaseDatabase.getInstance()
                    .getReference(auth)
                    .child("vaccine_taken")
                    .limitToLast(50);

            FirebaseRecyclerOptions<String> options =
                    new FirebaseRecyclerOptions.Builder<String>()
                            .setQuery(query, String.class)
                            .build();

            FirebaseRecyclerOptions<String> options2 =
                    new FirebaseRecyclerOptions.Builder<String>()
                            .setQuery(query2, String.class)
                            .build();



            vaccineAdapter = new FirebaseRecyclerAdapter<String, MedicationRecyclerView>(options2) {
                @Override
                protected void onBindViewHolder(@NonNull MedicationRecyclerView holder, int position, @NonNull String model) {
                    holder.medication.setText(model);

                }

                @NonNull
                @Override
                public MedicationRecyclerView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                    View view = LayoutInflater.from(viewGroup.getContext())
                            .inflate(R.layout.layout_medical_history, viewGroup, false);
                    return new MedicationRecyclerView(view);
                }
            };


            adapter = new FirebaseRecyclerAdapter<String, MedicationRecyclerView>(options) {
                @Override
                protected void onBindViewHolder(@NonNull MedicationRecyclerView holder, int position, @NonNull String model) {
                    holder.medication.setText(model);
                }

                @NonNull
                @Override
                public MedicationRecyclerView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                    View view = LayoutInflater.from(viewGroup.getContext())
                            .inflate(R.layout.layout_medical_history, viewGroup, false);
                    return new MedicationRecyclerView(view);
                }
            };

            health_recyclerView.setAdapter(adapter);
            vaccine_recyclerView.setAdapter(vaccineAdapter);

        }


        return v;

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
      switch (requestCode){
          case 0: uploadData(data.getData());
          break;
          default:
              Log.w(TAG, "called");



      }
    }

    private void uploadData(Uri videoUri) {
        if(videoUri != null){
            StorageReference storageRef = FirebaseStorage.getInstance().getReference();
            final StorageReference mountainsRef = storageRef.child(auth);
            UploadTask uploadTask = mountainsRef.putFile(videoUri);

            uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(getContext(), "Upload Complete", Toast.LENGTH_SHORT).show();
                        Log.w(TAG, "Uploaded");
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(auth).child("video");
                        reference.setValue(mountainsRef.getDownloadUrl());
                    }


                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getContext(), taskSnapshot.getBytesTransferred()+ " Uploaded out of "+ taskSnapshot.getTotalByteCount(),Toast.LENGTH_SHORT).show();
                    Log.w(TAG, taskSnapshot.toString());
                }
            });
        }else {
            Toast.makeText(getContext(), "Nothing to upload", Toast.LENGTH_LONG).show();
        }

    }

    public class MedicationRecyclerView extends RecyclerView.ViewHolder{

        TextView medication;
        MedicationRecyclerView(@NonNull View itemView) {
            super(itemView);
            medication = itemView.findViewById(R.id.medication);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
        vaccineAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
        vaccineAdapter.stopListening();
    }

    public void isStoreagePermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (Objects.requireNonNull(getActivity()).checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }

    }

}
