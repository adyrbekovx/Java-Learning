package Q5.QueueLinkedList;

import org.junit.jupiter.api.Test;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.*;

public class QueueIntegerLinkedListTest {

    @Test
    public void testFifoLogic() {
        QueueIntegerLinkedList queue = new QueueIntegerLinkedList();

        assertEquals(0, queue.getSize(), "Начальный размер должен быть 0");

        // Добавляем объекты Integer
        queue.add(100);
        queue.add(200);
        queue.add(300);

        assertEquals(3, queue.getSize(), "Размер после добавления должен быть 3");
        assertEquals(100, queue.peek(), "Первым элементом должен быть 100");

        // Проверяем порядок извлечения (FIFO)
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
    public void testEmptyQueueExceptions() {
        QueueIntegerLinkedList queue = new QueueIntegerLinkedList();

        // Проверяем, что методы бросают ошибку, если очередь пуста
        assertThrows(NoSuchElementException.class, queue::remove,
                "remove() должен бросать исключение при пустой очереди");

        assertThrows(NoSuchElementException.class, queue::peek,
                "peek() должен бросать исключение при пустой очереди");
    }

    @Test
    public void testAddNull() {
        QueueIntegerLinkedList queue = new QueueIntegerLinkedList();

        // Проверяем, как ваша реализация обрабатывает null.
        // Если вы запретили null, то ожидаем исключение, если разрешили — корректную работу.
        queue.add(null);
        assertEquals(1, queue.getSize());
        assertNull(queue.peek(), "Первым элементом должен быть null");
        assertNull(queue.remove(), "Удаленный элемент должен быть null");
        assertEquals(0, queue.getSize());
    }

    @Test
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
    public void testPeekDoesNotRemove() {
        QueueIntegerLinkedList queue = new QueueIntegerLinkedList();
        queue.add(55);
        queue.add(66);

        // Проверяем, что peek() возвращает значение, но не удаляет его
        assertEquals(55, queue.peek());
        assertEquals(55, queue.peek());
        assertEquals(2, queue.getSize(), "peek() не должен изменять размер очереди");
    }

    @Test
    public void testSequenceOfInterleavedOperations() {
        QueueIntegerLinkedList queue = new QueueIntegerLinkedList();

        queue.add(1); // [1]
        queue.add(2); // [1, 2]
        assertEquals(1, queue.remove()); // [2]

        queue.add(3); // [2, 3]
        assertEquals(2, queue.remove()); // [3]

        queue.add(4); // [3, 4]
        assertEquals(3, queue.remove()); // [4]
        assertEquals(4, queue.remove()); // []

        assertEquals(0, queue.getSize());
    }

    @Test
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