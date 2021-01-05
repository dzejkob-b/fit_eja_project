package fit.bikeja.client.ui;

public class PageBackLink extends PageComponent {

    private String path;

    public PageBackLink(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "<p><a href='" + this.path + "'>&lt;&lt; Zpět</a></p>";
    }

}
