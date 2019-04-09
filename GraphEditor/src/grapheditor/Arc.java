/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grapheditor;

/**
 *
 * @author Mad Scientifique
 */
class Arc {
    private double val;
    private Sommet dest;
    private Arc suiv;
    
    // ctors ----
    public Arc(double v, Sommet d, Arc s){
        val = v;
        dest = d;
        suiv = s;
    }
    
    public Arc(int v, Sommet d){
        this (v,d,null);
    }
    // ----
    
    public double Valeur() {
        return val;
    }
    
    public Sommet Destination() {
        return dest;
    }
    
    public Arc Suivant() {
        return suiv;
    }
    
    public Arc Ajouter(int v, Sommet d){
        if(suiv != null) {
            return suiv.Ajouter(v, d);
        } else {
            suiv= new Arc(v,d);
            return suiv;
        }
    }
}
