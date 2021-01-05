package fit.bikeja.client.ui;

public class PageContent extends PageComponent {

    private String content;

    public PageContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return this.content;
    }

}
