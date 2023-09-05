package org.example.arcus;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SimpleApiTest {

  ArcusWrapper<ShopItem> shopItemArcus;

  @BeforeEach
  void setUp() {
    shopItemArcus = new ArcusWrapper<>();
    shopItemArcus.connect();
  }

  @AfterEach
  void cleanUp() {
    shopItemArcus.close();
  }

  @Test
  void itemTest() {
    ShopItem item = new ShopItem("item1", 1000);
    String prefix = "hotItem";
    String key = prefix + item.getItemId();

    boolean result = shopItemArcus.insert(key, item);
    assertThat(result).isTrue();

    Optional<ShopItem> getItem = shopItemArcus.get(key);
    assertThat(getItem).isPresent();
  }
}
