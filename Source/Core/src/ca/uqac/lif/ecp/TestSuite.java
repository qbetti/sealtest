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
package ca.uqac.lif.ecp;

import ca.uqac.lif.structures.MathSet;

/**
 * A collection of traces
 * @author Sylvain Hallé
 *
 * @param <T> The type of the events in the traces
 */
public class TestSuite<T extends Event> extends MathSet<Trace<T>>
{
	/**
	 * Dummy UID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Creates a new empty test suite
	 */
	public TestSuite()
	{
		super();
	}
	
	/**
	 * Creates a test suite by copying the contents of another suite
	 * @param suite The other suite
	 */
	public TestSuite(TestSuite<T> suite)
	{
		super();
		addAll(suite);
	}
	
	/**
	 * Gets the total length of the test suite
	 * @return The total length
	 */
	public int getTotalLength()
	{
		int length = 0;
		for (Trace<T> trace : this)
		{
			length += trace.size();
		}
		return length;
	}
}
