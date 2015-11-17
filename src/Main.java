import java.io.IOException;

public class Main {
	public static void main(String[] args) throws IOException {
		System.out.println("Start....GA");
		TSPSlove GAslove = new GA(30,48,1000,0.8f,0.9f);
		GAslove.init("/Users/Joker/Documents/Code/Java/TSP/src/data.txt");
		GAslove.slove();
		System.out.println("Start....PSO");
		TSPSlove PSOslove = new PSO(10,48,100);
		PSOslove.init("/Users/Joker/Documents/Code/Java/TSP/src/data.txt");
		PSOslove.slove();
	}
}
