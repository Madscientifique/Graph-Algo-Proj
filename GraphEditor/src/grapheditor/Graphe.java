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
    int nbSommet;
    
    
    // ctors
    public Graphe(Sommet p){
        prem = p;
        if(p!=null){
            nbSommet=1;
        } else {
            nbSommet=0;
        }
    }
    
    public Graphe(){
        this(null);
    }
    // ----
    
    void Graphe2Fs(){
        fs= new int[(nbSommet*nbSommet)];
        fs[0]=nbSommet;
        int iter=1;
        Sommet s = prem;
        while(s!=null){
            Arc a = s.PremierArc();
            while(a!=null){
                fs[iter]=a.Destination().Numero();
                iter++;
                a=a.Suivant();
            }
            fs[iter]=0;
            iter++;
            s=s.Suivant();
        }
    }
    
    public int nombreSommet(){
        return nbSommet;
    }
    
    public Sommet AjouterSommet(int n){
        if(prem != null){
            nbSommet++;
            return prem.Ajouter(n);
        } else {
            prem = new Sommet(n);
            nbSommet++;
            return prem;
        }
    }
    
    public Sommet PremierSommet(){
        return prem;        
    }
    
    public Sommet TrouveSommet(int n){
        if(prem!= null){
            return prem.Trouve(n);
        } else {
            return prem;
        }
    }
}
