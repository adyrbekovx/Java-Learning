package q6;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HashMapStringStringTest {

    private HashMapStringString map;

    @BeforeEach
    public void setUp() {
        // Создаем чистую мапу
        map = new HashMapStringString();
    }

    @Test
    @DisplayName("Тест на работы самого HashMap")
    public void testPutAndGetBasic() {
        map.put("Name", "Nurel");
        map.put("City", "Bishkek");

        assertEquals("Nurel", map.get("Name"), "Значение по ключу Name должно быть 'Nurel'");
        assertEquals("Bishkek", map.get("City"), "Значение по ключу City должно быть 'Bishkek'");
        assertNull(map.get("Country"), "Для несуществующего ключа должен возвращаться null");
        assertEquals(2, map.size(), "Размер мапы должен быть 2");
    }

    @Test
    @DisplayName("Обновление значения по существующему ключу (защита от дубликатов)")
    public void testPutUpdateValue() {
        map.put("Color", "Red");
        // Создаем новую строку через new String(), чтобы гарантировать другой адрес в памяти.
        // Если в классе написано == вместо .equals(), этот тест обязательно УПАДЕТ!
        map.put(new String("Color"), "Blue");

        assertEquals("Blue", map.get("Color"), "Значение должно обновиться на 'Blue'");
        assertEquals(1, map.size(), "Размер не должен увеличиться при обновлении");
    }

    @Test
    @DisplayName("Обработка коллизий: добавление элементов с одинаковым индексом корзины")
    public void testCollisions() {
        // Для массива размером 5: ключи "A" (65), "F" (70) и "K" (75) попадут в индекс 0
        map.put("A", "Alpha");
        map.put("F", "Foxtrot");
        map.put("K", "Kilo");

        assertEquals("Alpha", map.get("A"));
        assertEquals("Foxtrot", map.get("F"));
        assertEquals("Kilo", map.get("K"));
        assertEquals(3, map.size(), "Все 3 элемента с коллизиями должны быть сохранены");
    }

    @Test
    @DisplayName("Хранение длинной цепочки узлов (5 штук) в одной корзине (bucket)")
    public void testMultipleNodesInSingleBucket() {
        // У нас массив из 5 ячеек (buckets.length = 5).
        // Буквы A (65), F (70), K (75), P (80) и U (85) имеют числовые коды, которые делятся на 5.
        // Значит, остаток от деления (bucketIndex) для них ВСЕХ будет равен 0!
        // Все эти 5 узлов попадут в самую первую корзину (индекс 0) и образуют длинный связный список.

        map.put("A", "Alpha");
        map.put("F", "Foxtrot");
        map.put("K", "Kilo");
        map.put("P", "Papa");
        map.put("U", "Uniform");

        // Убеждаемся, что мы добавили ровно 5 элементов и никто никого не перезаписал
        assertEquals(5, map.size(), "Размер коллекции должен быть 5");

        // Проверяем, что метод get() способен пройтись по всей длинной цепочке и найти каждого
        assertEquals("Alpha", map.get("A"), "Не найден первый узел в корзине");
        assertEquals("Foxtrot", map.get("F"), "Не найден второй узел в корзине");
        assertEquals("Kilo", map.get("K"), "Не найден третий узел в корзине");
        assertEquals("Papa", map.get("P"), "Не найден четвертый узел в корзине");
        assertEquals("Uniform", map.get("U"), "Не найден пятый узел в корзине");

        // Дополнительно: пробуем удалить элемент из самой середины этой длинной цепочки
        String removed = map.remove("K");
        assertEquals("Kilo", removed, "Должны были удалить Kilo");
        assertEquals(4, map.size(), "Размер должен стать 4");
        assertNull(map.get("K"), "Элемент K должен быть недоступен после удаления");

        // Убеждаемся, что цепочка не порвалась и остальные элементы (особенно после K) на месте
        assertEquals("Papa", map.get("P"));
        assertEquals("Uniform", map.get("U"));
    }

    @Test
    @DisplayName("Базовое удаление элемента (remove)")
    public void testRemoveBasic() {
        map.put("Hero", "Batman");
        map.put("Villain", "Joker");

        String removed = map.remove("Hero");

        assertEquals("Batman", removed, "Метод remove должен вернуть удаленное значение");
        assertNull(map.get("Hero"), "Ключ Hero должен быть удален");
        assertEquals(1, map.size(), "Размер должен уменьшиться до 1");
    }

    @Test
    @DisplayName("Удаление элемента из середины связного списка при наличии коллизий")
    public void testRemoveWithCollision() {
        // Создаем коллизию в корзине 0: A -> F -> K
        map.put("A", "Alpha");
        map.put("F", "Foxtrot");
        map.put("K", "Kilo");

        // Удаляем элемент F из середины связного списка
        String removedValue = map.remove("F");

        assertEquals("Foxtrot", removedValue);
        assertNull(map.get("F"), "Удаленный элемент не должен находиться");

        // Проверяем, что соседи A и K не пострадали, и список не порвался
        assertEquals("Alpha", map.get("A"));
        assertEquals("Kilo", map.get("K"));
        assertEquals(2, map.size());
    }

    @Test
    @DisplayName("Выброс IllegalArgumentException при передаче null в качестве ключа")
    public void testExceptionsOnNullKey() {

        assertThrows(IllegalArgumentException.class, () -> {
            map.put(null, "Value");
        }, "Метод put должен бросать исключение при key == null");

        assertThrows(IllegalArgumentException.class, () -> {
            map.get(null);
        }, "Метод get должен бросать исключение при key == null");

        assertThrows(IllegalArgumentException.class, () -> {
            map.remove(null);
        }, "Метод remove должен бросать исключение при key == null");
    }

    @Test
    @DisplayName("Корректное поведение пустой коллекции (размер 0 и возврат null)")
    public void testEmptyMap() {
        assertEquals(0, map.size(), "Новая мапа должна иметь размер 0");
        assertNull(map.get("Key"), "Пустая мапа должна возвращать null");
        assertNull(map.remove("Key"), "Удаление из пустой мапы должно возвращать null");
    }

    @Test
    @DisplayName("Сложные краевые случаи: Удаление головы (Head) и хвоста (Tail) связного списка")
    public void testRemoveHeadAndTailEdgeCases() {
        // Создаем коллизию из 3 элементов в корзине 0 (для размера массива 5)
        // Структура в памяти будет такой: buckets[0] -> A -> F -> K -> null
        map.put("A", "Alpha");   // Голова (Head)
        map.put("F", "Foxtrot"); // Середина
        map.put("K", "Kilo");    // Хвост (Tail)

        assertEquals(3, map.size());

        // ШАГ 1: Удаляем хвост цепочки ("K")
        // Алгоритм должен дойти до конца, убедиться, что next у "K" это null,
        // и сказать предыдущему узлу ("F") указывать на null.
        String removedTail = map.remove("K");

        assertEquals("Kilo", removedTail, "Должны были удалить Kilo (хвост)");
        assertNull(map.get("K"), "Kilo должен быть недоступен");
        assertEquals(2, map.size(), "Размер после удаления хвоста = 2");

        // Убеждаемся, что голова и середина остались целыми
        assertEquals("Alpha", map.get("A"));
        assertEquals("Foxtrot", map.get("F"));

        // ШАГ 2: Удаляем голову цепочки ("A")
        // Это самый опасный сценарий. Элемент "A" лежит прямо в buckets[0].
        // Метод должен взять buckets[0] и переписать его так, чтобы он указывал на "F".
        String removedHead = map.remove("A");

        assertEquals("Alpha", removedHead, "Должны были удалить Alpha (голову)");
        assertNull(map.get("A"), "Alpha должна быть недоступна");
        assertEquals(1, map.size(), "Размер после удаления головы = 1");

        // Убеждаемся, что оставшийся элемент "F" выжил и теперь сам стал "головой"
        assertEquals("Foxtrot", map.get("F"));

        // ШАГ 3: Удаляем последний оставшийся элемент ("F")
        // Теперь корзина buckets[0] должна стать полностью null.
        String removedLast = map.remove("F");

        assertEquals("Foxtrot", removedLast);
        assertEquals(0, map.size(), "Мапа должна стать абсолютно пустой");
        assertNull(map.get("F"));
    }
}