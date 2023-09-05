package org.example.arcus;

public class ShopItem implements java.io.Serializable {

  private String itemName;
  private long itemId;

  private ShopItem() {
  }

  public ShopItem(String itemName, long itemId) {
    this.itemName = itemName;
    this.itemId = itemId;
  }

  public long getItemId() {
    return itemId;
  }

  @Override
  public String toString() {
    return "ShopItem{" +
        "itemName='" + itemName + '\'' +
        ", itemId=" + itemId +
        '}';
  }
}
