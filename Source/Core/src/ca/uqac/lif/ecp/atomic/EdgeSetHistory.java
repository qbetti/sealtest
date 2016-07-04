package ca.uqac.lif.ecp.atomic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ca.uqac.lif.ecp.Edge;
import ca.uqac.lif.ecp.MathList;
import ca.uqac.lif.ecp.MathSet;

/**
 * Triaging function where the class of a trace is the set of <i>n</i>-grams
 * found in the sequence of transitions of the automaton that are visited
 */
public class EdgeSetHistory extends AutomatonFunction<MathSet<Collection<Edge<AtomicEvent>>>> 
{
	/**
	 * The width of the history
	 */
	protected int m_width;
	
	/**
	 * Whether the n-grams should be ordered or not
	 */
	protected boolean m_ordered = true;
	
	/**
	 * Whether to take the label of the transition into account,
	 * or just the endpoints (source-destination states)
	 */
	protected boolean m_useLabel = false;
	
	/**
	 * The history of n-grams collected so far
	 */
	protected MathSet<Collection<Edge<AtomicEvent>>> m_history;

	/**
	 * A sliding window of the n last edges visited
	 */
	protected List<Edge<AtomicEvent>> m_stateWindow;

	/**
	 * Instantiates the triaging function
	 * @param a The automaton that is used as the basis for the triaging
	 * @param width The size of the <i>n</i>-grams (i.e. the value of
	 *   <i>n</i>)
	 * @param ordered Whether the n-grams should be ordered or not
	 * @param label Whether to take the label of the transition into account,
	 *   or just the endpoints (source-destination states)
	 */
	public EdgeSetHistory(Automaton a, int width, boolean ordered, boolean label)
	{
		super(a);
		m_ordered = ordered;
		m_width = width;
		m_useLabel = label;
	}

	@Override
	public MathSet<Collection<Edge<AtomicEvent>>> getStartClass()
	{
		return new MathSet<Collection<Edge<AtomicEvent>>>();
	}
	
	@Override
	public void reset()
	{
		super.reset();
		m_history = new MathSet<Collection<Edge<AtomicEvent>>>();
		m_stateWindow = new ArrayList<Edge<AtomicEvent>>(m_width);		
	}
	
	@Override
	public MathSet<Collection<Edge<AtomicEvent>>> processTransition(Edge<AtomicEvent> edge)
	{
		if (m_useLabel)
		{
			m_stateWindow.add(edge);
		}
		else
		{
			m_stateWindow.add(new EdgeLabelDontCare(edge));	
		}
		if (m_stateWindow.size() == m_width + 1)
		{
			m_stateWindow.remove(0);
		}
		Collection<Edge<AtomicEvent>> ost = newCollection();
		ost.addAll(m_stateWindow);
		m_history.add(ost);
		MathSet<Collection<Edge<AtomicEvent>>> out_set = new MathSet<Collection<Edge<AtomicEvent>>>();
		out_set.addAll(m_history);
		return out_set;		
	}
	
	/**
	 * Gets a new collection of states. Depending on whether the visitor
	 * is ordered or not, a set or a list will be given.
	 * @return The collection
	 */
	protected Collection<Edge<AtomicEvent>> newCollection()
	{
		if (m_ordered)
		{
			return new MathList<Edge<AtomicEvent>>();
		}
		return new MathSet<Edge<AtomicEvent>>();
	}
	
	/**
	 * A special type of edge that does not compare labels when equality
	 * with another edge is computed
	 */
	protected static class EdgeLabelDontCare extends Edge<AtomicEvent>
	{
		public EdgeLabelDontCare(Edge<AtomicEvent> e)
		{
			super(e.getSource(), e.getLabel(), e.getDestination());
		}
		
		@Override
		public int hashCode()
		{
			return m_source + m_destination;
		}
		
		@Override
		public boolean equals(Object o)
		{
			if (o == null || !(o instanceof Edge))
			{
				return false;
			}
			@SuppressWarnings("unchecked")
			Edge<AtomicEvent> e = (Edge<AtomicEvent>) o;
			return m_source == e.getSource() && m_destination == e.getDestination();
		}
	}
}