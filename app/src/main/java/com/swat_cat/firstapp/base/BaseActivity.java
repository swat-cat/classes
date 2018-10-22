package com.swat_cat.firstapp.base;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.ViewUtils;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.pavlospt.rxfile.RxFile;
import com.squareup.otto.Bus;
import com.swat_cat.firstapp.R;
import com.swat_cat.firstapp.base.dialogs.DialogShower;
import com.swat_cat.firstapp.base.dialogs.events.DialogWasDissmisedEvent;
import com.swat_cat.firstapp.base.dialogs.events.HideDialogEvent;
import com.swat_cat.firstapp.base.dialogs.events.ShowDialogEvent;
import com.swat_cat.firstapp.services.Navigator;
import com.swat_cat.firstapp.services.navigation.BackNavigator;
import com.swat_cat.firstapp.services.navigation.managers.ScreenNavigationBackManager;
import com.swat_cat.firstapp.services.navigation.managers.ScreenNavigationManager;
import com.swat_cat.firstapp.services.navigation.managers.events.BackPressEvent;
import com.swat_cat.firstapp.shopping_list.shopping_item.LoadImageType;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import rx.Scheduler;
import rx.Subscriber;
import timber.log.Timber;

/**
 * Created by max_ermakov on 1/13/17.
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected final String TAG = getClass().getSimpleName();
    private Bus bus;
    private CompositeDisposable subscriptions;
    private DialogShower dialogShower;
    private CompositeDisposable imageSubs;
    private Handler handler;
    static final int REQUEST_TAKE_PHOTO = 92;
    static final int REQUEST_LOAD_PHOTO = 93;
    private String photoPath = "";
    final RxPermissions rxPermissions = new RxPermissions(this);
    private Navigator navigator;
    private BackNavigator navigationBackManager;

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, " onStart()");
        if (subscriptions == null) {
            subscriptions = new CompositeDisposable();
        }
        imageSubs = new CompositeDisposable();
    }



    @Override
    protected void onStop() {
        Log.i(TAG, " onStop()");
        if (subscriptions!=null && !subscriptions.isDisposed()){
            subscriptions.dispose();
            subscriptions.clear();
            subscriptions = null;
        }
        if (imageSubs!=null && !imageSubs.isDisposed()){
            imageSubs.dispose();
        }
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, " onCreate()" + (savedInstanceState != null ? " recreating" : ""));
        super.onCreate(savedInstanceState);
        handler = new Handler(Looper.getMainLooper());
        bus = new Bus();
        dialogShower = new DialogShower(this);
        navigator = new ScreenNavigationManager(this);
        navigationBackManager = new ScreenNavigationBackManager(this);
        bus.register(dialogShower);
        bus.register(navigationBackManager);
    }

    @Override
    public void onBackPressed() {
        bus.post(new BackPressEvent());
    }


    public void loadImage(LoadImageType type){
        if (type == LoadImageType.CAMERA){
            rxPermissions.request(Manifest.permission.CAMERA)
                    .subscribe(granted ->{
                        if (granted){
                            loadFromCamera();
                        }
                    });
        }else {
            loadFromGallery();
        }
    }

    private void loadFromCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Timber.e(ex);
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                if ( Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP ) {
                    takePictureIntent.setClipData( ClipData.newRawUri( "", photoURI ) );
                    takePictureIntent.addFlags( Intent.FLAG_GRANT_WRITE_URI_PERMISSION|Intent.FLAG_GRANT_READ_URI_PERMISSION );
                }
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String imageFileName = "item"+new Date().toString();
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        photoPath = image.getAbsolutePath();
        return image;
    }

    private void loadFromGallery() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, REQUEST_LOAD_PHOTO);
    }

    //public abstract void lostConnectivityAction();

    @Override
    protected void onResume() {
        Log.i(TAG, " onResume()");
        super.onResume();
        //checkConnectivity();
    }

    @Override
    protected void onPostResume() {
        Log.i(TAG, " onPostResume()");
        super.onPostResume();
    }


    @Override
    protected void onPause() {
        Log.i(TAG, " onPause()");
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, " onDestroy()");
        bus.unregister(dialogShower);
        bus.unregister(navigationBackManager);
        super.onDestroy();
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(TAG, "onNewIntent() Intent=" + intent);
    }


    public Bus getBus() {
        return bus;
    }


    public void freeMemory() {
        System.runFinalization();
        Runtime.getRuntime().gc();
        System.gc();
    }

    public void hideKeyboard() {
        try {
            IBinder windowToken = getWindow().getDecorView().getRootView().getWindowToken();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(windowToken, 0);
        } catch (Exception e) {
            Timber.e(e.getLocalizedMessage());
        }
    }

    public void showKeyboard(EditText editText) {
        try {
            IBinder windowToken = getWindow().getDecorView().getRootView().getWindowToken();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(editText, 0);
        } catch (Exception e) {
            Timber.e(e.getLocalizedMessage());
        }
    }
    //public abstract ActionBarContract.View getActionBarView();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK){
            if (requestCode == REQUEST_TAKE_PHOTO){
                File file = new File(photoPath);
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        getBus().post(new ImageEvent(file));
                    }
                });
            }else if (requestCode == REQUEST_LOAD_PHOTO){
                final Uri imageUri = data.getData();
                if (imageUri != null) {
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                           RxFile.createFileFromUri(BaseActivity.this,imageUri)
                                    .subscribe(new Subscriber<File>() {
                                        @Override
                                        public void onCompleted() {

                                        }

                                        @Override
                                        public void onError(Throwable e) {

                                        }

                                        @Override
                                        public void onNext(File file) {
                                            getBus().post(new ImageEvent(file));
                                        }
                                    });

                        }
                    });
                }
            }
        }
    }

    public static String getRealPathFromURI_API19(Context context, Uri uri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        String result = null;

        CursorLoader cursorLoader = new CursorLoader(
                context,
                uri, proj, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();

        if(cursor != null){
            int column_index =
                    cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            result = cursor.getString(column_index);
        }
        return result;
    }
    public static String getRealPathFromURI_BelowAPI11(Context context, Uri contentUri){
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
        String p = null;
        if (cursor!=null) {
            int column_index
                    = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            p = cursor.getString(column_index);
            cursor.close();
        }
        return p;
    }

    public void dialogDismissed(){
        /*if (!connectivityNotifier.isConnected()){
            isNoNetworkDialogShowing = false;

            checkInternetWithDelay(INTERVAL_SHOWING_NO_NETWORK_DIALOG);
        }*/
        getBus().post(new DialogWasDissmisedEvent());
    }

    public Navigator getNavigator() {
        return navigator;
    }

    public BackNavigator getNavigationBackManager() {
        return navigationBackManager;
    }
}

