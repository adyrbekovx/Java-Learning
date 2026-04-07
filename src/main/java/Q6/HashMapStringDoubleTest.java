package Q6;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HashMapStringDoubleTest {

    private HashMapStringDouble map;

    @BeforeEach
    public void setUp() {
        // Создаем чистый объект перед каждым тестом
        map = new HashMapStringDouble();
    }

    @Test
    @DisplayName("Тест на работы самого HashMap")
    public void testPutAndGetBasic() {
        map.put("Apple", 10.5);
        map.put("Banana", 20.75);

        assertEquals(10.5, map.get("Apple"), "Значение Apple должно быть 10.5");
        assertEquals(20.75, map.get("Banana"), "Значение Banana должно быть 20.75");
        assertNull(map.get("Cherry"), "Для несуществующего ключа должен возвращаться null");
        assertEquals(2, map.size(), "Размер мапы должен быть 2");
    }

    @Test
    @DisplayName("Обновление значения по существующему ключу (отсутствие дубликатов)")
    public void testPutUpdateValue() {
        map.put("Apple", 10.5);
        map.put("Apple", 99.99); // Обновляем значение (дубликат ключа)

        assertEquals(99.99, map.get("Apple"), "Значение должно обновиться на 99.99");
        assertEquals(1, map.size(), "Размер не должен увеличиться при обновлении");
    }

    @Test
    @DisplayName("Хранение длинной цепочки узлов (5 штук) в одной корзине (bucket = 5)")
    public void testMultipleNodesInSingleBucket() {
        // Размер массива = 5. Буквы A, F, K, P, U имеют хеши кратные 5.
        // Все они попадут в ячейку с индексом 0.
        map.put("A", 1.1);
        map.put("F", 2.2);
        map.put("K", 3.3);
        map.put("P", 4.4);
        map.put("U", 5.5);

        assertEquals(5, map.size(), "Размер коллекции должен быть 5");

        // Проверяем, что все элементы находятся в связном списке
        assertEquals(1.1, map.get("A"), "Не найден первый узел");
        assertEquals(2.2, map.get("F"), "Не найден второй узел");
        assertEquals(3.3, map.get("K"), "Не найден третий узел");
        assertEquals(4.4, map.get("P"), "Не найден четвертый узел");
        assertEquals(5.5, map.get("U"), "Не найден пятый узел");

        // Удаляем элемент из середины (K)
        Double removed = map.remove("K");
        assertEquals(3.3, removed, "Должны были удалить K со значением 3.3");
        assertEquals(4, map.size(), "Размер должен стать 4");
        assertNull(map.get("K"), "Элемент K должен быть недоступен");

        // Проверяем, что цепочка не порвалась
        assertEquals(4.4, map.get("P"));
        assertEquals(5.5, map.get("U"));
    }

    @Test
    @DisplayName("Обработка коллизий: добавление элементов с одинаковым индексом корзины")
    public void testCollisions() {
        // Для массива размером 5: ключи "A" (65), "F" (70) и "K" (75) попадут в индекс 0
        map.put("A", 1.1);
        map.put("F", 2.2);
        map.put("K", 3.3);

        assertEquals(1.1, map.get("A"));
        assertEquals(2.2, map.get("F"));
        assertEquals(3.3, map.get("K"));
        assertEquals(3, map.size(), "Все 3 элемента с коллизиями должны быть сохранены");
    }

    @Test
    @DisplayName("Базовое удаление элемента (remove)")
    public void testRemoveBasic() {
        map.put("Apple", 10.5);
        map.put("Banana", 20.75);

        Double removedValue = map.remove("Apple");

        assertEquals(10.5, removedValue, "Метод remove должен вернуть удаленное значение");
        assertNull(map.get("Apple"), "Ключ Apple должен быть удален");
        assertEquals(1, map.size(), "Размер должен уменьшиться до 1");
    }

    @Test
    @DisplayName("Удаление элемента из середины связного списка при наличии коллизий")
    public void testRemoveWithCollision() {
        // Создаем коллизию в корзине 0: A -> F -> K
        map.put("A", 1.1);
        map.put("F", 2.2);
        map.put("K", 3.3);

        // Удаляем элемент F из середины связного списка
        Double removedValue = map.remove("F");

        assertEquals(2.2, removedValue);
        assertNull(map.get("F"), "Удаленный элемент не должен находиться");

        // Проверяем, что соседи A и K не пострадали, и список не порвался
        assertEquals(1.1, map.get("A"));
        assertEquals(3.3, map.get("K"));
        assertEquals(2, map.size());
    }

    @Test
    @DisplayName("Выброс IllegalArgumentException при передаче null в качестве ключа")
    public void testExceptionsOnNullKey() {

        assertThrows(IllegalArgumentException.class, () -> {
            map.put(null, 5.0);
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
        // Создаем коллизию из 3 элементов в корзине 0
        // Структура в памяти: buckets[0] -> A -> F -> K -> null
        map.put("A", 1.1); // Голова (Head)
        map.put("F", 2.2); // Середина
        map.put("K", 3.3); // Хвост (Tail)

        assertEquals(3, map.size());

        // ШАГ 1: Удаляем хвост цепочки ("K")
        Double removedTail = map.remove("K");

        assertEquals(3.3, removedTail, "Должны были удалить хвост (3.3)");
        assertNull(map.get("K"), "Узел K должен быть недоступен");
        assertEquals(2, map.size(), "Размер после удаления хвоста = 2");

        // Убеждаемся, что голова и середина остались целыми
        assertEquals(1.1, map.get("A"));
        assertEquals(2.2, map.get("F"));

        // ШАГ 2: Удаляем голову цепочки ("A")
        Double removedHead = map.remove("A");

        assertEquals(1.1, removedHead, "Должны были удалить голову (1.1)");
        assertNull(map.get("A"), "Узел A должен быть недоступен");
        assertEquals(1, map.size(), "Размер после удаления головы = 1");

        // Убеждаемся, что оставшийся элемент выжил и стал новой "головой"
        assertEquals(2.2, map.get("F"));

        // ШАГ 3: Удаляем последний оставшийся элемент ("F")
        Double removedLast = map.remove("F");

        assertEquals(2.2, removedLast);
        assertEquals(0, map.size(), "Коллекция должна стать пустой");
        assertNull(map.get("F"));
    }
}