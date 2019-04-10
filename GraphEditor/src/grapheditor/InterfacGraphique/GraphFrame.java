/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grapheditor.InterfacGraphique;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import static java.awt.FlowLayout.LEADING;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import static javax.swing.SwingConstants.CENTER;

/**
 *
 * @author Mad Scientifique
 */
public class GraphFrame extends JFrame {
    
    JLabel currentSommet;
    JTextField arcValue;
    Point framePos;
    ImageIcon circle;
    int currentSommetNumber;
    
    public GraphFrame(){
        super("éditeur de graphes");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        super.setMinimumSize(new Dimension(1300,700));
        
        circle = new ImageIcon(this.getClass().getResource("/image/circle.png"));
        
        setup();
        
        pack();
        setVisible(true);
        
        //framePos = super.getLocationOnScreen();
        
        currentSommetNumber=0;
    }
    
    void setOtherButtons(JPanel jp){
        JLabel jl = new JLabel();
        jl.setText("<< Ajoute  un sommet modifiable");
        jp.add(jl);
        setVisible(true);
    }
    
    void setup(){
        getContentPane().setLayout(new FlowLayout(LEADING));
        
        JPanel wind = new JPanel();
        wind.setBackground(Color.white);
        wind.setPreferredSize(new Dimension(1300, 1000));
        getContentPane().add(wind);
        
        JPanel jp = new JPanel();
        jp.setLayout(new GridLayout(1,2));
        
        JButton ajoutSommetBut = new JButton();
        ajoutSommetBut.setText("ajouter un sommet");
        
        ajoutSommetBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                JLabel lab = new JLabel("S"+(currentSommetNumber+1)){
                    @Override
                    public void paintComponent(Graphics g){
                        g.drawImage(circle.getImage(), 0, 0, null);
                        super.paintComponent(g);
                    }
                };      
                currentSommetNumber++;
                
                lab.setOpaque(false);
                lab.setPreferredSize(new Dimension(64,64));
                lab.setHorizontalTextPosition(CENTER);
                lab.setHorizontalAlignment(CENTER);
                
                lab.addMouseListener(new MouseListener(){

                    @Override
                    public void mouseClicked(MouseEvent me) {
                    }

                    @Override
                    public void mousePressed(MouseEvent me) {
                        
                    }

                    @Override
                    public void mouseReleased(MouseEvent me) {
                        if(!me.isPopupTrigger()){
                            lab.setLocation(me.getXOnScreen()-30, me.getYOnScreen()-60);
                        } else {
                            currentSommet = lab;
                        }
                    }

                    @Override
                    public void mouseEntered(MouseEvent me) {
                    }

                    @Override
                    public void mouseExited(MouseEvent me) {
                    }
                });
                wind.add(lab);
                setVisible(true);
            }
        });
        jp.add(ajoutSommetBut);
        
        setOtherButtons(jp);
        
        getContentPane().add(jp);
        
    }
    
}
