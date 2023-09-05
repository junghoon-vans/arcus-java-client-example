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
    shopItemArcus.deleteAll();
    shopItemArcus.close();
  }

  @Test
  void shouldSaveItem() {
    // given
    ShopItem item = new ShopItem("item1", 1000);
    String prefix = "hotItem";
    String key = prefix + item.getItemId();

    // when
    boolean result = shopItemArcus.insert(key, item);

    // then
    assertThat(result).isTrue();
  }

  @Test
  void shouldGetItem() {
    // given
    ShopItem item = new ShopItem("item1", 1000);
    String prefix = "hotItem";
    String key = prefix + item.getItemId();
    boolean result = shopItemArcus.insert(key, item);

    // when
    Optional<ShopItem> getItem = shopItemArcus.get(key);

    // then
    assertThat(getItem).isPresent();
  }
}
