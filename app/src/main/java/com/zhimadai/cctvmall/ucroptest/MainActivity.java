package com.zhimadai.cctvmall.ucroptest;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;
import com.zhimadai.cctvmall.ucroptest.util.EmptyUtils;
import com.zhimadai.cctvmall.ucroptest.util.PerUtils;
import com.zhimadai.cctvmall.ucroptest.util.PhotoUtils;
import com.zhimadai.cctvmall.ucroptest.util.RxSPUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static com.zhimadai.cctvmall.ucroptest.MyApp.mContext;

public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {
    private Uri resultUri;
    private ImageView imageView;
    private int mShareIndex = -1;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 2;
    public static final String AVATAR = "AVATAR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.iv_avatar);

        if (EmptyUtils.isNotEmpty(RxSPUtils.getContent(mContext, AVATAR))){
            String a = RxSPUtils.getContent(mContext, AVATAR);
            resultUri = Uri.parse(RxSPUtils.getContent(mContext, AVATAR));
            roadImageView(resultUri,imageView);
        }


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogHelper.showSaveDlg(MainActivity.this, mShareIndex, new Callback<DlgCallbackEntity>() {
                    @Override
                    public void submit(DlgCallbackEntity datas) {
                        if (0 == datas.index) {
                            methodRequiresTwoPermission();
                        } else if (1 == datas.index) {
                            methodRequiresOnePermission();
                        }
                    }
                });
            }
        });
    }

    //存储
    @AfterPermissionGranted(MY_PERMISSIONS_REQUEST_CALL_PHONE)
    private void methodRequiresTwoPermission() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            photo();
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.camera_and_location_rationale),
                    MY_PERMISSIONS_REQUEST_CALL_PHONE, perms);
        }
    }

    private void photo() {
        PhotoUtils.openLocalImage(this);
    }

    //相机
    @AfterPermissionGranted(MY_PERMISSIONS_REQUEST_CAMERA)
    private void methodRequiresOnePermission() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            camera();
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.camera_and_location_rationale),
                    MY_PERMISSIONS_REQUEST_CAMERA, perms);
        }
    }

    private void camera() {
        PhotoUtils.openCameraImage(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {
        // Some permissions have been granted
        // ...
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {
        // Some permissions have been denied
        // ...
        PerUtils.somePermissionPermanentlyDenied(this, list);

    }


    //重头戏
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PhotoUtils.GET_IMAGE_FROM_PHONE://选择相册之后的处理
                if (resultCode == RESULT_OK) {
                    initUCrop(data.getData());
                }

                break;
            case PhotoUtils.GET_IMAGE_BY_CAMERA://选择照相机之后的处理
                if (resultCode == RESULT_OK) {
                    initUCrop(PhotoUtils.imageUriFromCamera);
                }

                break;
            case PhotoUtils.CROP_IMAGE://普通裁剪后的处理
                Glide.with(mContext).
                        load(PhotoUtils.cropImageUri).
                        diskCacheStrategy(DiskCacheStrategy.RESULT).
                        bitmapTransform(new CropCircleTransformation(mContext)).
                        thumbnail(0.5f).
                        placeholder(R.mipmap.circle_elves_ball).
                        priority(Priority.LOW).
                        error(R.mipmap.circle_elves_ball).
                        fallback(R.mipmap.circle_elves_ball).
                        dontAnimate().
                        into(imageView);
                break;

            case UCrop.REQUEST_CROP://UCrop裁剪之后的处理
                if (resultCode == RESULT_OK) {
                    resultUri = UCrop.getOutput(data);
                    roadImageView(resultUri, imageView);
                    //上传服务端
                    RxSPUtils.putContent(mContext, AVATAR, resultUri.toString());
                } else if (resultCode == UCrop.RESULT_ERROR) {
                    final Throwable cropError = UCrop.getError(data);
                }
                break;
            case UCrop.RESULT_ERROR://UCrop裁剪错误之后的处理
                final Throwable cropError = UCrop.getError(data);
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //从Uri中加载图片 并将其转化成File文件返回
    private File roadImageView(Uri uri, ImageView imageView) {
        Glide.with(mContext).
                load(uri).
                diskCacheStrategy(DiskCacheStrategy.RESULT).
                bitmapTransform(new CropCircleTransformation(mContext)).
                thumbnail(0.5f).
                placeholder(R.mipmap.circle_elves_ball).
                priority(Priority.LOW).
                error(R.mipmap.circle_elves_ball).
                fallback(R.mipmap.circle_elves_ball).
                dontAnimate().
                into(imageView);

        return (new File(PhotoUtils.getImageAbsolutePath(this, uri)));
    }


    private void initUCrop(Uri uri) {
        //Uri destinationUri = RxPhotoUtils.createImagePathUri(this);

        SimpleDateFormat timeFormatter = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA);
        long time = System.currentTimeMillis();
        String imageName = timeFormatter.format(new Date(time));

        Uri destinationUri = Uri.fromFile(new File(getCacheDir(), imageName + ".png"));

        UCrop.Options options = new UCrop.Options();
        //设置裁剪图片可操作的手势
        options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.ALL);
        //设置隐藏底部容器，默认显示
        //options.setHideBottomControls(true);
        //设置toolbar颜色
        options.setToolbarColor(ActivityCompat.getColor(this, R.color.colorPrimary));
        //设置状态栏颜色
        options.setStatusBarColor(ActivityCompat.getColor(this, R.color.colorPrimaryDark));

        //开始设置
        //设置最大缩放比例
        options.setMaxScaleMultiplier(5);
        //设置图片在切换比例时的动画
        options.setImageToCropBoundsAnimDuration(666);
        //设置裁剪窗口是否为椭圆
//        options.setOvalDimmedLayer(true);
        //设置是否展示矩形裁剪框
//        options.setShowCropFrame(false);
        //设置裁剪框横竖线的宽度
//        options.setCropGridStrokeWidth(20);
        //设置裁剪框横竖线的颜色
//        options.setCropGridColor(Color.GREEN);
        //设置竖线的数量
//        options.setCropGridColumnCount(2);
        //设置横线的数量
//        options.setCropGridRowCount(1);

        UCrop.of(uri, destinationUri)
                .withAspectRatio(1, 1)
                .withMaxResultSize(1000, 1000)
                .withOptions(options)
                .start(this);
    }
}
