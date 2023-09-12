package pages.BasePages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.BasePages.BasePage;
import utils.Utils;

import java.util.List;

public class W3SchoolBasePage extends BasePage {
    public W3SchoolBasePage(WebDriver driver) {
        super(driver);
    }

    /**
     * @param locator
     * @param columnName
     * @return this function gets the column index dynamically via column text
     */
    public int getTableColumnIndex(String locator, String columnName) {
        List<WebElement> element = driver.findElements(By.xpath(locator + "//th"));
        for (int i = 0; i < element.size(); i++) {
            if (element.get(i).getText().trim().equalsIgnoreCase(columnName)) {
                return i;
            }
        }
        throw new RuntimeException("The column " + Utils.readProperty("columnName") + " does not exist");
    }

    /**
     *
     * @param tableLocator
     * @param columnName
     * @param rowIndex
     * @return returns a specific cell value under a specific column via xpath by only searching for the value without looping
     */
    public String getTableCellValueByXpath(String tableLocator, String columnName, int rowIndex) {
        int column = getTableColumnIndex(tableLocator, columnName);
        WebElement tableRow = driver.findElement(By.xpath(tableLocator + "//tbody//tr[" + rowIndex + "]"));
        WebElement tdCell = tableRow.findElement(By.xpath(".//td[" + (column + 1) + "]"));
        return tdCell.getText();
    }

    /**
     * @param columnIndex
     * @param cellText
     * @return
     * @description another solution for the getTableCellValueByXpath without looping
     */
    public String getTableCellByXpath(int columnIndex, String cellText) {
        WebElement tableElement = driver.findElement(By.xpath("//table[@id='customers']//td[" + columnIndex + " and text()='" + cellText + "']"));
        return tableElement.getText();
    }

    /**
     *
     * @param tableLocator
     * @param searchColumn
     * @param searchText
     * @param returnedColumn
     * @return this function returns a cell value in another column in the same row as the searched for text - example: get company name and return its country
     */
    public String getTableCellText(String tableLocator, int searchColumn, String searchText, String returnedColumn) {
        int column = getTableColumnIndex(tableLocator, returnedColumn);
        List<WebElement> cells = driver.findElements(By.xpath(tableLocator + "//td[" + searchColumn + "]"));
        for (WebElement cell : cells) {
            String cellText = cell.getText();
            if (cellText.trim().equalsIgnoreCase(searchText)) {
                WebElement cellValueUnderSpecificColumn = cell.findElement(By.xpath(".//..//td[" + (column + 1) + "]"));
                return cellValueUnderSpecificColumn.getText();
            }
        }
        throw new RuntimeException("The actual column does not match the expected column name: " + returnedColumn);
    }

    /**
     *
     * @param table
     * @param searchColumn
     * @param searchText
     * @param returnColumnText
     * @return returns true or false based on calling the getTableCellText function
     */
    public boolean verifyTableCellText(String table, int searchColumn, String searchText, String returnColumnText) {
        try {
            getTableCellText(table, searchColumn, searchText, returnColumnText);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }
}
