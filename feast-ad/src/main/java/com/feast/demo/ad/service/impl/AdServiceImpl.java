package com.feast.demo.ad.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.feast.demo.ad.dao.AdvertisementDao;
import com.feast.demo.ad.entity.AdTargetType;
import com.feast.demo.ad.entity.Advertisement;
import com.feast.demo.ad.entity.TAd;
import com.feast.demo.ad.service.AdService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by pinyou on 17-4-11.
 */
@Service
@Transactional(readOnly = true)
public class AdServiceImpl implements AdService {

    @Autowired
    private AdvertisementDao advertisementDao;

    public List<Advertisement> findAll(){
        return (List<Advertisement>) advertisementDao.findAll2();
    }

    @Transactional(readOnly = false)
    public void save(Advertisement advertisement) {
        advertisementDao.save(advertisement);
    }

    /**
     * 无参调用
     * @return
     */
    public String status() {
        return "AD service OK!";
    }

    public String transferEntity(TAd tad) {
        System.out.println(tad.getName());
        return "tad's name " + tad.getName();
    }

    /**
     * 字符串做参数并有字符串返回值
     * @param msg
     * @return
     */
    public String transferString(String msg) {
        return "AdService-->" + msg;
    }

    /**
     * 返回列表的调用
     * @param num
     * @return
     */
    public List<TAd> getAdList(Integer num) {
        List<TAd> list = Lists.newArrayList();
        if (num == null || num <= 0) {
            return list;
        }
        //随即生成num个TA的对象，name为随即字符串
        for (int i = 0; i < num; i++) {
            TAd ad = new TAd();
            ad.setName(getRandomString());

            list.add(ad);
        }

        return list;
    }

    /**
     * 根据参数返回指定类型广告的远程url
     * @param type
     * @param width
     * @param height
     * @return
     */
    public String getRemoteUrl(AdTargetType type, Integer width, Integer height) {
        String url = "";
        switch(type){
            case html:
                url = "/s/html/"+width+"_"+height;
                if(width==1920 && height==1130){
                    url +="_"+((new Date()).getTime()%2);
                }
                break;
            case jpg:
                url = "/s/images/ad/"+width+"_"+height;
                break;
            case png:
                url = "/s/images/ad/"+width+"_"+height;
                break;
            case gif:
                url = "/s/images/ad/"+width+"_"+height;
                break;
        }
        return url.equals("")?null:url+"."+type;
    }

    public List<String> getAdArray(Integer num,String width,String height){
        List<String> result = Lists.newArrayList();
        if(num<1){
            return result;
        }
        String directory =  File.separator+ "s"+File.separator+"html"+File.separator+"ad"+File.separator+width + "_" + height +File.separator;
        for(int i=0;i<num;i++){
            result.add(directory + (new Random()).nextInt(10)+".html");
        }
        return result;
    }

    public Page<Advertisement> findByPage(Pageable pageable) {
        return advertisementDao.findByPage(pageable);
    }

    /**
     * 根据广告类型和尺寸查询广告列表
     * @param type
     * @param width
     * @param height
     * @return
     */
    public List<Advertisement> findByTypeAndSize(String type, Integer width, Integer height) {
        return advertisementDao.findByTypeAndSize(type,width,height);
    }

    /** 根据广告类型和尺寸查询广告列表
     * @param type
     * @param width
     * @param height
     * @return
             */
    public Page<Advertisement> findPageByTypeAndSize(String type, Integer width, Integer height,Integer pageNo,Integer pageNum) {
        Pageable pageable = new PageRequest(pageNo, pageNum);
        return advertisementDao.findPageByTypeAndSize(type,width,height,pageable);
    }

    public List<Advertisement> findBySizeUseNativeSql(Integer width,Integer height,Integer num,boolean isRand) {
        return advertisementDao.findBySizeUseNativeSql(width,height,num,isRand);
    }

    /**
     *
     * @param page 第几页
     * @param size 每页几条
     * @return
     */
    public Page<Advertisement> findByPage(int page,int size) {
        PageRequest pageRequest = new PageRequest(page,size);
        return advertisementDao.findByPage(pageRequest);
    }

    private String getRandomString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            sb.append(String.valueOf((char) Math.round(Math.random() * 25 + 97)));
        }
        return sb.toString();
    }
}
