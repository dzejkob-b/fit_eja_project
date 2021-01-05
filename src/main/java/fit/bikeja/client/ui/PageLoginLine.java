package fit.bikeja.client.ui;

import fit.bikeja.session.UserSession;

public class PageLoginLine extends PageComponent {

    private UserSession userSession;

    public PageLoginLine(UserSession userSession) {
        this.userSession = userSession;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        if (this.userSession.getUser().isPresent()) {
            sb.append("<div class='login_line'>Přihlášen uživatel: <strong>").append(this.userSession.getUser().get().getLoginName()).append("</strong> ");
            sb.append("<form method='post' action='/logout.userSessionDo'><input type='submit' value='Odhlásit'/></form>");
            sb.append("</div>");

        } else {
            sb.append("<p>Uživatel není přihlášen.</p>");
        }

        return sb.toString();
    }
}
