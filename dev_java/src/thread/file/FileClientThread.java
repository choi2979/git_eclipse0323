package thread.file;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class FileClientThread extends Thread {
	FileClient fc = null;
	public FileClientThread() {
	
	}
	public FileClientThread(FileClient fc) {
		this.fc = fc;
	}
	@Override
	public void run() {
		try {
			String fileName = "src\\emoticon\\lion1.png";
			OutputStream out = new FileOutputStream(fileName);
			InputStream is = fc.socket.getInputStream();
			int readcount = 0;
			byte buffer[] = new byte[512];
			while((readcount = is.read())!=-1) {
				out.write(buffer,0,readcount);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
