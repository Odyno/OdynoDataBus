package net.staniscia.odynodatabus.msg;

/**
 * Manage simple String message
 */
public final class StringMessage implements Envelop<String> {

    private static final long serialVersionUID = -6418123090379845471L;
    private long timeOfOccurence;
    private String treasure;

    /**
     * The costructor with message to send
     * @param string 
     */
    public StringMessage(String string) {
        timeOfOccurence = System.currentTimeMillis();
        this.treasure = string;
    }

    
    @Override
    public long getTimeOfOccurence() {
        return timeOfOccurence;
    }

    @Override
    public Class<String> getContentType() {
        return String.class;
    }

    @Override
    public String getContent() {
        return treasure;
    }

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
