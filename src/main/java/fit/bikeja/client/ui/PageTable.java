package fit.bikeja.client.ui;

import java.util.ArrayList;

public class PageTable extends PageComponent {

    private final String tableCaption;
    private final ArrayList<PageTableField> fields;
    private final ArrayList<Object> lines;

    public PageTable(String tableCaption) {
        this.tableCaption = tableCaption;
        this.fields = new ArrayList<>();
        this.lines = new ArrayList<>();
    }

    public PageTableField addField(String caption, String name, IPageTableFieldCallback callback) {
        PageTableField fd = new PageTableField(caption, name, callback);
        this.fields.add(fd);
        return fd;
    }

    public void addField(IPageTableFieldCallback callback) {
        this.fields.add(new PageTableField("", "", callback));
    }

    public void addLine(Object line) {
        this.lines.add(line);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("<div class='cnt_table'>").append("<span class='tbl_caption'>").append(this.tableCaption).append("</span>");
        sb.append("<table cellspacing='0' cellpadding='0' border='0'>");

        sb.append("<tr>");

        for (PageTableField fn : this.fields) {
            sb.append("<td class='title'>").append(fn.getCaption()).append("</td>");
        }

        sb.append("</tr>");

        for (Object line : this.lines) {
            sb.append("<tr>");

            for (PageTableField fn : this.fields) {
                if (fn.getAlign() == 1) {
                    sb.append("<td style='text-align : center'>");
                } else {
                    sb.append("<td>");
                }

                sb.append(fn.toValue(line)).append("</td>");
            }

            sb.append("</tr>");
        }

        sb.append("</table></div>");

        return sb.toString();
    }
}
