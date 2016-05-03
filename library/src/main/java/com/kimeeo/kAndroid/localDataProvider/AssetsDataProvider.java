package com.kimeeo.kAndroid.localDataProvider;

import android.content.Context;

import java.io.InputStream;

/**
 * Created by bhavinpadhiyar on 2/15/16.
 */
abstract public class AssetsDataProvider extends BaseDataProvider {

    public AssetsDataProvider(Context context)
    {
        super(context);
    }

    @Override
    protected InputStream getNextInputStream(Context context) throws Exception {
        String url=getNextAssetsPath();
        if(url!=null)
            return context.getAssets().open(url);
        else
            return null;
    }
    protected InputStream getRefreshInputStream(Context context) throws Exception
    {
        String url=getRefreshAssetsPath();
        if(url!=null)
            return context.getAssets().open(url);
        else
            return null;
    }
    protected abstract String getNextAssetsPath();
    protected String getRefreshAssetsPath() {
        return null;
    }



    @Override
    public String[] requirePermissions() {
        return null;
    }
}
