// Image Compression, Java version by Andy Harrington 
import java.util.*;
import java.io.*;

public class compress
{
    static int[][] image;
    static int T;

    static void smooth(int x,int y, int w) {
        int n = 0;
        for (int i = 0; i < w; i++)
            for (int j = 0; j < w; j++)
                if (image[x+i][y+j] == 1)
                    n++;
        int dominant = 2;  // mark non-terminal 
        if (n*100 >= T*w*w)
            dominant = 1;
        else if ((w*w - n)*100 >= T*w*w)
            dominant = 0;
        if (dominant == 2) {
            smooth(x, y, w/2);
            smooth(x+w/2, y, w/2);
            smooth(x, y+w/2, w/2);
            smooth(x+w/2, y+w/2, w/2);
        }
        else
            for (int i = 0; i < w; i++)
                for (int j = 0; j < w; j++)
                    image[x+i][y+j] = dominant;
    }
    
    public static void main(String[] args) throws Exception {
        String file = (args.length > 0) ? args[0] : "compress.in";
        Scanner in = new Scanner(new File(file));
        int set = 1;
        while(true) {
            int w  = in.nextInt();
            if (w == 0) break;
            T = in.nextInt();
            System.out.println("Image " + set + ":");
            set++;
            image = new int[w][w];
            for (int i = 0; i < w; i++) {
                String s = in.next();
                for (int j = 0; j < w; j++)
                    image[i][j] = s.charAt(j)-'0';
            }
            smooth(0, 0, w);
            for (int i = 0; i < w; i++) {
                for (int j = 0; j < w; j++)
                    System.out.print(image[i][j]);
                System.out.println();
            }
        }
    }
}

