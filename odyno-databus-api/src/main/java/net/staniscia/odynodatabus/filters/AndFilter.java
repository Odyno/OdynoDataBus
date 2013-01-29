/*  
    Copyright 2012  Alessandro Staniscia ( alessandro@staniscia.net )

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License, version 2, as
    published by the Free Software Foundation.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
*/
package net.staniscia.odynodatabus.filters;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class AndFilter.
 *
 * @param <T> the generic type
 */
public class AndFilter<T extends Serializable> extends Filter<T> {
	
	/** The filters. */
	private final Filter<T>[] filters;

	/**
	 * Instantiates a new and filter.
	 *
	 * @param filters the filters
	 */
	public AndFilter(final Filter<T>... filters) {
		this.filters = filters;
	}

	/* (non-Javadoc)
	 * @see net.staniscia.odynodatabus.filters.Filter#passes(java.lang.Object)
	 */
	@Override
	public boolean passes(T object) {
		for (Filter<T> filter : filters) {
			if (!filter.passes(object))
				return false; // short circuit
		}
		return true;
	}
}