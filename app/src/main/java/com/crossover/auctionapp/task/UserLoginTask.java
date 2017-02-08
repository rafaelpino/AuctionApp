package com.crossover.auctionapp.task;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.crossover.auctionapp.activity.LoginActivity;
import com.crossover.auctionapp.R;
import com.crossover.auctionapp.activity.IActivity;
import com.crossover.auctionapp.activity.MainActivity;
import com.crossover.auctionapp.activity.SignUpActivity;
import com.crossover.auctionapp.facade.PersistenceFacade;
import com.crossover.auctionapp.utils.ExtraUtils;
import com.crossover.auctionapp.vo.UserVO;

/**
 * Created by rafaelpino on 12/13/16.
 * Represents an asynchronous login/registration task used to authenticate
 * the user.
 */
public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
    private final String mEmail;
    private final String mPassword;
    private IActivity callBackActivity;
    private PersistenceFacade facade;
    private Context context;

    public UserLoginTask(String email, String password, IActivity activity) {
        mEmail = email;
        mPassword = password;
        callBackActivity = activity;
        this.facade = new PersistenceFacade();
        this.context = ((AppCompatActivity) callBackActivity).getBaseContext();
    }

    /***
     * Authentication against database
     * @param params
     * @return
     */
    @Override
    protected Boolean doInBackground(Void... params) {
        return facade.authenticate(context,mEmail,mPassword);
    }

    /***
     * Go to next activiy if user is authenticated or registration form otherwise
     * @param success
     */
    @Override
    protected void onPostExecute(final Boolean success) {
        callBackActivity.showProgress(false);
        callBackActivity.clearTasks();

        if (success) {
            // Go to main activity
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra(ExtraUtils.LOGIN_EXTRA,mEmail);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } else {
            UserVO user = facade.getUserByLogin(context, mEmail);
            if(null == user){ // If user doesnt exist go to signup activity
                Intent intent = new Intent(context, SignUpActivity.class);
                intent.putExtra(ExtraUtils.LOGIN_EXTRA,mEmail);
                intent.putExtra(ExtraUtils.PASS_EXTRA,mPassword);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }else{ //Otherwise show password incorrect error
                if ( callBackActivity instanceof LoginActivity){
                    EditText mPasswordView = ((LoginActivity)callBackActivity).getPasswordView();
                    mPasswordView.setError(((LoginActivity) callBackActivity).getBaseContext().getString(R.string.error_incorrect_password));
                    mPasswordView.requestFocus();
                }
            }
        }
    }

    @Override
    protected void onCancelled() {
        callBackActivity.showProgress(false);
    }
}
