package fit.bikeja.client.ui;

import java.util.ArrayList;

public class PageMenu extends PageComponent {

    private final ArrayList<PageMenuItem> items;

    public PageMenu() {
        this.items = new ArrayList<>();
    }

    public PageMenu addItem(String caption, String path) {
        this.items.add(new PageMenuItem(caption, path));
        return this;
    }

    public PageMenu addDefault() {
        this.addItem("Správa uživatelů", "/users");
        this.addItem("Správa a rezervace předmětů", "/items");

        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("<ul class='menu'>");

        for (PageMenuItem it : this.items) {
            sb.append("<li>").append("<a href='").append(it.getPath()).append("'>");
            sb.append(it.getCaption()).append("</a></li>");
        }

        sb.append("</ul>");

        return sb.toString();
    }
}
