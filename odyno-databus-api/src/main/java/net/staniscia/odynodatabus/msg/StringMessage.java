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
package net.staniscia.odynodatabus.msg;

// TODO: Auto-generated Javadoc
/**
 * Manage simple String message.
 */
public class StringMessage implements Envelop<String> {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -6418123090379845471L;
    
    /** The time of occurence. */
    private long timeOfOccurence;
    
    /** The treasure. */
    private String treasure;

    /**
     * The constructor with message to send.
     *
     * @param string the string
     */
    public StringMessage(String string) {
        timeOfOccurence = System.currentTimeMillis();
        this.treasure = string;
    }
    
    
    /* (non-Javadoc)
     * @see net.staniscia.odynodatabus.msg.Envelop#getTimeOfOccurence()
     */
    @Override
    public long getTimeOfOccurence() {
        return timeOfOccurence;
    }

    /* (non-Javadoc)
     * @see net.staniscia.odynodatabus.msg.Envelop#getContentType()
     */
    @Override
    public Class<String> getContentType() {
        return String.class;
    }

    /* (non-Javadoc)
     * @see net.staniscia.odynodatabus.msg.Envelop#getContent()
     */
    @Override
    public String getContent() {
        return treasure;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("StringMessage [");
        builder.append("getContent=");
        builder.append(getContent());
        builder.append(", getContentType=");
        builder.append(getContentType());
        builder.append(", getTimeOfOccurence=");
        builder.append(getTimeOfOccurence());
        builder.append(", serialVersionUID=");
        builder.append(serialVersionUID);
        builder.append(", timeOfOccurence=");
        builder.append(timeOfOccurence);
        builder.append(", treasure=");
        builder.append(treasure);
        builder.append(", getClass=");
        builder.append(getClass());
        builder.append(", hashCode=");
        builder.append(hashCode());
        builder.append(", toString=");
        builder.append(toString());
        builder.append("]");
        return builder.toString();
    }
    
    
}
