package palmap;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class DirScanner {

	private String srcPath;
	private String dirPath;
	public int count = 0;
	private boolean floorexsits = false;
	
	public DirScanner(String srcPath, String dirPath) throws IOException {
		this.srcPath = srcPath;
		this.dirPath = dirPath;
	}

	public void scanDir() throws IOException {
		Path path = Paths.get(dirPath);
		Files.walkFileTree(path, new FileVis());
		
	}
	
	private class FileVis extends SimpleFileVisitor<Path> {
		
		private final Path floorMdb = Paths.get("Floor.mdb");
		
		@Override
		public FileVisitResult visitFile (Path file, BasicFileAttributes attrs)
				throws IOException {
			Path fileName = file.getFileName();
			if (fileName.equals(floorMdb)) {
				MdbOper mdbOper = new MdbOper(srcPath, file.toString());
				mdbOper.setNewUDID("Mall");
				count++;
				floorexsits = true;
				System.out.println(file.getParent().getFileName().toString()+"修改完成");
			}
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc)
				throws IOException {
			if (dir.getParent().equals(Paths.get(dirPath))) {
				if (floorexsits) {
					floorexsits = false;
				} else {
					System.out.println(dir.getFileName()+"不包含Floor.mdb");
				}
			}
			
			return FileVisitResult.CONTINUE;
		}
		

	}	
	
}
