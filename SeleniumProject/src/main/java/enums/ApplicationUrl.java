package enums;

public enum ApplicationUrl {
    W3SCHOOLS_WEB("https://www.w3schools.com/html/html_tables.asp");

    private final String web;

    ApplicationUrl(String web) {
        this.web = web;
    }

    public String getUrl() {
        return web;
    }

}
