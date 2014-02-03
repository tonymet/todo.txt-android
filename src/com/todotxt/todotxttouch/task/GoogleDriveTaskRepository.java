/**
 * 
 */
package com.todotxt.todotxttouch.task;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.OpenFileActivityBuilder;
import com.google.android.gms.drive.DriveApi.ContentsResult;
import com.todotxt.todotxttouch.TodoException;
import com.todotxt.todotxttouch.TodoPreferences;
import com.todotxt.todotxttouch.util.TaskIo;

/**
 * @author tonym
 *
 */
public class GoogleDriveTaskRepository implements LocalTaskRepository {

	private static final String TAG = "GoogleDriveTaskRepository";
	private DriveId driveId = null; 
	private GoogleApiClient api = null;
	/**
	 * 
	 */
	public GoogleDriveTaskRepository(String resourceId, GoogleApiClient api) {
		this.api = api;
		this.driveId = DriveId.createFromResourceId(resourceId);
		// TODO Auto-generated constructor stub
	}

	 private class RetrieveDriveFileContentsAsyncTask extends AsyncTask<DriveId, Boolean, String> {

	        private static final String TAG = "RetrieveDriveFileContentsAsyncTask";

			@Override
	        protected String doInBackground(DriveId... params) {
	            String contents = null;
	            DriveFile file = Drive.DriveApi.getFile(api, params[0]);
	            ContentsResult contentsResult =
	                    file.openContents(api, DriveFile.MODE_READ_ONLY, null).await();
	            if (!contentsResult.getStatus().isSuccess()) {
	                return null;
	            }
	            BufferedReader reader = new BufferedReader(
	                    new InputStreamReader(contentsResult.getContents().getInputStream()));
	            StringBuilder builder = new StringBuilder();
	            String line;
	            try {
	                while ((line = reader.readLine()) != null) {
	                    builder.append(line);
	                }
	                contents = builder.toString();
	            } catch (IOException e) {
	                Log.e(TAG, "IOException while reading from the stream", e);
	            }

	            file.discardContents(api, contentsResult.getContents()).await();
	            return contents;
	        }

	        @Override
	        protected void onPostExecute(String result) {
	            super.onPostExecute(result);
	            if (result == null) {
	                Log.i(TAG,"Error while reading from the file");
	                return;
	            }
	            try {
	            	TaskIo.loadTasksFromInputStream(new ByteArrayInputStream(result.getBytes("UTF-8")));
	            } catch (IOException e) {
	              throw new TodoException("Error loading from local file", e);
	            }
	        }
	   }
	/* (non-Javadoc)
	 * @see com.todotxt.todotxttouch.task.LocalTaskRepository#init()
	 */
	@Override
	public void init() {
		// (DriveId) data.getParcelableExtra(
        // OpenFileActivityBuilder.EXTRA_RESPONSE_DRIVE_ID);
		// TODO trigger file open
		//make sure driveId is set here. 
       
    }

	/* (non-Javadoc)
	 * @see com.todotxt.todotxttouch.task.LocalTaskRepository#purge()
	 */
	@Override
	public void purge() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)buf
	 * @see com.todotxt.todotxttouch.task.LocalTaskRepository#load()
	 */
	/*
	@Override
	public ArrayList<Task> loadAsync() {
		 new RetrieveDriveFileContentsAsyncTask()
         .execute(driveId);
		// TODO Auto-generated method stub
		return null;
	}
	*/
	
	@Override
	public ArrayList<Task> load(){
          DriveFile file = Drive.DriveApi.getFile(api, driveId);
          ContentsResult contentsResult =
                  file.openContents(api, DriveFile.MODE_READ_ONLY, null).await();
          if (!contentsResult.getStatus().isSuccess()) {
              return null;
          }
          try{
        	  return TaskIo.loadTasksFromInputStream( contentsResult.getContents().getInputStream());
          }
          catch(IOException e){
        	  Log.e(TAG, e.toString());
        	  
          }
          return null;
	}

	/* (non-Javadoc)
	 * @see com.todotxt.todotxttouch.task.LocalTaskRepository#store(java.util.ArrayList)
	 */
	@Override
	public void store(ArrayList<Task> tasks) {
		DriveFile file = Drive.DriveApi.getFile(api, driveId);
        ContentsResult contentsResult = file.openContents(
                api,  DriveFile.MODE_WRITE_ONLY, null).await();
        if (!contentsResult.getStatus().isSuccess()) {
            Log.e(TAG, "ERROR: failure writing contents");
        }
        OutputStream outputStream = contentsResult.getContents().getOutputStream();
        TaskIo.writeToOutputStream(tasks, outputStream);
        com.google.android.gms.common.api.Status status = file.commitAndCloseContents(
                api, contentsResult.getContents()).await();
	}

	/* (non-Javadoc)
	 * @see com.todotxt.todotxttouch.task.LocalTaskRepository#archive(java.util.ArrayList)
	 */
	@Override
	public void archive(ArrayList<Task> tasks) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.todotxt.todotxttouch.task.LocalTaskRepository#loadDoneTasks()
	 */
	@Override
	public ArrayList<Task> loadDoneTasks() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.todotxt.todotxttouch.task.LocalTaskRepository#storeDoneTasks(java.util.ArrayList)
	 */
	@Override
	public void storeDoneTasks(ArrayList<Task> tasks) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.todotxt.todotxttouch.task.LocalTaskRepository#storeDoneTasks(java.io.File)
	 */
	@Override
	public void storeDoneTasks(File file) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.todotxt.todotxttouch.task.LocalTaskRepository#todoFileModifiedSince(java.util.Date)
	 */
	@Override
	public boolean todoFileModifiedSince(Date date) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.todotxt.todotxttouch.task.LocalTaskRepository#doneFileModifiedSince(java.util.Date)
	 */
	@Override
	public boolean doneFileModifiedSince(Date date) {
		// TODO Auto-generated method stub
		return false;
	}

}
