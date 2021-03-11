import java.io.*;
import java.rmi.registry.LocateRegistry; 
import java.rmi.registry.Registry;  

public class Client {  
   private Client() {}  
   public static void main(String[] args) throws IOException {  
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));  
    try {  

        Registry registry = LocateRegistry.getRegistry(null); 
        Mst stub = (Mst) registry.lookup("RMIServer"); 
        int count = 0;
        while(true) {
            String userInput[] = br.readLine().split(" ");
            String requestType = userInput[0];
            String id = userInput[1];

            if(requestType.equals("add_graph"))
            {
                int vertices = Integer.parseInt(userInput[2]);
                stub.addGraph(id, vertices);
            }
            else if(requestType.equals("add_edge"))
            {
                int u = Integer.parseInt(userInput[2]);
                int v = Integer.parseInt(userInput[3]);
                int weight = Integer.parseInt(userInput[4]);
                stub.addEdge(id, u, v, weight);

            }
            else if(requestType.equals("get_mst"))
            {
                System.out.println(stub.getMst(id));
            }
        }
      } catch (NullPointerException e) {

      } 
      catch (Exception e) {
         System.err.println("Client exception: " + e.toString()); 
         e.printStackTrace(); 
      } 
   } 
}
