package net.staniscia.odynodatabus.filters;

/**
 * Filtro per comparazione di oggetti
 * 
 * @author Alessandro Staniscia
 * 
 * @param <T>
 */
public abstract class ComparableFilter<T extends Comparable<T>> extends	Filter<T> {

	private final T comparable;

	protected ComparableFilter(T comparable) {
		this.comparable = comparable;
	}

	@Override
	public boolean passes(T object) {
		return passes(object.compareTo(comparable));
	}

	protected abstract boolean passes(int result);

	
	public static <T extends Comparable<T>> Filter<T> lessThan(	T comparable) {
		return new ComparableFilter<T>(comparable) {
			@Override
			protected boolean passes(int result) {
				return result < 0;
			}
		};
	}
	
	public static <T extends Comparable<T>> Filter<T> moreThan(	T comparable) {
		return new ComparableFilter<T>(comparable) {
			@Override
			protected boolean passes(int result) {
				return result > 0;
			}
		};
	}
	
	public static <T extends Comparable<T>> Filter<T> equalThan(T comparable) {
		return new ComparableFilter<T>(comparable) {
			@Override
			protected boolean passes(int result) {
				return result == 0;
			}
		};
	}

}
