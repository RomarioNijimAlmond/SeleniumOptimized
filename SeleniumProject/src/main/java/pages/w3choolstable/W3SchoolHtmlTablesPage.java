package pages.w3choolstable;

import org.openqa.selenium.WebDriver;
import pages.BasePages.W3SchoolBasePage;
import utils.Utils;

public class W3SchoolHtmlTablesPage extends W3SchoolBasePage {

    String XPATH_TABLE_LOCATOR = "//div[@class='w3-white w3-padding notranslate w3-padding-16']";

    public W3SchoolHtmlTablesPage(WebDriver driver) {
        super(driver);
    }

    public String getTableCellByXpath(int rowIndex) {
        String cell = getTableCellValueByXpath(XPATH_TABLE_LOCATOR, Utils.readProperty("columnName"), rowIndex);
        return cell;
    }

    public String getTableCountryCellText(int searchColumn, String searchedText, String returnedColumnValue) {
        String cellText = getTableCellText(XPATH_TABLE_LOCATOR, searchColumn, searchedText, returnedColumnValue);
        return cellText;
    }

    public Boolean validateTableCellValue(int searchColumn, String searchedText, String returnedColumnValue) {
        Boolean cellValueValidation = verifyTableCellText(XPATH_TABLE_LOCATOR, searchColumn, searchedText, returnedColumnValue);
        return cellValueValidation;
    }

}
