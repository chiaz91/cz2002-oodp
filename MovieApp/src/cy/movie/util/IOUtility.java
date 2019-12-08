package cy.movie.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import cy.movie.AppConstants;
import cy.movie.Log;

public class IOUtility implements AppConstants {
	
	// using interface allow asynchronous saving in background thread (not implemented here) 
	public static interface LoadDataInterface{
		void beforeLoading();
		void afterLoading(Object[] data, Exception e);
	}
	

	public static interface SaveDataInterface{
		void beforeSaving();
		Object[] getSavingData(); 
		void afterSaving(boolean success, Exception e);
	}
	

	public static boolean load(String fileName, LoadDataInterface loadInf) {
		boolean isSuccess = false;
		File dataFolder = new File(PATH_DATA_FOLDER);
		if (!dataFolder.exists()) {
			dataFolder.mkdirs();
			return false;
		}
		
		File dataFile = new File(dataFolder, fileName);
		if (!dataFile.exists()) {
			return false;
		}
		
		loadInf.beforeLoading();
		Object[] data;
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		
		try {
			fis = new FileInputStream(dataFile.toString());
			ois = new ObjectInputStream(fis);
			data = (Object[]) ois.readObject();
			ois.close();
			isSuccess = true;
			loadInf.afterLoading(data, null);
		} catch (Exception e) {
			loadInf.afterLoading(null, e);
			Log.e(e.getMessage());
			if (IS_TESTING) {
				e.printStackTrace();
			}
		} finally {
			if (ois != null) {
				try {
					ois.close();
				}catch (IOException e) {
					Log.e(e.getMessage());
				}
			}
			if (fis != null) {
				try {
					fis.close();
				}catch (IOException e) {
					Log.e(e.getMessage());
				}
			}

		}
		return isSuccess;
	}
	public static boolean save(String fileName, SaveDataInterface saveIntf) {
		saveIntf.beforeSaving();
		boolean isSuccess = false;
		// check date folder
		File dataFolder = new File(PATH_DATA_FOLDER);
		if (!dataFolder.exists()) {
			Log.i("Create path: "+dataFolder.getAbsolutePath());
			dataFolder.mkdirs();
		}
		
		// prepare writing to file
		Object[] data = saveIntf.getSavingData();
		
		File dataFile = new File(dataFolder, fileName);
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			fos = new FileOutputStream(dataFile.toString());
			oos = new ObjectOutputStream(fos);
			oos.writeObject(data);
			isSuccess = true;
			saveIntf.afterSaving(isSuccess, null);
		} catch (Exception e) {
			saveIntf.afterSaving(isSuccess, e);
			Log.e(e.getMessage());
			if (IS_TESTING) {
				e.printStackTrace();
			}
		} finally {
			if (oos != null) {
				try {
					oos.close();
				}catch (IOException e) {
					Log.e(e.getMessage());
				}
			}
			if (fos != null) {
				try {
					fos.close();
				}catch (IOException e) {
					Log.e(e.getMessage());
				}
			}

		}
		return isSuccess;
	}

}
