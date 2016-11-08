/*
    Log trace triaging and etc.
    Copyright (C) 2016 Sylvain Hallé

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published
    by the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ca.uqac.lif.ecp.graphs;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.Scanner;

import org.junit.Test;

import ca.uqac.lif.ecp.CayleyGraph;
import ca.uqac.lif.ecp.PrefixCategoryClosure;
import ca.uqac.lif.ecp.atomic.AtomicEvent;
import ca.uqac.lif.ecp.atomic.Automaton;

/**
 * Unit tests for the prefix category closure solver
 * 
 * @author Sylvain Hallé
 */
public class PrefixCategoryClosureTest 
{
	@Test
	public void test1()
	{
		Automaton g = loadAutomaton("test1.dot");
		assertEquals(4, g.getVertexCount());
		assertEquals(4, g.getEdgeCount());
		PrefixCategoryClosure<AtomicEvent,String> solver = new PrefixCategoryClosure<AtomicEvent,String>();
		CayleyGraph<AtomicEvent,String> new_graph = solver.getClosureGraph(g, 3);
		assertNotNull(new_graph);
		assertEquals(6, new_graph.getVertexCount());
		//assertEquals(1, new_graph.getEdgeCount());
	}
		
	public Automaton loadAutomaton(String filename)
	{
		 InputStream is = this.getClass().getResourceAsStream(filename);
		 Scanner scanner = new Scanner(is);
		 return Automaton.parseDot(scanner);
	}
}
