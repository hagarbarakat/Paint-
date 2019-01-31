/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint.view;



import javax.swing.*;
import java.applet.*;
import java.awt.*;
import java.awt.event.*;
/**
 *
 * @author Alex Laptop
 */
public class ColorPalatte extends JFrame{
    

final JColorChooser colorChooser = new JColorChooser();

 ActionListener okActionListener = new ActionListener() {
      public void actionPerformed(ActionEvent actionEvent) {
        System.out.println("OK Button");
        System.out.println(colorChooser.getColor());
      }
    };

    ActionListener cancelActionListener = new ActionListener() {
      public void actionPerformed(ActionEvent actionEvent) {
        System.out.println("Cancel Button");
      }
    };

    final JDialog dialog = JColorChooser.createDialog(null, "Change Button Background", true,
        colorChooser, okActionListener, cancelActionListener);
         
  }




        
    

