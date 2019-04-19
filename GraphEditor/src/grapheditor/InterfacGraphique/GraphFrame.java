/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grapheditor.InterfacGraphique;

import grapheditor.Graphe;
import grapheditor.Sommet;
import grapheditor.Arc;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import static java.awt.FlowLayout.LEADING;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import static javax.swing.SwingConstants.CENTER;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Mad Scientifique
 */
public class GraphFrame extends JFrame {
    
    JLabel currentSommet;
    JLabel infoDisp;
    JTextField arcValue;
    Point framePos;
    ImageIcon circle;
    Graphe currentGraph;
    int currentSommetNumber;
    ArrayList<JLabel> labelList;
    JPanel window;
    
    public GraphFrame(){
        super("éditeur de graphes");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        super.setMinimumSize(new Dimension(1520,750));
        
        
        labelList = new ArrayList<>();
        
        circle = new ImageIcon(this.getClass().getResource("/image/circle.png"));
        
        currentGraph = new Graphe(); 
        
        setup();
        
        pack();
        setVisible(true);
        
        //framePos = super.getLocationOnScreen();
        
        currentSommetNumber=0;
    }
    
    void setOtherButtons(JPanel jp){
        JLabel jl = new JLabel();
        jl.setText(" << Ajoute  un sommet modifiable");
        jp.add(jl);
        setVisible(true);
        JTextField destAjoutee = new JTextField();
        jp.add(destAjoutee);
        JLabel l1 = new JLabel(" << Sommet de destination 'd' ");
        jp.add(l1);
        JTextField valArc = new JTextField();
        jp.add(valArc);
        JLabel l2 = new JLabel(" << Valeur de la transition 'v' ");
        jp.add(l2);
        JButton ajoutArc = new JButton(" ajout d'arc vers 'd' de valeur 'v' ");
        ajoutArc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                int n = Integer.parseInt(destAjoutee.getText());
                double v = Double.parseDouble(valArc.getText());
                if(n<1000 && n>0){
                    String s = currentSommet.getText();
                    s = s.substring(1);
                    int sommet = Integer.parseInt(s);
                    Sommet origine = currentGraph.TrouveSommet(sommet);
                    Sommet destination = currentGraph.TrouveSommet(n);
                    if(destination!= null)
                    {
                        origine.AjouterArc((v<1000 && v>-1000) ? v : 0.0, destination);
                    }
                }
            }
        });
        jp.add(ajoutArc);
        
        JLabel desc = new JLabel(" << ajoute l'arc au sommet sélectionné");
        jp.add(desc);
        
        JButton reset = new JButton("Réinitialiser");
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae){
                currentGraph = new Graphe();
                for(int i=0; i<labelList.size(); i++){
                    JLabel l = labelList.get(i);
                    Container c = l.getParent();
                    c.remove(l);
                }
                labelList.clear();
                currentSommet=null;
                currentSommetNumber = 0;
                infoDisp.setText("Pour commencer, sélectionnez un sommet");
                repaint();
            }
        });
        jp.add(reset);
        jp.add(new JLabel(" << met à zéro le graphe "));
    }
    
    void setSaveButtons(JPanel jp){
        jp.add(new JSeparator());
        jp.add(new JSeparator());
        
        JButton save = new JButton(" Sauvegarder ");
        JButton load = new JButton(" Charger ");
        
        save.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae){
                    File file=new File("Graphe_Sauvegardé.txt");
                    try(FileWriter fw=new FileWriter(file)) {
                        
                        fw.write(String.valueOf(currentGraph.nombreSommet()));
                        fw.write(" ");
                        
                        Sommet sav = currentGraph.PremierSommet();
                        while(sav != null){
                            Arc a = sav.PremierArc();
                            while(a != null){
                                fw.write(String.valueOf(a.Destination().Numero()));
                                fw.write(" ");
                                fw.write(String.valueOf(a.Valeur()));
                                fw.write(" ");
                                a= a.Suivant();
                            }
                            fw.write("9999 ");
                            sav = sav.Suivant();
                        }
                        
                        fw.flush();
                        fw.close();
                    } catch(IOException ex) {
                    ex.printStackTrace();
                }
            }
        
        });
        
        load.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae){
                if(currentGraph.PremierSommet()==null){
                    Scanner scan;
                    
                    File file = new File("Graphe_Sauvegardé.txt");
                    try {
                        scan = new Scanner(file);
                        scan.useLocale(Locale.US);
                        
                        if(scan.hasNext()){
                            int nb = scan.nextInt();
                            for(int i=0; i<nb; i++){
                                currentGraph.AjouterSommet(i+1);
                                currentSommetNumber++;
                                
                                JLabel lab = new JLabel("S"+(i+1)){
                                    @Override
                                    public void paintComponent(Graphics g){
                                    g.drawImage(circle.getImage(), 0, 0, null);
                                    super.paintComponent(g);
                                    }
                                };
                                labelList.add(lab);
                                
                                lab.setOpaque(false);
                                lab.setPreferredSize(new Dimension(64, 64));
                                lab.setHorizontalTextPosition(CENTER);
                                lab.setHorizontalAlignment(CENTER);

                                lab.addMouseListener(new MouseListener() {

                                    @Override
                                    public void mouseClicked(MouseEvent me) {
                                    }

                                    @Override
                                    public void mousePressed(MouseEvent me) {

                                    }

                                    @Override
                                    public void mouseReleased(MouseEvent me) {
                                        if (!me.isPopupTrigger()) {
                                            lab.setLocation(me.getXOnScreen() - 30, me.getYOnScreen() - 60);
                                        } else {
                                            currentSommet = lab;

                                            String s = currentSommet.getText();
                                            s = s.substring(1);
                                            int n = Integer.parseInt(s);

                                            Sommet toFind = currentGraph.TrouveSommet(n);
                                            if (toFind != null) {
                                                String infoSommet = toFind.ImprimeDestination();
                                                infoDisp.setText(infoSommet);
                                            }
                                        }
                                    }

                                    @Override
                                    public void mouseEntered(MouseEvent me) {
                                    }

                                    @Override
                                    public void mouseExited(MouseEvent me) {
                                    }
                                });
                                
                                window.add(lab);
                                
                            }
                        }
                        setVisible(true);
                        
                        Sommet s = currentGraph.PremierSommet();
                        int n=0;
                                
                        while(scan.hasNext()){
                            
                            n=scan.nextInt();
                            
                            while(n!=9999 && scan.hasNext()){
                                Sommet ajout = currentGraph.TrouveSommet(n);
                                s.AjouterArc(scan.nextDouble(), ajout);
                                n=scan.nextInt();
                            }
                            s= s.Suivant();
                            
                        }

                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    
                    infoDisp.setText("Veuillez réinitialiser le graphe avant de charger un autre graphe.");
                    
                }

            }
        
        });
        
        jp.add(save);
        jp.add(load);
        
    }
    
    void setup(){
        getContentPane().setLayout(new FlowLayout(LEADING));
        
        JPanel wind = new JPanel() {
            @Override
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                
                Sommet s = currentGraph.PremierSommet();
                
                for(int i = 0; i<labelList.size(); i++){
                    Point p = labelList.get(i).getLocationOnScreen();
                    p.y-=5;
                    p.x-=2;
                    int n = Integer.parseInt(labelList.get(i).getText().substring(1));
                    n=((n%2)*2-1)*10;
                    if(s!=null){
                        Arc a = s.PremierArc();
                        while(a!=null){
                            Point p2 = labelList.get(a.Destination().Numero()-1).getLocationOnScreen();
                            
                            Line2D lin = new Line2D.Float(p.x+10, p.y+(n*3), p.x+20, p.y+(n*4));
                            Line2D lin2 = new Line2D.Float(p.x+30, p.y+(n*3), p.x+20, p.y+(n*4));
                            Line2D lin3 = new Line2D.Float(p.x+20, p.y+(n*4), p.x+20, p.y+(n*8));
                            Line2D lin4 = new Line2D.Float(p.x+20, p.y+(n*8), p2.x, p2.y);
                            
                            g2.draw(lin);
                            g2.draw(lin2);
                            g2.draw(lin3);
                            g2.draw(lin4);
                            g2.drawString(String.valueOf(a.Valeur()),((p.x+20)+(p2.x+20))/2 +(20*((p.x-p2.x)/Math.abs(p.x-p2.x))) ,((p.y+(n*8))+(p2.y))/2);
                            
                            a=a.Suivant();
                        }
                    }
                    
                    s=s.Suivant();
                }
            }
        };
        
        window = wind;
        wind.setLayout(new GridBagLayout());
        wind.setPreferredSize(new Dimension(1000, 700));
        wind.setBackground(Color.white);
        
        JLabel gomme = new JLabel("Gomme"){
            @Override
                public void paintComponent(Graphics g){
                g.drawImage(circle.getImage(), 0, 0, null);
                super.paintComponent(g);
            }
        };    
        
        gomme.setOpaque(false);
                gomme.setPreferredSize(new Dimension(64,64));
                gomme.setHorizontalTextPosition(CENTER);
                gomme.setHorizontalAlignment(CENTER);
                
                gomme.addMouseListener(new MouseListener(){
                    @Override
                    public void mouseReleased(MouseEvent me) {
                        if(!me.isPopupTrigger()){
                            gomme.setLocation(me.getXOnScreen()-30, me.getYOnScreen()-60);
                        }
                    }

                    @Override
                    public void mouseClicked(MouseEvent me) {
                    }

                    @Override
                    public void mousePressed(MouseEvent me) {
                    }

                    @Override
                    public void mouseEntered(MouseEvent me) {
                    }

                    @Override
                    public void mouseExited(MouseEvent me) {
                    }
                });
        
        wind.add(gomme);
        
        getContentPane().add(wind);
        
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(2,1));
        rightPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JPanel jp = new JPanel();
        jp.setLayout(new GridLayout(7,2));
        
        JButton ajoutSommetBut = new JButton();
        ajoutSommetBut.setText("ajouter un sommet");
        
        ajoutSommetBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                currentSommetNumber++;
                
                JLabel lab = new JLabel("S"+(currentSommetNumber)){
                    @Override
                    public void paintComponent(Graphics g){
                        g.drawImage(circle.getImage(), 0, 0, null);
                        super.paintComponent(g);
                    }
                };      
                
                currentGraph.AjouterSommet(currentSommetNumber);
                labelList.add(lab);
                
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
                            
                            String s = currentSommet.getText();
                            s = s.substring(1);
                            int n = Integer.parseInt(s);
                            
                            Sommet toFind = currentGraph.TrouveSommet(n);
                            if(toFind != null){
                                String infoSommet = toFind.ImprimeDestination();
                                infoDisp.setText(infoSommet);
                            }
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
        jp.setBorder(new EmptyBorder(20, 0, 20, 0));
        
        setOtherButtons(jp);
        
        setSaveButtons(jp);
        
        rightPanel.add(jp);
        
        JPanel info = new JPanel();
        info.setBorder(new EmptyBorder(20, 0, 20, 0));
        infoDisp = new JLabel("Pour commencer, sélectionnez un sommet");
        infoDisp.setOpaque(true);
        infoDisp.setBackground(Color.cyan);
        info.add(infoDisp);
        
        rightPanel.add(info);
        
        getContentPane().add(rightPanel);
        
    }
    
}
