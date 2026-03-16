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
}