package com.antocistudios.datalayer.entity;

import com.orm.SugarRecord;

/**
 * Created by Adrian Antoci.
 *
 */
public class DBMovie extends SugarRecord<DBMovie> {

    private String title;
    private String posterURL;

    public DBMovie(){
        //
    }
    public DBMovie(String title, String posterURL) {
        this.title = title;
        this.posterURL = posterURL;
    }

    public long getID() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterURL() {
        return posterURL;
    }

    public void setPosterURL(String posterURL) {
        this.posterURL = posterURL;
    }
}
