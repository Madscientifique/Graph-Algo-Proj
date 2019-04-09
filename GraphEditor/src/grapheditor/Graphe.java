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
public class Graphe {
    Sommet prem;
    int[] fs;
    int[] aps;
    int[][] adj;
    
    
    // ctors
    public Graphe(Sommet p){
        prem = p;
    }
    
    public Graphe(){
        this(null);
    }
    // ----
    
    
    public Sommet AjouterSommet(int n){
        if(prem != null){
            return prem.Ajouter(n);
        } else {
            prem = new Sommet(n);
            return prem;
        }
    }
}
