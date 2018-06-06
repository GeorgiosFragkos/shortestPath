package shortestPath;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;


public class App {

    private List<Vertex> nodes = new ArrayList<Vertex>();
    private List<Edge> edges = new ArrayList<Edge>();
    private Graph graph = new Graph(nodes, edges);
    private Scanner sc = new Scanner(System.in);
    private String input;
    private boolean flag;
      
    
    private void addLane(String laneId, int sourceLocNo, int destLocNo,
            int duration) {
        Edge lane = new Edge(laneId,nodes.get(sourceLocNo), nodes.get(destLocNo), duration );
        edges.add(lane);  
    }//end of addLane method
    
    
    public void menuScreen() {
		System.out.println("----------Refuel Shortest Path application----------");
    	System.out.println("Choose an action (type the digit):");
    	System.out.println("0: Load a ready map");
    	System.out.println("1: Add new city");
    	System.out.println("2: Link cities");
    	System.out.println("3: Display cities");
    	System.out.println("4: Create route");
    	System.out.println("5: Exit");
    	menuChoice();
	}//end of menuScreen method
    
    
    public void menuChoice() {

		input = sc.next();
		
    	switch (input) {
    	case "0":
    			oneClickMap();
    	case "1": 
    			addNewCities();
    			break;
    	case "2": 
    			linkCities();
    			break;
    	case "3": 
    			flag = false;
    			displayCities();
    			break;
    	case "4": createRoute();
    			break;
    	case "5": 
    			System.out.println("Goodbye!");
    			System.exit(0);
    	default: 
    			System.out.println("Invalid input,try again...");
    			menuScreen();
    	}
    }//end of menuChoice method
    
    
    public void addNewCities() { 
    	String cityName;
    	boolean cityExists = false;
    	
		System.out.println("Provide a new city name: ");
        cityName = sc.next();
        
        for (Vertex vertex : nodes) {
        	if (vertex.getName().compareTo(cityName) == 0) {
        		System.out.println("City already exists.");
        		cityExists = true;
        		break;
        	}
        }
        
        if (cityExists == false) {
        	Vertex location = new Vertex(cityName, cityName);
            nodes.add(location);
        }
        
        menuScreen();
	}//end of addnewCities method
	
    
	public void linkCities() {
		flag = true;
		displayCities();
		
		try {
			int city_1;
			System.out.println("Enter first city id: ");
			city_1 = sc.nextInt();
			System.out.println("Enter second city id: ");
			int city_2;
			city_2 = sc.nextInt();
			if (edges.contains(edges.get(city_1))&&edges.contains(edges.get(city_2))) {
				System.out.println("Cities already connected.");
			}
			else {
				System.out.println("Enter the distance between them: ");
				int distance;
				distance = sc.nextInt();
				addLane("lane_1",city_1,city_2,distance);
				addLane("lane_2",city_2,city_1,distance);
			}
			
		} catch (Exception e) {
			//e.printStackTrace();
			//Logger.getLogger(App.class.getName()).log(Level.INFO, "Invalid input,try again", e);
			System.out.println("Invalid input,try again...");
		}
		
		menuScreen();
    }//end of linkCities method
	
	
	public void displayCities() {
		System.out.println("Cities' list:");
		if (nodes.isEmpty()) {
			System.out.println("No cities in the list.");
		}
		else {
			for (Vertex vertex : nodes) {
	            System.out.print(nodes.indexOf(vertex)+":");
	            System.out.println(vertex.getName());
	        }
			/*for (Edge edge : edges) {
				System.out.print(edges.indexOf(edge)+":");
	            System.out.println(" ID: " + edge.getId() + " Weight: " + edge.getWeight());
			}*/
		}
		
		if (flag==false) {
			menuScreen();
		}
	}//end of displayCities method
	
	
	public void createRoute( ) {
		flag=true;
		displayCities();
		
        try {
			int start;
			System.out.print("Provide starting point: ");
			start = sc.nextInt();
			System.out.println();
			int dest;
			System.out.print("Provide destination: ");
			dest = sc.nextInt();
			
			DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
			dijkstra.execute(nodes.get(start));
			LinkedList<Vertex> path = dijkstra.getPath(nodes.get(dest));
			
			System.out.println("Nodes on this route: " + path.size());
			for (Vertex vertex : path) {
			    System.out.println(vertex);
			}
			System.out.println("Total distance: " + dijkstra.getShortestDistance(nodes.get(dest)));
			
		} catch (Exception e) {
			System.out.println("Invalid input,try again...");
			//e.printStackTrace();
		}
        
        menuScreen();
	}//end of createRoute method
    
	
	public void oneClickMap() {
		Vertex Athens = new Vertex("Ath","Athens");
        nodes.add(Athens);
        Vertex Corinth = new Vertex("Cor","Corinth");
        nodes.add(Corinth);
        Vertex Tripoli = new Vertex("Tri","Tripoli");
        nodes.add(Tripoli);
        Vertex Patra = new Vertex("Pat","Patra");
        nodes.add(Patra);
        Vertex Kalamata = new Vertex("Kal","Kalamata");
        nodes.add(Kalamata);
        Vertex Sparti = new Vertex("Spa","Sparti");
        nodes.add(Sparti);
        
        addLane("Ath-Cor",nodes.indexOf(Athens),nodes.indexOf(Corinth),80);
        addLane("Cor-Ath",nodes.indexOf(Corinth),nodes.indexOf(Athens),80);
        
        addLane("Cor-Tri",nodes.indexOf(Corinth),nodes.indexOf(Tripoli),79);
        addLane("Tri-Cor",nodes.indexOf(Tripoli),nodes.indexOf(Corinth),79);
        
        addLane("Cor-Pat",nodes.indexOf(Corinth),nodes.indexOf(Patra),132);
        addLane("Pat-Cor",nodes.indexOf(Patra),nodes.indexOf(Corinth),132);
        
        addLane("Tri-Kal",nodes.indexOf(Tripoli),nodes.indexOf(Kalamata),82);
        addLane("Kal-Tri",nodes.indexOf(Kalamata),nodes.indexOf(Tripoli),82);
        
        addLane("Tri-Spa",nodes.indexOf(Tripoli),nodes.indexOf(Sparti),50);
        addLane("Spa-Tri",nodes.indexOf(Sparti),nodes.indexOf(Tripoli),50);
        
        addLane("Pat-Kal",nodes.indexOf(Patra),nodes.indexOf(Kalamata),216);
        addLane("Kal-Pat",nodes.indexOf(Kalamata),nodes.indexOf(Patra),216);
        
        menuScreen();
	}//end of oneClickMap method
	
}
