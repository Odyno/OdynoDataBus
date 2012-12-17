/**
 * Simple implementation of DataBus
 */
package net.staniscia.odynodatabus.imp.mem;

import java.io.Serializable;
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
import net.staniscia.odynodatabus.msg.Envelop;
import net.staniscia.odynodatabus.filters.Filter;

public class MemoryDataBus implements DataBusService, Runnable {

    private static final Logger LOGGER = Logger.getLogger(MemoryDataBus.class.getName());
    private static final long TIME_LIMIT_TO_ATTENDS = 1;
    private final List<Subscriber<?, ?>> subscribers = new LinkedList<Subscriber<?, ?>>();
    private final LinkedBlockingQueue<Envelop<?>> memorydata = new LinkedBlockingQueue<Envelop<?>>();
    private final ExecutorService executor = Executors.newCachedThreadPool();
    private final ReadWriteLock rwloaLock = new ReentrantReadWriteLock();
    private final Lock read = rwloaLock.readLock();
    private final Lock write = rwloaLock.writeLock();
    private DataBusServiceStatus myStatus;

    /**
     * Memory implementation of odyno-databus-api
     */
    public MemoryDataBus() {
        super();
          swithMyStatus(DataBusServiceStatus.BOOTING);

    }

    /**
     * Init of component
     */
    public void init() {
        executor.execute(this);
                  
    }

    /**
     * Shutdown of component
     */
    public void stop() {
        swithMyStatus(DataBusServiceStatus.DESTROY);
        executor.shutdownNow();
    }

    /**
     * Function to use when someone want submit some data on databus
     *
     * @param objectType to submit
     */
    public void submitData(final Envelop<?> objectType) {
        executor.execute(new Runnable() {
            public void run() {
                memorydata.add(objectType);
            }
        });
    }

    @Override
    public <T extends Serializable> MemDataPublisher<T> getDataPublisher(Class<T> objectType) {
        return new MemDataPublisher<T>(this);
    }

    @Override
    public <T extends Serializable> boolean registerSubscriber(Subscriber<T, ? extends Filter<T>> subscriver) {
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

    @Override
    public <T extends Serializable> boolean unRegisterSubscriber(Subscriber<T, ? extends Filter<T>> subscriver) {
        write.lock();
        try {
            subscriver.onChangeSystemStatus(DataBusServiceStatus.DESTROY);
            return subscribers.remove(subscriver);
        } finally {
            write.unlock();
        }
    }

    /**
     * none
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

    private void notifyAtListner(final Subscriber<?, ?> listener, final Envelop data) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Filter f = listener.getFilter();
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
