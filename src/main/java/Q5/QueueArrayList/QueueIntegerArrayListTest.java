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
}