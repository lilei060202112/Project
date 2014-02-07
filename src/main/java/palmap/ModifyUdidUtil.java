package palmap;

import java.io.IOException;

public class ModifyUdidUtil {
	
	public DirScanner ds;
	
	public ModifyUdidUtil(String srcPath, String dirPath) throws IOException {
		ds = new DirScanner(srcPath, dirPath);
	}

	public void modifyMdb() throws IOException {
		ds.scanDir();
	}
	
}
