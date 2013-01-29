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

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.NoSuchElementException;

// TODO: Auto-generated Javadoc
/**
 * Classe per il filtraggio degli oggetti.
 *
 * @param <T> the generic type
 * @author Alessandro Staniscia
 */
public abstract class Filter<T> {

	/**
	 * Metodo su cui inserire la logica di filtro.
	 *
	 * @param object the object
	 * @return true, if successful
	 */
	public abstract boolean passes(T object);

	/**
	 * Gets the managed type.
	 *
	 * @return the managed type
	 */
	public Class<T> getManagedType() {
		Class<T> result = null;
		Type type = this.getClass().getGenericSuperclass();

		if (type instanceof ParameterizedType) {
			ParameterizedType pt = (ParameterizedType) type;
			Type[] fieldArgTypes = pt.getActualTypeArguments();
			result = extracted(fieldArgTypes);
		}

		return result;
	}

	/**
	 * Extracted.
	 *
	 * @param fieldArgTypes the field arg types
	 * @return the class
	 */
	@SuppressWarnings("unchecked")
	private Class<T> extracted(Type[] fieldArgTypes) {
		return (Class<T>) fieldArgTypes[0];
	}

	/**
	 * Filter.
	 *
	 * @param iterator the iterator
	 * @return the iterator
	 */
	public Iterator<T> filter(Iterator<T> iterator) {
		return new FilterIterator(iterator);
	}

	/**
	 * Filter.
	 *
	 * @param iterable the iterable
	 * @return the iterable
	 */
	public Iterable<T> filter(final Iterable<T> iterable) {
		return new Iterable<T>() {
			public Iterator<T> iterator() {
				return filter(iterable.iterator());
			}
		};
	}

	/**
	 * The Class FilterIterator.
	 */
	private class FilterIterator implements Iterator<T> {
		
		/** The iterator. */
		private Iterator<T> iterator;
		
		/** The next. */
		private T next;

		/**
		 * Instantiates a new filter iterator.
		 *
		 * @param iterator the iterator
		 */
		private FilterIterator(Iterator<T> iterator) {
			this.iterator = iterator;
			toNext();
		}

		/* (non-Javadoc)
		 * @see java.util.Iterator#hasNext()
		 */
		public boolean hasNext() {
			return next != null;
		}

		/* (non-Javadoc)
		 * @see java.util.Iterator#next()
		 */
		public T next() {
			if (next == null)
				throw new NoSuchElementException();
			T returnValue = next;
			toNext();
			return returnValue;
		}

		/* (non-Javadoc)
		 * @see java.util.Iterator#remove()
		 */
		public void remove() {
			throw new UnsupportedOperationException();
		}

		/**
		 * To next.
		 */
		private void toNext() {
			next = null;
			while (iterator.hasNext()) {
				T item = iterator.next();
				if (item != null && passes(item)) {
					next = item;
					break;
				}
			}
		}
	}
}
