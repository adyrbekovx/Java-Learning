package q7;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class SimpleHashMapTest {

    private SimpleHashMap map;

    @BeforeEach
    void setUp() {
        // Создаем новый экземпляр перед каждым тестом
        map = new SimpleHashMap();
    }

    @Test
    @DisplayName("Базовое добавление и получение элементов")
    void testPutAndGet() {
        map.put("One", 1);
        map.put("Two", 2);

        assertEquals(1, map.get("One"));
        assertEquals(2, map.get("Two"));
        assertEquals(2, map.size());
    }

    @Test
    @DisplayName("Обновление значения по существующему ключу")
    void testPutUpdatesExistingKey() {
        map.put("Key", 100);
        map.put("Key", 200); // Должно перезаписать значение

        assertEquals(200, map.get("Key"));
        assertEquals(1, map.size());
    }

    @Test
    @DisplayName("Удаление элемента")
    void testRemove() {
        map.put("Key", 100);
        Integer removedValue = map.remove("Key");

        assertEquals(100, removedValue);
        assertNull(map.get("Key"));
        assertEquals(0, map.size());
    }

    @Test
    @DisplayName("Проверка расширения массива при заполнении на 75%")
    void testResizingOn75PercentLoad() throws Exception {
        // 1. Заполняем до 13 элементов.
        // 16 * 0.75 = 12. На 13-м элементе условие (12 > 12) = false. Размер останется 16.
        for (int i = 0; i < 13; i++) {
            map.put("Item" + i, i);
        }

        // Проверяем текущий размер внутреннего массива через Reflection
        assertEquals(16, getBucketsLength(map), "Размер массива должен оставаться 16");
        assertEquals(13, map.size());

        // 2. Добавляем 14-й элемент.
        // Сейчас size = 13. Условие (13 > 12) = true. Должно произойти расширение.
        map.put("Item13", 13);

        // Проверяем, что массив увеличился вдвое 16 * 2 = 32
        assertEquals(32, getBucketsLength(map), "Размер массива должен увеличиться до 32");
        assertEquals(14, map.size());

        // 3. Проверяем, что все старые данные не потерялись и доступны после Rehash
        for (int i = 0; i <= 13; i++) {
            assertEquals(i, map.get("Item" + i), "Элемент " + i + " должен быть доступен после расширения");
        }
    }

    /**
     * Вспомогательный метод для получения длины приватного массива buckets с помощью Reflection API.
     */
    private int getBucketsLength(SimpleHashMap mapInstance) throws Exception {
        Field bucketsField = SimpleHashMap.class.getDeclaredField("buckets");
        bucketsField.setAccessible(true);
        Object[] buckets = (Object[]) bucketsField.get(mapInstance);
        return buckets.length;
    }
}