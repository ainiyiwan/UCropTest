package com.zhimadai.cctvmall.ucroptest;

import android.content.res.Resources;

import java.util.ArrayList;
import java.util.List;


public class DataHelper {

    /**
     * 获取分享信息
     */
    public static List<AuthBottomDlgEntity> getShareData(int index) {
        List<AuthBottomDlgEntity> datas = new ArrayList<>();
        AuthBottomDlgEntity entity1 = new AuthBottomDlgEntity();
        Resources resources = MyApp.mContext.getResources();
        entity1.name = resources.getString(R.string.save_galley);
        entity1.code = 10;
        AuthBottomDlgEntity entity2 = new AuthBottomDlgEntity();
        entity2.name = resources.getString(R.string.cancel);
        entity2.code = 20;

        switch (index) {
            case 0:
                entity1.isCheck = true;
                entity2.isCheck = false;
                break;
            case 1:
                entity1.isCheck = false;
                entity2.isCheck = true;
                break;
        }


        datas.add(entity1);
        datas.add(entity2);

        return datas;
    }
}
