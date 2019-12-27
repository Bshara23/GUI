package Entities;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

public class File extends SqlObject implements Serializable {

	private static File emptyInstance = new File(0, 0, null, null);
	public static File getEmptyInstance() {
		return emptyInstance;
	}
	
	public long ID, requestID;
	public String fileName, type;
	

	public File(long iD, long requestID , String fileName, String type) {
		super();
		ID = iD;
		this.requestID = requestID;
		this.fileName = fileName;
		this.type = type;
		
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public long getID() {
		return ID;
	}

	public void setID(long iD) {
		ID = iD;
	}

	public long getRequestID() {
		return requestID;
	}

	public void setRequestID(long requestID) {
		this.requestID = requestID;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public int getPrimaryKeyIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getForeignKeyIndex() {
		return 1;
	}

	@Override
	public String getReferenceTableName() {
		return "ChangeRequest";
	}

	@Override
	public boolean hasForeignKey() {
		return true;
	}

	@Override
	public String getReferenceTableForeignKeyName() {
		// TODO Auto-generated method stub
		return "requestID";
	}

	@Override
	public int fieldsLastIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isPrimaryKeyIncremental() {
		return true;
	}

	private byte[] storedBytes;
	
	public void loadBytesFromLocal() {
		java.io.File file= new java.io.File(fileName);
		try {
			FileInputStream inputStream= new FileInputStream(file);
			BufferedInputStream bis = new BufferedInputStream(inputStream);
			storedBytes = new byte[(int) file.length()];
			bis.read(storedBytes, 0, storedBytes.length);
			bis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void autoSetTypeAndNameFromLocation() {
		
		setType(getFileName().substring(getFileName().lastIndexOf('.') + 1));
		setFileName(getFileName().substring(getFileName().lastIndexOf('/') + 1));
	}

	public InputStream getBinaryStream() {
		return new ByteArrayInputStream(storedBytes);
	}
	
	public void setBytes(InputStream is, int lenght) {
		try {
			storedBytes = new byte[lenght];
			is.read(storedBytes);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void writeFileToLocal() {
		try {
			InputStream is = getBinaryStream();
			FileOutputStream fos = new FileOutputStream("2332" + getFileName());

			BufferedOutputStream bos = new BufferedOutputStream(fos);

			// Starts writing the bytes in it
			try {
				byte[] buffer = new byte[1];
				while (is.read(buffer) > 0) {
					bos.write(buffer);
				}

				bos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
}
