package com.fmtech.empf.model;

/**
 * ==================================================================
 * Copyright (C) 2016 FMTech All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @email chiangchuna@gmail.com
 * @create_date 2016/1/4 14:34
 * @description ${todo}
 * <p/>
 * Modification History:
 * Date            Author            Version         Description
 * -----------------------------------------------------------------
 * 2016/1/4 14:34  Drew.Chiang       v1.0.0          create
 * <p/>
 * ==================================================================
 */

public class NewsInfo {

    private String newsTitle;
    private String newsPic;
    private String newsTime;

    public NewsInfo(String newsTitle, String newsPic, String newsTime){
        this.newsTitle = newsTitle;
        this.newsPic = newsPic;
        this.newsTime = newsTime;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsPic() {
        return newsPic;
    }

    public void setNewsPic(String newsPic) {
        this.newsPic = newsPic;
    }

    public String getNewsTime() {
        return newsTime;
    }

    public void setNewsTime(String newsTime) {
        this.newsTime = newsTime;
    }


}
