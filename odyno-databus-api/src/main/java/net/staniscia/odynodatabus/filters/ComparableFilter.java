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

// TODO: Auto-generated Javadoc
/**
 * Filtro per comparazione di oggetti.
 *
 * @param <T> the generic type
 * @author Alessandro Staniscia
 */
public abstract class ComparableFilter<T extends Comparable<T>> extends	Filter<T> {

	/** The comparable. */
	private final T comparable;

	/**
	 * Instantiates a new comparable filter.
	 *
	 * @param comparable the comparable
	 */
	protected ComparableFilter(T comparable) {
		this.comparable = comparable;
	}

	/* (non-Javadoc)
	 * @see net.staniscia.odynodatabus.filters.Filter#passes(java.lang.Object)
	 */
	@Override
	public boolean passes(T object) {
		return passes(object.compareTo(comparable));
	}

	/**
	 * Passes.
	 *
	 * @param result the result
	 * @return true, if successful
	 */
	protected abstract boolean passes(int result);

	
	/**
	 * Less than.
	 *
	 * @param <T> the generic type
	 * @param comparable the comparable
	 * @return the filter
	 */
	public static <T extends Comparable<T>> Filter<T> lessThan(	T comparable) {
		return new ComparableFilter<T>(comparable) {
			@Override
			protected boolean passes(int result) {
				return result < 0;
			}
		};
	}
	
	/**
	 * More than.
	 *
	 * @param <T> the generic type
	 * @param comparable the comparable
	 * @return the filter
	 */
	public static <T extends Comparable<T>> Filter<T> moreThan(	T comparable) {
		return new ComparableFilter<T>(comparable) {
			@Override
			protected boolean passes(int result) {
				return result > 0;
			}
		};
	}
	
	/**
	 * Equal than.
	 *
	 * @param <T> the generic type
	 * @param comparable the comparable
	 * @return the filter
	 */
	public static <T extends Comparable<T>> Filter<T> equalThan(T comparable) {
		return new ComparableFilter<T>(comparable) {
			@Override
			protected boolean passes(int result) {
				return result == 0;
			}
		};
	}

}
