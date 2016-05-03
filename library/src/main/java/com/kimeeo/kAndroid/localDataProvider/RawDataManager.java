package com.kimeeo.kAndroid.localDataProvider;

import android.content.Context;

import java.io.InputStream;

/**
 * Created by bhavinpadhiyar on 2/15/16.
 */
abstract public class RawDataManager extends BaseDataManager {

    public RawDataManager(Context context)
    {
        super(context);
    }

    @Override
    protected InputStream getNextInputStream(Context context) throws Exception {
        String url=getNextURL();
        if(url!=null) {
            int id = context.getResources().getIdentifier(url, "raw", context.getPackageName());
            return context.getResources().openRawResource(id);
        }
        else
            return null;
    }
    protected InputStream getRefreshInputStream(Context context) throws Exception
    {
        String url=getRefreshURL();
        if(url!=null) {
            int id = context.getResources().getIdentifier(url, "raw", context.getPackageName());
            return context.getResources().openRawResource(id);
        }
        else
            return null;
    }
    protected abstract String getNextURL();
    protected String getRefreshURL() {
        return null;
    }

    @Override
    public String[] requirePermissions() {
        return null;
    }
}

