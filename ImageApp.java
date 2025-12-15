/*
 * ImageApp
 * ----------
 * This class contains all image-processing logic for the PLTW Image App.
 * It supports:
 *  - Color manipulation (recolor, negative, grayscale)
 *  - Image rotation using matrix multiplication
 *  - Inserting a smaller image into a larger image with background removal
 *  - Runtime updates via UI buttons in PictureExplorer
 *
 * All methods are static and operate on Picture and Pixel[][] objects.
 */

import java.awt.Color;

public class ImageApp
{

  /**
   * Stores the currently displayed image so the UI can update it at runtime.
   */
  private static Picture singlePicture;

  /**
   * Returns the currently active Picture.
   * @return the current Picture object
   */
  public static Picture getPicture(){
    return singlePicture;
  }

  /**
   * Rotates an image by a given number of degrees using matrix multiplication.
   * Rotation is performed in 90-degree increments.
   *
   * @param degree number of degrees to rotate (must be multiple of 90)
   * @param pixels original pixel array
   * @param origImage original Picture object
   * @return a new Picture rotated by the specified amount
   */
  public static Picture rotate(int degree, Pixel[][] pixels, Picture origImage){

    /*
     * Create a reference copy of the image so rotations do not
     * accidentally modify the original pixel array.
     */
    Picture refPicture = new Picture(pixels.length, pixels[0].length);
    Pixel[][] refPixels = refPicture.getPixels2D();
    for (int i = 0; i< pixels.length; i++){
      for (int j = 0; j< pixels[i].length; j++){
        refPixels[i][j].setColor(pixels[i][j].getColor());
      }
    }

    /*
     * 90-degree rotation matrix:
     * [ 0  -1 ]
     * [ 1   0 ]
     */
    int[][] rotationalArr = {{0, -1}, {1, 0}};
    Matrix rotationalMatrix = new Matrix(rotationalArr);

    int degreesremaining = degree;

    // Rotate repeatedly in 90-degree steps
    while (degreesremaining >= 90){

      // Source image dimensions
      int srcH = refPixels.length;
      int srcW = refPixels[0].length;

      // Destination dimensions swap for 90-degree rotation
      int destW = srcH;
      int destH = srcW;

      origImage = new Picture(destH, destW);
      pixels = origImage.getPixels2D();

      /*
       * Apply matrix multiplication to each pixel coordinate
       * to compute its new rotated position.
       */
      for (int i = 0; i< srcH; i++){
        for (int j = 0; j< srcW; j++){
          int[] tempVector = {j, i};
          Vector vect1 = new Vector(tempVector);
          Vector rotated = Matrix.matrixMultiply(vect1, rotationalMatrix);

          int newX = rotated.get(0);
          int newY = rotated.get(1);

          // Translate negative coordinates into valid image space
          int newYTranslated = newY + (destH - 1);
          int newXTranslated = newX - 1;

          // Bounds check before setting pixel color
          if (newXTranslated >= 0 && newXTranslated < destW &&
              newYTranslated >= 0 && newYTranslated < destH){
            pixels[newYTranslated][newXTranslated]
                .setColor(refPixels[i][j].getColor());
          }
        }
      }

      /*
       * Reset reference image so additional rotations
       * build on the last result.
       */
      refPicture = new Picture(pixels.length, pixels[0].length);
      refPixels = refPicture.getPixels2D();
      for (int i = 0; i< pixels.length; i++){
        for (int j = 0; j< pixels[i].length; j++){
          refPixels[i][j].setColor(pixels[i][j].getColor());
        }
      }

      degreesremaining -= 90;
    }
    return origImage;
  }

  /**
   * Inserts a smaller image onto a larger image at (x, y).
   * White pixels in the smaller image are ignored (background removal).
   *
   * @param x x-position in the main image
   * @param y y-position in the main image
   * @param pixels pixel array of the main image
   */
  public static void insert(int x, int y, Pixel[][] pixels){
    Picture smallPic = new Picture("lib2/balloon.png");
    Pixel[][] smallPixels = smallPic.getPixels2D();
    Pixel[][] mainPixels = pixels;

    for (int i = 0; i < smallPixels.length; i++){
      for (int j = 0; j < smallPixels[i].length; j++){
        if (y+i < mainPixels.length &&
            x+j < mainPixels[0].length &&
            !smallPixels[i][j].getColor().equals(Color.WHITE)){
          mainPixels[y+i][x+j]
              .setColor(smallPixels[i][j].getColor());
        }
      }
    }
  }

  /**
   * Recolors an image by rearranging RGB values.
   * (Green → Red, Blue → Green, Red → Blue)
   *
   * @param recoloredPixels pixel array to modify
   */
  public static void recolor(Pixel[][] recoloredPixels){
    for (Pixel[] row : recoloredPixels) {
      for (Pixel p : row) {
        int red = p.getRed();
        int green = p.getGreen();
        int blue = p.getBlue();
        p.setColor(new Color(green, blue, red));
      }
    }
  }

  /**
   * Creates a photographic negative of an image.
   *
   * @param negPixels pixel array to modify
   */
  public static void negative(Pixel[][] negPixels){
    for (Pixel[] row : negPixels) {
      for (Pixel p : row) {
        p.setColor(new Color(
            255 - p.getRed(),
            255 - p.getGreen(),
            255 - p.getBlue()
        ));
      }
    }
  }

  /**
   * Converts an image to grayscale using average RGB intensity.
   *
   * @param grayscalePixels pixel array to modify
   */
  public static void grayscale(Pixel[][] grayscalePixels){
    for (Pixel[] row : grayscalePixels) {
      for (Pixel p : row) {
        int avg = (p.getRed() + p.getGreen() + p.getBlue()) / 3;
        p.setColor(new Color(avg, avg, avg));
      }
    }
  }

  /**
   * Updates the displayed image when a UI button is pressed.
   *
   * @param newPic the updated Picture to display
   */
  public static void updateImageAtRuntime(Picture newPic){
    singlePicture = newPic;
    singlePicture.explore();
  }

  /**
   * Program entry point.
   * Initializes the image and launches the PictureExplorer UI.
   */
  public static void main(String[] args)
  {
    String pictureFile = "lib/beach.jpg";

    ImageApp.singlePicture = new Picture(pictureFile);
    Pixel[][] origPixels = ImageApp.singlePicture.getPixels2D();

    System.out.println(origPixels[0][0].getColor());
    ImageApp.singlePicture.explore();

    /*
     * All image manipulation calls are triggered by UI buttons
     * located in PictureExplorer.java (createControlPanel method).
     */
  }
}
