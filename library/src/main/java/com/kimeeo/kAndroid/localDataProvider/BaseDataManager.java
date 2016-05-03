package com.kimeeo.kAndroid.localDataProvider;

import android.content.Context;

import com.google.gson.Gson;
import com.kimeeo.kAndroid.listViews.dataProvider.DataModel;
import com.kimeeo.kAndroid.listViews.dataProvider.IParseableObject;
import com.kimeeo.kAndroid.listViews.dataProvider.PermissionsBasedDataProvider;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by bhavinpadhiyar on 1/30/16.
 */
abstract public class BaseDataManager extends PermissionsBasedDataProvider {

    protected abstract Class<DataModel> getDataModel();
    abstract protected InputStream getNextInputStream(Context context) throws Exception;
    abstract protected InputStream getRefreshInputStream(Context context) throws Exception;

    protected Gson gson;
    private static final String LOG_TAG= "BaseDataManager";
    protected Context context;
    public BaseDataManager(Context context) {
        super(context);
        this.context = context;
        gson= new Gson();
    }

    @Override
    protected void invokeLoadNext()
    {
        try {
            String data=readTxt(getNextInputStream(context));
            dataHandler(data);
        } catch (Exception e) {
            setCanLoadNext(false);
            dataLoadError(e);
        }
    }

    @Override
    protected void invokeLoadRefresh() {
        try {
            String data=readTxt(getRefreshInputStream(context));
            dataHandler(data);
        } catch (Exception e) {
            setCanLoadNext(false);
            dataLoadError(e);
        }
    }


    protected void dataHandler(String json)
    {
        if(json!=null) {
            try {
                Class<DataModel> clazz = getDataModel();
                DataModel dataModel = gson.fromJson(json, clazz);
                List<?> list = dataModel.getDataProvider();
                if (list != null) {
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i) instanceof IParseableObject)
                            ((IParseableObject) list.get(i)).dataLoaded(dataModel);
                    }
                    dataIn(dataModel);
                    addData(list);
                } else {
                    dataIn(null);
                    dataLoadError(null);
                }
            } catch (Throwable e) {
                dataIn(null);
                dataLoadError(e);
            }
        }
        else
        {
            dataIn(null);
            dataLoadError(null);
        }

    }

    private void dataIn(DataModel dataModel) {

    }

    public void garbageCollectorCall() {
        super.garbageCollectorCall();
        context=null;
    }

    protected String readTxt(InputStream inputStream){
        if(inputStream==null)
            return null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int i;
        try {
            i = inputStream.read();
            while (i != -1)
            {
                byteArrayOutputStream.write(i);
                i = inputStream.read();
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArrayOutputStream.toString();
    }
}
