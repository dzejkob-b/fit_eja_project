package fit.bikeja.client.ui;

import java.util.ArrayList;

public class PageComposer {

    private final ArrayList<PageComponent> headComp;
    private final ArrayList<PageComponent> bodyComp;

    public PageComposer() {
        this.headComp = new ArrayList<>();
        this.bodyComp = new ArrayList<>();
    }

    public void addHead(PageComponent c) {
        this.headComp.add(c);
    }

    public void addBody(PageComponent c) {
        this.bodyComp.add(c);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("<!DOCTYPE html>");
        sb.append("<html lang='cs'>");
        sb.append("<head>");

        for (PageComponent c : this.headComp) {
            sb.append(c.toString());
        }

        sb.append("</head>");
        sb.append("<body>");

        for (PageComponent c : this.bodyComp) {
            sb.append(c.toString());
        }

        sb.append("</body>");
        sb.append("</html>");

        return sb.toString();
    }
}
