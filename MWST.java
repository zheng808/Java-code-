/* MWST.java
 CSC 226 - Spring 2015
 Assignment 1 - Minimum Weight Spanning Tree Template
 
 This template includes some testing code to help verify the implementation.
 To interactively provide test inputs, run the program with
	java MWST
	
 To conveniently test the algorithm with a large input, create a text file
 containing one or more test graphs (in the format described below) and run
 the program with
	java MWST file.txt
 where file.txt is replaced by the name of the text file.
 
 The input consists of a series of graphs in the following format:
 
 <number of vertices>
	<adjacency matrix row 1>
	...
	<adjacency matrix row n>
	
 Entry A[i][j] of the adjacency matrix gives the weight of the edge from
 vertex i to vertex j (if A[i][j] is 0, then the edge does not exist).
 Note that since the graph is undirected, it is assumed that A[i][j]
 is always equal to A[j][i].
	
 An input file can contain an unlimited number of graphs; each will be
 processed separately.
 */

import java.util.Arrays;
import java.util.Scanner;
import java.util.Vector;
import java.io.File;

//Do not change the name of the MWST class
public class MWST{
    
    /* mwst(G)
     Given an adjacency matrix for graph G, return the total weight
     of all edges in a minimum weight spanning tree.
     
     If G[i][j] == 0, there is no edge between vertex i and vertex j
     If G[i][j] > 0, there is an edge between vertices i and j, and the
     value of G[i][j] gives the weight of the edge.
     No entries of G will be negative.
     */
    static int MWST(int[][] G){
        int numVerts = G.length;
        int totalWeight = 0;
        int minedge=0;
        boolean[] arrayvertx=new boolean[numVerts];
        for(int i=0;i<numVerts;i++){
            arrayvertx[i]=false;
        }
        arrayvertx[0]=true;
        int vistednode=0;
        while(checkcycle(arrayvertx)==true)
        {
            int miniweight=10000;
            for(int i=0;i<numVerts;i++){
                for(int j=0;j<numVerts;j++){
                    if(arrayvertx[i]==true)
                    {
                        if(G[i][j]>0&&G[i][j]<miniweight){
                            if(arrayvertx[j]==false){
                                miniweight=G[i][j];
                                minedge=miniweight;
                                vistednode=j;   //update boolean array
                            }
                        }
                    }
                }
            }
            totalWeight=totalWeight+minedge;
            arrayvertx[vistednode]=true;
        }
        
        
        /* ... Your code here ... */
        
        return totalWeight;
        
    }
    public static boolean checkcycle(boolean[] array){
        int length=array.length;
        for(int i=0;i<length;i++){
            if(array[i]==false){
                return true;
            }
        }
        return false;
    }
    
    /* main()
     Contains code to test the MWST function. You may modify the
     testing code if needed, but nothing in this function will be considered
     during marking, and the testing process used for marking will not
     execute any of the code below.
     */
    public static void main(String[] args){
        Scanner s;
        if (args.length > 0){
            try{
                s = new Scanner(new File(args[0]));
            } catch(java.io.FileNotFoundException e){
                System.out.printf("Unable to open %s\n",args[0]);
                return;
            }
            System.out.printf("Reading input values from %s.\n",args[0]);
        }else{
            s = new Scanner(System.in);
            System.out.printf("Reading input values from stdin.\n");
        }
        
        int graphNum = 0;
        double totalTimeSeconds = 0;
        
        //Read graphs until EOF is encountered (or an error occurs)
        while(true){
            graphNum++;
            if(graphNum != 1 && !s.hasNextInt())
                break;
            System.out.printf("Reading graph %d\n",graphNum);
            int n = s.nextInt();
            int[][] G = new int[n][n];
            int valuesRead = 0;
            for (int i = 0; i < n && s.hasNextInt(); i++){
                for (int j = 0; j < n && s.hasNextInt(); j++){
                    G[i][j] = s.nextInt();
                    valuesRead++;
                }
            }
            if (valuesRead < n*n){
                System.out.printf("Adjacency matrix for graph %d contains too few values.\n",graphNum);
                break;
            }
            long startTime = System.currentTimeMillis();
            
            int totalWeight = MWST(G);
            long endTime = System.currentTimeMillis();
            totalTimeSeconds += (endTime-startTime)/1000.0;
            
            System.out.printf("Graph %d: Total weight is %d\n",graphNum,totalWeight);
        }
        graphNum--;
        System.out.printf("Processed %d graph%s.\nAverage Time (seconds): %.2f\n",graphNum,(graphNum != 1)?"s":"",(graphNum>0)?totalTimeSeconds/graphNum:0);
    }
}
