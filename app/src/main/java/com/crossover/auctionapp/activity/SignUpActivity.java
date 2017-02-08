package com.crossover.auctionapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.crossover.auctionapp.R;
import com.crossover.auctionapp.facade.PersistenceFacade;
import com.crossover.auctionapp.utils.ExtraUtils;
import com.crossover.auctionapp.utils.ValidationUtils;
import com.crossover.auctionapp.vo.UserVO;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    PersistenceFacade facade = new PersistenceFacade();
    private String login;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        login = getIntent().getStringExtra(ExtraUtils.LOGIN_EXTRA);
        password = getIntent().getStringExtra(ExtraUtils.PASS_EXTRA);
        FloatingActionButton saveButton = (FloatingActionButton) findViewById(R.id.saveProfile);
        saveButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //Save profile data
        EditText name = (EditText) findViewById(R.id.prf_nameText);
        EditText lastName = (EditText) findViewById(R.id.prf_lastNameText);
        EditText age = (EditText) findViewById(R.id.prf_ageText);
        EditText document = (EditText) findViewById(R.id.prf_idDocumentText);
        int selectedGenderOption = ((RadioGroup) findViewById(R.id.prf_genderOptions)).getCheckedRadioButtonId();
        View emptyView = null;
        // Validate mandatory fields
        if(TextUtils.isEmpty(name.getText())){
            emptyView = name;
        }else if(TextUtils.isEmpty(lastName.getText())){
            emptyView = lastName;
        }else if(TextUtils.isEmpty(age.getText())){
            emptyView = age;
        }else if(TextUtils.isEmpty(document.getText())){
            emptyView = document;
        }

        if(null != emptyView){
            emptyView.requestFocus();
            ValidationUtils.showEmptyFieldMessage(emptyView);
        }else{
            if(selectedGenderOption >0){
                RadioButton genderView = (RadioButton)findViewById(selectedGenderOption);
                // Everything is ok , save form
                UserVO user = new UserVO(null,login,name.getText().toString(),lastName.getText().toString(),Integer.valueOf(age.getText().toString()),genderView.getText().toString(),Integer.valueOf(document.getText().toString()),password);
                boolean isSaved = facade.insertUser(this,user);
                if(isSaved){
                    //Go to next activity
                    Snackbar.make(v, "Saved", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.putExtra(ExtraUtils.LOGIN_EXTRA,login);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }else{
                    Snackbar.make(v, "Error saving user, try later...", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }else{
                Snackbar.make(v, "Select a gender option", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }
    }


}
