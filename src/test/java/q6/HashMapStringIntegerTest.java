package q6;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HashMapStringIntegerTest {

    private HashMapStringInteger map;

    @BeforeEach
    public void setUp() {
        map = new HashMapStringInteger();
    }

    @Test
    @DisplayName("Тест на работы самого HashMap")
    public void testPutAndGetBasic() {
        map.put("Apple", 10);
        map.put("Banana", 20);

        assertEquals(10, map.get("Apple"), "Значение Apple должно быть 10");
        assertEquals(20, map.get("Banana"), "Значение Banana должно быть 20");
        assertNull(map.get("Cherry"), "Для несуществующего ключа должен возвращаться null");
        assertEquals(2, map.size(), "Размер мапы должен быть 2");
    }

    @Test
    @DisplayName("Обновление значения по существующему ключу (отсутствие дубликатов)")
    public void testPutUpdateValue() {
        map.put("Apple", 10);
        map.put("Apple", 100); // Обновляем значение

        assertEquals(100, map.get("Apple"), "Значение должно обновиться на 100");
        assertEquals(1, map.size(), "Размер не должен увеличиться при обновлении");
    }

    @Test
    @DisplayName("Обработка коллизий: добавление элементов с одинаковым индексом корзины")
    public void testCollisions() {
        // Как мы выяснили ранее, A, H и O дают одинаковый индекс (2) при размере массива 7.
        map.put("A", 1);
        map.put("H", 2);
        map.put("O", 3);

        assertEquals(1, map.get("A"));
        assertEquals(2, map.get("H"));
        assertEquals(3, map.get("O"));
        assertEquals(3, map.size(), "Все 3 элемента с коллизиями должны быть сохранены");
    }

    @Test
    @DisplayName("Базовое удаление элемента (remove)")
    public void testRemoveBasic() {
        map.put("Apple", 10);
        map.put("Banana", 20);

        Integer removedValue = map.remove("Apple");

        assertEquals(10, removedValue, "Метод remove должен вернуть удаленное значение");
        assertNull(map.get("Apple"), "Ключ Apple должен быть удален");
        assertEquals(1, map.size(), "Размер должен уменьшиться до 1");
    }

    @Test
    @DisplayName("Удаление элемента из середины связного списка при наличии коллизий")
    public void testRemoveWithCollision() {
        // A -> H -> O
        map.put("A", 1);
        map.put("H", 2);
        map.put("O", 3);

        Integer removedValue = map.remove("H");

        assertEquals(2, removedValue);
        assertNull(map.get("H"));

        assertEquals(1, map.get("A"));
        assertEquals(3, map.get("O"));
        assertEquals(2, map.size());
    }

    @Test
    @DisplayName("Хранение длинной цепочки узлов (5 штук) в одной корзине (bucket = 7)")
    public void testMultipleNodesInSingleBucket() {
        // Размер массива = 7.
        // Буквы 'A'(65), 'H'(72), 'O'(79), 'V'(86) и 'd'(100) при делении на 7 дают остаток 2.
        // Значит, все они выстроятся в цепочку в ячейке с индексом 2.
        map.put("A", 10);
        map.put("H", 20);
        map.put("O", 30);
        map.put("V", 40);
        map.put("d", 50);

        assertEquals(5, map.size(), "Размер коллекции должен быть 5");

        // Проверяем доступ к каждому звену цепочки
        assertEquals(10, map.get("A"), "Не найден первый узел");
        assertEquals(20, map.get("H"), "Не найден второй узел");
        assertEquals(30, map.get("O"), "Не найден третий узел");
        assertEquals(40, map.get("V"), "Не найден четвертый узел");
        assertEquals(50, map.get("d"), "Не найден пятый узел");

        // Удаляем элемент из середины (O)
        Integer removed = map.remove("O");
        assertEquals(30, removed, "Должны были удалить O со значением 30");
        assertEquals(4, map.size(), "Размер должен стать 4");
        assertNull(map.get("O"), "Элемент O должен быть недоступен");

        // Проверяем целостность хвоста списка
        assertEquals(40, map.get("V"));
        assertEquals(50, map.get("d"));
    }

    @Test
    @DisplayName("Выброс IllegalArgumentException при передаче null в качестве ключа")
    public void testExceptionsOnNullKey() {
        // Проверяем, что методы действительно выбрасывают IllegalArgumentException при передаче null

        assertThrows(IllegalArgumentException.class, () -> {
            map.put(null, 5);
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
        assertNull(map.get("Any"), "Пустая мапа должна возвращать null");
        assertNull(map.remove("Any"), "Удаление из пустой мапы должно возвращать null");
    }

    @Test
    @DisplayName("Сложные краевые случаи: Удаление головы (Head) и хвоста (Tail) связного списка")
    public void testRemoveHeadAndTailEdgeCases() {
        // Создаем коллизию из 3 элементов в корзине 2
        // Структура в памяти: buckets[2] -> A -> H -> O -> null
        map.put("A", 10); // Голова (Head)
        map.put("H", 20); // Середина
        map.put("O", 30); // Хвост (Tail)

        assertEquals(3, map.size());

        // ШАГ 1: Удаляем хвост цепочки ("O")
        Integer removedTail = map.remove("O");

        assertEquals(30, removedTail, "Должны были удалить хвост (30)");
        assertNull(map.get("O"), "Узел O должен быть недоступен");
        assertEquals(2, map.size(), "Размер после удаления хвоста = 2");

        // Убеждаемся, что голова и середина остались целыми
        assertEquals(10, map.get("A"));
        assertEquals(20, map.get("H"));

        // ШАГ 2: Удаляем голову цепочки ("A")
        Integer removedHead = map.remove("A");

        assertEquals(10, removedHead, "Должны были удалить голову (10)");
        assertNull(map.get("A"), "Узел A должен быть недоступен");
        assertEquals(1, map.size(), "Размер после удаления головы = 1");

        // Убеждаемся, что оставшийся элемент выжил и стал новой "головой"
        assertEquals(20, map.get("H"));

        // ШАГ 3: Удаляем последний оставшийся элемент ("H")
        Integer removedLast = map.remove("H");

        assertEquals(20, removedLast);
        assertEquals(0, map.size(), "Коллекция должна стать пустой");
        assertNull(map.get("H"));
    }

}