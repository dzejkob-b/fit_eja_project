package fit.bikeja.client.ui;

public class PageMenuItem {

    private String caption;
    private String path;

    public PageMenuItem(String caption, String path) {
        this.caption = caption;
        this.path = path;
    }

    public String getCaption() {
        return this.caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
