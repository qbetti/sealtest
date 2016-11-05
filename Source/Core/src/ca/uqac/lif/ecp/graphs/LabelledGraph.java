package ca.uqac.lif.ecp.graphs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import ca.uqac.lif.ecp.Edge;
import ca.uqac.lif.ecp.Event;

public class LabelledGraph<T extends Event>
{
	/**
	 * The set of vertices of this graph. Each vertex is responsible
	 * for storing its outgoing edges.
	 */
	private Set<Vertex<T>> m_vertices;
	
	/**
	 * The ID of the initial index
	 */
	private int m_initialId;
	
	/**
	 * Creates an empty graph
	 */
	public LabelledGraph()
	{
		super();
		m_vertices = new HashSet<Vertex<T>>();
		m_initialId = 0;
	}

	/**
	 * Creates a copy of a graph graph
	 * @param graph The graph to copy
	 */
	public LabelledGraph(LabelledGraph<T> graph)
	{
		this();
		for (Vertex<T> v : graph.m_vertices)
		{
			m_vertices.add(new Vertex<T>(v));
		}
	}
	
	/**
	 * Gets the vertex with given ID
	 * @param id The ID
	 * @return The vertex, or null if no vertex exists with that ID
	 */
	public Vertex<T> getVertex(int id)
	{
		for (Vertex<T> v : m_vertices)
		{
			if (v.m_id == id)
			{
				return v;
			}
		}
		return null;
	}
	
	/**
	 * Adds a vertex to this graph
	 * @param v The vertex
	 * @return This graph
	 */
	public void add(Vertex<T> v)
	{
		m_vertices.add(v);
	}
	
	/**
	 * Produces the adjacency matrix of the Cayley Graph. This matrix is
	 * such that entry (<i>i</i>,<i>j</i>) = <i>k</i> if there exist <i>k</i> 
	 * directed edges from
	 * vertex labelled <i>i</i> to vertex labelled <i>j</i>. Although the
	 * entries of that matrix are natural numbers, their type is <tt>float</tt>,
	 * as that matrix can be used in algorithms that multiply it by a vector
	 * of <tt>float</tt>s.
	 * 
	 * @return The matrix
	 */
	public float[][] getAdjacencyMatrix()
	{
		int size = m_vertices.size();
		// Create empty table and fill with 0
		float table[][] = new float[size][size];
		for (int i = 0; i < size; i++)
		{
			for (int j = 0; j < size; j++)
			{
				table[i][j] = 0;
			}
		}
		// Get vertex labels in a sorted list
		// Iterate over each vertex...
		for (int i = 0; i < size; i++)
		{
			int label = i;
			Vertex<T> v = getVertex(label);
			// Iterate over each edge leaving this vertex
			for (Edge<T> e : v.m_outEdges)
			{
				int j = e.getDestination();
				table[j][i]++;
			}
		}
		return table;
	}
	
	/**
	 * Fetches the list of all vertex IDs present in the graph 
	 * @return The list of IDs
	 */
	public List<Integer> getVertexLabels()
	{
		List<Integer> out = new ArrayList<Integer>();
		for (Vertex<T> v : m_vertices)
		{
			out.add(v.m_id);
		}
		return out;
	}
	
	/**
	 * Gets the vertex with given ID
	 * @param id The ID
	 * @return The vertex, or null if not found
	 */
	public Vertex<T> getVertexWithId(int id)
	{
		for (Vertex<T> v : m_vertices)
		{
			if (v.getId() == id)
			{
				return v;
			}
		}
		return null;
	}
	
	/**
	 * Gets the number of edges in the graph
	 * @return The number of edges
	 */
	public int getEdgeCount()
	{
		int count = 0;
		for (Vertex<T> v : m_vertices)
		{
			count += v.m_outEdges.size();
		}
		return count;
	}
	
	/**
	 * Gets the number of vertices in the graph
	 * @return The number of vertices
	 */
	public int getVertexCount()
	{
		return m_vertices.size();
	}
	
	/**
	 * Gets the set of vertices of this graph
	 * @return The set of vertices
	 */
	public Set<Vertex<T>> getVertices()
	{
		return m_vertices;
	}
	
	/**
	 * Gets the list of all edges in the graph
	 * @return The list of edges
	 */
	public List<Edge<T>> getEdges()
	{
		List<Edge<T>> edges = new LinkedList<Edge<T>>();
		for (Vertex<T> v : m_vertices)
		{
			edges.addAll(v.getEdges());
		}
		return edges;
	}

	/**
	 * Gets the initial vertex of the Cayley graph
	 * @return The initial vertex
	 */
	public Vertex<T> getInitialVertex()
	{
		return getVertex(m_initialId);
	}
	
	/**
	 * Sets the ID of the initial vertex
	 * @param id The ID
	 */
	public void setInitialVertexId(int id)
	{
		m_initialId = id;
	}
}