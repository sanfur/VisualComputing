/*
 * NTB - Interstaatliche Hochschule für Technik Buchs
 * Schoenauweg 4, 9000 St. Gallen
 * All rights reserved
 */
package ch.ntb.cv.background;

import java.awt.Color;
import java.awt.image.BufferedImage;


public class BackgroundRemoval {
    
    public static enum Channel{
        HUE,
        SATURATION,
        VALUE,
        ALPHA;
    }
    
    public class HSV{
        float[][] hue;
        char[][] bhue;
        char[][] value;
        char[][] saturation;
        
        public HSV(int w, int h){
            hue = new float[w][h];
            bhue = new char[w][h];
            value = new char[w][h];
            saturation = new char[w][h];
        }
    }

    
    private BufferedImage biForeground = null;
    private BufferedImage biBackground = null;
    public BufferedImage biComposite = null;
    
    public HSV hsvForeground = null;
    private boolean[][] mask;
    
    private final int width;
    private final int height;
    
    private final float minRange = 0.27f;
    private final float maxRange = 0.37f;
    
    
    
    /*
    Der K-tor uebernimmt die gelesenen Bilden, fuehrt eine tiefe Kopie des Hintergrundes durch und ruft alle noetigen Funktionen in korrekter
    Reihenfolge auf. Im Normalfall ist eine Anpassung des K-Tors nicht noetig. Gesetzte Werte sind nicht verpflichtend.
    */
    public BackgroundRemoval(BufferedImage fg, BufferedImage bg){

        if(fg == null || bg == null) System.err.println("Image(s) NULL!");
        
        biForeground = fg;
        
        width = biForeground.getWidth();
        height = biForeground.getHeight();
        
        if(bg.getHeight() != height || bg.getWidth() != width) System.err.println("Image sizes not equal!");
        
        biBackground  = new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
        
        // Tiefe Kopie (Deep copy) des Hintergrundbuffers, um das Original beim Einfuegen des Schattens nicht zu veraendern
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                biBackground.setRGB(x, y, bg.getRGB(x, y));
            }
        }
        
        mask = new boolean[width][height];
        hsvForeground = new HSV(width, height);
        
        // vorbereiten der Matrize fuer das Endergebnis
        biComposite = new BufferedImage(biForeground.getWidth(),biForeground.getHeight(), BufferedImage.TYPE_INT_RGB);
        
       
        
        
   
    }
    
    public void execute(){
         // Konvertieren des Vordergrundes in HSV
        createHSV();
        
        // Erstellen der Alpha-Maske basierend auf einem Grünwertebereich
        createAlphaMask();
                
        // Fakultativ: Anpassen des Value-Kanals im HSV
        histogramStretchValueChannel(0, 255, 30, 160);

        // MorphologischeOperationen fuer sauberere Kante
        // TODO: Waehlen Sie geeignete Operationen und Parameter um die Kanten zu verbessern.
        System.err.println("Morphological operations have not been used");
         
        // Einkopieren des Schattens, dazu multiplizieren mit Value Kanal
        mergeValueChannel();

        // Zusammenfuehren von fg + bg basierend auf Maske. Fakultativ: Randpixel semi-transparant machen.
        merge();
    }
    
    
    private void createHSV(){
        // TODO: Konvertieren Sie das Vordergrundbild in den HSV Farbraum
        
        System.err.println("createHSV is not yet implemented");

    }
    
    private void createAlphaMask(){
        // TODO: Erstellen Sie eine 1-bit Maske basierend auf den Farbwerten des Vordergrundbildes.
        
        System.err.println("createAlphaMask is not yet implemented");
        
    }
    
    private void merge(){
        
        // Dies ist ein Beispiel, wie auf die Bildmatrize lesend und schreibend zugegriffen wird. In diesem Fall wird jede zweite Zeile zwischen
        // Vordergrund und Hintergrund gewechselt.  Ersetzen Sie die Funktionalitaet mit einer geeigneten, maskenbasierten Logik
        
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                if(x % 2 == 0){
                    biComposite.setRGB(x, y, biForeground.getRGB(x, y));
                }else{
                    biComposite.setRGB(x, y, biBackground.getRGB(x, y)); 
                }
            }
        }
        System.err.println("Merge is not yet implemented");
    }
    
    private void mergeValueChannel(){
        
        // TODO: Rechnen Sie den V-Kanal des HSV-Vordergrundes in das Hintergrundbild indem Sie pixelweise die RGB-Kanaele mit dem V-Kanal multiplizieren
        // FRAGE: Kann es hierbei zu Farbueberlaeufen (Farbwerte ausserhalb von 0-255) kommen? Begruenden Sie.
        
        System.err.println("mergeValueChannel is not yet implemented");
    }
    
    
    private void histogramStretchValueChannel(int targetMin, int targetMax, int lowest, int highest){
        // TODO: Strecken/Ueberstrecken Sie das Histogram des V-Kanals. Ziel ist, soviele Grauwerte, die nicht zum Schatten gehoeren 
        // auszuloeschen ( Weiss zu setzen ) und den Schtten ggf. etwas zu verstaerken
        System.err.println("HistogrammStretchValueChannel is not yet implemented");
        
        // TODO: Finden Sie die lokalen min/max Werte

        
        
        // TODO: Setzen Sie max auf den kleinensten Maximalwert und min auf den groessten Minimalwert

        
        
        // TODO: Weisen Sie dem V-Kanal den neu berechneten Grauwert zu.
        // FRAGE: Kann es zu einem Farbueberlauf  (Grauwerte ausserhalb von 0-255) kommen? Fuehren Sie ggf. noetige Schritte durch.
        
    }
    

    private void closing(int len, boolean diagonal){
        System.err.println("Closing is not yet implemented");
    }
    private void opening(int len, boolean diagonal){
        System.err.println("Opening is not yet implemented");
    }
    
    private void dilatation(int len, boolean diagonal){
        // TODO: erstellen Sie eine Arbeitskopie der Maske
        
        
        // TODO: Implementieren Sie eine Dilatation mit rechteckigem (Seitenlaenge 2*len+1) Strukturelement. Nutzen Sie Symmetrie.
        
        System.err.println("Dilatation is not yet implemented");
    }
    
    private void erosion(int len, boolean diagonal){
        // TODO: erstellen Sie eine Arbeitskopie der Maske
        
        
        // TODO: Implementieren Sie eine Erosion mit rechteckigem (Seitenlaenge 2*len+1) Strukturelement. Nutzen Sie Symmetrie.
        
        System.err.println("Erosion is not yet implemented");
    }
    
    public BufferedImage getHsvChannel(Channel c){
        BufferedImage img = new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
        int pxc = 0;
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                switch(c){
                    case HUE: pxc = hsvForeground.bhue[x][y]; break;
                    case SATURATION:pxc = hsvForeground.saturation[x][y]; break;
                    case VALUE: pxc = hsvForeground.value[x][y]; break;
                    case ALPHA: if(mask[x][y]){ pxc = 0; }else{ pxc = 255; }
                }
                img.setRGB(x, y, (pxc << 16) + (pxc << 8) + pxc  );
            }
        }
        
        return img;
    }
}
