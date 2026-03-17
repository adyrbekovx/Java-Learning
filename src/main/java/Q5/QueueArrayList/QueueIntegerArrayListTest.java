package Q5.QueueArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QueueIntegerArrayListTest {

    @Test
    @DisplayName("Тест на многократное расширение массива")
    void testMassiveExpansion() {
        QueueIntegerArrayList queue = new QueueIntegerArrayList(1); // Начинаем с 1
        int count = 1000;

        for (int i = 0; i < count; i++) {
            queue.add(i);
        }

        assertEquals(count, queue.getSize(), "Размер должен быть 1000");
        assertEquals(0, queue.peek(), "Первый элемент должен быть 0");

        for (int i = 0; i < count; i++) {
            assertEquals(i, queue.remove(), "Элементы должны выходить по порядку");
        }
        assertEquals(0, queue.getSize());
    }

    @Test
    @DisplayName("Тест на чередование add и remove (проверка сдвига)")
    void testAddRemoveInterleaving() {
        QueueIntegerArrayList queue = new QueueIntegerArrayList(5);

        queue.add(1);
        queue.add(2);
        assertEquals(1, queue.remove()); // Остался [2]

        queue.add(3);
        queue.add(4);
        assertEquals(2, queue.remove()); // Остались [3, 4]

        assertEquals(2, queue.getSize());
        assertEquals(3, queue.peek());
    }

    @Test
    void testNullElement() {
        QueueIntegerArrayList queue = new QueueIntegerArrayList(2);
        assertThrows(IllegalArgumentException.class, () -> queue.add(null));
    }

    @Test
    @DisplayName("Проверка, что после очистки очередь снова работает")
    void testReuseAfterEmptying() {
        QueueIntegerArrayList queue = new QueueIntegerArrayList(2);

        // Наполнили - очистили
        queue.add(100);
        queue.remove();

        // Снова наполнили
        queue.add(200);
        queue.add(300);

        assertEquals(2, queue.getSize());
        assertEquals(200, queue.remove());
    }

    @Test
    @DisplayName("Тест на 'прокачку' очереди (многократный сдвиг)")
    void testShiftingLogic() {
        // Начальная емкость 10
        QueueIntegerArrayList queue = new QueueIntegerArrayList(10);

        // Добавляем и удаляем элементы многократно.
        // Если внутри используется сдвиг массива влево при каждом remove,
        // этот тест проверит, не ломается ли индекс последнего элемента.
        for (int i = 0; i < 100; i++) {
            queue.add(i);
            assertEquals(i, queue.remove(), "Ошибка на итерации " + i);
        }
        assertEquals(0, queue.getSize());
    }

    @Test
    @DisplayName("Тест на добавление элемента ровно в момент заполнения")
    void testGrowthAtBoundary() {
        int initialCapacity = 4;
        QueueIntegerArrayList queue = new QueueIntegerArrayList(initialCapacity);

        for (int i = 0; i < initialCapacity; i++) {
            queue.add(i);
        }

        // Очередь полна. Следующий add должен вызвать расширение
        assertDoesNotThrow(() -> queue.add(99), "Очередь должна расшириться без ошибок");
        assertEquals(initialCapacity + 1, queue.getSize());

        // Проверка, что старые данные не затерты
        assertEquals(0, queue.peek());
    }

    @Test
    @DisplayName("Исключения при работе с пустой очередью")
    void testEmptyExceptions() {
        QueueIntegerArrayList queue = new QueueIntegerArrayList(5);

        // В зависимости от вашей реализации, тут может быть NoSuchElementException
        // или IllegalStateException. Замените на нужный тип.
        assertThrows(RuntimeException.class, queue::remove, "Удаление из пустой очереди");
        assertThrows(RuntimeException.class, queue::peek, "Чтение из пустой очереди");
    }

    @Test
    @DisplayName("Проверка порядка элементов после расширения")
    void testOrderAfterExpansion() {
        QueueIntegerArrayList queue = new QueueIntegerArrayList(2);
        queue.add(10);
        queue.add(20);

        // Сейчас массив полон. Добавляем 3-й, вызывая resize.
        queue.add(30);

        assertEquals(10, queue.remove());
        assertEquals(20, queue.remove());
        assertEquals(30, queue.remove());
        assertTrue(queue.getSize() == 0);
    }
}