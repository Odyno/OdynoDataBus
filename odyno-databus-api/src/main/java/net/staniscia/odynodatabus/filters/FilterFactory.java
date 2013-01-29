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
 * Filter Factory for filters.
 */
public class FilterFactory {
	
	 /**
 	 * Make no filter.
 	 *
 	 * @param <T> the generic type
 	 * @param obj the obj
 	 * @return the filter
 	 */
 	public static final <T> Filter<T> makeNoFilter(T obj){
		return new Filter<T>() {
			@Override
			public boolean passes(T object) {
				return true;
			}
		};
	}
	 
	 /**
 	 * Make all filter.
 	 *
 	 * @param <T> the generic type
 	 * @param obj the obj
 	 * @return the filter
 	 */
 	public static final <T> Filter<T> makeAllFilter(T obj){
		return new Filter<T>() {
			@Override
			public boolean passes(T object) {
				return false;
			}
		};
	}
}
