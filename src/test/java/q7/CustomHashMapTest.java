package q7;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class CustomHashMapTest {

    private CustomHashMap map;

    @BeforeEach
    void setUp() {
        // Создаем новый экземпляр перед каждым тестом
        map = new CustomHashMap();
    }

    @Test
    @DisplayName("Базовое работа HashMap")
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
        map.put("Key", 200);

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

    @Test
    @DisplayName("Стресс-тест: Множественные расширения (10 000 элементов)")
    void testMultipleResizesStressTest() throws Exception {
        int targetSize = 10000;

        // Добавляем 10 000 элементов
        for (int i = 0; i < targetSize; i++) {
            map.put("StressKey" + i, i);
        }

        // Проверяем, что размер правильный
        assertEquals(targetSize, map.size());

        // Вычисляем, до какого размера должен был вырасти массив.
        //16 -> 32 -> 64 -> 128 -> 256 -> 512 -> 1024 -> 2048 -> 4096 -> 8192 -> 16384
        int currentCapacity = getBucketsLength(map);
        assertTrue(currentCapacity >= targetSize / 0.75,
                "Массив должен был расшириться достаточно, чтобы вместить все элементы. Текущая емкость: " + currentCapacity);

        // Проверяем, что НИ ОДИН элемент не потерялся при всех этих перестроениях
        for (int i = 0; i < targetSize; i++) {
            assertEquals(i, map.get("StressKey" + i), "Потерян элемент StressKey" + i);
        }
    }

    @Test
    @DisplayName("Экстремальные коллизии: Строки с одинаковым HashCode")
    void testExtremeCollisionsAndResizing() throws Exception {
        // Известный факт в Java: строки "Aa" и "BB" имеют одинаковый hashCode (2112).
        // Комбинируя их, мы можем создать много строк с АБСОЛЮТНО одинаковым хэшом.
        // Все они гарантированно попадут в одну корзину (создадут длинный связный список).
        String[] collidingKeys = {
                "AaAa", "AaBB", "BBAa", "BBBB",
                "AaAaAa", "AaAaBB", "AaBBAa", "BBBBBB" // и так далее, хэши у пар могут совпадать
        };

        // Добавим их в мапу
        for (int i = 0; i < collidingKeys.length; i++) {
            map.put(collidingKeys[i], i);
        }

        // Убедимся, что все добавлены и читаются
        assertEquals(collidingKeys.length, map.size());
        for (int i = 0; i < collidingKeys.length; i++) {
            assertEquals(i, map.get(collidingKeys[i]));
        }

        // Теперь "добьем" мапу обычными ключами, чтобы спровоцировать Resize
        for (int i = 0; i < 20; i++) {
            map.put("NormalKey" + i, 100 + i);
        }

        // Массив расширился. Проверяем, что наши коллизионные ключи (которые лежали в одной ячейке)
        // успешно пережили перераспределение (rehash) и не затерли друг друга.
        for (int i = 0; i < collidingKeys.length; i++) {
            assertEquals(i, map.get(collidingKeys[i]), "Коллизионный ключ потерян после Resize: " + collidingKeys[i]);
        }
    }

    @Test
    @DisplayName("Смешанные операции: Добавление, Удаление, Изменение и Resize")
    void testMixedOperationsWithResize() {
        // 1. Добавляем 10 элементов
        for (int i = 0; i < 10; i++) {
            map.put("MixKey" + i, i);
        }

        // 2. Удаляем половину (четные)
        for (int i = 0; i < 10; i += 2) {
            map.remove("MixKey" + i);
        }
        assertEquals(5, map.size(), "Должно остаться 5 элементов");

        // 3. Обновляем оставшиеся (нечетные)
        for (int i = 1; i < 10; i += 2) {
            map.put("MixKey" + i, i * 10);
        }

        // 4. Добавляем еще 20 новых элементов, чтобы ГАРАНТИРОВАННО вызвать Resize
        for (int i = 10; i < 30; i++) {
            map.put("MixKey" + i, i);
        }

        // 5. Проверяем целостность всех данных
        // Проверяем удаленные (должно быть null)
        assertNull(map.get("MixKey0"));
        assertNull(map.get("MixKey2"));

        // Проверяем обновленные
        assertEquals(10, map.get("MixKey1"));
        assertEquals(30, map.get("MixKey3"));

        // Проверяем новые
        assertEquals(29, map.get("MixKey29"));

        // Ожидаемый размер: 5 (оставшиеся) + 20 (новые) = 25
        assertEquals(25, map.size());
    }

    @Test
    @DisplayName("Сохранение и получение null значений")
    void testNullValuesAllowed() {
        // Ключ не может быть null (проверяли ранее), но значение может быть null.
        map.put("KeyWithNullValue", null);

        assertEquals(1, map.size());
        assertNull(map.get("KeyWithNullValue"), "Метод get должен вернуть null, если значение было null");

        // Убеждаемся, что ключ реально существует в мапе, а не просто возвращает null из-за отсутствия
        assertTrue(map.remove("KeyWithNullValue") == null && map.size() == 0,
                "После удаления размер должен стать 0");
    }

    @Test
    @DisplayName("Обновление значения в хвосте коллизионного списка")
    void testUpdateValueDeepInCollisionChain() {
        // Заполняем массив так, чтобы элементы точно попадали в коллизии
        for (int i = 0; i < 50; i++) {
            map.put("Key" + i, i);
        }

        // Изменяем элемент, который 100% находится где-то глубоко в цепочке Node.next
        map.put("Key25", 9999);

        assertEquals(9999, map.get("Key25"));
        assertEquals(50, map.size()); // Размер не должен был измениться при обновлении
    }

    /**
     * Вспомогательный метод для получения длины приватного массива buckets с помощью Reflection API.
     */
    private int getBucketsLength(CustomHashMap mapInstance) throws Exception {
        Field bucketsField = CustomHashMap.class.getDeclaredField("buckets");
        bucketsField.setAccessible(true);
        Object[] buckets = (Object[]) bucketsField.get(mapInstance);
        return buckets.length;
    }
}