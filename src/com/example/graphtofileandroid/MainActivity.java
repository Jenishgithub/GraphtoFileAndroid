package com.example.graphtofileandroid;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.alg.KShortestPaths;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
	static SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> graph;
	static SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> retrievedgraphs;
	public static String fileName = "createResumeForm.ser";
	Button btnDeleteFile;
	public boolean isFileDeleated;
	public static TextView textView1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btnDeleteFile = (Button) findViewById(R.id.btnDeleteFile);
		textView1 = (TextView) findViewById(R.id.textView1);
		btnDeleteFile.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				isFileDeleated = MainActivity.this.deleteFile(fileName);
			}
		});

		graph = readFromFile(getApplicationContext());
		if (graph == null) {
			graph = new SimpleDirectedWeightedGraph<String, DefaultWeightedEdge>(
					DefaultWeightedEdge.class);
			addYensGraph();
			saveToFile(getApplicationContext());
			Toast.makeText(getApplicationContext(), "Only 1 time...",
					Toast.LENGTH_SHORT).show();
		}
		// retrievedgraphs = readFromFile(getApplicationContext());
		calculatepaths(graph);
	}

	public static void addYensGraph() {
		// TODO Auto-generated method stub
		graph.addVertex("C");
		graph.addVertex("D");
		graph.addVertex("E");
		graph.addVertex("F");
		graph.addVertex("G");
		graph.addVertex("H");

		DefaultWeightedEdge e1 = graph.addEdge("C", "D");
		graph.setEdgeWeight(e1, 3.554);

		DefaultWeightedEdge e2 = graph.addEdge("C", "E");
		graph.setEdgeWeight(e2, 2.554);

		DefaultWeightedEdge e3 = graph.addEdge("D", "F");
		graph.setEdgeWeight(e3, 4.554);

		DefaultWeightedEdge e4 = graph.addEdge("E", "D");
		graph.setEdgeWeight(e4, 1.554);

		DefaultWeightedEdge e5 = graph.addEdge("E", "F");
		graph.setEdgeWeight(e5, 2.554);

		DefaultWeightedEdge e6 = graph.addEdge("E", "G");
		graph.setEdgeWeight(e6, 3.554);

		DefaultWeightedEdge e7 = graph.addEdge("F", "G");
		graph.setEdgeWeight(e7, 2.554);

		DefaultWeightedEdge e8 = graph.addEdge("F", "H");
		graph.setEdgeWeight(e8, 1.554);

		DefaultWeightedEdge e9 = graph.addEdge("G", "H");
		graph.setEdgeWeight(e9, 2.554);
	}

	private static void calculatepaths(
			SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> retrievedgraphs) {
		// TODO Auto-generated method stub
		System.out.println("Now displaying k shortest paths:");
		KShortestPaths ksp = new KShortestPaths(retrievedgraphs, "C", 3);// here
																			// starting
		// node is "C"
		List<GraphPath> paths = ksp.getPaths("H");// here ending node is "H"
		if (paths == null)
			System.out.println("no path found.");
		else {
			StringBuilder builder = new StringBuilder();
			List<String> list = new ArrayList<>();
			for (GraphPath p : paths) {
				list = Graphs.getPathVertexList(p);
				System.out.println(list + "dick facess");
				builder.append(list + "dick faces");
			}
			textView1.setText(builder.toString());
		}
	}

	public void saveToFile(Context context) {
		try {
			FileOutputStream fileOutputStream = context.openFileOutput(
					fileName, Context.MODE_PRIVATE);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(
					fileOutputStream);
			objectOutputStream.writeObject(graph);
			objectOutputStream.close();
			fileOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> readFromFile(
			Context context) {
		SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> createResumeForm = null;
		try {
			FileInputStream fileInputStream = context.openFileInput(fileName);
			ObjectInputStream objectInputStream = new ObjectInputStream(
					fileInputStream);
			createResumeForm = (SimpleDirectedWeightedGraph<String, DefaultWeightedEdge>) objectInputStream
					.readObject();
			objectInputStream.close();
			fileInputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return createResumeForm;
	}

}
