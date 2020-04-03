package thread.file;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class FileServerThread extends Thread {
	FileServer fs = null;
	public FileServerThread() {
	
	}
	public FileServerThread(FileServer fs) {
		this.fs = fs;
	}
	@Override
	public void run() {
		boolean isStop = false;
		while(!isStop) {
			try {
				String fileName = "src\\emoticon\\lion11.png";
				OutputStream out = fs.socket.getOutputStream();
				InputStream is = new FileInputStream(fileName);
				int readcount = 0;
				byte buffer[] = new byte[512];
				while((readcount = is.read())!=-1) {
					out.write(buffer,0,readcount);
				}
				out.close();
				is.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	
}
