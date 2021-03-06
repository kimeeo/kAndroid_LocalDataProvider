package com.kimeeo.kAndroid.localDataProvider;

import android.Manifest;
import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by bhavinpadhiyar on 2/15/16.
 */

abstract public class FileDataProvider extends BaseDataProvider
{
    @Override
    final protected InputStream getNextInputStream(Context context) throws Exception {
        return null;
    }

    @Override
    final protected InputStream getRefreshInputStream(Context context) throws Exception {
        return null;
    }

    public FileDataProvider(Context context) {
        super(context);
    }

    protected abstract String getNextPath();
    protected String getRefreshPath() {
        return null;
    }

    @Override
    protected void invokeLoadNext()
    {
        String url =getNextPath();
        if(url!=null) {
            File file = new File(url);
            if (file.exists() && file.isDirectory() == false) {
                String data = readTxt(file);
                dataHandler(data);
            }
            else
            {
                setCanLoadNext(false);
                dataLoadError(null);
            }
        }
        else
        {
            setCanLoadNext(false);
            dataLoadError(null);
        }
    }

    @Override
    protected void invokeLoadRefresh() {
        String url =getRefreshPath();
        if(url!=null) {
            File file = new File(url);
            if (file.exists() && file.isDirectory() == false) {
                String data = readTxt(file);
                dataHandler(data);
            }
            else
            {
                setCanLoadNext(false);
                dataLoadError(null);
            }
        }
        else
        {
            setCanLoadNext(false);
            dataLoadError(null);
        }
    }

    @Override
    public String[] requirePermissions() {
        return new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    }

    @Override
    public String[] getFriendlyPermissionsMeaning() {
        return new String[]{"Storage"};
    }

    protected String readTxt(File file){
        StringBuilder text = new StringBuilder();
        try {

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();
        }
        catch (IOException e) {

        }
        return text.toString();
    }
}