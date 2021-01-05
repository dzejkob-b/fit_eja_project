package fit.bikeja.client.ui;

public class PageActionForm extends PageForm {

    private String promptMessage;
    private boolean inCont;

    public PageActionForm(String formAction, String buttonCaption) {
        super(formAction, "", buttonCaption);
        this.promptMessage = null;
        this.inCont = false;
    }

    public PageActionForm setPromptMessage(String promptMessage) {
        this.promptMessage = promptMessage;
        return this;
    }

    public PageActionForm addFieldHidden(String name, String value) {
        PageFormField fn = new PageFormField("", name);

        fn.setType("hidden");
        fn.setValue(value);

        this.fields.add(fn);
        return this;
    }

    public PageActionForm setInCont(boolean inCont) {
        this.inCont = inCont;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        if (this.inCont) {
            sb.append("<div class='alone_form'>");
        }

        sb.append("<form method='post' action='").append(this.formAction).append("'");

        if (this.promptMessage != null) {
            sb.append(" onsubmit=\"return confirm('").append(this.promptMessage).append("')\"");
        }

        sb.append(">");

        for (PageFormField fn : this.fields) {
            sb.append(fn.toString());
        }

        sb.append("<input type='submit' value='").append(this.buttonCaption).append("'");

        sb.append("/>");
        sb.append("</form>");

        if (this.inCont) {
            sb.append("</div>");
        }

        return sb.toString();
    }

}
