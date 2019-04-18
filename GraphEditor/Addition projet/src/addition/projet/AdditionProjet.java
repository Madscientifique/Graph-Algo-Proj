/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package addition.projet;

import static java.lang.Integer.MAX_VALUE;

/**
 *
 * @author Laurine
 */
public class AdditionProjet {

    public void fs2aps (int[] fs, int n, int[] aps) {
        int j = 2;
        aps = new int[n+1];
        aps[0] = n;
        aps[1] = 1;
        for(int i = 1; i<fs[0]; i++)
        {
            if( fs[i] == 0)
            {
                aps[j] = i+1;
                j++;
            }
        }
    }
    
    public void det_ddi(int[] fs, int[] aps, int[] ddi) {
        ddi = new int[aps[0]+1];
        for(int i = 1; i < aps[0]; i++)
            ddi[i] = 0;
        for(int i = 1; i < fs[0]; i++)
            ddi[fs[i]]++;
    }
    
    public void empiler(int i, int[] pilch){
        pilch[i]=pilch[0];
        pilch[0]=i;
    }
    
    public int depiler(int[] pilch) {
        int x=pilch[0];
        pilch[0]=pilch[x];
        return x;
    }
    
    public boolean rang(int[] fs, int[] aps, int r, int[] rg){
        int[] ddi;
        det_ddi(fs, aps, ddi);
        r = 0;
        int k = 0;
        int s, t;
        int n = aps[0];
        rg = new int[n+1];
        int[] pilch = new int[n+1];
        pilch[0] = 0;
        for(int i = 1; i <=n; i++)
        {
            rg[i] = -1;
            if(ddi[i] == 0)
                empiler(i, pilch);
        }
        while (pilch[0] > 0)
        {
            s = pilch[0];
            pilch[0] = 0;
            while (s > 0)
            {
                rg[s] = r;
                k++;
                for(int h = aps[s]; (t=fs[h])!=0; h++)
                {
                    ddi[t]--;
                    if(ddi[t] == 0)
                        empiler(t,pilch);
                }
                s = pilch[s];
            }
            r++;
        }
        r--;
        return (k == n);
    }

    public void calcul_cfc(int[][] dist, int[] prem, int[] pilch, int[] cfc) {
        int n = dist[0][0];
        int nb = 0;
        int s = 0;
        cfc = new int[n+1];
        prem = new int[n+1];
        pilch = new int[n+1];
        cfc[0] = n;
        for(int i = 1; i<=n; i++)
        {
            if(cfc[i] == 0)
            {
                nb++;
                cfc[i] = nb;
                prem[nb] = i;
                s = i;
                for(int j = i+1; j<=n; j++)
                {
                    if(cfc[j] == 0)
                    {
                        if(dist[i][j] != -1)
                        {
                            pilch[s] = j;
                            s = j;
                            cfc[j] = nb;
                        }
                    }
                }
            }
            pilch[s] = 0;
        }
        prem[0] = nb;
    }
    
    public void dijkstra(int[] fs, int[] aps, int[][] c, int s, int[] d, int[] pred){
        int v, j, max;
        int n = aps[0];
        d = new int[n+1];
        d[0] = n;
        pred = new int[n+1];
        pred[0] = n;
        boolean[] x = new boolean[n+1];
        for(int i = 1; i <= n; i++)
        {
            d[i] = c[s][i];
            pred[i] = 0;
            x[i] = true;
        }
        x[s] = false;
        d[s]=0;
        for(int cpt  =0; cpt  <n; cpt++)
        {
            max=MAX_VALUE;
            for(int i=1; i<=n  ; i++)
                if(  x[i] && d[i]<max)
                {
                    max=d[i];
                    j=i;
                }
            if( d[j]!= max)
            { 
                x[j]=false;
                for(int l=aps[j], k=fs[l]; k != 0; l++)
                {
                    if( x[k])
                    {
                        v=d[j]+c[j][k];
                        if( v<d[k])
                        {
                            d[k]=v;
                            pred[k]= j;
                        }
                    }
                }
            }
            else
                return;
        }
    }
    
}

