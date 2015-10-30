package sh.dreamingfi.utils.pcm2wav;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

public class TestWAVHeader {

    @Test
    public void test() {
        InputStream inputStream = TestWAVHeader.class.getResourceAsStream(
                "/crowdtest-2015-10-27-2094633209562f42af3d5e37.30491309_6195513_alex-car-00008.m4a.wav");
        assertNotNull(inputStream);
        byte[] bytes = new byte[44];
        WAVHeader header = new WAVHeader();

        try {
            int read = inputStream.read(bytes);
            if (read >= 44) {
                assertTrue(header.parseHeader(bytes));
                System.out.println(header.printFormat());
            }
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.getLocalizedMessage());
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        inputStream = TestWAVHeader.class.getResourceAsStream("/alex-car-00001.m4a.pcm");
        assertNotNull(inputStream);
        bytes = new byte[30000];
        try {
            int read = inputStream.read(bytes, 44, 30000 - 44);
            header.setSubChunk2Size(read);
            header.writeHeader(bytes, 0);
            File outputFile = new File("./alex-car-00001.m4a.wav");
            if (!outputFile.exists()) {
                outputFile.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
            fileOutputStream.write(bytes, 0, read + 44);
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.getLocalizedMessage());
        }
    }

}
