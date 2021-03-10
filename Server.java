import java.util.*;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.Remote;


class Pair{
        private int vertex, weight;

        public Pair(int weight, int vertex){
            this.weight = weight;
            this.vertex = vertex;
        }

        public int getVertex(){
            return vertex;
        }

        public int getWeight(){
            return weight;
        }
    
        // @Override
        // public String toString(){
        //     return "Vertex : " + vertex + " Weight : " + weight;
        }
}


interface Mst extends Remote {
    public void addGraph(String id, int vertices)throws RemoteException;
    public void addEdge(String id, int u, int v, int weight)throws RemoteException;
    public int getMst(String id)throws RemoteException;
}


class Graph{
    List<List<Pair>> adjList;
    
    Graph(int vertices) {
        adjList = new ArrayList<>();
        for(int i = 0; i < vertices; i++) {
            adjList.add(new ArrayList<>());
        }
    }
    List<List<Pair>> getAdjList(){
        return adjList;
    }
    int getAdjListSize(){
        return adjList.size();
    }
    void addEdge(int u, int v, int weight) {
        Pair pair1 = new Pair(weight, v);
        adjList.get(u - 1).add(pair1);
        Pair pair2 = new Pair(weight, u);
        adjList.get(v - 1).add(pair2);
    }
}


class MstImpl implements Mst {  
   
    Map<String, Graph> map = new HashMap<>();

    // Graph graph;
    public void addGraph(String id, int vertices)throws RemoteException{
        Graph graph = new Graph(vertices);
        if(!map.containsKey(id))
            map.put(id, graph);
    }

    public void addEdge(String id, int u, int v, int weight)throws RemoteException {
        Graph graph = map.get(id);
        graph.addEdge(u, v, weight);
        map.put(id, graph);
    }


    public int getMst(String id)throws RemoteException {
        Graph graph = map.get(id);
        int vertices = graph.getAdjListSize();
        int visited[] = new int[vertices];
        int beginnerNode = 1;
        int mstCost = primsAlgorithm(beginnerNode, visited, graph.getAdjList());
        return mstCost;
    }


    public int primsAlgorithm(int node, int [] visited, List<List<Pair>> adjList)
    {
        int minimumCost = 0;
        PriorityQueue<Pair> minheap = new PriorityQueue<>((pair1, pair2) -> pair1.getWeight() - pair2.getWeight());

        minheap.add(new Pair(0,node));
        while(!minheap.isEmpty())
        {
            Pair pair = minheap.poll();
            
            int u = pair.getVertex();
            int w = pair.getWeight();
            System.out.println(pair);
            if(visited[u-1] == 1)
                continue;
            
            visited[u-1] = 1;
            minimumCost += w;

            for(int i = 0; i < adjList.get(u-1).size(); i++)
            {
                Pair p = adjList.get(u-1).get(i);
                
                int v = p.getVertex();
                int weight = p.getWeight();
                if(visited[v-1] == 0)
                    minheap.add(new Pair(weight,v));
            }
        }
        for (int i = 0; i < visited.length; i++)
        {
            if(visited[i] == 0)
            {
                minimumCost = -1;
                break;
            }
        }
        return minimumCost;
    }
}


public class Server extends MstImpl { 
    
    public Server() {} 
    public static void main(String args[]) { 
      try { 

        Mst mst = new MstImpl();
        Mst skeleton = (Mst) UnicastRemoteObject.exportObject(mst, 0);

        Registry registry = LocateRegistry.getRegistry(); 
        registry.bind("RMIServer", skeleton);  

        System.err.println("Server ready"); 
      } catch (Exception e) { 
        System.err.println("Server exception: " + e.toString()); 
        e.printStackTrace(); 
      } 
   } 
}