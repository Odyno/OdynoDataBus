package net.staniscia.odynodatabus.filters;

public class FilterFactory {
	
	 public static final <T> Filter<T> makeNoFilter(T obj){
		return new Filter<T>() {
			@Override
			public boolean passes(T object) {
				return true;
			}
		};
	}
	 
	 public static final <T> Filter<T> makeAllFilter(T obj){
		return new Filter<T>() {
			@Override
			public boolean passes(T object) {
				return false;
			}
		};
	}
}
