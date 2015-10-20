package com.parse.ummalibu.objects;

import com.parse.ParseFile;
import com.parse.ParseObject;

/**
 * Created by rjaylward on 12/16/14.
 */
public class Sermon extends ParseObject{

        private ParseFile graphic;
        private String title;
        private String url;
        private String datePreached;

        Sermon(ParseFile graphicData, String sermonTitle, String sermonUrl, String datePreachedText) {
            graphic = graphicData;
            title = sermonTitle;
            url = sermonUrl;
            datePreached = datePreachedText;

        }

        public String getUrl() {
            return url;
        }
        public void setUrl(String url) {
            this.url = url;
        }
        public String getTitle() {
            return title;
        }
        public void setTitle(String title) {
            this.title = title;
        }
        public ParseFile getGraphic() {
            return graphic;
        }
        public void setContent(ParseFile graphic) {
            this.graphic = graphic;
        }
        public String getDatePreached() {
            return datePreached;
        }
        public void setDatePreached(String datePreached) {
            this.datePreached = datePreached;
        }

}
