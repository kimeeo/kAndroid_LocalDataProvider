package com.kimeeo.kAndroid.localDataProvider;

import android.content.Context;
import android.support.annotation.RawRes;

import java.io.InputStream;

/**
 * Created by bhavinpadhiyar on 2/15/16.
 */
abstract public class RawDataProvider extends BaseDataProvider {

    public RawDataProvider(Context context)
    {
        super(context);
    }

    @Override
    protected InputStream getNextInputStream(Context context) throws Exception {
        int id=getNextResID();
        if(id!=-1) {
            //int id = context.getResources().getIdentifier(url, "raw", context.getPackageName());
            return context.getResources().openRawResource(id);
        }
        else
            return null;
    }
    protected InputStream getRefreshInputStream(Context context) throws Exception
    {
        int id=getRefreshResID();
        if(id!=-1) {
            //int id = context.getResources().getIdentifier(url, "raw", context.getPackageName());
            return context.getResources().openRawResource(id);
        }
        else
            return null;
    }
    @RawRes
    protected abstract int getNextResID();

    @RawRes
    protected int getRefreshResID() {
        return -1;
    }

    @Override
    public String[] requirePermissions() {
        return null;
    }
}

