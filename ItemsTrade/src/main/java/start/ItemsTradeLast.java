package start;
import presistens.DAOParsingItems;
/**
 * Script for model launch.
 */
public class ItemsTradeLast {
    public static void main(String[] args) {
        DAOParsingItems daoParsingItems = DAOParsingItems.getINSTANCE();
        daoParsingItems.addItemsLast(daoParsingItems.getItem(daoParsingItems.getItemConvert("./ParsingAutoRu/src/main/resources/lastitems.json")));
    }
}
