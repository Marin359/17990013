package com.example.djw.bean;

import com.example.djw.R;

import java.util.ArrayList;

public class GoodsInfo {
    public long rowid; // 行号
    public int sn; // 序号
    public String name; // 名称
    public String desc; // 描述
    public float price; // 价格
    public String thumb_path; // 小图的保存路径
    public String pic_path; // 大图的保存路径
    public int thumb; // 小图的资源编号
    public int pic; // 大图的资源编号

    public GoodsInfo() {
        rowid = 0L;
        sn = 0;
        name = "";
        desc = "";
        price = 0;
        thumb_path = "";
        pic_path = "";
        thumb = 0;
        pic = 0;
    }

    // 声明一个手机商品的名称数组
    private static String[] mNameArray = {
            "字母哥2代", "AIR JORDAN MARS 270", "AJ1", "滑板鞋", "实战减震篮球鞋", "AJ4", "AJ4", "篮球鞋"
    };
    // 声明一个手机商品的描述数组
    private static String[] mDescArray = {
            "胜道运动耐克 男子 ZOOM FREAK 2 EP 字母哥2代 篮球鞋 CK5825 CK5825-005 43",
            "AJ 男子 AIR JORDAN MARS 270 LOW 运动鞋 CK1196 CK1196-101 42",
            "NIKE耐克Air Jordan1 mid AJ1乔1男鞋女鞋液态金UNION芝加哥湖人脚趾篮球鞋 黑绿脚趾554724-067/554725-067 39",
            "NIKE耐克官方旗舰男鞋滑板鞋 2020冬季新品高帮运动鞋防滑耐磨轻便缓震鞋子透气休闲鞋CD5466 CD5466-200/亚麻色/中帮 42/265mm",
            "NIKE耐克男鞋2020秋冬季新款运动鞋RENEW ELEVATE实战减震篮球鞋 CK2669-001偏小半码 40",
            " NIKE耐克官方男鞋 2020秋季官网新品AJ4兄弟Air max气垫鞋子缓震外场实战运动鞋篮球鞋 CN5668-600/AJ兄弟款/黑红 41",
            "NIKE耐克官网男鞋 2020冬季新品AJ4兄弟款休闲鞋Air Max气垫鞋跑步鞋运动鞋实战篮球鞋 DB5918-001/AJ4兄弟款/店长推荐 41",
            "NIKE耐克男鞋2020秋季新款运动鞋LEBRON詹姆斯实战减震耐磨篮球鞋 CD0188-001/主图款 42"
    };
    // 声明一个手机商品的价格数组
    private static float[] mPriceArray = {764, 881, 1279, 699, 499, 679,799,499};
    // 声明一个手机商品的小图数组
    private static int[] mThumbArray = {
            R.drawable.xie1, R.drawable.xie2, R.drawable.xie3,
            R.drawable.xie4, R.drawable.xie5, R.drawable.xie6,
            R.drawable.xie7, R.drawable.xie8
    };
    // 声明一个手机商品的大图数组
    private static int[] mPicArray = {
            R.drawable.xie1, R.drawable.xie2, R.drawable.xie3,
            R.drawable.xie4, R.drawable.xie5, R.drawable.xie6,
            R.drawable.xie7, R.drawable.xie8
    };

    // 获取默认的手机信息列表
    public static ArrayList<GoodsInfo> getDefaultList() {
        ArrayList<GoodsInfo> goodsList = new ArrayList<GoodsInfo>();
        for (int i = 0; i < mNameArray.length; i++) {
            GoodsInfo info = new GoodsInfo();
            info.name = mNameArray[i];
            info.desc = mDescArray[i];
            info.price = mPriceArray[i];
            info.thumb = mThumbArray[i];
            info.pic = mPicArray[i];
            goodsList.add(info);
        }
        return goodsList;
    }
}