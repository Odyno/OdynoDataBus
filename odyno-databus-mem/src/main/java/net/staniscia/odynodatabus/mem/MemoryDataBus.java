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
package net.staniscia.odynodatabus.mem;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.staniscia.odynodatabus.DataBusService;
import net.staniscia.odynodatabus.DataBusServiceStatus;
import net.staniscia.odynodatabus.Subscriber;
import net.staniscia.odynodatabus.filters.Filter;
import net.staniscia.odynodatabus.msg.Envelop;
import org.osgi.service.component.annotations.Component;

// TODO: Auto-generated Javadoc
/**
 * The Class MemoryDataBus.
 */
@Component
public class MemoryDataBus implements DataBusService, Runnable {

    /**
     * The Constant LOGGER.
     */
    private static final Logger LOGGER = Logger.getLogger(MemoryDataBus.class.getName());
    /**
     * The Constant TIME_LIMIT_TO_ATTENDS.
     */
    private static final long TIME_LIMIT_TO_ATTENDS = 1;
    /**
     * The subscribers.
     */
    private final List<Subscriber<?, ?>> subscribers = new LinkedList<Subscriber<?, ?>>();
    /**
     * The memorydata.
     */
    private final LinkedBlockingQueue<Envelop<?>> memorydata = new LinkedBlockingQueue<Envelop<?>>();
    /**
     * The executor.
     */
    private final ExecutorService executor = Executors.newCachedThreadPool();
    /**
     * The rwloa lock.
     */
    private final ReadWriteLock rwloaLock = new ReentrantReadWriteLock();
    /**
     * The read.
     */
    private final Lock read = rwloaLock.readLock();
    /**
     * The write.
     */
    private final Lock write = rwloaLock.writeLock();
    /**
     * The my status.
     */
    private DataBusServiceStatus myStatus;

    /**
     * Memory implementation of odyno-databus-api.
     */
    public MemoryDataBus() {
        super();
        swithMyStatus(DataBusServiceStatus.BOOTING);
        LOGGER.log(Level.FINEST, "Startup OdynoDataBus");
    }

    /**
     * Init of component.
     */
    public void init() {
        executor.execute(this);
        LOGGER.log(Level.FINEST, "Init OdynoDataBus");
    }

    /**
     * Shutdown of component.
     */
    public void destroy() {
        swithMyStatus(DataBusServiceStatus.DESTROY);
        executor.shutdownNow();
        LOGGER.log(Level.FINEST, "Destory OdynoDataBus");
    }

    /**
     * Function to use when someone want submit some data on databus.
     *
     * @param objectType to submit
     */
    public void submitData(final Envelop<?> objectType) {
        LOGGER.log(Level.FINEST, "Submit data on OdynoDataBus");
        executor.execute(new Runnable() {
            public void run() {
                memorydata.add(objectType);
            }
        });
    }

    /* (non-Javadoc)
     * @see net.staniscia.odynodatabus.DataBusService#getDataPublisher(java.lang.Class)
     */
    @Override
    public <T extends Serializable> MemDataPublisher<T> getDataPublisher(Class<T> objectType) {
        LOGGER.log(Level.FINEST, "Returned one publisher on OdynoDataBus");
        return new MemDataPublisher<T>(this);
    }

    /* (non-Javadoc)
     * @see net.staniscia.odynodatabus.DataBusService#registerSubscriber(net.staniscia.odynodatabus.Subscriber)
     */
    @Override
    public <T extends Serializable> boolean registerSubscriber(Subscriber<T, ? extends Filter<T>> subscriver) {
        LOGGER.log(Level.FINEST, "request Subscription  on OdynoDataBus");
        subscriver.onChangeSystemStatus(DataBusServiceStatus.BOOTING);
        write.lock();
        try {
            final boolean reason = subscribers.add(subscriver);
            subscriver.onChangeSystemStatus(myStatus);
            return reason;
        } finally {
            write.unlock();
        }
    }

    /* (non-Javadoc)
     * @see net.staniscia.odynodatabus.DataBusService#unRegisterSubscriber(net.staniscia.odynodatabus.Subscriber)
     */
    @Override
    public <T extends Serializable> boolean unRegisterSubscriber(String subscriverId) {
        LOGGER.log(Level.FINEST, "request unSubscription  on OdynoDataBus");
        for (Iterator<Subscriber<?, ?>> it = subscribers.iterator(); it.hasNext();) {
            Subscriber<? extends Serializable, ? extends Filter<? extends Serializable>> subscriber = it.next();
            if (subscriber.getIdentification().equals(subscriverId)) {
                write.lock();
                try {
                    subscriber.onChangeSystemStatus(DataBusServiceStatus.DESTROY);
                    return subscribers.remove(subscriber);
                } finally {
                    write.unlock();
                }
            }
        }
        return false;
 }

    /**
     * none.
     */
    @Override
    public void run() {
        try {
            while (DataBusServiceStatus.BOOTING.equals(myStatus)
                    || DataBusServiceStatus.RUNNING.equals(myStatus)) {
                Envelop<?> data = memorydata.poll(TIME_LIMIT_TO_ATTENDS, TimeUnit.SECONDS);
                if (data != null) {
                    read.lock();
                    try {
                        for (Subscriber<?, ?> listener : subscribers) {
                            notifyAtListner(listener, data);
                        }
                    } finally {
                        read.unlock();
                    }
                }
                if (DataBusServiceStatus.BOOTING.equals(myStatus)) {
                    swithMyStatus(DataBusServiceStatus.RUNNING);
                }
            }
        } catch (InterruptedException e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }
    }

    /**
     * Notify at listner.
     *
     * @param listener the listener
     * @param data the data
     */
    private void notifyAtListner(final Subscriber<?, ?> listener, final Envelop data) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                final Filter f = listener.getFilter();
                LOGGER.log(Level.FINEST, "\ndata: {0}data.getDataType(): {1} listener: {2} f.getManagedType: {3} isAssignagle: {4} isEquals: {5}", new Object[]{data, data.getContentType(), listener, f.getManagedType(), data.getContentType().isAssignableFrom(f.getManagedType()), data.getContentType().equals(f.getManagedType())});
                if (data.getContentType().isAssignableFrom(f.getManagedType())) {
                    Serializable content = data.getContent();
                    if (f.passes(content)) {
                        listener.handle(data);
                    }
                }
            }
        });
    }

    /**
     * Swith my status.
     *
     * @param dataDistributionServiceStatus the data distribution service status
     */
    private void swithMyStatus(final DataBusServiceStatus dataDistributionServiceStatus) {
        for (final Subscriber<?, ?> listener : subscribers) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    listener.onChangeSystemStatus(dataDistributionServiceStatus);
                }
            });
        }
        myStatus = dataDistributionServiceStatus;
    }
}
