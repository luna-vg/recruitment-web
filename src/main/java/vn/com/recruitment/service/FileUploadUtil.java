package vn.com.recruitment.service;

import java.io.IOException;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {
	
	public static void saveFile(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException {
		
		Path uploadPath = Paths.get(uploadDir);
		
		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}
		
		try (InputStream inputStream = multipartFile.getInputStream()) {
			Path filePath = uploadPath.resolve(fileName);
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException ioe) {
			throw new IOException("Could not save cv file: " + fileName, ioe);
		}
	}

	public static void copyFile(String sourcePathCv, String copyPathCv, String fileName) throws IOException {
		
		Path sourcePath = Paths.get(sourcePathCv);
		
		Path copyPath = Paths.get(copyPathCv);
		
		if (!Files.exists(copyPath)) {
			Files.createDirectories(copyPath);
		}
		
		try {
			Path sourceFile = sourcePath.resolve(fileName);
			Path copyFile = copyPath.resolve(fileName);
			Files.copy(sourceFile, copyFile, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException ioe) {
			throw new IOException("Could not save user default cv", ioe);
		}
		
	}

}
