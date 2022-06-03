package start;

import javax.swing.*;

class JavaxSwing {
    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
              public void run() {
                  System.out.println("Hello World!");
              }
        });
       
    }
}