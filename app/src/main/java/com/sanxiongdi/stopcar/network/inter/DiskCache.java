package com.sanxiongdi.stopcar.network.inter;

import com.google.gson.Gson;
import com.sanxiongdi.stopcar.base.BaseApplication;
import com.sanxiongdi.stopcar.entity.WrapperEntity;
import com.sanxiongdi.stopcar.uitls.Config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by lin.woo on 2017/5/3.
 */
public class DiskCache {

    private static File cacheDir;

    static {
        File dir = BaseApplication.getInstance().getCacheDir();
        cacheDir = new File(dir.getAbsolutePath(), "DiskCache");
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }
    }

    private static File getFile(String name) {
        File file = new File(cacheDir, name);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    public static Observable getObservable(String name, final Type type) {
        return Observable.just(name)
                .flatMap(new Func1<String, Observable<File>>() {
                    @Override
                    public Observable<File> call(String s) {
                        return Observable.just(getFile(s));
                    }
                }).flatMap(new Func1<File, Observable<WrapperEntity>>() {
                    @Override
                    public Observable<WrapperEntity> call(File file) {
                        WrapperEntity bean = null;
                        try {
                            bean = new Gson().fromJson(new FileReader(file), type);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        return Observable.just(bean);
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static void save(final String name, final WrapperEntity bean) {
        File file = getFile(name);
        OutputStream os = null;
        try {
            os = new FileOutputStream(file);
            Gson gson = new Gson();
            String buffer = gson.toJson(bean);
            Config.e(buffer);
            os.write(buffer.getBytes());
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != os) {
                try {
                    os.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
