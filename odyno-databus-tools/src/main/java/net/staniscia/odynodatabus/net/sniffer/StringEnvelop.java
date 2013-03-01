/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.staniscia.odynodatabus.net.sniffer;

import net.staniscia.odynodatabus.msg.Envelop;

/**
 *
 * @author odyssey
 */
public class StringEnvelop implements Envelop<String> {
     /**
         * The Constant serialVersionUID.
         */
        private static final long serialVersionUID = -6418123090379845471L;
        /**
         * The time of occurence.
         */
        private long timeOfOccurence;
        /**
         * The treasure.
         */
        private String treasure;

        public StringEnvelop() {
            timeOfOccurence = System.currentTimeMillis();
            
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

        public void setContent(String treasure) {
            this.treasure = treasure;
        }
        
        public static StringEnvelop make(final String t){
            StringEnvelop msg= new StringEnvelop();
            msg.setContent(t);
            return msg;
        }
}
