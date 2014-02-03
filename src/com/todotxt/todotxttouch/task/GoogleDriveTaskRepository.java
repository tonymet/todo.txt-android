/**
 * 
 */
package com.todotxt.todotxttouch.task;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.OpenFileActivityBuilder;

/**
 * @author tonym
 *
 */
public class GoogleDriveTaskRepository implements LocalTaskRepository {

	private DriveId driveId = null; 
	/**
	 * 
	 */
	public GoogleDriveTaskRepository(DriveId pDriveId) {
		this.driveId = pDriveId;
		// TODO Auto-generated constructor stub
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

	/* (non-Javadoc)
	 * @see com.todotxt.todotxttouch.task.LocalTaskRepository#load()
	 */
	@Override
	public ArrayList<Task> load() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.todotxt.todotxttouch.task.LocalTaskRepository#store(java.util.ArrayList)
	 */
	@Override
	public void store(ArrayList<Task> tasks) {
		// TODO Auto-generated method stub

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
