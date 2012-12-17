package net.staniscia.odynodatabus.tests;

import java.io.Serializable;
/**
 * Class <code>PersonBean</code>.
 */
public class PersonBeanTest implements java.io.Serializable {
 
    private String name = null;
    
    
    private boolean deceased = false;
 
    /** No-arg constructor (takes no arguments). */
    public PersonBeanTest() {
    }
 
    /**
     * Property <code>name</code> (note capitalization) readable/writable.
     */
    public String getName() {
        return name;
    }
 
    /**
     * Setter for property <code>name</code>.
     * @param NAME
     */
    public void setName(final String NAME) {
        name = NAME;
    }
 
    /**
     * Getter for property "deceased"
     * Different syntax for a boolean field (is vs. get)
     */
    public boolean isDeceased() {
        return deceased;
    }
 
    /**
     * Setter for property <code>deceased</code>.
     * @param DECEASED
     */
    public void setDeceased(final boolean DECEASED) {
        deceased = DECEASED;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (deceased ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PersonBeanTest other = (PersonBeanTest) obj;
		if (deceased != other.deceased)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PersonBeanTest [name=" + name + ", deceased=" + deceased + "]";
	}
    
    
}