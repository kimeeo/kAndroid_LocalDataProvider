package com.kimeeo.kAndroid.localDataProvider;

import android.content.Context;

import com.kimeeo.kAndroid.listViews.dataProvider.DataModel;

import java.io.InputStream;

/**
 * Created by bhavinpadhiyar on 2/15/16.
 */
abstract public class AssetsDataManager extends BaseDataManager {

    public AssetsDataManager(Context context)
    {
        super(context);
    }

    @Override
    protected InputStream getNextInputStream(Context context) throws Exception {
        String url=getNextURL();
        if(url!=null)
            return context.getAssets().open(url);
        else
            return null;
    }
    protected InputStream getRefreshInputStream(Context context) throws Exception
    {
        String url=getRefreshURL();
        if(url!=null)
            return context.getAssets().open(url);
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
