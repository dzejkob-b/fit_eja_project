package fit.bikeja.client.ui;

public class PageTableField {

    private final String name;
    private final String caption;
    private final IPageTableFieldCallback callback;
    private int align;

    public PageTableField(String caption, String name, IPageTableFieldCallback callback) {
        this.caption = caption;
        this.name = name;
        this.callback = callback;
        this.align = 0;
    }

    public String getName() {
        return this.name;
    }

    public String getCaption() {
        return this.caption;
    }

    public String toValue(Object line) {
        return this.callback.toValue(line);
    }

    public int getAlign() {
        return this.align;
    }

    public void setAlign(int align) {
        this.align = align;
    }
}
