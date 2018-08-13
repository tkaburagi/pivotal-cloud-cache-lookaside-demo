package io.pivotal.pccdemo.cq;


import org.apache.geode.cache.Operation;
import org.apache.geode.cache.query.CqEvent;
import org.apache.geode.cache.query.CqListener;


public class BookEventListener implements CqListener {


    @Override
    public void onEvent(CqEvent cqEvent) {
        Operation queryOperation = cqEvent.getQueryOperation();


        if (queryOperation.isUpdate())
        {

        }
        else if (queryOperation.isCreate())
        {

        }
        else if (queryOperation.isDestroy())
        {

        }
    }

    @Override
    public void onError(CqEvent cqEvent) {
        Throwable th = cqEvent.getThrowable();
        th.printStackTrace();
    }

    @Override
    public void close() {

    }
}
