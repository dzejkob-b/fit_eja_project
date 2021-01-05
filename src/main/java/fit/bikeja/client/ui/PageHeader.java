package fit.bikeja.client.ui;

public class PageHeader extends PageComponent {

    private final String title;

    public PageHeader(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("<head>");
        sb.append("<title>").append(this.title).append("</title>");
        sb.append("<style type='text/css'>");

        sb.append(" body { margin : 0;padding : 30px; } ");
        sb.append(" body, p, td, input, button { font-family : Arial;font-size : 14px; } ");

        sb.append(" p { padding : 0;margin : 0 0 30px 0; } ");
        sb.append(" p.error_msg { color : white;font-size : 16px;background-color : #b54141;padding : 20px;text-align : center; } ");

        sb.append(" div.alone_form { margin : 0;padding : 0 0 30px 0; } ");

        sb.append(" div.form_table { border : 2px solid silver;padding : 15px;margin-bottom : 30px; } ");
        sb.append(" div.form_table table { } ");
        sb.append(" div.form_table table td { padding : 2px; } ");
        sb.append(" div.form_table table td.col_caption { font-weight : bold;font-size : 16px;padding-bottom : 15px; } ");
        sb.append(" div.form_table table td.col_left { padding-right : 20px; } ");
        sb.append(" div.form_table table td.col_right {  } ");
        sb.append(" div.form_table table td.col_right input { width : 200px; } ");

        sb.append(" div.cnt_table { border : 2px solid silver;padding : 15px;margin-bottom : 30px; } ");
        sb.append(" div.cnt_table span.tbl_caption { font-weight : bold;font-size : 16px;padding-bottom : 15px;display : block; } ");
        sb.append(" div.cnt_table table { border-left : 1px solid gray;border-top : 1px solid gray; } ");
        sb.append(" div.cnt_table table td { padding : 2px 10px 2px 10px;border-right : 1px solid gray;border-bottom : 1px solid gray; } ");
        sb.append(" div.cnt_table table td.title { background-color : darkgray;color : white;font-weight : bold; } ");

        sb.append(" div.login_line { border : 2px solid silver;padding : 15px;margin-bottom : 30px;background-color : #f5f5f5; } ");
        sb.append(" div.login_line form { float : right; } ");

        sb.append(" ul.menu { list-style-type : none;margin : 0;padding : 0 0 30px 0;display : block; } ");
        sb.append(" ul.menu li { border : 2px solid silver;background-color : #f5f5f5;display : inline-block; } ");
        sb.append(" ul.menu li a { padding : 15px;display : block; } ");
        sb.append(" ul.menu li+li { margin-left : 15px; } ");

        sb.append("</style>");
        sb.append("</head>");

        return sb.toString();
    }
}
