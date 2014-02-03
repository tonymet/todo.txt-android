/**
 * This file is part of Todo.txt for Android, an app for managing your todo.txt file (http://todotxt.com).
 *
 * Copyright (c) 2009-2013 Todo.txt for Android contributors (http://todotxt.com)
 *
 * LICENSE:
 *
 * Todo.txt for Android is free software: you can redistribute it and/or modify it under the terms of the GNU General
 * Public License as published by the Free Software Foundation, either version 2 of the License, or (at your option) any
 * later version.
 *
 * Todo.txt for Android is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the 
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with Todo.txt for Android. If not, see
 * <http://www.gnu.org/licenses/>.
 *
 * Todo.txt for Android's source code is available at https://github.com/ginatrapani/todo.txt-android
 *
 * @author Todo.txt for Android contributors <todotxt@yahoogroups.com>
 * @license http://www.gnu.org/licenses/gpl.html
 * @copyright 2009-2013 Todo.txt for Android contributors (http://todotxt.com)
 */
package com.todotxt.todotxttouch;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.OpenFileActivityBuilder;
import com.todotxt.todotxttouch.remote.RemoteClient;
import com.todotxt.todotxttouch.util.Util;

public class LoginScreen extends Activity 
	implements 
	GoogleApiClient.ConnectionCallbacks,
	GoogleApiClient.OnConnectionFailedListener {
	final static String TAG = LoginScreen.class.getSimpleName();

	private static final int REQUEST_CODE_OPENER = 0x01;

	private TodoApplication m_app;
	private Button m_LoginButton;
	private BroadcastReceiver m_broadcastReceiver; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Log.i(TAG, "LoginScreen onCreate");

		setContentView(R.layout.login);

		m_app = (TodoApplication) getApplication();

		// supposed to help with the banding on the green background
		findViewById(R.id.loginbackground).getBackground().setDither(true);

		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("com.todotxt.todotxttouch.ACTION_LOGIN");
		m_broadcastReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				switchToTodolist();
			}
		};

		registerReceiver(m_broadcastReceiver, intentFilter);

		m_LoginButton = (Button) findViewById(R.id.login);
		m_LoginButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startLogin();
			}
		});

		RemoteClient remoteClient = m_app.getRemoteClientManager()
				.getRemoteClient();

		if (remoteClient.isAuthenticated()) {
			switchToTodolist();
		}
	}

	private void switchToTodolist() {
		Intent intent = new Intent(this, TodoTxtTouch.class);
		startActivity(intent);
		finish();
	}

	@Override
    protected void onResume() {
        super.onResume();
        if (m_app.getGoogleApiClient() == null) {
            m_app.setGoogleApiClient(
            		new GoogleApiClient.Builder(this)
	                    .addApi(Drive.API)
	                    .addScope(Drive.SCOPE_FILE)
	                    .addConnectionCallbacks(this)
	                    .addOnConnectionFailedListener(this)
	                    .build()
            );
        }
        Log.i(TAG, "onResume(): reconnecting Google Drive");
        m_app.getGoogleApiClient().connect();
        finishLogin();
    }

	private void finishLogin() {
		RemoteClient remoteClient = m_app.getRemoteClientManager()
				.getRemoteClient();

		if (remoteClient.finishLogin() && remoteClient.isAuthenticated()) {
			Log.i(TAG, "LoginScreen: login complete: about to start the app.");

			Intent broadcastLoginIntent = new Intent(
					"com.todotxt.todotxttouch.ACTION_LOGIN");
			sendBroadcast(broadcastLoginIntent);
		} else {
			Log.i(TAG, "LoginScreen: not logged in. Showing login screen.");
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(m_broadcastReceiver);
	}

	void startLogin() {
		final RemoteClient client = m_app.getRemoteClientManager()
				.getRemoteClient();

		if (!client.isAvailable()) {
			Log.d(TAG, "Remote service " + client.getClass().getSimpleName()
					+ " is not available; aborting login");

			Util.showToastLong(m_app, R.string.toast_login_notconnected);
		} else {
			client.startLogin();
		}
	}
	// tonym for google drive
	   @Override
	    public void onConnected(Bundle connectionHint) {
		   // choose file for Google drive and save file id in local preferences
	        Log.i(TAG, "GoogleApiClient connected");
	        // pop file chooser and save to file location
            IntentSender intentSender = Drive.DriveApi
                    .newOpenFileActivityBuilder()
                    //.setMimeType(new String[] { "application/vnd.google-apps.folder" })
                    .setMimeType(new String[] { "text/plain" })
                    .build(m_app.getGoogleApiClient());
            try {
                startIntentSenderForResult(
                        intentSender, 0x01, null, 0, 0, 0);
            } catch (SendIntentException e) {
              Log.w(TAG, "Unable to send intent", e);
            }
	    }

	    /**
	     * Called when {@code mGoogleApiClient} is disconnected.
	     */
	    @Override
	    public void onDisconnected() {
	        Log.i(TAG, "GoogleApiClient disconnected");
	    }
	    @Override
	    public void onConnectionFailed(ConnectionResult result) {
	        Log.i(TAG, "GoogleApiClient connection failed: " + result.toString());
	        if (!result.hasResolution()) {
	            // show the localized error dialog.
	            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this, 0).show();
	            return;
	        }
	        try {
	        	// todo tonym update 2nd argument to something meaninful
	            result.startResolutionForResult(this, 0x01);
	        } catch (SendIntentException e) {
	            Log.e(TAG, "Exception while starting resolution activity", e);
	        }
	    }
	    
	    @Override
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	        switch(requestCode) {
		        case REQUEST_CODE_OPENER:
		            if (resultCode == RESULT_OK) {
		                DriveId driveId = (DriveId) data.getParcelableExtra(
		                        OpenFileActivityBuilder.EXTRA_RESPONSE_DRIVE_ID);
		                Log.i(TAG,"Selected folder's ID: " + driveId);
		                Log.i(TAG,"Selected folder's resource ID: " + driveId.getResourceId());
		                m_app.m_prefs.setGoogleDriveResourceId(driveId.getResourceId());
		            }
		            finish();
		            switchToTodolist();
		            break;
	        default:
	        	Log.e(TAG, "Error requestCode not specified");
	            break;
	        }
	    }

}
