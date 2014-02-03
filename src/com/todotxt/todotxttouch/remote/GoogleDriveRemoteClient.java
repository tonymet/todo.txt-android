/**
 * 
 */
package com.todotxt.todotxttouch.remote;

import java.io.File;
import java.util.List;

/**
 * @author tonym
 *
 */
public class GoogleDriveRemoteClient implements RemoteClient {

	/**
	 * 
	 */
	public GoogleDriveRemoteClient() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.todotxt.todotxttouch.remote.RemoteClient#getClient()
	 */
	@Override
	public Client getClient() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.todotxt.todotxttouch.remote.RemoteClient#authenticate()
	 */
	@Override
	public boolean authenticate() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.todotxt.todotxttouch.remote.RemoteClient#startLogin()
	 */
	@Override
	public boolean startLogin() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.todotxt.todotxttouch.remote.RemoteClient#finishLogin()
	 */
	@Override
	public boolean finishLogin() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.todotxt.todotxttouch.remote.RemoteClient#deauthenticate()
	 */
	@Override
	public void deauthenticate() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.todotxt.todotxttouch.remote.RemoteClient#isAuthenticated()
	 */
	@Override
	public boolean isAuthenticated() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.todotxt.todotxttouch.remote.RemoteClient#isLoggedIn()
	 */
	@Override
	public boolean isLoggedIn() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.todotxt.todotxttouch.remote.RemoteClient#pullTodo()
	 */
	@Override
	public PullTodoResult pullTodo() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.todotxt.todotxttouch.remote.RemoteClient#pushTodo(java.io.File, java.io.File, boolean)
	 */
	@Override
	public void pushTodo(File todoFile, File doneFile, boolean overwrite) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.todotxt.todotxttouch.remote.RemoteClient#isAvailable()
	 */
	@Override
	public boolean isAvailable() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.todotxt.todotxttouch.remote.RemoteClient#getSubFolders(java.lang.String)
	 */
	@Override
	public List<RemoteFolder> getSubFolders(String path) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.todotxt.todotxttouch.remote.RemoteClient#getFolder(java.lang.String)
	 */
	@Override
	public RemoteFolder getFolder(String path) {
		// TODO Auto-generated method stub
		return null;
	}

}
