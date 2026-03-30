package Q5.QueueLinkedList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.*;

public class QueueIntegerLinkedListTest {

    @Test
    @DisplayName("Тест на проверку логику АШАЩ")
    public void testFifoLogic() {
        QueueIntegerLinkedList queue = new QueueIntegerLinkedList();

        assertEquals(0, queue.getSize(), "Начальный размер должен быть 0");

        queue.add(100);
        queue.add(200);
        queue.add(300);

        assertEquals(3, queue.getSize(), "Размер после добавления должен быть 3");
        assertEquals(100, queue.peek(), "Первым элементом должен быть 100");

        // Проверяем порядок удаление элементов в очереди (FIFO)
        assertEquals(100, queue.remove(), "Ожидается 100");
        assertEquals(200, queue.remove(), "Ожидается 200");
        assertEquals(1, queue.getSize(), "Размер должен уменьшиться до 1");

        assertEquals(300, queue.remove(), "Ожидается 300");
        assertEquals(0, queue.getSize(), "Очередь должна стать пустой");

        // Проверяем работу после полного опустошения
        queue.add(400);
        assertEquals(400, queue.peek(), "Новый элемент должен стать первым");
        assertEquals(1, queue.getSize());
    }

    @Test
    @DisplayName("Извлечение и чтение из пустой очереди должно выбрасывать NoSuchElementException")
    public void shouldThrowExceptionWhenQueueIsEmpty() {
        QueueIntegerLinkedList queue = new QueueIntegerLinkedList();

        assertThrows(NoSuchElementException.class, () -> queue.remove(),
                "Вызов remove() у пустой очереди должен приводить к ошибке");

        assertThrows(NoSuchElementException.class, () -> queue.peek(),
                "Вызов peek() у пустой очереди должен приводить к ошибке");
    }

    @Test
    @DisplayName("Тест,где Очередь должна корректно принимать, хранить и выдавать null")
    public void testAddNull() {
        QueueIntegerLinkedList queue = new QueueIntegerLinkedList();

        queue.add(null);
        assertEquals(1, queue.getSize());
        assertNull(queue.peek(), "Первым элементом должен быть null");
        assertNull(queue.remove(), "Удаленный элемент должен быть null");
        assertEquals(0, queue.getSize());
    }

    @Test
    @DisplayName("Тест очистки и повторного использования")
    public void testClearAndReuse() {
        QueueIntegerLinkedList queue = new QueueIntegerLinkedList();

        // Несколько циклов добавления/удаления, чтобы проверить,
        // что указатели head и tail не "ломаются"
        for (int i = 0; i < 5; i++) {
            queue.add(i);
            assertEquals(i, queue.remove());
        }
        assertEquals(0, queue.getSize());

        queue.add(10);
        queue.add(20);
        assertEquals(10, queue.peek());
        assertEquals(2, queue.getSize());
    }

    @Test
    @DisplayName("Проверка работы FIFO-логики на объеме в 10 000 элементов")
    public void testLargeNumberOfElements() {
        QueueIntegerLinkedList queue = new QueueIntegerLinkedList();
        int count = 10000;

        for (int i = 0; i < count; i++) {
            queue.add(i);
        }

        assertEquals(count, queue.getSize());

        for (int i = 0; i < count; i++) {
            assertEquals(i, queue.remove(), "Ошибка в порядке на элементе: " + i);
        }

        assertEquals(0, queue.getSize());
    }

    @Test
    @DisplayName("Тест поведения конвейера")
    public void testSequenceOfInterleavedOperations() {
        QueueIntegerLinkedList queue = new QueueIntegerLinkedList();

        queue.add(1); // 1
        queue.add(2); // 1, 2
        assertEquals(1, queue.remove()); // 2

        queue.add(3); // 2, 3
        assertEquals(2, queue.remove()); // 3

        queue.add(4); // 3, 4
        assertEquals(3, queue.remove()); // 4
        assertEquals(4, queue.remove()); // null

        assertEquals(0, queue.getSize());
    }

    @Test
    @DisplayName("Тест минимума, максимума и нуля")
    public void testBoundaryValues() {
        QueueIntegerLinkedList queue = new QueueIntegerLinkedList();
        queue.add(Integer.MAX_VALUE);
        queue.add(Integer.MIN_VALUE);
        queue.add(0);

        assertEquals(Integer.MAX_VALUE, queue.remove());
        assertEquals(Integer.MIN_VALUE, queue.remove());
        assertEquals(0, queue.remove());
    }
}