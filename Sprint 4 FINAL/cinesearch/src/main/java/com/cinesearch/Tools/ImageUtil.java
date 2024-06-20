/* 

package com.cinesearch.Tools;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.internal.ExceptionConverterImpl;

import com.cinesearch.DAO.FilmeDAO;

import reactor.core.Exceptions;

public class ImageUtil {
    static String path = "C:\\Users\\tulio\\Downloads\\cinesearch\\cinesearch\\src\\main\\java\\com\\cinesearch\\posters";

    public static byte[] imageToByteArray(String imagePath) throws IOException {
        try (FileInputStream fis = new FileInputStream(imagePath);
             ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }
            return bos.toByteArray();
        }
    }

    public static List<byte[]> convertImagesInDirectory(String directoryPath) throws IOException {
        List<byte[]> imagesData = new ArrayList<>();
        File directory = new File(directoryPath);

        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".jpg"));
            if (files != null) {
                for (File file : files) {
                    byte[] imageData = imageToByteArray(file.getAbsolutePath());
                    imagesData.add(imageData);
                }
            }
        } else {
            System.err.println("O diretório especificado não existe ou não é um diretório.");
        }

        return imagesData;
    }

    public static void main(String[] args)throws Exception {
        FilmeDAO filme = new FilmeDAO();
        int alienID = 10;
        int jawsId = 3;
        int screamID = 4;
        int sextaFeiraID = 1;
        int starwarsID = 2;
        int etID = 5;
        int marioID = 6;
        int indianaID = 8;
        int ghostbustersID = 7;
        int futuroID = 9;


        String alienPath = path + "/alien.jpg";
        String jawsPath = path + "/jaws.jpg";
        String screamPath = path + "/scream.jpg";
        String sextaFeiraPath = path + "/sexta13.jpg";
        String starwarsPath = path + "/starwars.jpg";
        String etPath = path + "/et.jpg";
        String marioPath = path + "/supermario.jpg";
        String indianaPath = path + "/indiana.jpg";
        String ghostbustersPath = path + "/ghostbusters.jpg";
        String futuroPath = path + "/futuro.jpg";








        try {
            byte[] alienData = imageToByteArray(alienPath);
            byte[] jawsData = imageToByteArray(jawsPath);
            byte[] screamData = imageToByteArray(screamPath);
            byte[] sextaFeiraData = imageToByteArray(sextaFeiraPath);
            byte[] starwarsData = imageToByteArray(starwarsPath);
            byte[] etData = imageToByteArray(etPath);
            byte[] marioData = imageToByteArray(marioPath);
            byte[] indianaData = imageToByteArray(indianaPath);
            byte[] ghostbustersData = imageToByteArray(ghostbustersPath);
            byte[] futuroData = imageToByteArray(futuroPath);





            filme.updatepost(alienID, alienData);
            filme.updatepost(jawsId, jawsData);
            filme.updatepost(screamID, screamData);
            filme.updatepost(sextaFeiraID, sextaFeiraData);
            filme.updatepost(starwarsID, starwarsData);
            filme.updatepost(etID, etData);
            filme.updatepost(marioID, marioData);
            filme.updatepost(indianaID, indianaData);
            filme.updatepost(ghostbustersID, ghostbustersData);
            filme.updatepost(futuroID, futuroData);





        } catch (IOException e) {
            e.printStackTrace();
        }
       
    }
}

*/