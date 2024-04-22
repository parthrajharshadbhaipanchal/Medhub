package org.asdc.medhub.Utility;

import io.jsonwebtoken.io.IOException;
import org.junit.Test;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UtilityMethodsTest {
    @Test
    public void testGetBase64StringFromImage_Success() throws Exception {
        //Arrange
        String filePath="testImage.png";
        String filePathFull=System.getProperty("user.dir")+"/"+filePath;
        String base64Image="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";
        byte[] imageBytes = Base64.getDecoder().decode(base64Image);
        FileOutputStream fos = new FileOutputStream(filePathFull);
        fos.write(imageBytes);
        fos.close();

        //Action
        var result=UtilityMethods.getBase64StringFromImage(System.getProperty("user.dir")+"/",filePath);
        Files.delete(Path.of(filePathFull));

        //Assert
        assertEquals(base64Image,result);
    }

    @Test
    public void testGetBase64StringFromImage_FileNotFound() throws IOException {
        // Mocking the profilePictureFolderPath and fileName
        String profilePictureFolderPath = "/path/to/nonexistent/folder";
        String fileName = "nonexistentImage.jpg";

        // Call the method under test
        String result = UtilityMethods.getBase64StringFromImage(profilePictureFolderPath, fileName);

        // Assert that the result is null when file is not found
        assertNull(result);
    }
}
