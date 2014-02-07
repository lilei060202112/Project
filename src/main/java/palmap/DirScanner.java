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
				System.out.println(file.getParent().getFileName().toString()+"ÐÞ¸ÄÍê³É");
			}
			return FileVisitResult.CONTINUE;
		}

	}	
	
}
