/**
 *
 * @author quimo
 */

import com.formdev.flatlaf.FlatDarculaLaf;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.UIManager;
import org.apache.commons.io.FileUtils;

public class Main {
    
    File themeFile = new File(System.getProperty("user.home") + "/Stream-Pi/ThemeEditor/newtheme/theme.xml");
    File styleSheet = new File(System.getProperty("user.home") + "/Stream-Pi/ThemeEditor/newtheme/res/style.css");
    
    public void createTheme() throws IOException
    {
        File theDir = new File(System.getProperty("user.home") + "/Stream-Pi/ThemeEditor/newtheme/res/");
        theDir.delete();
        
        InputStream is = getClass().getClassLoader().getResourceAsStream("theme.xml");
        FileUtils.copyInputStreamToFile(is, themeFile);
        
        InputStream is2 = getClass().getClassLoader().getResourceAsStream("style.css");
        FileUtils.copyInputStreamToFile(is2, styleSheet);
        
        modifyFile(themeFile.getAbsolutePath(), "githubuser", mainApp.user);
        modifyFile(themeFile.getAbsolutePath(), "newtheme", mainApp.tName);
        modifyFile(themeFile.getAbsolutePath(), "themeversion", mainApp.tVer);
        
        modifyFile(styleSheet.getAbsolutePath(), "insertbgcolorhere", mainApp.bgcolor.toLowerCase());
        modifyFile(styleSheet.getAbsolutePath(), "insertfontcolorhere", mainApp.fontcolor.toLowerCase());
        
            
        File picture = new File(mainApp.picturePath);
        picture.renameTo(new File(theDir.getAbsoluteFile() + "/bg.png"));
        
        if(null != mainApp.iconShape)
        switch (mainApp.iconShape) {
            case "Box":
                modifyFile(styleSheet.getAbsolutePath(), "	-fx-shape: iconshape;", "");
                break;
            case "Circle":
                modifyFile(styleSheet.getAbsolutePath(), "	-fx-shape: iconshape;", "	-fx-shape: \" M 100, 100 m -75, 0 a 75,75 0 1,0 150,0 a 75,75 0 1,0 -150,0\";");
                break;
            case "Human":
                modifyFile(styleSheet.getAbsolutePath(), "	-fx-shape: iconshape;", "	-fx-shape: \" M 100, 100 m -150, 100 a -100,50 0 1,0 50,0 a 50,50 0 1,0 -50,0\";");
                break;
            default:
                break;
            
        }
        throw new IOException("Theme compiled! Located at: " + System.getProperty("user.home") + "/Stream-Pi/ThemeEditor/newtheme/");
    }
    
    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        new mainApp().setVisible(true);
    }
    
    static void modifyFile(String filePath, String oldString, String newString)
    {
        File fileToBeModified = new File(filePath);
         
        String oldContent = "";
         
        BufferedReader reader = null;
         
        FileWriter writer = null;
         
        try
        {
            reader = new BufferedReader(new FileReader(fileToBeModified));
             
            //Reading all the lines of input text file into oldContent
             
            String line = reader.readLine();
             
            while (line != null) 
            {
                oldContent = oldContent + line + System.lineSeparator();
                 
                line = reader.readLine();
            }
             
            //Replacing oldString with newString in the oldContent
             
            String newContent = oldContent.replaceAll(oldString, newString);
             
            //Rewriting the input text file with newContent
             
            writer = new FileWriter(fileToBeModified);
             
            writer.write(newContent);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                //Closing the resources
                 
                reader.close();
                 
                writer.close();
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }
        }
    }
}
