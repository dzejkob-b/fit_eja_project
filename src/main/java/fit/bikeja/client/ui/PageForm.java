package fit.bikeja.client.ui;

import java.util.ArrayList;

public class PageForm extends PageComponent {

    protected final ArrayList<PageFormField> fields;
    protected final String formAction;
    protected final String formCaption;
    protected final String buttonCaption;

    public PageForm(String action, String formCaption, String buttonCaption) {
        this.fields = new ArrayList<>();
        this.formAction = action;
        this.formCaption = formCaption;
        this.buttonCaption = buttonCaption;
    }

    public PageForm(String action, String formCaption) {
        this.fields = new ArrayList<>();
        this.formAction = action;
        this.formCaption = formCaption;
        this.buttonCaption = null;
    }

    public PageForm addField(String caption, String name) {
        PageFormField fn = new PageFormField(caption, name);

        if (this.buttonCaption == null) {
            fn.setType("info");
        }

        this.fields.add(fn);
        return this;
    }

    public PageForm addField(String caption, String name, IPageFormFieldCallback callback) {
        PageFormField fn = new PageFormField(caption, name);

        fn.setCallback(callback);

        if (this.buttonCaption == null) {
            fn.setType("info");
        }

        this.fields.add(fn);
        return this;
    }

    public void setData(Object ref) {
        for (PageFormField fn : this.fields) {
            fn.setValue(ref);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("<form method='post' action='").append(this.formAction).append("'>");
        sb.append("<div class='form_table'><table cellspacing='0' cellpadding='0' border='0'>");

        sb.append("<tr>");
        sb.append("<td class='col_caption' colspan='2'>").append(this.formCaption).append("</td>");
        sb.append("</tr>");

        for (PageFormField fn : this.fields) {
            sb.append("<tr>");
            sb.append("<td class='col_left'>").append(fn.getCaption()).append(": </td>");
            sb.append("<td class='col_right'>").append(fn.toString()).append("</td>");
            sb.append("</tr>");
        }

        if (this.buttonCaption != null) {
            sb.append("<tr>");
            sb.append("<td>&nbsp;</td>");
            sb.append("<td class='col_button'>").append("<input type='submit' value='").append(this.buttonCaption).append("'/>").append("</td>");
            sb.append("</tr>");
        }

        sb.append("</table>");
        sb.append("</form></div>");

        return sb.toString();
    }
}
