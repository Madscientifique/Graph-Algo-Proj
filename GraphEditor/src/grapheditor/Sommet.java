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
public class Sommet {
    private int num;
    private Sommet suiv;
    private Arc prem; 

    // ctors ----
    public Sommet(int n, Sommet s, Arc p){
        num = n;
        suiv = s;
        prem = p;
    }
    
    public Sommet(int n){
        this(n, null, null);
    }
    // ----
    
    
    public int Numero() {
        return num;
    }

    public Arc PremierArc() {
        return prem;
    }
    
    public Arc AjouterArc(int v, Sommet d){
        if(prem != null) {
            return prem.Ajouter(v, d);
        } else {
            prem = new Arc(v,d);
            return prem;
        }
    }
    
    public Sommet Suivant() {
        return suiv;
    }
    
    public Sommet Ajouter(int n){
        if(suiv!= null){
             return suiv.Ajouter(n);
        } else {
            suiv = new Sommet(n);
            return suiv;
        }
    }
    
}
