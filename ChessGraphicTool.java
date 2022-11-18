package chess;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ChessGraphicTool {

    /**
     * load an image from disk
     *
     * @param imagePath is the path+name of the file to load
     * @return a BufferedImage or null on error
     */
    static public BufferedImage load(final String imagePath) {
        File file;
        try {
            file = new File(imagePath);
            if (!file.exists())
                return null; // image not found
            return ImageIO.read(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; // error
    }

    // --

    /**
     * default constructor
     */
    public ChessGraphicTool() {

    }
}