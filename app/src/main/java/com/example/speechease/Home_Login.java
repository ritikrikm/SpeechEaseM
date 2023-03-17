package com.example.speechease;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.speechease.Utils.Save;
import com.example.speechease.Utils.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

import java.util.regex.Pattern;

public class Home_Login extends AppCompatActivity implements TextWatcher,
        CompoundButton.OnCheckedChangeListener {
    Button btnSignUp;
    boolean session;
    Button go;
    EditText emailId, password;
    FirebaseAuth mFirebaseAuth;
    String pstatus;
    ImageView chatbot;
    private ProgressBar progressBars;
    TextView fgt_pass;
    private CheckBox rem_userpass;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private static final String PREF_NAME = "prefs";
    private static final String KEY_REMEMBER = "remember";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASS = "password";
    private FirebaseAuth.AuthStateListener mAuthStateListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_login);
        fgt_pass=findViewById( R.id.frgt_pass );
        fgt_pass.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getApplicationContext(), Change_Pass.class );
                startActivity( intent );
                overridePendingTransition( android.R.anim.fade_in, android.R.anim.fade_out );
            }
        } );
        if(!haveNetworkConnection()){
            Toast.makeText(getApplicationContext(),R.string.network,Toast.LENGTH_LONG).show();
        }
        SESSION();
        progressBars = findViewById(R.id.progressBar2);
        progressBars.setVisibility(View.GONE);

        btnSignUp=findViewById(R.id.signup);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);
            }
        });
        go=findViewById( R.id.go1 );


        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        rem_userpass = findViewById(R.id.checkBox);
        rem_userpass.setChecked(sharedPreferences.getBoolean(KEY_REMEMBER, false));

        mFirebaseAuth = FirebaseAuth.getInstance();

        emailId = findViewById( R.id.Lemail );
        password = findViewById( R.id.Lpass );
        emailId.setText( sharedPreferences.getString( KEY_USERNAME, "" ) );
        password.setText( sharedPreferences.getString( KEY_PASS, "" ) );

        emailId.addTextChangedListener( this );
        password.addTextChangedListener( this );
        rem_userpass.setOnCheckedChangeListener( this );

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if(mFirebaseUser==null){
                    // Toast.makeText(LoginActivity.this,"Please Login",Toast.LENGTH_SHORT).show();*******************************************************************
                }
            }
        };
        go.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  showProgressDialog();
                progressBars.setVisibility(View.VISIBLE);
                if(!haveNetworkConnection()){


                    Toast.makeText(getApplicationContext(),R.string.network,Toast.LENGTH_LONG).show();
                    progressBars.setVisibility(View.GONE);
                }

                String email = emailId.getText().toString();
                String pwd = password.getText().toString();
                if (email.isEmpty()) {
                    // progressBars.setVisibility(View.GONE);
                    progressBars.setVisibility(View.GONE);
                    emailId.setError( getString(R.string.enter_email_id) );
                    emailId.requestFocus();
                } else if (pwd.isEmpty()) {
                    //progressBars.setVisibility(View.GONE);
                    progressBars.setVisibility(View.GONE);
                    Toast.makeText( getApplicationContext(),R.string.required,Toast.LENGTH_SHORT ).show();
                } else if (email.isEmpty() && pwd.isEmpty()) {
                    //   progressBars.setVisibility(View.GONE);
                    progressBars.setVisibility(View.GONE);
                    Toast.makeText( getApplicationContext(), R.string.field, Toast.LENGTH_SHORT ).show();
                } else if (!(email.isEmpty() && pwd.isEmpty())) {
                    //check this runs
                    Log.d( "LOOP 1", "status: login " );//ye lga rhndo

                    mFirebaseAuth.signInWithEmailAndPassword( email, pwd ).addOnCompleteListener( Home_Login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {

                                progressBars.setVisibility(View.GONE);

                                Toast.makeText( Home_Login.this, R.string.wrong, Toast.LENGTH_SHORT ).show();

                            } else {
                                // progressBars.setVisibility(View.GONE);

                                Log.d( ">> NOTWORKING 1", "onComplete: + COME IN LOOP " );

                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
                                databaseReference.keepSynced(true);
                                databaseReference.orderByChild("uid").equalTo(mFirebaseAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                                            User user = dataSnapshot1.getValue(User.class);
                                            assert user != null;


                                            Log.d("HEL**************","**************************************   "+pstatus);

                                            FirebaseUser firebaseUser=mFirebaseAuth.getCurrentUser();       //uid

                                            if ( firebaseUser.isEmailVerified())
                                            {
                                                progressBars.setVisibility(View.GONE);
                                                // Toast.makeText(Login_Student.this, "Successfully logged in", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(Home_Login.this, Dashboard.class);

                                                Save.save(getApplicationContext(),"session","true");
                                                startActivity(intent);
                                                finish();
                                            }
                                            else
                                            {
                                                // email is not verified, so just prompt the message to the user and restart this activity.
                                                // NOTE: don't forget to log out the user.
                                                FirebaseAuth.getInstance().signOut();
                                                progressBars.setVisibility(View.GONE);
                                                Save.save(getApplicationContext(),"session","false");
                                                Toast.makeText(Home_Login.this, R.string.not_verify, Toast.LENGTH_SHORT).show();
                                                //restart this activity
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                        }
                    } );

                }
            }
        } );
    }

    public void SESSION(){
        session= Boolean.valueOf( Save.read(getApplicationContext(),"session","false"));
        if(session){
            //here when user first or logout
            //In here,intent to signup for first reg

            Toast.makeText(this,R.string.already,Toast.LENGTH_LONG).show();
            Intent signup=new Intent(getApplicationContext(),Dashboard.class);
            startActivity(signup);

            finish();
        }
        else{
            //here when user logged in
            //value here is true
            //Toast.makeText(this,"ALREADY LOGGED IN",Toast.LENGTH_SHORT).show();


        }

    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        managePrefs();
    }

    private void managePrefs() {
        if (rem_userpass.isChecked()) {
            editor.putString( KEY_USERNAME, emailId.getText().toString().trim() );
            editor.putString( KEY_PASS, password.getText().toString().trim() );
            editor.putBoolean( KEY_REMEMBER, true );
            editor.apply();
        } else {
            editor.putBoolean( KEY_REMEMBER, false );
            editor.remove( KEY_PASS );//editor.putString(KEY_PASS,"");
            editor.remove( KEY_USERNAME );//editor.putString(KEY_USERNAME, "");
            editor.apply();
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
}