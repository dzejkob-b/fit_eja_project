package fit.bikeja.client.ui;

public class PageFormField extends PageComponent {

    private String caption;
    private String name;
    private String type;
    private String value;
    private IPageFormFieldCallback callback;

    public PageFormField(String caption, String name) {
        this.caption = caption;
        this.name = name;
        this.type = "text";
        this.value = "";
        this.callback = null;
    }

    public String getCaption() {
        return this.caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setValue(Object ref) {
        this.value = this.callback != null ? this.callback.getValue(ref) : "";
    }

    public IPageFormFieldCallback getCallback() {
        return this.callback;
    }

    public void setCallback(IPageFormFieldCallback callback) {
        this.callback = callback;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        if (this.type == "info") {
            sb.append("<strong>");
            sb.append(this.value);
            sb.append("</strong>");

        } else {
            sb.append("<input type='").append(this.type).append("' ");
            sb.append("name='").append(this.name).append("' ");
            sb.append("value='").append(this.value.replaceAll("\"", "\\\"")).append("'");
            sb.append("/>");
        }

        return sb.toString();
    }
}
